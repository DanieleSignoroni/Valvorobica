package it.valvorobica.thip.base.portal;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 22/01/2024
 * <br><br>
 * <b>71392	DSSOF3	22/01/2024</b>    <p>Prima stesura</p>
 */


public class YImgCatalogoPortaleTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String TIPO_CLASSIFICAZIONE = "TIPO_CLASSIFICAZIONE";

	public static final String ID_CLASSIFICAZIONE = "ID_CLASSIFICAZIONE";

	public static final String URL_IMG = "URL_IMG";

	public static final String COLONNE_DA_OCCUPARE = "COLONNE_DA_OCCUPARE";
	
	public static final String DESCRIZIONE = "DESCRIZIONE";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YIMG_CATALOGO_PORTALE";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.base.portal.YImgCatalogoPortale.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YImgCatalogoPortaleTM.class);
		}
		return cInstance;
	}

	public YImgCatalogoPortaleTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("TipoClassificazione", TIPO_CLASSIFICAZIONE);
		addAttribute("IdClassificazione", ID_CLASSIFICAZIONE);
		addAttribute("UrlImg", URL_IMG);
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("Colonne", COLONNE_DA_OCCUPARE, "getIntegerObject");
		
		addAttribute("Descrizione", DESCRIZIONE);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + TIPO_CLASSIFICAZIONE + "," + ID_CLASSIFICAZIONE);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure();
	}

}
