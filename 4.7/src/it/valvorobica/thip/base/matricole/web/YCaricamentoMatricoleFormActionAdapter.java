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

import it.valvorobica.thip.base.matricole.YCaricamentoMatricole;

public class YCaricamentoMatricoleFormActionAdapter extends FormActionAdapter {

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
			File file = YCaricamentoMatricole.getDirectoryTemporanea(se, multiPartFile);
			if (file.getName().endsWith(".xlsx") || file.getName().endsWith(".xls")) {
				int num_importati = 0;
				try {
					num_importati = YCaricamentoMatricole.caricamentoMatricoleExcel(file);
					out.println("parent.window.alert('Importate correttamente : " + num_importati + " matricole');");
				} catch (SQLException e) {
					out.println("parent.window.alert('Errore nell'importazione!, controllare il file');");
					e.printStackTrace(Trace.excStream);
				}
				out.println("parent.window.close();");
			} else {
				out.println(
						"parent.window.alert('Attenzione e' stato droppato un file che non ha estensione \n, ne xlsx ne xls!);");
			}
		}
		out.println("</script>");
	}

}
