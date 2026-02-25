package it.valvorobica.thip.vendite.documentoVE.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.WebJSTypeList;

import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.documentoVE.web.DocumentoVenditaEstrattoFormModifier;

import it.valvorobica.thip.base.comuniVenAcq.YUtilsSemaforo;
import it.valvorobica.thip.logis.bas.YCostantiValvo;

/**
 * <h1>Softre Solutions</h1>
 * @author Andrea Gatta
 * <br><br>
 * <b>70647	AGSOF3	01/09/2022</b>	<p>Aggiunto semaforo nella lista dei bottoni</p>
 * <b>71426	DSSOF3	07/03/2024</b>	
 * <p>
 * Code refactor.
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72375    20/02/2026  DSSOF3   Trasferimento fincantieri.
 */

public class YDocumentoVenditaEstrattoFormModifier extends DocumentoVenditaEstrattoFormModifier {

	public static final String RESOURCES_PERS = "it/valvorobica/thip/vendite/documentoVE/resources/YDocumentoVendita";

	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {
		super.writeBodyEndElements(out);
		DocumentoVendita doc = (DocumentoVendita) getBODataCollector().getBo();
		if(doc != null) {
			String colore = YUtilsSemaforo.isNotaClientePresente(doc.getIdAzienda(), doc.getIdCliente()) ? "#ff0000b3" : "#00ff00"; //#00ff00=verde
			out.println("<script>");
			out.println(" addBtnSemaforo('"+colore+"'); ");
			out.println("</script>");	
		}
	}

	@Override
	public void writePulsantiBarraAzioniPers(JspWriter out) throws IOException {
		super.writePulsantiBarraAzioniPers(out);
		DocumentoVendita doc = (DocumentoVendita)getBODataCollector().getBo();
		//72375 <
		if(doc.getStatoAvanzamento() == StatoAvanzamento.PROVVISORIO) {
			TestataLista tl = YCostantiValvo.testataListaDocumentoVendita(doc, PersistentObject.NO_LOCK);
			if(tl != null && tl.getCodiceTipoLista().equals(YCostantiValvo.codTipoListaTrasferimentoFincantieri())) {
				out.println("<td nowrap=\"true\" height=\"0\">");
				out.println("<button name=\"thChiudiListaTrasfFincanti\" id=\"thChiudiListaTrasfFincanti\" onclick=\"chiudiListaTrasferimentoFincantieri()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RESOURCES_PERS, "PulsanteChiudiListaTrasfFincanti") + "\">");
				out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"" + "it/thera/thip/logis/lgb/images/ChiudiLista.gif" + "\" >");
				out.println("</button>");
				out.println("</td>");
			}
		}
		//72375 >
	}


	public void writeHeadElements(JspWriter out) throws java.io.IOException {
		super.writeHeadElements(out);
		out.println(WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/comuniVenAcq/YUtilsSemaforo.js", getServletEnvironment().getRequest()));
	}

}