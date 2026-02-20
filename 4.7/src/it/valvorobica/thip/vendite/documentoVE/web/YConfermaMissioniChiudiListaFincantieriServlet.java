package it.valvorobica.thip.vendite.documentoVE.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.ServletEnvironment;

import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.documentoVE.web.ServletGestioneDocumentoVendita;
import it.valvorobica.thip.logis.bas.YCostantiValvo;
import it.valvorobica.thip.logis.lgb.YTestataLista;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 20/02/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    20/02/2026  DSSOF3   Prima stesura
 */

public class YConfermaMissioniChiudiListaFincantieriServlet extends ServletGestioneDocumentoVendita {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public void processAction(ServletEnvironment se) throws ServletException, IOException {
		super.processAction(se);
		String action = getStringParameter(se.getRequest(), "thAction");
		String azione =  getStringParameter(se.getRequest(), "thAzione");
		String idAzienda = getStringParameter(se.getRequest(), "thIdAzienda");
		String idNumeroDoc = getStringParameter(se.getRequest(), "thNumeroDocumento");
		String idAnnoDoc = getStringParameter(se.getRequest(), "thAnnoDocumento");
		String className = getStringParameter(se.getRequest(),"thClassName");
		String boDataCollectorName = getStringParameter(se.getRequest(), COLLECTOR_NAME);
		List errori = null;
		try {
			DocumentoVendita dv = (DocumentoVendita) creaDocumento(idAzienda, idAnnoDoc, idNumeroDoc);
			if(dv != null) {
				YTestataLista tl = (YTestataLista) YCostantiValvo.testataListaDocumentoVendita(dv, PersistentObject.NO_LOCK);
				if(tl != null && tl.getStatoLista() == TestataLista.APERTO) {
					errori = tl.eseguiMissioniTrasferimentoFincantieri();
				}else if(tl != null && tl.getStatoLista() != TestataLista.APERTO){
					errori.add(new ErrorMessage("BAS0000078","Stato lista diverso da APERTO"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		if (errori!=null && !errori.isEmpty()){
			String errorMsg = getTextErrors(errori);
			se.sendRequest(getServletContext(),"it/thera/thip/base/comuniVenAcq/NascondiImmagine.jsp?Error="+com.thera.thermfw.web.URLUtils.get().encode(errorMsg), true);
		}
		else
			se.sendRequest(getServletContext(),"it/thera/thip/base/comuniVenAcq/NascondiImmagine.jsp", true);
	}
}
