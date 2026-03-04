package it.valvorobica.thip.base.portal;

import com.thera.thermfw.persist.*;
import java.sql.*;
import com.thera.thermfw.base.*;

/**
 * <h1>Softre Solutions</h1>
 * @author Andrea Gatta 24/03/2023
 * <br><br>
 * <b>71008	AGSOF3	24/03/2023</b>	<p>Nuova anagrafica personalizzata per il login nel portale</p>
 * <b>71162	DSSOF3	04/07/2023</b>	<p>Aggiunto fornitore</p>
 */

public class YAziendeUserPortalTM extends TableManager {

	public static final String ID_UTENTE = "ID_UTENTE";

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String TIPO_USER = "TIPO_USER";

	public static final String R_AGENTE = "R_AGENTE";

	public static final String R_CLIENTE = "R_CLIENTE";

	public static final String R_DIPENDENTE = "R_DIPENDENTE";

	public static final String R_UTENTE_PTH = "R_UTENTE_PTH";
	
	public static final String R_FORNITORE = "R_FORNITORE";
	
	public static final String E_COMMERCE = "E_COMMERCE";
	
	public static final String GES_CONTO_DEP = "GES_CONTO_DEP";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YAZIENDE_USER_PORTAL";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.base.portal.YAziendeUserPortal.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager)Factory.createObject(YAziendeUserPortalTM.class);
		}
		return cInstance;
	}

	public YAziendeUserPortalTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("TipoUser", TIPO_USER);
		addAttribute("IdUtente", ID_UTENTE);
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("RAgente", R_AGENTE);
		addAttribute("RCliente", R_CLIENTE);
		addAttribute("RDipendente", R_DIPENDENTE);
		addAttribute("RUtentePth", R_UTENTE_PTH);
		addAttribute("RFornitore",R_FORNITORE);
		
		addAttribute("ECommerce", E_COMMERCE);
		
		addAttribute("GestioneContoDep", GES_CONTO_DEP);
		
		setKeys(ID_UTENTE + "," + ID_AZIENDA);
	}

	private void init() throws SQLException {
		configure();
	}

}