package it.valvorobica.thip.base.matricole;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

public class YMatchMatricolaArticoloTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String ID_ARTICOLO = "ID_ARTICOLO";

	public static final String ID_LOTTO = "ID_LOTTO";

	public static final String ID_MATRICOLA_DA = "ID_MATRICOLA_DA";

	public static final String ID_MATRICOLA_A = "ID_MATRICOLA_A";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YMATCH_MATRICOLA_ARTICOLO";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.base.matricole.YMatchMatricolaArticolo.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YMatchMatricolaArticoloTM.class);
		}
		return cInstance;
	}

	public YMatchMatricolaArticoloTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("IdArticolo", ID_ARTICOLO);
		addAttribute("IdMatricolaDa", ID_MATRICOLA_DA);
		addAttribute("IdMatricolaA", ID_MATRICOLA_A);
		addAttribute("IdLotto", ID_LOTTO);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + ID_ARTICOLO + "," + ID_MATRICOLA_DA + "," + ID_MATRICOLA_A);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure(ID_AZIENDA + ", " + ID_ARTICOLO + ", " + ID_MATRICOLA_DA + ", " + ID_MATRICOLA_A + ", " + ID_LOTTO
				+ ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", "
				+ TIMESTAMP_AGG);
	}

}
