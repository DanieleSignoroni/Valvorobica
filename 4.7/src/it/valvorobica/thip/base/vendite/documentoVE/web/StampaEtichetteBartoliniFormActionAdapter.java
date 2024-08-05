package it.valvorobica.thip.base.vendite.documentoVE.web;

import com.thera.thermfw.web.MDVToolBarButton;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;
import com.thera.thermfw.web.servlet.FormActionAdapter;

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

public class StampaEtichetteBartoliniFormActionAdapter extends FormActionAdapter {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		WebToolBarButton save = (WebToolBarButton) toolBar.getButtons().get(1);
		MDVToolBarButton mdv = (MDVToolBarButton) toolBar.getButtons().get(12);
		toolBar.getButtons().clear();
		toolBar.addButton(save);
		toolBar.addButton(mdv);
	}
}
