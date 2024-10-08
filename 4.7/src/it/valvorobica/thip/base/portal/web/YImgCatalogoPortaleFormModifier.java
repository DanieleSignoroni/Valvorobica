package it.valvorobica.thip.base.portal.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.IniFile;
import com.thera.thermfw.web.WebElement;
import com.thera.thermfw.web.WebFormModifier;

import it.valvorobica.thip.base.portal.YImgCatalogoPortale;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 22/01/2024
 * <br><br>
 * <b>71392	DSSOF3	22/01/2024</b>    <p>Prima stesura</p>
 */


public class YImgCatalogoPortaleFormModifier extends WebFormModifier {

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {

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
		YImgCatalogoPortale img = (YImgCatalogoPortale) this.getBODataCollector().getBo();
		if (img.isOnDB()) {
			if (img.getUrlImg() != null) {
				out.println("<script>");
				String url = formatUrlImgForPortal(img.getUrlImg());
				out.println("document.getElementById('imgSuDB').src = '"+WebElement.formatStringForHTML(url)+"';");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("document.getElementById('imgSuDB').parentNode.parentNode.style.display = 'none';");
				out.println("</script>");
			}
		} else {
			out.println("<script>");
			out.println("document.getElementById('imgSuDB').parentNode.parentNode.style.display = 'none';");
			out.println("</script>");
		}
	}
	
	public String formatUrlImgForPortal(String urlImg) {
		String userDirectory = System.getProperty("user.dir") + "\\" + "WebContent";
		urlImg = urlImg.replace(userDirectory, "");
		String wb = "/"+IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath")+"" + "/ImmaginiPortale/";
		String fileName = null;
		fileName = urlImg.substring(urlImg.lastIndexOf("\\") + 1, urlImg.length());
		urlImg = wb + fileName;
		return urlImg;
	}

}
