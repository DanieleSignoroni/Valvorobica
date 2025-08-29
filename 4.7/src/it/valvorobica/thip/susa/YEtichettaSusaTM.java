package it.valvorobica.thip.susa;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

public class YEtichettaSusaTM extends TableManager {

	public static final String COD_UDS = "COD_UDS";

	public static final String ETICHETTA_BASE_64 = "ETICHETTA_BASE_64";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "ETICHETTE_SUSA";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.susa.YEtichettaSusa.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YEtichettaSusaTM.class);
		}
		return cInstance;
	}

	public YEtichettaSusaTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();

		addAttribute("CodUds", COD_UDS);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(COD_UDS);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure(ETICHETTA_BASE_64 + ", " + COD_UDS + ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", "
				+ R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
	}

}
