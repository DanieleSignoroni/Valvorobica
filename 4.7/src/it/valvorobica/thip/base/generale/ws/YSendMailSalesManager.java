package it.valvorobica.thip.base.generale.ws;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.cbs.Message;
import com.thera.thermfw.cbs.MessageReplyType;
import com.thera.thermfw.cbs.MessageTmpl;
import com.thera.thermfw.persist.ErrorCodes;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Security;

import it.thera.thip.base.generale.ParametroPsn;

/**
 * <h1>Softre Solutions</h1>
 * <h2>Portale E-Commerce</h2>
 * Web Service che permette l'invio della mail al commerciale.<br>
 * Questo serve per avvisare il commerciale che il cliente ha richiesto della merce che non ha disponibilita'.<br>
 * <br>
 * @author Daniele Signoroni 07/10/2024
 * <br><br>
 * <b>71XXX	DSSOF3	07/10/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YSendMailSalesManager extends YPortalGenRequestJSON {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		Map response = super.execute(m);

		List errors = new ArrayList();

		String nome = getAppParam("nome") != null ? getAppParam("nome") : "";
		String cognome = getAppParam("cognome") != null ? getAppParam("cognome") : "";
		String email= getAppParam("email") != null ? getAppParam("email") : "";
		String note = getAppParam("note");

		String idArticolo = getAppParam("productId") != null ? getAppParam("productId") : "";

		if(nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || idArticolo.isEmpty()) {
			errors.add("Non tutti i campi sono stati compilati");
		}else {

			String EmailSalesManagerReceiver = ParametroPsn.getValoreParametroPsn("YPortaleECommerce", "EmailSalesManagerReceiver");
			String EmailSalesManagerSender = ParametroPsn.getValoreParametroPsn("YPortaleECommerce", "EmailSalesManagerSender");
			Message messaggio = (Message) Factory.createObject(Message.class);
			messaggio.setSender(EmailSalesManagerSender);
			messaggio.setEmailDest(EmailSalesManagerReceiver);
			
			messaggio.setText("");
			try {
				int rc = 0;
				if (messaggio != null) {
					if ( (rc = messaggio.save()) >= ErrorCodes.OK) {
						rc = messaggio.sendMail();
					} else {
						Trace.excStream.println("Fallito il save del messaggio: " + messaggio.getSubject());
					}
				}
			}catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}

		response.put("errors", errors);

		return response;
	}

}
