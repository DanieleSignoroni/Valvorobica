package it.valvorobica.thip.base.firmadigitale;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 30/01/2025
 * <br><br>
 * <b>71805	DSSOF3	30/01/2025</b>
 * <p></p>
 */

public class YFirmaDigitVettoriEsclusiTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String R_VETTORE = "R_VETTORE";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YFIRMA_DIG_VETTORI_ESCLUSI";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.base.firmadigitale.YFirmaDigitVettoriEsclusi.class
			.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YFirmaDigitVettoriEsclusiTM.class);
		}
		return cInstance;
	}

	public YFirmaDigitVettoriEsclusiTM() throws SQLException {
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
		addAttribute("IdVettore", R_VETTORE);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + R_VETTORE);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure();
	}

}
