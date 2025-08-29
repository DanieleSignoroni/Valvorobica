package it.valvorobica.thip.susa.utility.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.web.WebFormModifier;

import it.valvorobica.thip.susa.utility.CaricamentoFileSusa;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 12/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72102    12/08/2025  DSSOF3   Prima stesura
 */

public class CaricamentoFileSusaFormModifier extends WebFormModifier {

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		CaricamentoFileSusa bo = (CaricamentoFileSusa) getBODataCollector().getBo();
		String classNameOrig = (String) getServletEnvironment().getRequest().getAttribute("ClassNameOrigine");
		bo.setClassNameOrigine(classNameOrig);
		getBODataCollector().setOnBORecursive();
	}

	@Override
	public void writeBodyStartElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeFormStartElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {

	}

}
