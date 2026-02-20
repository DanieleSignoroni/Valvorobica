package it.valvorobica.thip.vendite.documentoVE.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.base.IniFile;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.web.ServletEnvironment;

import it.thera.thip.base.documenti.web.DocumentoDatiSessione;
import it.thera.thip.base.documenti.web.DocumentoGridActionAdapter;
import it.thera.thip.vendite.documentoVE.web.DocumentoTestataVenditaEstrattoFormActionAdapter;

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

public class YDocumentoTestataVenditaEstrattoFormActionAdapter extends DocumentoTestataVenditaEstrattoFormActionAdapter {

	private static final long serialVersionUID = 1L;

	public static final String CONFERMA_MISSIONI_CHIUDI_LISTA_TRASF_FINCANTIERI = "CONFERMA_MISSIONI_CHIUDI_LISTA_TRASF_FINCANTIERI";

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String azione = getAzione(se);
		if(azione.equals(CONFERMA_MISSIONI_CHIUDI_LISTA_TRASF_FINCANTIERI)) {
			confermaMissioniChiudiListaTrasferimentoFincantieri(cadc, se);
		}else {
			super.otherActions(cadc, se);
		}
	}

	protected void confermaMissioniChiudiListaTrasferimentoFincantieri(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		DocumentoDatiSessione datiSessione = getDatiSessione(se);
		String chiaveDatiSessione = getStringParameter(se.getRequest(), DocumentoDatiSessione.CHIAVE_DATI_SESSIONE);
		String chiavi[] = datiSessione.getValoriChiaviDocumento();
		String idAzienda = chiavi[0];
		String idNumeroDoc = chiavi[2];
		String idAnnoDoc = chiavi[1];
		String appPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");
		String servPath = IniFile.getValue("thermfw.ini", "Web", "ServletPath");
		String indirizzoServer = "/" + appPath + "/" + servPath + "/";
		String initialActionAdapter = DocumentoGridActionAdapter.class.getName();
		String objectKey = com.thera.thermfw.web.URLUtils.get().encode(KeyHelper.buildObjectKey(chiavi));

		String parametri = "?IdAzienda=" + idAzienda +
				"&IdNumeroDoc=" + idNumeroDoc +
				"&IdAnnoDoc=" + idAnnoDoc +
				"&Azione=" + CONFERMA_MISSIONI_CHIUDI_LISTA_TRASF_FINCANTIERI +
				"&" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" + chiaveDatiSessione +
				"&InitialActionAdapter=" + initialActionAdapter +
				"&" + INDIRIZZO_SERVER + "=" + indirizzoServer +
				"&ObjectKey=" + objectKey
				+"&Mode=UPDATE" +"&Key="+objectKey;
		se.sendRequest(getServletContext(), "it/valvorobica/thip/vendite/documentoVE/ConfermaMissioniChiudiListaFincantieri.jsp"+parametri, true);
	}
}
