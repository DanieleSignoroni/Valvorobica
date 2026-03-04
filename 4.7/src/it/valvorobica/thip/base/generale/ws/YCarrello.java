package it.valvorobica.thip.base.generale.ws;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.Column;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.articolo.ArticoloTM;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.cliente.ModalitaConsegna;
import it.thera.thip.base.cliente.ModalitaConsegnaTM;
import it.thera.thip.base.cliente.ModalitaSpedizione;
import it.thera.thip.base.cliente.ModalitaSpedizioneTM;
import it.thera.thip.base.fornitore.FornitoreAcquistoTM;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.base.partner.AnagraficoDiBasePrimroseTM;
import it.thera.thip.base.partner.Valuta;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.vendite.generaleVE.CondizioniDiVendita;
import it.thera.thip.vendite.generaleVE.RicercaCondizioniDiVendita;
import it.valvorobica.thip.base.portal.YCarrelloPortaleTM;
import it.valvorobica.thip.base.portal.YUserPortalSession;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 31/08/2023
 * <br><br>
 * <b>71392	DSSOF3	31/08/2023</b>	<p>Prima stesura.<br>
 * 									   WebService per la retrieve e la visualizzazione del carrello di un utente di tipo cliente.<br>
 * 									   Retrieve su tabella {@value YCarrelloPortaleTM#TABLE_NAME}, recupero alcuni dati dalla tabella articoli,
 * 									   e il prezzo dal listino.
 * 									</p>
 * <b>71478	DSSOF3	22/03/2024</b>
 * <p>
 * Ereditare da {@link YPortalGenRequestJSON}.<br>
 * </p>
 * <b>71660	DSSOF3	10/16/2024</b>
 * <p>
 * Ritornare alcune informazioni utili al checkout dal ws.
 * </p>
 * <b>71699	DSSOF3	14/11/2024</b>
 * <p>
 * Ridisegnare retrieve.<br>
 * Articoli critici e articoli con prezzo 0 oscurati.<br>
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72114    09/09/2025  DSSOF3   Introdurre gestione giacenza Aellebi
 */

public class YCarrello extends YPortalGenRequestJSON {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		ClienteVendita cliente = getUserPortalSession().getCliente();
		if(cliente == null) {
			try {
				cliente = (ClienteVendita) ClienteVendita.elementWithKey(ClienteVendita.class,
						KeyHelper.buildObjectKey(new String[] {getUserPortalSession().getIdAzienda(),getUserPortalSession().getIdCliente()}), PersistentObject.NO_LOCK);
				getUserPortalSession().setCliente(cliente);
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		ArrayList<YCarrello.ItemCarrello> items = new ArrayList<YCarrello.ItemCarrello>();
		String select = "SELECT "
				+ " C."+YCarrelloPortaleTM.PROGRESSIVO+", "
				+ " C."+YCarrelloPortaleTM.R_ARTICOLO+", "
				+ " A."+ArticoloTM.DESC_ESTESA+", "
				+ " C."+YCarrelloPortaleTM.QUANTITA+", "
				+ " C."+YCarrelloPortaleTM.R_CLIENTE+", "
				+ " C."+YCarrelloPortaleTM.TIMESTAMP_CRZ+", "
				+ " C."+YCarrelloPortaleTM.TIMESTAMP_AGG+","
				+ " SB.QTA_GIAC_PRM AS DISPONIBILITA "
				+ " FROM "+YCarrelloPortaleTM.TABLE_NAME+" C "
				+ " INNER JOIN "+ArticoloTM.TABLE_NAME+" A "
				+ " ON A."+ArticoloTM.ID_AZIENDA+" = C."+YCarrelloPortaleTM.ID_AZIENDA+" "
				+ " AND A."+ArticoloTM.ID_ARTICOLO+" = C."+YCarrelloPortaleTM.R_ARTICOLO+" "
				+ " INNER JOIN SOFTRE.Y_SALDI_BASE_V03 SB ON SB.ID_AZIENDA = A.ID_AZIENDA  AND SB.ID_ARTICOLO = A.ID_ARTICOLO ";
		String where = "WHERE C."+YCarrelloPortaleTM.ID_AZIENDA+" = '"+getUserPortalSession().getIdAzienda()+"'"
				+ " AND C."+YCarrelloPortaleTM.R_UTENTE_PORTALE+" = '"+getUserPortalSession().getIdUtente()+"' ";
		where += " AND C."+YCarrelloPortaleTM.GES_CONTO_DEP+" = '"+Column.FALSE_CHAR+"' ";
		CachedStatement cs = null;
		ResultSet rs = null;
		try {		
			cs = new CachedStatement(select+where);
			rs = cs.executeQuery();
			while(rs.next()) {
				ItemCarrello item = new ItemCarrello();
				Integer idProgressivo = rs.getInt(YCarrelloPortaleTM.PROGRESSIVO);
				String idArticolo = rs.getString(YCarrelloPortaleTM.R_ARTICOLO).trim();
				String descExtArticolo =  rs.getString(ArticoloTM.DESC_ESTESA).trim();
				BigDecimal quantita = rs.getBigDecimal(YCarrelloPortaleTM.QUANTITA);
				String idCliente = rs.getString(YCarrelloPortaleTM.R_CLIENTE).trim();
				item.setIdProgressivo(idProgressivo.toString());
				item.setIdArticolo(idArticolo);
				item.setIdCliente(idCliente);
				item.setQuantita(quantita.setScale(2,RoundingMode.DOWN).toString());
				item.setDescrExtArticolo(descExtArticolo);
				String key = KeyHelper.buildObjectKey(new String[]{
						getUserPortalSession().getIdAzienda(),
						getUserPortalSession().getIdUtente(),
						idProgressivo.toString()
				});
				BigDecimal disp = rs.getBigDecimal("DISPONIBILITA");
				item.setDisponibilita(disp.setScale(2,RoundingMode.DOWN).toString());
				item.setKey(key);
				items.add(item);
			}
		}catch (Throwable t) {
			t.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(cs != null) {
					cs.free();
				}
			}catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		BigDecimal totale = BigDecimal.ZERO;
		try {
			Valuta valuta = (Valuta) Valuta.elementWithKey(Valuta.class, "EUR", 0);
			for(ItemCarrello item : items) {

				BigDecimal disp = new BigDecimal(item.getDisponibilita());
				BigDecimal qta = new BigDecimal(item.getQuantita());
				Articolo articolo = (Articolo) Articolo.elementWithKey(Articolo.class, 
						KeyHelper.buildObjectKey(new String[] {
								getUserPortalSession().getIdAzienda(),
								item.getIdArticolo()
						}), PersistentObject.NO_LOCK);
				RicercaCondizioniDiVendita rcdv = new RicercaCondizioniDiVendita();
				CondizioniDiVendita cdv = null;
				cdv = rcdv.ricercaCondizioniDiVendita(
						getUserPortalSession().getIdAzienda(),
						getUserPortalSession().getCliente().getListino(), 
						getUserPortalSession().getCliente(),
						articolo,
						articolo.getUMDefaultVendita(),
						BigDecimal.ONE, 
						null, 
						null,
						TimeUtils.getCurrentDate(),
						getUserPortalSession().getCliente().getAgente(),
						getUserPortalSession().getCliente().getSubAgente(),
						null,
						BigDecimal.ONE,
						valuta,
						articolo.getUMPrmMag(),
						null);
				if(cdv != null) {
					BigDecimal prezzo = cdv.getPrezzoAlNettoSconti();
					if(prezzo != null) {
						totale = totale.add(prezzo.multiply(qta));
						item.setPrezzo(prezzo.setScale(2, RoundingMode.DOWN).toString());

						//Se la disponibilita non copre la richiesta o il prezzo e' <= 0 allora l'articolo e' critico
						if(qta.compareTo(disp) > 0 || prezzo.compareTo(BigDecimal.ZERO) <= 0) {
							//72114
							BigDecimal dispAellebi = YCatalogoPortaleDettaglio.getDisponibilita(articolo, "002");
							if(dispAellebi.compareTo(qta) >= 0) {
								item.setDisponibileAellebi(true);
							}else {
								item.setArticoloCritico(true);
							}
							//72114
						}
					}else {
						item.setArticoloCritico(true);
					}

				}else {
					item.setArticoloCritico(true);
				}

				item.setIdArticoloCatalogo(articolo.getVecchioArticolo() != null ? articolo.getVecchioArticolo() : articolo.getIdArticolo());
				item.setUmDefVen(articolo.getUMDefaultVendita().getIdUnitaMisura());

			}

		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}

		m.put("total",totale);
		m.put("items", items);

		YUserPortalSession user = getUserPortalSession();
		JSONObject deliveryMethods = user.getDeliveryMethods();

		if (deliveryMethods == null) {
			deliveryMethods = deliveryMethodsList();
			user.setDeliveryMethods(deliveryMethods);
		}
		m.put("deliveryMethods", deliveryMethods);

		JSONObject shipmentMethods = user.getShipmentMethods();
		if (shipmentMethods == null) {
			shipmentMethods = shipmentMethodsList();
			user.setShipmentMethods(shipmentMethods);
		}
		m.put("shipmentMethods", shipmentMethods);

		JSONObject shippers = user.getShippers();
		if(shippers == null) {
			shippers = shippers();
			user.setShippers(shippers);
		}
		m.put("shippers", shippers);

		m.put("salesConditionsPDFKey", getChiaveDocumentoDigitaleCondizioniVendita());

		boolean isVettoreRequired = true;
		JSONObject shipmentMethod = user.getShipmentDefaultMethod();

		if(cliente.getModalitaSpedizione() != null && shipmentMethod == null) {
			shipmentMethod = new JSONObject();
			ModalitaSpedizione mod = (ModalitaSpedizione) cliente.getModalitaSpedizione();
			if(!mod.getDescrizione().getDescrizione().equals(".") && (mod.getIdModSpedizione().equals("VE") || mod.getIdModSpedizione().equals("DE"))) {
				shipmentMethod.put("Id", mod.getIdModSpedizione());
				shipmentMethod.put("Description", mod.getDescrizione().getDescrizione());
				//shipmentMethod.put("isVettoreRequired", mod.getIdModSpedizione().equals("VE") ? true : false);
			}
			user.setShipmentDefaultMethod(shipmentMethod);
			//			if(mod.getIdModSpedizione().equals("VE")) {
			//				isVettoreRequired = true;
			//			}
		}else {
			shipmentMethod = null;
		}

		m.put("shipmentDefaultMethod", shipmentMethod);

		JSONObject defaultShipper = user.getDefaultShipper();

		if(cliente.getVettore1() != null && defaultShipper == null) {
			defaultShipper = new JSONObject();
			defaultShipper.put("Id", cliente.getVettore1().getIdFornitore());
			defaultShipper.put("Description", cliente.getVettore1().getRagioneSociale());
		}

		m.put("deafaultShipper", defaultShipper);

		JSONObject deafultDeliveryMethod = user.getDefaultDeliveryMethod();

		if(cliente.getModalitaConsegna() != null && deafultDeliveryMethod == null) {
			deafultDeliveryMethod = new JSONObject();

			deafultDeliveryMethod.put("Id", cliente.getModalitaConsegna().getIdModConsegna());
			deafultDeliveryMethod.put("Description", cliente.getModalitaConsegna().getDescrizione());

			//..Se modalita' di consegna == ASSEGNATO allora aggiungo questo valore
			if(cliente.getIdModalitaConsegna().equals("02")) {
				JSONArray shippersArray = (JSONArray) shippers.get("shippers");
				boolean alreadyPresent = false;
				for (int i = 0; i < shippersArray.length(); i++) {
					JSONObject obj = shippersArray.getJSONObject(i);
					if ("X".equals(obj.optString("Id"))) {
						alreadyPresent = true;
						break;
					}
				}

				if (!alreadyPresent) {
					JSONObject json = new JSONObject();
					json.put("Id", "X");
					json.put("Description", "Attendo avviso merce pronta");
					shippersArray.put(json);
				}
			}
		}

		m.put("deafultDeliveryMethod", deafultDeliveryMethod);

		JSONObject datiDestinazione = new JSONObject();

		if(user.getCliente().getIndirizzoSpedizione() != null) {

			datiDestinazione.put("Indirizzo", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getIndirizzo());
			datiDestinazione.put("CAP", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getCAP());
			datiDestinazione.put("Localita", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getLocalita());
			datiDestinazione.put("Provincia", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getIdProvincia());
		}else {
			datiDestinazione.put("Indirizzo", user.getCliente().getIndirizzoBase().getIndirizzo());
			datiDestinazione.put("CAP", user.getCliente().getIndirizzoBase().getCAP());
			datiDestinazione.put("Localita", user.getCliente().getIndirizzoBase().getLocalita());
			datiDestinazione.put("Provincia", user.getCliente().getIndirizzoBase().getIdProvincia());
		}

		m.put("shipmentData", datiDestinazione);

		//		if(user.getShipmentDefaultMethod() != null && m.get("deafultDeliveryMethod") != null) {
		//			JSONObject modalitaConsegna = (JSONObject) m.get("deafultDeliveryMethod");
		//			if(modalitaConsegna.get("Id").equals("02")) {
		//				isVettoreRequired = false;
		//			}
		//		}

		m.put("isVettoreRequired", isVettoreRequired);

		return m;
	}

	protected JSONObject shippers() {
		JSONObject response = new JSONObject();
		String where = " SELECT F."+FornitoreAcquistoTM.ID_FORNITORE+",A."+AnagraficoDiBasePrimroseTM.RAGIONE_SOCIALE+" FROM "+FornitoreAcquistoTM.TABLE_NAME+" F "
				+ "INNER JOIN "+AnagraficoDiBasePrimroseTM.TABLE_NAME+" A "
				+ " ON F."+FornitoreAcquistoTM.R_ANAGRAFICO+" = A."+AnagraficoDiBasePrimroseTM.ID_ANAGRAFICO+" "
				+ "WHERE F."+FornitoreAcquistoTM.ID_AZIENDA+" = '"+getUserPortalSession().getIdAzienda()+"' "
				+ "AND F."+FornitoreAcquistoTM.STATO+" = '"+DatiComuniEstesi.VALIDO+"' ";
		where += " AND F."+FornitoreAcquistoTM.ID_FORNITORE+" IN ('001682') ";
		ResultSet rs = null;
		CachedStatement cs = null;
		try {
			cs = new CachedStatement(where);
			rs = cs.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {

				JSONObject json = new JSONObject();
				json.put("Id", rs.getString(FornitoreAcquistoTM.ID_FORNITORE));
				json.put("Description", rs.getString(AnagraficoDiBasePrimroseTM.RAGIONE_SOCIALE));

				arr.put(json);
			}
			response.put("shippers", arr);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				rs.close();
				cs.free();
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return response;
	}

	public String getChiaveDocumentoDigitaleCondizioniVendita() {
		return ParametroPsn.getValoreParametroPsn("YPortaleECommerce", "SalesConditionPdfKey");
	}

	@SuppressWarnings("rawtypes")
	public JSONObject deliveryMethodsList() {
		JSONObject response = new JSONObject();
		String where = " "+ModalitaConsegnaTM.ID_AZIENDA+" = '"+getUserPortalSession().getIdAzienda()+"' AND "+ModalitaConsegnaTM.STATO+" = '"+DatiComuniEstesi.VALIDO+"' ";
		where += " AND "+ModalitaConsegnaTM.ID_MOD_CONSEGNA+" = '05' "; //PER ORA SOLO FRANCO CON ADDEBITO IN FATT.
		try {
			Vector list = ModalitaConsegna.retrieveList(ModalitaConsegna.class, where, "", false);
			Iterator iter = list.iterator();
			JSONArray arr = new JSONArray();
			while(iter.hasNext()) {
				ModalitaConsegna mod = (ModalitaConsegna) iter.next();

				if(!mod.getDescrizione().getDescrizione().equals(".")) {
					JSONObject json = new JSONObject();
					json.put("Id", mod.getIdModConsegna());
					json.put("Description", mod.getDescrizione().getDescrizione());

					arr.put(json);
				}
			}
			response.put("deliveryMethods", arr);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	public JSONObject shipmentMethodsList() {
		JSONObject response = new JSONObject();
		String where = " "+ModalitaSpedizioneTM.ID_AZIENDA+" = '"+getUserPortalSession().getIdAzienda()+"' AND "+ModalitaSpedizioneTM.STATO+" = '"+DatiComuniEstesi.VALIDO+"' ";
		where += " AND "+ModalitaSpedizioneTM.ID_MOD_SPEDIZIONE+" IN('VE','DE') "; //PER ORA SOLO VETTORE
		try {
			Vector list = ModalitaSpedizione.retrieveList(ModalitaSpedizione.class, where, "", false);
			Iterator iter = list.iterator();
			JSONArray arr = new JSONArray();
			while(iter.hasNext()) {
				ModalitaSpedizione mod = (ModalitaSpedizione) iter.next();

				if(!mod.getDescrizione().getDescrizione().equals(".")) {
					JSONObject json = new JSONObject();
					json.put("Id", mod.getIdModSpedizione());
					json.put("Description", mod.getDescrizione().getDescrizione());
					json.put("isVettoreRequired", mod.getIdModSpedizione().equals("VE") ? true : false);
					arr.put(json);
				}
			}
			response.put("shipmentMethods", arr);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return response;
	}

	public class ItemCarrello {

		protected String idProgressivo;

		protected String idArticolo;
		protected String idArticoloCatalogo;

		protected String quantita;

		protected String idCliente;

		protected String descrExtArticolo;

		protected String umDefVen;

		protected String key;

		protected String prezzo;

		protected String disponibilita;

		protected boolean articoloCritico = false;
		protected boolean disponibileAellebi = false;

		public boolean isArticoloCritico() {
			return articoloCritico;
		}

		public void setArticoloCritico(boolean isArticoloCritico) {
			this.articoloCritico = isArticoloCritico;
		}

		public boolean isDisponibileAellebi() {
			return disponibileAellebi;
		}

		public void setDisponibileAellebi(boolean disponibileAellebi) {
			this.disponibileAellebi = disponibileAellebi;
		}

		public String getIdProgressivo() {
			return idProgressivo;
		}

		public void setIdProgressivo(String idProgressivo) {
			this.idProgressivo = idProgressivo;
		}

		public String getIdArticolo() {
			return idArticolo;
		}

		public void setIdArticolo(String idArticolo) {
			this.idArticolo = idArticolo;
		}

		public String getIdArticoloCatalogo() {
			return idArticoloCatalogo;
		}

		public void setIdArticoloCatalogo(String idArticoloCatalogo) {
			this.idArticoloCatalogo = idArticoloCatalogo;
		}

		public String getQuantita() {
			return quantita;
		}

		public void setQuantita(String quantita) {
			this.quantita = quantita;
		}

		public String getIdCliente() {
			return idCliente;
		}

		public void setIdCliente(String idCliente) {
			this.idCliente = idCliente;
		}

		public String getDescrExtArticolo() {
			return descrExtArticolo;
		}

		public void setDescrExtArticolo(String descrExtArticolo) {
			this.descrExtArticolo = descrExtArticolo;
		}

		public String getUmDefVen() {
			return umDefVen;
		}

		public void setUmDefVen(String umDefVen) {
			this.umDefVen = umDefVen;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getPrezzo() {
			return prezzo;
		}

		public void setPrezzo(String prezzo) {
			this.prezzo = prezzo;
		}

		public String getDisponibilita() {
			return disponibilita;
		}

		public void setDisponibilita(String disponibilita) {
			this.disponibilita = disponibilita;
		}


	}
}
