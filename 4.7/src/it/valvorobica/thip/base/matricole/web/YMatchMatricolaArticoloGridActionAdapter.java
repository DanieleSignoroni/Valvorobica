package it.valvorobica.thip.base.matricole.web;

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
 * @author Daniele Signoroni 17/06/2024
 * <br><br>
 * <b>71XXX	DSSOF3	17/06/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YMatchMatricolaArticoloGridActionAdapter extends GridActionAdapter {
	
	protected static final String IMPORTA = "IMPORTA";
	protected static String IMPORTA_IMG = "it/valvorobica/thip/base/matricole/img/import.png";
	protected static String IMPORTA_RES = "it/valvorobica/thip/base/matricole/resources/YMatchMatricolaArticolo";

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
			se.sendRequest(getServletContext(), "it/valvorobica/thip/base/matricole/YCaricamentoMatch.jsp", false);
		} else {
			super.otherActions(cadc, se);
		}

	}
}
