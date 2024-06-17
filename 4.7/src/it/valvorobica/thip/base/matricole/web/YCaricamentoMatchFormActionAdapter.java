package it.valvorobica.thip.base.matricole.web;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.web.MultipartFile;
import com.thera.thermfw.web.MultipartHandler;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.servlet.FormActionAdapter;

import it.valvorobica.thip.base.matricole.YCaricamentoMatchMatricoleArticolo;
import it.valvorobica.thip.base.matricole.YCaricamentoMatricole;

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

public class YCaricamentoMatchFormActionAdapter extends FormActionAdapter {

	private static final long serialVersionUID = 1L;

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		toolBar.getButtons().clear();
	}

	@Override
	public void processAction(ServletEnvironment se) throws ServletException, IOException {
		ServletOutputStream out = se.getResponse().getOutputStream();
		out.println("<script>");
		MultipartHandler mh = new MultipartHandler(se);
		mh.initialize();
		MultipartFile multiPartFile = null;
		if (YCaricamentoMatricole.checkFile(se, mh)) {
			multiPartFile = (MultipartFile) mh.getMultipartFiles().get(0);
			String idLotto = mh.getMultipartRequest().getParameter("IdLotto");
			if(idLotto != null) {
				File file = YCaricamentoMatricole.getDirectoryTemporanea(se, multiPartFile);
				if (file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls")) {
					int num_importati = 0;
					try {
						YCaricamentoMatchMatricoleArticolo.caricamentoMatchMatricoleArticoloExcel(file,idLotto);
						out.println("parent.window.alert('Importate correttamente : " + num_importati + " matricole');");
						out.println("parent.window.close();");
					} catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				} else {
					out.println(
							"parent.window.alert('Attenzione e' stato droppato un file che non ha estensione \n, ne xlsx ne xls!);");
				}
			}else {
				out.println("parent.window.alert('Il lotto e' obbligatorio!');");
			}
		}else {
			out.println("parent.window.alert('File obbligatorio!');");
		}
		out.println("parent.window.close();");
		out.println("</script>");
	}

}
