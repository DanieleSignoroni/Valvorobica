package it.valvorobica.thip.susa.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;
import com.thera.thermfw.web.servlet.GridActionAdapter;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 08/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72102    08/08/2025  DSSOF3   Prima stesura
 */

public class CapparioSusaGridActionAdapter extends GridActionAdapter {

	public static final String IMPORTA = "IMPORTA";
	public static String IMPORTA_IMG = "it/valvorobica/thip/base/matricole/img/import.png";
	public static String IMPORTA_RES = "it/valvorobica/thip/susa/resources/CapparioSusa";

	private static final long serialVersionUID = 1L;

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		WebToolBarButton importa = new WebToolBarButton(IMPORTA, "action_submit", "new", "no", IMPORTA_RES, IMPORTA,
				IMPORTA_IMG, IMPORTA, "", false);
		toolBar.addButton("tbbtExtension", importa);
	}

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String action = getStringParameter(se.getRequest(), ACTION);
		if (action.equals(IMPORTA)) {
			se.getRequest().setAttribute("ClassNameOrigine", getStringParameter(se.getRequest(), CLASS_NAME));
			se.sendRequest(getServletContext(), "it/valvorobica/thip/susa/utility/CaricamentoFileSusa.jsp", false);
		} else {
			super.otherActions(cadc, se);
		}

	}

}