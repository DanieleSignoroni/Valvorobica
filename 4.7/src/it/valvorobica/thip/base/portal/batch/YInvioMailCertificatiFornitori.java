package it.valvorobica.thip.base.portal.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.cbs.MessageTmpl;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.ssd.SSDDocument;

import it.thera.thip.base.articolo.ArticoloTM;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.documentoDgt.DestinatarioDgt;
import it.thera.thip.base.documentoDgt.DestinatarioDgtRiga;
import it.thera.thip.base.documentoDgt.DestinatarioDgtTM;
import it.thera.thip.base.documentoDgt.DocumentoDigitaleTM;
import it.thera.thip.base.documentoDgt.DocumentoSSD;
import it.thera.thip.base.documentoDgt.TipoDocumentoDigitale;
import it.thera.thip.base.profilo.UtenteAzienda;
import it.thera.thip.magazzino.generalemag.LottoTM;
import it.valvorobica.thip.base.articolo.YMicroFamigliaTM;
import it.valvorobica.thip.base.fornitore.YFornitoreAcquisto;
import it.valvorobica.thip.base.fornitore.YFornitoreAcquistoTM;
import it.valvorobica.thip.magazzino.generalemag.YLottoTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 13/02/2024
 * <br><br>
 * <b></b>    <p></p>
 */

public class YInvioMailCertificatiFornitori extends BatchRunnable{

	@Override
	protected boolean run() {
		boolean ret = true;
		MessageTmpl modelloDiMessaggio = null;
		TipoDocumentoDigitale tipoDocumento = null;
		Iterator<YFornitoreAcquisto> iterFornitoriAbilitati = getFornitoriAcquistoAbilitatiRicezioneCertificatiPortale().iterator();
		while (iterFornitoriAbilitati.hasNext()) {
			YFornitoreAcquisto fornitore = (YFornitoreAcquisto) iterFornitoriAbilitati.next();
			ResultSet rs = null;
			String stmt = getStmtEstrazione(fornitore.getIdFornitore(), "CERT_F02", true);
			CachedStatement cs = new CachedStatement(stmt);
			boolean inviaMail = false;
			try {
				rs = cs.executeQuery();
				if(rs.next()) {
					inviaMail = true;
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}finally {
				try { 
					if(rs != null) {
						rs.close();
					}
				}catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
			if(inviaMail) {
				DestinatarioDgt destinatario = recuperaDestinatarioDigitalePerTipoDocumento(DestinatarioDgt.FORNITORE, "", fornitore.getIdFornitore());
				if(destinatario == null) {
					destinatario = recuperaDestinatarioDigitaleGenerico(DestinatarioDgt.FORNITORE, fornitore.getIdFornitore());
					if(destinatario != null) {
						try {
							boolean isOk = inviaMailFornitore(modelloDiMessaggio, destinatario, tipoDocumento, fornitore);
							if(isOk) {
								output.println("<< Mail inviata correttament al fornitore: "+fornitore.getIdFornitore()+"/"+fornitore.getRagioneSociale());
							}
						} catch (SQLException e) {
							output.println("<< ERRORE:!!"+e.getMessage()+" >>");
							e.printStackTrace(Trace.excStream);
						}
					}else {
						output.println("<< ATTENZIONE!! : Per il fornitore = "+fornitore.getIdFornitore()+"/"+fornitore.getRagioneSociale()+" \n"
								+ " non e' stato definito nessun destinatario digitale, impossibile inviare la mail!  >>.");
						continue;
					}
				}
			}
		}
		return ret;
	}

	protected boolean inviaMailFornitore(MessageTmpl mss, DestinatarioDgt dest, TipoDocumentoDigitale tipoDoc,YFornitoreAcquisto fornitore) throws SQLException {
		DocumentoSSD ssd = null;
		ssd = (DocumentoSSD) Factory.createObject(DocumentoSSD.class);
		ssd.setTipoDocumentoDigitale(tipoDoc);
		ssd.setIdAzienda(Azienda.getAziendaCorrente());
		ssd.setMsgTmpl(mss);
		ssd.setFornitore(fornitore);
		ssd.setSender("daniele.signoroni@softre.it");
		ssd.setAddressee(dest.getIndirizzoSpecifico());
		String elencoCC = "";
		for (int ic = 0; ic < dest.getDestinatarioDgtRiga().size(); ic++) {
			if (elencoCC.equals(""))
				elencoCC += ((DestinatarioDgtRiga)dest.getDestinatarioDgtRiga().get(ic)).getIndirizziCC();
			else
				elencoCC += "," + ((DestinatarioDgtRiga)dest.getDestinatarioDgtRiga().get(ic)).getIndirizziCC();
			dest.getDestinatarioDgtRiga().remove(ic);
			ic--;
		}
		if (!elencoCC.equals(""))
			ssd.setAddresseeCC(elencoCC);
		else
			ssd.setAddresseeCC(null);
		ssd.setSubject(UtenteAzienda.getUtenteAziendaConnesso().getAzienda().getDescrizione()+ " - " + "Certificati lotto da allegare");
		String msg = mss.getText();
		String link = "<a href=http://62.152.111.69:10101/panth01/customersportal?offerKey=>Click here to fill the offer</a>";
		ssd.setHTMLText(msg + "<br>" + link);
		ssd.setStatus(SSDDocument.READY);
		if(ssd.save() >= 0) {
			ConnectionManager.commit();
			boolean ris = ssd.sendDocument();
			if(!ris) {
				throw new SQLException("Errore nell'invio della mail");
			}
		}else {
			throw new SQLException("Errore nel salvataggio del documento SSD");
		}
		return true;
	}

	protected String getStmtEstrazione(String idFornitore,String tipoCertificato, boolean abilitatoPortale) {
		String stmt = null;
		stmt = "SELECT * FROM "+YLottoTM.TABLE_NAME_EXT+" YLOT "
				+ "INNER JOIN "+LottoTM.TABLE_NAME+" LOT "
				+ "ON YLOT."+YLottoTM.ID_AZIENDA+" = LOT."+LottoTM.ID_AZIENDA+" "
				+ "AND YLOT."+YLottoTM.ID_ARTICOLO+" = LOT."+LottoTM.ID_ARTICOLO+"  "
				+ "AND YLOT."+YLottoTM.ID_LOTTO+" = LOT."+LottoTM.ID_LOTTO+" "
				+ "INNER JOIN "+YFornitoreAcquistoTM.TABLE_NAME_EXT+" F "
				+ "ON YLOT."+YLottoTM.ID_AZIENDA+" = F."+YFornitoreAcquistoTM.ID_AZIENDA+"  "
				+ "AND LOT."+LottoTM.R_FORNITORE+" = F."+YFornitoreAcquistoTM.ID_FORNITORE+"  "
				+ "INNER JOIN "+ArticoloTM.TABLE_NAME+" ART  "
				+ "ON ART."+ArticoloTM.ID_AZIENDA+" = YLOT."+YLottoTM.ID_AZIENDA+"  "
				+ "AND ART."+ArticoloTM.ID_ARTICOLO+" = LOT."+YLottoTM.ID_ARTICOLO+"  "
				+ "INNER JOIN "+YMicroFamigliaTM.TABLE_NAME_EXT+" MICR "
				+ "ON MICR."+YMicroFamigliaTM.ID_AZIENDA+" = YLOT."+YLottoTM.ID_AZIENDA+"  "
				+ "AND MICR."+YMicroFamigliaTM.ID_MICROFAMIGLIA+" = ART."+ArticoloTM.ID_MICROFAMIGLIA+" ";
		stmt +=  "WHERE YLOT."+YLottoTM.CERTIFICATI_DA_PORTALE+" = '"+(abilitatoPortale ? 'Y' : 'N')+"' "
				+ "AND F."+YFornitoreAcquistoTM.CERTIFICATI_DA_PORTALE+" = '"+(abilitatoPortale ? 'Y' : 'N')+"' "
				+ "AND MICR."+YMicroFamigliaTM.CERTIFICATI_DA_PORTALE+" = '"+(abilitatoPortale ? 'Y' : 'N')+"' "
				+ "AND F."+YFornitoreAcquistoTM.ID_FORNITORE+" = '"+idFornitore+"' "
				+ "AND NOT EXISTS ( "
				+ "	SELECT * FROM "+DocumentoDigitaleTM.TABLE_NAME+" WHERE "+DocumentoDigitaleTM.R_TIPO_DOC_DGT+" = '"+tipoCertificato+"' "
				+ "	AND "+DocumentoDigitaleTM.R_FORNITORE+" = F."+YFornitoreAcquistoTM.ID_FORNITORE+" "
				+ "	AND "+DocumentoDigitaleTM.R_ARTICOLO+" = YLOT."+YLottoTM.ID_ARTICOLO+"  "
				+ "	AND "+DocumentoDigitaleTM.CLASSIFICAZIONE4+" = YLOT."+YLottoTM.ID_LOTTO+"  "
				+ ")";
		return stmt;
	}

	@SuppressWarnings("rawtypes")
	public static DestinatarioDgt recuperaDestinatarioDigitalePerTipoDocumento(char tipoDest, String idTipoDocDgt, String idDest) {
		String where = DestinatarioDgtTM.TIPO_DESTIN + "='" + tipoDest + "' AND " + DestinatarioDgtTM.R_TIPO_DOC_DGT
				+ "='" + idTipoDocDgt + "' AND (" + DestinatarioDgtTM.R_DESTINATARIO + "='" + idDest + "' OR  "+DestinatarioDgtTM.R_DESTINATARIO + " IS NULL )  AND "+ DestinatarioDgtTM.ID_AZIENDA + "='" + Azienda.getAziendaCorrente() + "'";
		try {
			List lista = DestinatarioDgt.retrieveList(where, "", false);
			if (lista.isEmpty()) {
				return (DestinatarioDgt) lista.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream); 
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static DestinatarioDgt recuperaDestinatarioDigitaleGenerico(char tipoDest, String idDest) {
		String where = DestinatarioDgtTM.TIPO_DESTIN + "='" + tipoDest + "' AND " + DestinatarioDgtTM.R_DESTINATARIO + "='" + idDest + "' AND "+ DestinatarioDgtTM.ID_AZIENDA + "='" + Azienda.getAziendaCorrente() + "'";
		try {
			List lista = DestinatarioDgt.retrieveList(where, "", false);
			if (lista.isEmpty()) {
				return (DestinatarioDgt) lista.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	/**
	 * Ritorna tutti quei fornitori dell'azienda corrente che hanno la ricezione certificati da portale abilitata.
	 * @return a {@link List}
	 */
	@SuppressWarnings("unchecked")
	public static List<YFornitoreAcquisto> getFornitoriAcquistoAbilitatiRicezioneCertificatiPortale(){
		List<YFornitoreAcquisto> list = new ArrayList<YFornitoreAcquisto>();
		try {
			String where = " "+YFornitoreAcquistoTM.ID_AZIENDA+" = '"+Azienda.getAziendaCorrente()+"' AND "+YFornitoreAcquistoTM.CERTIFICATI_DA_PORTALE+" = 'Y' ";
			list = YFornitoreAcquisto.retrieveList(YFornitoreAcquisto.class, where, "", false);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return list;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YInsCertPortale";
	}

}
