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
import org.apache.poi.ss.usermodel.Row;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.vendite.proposteEvasione.CreaMessaggioErrore;

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

public class YCaricamentoMatchMatricoleArticolo {

	public static int caricamentoMatchMatricoleArticoloExcel(File file, String idLotto) throws SQLException {
		int num_importati = 0;
		HSSFWorkbook myWorkBook = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			myWorkBook = new HSSFWorkbook(fileInputStream);
			HSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator<Row> iterRigheExcel = mySheet.iterator();
			int index = 0;
			String prefissoMatricola = null;
			while (iterRigheExcel.hasNext()) {
				Row row = iterRigheExcel.next();
				Iterator<Cell> iterCelle = row.cellIterator();
				List<Cell> list = IteratorUtils.toList(iterCelle);
				if(index == 0) {
					//sono negli headers quindi prelevo il codice seriale, es header : Serial #(3766.23)
					prefissoMatricola = list.get(list.size() -1).getStringCellValue();
					prefissoMatricola = prefissoMatricola.substring(prefissoMatricola.indexOf("(")+1, prefissoMatricola.indexOf(")"));
				}else {
					if (list.size() > 0) {
						String codArt = list.get(0).getStringCellValue().trim();
						String range = list.get(list.size() -1).getStringCellValue().trim();
						if(!range.isEmpty() && !range.equals(" ")) {
							String[] ranges = range.split("-");
							YMatchMatricolaArticolo match = (YMatchMatricolaArticolo) Factory.createObject(YMatchMatricolaArticolo.class);
							match.setIdAzienda(Azienda.getAziendaCorrente());
							match.setIdArticolo(codArt);
							match.setIdMatricolaDa(prefissoMatricola + "." + ranges[0]);
							match.setIdMatricolaA(prefissoMatricola + "." + ranges[1]);
							match.setIdLotto(idLotto);
							int rc = 0;
							try {
								rc = match.save();
								if(rc < 0) {
									Trace.print(Trace.US1,CreaMessaggioErrore.daRcAErrorMessage(rc, (SQLException) match.getException()));
								}else {
									num_importati++;
								}
							} catch (SQLException e) {
								//boh qui vediamo che fare
								Trace.print(Trace.US1,CreaMessaggioErrore.daRcAErrorMessage(rc, e));
							}
						}
					}
				}
				index++;
			}
			ConnectionManager.commit();
		} catch (IOException e) {
			ConnectionManager.rollback();
			num_importati = 0;
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
