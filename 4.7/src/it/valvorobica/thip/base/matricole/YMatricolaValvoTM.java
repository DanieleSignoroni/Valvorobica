package it.valvorobica.thip.base.matricole;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

public class YMatricolaValvoTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String ID_MATRICOLA = "ID_MATRICOLA";

	public static final String BODY = "BODY";

	public static final String DISC = "DISC";

	public static final String SHAFT = "SHAFT";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YMATRICOLA_VALVO";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.base.matricole.YMatricolaValvo.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YMatricolaValvoTM.class);
		}
		return cInstance;
	}

	public YMatricolaValvoTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("IdMatricola", ID_MATRICOLA);
		addAttribute("Body", BODY);
		addAttribute("Disc", DISC);
		addAttribute("Shaft", SHAFT);
		addAttribute("IdAzienda", ID_AZIENDA);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + ID_MATRICOLA);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure(ID_MATRICOLA + ", " + BODY + ", " + DISC + ", " + SHAFT + ", " + ID_AZIENDA + ", " + STATO + ", "
				+ R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
	}

}
