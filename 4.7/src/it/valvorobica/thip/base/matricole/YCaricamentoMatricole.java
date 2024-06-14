package it.valvorobica.thip.base.matricole;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.web.MultipartFile;
import com.thera.thermfw.web.MultipartHandler;
import com.thera.thermfw.web.ServletEnvironment;

import it.thera.thip.base.azienda.Azienda;

public class YCaricamentoMatricole {

	public static boolean checkFile(ServletEnvironment se, MultipartHandler mh) {
		if (mh.getMultipartFiles().size() == 0) {
			return false;
		}
		MultipartFile mf = (MultipartFile) mh.getMultipartFiles().get(0);
		if (mf.getSize() == 0) {
			return false;
		}
		return true;
	}

	public static File getDirectoryTemporanea(ServletEnvironment se, MultipartFile mf) throws IOException {
		String rootPath = se.getSession().getServletContext().getRealPath("");
		File tmp = new File(rootPath, "tmp");
		if (!tmp.exists()) {
			tmp.mkdir();
		}
		String pathSave = tmp.getAbsolutePath().toString() + "\\";
		mf.save(pathSave);
		File newFile = new File(tmp.getAbsoluteFile().toString(), mf.getFileName());
		return newFile;
	}

	public static int caricamentoMatricoleExcel(File file) throws SQLException {
		int num_importati = 0;
		HSSFWorkbook myWorkBook = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			myWorkBook = new HSSFWorkbook(fileInputStream);
			HSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator<Row> iterRigheExcel = mySheet.iterator();
			IteratorUtils.skippingIterator(iterRigheExcel, 1);
			while (iterRigheExcel.hasNext()) {
				Row row = iterRigheExcel.next();
				Iterator<Cell> iterCelle = row.cellIterator();
				List<Cell> list = IteratorUtils.toList(iterCelle);
				if (list.size() > 0) {
					String serial = list.get(0).getStringCellValue().trim();
					String body = null;
					String shaft = null;
					String disc = null;
					if (list.get(1).getCellTypeEnum() == CellType.NUMERIC) {
						body = String.valueOf(list.get(1).getNumericCellValue()).trim();
					} else {
						body = list.get(1).getStringCellValue().trim();
					}
					if (list.get(2).getCellTypeEnum() == CellType.NUMERIC) {
						disc = String.valueOf(list.get(2).getNumericCellValue()).trim();
					} else {
						disc = list.get(2).getStringCellValue().trim();
					}
					if (list.get(3).getCellTypeEnum() == CellType.NUMERIC) {
						shaft = String.valueOf(list.get(3).getNumericCellValue()).trim();
					} else {
						shaft = list.get(3).getStringCellValue().trim();
					}
					YMatricolaValvo matricola = (YMatricolaValvo) Factory.createObject(YMatricolaValvo.class);
					matricola.setIdAzienda(Azienda.getAziendaCorrente());
					matricola.setIdMatricola(serial);
					matricola.setBody(body);
					matricola.setDisc(disc);
					matricola.setShaft(shaft);
					int rc = 0;
					try {
						rc = matricola.save();
						if (rc > 0)
							num_importati++;
					} catch (SQLException e) {
						// per ora non mi interessa
						Trace.println(Trace.US1, "Impossibile salvare la matricola: " + matricola.getIdMatricola()
								+ ", " + "con rc =" + rc + ", ed eccezione" + e.getMessage());
					}
				}
			}
			ConnectionManager.commit();
		} catch (IOException e) {
			ConnectionManager.rollback();
			e.printStackTrace(Trace.excStream);
		} finally {
			if (myWorkBook != null) {
				try {
					myWorkBook.close();
					if (file != null) {
						file.delete();
					}
				} catch (IOException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return num_importati;
	}

}
