package it.valvorobica.thip.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.valvorobica.thip.base.matricole.YMatricolaValvo;

/**
 * Lavoro per l'importazione delle 'Misure valvole farfalla new', tabella {@value YMisureValFarfMatricolateTM#TABLE_NAME}.<br>
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 14/06/2024
 * <br><br>
 * <b>71553	DSSOF3	14/06/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YImportazioneMisureValvFarfBatch extends BatchRunnable implements Authorizable {

	protected String iIdAzienda;

	protected Proxy iFornitore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);

	protected Proxy iMatricolaInizio = new Proxy(it.valvorobica.thip.base.matricole.YMatricolaValvo.class);

	protected Proxy iMatricolaFine = new Proxy(it.valvorobica.thip.base.matricole.YMatricolaValvo.class);

	protected String iTemporaryFileName;

	protected File file = null;

	public String getTemporaryFileName() {
		return iTemporaryFileName;
	}

	public void setTemporaryFileName(String iTemporaryFileName) {
		this.iTemporaryFileName = iTemporaryFileName;
	}

	public YImportazioneMisureValvFarfBatch() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iFornitore.setKey(KeyHelper.replaceTokenObjectKey(iFornitore.getKey(), 1, idAzienda));
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
		setIdAziendaInternal(iIdAzienda);
	}

	public void setFornitore(FornitoreAcquisto Fornitore) {
		this.iFornitore.setObject(Fornitore);
	}

	public FornitoreAcquisto getFornitore() {
		return (FornitoreAcquisto) iFornitore.getObject();
	}

	public void setFornitoreKey(String key) {
		iFornitore.setKey(key);
	}

	public String getFornitoreKey() {
		return iFornitore.getKey();
	}

	public void setIdFornitore(String rFornitore) {
		String key = iFornitore.getKey();
		iFornitore.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rFornitore));
	}

	public String getIdFornitore() {
		String key = iFornitore.getKey();
		String objRFornitore = KeyHelper.getTokenObjectKey(key, 2);
		return objRFornitore;
	}

	// Inizio Matricola Da
	public void setMatricolaInizio(YMatricolaValvo Fornitore) {
		this.iMatricolaInizio.setObject(Fornitore);
	}

	public YMatricolaValvo getMatricolaInizio() {
		return (YMatricolaValvo) iMatricolaInizio.getObject();
	}

	public void setMatricolaInizioKey(String key) {
		iMatricolaInizio.setKey(key);
	}

	public String getMatricolaInizioKey() {
		return iMatricolaInizio.getKey();
	}

	public void setIdMatricolaDA(String rFornitore) {
		String key = iMatricolaInizio.getKey();
		iMatricolaInizio.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rFornitore));
	}

	public String getIdMatricolaDA() {
		String key = iMatricolaInizio.getKey();
		String objRFornitore = KeyHelper.getTokenObjectKey(key, 2);
		return objRFornitore;
	}
	// Fine Matricola Da

	// Inizio Matricola A
	public void setMatricolaFine(YMatricolaValvo Fornitore) {
		this.iMatricolaFine.setObject(Fornitore);
	}

	public YMatricolaValvo getMatricolaFine() {
		return (YMatricolaValvo) iMatricolaFine.getObject();
	}

	public void setMatricolaFineKey(String key) {
		iMatricolaFine.setKey(key);
	}

	public String getMatricolaFineKey() {
		return iMatricolaFine.getKey();
	}

	public void setIdMatricolaA(String rFornitore) {
		String key = iMatricolaFine.getKey();
		iMatricolaFine.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rFornitore));
	}

	public String getIdMatricolaA() {
		String key = iMatricolaFine.getKey();
		String objRFornitore = KeyHelper.getTokenObjectKey(key, 2);
		return objRFornitore;
	}
	// Fine Matricola A

	/**
	 * Holda la lista delle matricole del range scelto dall'utente a video.<br>
	 */
	@SuppressWarnings("rawtypes")
	private Vector listaMatricole = null; 

	@Override
	protected boolean run() {
		listaMatricole = YMatricolaValvo.recuperaMatricoleByRange(getIdAzienda(), getIdMatricolaDA(), getIdMatricolaA());
		boolean isOk = processaFile();
		return isOk;
	}

	@SuppressWarnings("unchecked")
	protected boolean processaFile() {
		boolean isOk = true;
		output.println("** INIZIO L'IMPORTAZIONE DELLE VALVOLE FARFALLA NEW **");
		List<DataRow> disc = null;
		List<DataRow> shaft = null;
		List<DataRow> discInox = null;
		List<DataRow> body = null;
		output.println("--> Leggo il file e estrapolo {DISC,SHAFT,BODY,DISC INOX} ...");
		try (FileInputStream fileInputStream = new FileInputStream(getTemporaryFileName());
				Workbook workbook = WorkbookFactory.create(fileInputStream)) {
			Sheet discSheet = workbook.getSheet("DI DISC");
			if(discSheet == null) {
				output.println(" ## Attenzione, nel file excel manca il foglio 'DI DISC', controllare!");
				return false;
			}
			disc = leggiSheet(workbook, discSheet);
			Sheet shaftSheet = workbook.getSheet("SHAFT");
			if(shaftSheet == null) {
				output.println(" ## Attenzione, nel file excel manca il foglio 'SHAFT', controllare!");
				return false;
			}
			shaft = leggiSheet(workbook, shaftSheet);
			Sheet bodySheet = workbook.getSheet("BODY");
			if(bodySheet == null) {
				output.println(" ## Attenzione, nel file excel manca il foglio 'BODY', controllare!");
				return false;
			}
			body = leggiSheet(workbook, bodySheet);
			Sheet discInoxSheet = workbook.getSheet("X DISC");
			if(discInoxSheet == null) {
				output.println(" ## Attenzione, nel file excel manca il foglio 'X DISC', controllare!");
				return false;
			}
			discInox = leggiSheet(workbook, discInoxSheet);
		} catch (IOException e) {
			e.printStackTrace(Trace.excStream);
		} catch (EncryptedDocumentException e1) {
			e1.printStackTrace(Trace.excStream);
		} catch (InvalidFormatException e1) {
			e1.printStackTrace(Trace.excStream);
		}
		output.println("--> Inizio fase di processo matricola...");
		for(int i = 0; i < listaMatricole.size(); i++) {
			YMatricolaValvo matricola = (YMatricolaValvo) listaMatricole.get(i);
			//output.println("  --Inizio processo Matricola: "+matricola.getKey());
			DataRow bodyRow = getDataRowFromId("HEAT NO.", matricola.getBody(), body);
			if(bodyRow != null) {
				if(matricola.getMatchArticolo() == null) {
					output.println("  ##Per la matricola {"+matricola.getIdMatricola()+"} non esiste un match, la matricola viene saltata");
				}else {
					YMisureValFarfMatricolate misura = null;
					try {
						misura = YMisureValFarfMatricolate.misuraValvolaFarfallaDaRow(getIdFornitore(),
								matricola.getMatchArticolo(), matricola, bodyRow);
						try {
							if(misura.retrieve())
								continue;
						} catch (SQLException e) {
							e.printStackTrace(Trace.excStream);
						}
					}catch (ExcelFieldFormatException e) {
						isOk = false;
						output.println("  ##Errore nella formattazione di un numero, matricola {"+matricola.getIdMatricola()+"} nel foglio 'BODY',reason:"+e.getMessage());
						continue;
					}
					DataRow discRow = null;
					if(matricola.getMatchArticolo().getIdArticolo().contains("X")) //se termina con la X allora inox
						discRow = getDataRowFromId("HEAT NO.", matricola.getDisc(), discInox);
					else
						discRow = getDataRowFromId("HEAT NO.", matricola.getDisc(), disc);
					YMisureValFarfCompMatricolata discComp = null;
					if(discRow != null) {
						try {
							discComp = YMisureValFarfCompMatricolata.misuraValvolaFarfallaCompDaRow(misura, discRow, "001");
						}catch (ExcelFieldFormatException e) {
							String foglio = matricola.getMatchArticolo().getIdArticolo().contains("X") ? "DISC INOX" : "DISC";
							output.println("  ##Errore nella formattazione di un numero, matricola {"+matricola.getIdMatricola()+"} nel foglio '"+foglio+"',reason:"+e.getMessage());
							continue;
						}
					}else {
						output.println(" ##Per la matricola {"+matricola.getIdMatricola()+"} non e' stato trovato il DISC corrispondente "
								+ ", cercato con DISC = ["+matricola.getDisc()+"]");
						isOk = false;
					}
					DataRow shaftRow = getDataRowFromId("HEAT NO.", matricola.getShaft(), shaft);
					YMisureValFarfCompMatricolata discShaft  = null;
					if(shaftRow != null) {
						try {
							discShaft = YMisureValFarfCompMatricolata.misuraValvolaFarfallaCompDaRow(misura, shaftRow, "002");
						}catch (ExcelFieldFormatException e) {
							output.println("  ##Errore nella formattazione di un numero, matricola {"+matricola.getIdMatricola()+"} nel foglio 'SHAFT',reason:"+e.getMessage());
							continue;
						}
					}else {
						output.println(" ##Per la matricola {"+matricola.getIdMatricola()+"} non e' stato trovato il SHAFT corrispondente "
								+ ", cercato con SHAFT = ["+matricola.getShaft()+"]");
						isOk = false;
					}
					if(discComp != null)
						misura.getYMisureValvFarfComp().add(discComp);
					if(discShaft != null)
						misura.getYMisureValvFarfComp().add(discShaft);
					try {
						misura.save();
					} catch (SQLException e) {
						output.println("  ##Errore nel salvataggio della misura farf, reason:"+e.getMessage());
						e.printStackTrace(Trace.excStream);
					}
				}
			}else {
				output.println("Nel foglio BODY non e' stata trovata nessuna riga con HEAT NO. = "+matricola.getBody());
			}
			//output.println("Termine processo Matricola: "+matricola.getKey()+" --  ");
		}
		try {
			if(isOk) {
				ConnectionManager.commit();
			}else {
				ConnectionManager.rollback();
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return isOk;
	}

	private DataRow getDataRowFromId(String header, String id, List<DataRow> rows) {
		for (DataRow row : rows) {
			if (id.equals(row.getValue(header))) {
				return row;
			}
		}
		return null;
	}

	public List<DataRow> leggiSheet(Workbook workbook,Sheet sheet){
		int lastRowNum = sheet.getLastRowNum();
		if (lastRowNum < 4) {
			Trace.excStream.println("The sheet does not contain enough rows.");
			return null;
		}
		List<String> headers = new ArrayList<>();
		for (int colNum = 0; colNum < sheet.getRow(0).getLastCellNum(); colNum++) {
			StringBuilder header = new StringBuilder();
			for (int rowNum = 0; rowNum < 4; rowNum++) {
				Row headerRow = sheet.getRow(rowNum);
				Cell headerCell = headerRow.getCell(colNum);
				if (headerCell != null) {
					header.append(headerCell.getStringCellValue()).append(" ");
				}
			}
			headers.add(header.toString().trim());
		}

		// Read the data rows
		List<DataRow> dataRows = new ArrayList<>();
		for (int rowNum = 4; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null) continue;

			Map<String, String> dataMap = new LinkedHashMap<>();
			for (int colNum = 0; colNum < headers.size(); colNum++) {
				Cell cell = row.getCell(colNum);
				String value = "";
				if (cell != null) {
					switch (cell.getCellTypeEnum()) {
					case STRING:
						value = cell.getStringCellValue();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							value = cell.getDateCellValue().toString();
						} else {
							value = Double.toString(cell.getNumericCellValue());
						}
						break;
					case BOOLEAN:
						value = Boolean.toString(cell.getBooleanCellValue());
						break;
					case FORMULA:
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						CellValue cellValue = evaluator.evaluate(cell);
						switch (cellValue.getCellTypeEnum()) {
						case STRING:
							value = cellValue.getStringValue();
							break;
						case NUMERIC:
							value = Double.toString(cellValue.getNumberValue());
							break;
						case BOOLEAN:
							value = Boolean.toString(cellValue.getBooleanValue());
							break;
						default:
							break;
						}
						break;
					default:
						value = "Unknown Cell Type";
					}
				}
				String header = headers.get(colNum);
				if(header.contains("HEAT")) { //normalizzo le colate che non hanno punti
					if(value.contains("."))
						value = value.substring(0,value.indexOf("."));
				}
				dataMap.put(headers.get(colNum), value);
			}
			dataRows.add(new DataRow(dataMap));
		}
		return dataRows;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YImportMisureVF";
	}

	public class DataRow {
		public Map<String, String> data;

		public DataRow(Map<String, String> data) {
			this.data = data;
		}

		public String getValue(String header) {
			return data.get(header);
		}

		public void setValue(String header, String value) {
			data.put(header, value);
		}

		public String getValueAtPosition(int position) {
			if (position < 0 || position >= data.size()) {
				return null;
			}
			return data.entrySet().stream()
					.skip(position)
					.findFirst()
					.map(Map.Entry::getValue)
					.orElse(null);
		}

		@Override
		public String toString() {
			return "DataRow{" +
					"data=" + data +
					'}';
		}
	}

}
