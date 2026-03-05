package it.valvorobica.thip.base.generale.ws;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.cbs.Message;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.ecommerce.Gwmso00f;
import it.thera.thip.base.ecommerce.Gwror00f;
import it.thera.thip.base.ecommerce.Gwtor00f;
import it.thera.thip.base.ecommerce.Gwtor00fTM;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.base.partner.IndirizzoPrimRose;
import it.thera.thip.vendite.generaleVE.ws.RicercaPrezzoEcomm;
import it.thera.thip.vendite.proposteEvasione.CreaMessaggioErrore;
import it.valvorobica.thip.base.portal.YCarrelloPortale;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 31/08/2023
 * <br><br>
 * <b>71392	DSSOF3	31/08/2023</b>	<p>Prima stesura.<br>
 * 										Check-out carrello.<br>
 * 										Passo dagli ordini ECommerce:
 * 										<ul>
 * 											<li>{@link Gwtor00f} --> testata</li>
 * 											<li>{@link Gwror00f} --> righe</li>
 * 										</ul>
 * 										Se la creazione di queste e' andata a buon fine, tolgo l'articolo dal carrello.
 * 									</p>
 * <b>71478	DSSOF3	22/03/2024</b>
 * <p>
 * Ereditare da {@link YPortalGenRequestJSON}.<br>
 * </p>
 * <b>71660	DSSOF3	10/16/2024</b>
 * <p>
 * Nuova gestione checkout.<br>
 * Con dati come (modalita consegna, spedizione, vettore).<br>
 * Indirizzo di destinazione.
 * </p>
 * <b>71805	DSSOF3	05/02/2025</b>
 * <p>
 * Sistemare recupero items.<br>
 * </p>
 */

public class YCheckOutCarrello extends YPortalGenRequestJSON {

	protected Gwmso00f flusso;

	public Gwmso00f getFlusso() {
		return flusso;
	}

	public void setFlusso(Gwmso00f flusso) {
		this.flusso = flusso;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		List errors = new ArrayList();
		JSONObject body = new JSONObject(toOutputJSON(getAppParams()));

		Gson gson = new Gson();
		Item[] items = gson.fromJson(this.getAppParams().get("items").toString(), Item[].class);
		if(items.length == 0) {
			errors.add("Non e' presente nessun articolo da ordinare, aggiungerne almeno uno prima al carrello!");
		}
		String errorQta = checkQuantita(items);
		if(errorQta != null) 
			errors.add(errorQta);

		if(errors.isEmpty()) {
			List errorsOrdEComm = generaOrdineECommerce(body, items);
			errors.addAll(errorsOrdEComm);
		}

		m.put("errors", errors);
		return m;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List generaOrdineECommerce(JSONObject body, Item[] items) {
		List errors = new ArrayList();

		ClienteVendita cliente = getUserPortalSession().getCliente();
		try {		
			String idFlusso = "";
			if(cliente.getIdCategContabileCliente().equals("ITA")) {
				idFlusso = "PortaleIT";
			}
			if(cliente.getIdCategContabileCliente().equals("EST")) {
				idFlusso = "PortaleExt";
			}
			if(cliente.getIdCategContabileCliente().equals("CEE")) {
				idFlusso = "PortaleCEE";
			}
			codificaFlusso(idFlusso);

			JSONObject formData = new JSONObject(body.get("formData").toString());

			String idModalitaSpedizione = formData.getString("IdModalitaSpedizione");
			String idVettore1 = formData.has("IdVettore1") ? formData.getString("IdVettore1") : null;
			String idModalitaConsegna = formData.getString("IdModalitaConsegna");

			String indirizzo = formData.getString("Indirizzo");
			String CAP = formData.getString("CAP");
			String localita = formData.getString("Localita");
			String provincia = formData.getString("Provincia");

			Gwtor00f testata = generaTestataOrdineECommerce(cliente);

			testata.setTosped(idModalitaSpedizione);
			if((idVettore1 != null && !idVettore1.equals("X")))
				testata.setTovet1(idVettore1);
			testata.setTocons(idModalitaConsegna);

			String note = formData.getString("note");
			String numeroRiferimentoCliente = formData.getString("vsNr");

			String email = formData.getString("Email");

			testata.setTocomm(note);
			testata.setTovsri(numeroRiferimentoCliente);

			IndirizzoPrimRose indirizzoSpedizione = cliente.getIndirizzoSpedizione() != null ? cliente.getIndirizzoSpedizione().getDatiIndirizzo() : null;

			if (indirizzoSpedizione != null && indirizzoSpedizione.getIndirizzo().equals(indirizzo)
					&& indirizzoSpedizione.getCAP().equals(CAP)
					&& indirizzoSpedizione.getLocalita().equals(localita)) {

				testata.setToinsp(indirizzoSpedizione.getIndirizzo());
				testata.setTocasp(indirizzoSpedizione.getCAP());
				testata.setTolosp(indirizzoSpedizione.getLocalita());
				testata.setTocnaz(cliente.getIdNazione());
				testata.setToprsp(indirizzoSpedizione.getIdProvincia());
			} else {
				testata.setToinsp(indirizzo);
				testata.setTocasp(CAP);
				testata.setTolosp(localita);
				testata.setTocnaz(cliente.getIdNazione());
				testata.setToprsp(provincia.toUpperCase());
			}

			testata.setTorasp(cliente.getRagioneSociale());

			testata.setTolisw(cliente.getIdListino());

			testata.setTomail(email);

			int rc = 0;
			rc = testata.save();
			if (rc > 0) {
				if (items != null) {
					for (int i = 0; i < items.length; i++) {
						Item itemCheckOut = items[i];

						YCarrelloPortale item = (YCarrelloPortale) YCarrelloPortale.elementWithKey(
								YCarrelloPortale.class, itemCheckOut.getKey(), PersistentObject.NO_LOCK);

						if(item == null) {
							errors.add("Non e' stata trovata la riga nel carrello, ricaricare la pagina del carrrello");
							return errors;
						}

						Gwror00f riga = generaRigaOrdineECommerce(itemCheckOut, item, testata);
						riga.setRocarr(new BigDecimal(i)); 

						rc = riga.save();
						if (rc < 0) {
							errors.add("Impossibile salvare la riga ordine E-Commerce" + 
									CreaMessaggioErrore.daRcAErrorMessage(rc, (SQLException) riga.getException()));
							break;
						} else {
							rc = item.delete();
							if (rc < 0) { 
								errors.add("Impossibile cancellare la riga dal carrello" + 
										CreaMessaggioErrore.daRcAErrorMessage(rc, (SQLException) riga.getException()));
								break;
							}
						}
					}
				}

				if (rc > 0) {
					ConnectionManager.commit();
				} else {
					ConnectionManager.rollback();
				}
			} else {
				ConnectionManager.rollback();
			}

			if(errors.isEmpty()) {
				//Inviare la email di conferma ricezione ordine, questa avvisa il cliente che una volta revisionata
				//Gli verra' mandata la conferma pdf da firmare via mail da parte di valvorobica
				String text = "";
				String messageModelID = ParametroPsn.getValoreParametroPsn("YPortaleECommerce", "ModelMessageEmailCheckOut");
				Message msg = YSendMailSalesManager.buildMessage(new String[] {}, new String[] {}, text, messageModelID);
				msg.setEmailDest(email); //necessario
				String EmailSalesManagerSender = ParametroPsn.getValoreParametroPsn("YPortaleECommerce", "EmailSalesManagerSender");
				Vector mailNotSend = YSendMailSalesManager.sendMail(msg,EmailSalesManagerSender , email);
				if(mailNotSend.size() > 0) {
					errors.add("Impossibile inviare la mail verso : "+mailNotSend.get(0));
				}
			}

		}catch (Throwable t) {
			getErrors().add(t.getMessage());
			t.printStackTrace(Trace.excStream);
		}
		return errors;
	}

	protected void codificaFlusso(String idFlusso) throws Exception {
		try {
			flusso = (Gwmso00f)
					Gwmso00f.elementWithKey(Gwmso00f.class, 
							KeyHelper.buildObjectKey(new String[] {
									this.getCompany(),
									idFlusso
							}), PersistentObject.NO_LOCK);
			if(flusso == null) {
				throw new Exception("Errore nella codifica del flusso, contattare l'azienda");
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Gwror00f generaRigaOrdineECommerce(Item itemCheckOut, YCarrelloPortale item, Gwtor00f testata) throws SQLException {
		Articolo art = (Articolo)
				Articolo.elementWithKey(Articolo.class, KeyHelper.buildObjectKey(new String[] {
						this.getCompany(),
						item.getRArticolo()
				}), PersistentObject.NO_LOCK);
		Gwror00f riga = (Gwror00f) Factory.createObject(Gwror00f.class);
		riga.setTestata(testata);
		riga.setRoidaz(this.getCompany());
		riga.setRocarp(testata.getTocarp());
		riga.setRocard(testata.getTocard());
		riga.setIdArticolo(item.getRArticolo());
		riga.setRofoor(testata.getTofoor());
		riga.setRodear(""); //desc art
		riga.setRocafi(this.getFlusso().getCodCauVenRig()); //causale riga 
		riga.setRostat('V'); //stato
		riga.setRocumm(""); //um rif
		riga.setRocumv(""); // um ven
		riga.setRoumuv(new BigDecimal(1));
		riga.setRoopmv('M');
		riga.setRopzum('V');
		riga.setRotpre('1');
		riga.setRotmov(false);
		riga.setRoasfi(""); // assog iva
		riga.setRotpev('P');
		riga.setStatoErrImp('0');
		riga.setRomagw("001");
		BigDecimal qta = new BigDecimal(itemCheckOut.getQuantita());
		riga.setRoqtor(qta);
		riga.setRoqt2o(qta);
		riga.setIdUMPrm(art.getIdUMPrmMag());
		riga.setIdUMRif(art.getIdUMRiferimento());
		riga.setRoutcr(this.getUserId());
		riga.setRoutag(this.getUserId());
		Map parametriRcPrz = new HashMap();
		parametriRcPrz.put("codArticolo", item.getRArticolo());
		parametriRcPrz.put("codCliente", getUserPortalSession().getCliente().getIdCliente());
		parametriRcPrz.put("tipoUMVendita", "V");
		parametriRcPrz.put("company", this.getCompany());
		parametriRcPrz.put("qtaRichiesta", itemCheckOut.getQuantita());
		parametriRcPrz.put("codListino", "VEN");
		RicercaPrezzoEcomm rcPrz = (RicercaPrezzoEcomm) Factory.createObject(RicercaPrezzoEcomm.class); //ricerco prezzo tramite ws std
		rcPrz.setUseAuthentication(false);
		rcPrz.setUseAuthorization(false);
		rcPrz.setUseLicence(false);
		rcPrz.setConnectionDescriptor(this.getConnectionDescriptor());
		rcPrz.setAppParams(parametriRcPrz);
		rcPrz.setQtaVenditaRich(qta);
		Map values = rcPrz.send();
		BigDecimal prezzo = BigDecimal.ZERO;
		if(values.get("prezzo") != null) {
			prezzo = (BigDecimal) values.get("prezzo");
		}
		BigDecimal scontoArt1 = values.get("prcScontoArticolo1") != null ? (BigDecimal) values.get("prcScontoArticolo1") : BigDecimal.ZERO;
		BigDecimal scontoArt2 = values.get("prcScontoArticolo2") != null ? (BigDecimal) values.get("prcScontoArticolo2") : BigDecimal.ZERO;
		BigDecimal maggiorazione = values.get("prcMaggiorazione") != null ? (BigDecimal) values.get("prcMaggiorazione") : BigDecimal.ZERO;
		riga.setRosccm(maggiorazione);
		riga.setRoscar(scontoArt1);
		riga.setScontoArt2(scontoArt2);
		riga.setRoprez(prezzo);
		riga.setRotpre('0');
		riga.setRolisw(getUserPortalSession().getCliente().getIdListino());
		return riga;
	}

	protected Gwtor00f generaTestataOrdineECommerce(ClienteVendita cliente) {
		Gwtor00f testata = (Gwtor00f) Factory.createObject(Gwtor00f.class);
		testata.setToidaz(this.getCompany());
		testata.setTocarp(String.valueOf(getNextIntGwTor()));
		testata.setTocard(TimeUtils.getCurrentDate());
		testata.setCliente(cliente);
		testata.setIdAnagrafico(cliente.getIdAnagrafico());
		testata.setToclir(true); //e' un cliente codificato
		testata.setToutcr(this.getUserId());
		testata.setToutag(this.getUserId());
		testata.setTodaor(TimeUtils.getCurrentDate());
		testata.setTostdo('1');
		testata.setTofoor(flusso.getIdFonteOrigine());
		testata.setTocafi(this.getFlusso().getCodCauVenTes());
		return testata;
	}

	protected Integer getNextIntGwTor() {
		ResultSet rs = null;
		CachedStatement cs = null;
		try {	
			String stmt = " SELECT COUNT(*)+1 AS MAX FROM "+Gwtor00fTM.TABLE_NAME+" ";
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		}catch (SQLException t) {
			t.printStackTrace(Trace.excStream);
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return null;

	}

	public static String checkQuantita(Item[] items) {
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if(item.getQuantita() == null
					|| item.getQuantita().isEmpty()
					|| item.getQuantita().equals("")
					|| item.getQuantita().equals(" ")) {
				return "Sistemare le quantita'";
			}else {
				try {
					new BigDecimal(item.getQuantita());
				}catch (NumberFormatException e) {
					e.printStackTrace(Trace.excStream);				
					return "Formato quantita errato";
				}
			}
		}
		return null;
	}

	public class Item{

		protected int id;

		protected String key;

		protected String quantita;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getQuantita() {
			return quantita;
		}

		public void setQuantita(String quantita) {
			this.quantita = quantita;
		}

	}
}
