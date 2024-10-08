package it.valvorobica.thip.base.generale.ws;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.cbs.EmailAccount;
import com.thera.thermfw.cbs.Mail;
import com.thera.thermfw.cbs.Message;
import com.thera.thermfw.cbs.MessageReplyType;
import com.thera.thermfw.cbs.MessageTextType;
import com.thera.thermfw.cbs.MessageTmpl;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Security;
import com.thera.thermfw.ssd.SSDConfiguration;

import it.thera.thip.base.articolo.Articolo;
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
			String modelMessageID = ParametroPsn.getValoreParametroPsn("YPortaleECommerce", "ModelMessageIdEmailSM");
			EmailSalesManagerSender = "info@portale.it";
			EmailSalesManagerReceiver = "daniele.signoroni@smeup.com";
			try {

				Articolo articolo = (Articolo) 
						Articolo.elementWithKey(Articolo.class, KeyHelper.buildObjectKey(new String[] {
								getUserPortalSession().getIdAzienda(),
								idArticolo
						}), PersistentObject.NO_LOCK);

				Message messaggio = buildMessage(
						new String[] {articolo.getIdArticolo()},
						new String[] {cognome,nome,idArticolo,note,email},
						EmailSalesManagerReceiver,
						modelMessageID);

				messaggio.getSenderObj().setEmailAddress(EmailSalesManagerSender);
				messaggio.setEmailDest(EmailSalesManagerReceiver);

				if (messaggio != null) {
					messaggio.save();
					Vector mailNotSend = sendMail(messaggio,EmailSalesManagerSender,EmailSalesManagerReceiver);
					if(mailNotSend.size() > 0) {
						errors.add("Impossibile inviare la mail verso : "+mailNotSend.get(0));
					}
				}
			}catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}

		response.put("errors", errors);

		return response;
	}

	public Message buildMessage (String[] textParameters,String[] subjectParameters, String msg, String msgModello) throws SQLException {
		Message m = null;
		MessageTmpl mt = MessageTmpl.elementWithKey(msgModello, PersistentObject.NO_LOCK);
		if (mt != null) {
			mt.setSubject(msgModello);
			mt.setText(msg);
			mt.setParams(textParameters, subjectParameters);
			mt.setReplyType(MessageReplyType.NO_REPLY);
			mt.setMsgBreak(false);
			m = mt.createMessage();
			if (m != null) {
				m.setSender(Security.getCurrentUser().getId());
			}
		}

		return m;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector sendMail(Message m, String from,String to) throws SQLException {
		int ret = 0;
		File[] tmpFile = null;
		Vector mailSend = new Vector();
		Vector mailNotSend = new Vector();

		Mail mail=null;

		SSDConfiguration ssdConf = (SSDConfiguration) SSDConfiguration.elementWithKey(SSDConfiguration.class, "0", PersistentObject.NO_LOCK);

		mail = (Mail) Factory.createObject(Mail.class);

		mail.setAuthType(EmailAccount.BASIC_AUTH);
		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(m.getSubject());
		mail.setText(m.prepareTextMail());
		mail.setDebug(m.getDebug());
		mail.setHTML(m.getTextType() == MessageTextType.HTML_TEXT_TYPE); 
		if (m.getAttachmentColl().size() > 0) {
			tmpFile = new File[m.getAttachmentColl().size()];
			mail.setAttachment(m.prepareAttachmentMail());
		}

		mail.setSmtp(ssdConf.getSMTPServer());
		mail.setSmtpPort(ssdConf.getSMTPPort());
		mail.setSmtpSsl(ssdConf.isSMTPSsl());
		mail.setSmtpTls(ssdConf.isStartTls());
		//mail.setAuthenticator(m.getBasicAuthenticator());

		Iterator iter = m.prepareToMailList().iterator();
		while (iter.hasNext()) {
			String tmpTo = (String) iter.next();
			if (m.isExplodeDestList())
				mail.setToCC(tmpTo);
			else
				mail.setTo(tmpTo);
			ret = mail.sendMail();
			if (ret == Mail.OK){
				mailSend.add(tmpTo);
			}
			else if (ret == Mail.SEND_ERROR){
				mailNotSend.add(tmpTo);
			}
			else {
				break;
			}
		}
		if (tmpFile != null)
			for (int n = 0; n < tmpFile.length; n++)
				tmpFile[n].delete();
		m.setAddresseNumber(mailSend.size()); 
		m.setSend(true);
		m.setPostingTimestamp(TimeUtils.getCurrentTimestamp());
		try {
			m.save();
		}
		catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return mailNotSend;
	}

}
