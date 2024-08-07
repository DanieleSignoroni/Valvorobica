package it.valvorobica.thip.vendite.documentoVE.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;

import it.thera.thip.base.comuniVenAcq.DdtTes;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.documentoVE.web.DdtTestataVenGridActionAdapter;
import it.valvorobica.thip.vendite.documentoVE.brt.EstremiBartolini;
import it.valvorobica.thip.vendite.documentoVE.brt.YExpDDTBartolini;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 06/08/2024
 * <br><br>
 * <b>71XXX	DSSOF3	06/08/2024</b>
 * <p>
 *  
 * </p>
 */

public class YDdtTestataVenGridActionAdapter extends DdtTestataVenGridActionAdapter{

	private static final long serialVersionUID = 1L;

	protected static final String RES = "it.valvorobica.thip.vendite.documentoVE.resources.YDocumentoVendita";
	protected static final String IMG = "thermweb/image/ajaxgrid/ObjMod.gif";
	protected static final String EMETTI_CERT = "EMETTI_CERT";

	protected static final String INVIA_BARTOLINI = "INVIA_BARTOLINI";

	public void modifyToolBar(WebToolBar toolBar) {//EmettiCart
		super.modifyToolBar(toolBar);
		WebToolBarButton emettiCert = 
				new WebToolBarButton("EmettiCart","action_submit","same","no",RES,"EmettiCart",IMG,EMETTI_CERT,"multiple", false);
		toolBar.addButton(emettiCert);//70859 AGSOF3 REMMATA, BOTTONE SPOSTATO NEI DDT

		WebToolBarButton inviaBartolini = 
				new WebToolBarButton("EsportaVersoBRT","action_submit","same","no",RES,"EsportaVersoBRT",
						"it/valvorobica/thip/vendite/documentoVE/brt/images/logo_brt.png",INVIA_BARTOLINI,"single", false);
		toolBar.addButton(inviaBartolini);
	}


	@SuppressWarnings("rawtypes")
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		//aggiungere popup di richiesta sicuro?
		String azione = se.getRequest().getParameter(ACTION);
		if(azione.equals(EMETTI_CERT)) {
			try {
				String[] objectKeys = se.getRequest().getParameterValues(OBJECT_KEY);
				for(int i=0;i<objectKeys.length;i++) {
					DdtTes ov = null;
					ov = (DdtTes) DdtTes.elementWithKey(DdtTes.class, objectKeys[i], PersistentObject.NO_LOCK);
					ov.setFlagRisUte5('Y');
					if(ov.save() >= 0) {
						//71182 ini
						DocumentoVendita docven = null;
						String where = "ID_AZIENDA = '"+ov.getIdAzienda()+"' AND ANNO_BOLLA = '"+ov.getIdAnnoDdt()+"' AND NUMERO_BOLLA = '"+ov.getIdNumeroDdt()+"' ";
						try {
							Vector docs = DocumentoVendita.retrieveList(where, "", false);
							if(docs.size() > 0) {
								docven = (DocumentoVendita) docs.get(0);
								docven.setFlagRiservatoUtente5('Y');
								docven.save();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						//71182 fin
						ConnectionManager.commit();
					}
					else
						ConnectionManager.rollback();
				}
				this.callShowGrid(se);
			}catch (SQLException e) {				
				e.printStackTrace();
			}
		}else if(azione.equals(INVIA_BARTOLINI)) {
			EstremiBartolini estremiBrt = EstremiBartolini.estremiBartolini(YExpDDTBartolini.RES);
			if(estremiBrt != null) {
				String key = getStringParameter(se.getRequest(), OBJECT_KEY);
				try {
					DdtTes ddt = (DdtTes) DdtTes.elementWithKey(DdtTes.class, key, PersistentObject.NO_LOCK);
					if(ddt != null && ddt.getFlagRisUte4() != YExpDDTBartolini.PROCESSATO) {
						JSONObject json = estremiBrt.recuperaJsonDDT(ddt, estremiBrt);
						JSONObject bodyAsJSON = estremiBrt.sendShipmentBartolini(ddt,json, estremiBrt);
							if(bodyAsJSON.has("labels")) {
								JSONArray labels = bodyAsJSON.getJSONArray("labels");
								for(int i = 0; i < labels.length(); i ++) {
									//JSONObject label = labels.getJSONObject(i);
								}
						}
						//Flaggo il ddt come processato e non lo ri-processero' piu'
						try {
							ddt.setFlagRisUte4(YExpDDTBartolini.PROCESSATO);
							int rcSave = ddt.save();
							if(rcSave <= 0) {
								ConnectionManager.rollback();
							}else {
								ConnectionManager.commit();
							}
						} catch (SQLException e) {
							e.printStackTrace(Trace.excStream);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
			this.callShowGrid(se);
		}else {
			super.otherActions(cadc, se);
		}
	}

}
