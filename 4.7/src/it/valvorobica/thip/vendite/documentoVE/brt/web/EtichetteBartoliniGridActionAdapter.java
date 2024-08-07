package it.valvorobica.thip.vendite.documentoVE.brt.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebMenuBar;
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

	protected static final String RES = "it.valvorobica.thip.vendite.documentoVE.brt.resources.EtichetteBartolini";

	public static final String PRINT_ETICHETTE = "PRINT_ETICHETTE";
	public static final String VIEW_LABELS = "VIEW_LABELS";

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		int btnRfrsh = toolBar.getButtonIndex("Refresh");
		
		WebToolBarButton btn = (WebToolBarButton) toolBar.getButtons().get(btnRfrsh);
		
		super.modifyToolBar(toolBar);
		
		toolBar.getButtons().clear();
		toolBar.addButton(btn);
		
		WebToolBarButton stampaEtichetta = new WebToolBarButton("StampaEtichette",
				"action_submit", "new", "no", RES, "StampaEtichette",
				"thermweb/image/gui/PreviewList.gif", PRINT_ETICHETTE, "multipleSelSingleWindow", false);
		
		WebToolBarButton visualizzaEtichetta = new WebToolBarButton("ViewLabels",
				"action_submit", "new", "no", RES, "ViewLabels",
				"it/thera/thip/base/documentoDgt/images/ShowAllOggettiContents.gif", VIEW_LABELS, "multiple", false);
		
		toolBar.addButton(stampaEtichetta);
		toolBar.addButton(visualizzaEtichetta);
	}

	@Override
	public void modifyMenuBar(WebMenuBar menuBar) {
		super.modifyMenuBar(menuBar);
		menuBar.removeMenu("ListMenu");
		menuBar.removeMenu("SelectedMenu");
		menuBar.removeMenu("ViewSettingMenu");
	}

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String azione = getStringParameter(se.getRequest(), ACTION);
		if(azione.equals(PRINT_ETICHETTE)) {
			String[] chiaviSel = se.getRequest().getParameterValues(OBJECT_KEY);
			se.getRequest().setAttribute("ChiaveSelezionato", chiaviSel);
			String jsp = "it/valvorobica/thip/vendite/documentoVE/brt/StampaEtichetteBartolini.jsp";
			se.sendRequest(getServletContext(),jsp+"?MODE=NEW&InitialActionAdapter=com.thera.thermfw.web.servlet.GridActionAdapter", false);
		}else if(azione.equals(VIEW_LABELS)) {
			String[] objectKeys = se.getRequest().getParameterValues(OBJECT_KEY);
		    int objectKeyNum = 0;
		    String objectKeyNumStr = getStringParameter(se.getRequest(), OBJECT_KEY_NUM);
		    if(objectKeyNumStr != null && objectKeyNumStr.length() > 0)
		        objectKeyNum = Integer.parseInt(objectKeyNumStr);
		    String keyObject =objectKeys[objectKeyNum];
			se.getRequest().setAttribute("ChiaveEtichetta", keyObject);
			se.sendRequest(getServletContext(), "it/valvorobica/thip/vendite/documentoVE/brt/VisualizzaEtichetta.jsp", false);
		}else {
			super.otherActions(cadc, se);
		}
	}
}
