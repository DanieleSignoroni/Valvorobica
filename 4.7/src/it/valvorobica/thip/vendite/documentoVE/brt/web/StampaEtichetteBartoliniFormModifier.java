package it.valvorobica.thip.base.vendite.documentoVE.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.web.WebFormModifier;

import it.thera.thip.base.azienda.Azienda;
import it.valvorobica.thip.base.vendite.documentoVE.StampaEtichetteBartolini;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 05/08/2024
 * <br><br>
 * <b>71XXX	DSSOF3	05/08/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class StampaEtichetteBartoliniFormModifier extends WebFormModifier {
	
	@Override
	public void writeHeadElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeBodyStartElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeFormStartElements(JspWriter out) throws IOException {
		try {
			String[] chiaveSelezionato = (String[]) getRequest().getAttribute("ChiaveSelezionato");
			getBODataCollector().setOnBORecursive(); //aggiungere per caricare i valori presenti sui componenti settati da parte dell'MDV
			StampaEtichetteBartolini bo = (StampaEtichetteBartolini) getBODataCollector().getBo();
			String list = "";
			for(String key : chiaveSelezionato) {
				String[] unp = KeyHelper.unpackObjectKey(key);
				list+= Azienda.getAziendaCorrente()+KeyHelper.KEY_SEPARATOR+unp[0]+"@";
			}
			bo.setChiaveSelezionato(list);
			getBODataCollector().setBo(bo);
			getBODataCollector().setOnBORecursive();
		} catch (Exception var9) {
			var9.printStackTrace(Trace.excStream);
		}
	}


	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {

	}
}
