package it.valvorobica.thip.susa;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

public class YRptSegnacolloSusaTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String BATCH_JOB_ID = "BATCH_JOB_ID";

	public static final String REPORT_NR = "REPORT_NR";

	public static final String RIGA_JOB_ID = "RIGA_JOB_ID";

	public static final String CODICE_LINEA = "CODICE_LINEA";

	public static final String CODICE_ZONA = "CODICE_ZONA";

	public static final String COD_UDS = "COD_UDS";

	public static final String VARCHAR01 = "VARCHAR01";

	public static final String VARCHAR02 = "VARCHAR02";

	public static final String VARCHAR03 = "VARCHAR03";

	public static final String VARCHAR04 = "VARCHAR04";

	public static final String VARCHAR05 = "VARCHAR05";

	public static final String FLAG01 = "FLAG01";

	public static final String FLAG02 = "FLAG02";

	public static final String FLAG03 = "FLAG03";

	public static final String FLAG04 = "FLAG04";

	public static final String FLAG05 = "FLAG05";

	public static final String DECIMAL01 = "DECIMAL01";

	public static final String DECIMAL02 = "DECIMAL02";

	public static final String DECIMAL03 = "DECIMAL03";

	public static final String DECIMAL04 = "DECIMAL04";

	public static final String DECIMAL05 = "DECIMAL05";

	public static final String INTEGER01 = "INTEGER01";

	public static final String INTEGER02 = "INTEGER02";

	public static final String INTEGER03 = "INTEGER03";

	public static final String INTEGER04 = "INTEGER04";

	public static final String INTEGER05 = "INTEGER05";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "RPT_SEGNACOLLO_SUSA";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.susa.YRptSegnacolloSusa.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YRptSegnacolloSusaTM.class);
		}
		return cInstance;
	}

	public YRptSegnacolloSusaTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("BatchJobId", BATCH_JOB_ID, "getIntegerObject");
		addAttribute("ReportNr", REPORT_NR, "getIntegerObject");
		addAttribute("RigaJobId", RIGA_JOB_ID, "getIntegerObject");
		addAttribute("CodiceLinea", CODICE_LINEA);
		addAttribute("CodiceZona", CODICE_ZONA);
		addAttribute("CodUds", COD_UDS);
		addAttribute("Varchar01", VARCHAR01);
		addAttribute("Varchar02", VARCHAR02);
		addAttribute("Varchar03", VARCHAR03);
		addAttribute("Varchar04", VARCHAR04);
		addAttribute("Varchar05", VARCHAR05);
		addAttribute("Flag01", FLAG01);
		addAttribute("Flag02", FLAG02);
		addAttribute("Flag03", FLAG03);
		addAttribute("Flag04", FLAG04);
		addAttribute("Flag05", FLAG05);
		addAttribute("Decimal01", DECIMAL01);
		addAttribute("Decimal02", DECIMAL02);
		addAttribute("Decimal03", DECIMAL03);
		addAttribute("Decimal04", DECIMAL04);
		addAttribute("Decimal05", DECIMAL05);
		addAttribute("Integer01", INTEGER01, "getIntegerObject");
		addAttribute("Integer02", INTEGER02, "getIntegerObject");
		addAttribute("Integer03", INTEGER03, "getIntegerObject");
		addAttribute("Integer04", INTEGER04, "getIntegerObject");
		addAttribute("Integer05", INTEGER05, "getIntegerObject");
		addAttribute("IdAzienda", ID_AZIENDA);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + BATCH_JOB_ID + "," + REPORT_NR + "," + RIGA_JOB_ID);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure();
	}

}
