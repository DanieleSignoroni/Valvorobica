package it.valvorobica.thip.base.portal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thera.thermfw.base.IniFile;
import com.thera.thermfw.persist.ConnectionDescriptor;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.KeyHelper;

import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.base.profilo.ws.AuthenticationManager;
import it.valvorobica.thip.base.portal.YUserPortalSession;

/**
 * <h1>Softre Solutions</h1>
 * @author Andrea Gatta	27/03/2023
 * <br><br>
 * 
 *	<b>71018 AGSOF3	27/03/2023</b>	<p>Questa è la servlet di passaggio per la validazione del login del portale,
 *								il login deve passare di qua per settare in header il token di validità 
 *								necessario ai ws per funzionare.</p>
 *	<b>71078 DSSOF3	12/05/2023</b>	<p>Se l'utente ha piu di un azienda memorizzo il dato.</p>
 *  <b>71162 DSSOF3	04/07/2023</b>	<p>Gestire il portale fornitore, settando chiave e id sull'userPortalSession</p>
 *  <b>71654 DSSOF3 06/10/2024</b>	<p>Introdurre gestione tokenUID revocando il token normale per questioni di validita'.<br></p>
 */

public class YValidatePortalLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processAction(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processAction(req,resp);
	}


	protected void processAction(HttpServletRequest req, HttpServletResponse resp) {
		String[] chiaveUser = KeyHelper.unpackObjectKey(req.getParameter("keyUser"));
		String idUtenteVideo = req.getParameter("idUtente");
		String company = chiaveUser[0];
		String user = chiaveUser[6];
		String pwd = "SOFTRE999"; //la password dell'utente deve sempre essere questa
		String token = retrieveToken(user, pwd, company);//in funzione dell'utente pth faccio una chiama al ws AM per farmi dare il token di accesso
		YUserPortalSession userPortal = new YUserPortalSession(chiaveUser[2],chiaveUser[3],chiaveUser[4],chiaveUser[5],idUtenteVideo,token, company);
		//parametrizziamo catalogo solo per aellebi intanto
		if(company.equals("001")) { 
			userPortal.isCatalogoAttivo = true;
		}
		//fine parametrizzazione
		userPortal.setIdFornitore(chiaveUser[chiaveUser.length-1]);
		boolean isMultipleEnv = (req.getParameter("MultipleEnv") != null && req.getParameter("MultipleEnv").equals("Y")) ? true : false; //71078
		userPortal.setMultipleEnv(isMultipleEnv); //71078
		String offerKey = req.getParameter("offerKey");
		if(offerKey != null) {
			userPortal.setSupplierOfferKey(offerKey);
		}
		try {
			String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath"); // prenderlo dall'ini classico
			String jspHome =  "/"+webAppPath+"/it/valvorobica/thip/base/portal/HomePortal.jsp";
			resp.setHeader("content-type", "application/json");
			
			//71654
			String tokenUID = ParametroPsn.getValoreParametroPsn("YUserPortalToken", "TokenUID");
			resp.setHeader("authorization", tokenUID);
			userPortal.setTokecUID(tokenUID);
			
			req.getSession().setAttribute("YUserPortal", userPortal);
			resp.sendRedirect(jspHome);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Faccio un login attraverso AM standard Panthera per farmi dare il token 
	 * che poi userò per fare le successive chiamate ai vari ws.
	 * Il token lo setto poi nell'header authorization
	 * @param user
	 * @param pwd
	 * @param company
	 * @return token di validità login con utente pth
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected String retrieveToken(String user, String pwd, String company) {
		String token = null;
		ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
		AuthenticationManager ws = new AuthenticationManager();
		ws.setUseAuthorization(false);
		ws.setUseLicence(false);
		ws.setConnectionDescriptor(cd);
		ws.setExpiredMinutes(new Long("60")); //aumentare durata token per quelle chiamate che usano authentication
		Map appParams = ws.getAppParams();
		appParams.put("verifyAuthorization", "on");
		appParams.put("user", user);
		appParams.put("pwd", pwd);
		appParams.put("company", company);
		ws.setAppParams(appParams);
		Map values = ws.send();
		ArrayList errors = (ArrayList) values.get("errors");
		if(errors != null && errors.isEmpty()) {//agsof3 aggiunto <> null
			token = (String) values.get("token");
		}
		return token;
	}


}