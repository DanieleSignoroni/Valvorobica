package it.valvorobica.thip.base.generale.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionDescriptor;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.security.Security;
import com.thera.thermfw.web.SessionEnvironment;

import it.thera.thip.acquisti.offerteFornitore.OffertaFornitoreTM;
import it.thera.thip.ws.GenericQuery;
import it.thera.thip.ws.servlet.WebServiceLauncher;
import it.valvorobica.thip.base.generale.ws.utils.YUtilsPortal;
import it.valvorobica.thip.base.portal.YAziendeUserPortal;
import it.valvorobica.thip.base.portal.YAziendeUserPortalTM;
import it.valvorobica.thip.base.portal.YUserPortalSession;

/**
 * <h1>Softre Solutions</h1>
 * @author Andrea Gatta 25/03/2023
 * <br><br>
 * <b>71018	AGSOF3	25/03/2023</b>	<p>Servlet che gestisce l'autenticazione personalizzata del portale</p>
 * <b>71162	DSSOF3	04/07/2023</b>	<p>Gestire parametro offerKey in {GET} per far collegare i fornitori e fargli compilare l'offerta di acquisto.<br>
 * 										L'utente e' codificato in {@link YAziendeUserPortal} e ha {@link YAziendeUserPortal#getTipoUser()} == {@value YUserPortalSession#FORNITORE}<br>
 * 										Qui voglio comunque controllare che l'offerta con la chiave passata in {GET} abbia come {@value OffertaFornitoreTM#R_FORNITORE} il valore
 * 										presente nella colonna {@link YAziendeUserPortalTM#R_FORNITORE}, se questo non fosse congruo do' errore.<br>
 * 										Se si sta collegando un fornitore senza passare l'offerat in {GET} do' msg di errore.
 * 									</p>
 * 									 
 *
 */

public class YPortalAuthentication extends WebServiceLauncher {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map values = new LinkedHashMap();
		ArrayList<String> errori = new ArrayList<String>();
		boolean isopen = false;
		Object[] info = SessionEnvironment.getDBInfoFromIniFile();
		String dbName = (String)info[0];
		String jspPage = null;
		String supplierOfferKey = req.getParameter("offerKey");
		try {		
			if(!Security.isCurrentDatabaseSetted()) {
				Security.setCurrentDatabase(dbName, null);
			}
			Security.openDefaultConnection();
			isopen = true;
			ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
			YAuthenticationManager ws = new YAuthenticationManager();
			//ws.setUseAuthentication(false);
			ws.setUseAuthorization(false);
			ws.setUseLicence(false);
			ws.setConnectionDescriptor(cd);
			//setto i parametri di login di un utente generico che DEVE esistere per fare le chiamate ai ws
			String user = YUtilsPortal.GENERIC_USER_PORTAL;
			String pwd = YUtilsPortal.GENERIC_PWD_PORTAL;
			String company = YUtilsPortal.GENERIC_COMPANY_PORTAL;
			Map appParams = ws.getAppParams();
			appParams.put("verifyAuthorization", "on");
			appParams.put("user", user); //parametri login standard necessari per Panthera
			appParams.put("pwd", pwd);//parametri login standard necessari per Panthera
			appParams.put("company", company);//parametri login standard necessari per Panthera
			appParams.put("userPortal", req.getParameter("user")); //parametri login pers. (inseriti a video in form login)
			String pwdPortal = YUtilsPortal.criptaPassword(req.getParameter("pwd"));
			appParams.put("pwdPortal", pwdPortal);  //parametri login pers. (inseriti a video in form login)
			ws.setAppParams(appParams);
			Map resAuth = ws.send();
			ArrayList errors = (ArrayList) resAuth.get("errors");
			if(errors != null && errors.isEmpty()) {
				boolean skipAll = false;
				ArrayList records = (ArrayList) resAuth.get("records"); //records delle aziende dell'utente
				records = formattaRecords(records);
				if(supplierOfferKey == null || supplierOfferKey.equals("null")) {
//					if(records.size() == 1 && isUnicoRecordFornitore(records)) {
//						skipAll = true;
//					}
//					records = escludiRecordFornitore(records);
				}
				if(!skipAll) {
					if(records.size() != 0) {
						String idUtente = req.getParameter("user");
						int numRecords = records.size();
						if(supplierOfferKey == null || supplierOfferKey.equals("null")) {
							switch (numRecords) {
							case 0:
								jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore=loginErrata";
								errori.add("Utente o password non corretti!");
								values.put("errors", errori);
								break;
							case 1://se ho un solo record mando direttamente il flusso in homepage
								jspPage = "/servlet/it.valvorobica.thip.base.portal.web.YValidatePortalLogin";
								jspPage += "?keyUser="+records.get(0)+"&idUtente="+idUtente;
								//SERLVET
								break;
							default://se > 1, devo permettere all'utente di scegliere l'azienda
								//jsp navigazione aziende --> serlvet
								req.setAttribute("valori", records);
								jspPage = "/it/valvorobica/thip/base/portal/YSceltaAziendePortal.jsp?idUtente="+idUtente;
								break;
							}
						}
						if(supplierOfferKey != null && !supplierOfferKey.equals("null")) {
							String chiaveUtentePerOfferta = getRecordInBaseAzienda(supplierOfferKey,records);
							String[] chiaveUtentePerOffertaSpacchettata = KeyHelper.unpackObjectKey(chiaveUtentePerOfferta);
							try {
								String[] unpckOffKey = KeyHelper.unpackObjectKey(supplierOfferKey);
								if(chiaveUtentePerOffertaSpacchettata[2].equals(YUserPortalSession.FORNITORE)) {
									String token = (String) resAuth.get("token");
									String idFornitore = chiaveUtentePerOffertaSpacchettata[chiaveUtentePerOffertaSpacchettata.length-1];
									String stmt = "SELECT "
											+ "CASE WHEN "+OffertaFornitoreTM.R_FORNITORE+" = '"+idFornitore+"' THEN '1' ELSE '-1' END "
											+ "FROM "+OffertaFornitoreTM.TABLE_NAME+" "
											+ "WHERE "+OffertaFornitoreTM.ID_AZIENDA+" = '"+unpckOffKey[0]+"' "
											+ "AND "+OffertaFornitoreTM.ID_ANNO_OFF+" = '"+unpckOffKey[1]+"' "
											+ "AND "+OffertaFornitoreTM.ID_NUMERO_OFF+" = '"+unpckOffKey[2]+"' ";
									GenericQuery gsq = (GenericQuery) Factory.createObject(GenericQuery.class);
									gsq.setUseAuthorization(false);
									gsq.setUseLicence(false);
									gsq.setConnectionDescriptor(cd);
									Map params = gsq.getAppParams();
									params.put("token", token);
									params.put("company", company);
									params.put("query", stmt);
									Map ris = gsq.send();
									records = (ArrayList) ris.get("records");
									if(records.size() > 0) {
										ArrayList<String> valori = (ArrayList<String>) records.get(0);
										Integer ris_code = Integer.valueOf(valori.get(0).trim());
										if(ris_code < 0) {
											errori.add("The supplier on the offer is different than the supplier on the user!");
											values.put("errors", errori);
											String errore = "The supplier on the offer is different than the supplier on the user!";
											jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore="+errore;
										}else {
											jspPage = "/servlet/it.valvorobica.thip.base.portal.web.YValidatePortalLogin";
											jspPage += "?keyUser="+chiaveUtentePerOfferta+"&idUtente="+idUtente;
											if(supplierOfferKey != null)
												jspPage += "&offerKey="+supplierOfferKey;
										}
									}else {
										String errore = "The offer doesn't exists";
										jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore="+errore;
									}
								}else {
									String errore = "Pulire l'url";
									jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore="+errore;
								}
							}catch (ArrayIndexOutOfBoundsException e) {
								e.printStackTrace();
								String errore = "Generic error";
								jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore="+errore;
							}
						}
					}else {
						String errore = "Utente o password non corretti!";
						jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore="+errore;
					}
				}else {
					String errore = "You are a supplier, you must provide an offer key in the URL!";
					jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore="+errore;
				}
			}else {
				String errore = (String) ((LinkedHashMap) errors.get(0)).get("0");
				jspPage = "/it/valvorobica/thip/base/portal/LoginErrata.jsp?errore="+errore;//Utente generico non valido PORTALUSER";
			}
		}catch(Throwable t) {
			t.printStackTrace(Trace.excStream);
			t.printStackTrace();
		}finally {
			if (isopen) {
				Security.closeDefaultConnection();
			}
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(jspPage);
		rd.forward(req, resp);
	}

	protected boolean isUnicoRecordFornitore(ArrayList<String> records) {
		for (String chiave :  records) {
			String[] chiaveSpacchettata = KeyHelper.unpackObjectKey(chiave);
			if(chiaveSpacchettata[2].equals(YUserPortalSession.FORNITORE)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	protected String getRecordInBaseAzienda(String supplierOfferKey, ArrayList records) {
		String[] unpackedOffKey = KeyHelper.unpackObjectKey(supplierOfferKey);
		String azi = unpackedOffKey[0];
		for (int i = 0; i < records.size(); i++) {
			String c = (String) records.get(0);
			String[] decompattata = KeyHelper.unpackObjectKey(c);
			if(decompattata[0].equals(azi)) {
				return c;
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ArrayList escludiRecordFornitore(ArrayList records) {
		ArrayList ret = new ArrayList<String>();
		for (int i = 0; i < records.size(); i++) {
			String rc = (String) records.get(i);
			String[] unpacked = KeyHelper.unpackObjectKey(rc);
			if(!unpacked[2].equals(YUserPortalSession.FORNITORE)) {
				ret.add(rc);
			}
		}
		return ret;
	}

	/**
	 * 	formatto i valori all'interno di records in stringhe stile chiavi pth trimmate
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected ArrayList formattaRecords(ArrayList records) {
		ArrayList<String> newRecords = new ArrayList<String>();
		for (int i = 0; i < records.size(); i++) {
			ArrayList<String> valori = (ArrayList<String>) records.get(i);
			String idAzienda = valori.get(1).trim();
			String desc = valori.get(valori.size()-1).trim();
			String tipoUser = valori.get(2).trim();
			String agente = valori.get(3).trim();
			String cliente = valori.get(4).trim();
			String dipendente = valori.get(5).trim();
			String utentePTH = valori.get(6).trim();
			String idFornitore = valori.get(7).trim();
			String key = KeyHelper.buildObjectKey(new String[]{
					idAzienda,
					desc,
					tipoUser,
					agente,
					cliente,
					dipendente,
					utentePTH,
					idFornitore
			});
			newRecords.add(key);		
		}
		return newRecords;
	}

}