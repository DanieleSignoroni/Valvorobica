package it.valvorobica.thip.base.vendite.documentoVE.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;
import com.thera.thermfw.web.servlet.GridActionAdapter;

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

public class EtichetteBartoliniGridActionAdapter extends GridActionAdapter {

	private static final long serialVersionUID = 1L;
	
	protected static final String RES = "it.valvorobica.thip.base.vendite.documentoVE.resources.EtichetteBartolini";
	
	public static final String PRINT_ETICHETTE = "PRINT_ETICHETTE";
	
	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		WebToolBarButton invioDocumento = new WebToolBarButton("StampaEtichette",
				"action_submit", "new", "no", PRINT_ETICHETTE, "StampaEtichette",
				"it/softre/thip/base/firmadigitale/img/firma.png", PRINT_ETICHETTE, "multipleSelSingleWindow", false);
		toolBar.addButton(invioDocumento);
	}
	
	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String azione = getStringParameter(se.getRequest(), ACTION);
		if(azione.equals(PRINT_ETICHETTE)) {
			String[] chiaviSel = se.getRequest().getParameterValues(OBJECT_KEY);
			se.getRequest().setAttribute("ChiaveSelezionato", chiaviSel);
			String jsp = "it/valvorobica/thip/base/vendite/documentoVE/StampaEtichetteBartolini.jsp";
			se.sendRequest(getServletContext(),jsp+"?MODE=NEW&InitialActionAdapter=com.thera.thermfw.web.servlet.GridActionAdapter", false);
		}else {
			super.otherActions(cadc, se);
		}
	}
}
