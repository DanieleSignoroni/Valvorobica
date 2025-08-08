package it.valvorobica.thip.susa;

import com.thera.thermfw.persist.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 08/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    08/08/2025  DSSOF3   Prima stesura
 */

public class CapparioSusaTM extends TableManager {


	/**
	 * Attributo CAP
	 */
	public static final String CAP = "CAP";

	/**
	 * Attributo LOCALITA
	 */
	public static final String LOCALITA = "LOCALITA";

	/**
	 * Attributo CODICE_INSTRADAMENTO
	 */
	public static final String CODICE_INSTRADAMENTO = "CODICE_INSTRADAMENTO";

	/**
	 * Attributo ID_PROVINCIA
	 */
	public static final String ID_PROVINCIA = "ID_PROVINCIA";

	/**
	 * Attributo STATO
	 */
	public static final String STATO = "STATO";

	/**
	 * Attributo R_UTENTE_CRZ
	 */
	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	/**
	 * Attributo TIMESTAMP_CRZ
	 */
	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	/**
	 * Attributo R_UTENTE_AGG
	 */
	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	/**
	 * Attributo TIMESTAMP_AGG
	 */
	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	/**
	 *  TABLE_NAME
	 */
	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "CAPPARIO_SUSA";

	/**
	 *  instance
	 */
	private static TableManager cInstance;

	/**
	 *  CLASS_NAME
	 */
	private static final String CLASS_NAME = it.valvorobica.thip.susa.CapparioSusa.class.getName();


	/**
	 *  getInstance
	 * @return TableManager
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager)Factory.createObject(CapparioSusaTM.class);
		}
		return cInstance;
	}

	/**
	 *  CapparioSusaTM
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	public CapparioSusaTM() throws SQLException {
		super();
	}

	/**
	 *  initialize
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	/**
	 *  initializeRelation
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("Cap", CAP);
		addAttribute("Localita", LOCALITA);
		addAttribute("CodiceInstradamento", CODICE_INSTRADAMENTO);
		addAttribute("IdProvincia", ID_PROVINCIA);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(CAP + "," + LOCALITA);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM)getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	/**
	 *  init
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	private void init() throws SQLException {
		configure(CAP + ", " + LOCALITA + ", " + CODICE_INSTRADAMENTO + ", " + ID_PROVINCIA
				+ ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG
				+ ", " + TIMESTAMP_AGG);
	}

}

