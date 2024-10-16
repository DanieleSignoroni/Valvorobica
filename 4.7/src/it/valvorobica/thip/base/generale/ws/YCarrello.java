package it.valvorobica.thip.base.generale.ws;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionDescriptor;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Security;
import com.thera.thermfw.web.SessionEnvironment;

import it.thera.thip.base.articolo.ArticoloTM;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.cliente.ModalitaConsegna;
import it.thera.thip.base.cliente.ModalitaConsegnaTM;
import it.thera.thip.base.cliente.ModalitaSpedizione;
import it.thera.thip.base.cliente.ModalitaSpedizioneTM;
import it.thera.thip.base.fornitore.FornitoreAcquistoTM;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.base.partner.AnagraficoDiBasePrimroseTM;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.vendite.generaleVE.ws.RicercaPrezzoEcomm;
import it.thera.thip.ws.GenericQuery;
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
 */

public class YCarrello extends YPortalGenRequestJSON {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		BigDecimal totale = BigDecimal.ZERO;
		ArrayList<YCarrello.ItemCarrello> items = new ArrayList<YCarrello.ItemCarrello>();
		String select = "SELECT "
				+ " C."+YCarrelloPortaleTM.PROGRESSIVO+", "
				+ " C."+YCarrelloPortaleTM.R_ARTICOLO+", "
				+ " A."+ArticoloTM.DESC_ESTESA+", "
				+ " UPPER(A.R_UM_PRM_VEN) , "
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
		Map values = null;
		boolean isopen = false;		
		Object[] info = SessionEnvironment.getDBInfoFromIniFile();
		String dbName = (String)info[0];
		try {		
			if(!Security.isCurrentDatabaseSetted()) {
				Security.setCurrentDatabase(dbName, null);
			}
			Security.openDefaultConnection();
			isopen = true;
			ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
			GenericQuery gq = new GenericQuery();
			gq.getAppParams().put("query", select+where);
			gq.setUseAuthentication(false);
			gq.setUseAuthorization(false);
			gq.setUseLicence(false);
			gq.setConnectionDescriptor(cd);
			values = gq.send();		
			ArrayList records = (ArrayList) values.get("records");
			for (int i = 0; i < records.size(); i++) {
				ArrayList valuesRecords = (ArrayList) records.get(i);
				ItemCarrello item = new ItemCarrello();
				String idProgressivo = valuesRecords.get(0).toString().trim();
				String idArticolo = valuesRecords.get(1).toString().trim();
				String descExtArticolo =  valuesRecords.get(2).toString().trim();
				String umDefVen = valuesRecords.get(3).toString().trim();
				String quantita = valuesRecords.get(4).toString().trim();
				BigDecimal qta = new BigDecimal(quantita);
				String idCliente = valuesRecords.get(5).toString().trim();
				item.setIdProgressivo(idProgressivo);
				item.setIdArticolo(idArticolo);
				item.setIdCliente(idCliente);
				item.setQuantita(qta.setScale(2,RoundingMode.DOWN).toString());
				item.setDescrExtArticolo(descExtArticolo);
				item.setUmDefVen(umDefVen);
				items.add(item);
				String key = KeyHelper.buildObjectKey(new String[]{
						getUserPortalSession().getIdAzienda(),
						getUserPortalSession().getIdUtente(),
						idProgressivo
				});
				BigDecimal disp = new BigDecimal(valuesRecords.get(8).toString().trim());
				item.setDisponibilita(disp.setScale(2,RoundingMode.DOWN).toString());
				item.setKey(key);
				Map parametriRcPrz = new HashMap();
				parametriRcPrz.put("codArticolo", item.getIdArticolo());
				parametriRcPrz.put("codCliente", item.getIdCliente().trim());
				parametriRcPrz.put("tipoUMVendita", "V");
				parametriRcPrz.put("company", getUserPortalSession().getIdAzienda());
				parametriRcPrz.put("qtaRichiesta", item.getQuantita());
				parametriRcPrz.put("codListino", "VEN");
				RicercaPrezzoEcomm rcPrz = (RicercaPrezzoEcomm) Factory.createObject(RicercaPrezzoEcomm.class); //ricerco prezzo tramite ws std
				rcPrz.setUseAuthentication(false);
				rcPrz.setUseAuthorization(false);
				rcPrz.setUseLicence(false);
				rcPrz.setConnectionDescriptor(cd);
				rcPrz.setAppParams(parametriRcPrz);
				rcPrz.setQtaVenditaRich(new BigDecimal(item.getQuantita()));
				values = rcPrz.send();
				BigDecimal prezzo = BigDecimal.ZERO;
				if(values.get("prezzo") != null) {
					prezzo = (BigDecimal) values.get("prezzo");
				}
				totale = totale.add(prezzo.multiply(qta));
				item.setPrezzo(prezzo.setScale(2, RoundingMode.DOWN).toString());
			}
		}
		catch (Throwable t) {
			t.printStackTrace(Trace.excStream);
		}
		finally {
			if (isopen) {
				Security.closeDefaultConnection();
			}
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

		try {
			ClienteVendita cliente = user.getCliente();
			if(cliente == null) {
				cliente = (ClienteVendita) ClienteVendita.elementWithKey(ClienteVendita.class,
						KeyHelper.buildObjectKey(new String[] {getUserPortalSession().getIdAzienda(),getUserPortalSession().getIdCliente()}), PersistentObject.NO_LOCK);
				user.setCliente(cliente);
			}

			JSONObject shipmentMethod = user.getShipmentDefaultMethod();

			if(cliente.getModalitaSpedizione() != null && shipmentMethod == null) {
				shipmentMethod = new JSONObject();
				ModalitaSpedizione mod = (ModalitaSpedizione) cliente.getModalitaSpedizione();
				if(!mod.getDescrizione().getDescrizione().equals(".")) {
					shipmentMethod.put("Id", mod.getIdModSpedizione());
					shipmentMethod.put("Description", mod.getDescrizione().getDescrizione());
				}
				user.setShipmentDefaultMethod(shipmentMethod);
			}

			m.put("shipmentDefaultMethod", shipmentMethod);

			JSONObject defaultShipper = user.getDefaultShipper();

			if(cliente.getVettore1() != null && defaultShipper == null) {
				defaultShipper = new JSONObject();
				defaultShipper.put("Id", cliente.getVettore1().getIdFornitore());
				defaultShipper.put("Description", cliente.getVettore1().getRagioneSociale());
			}

			m.put("deafaultShipper", defaultShipper);

			//			JSONObject deafultDeliveryMethod = user.getDefaultDeliveryMethod();
			//			
			//			if(cliente.getModalitaConsegna() != null && deafultDeliveryMethod == null) {
			//				deafultDeliveryMethod = new JSONObject();
			//				
			//				deafultDeliveryMethod.put("Id", cliente.getModalitaConsegna().getIdModConsegna());
			//				deafultDeliveryMethod.put("Description", cliente.getModalitaConsegna().getDescrizione());
			//			}

			//m.put("deafultDeliveryMethod", deafultDeliveryMethod);
			
		JSONObject datiDestinazione = new JSONObject();
		
		if(user.getCliente().getIndirizzoSpedizione() != null) {
			
			datiDestinazione.put("Indirizzo", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getIndirizzo());
			datiDestinazione.put("CAP", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getCAP());
			datiDestinazione.put("Localita", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getLocalita());
			datiDestinazione.put("Provincia", user.getCliente().getIndirizzoSpedizione().getDatiIndirizzo().getIdProvincia());
		}
		
		m.put("shipmentData", datiDestinazione);

		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return m;
	}

	protected JSONObject shippers() {
		JSONObject response = new JSONObject();
		String where = " SELECT F."+FornitoreAcquistoTM.ID_FORNITORE+",A."+AnagraficoDiBasePrimroseTM.RAGIONE_SOCIALE+" FROM "+FornitoreAcquistoTM.TABLE_NAME+" F "
				+ "INNER JOIN "+AnagraficoDiBasePrimroseTM.TABLE_NAME+" A "
				+ " ON F."+FornitoreAcquistoTM.R_ANAGRAFICO+" = A."+AnagraficoDiBasePrimroseTM.ID_ANAGRAFICO+" "
				+ "WHERE F."+FornitoreAcquistoTM.ID_AZIENDA+" = '"+getUserPortalSession().getIdAzienda()+"' "
				+ "AND F."+FornitoreAcquistoTM.STATO+" = '"+DatiComuniEstesi.VALIDO+"' ";
		where += " AND F."+FornitoreAcquistoTM.ID_FORNITORE+" IN ('001682','001846') ";
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
		where += " AND "+ModalitaSpedizioneTM.ID_MOD_SPEDIZIONE+" = 'VE' "; //PER ORA SOLO VETTORE
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

		protected String quantita;

		protected String idCliente;

		protected String descrExtArticolo;

		protected String umDefVen;

		protected String key;

		protected String prezzo;

		protected String disponibilita;

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
