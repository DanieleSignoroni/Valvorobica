package it.valvorobica.thip.susa.utility.web;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.thera.thermfw.web.MultipartFile;
import com.thera.thermfw.web.MultipartHandler;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.servlet.BaseServlet;

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
 * 72XXX    12/08/2025  DSSOF3   Prima stesura
 */
public class CaricamentoFileSusaSalvaFile extends BaseServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void processAction(ServletEnvironment se) throws Exception {
		MultipartHandler mh = new MultipartHandler(se);
		mh.initialize();
		MultipartFile mf = (MultipartFile) mh.getMultipartFiles().get(0);

		// nome originale e sanificazione
		String submitted = mf.getFileName(); // String
		String safeFilename = sanitizeFilename(submitted); // v. helper sotto

		// eviti collisioni
		String ext  = getExtension(safeFilename);
		String base = removeExtension(safeFilename);
		String uniqueName = base + "_" + UUID.randomUUID() + (ext.isEmpty() ? "" : "." + ext);

		// cartella temp di Tomcat/Java
		Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
		Files.createDirectories(tempDir);

		// path di destinazione
		Path target = tempDir.resolve(uniqueName);

		// dati del file (byte[])
		byte[] data = mf.getData();
		if (data == null || data.length == 0) {
			se.getResponse().sendError(HttpServletResponse.SC_BAD_REQUEST, "File vuoto");
			return;
		}

		// scrittura su disco
		Files.write(target, data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

		// risposta col full path
		se.getResponse().setContentType("text/plain; charset=UTF-8");
		se.getResponse().getWriter().write(target.toAbsolutePath().toString());

	}

	private static String sanitizeFilename(String name) {
		if (name == null || name.isEmpty()) return "file";
		return name.replaceAll("[\\r\\n]", "").replaceAll("[^A-Za-z0-9._-]", "_");
	}

	private static String getExtension(String name) {
		int i = name.lastIndexOf('.');
		return (i >= 0 && i < name.length() - 1) ? name.substring(i + 1) : "";
	}

	private static String removeExtension(String name) {
		int i = name.lastIndexOf('.');
		return (i > 0) ? name.substring(0, i) : name;
	}

}