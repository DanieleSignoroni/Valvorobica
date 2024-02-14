package it.valvorobica.thip.base.fornitore;

import java.sql.*;
import it.thera.thip.base.fornitore.*;
import com.thera.thermfw.base.*;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 20/10/2023
 * <br><br>
 * <b>71268	DSSOF3	20/10/2023</b>	Aggiunto colonna {@link #STAMPA_AUTOM_DDT_TERMINALINO} per s/bloccare la stampa automatica da terminalino.<br></br>
 * <b>XXXXX	DSSOF3	13/02/2024</b>
 * <p>
 * Aggiungere colonna {@value #CERTIFICATI_DA_PORTALE}
 * </p>
 */

public class YFornitoreAcquistoTM extends FornitoreAcquistoTM {

	public static final String STAMPA_AUTOM_DDT_TERMINALINO = "STAMPA_AUTOM_DDT_TERMINALINO";
	
	public static final String CERTIFICATI_DA_PORTALE = "CERTIFICATI_DA_PORTALE";

	public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YFORNITORI_ACQ";

	private static final String CLASS_NAME = it.valvorobica.thip.base.fornitore.YFornitoreAcquisto.class.getName();

	public YFornitoreAcquistoTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		super.initialize();
		setObjClassName(CLASS_NAME);
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		linkTable(TABLE_NAME_EXT);
		addAttributeOnTable("StampaAutomTerminalinoDDT", STAMPA_AUTOM_DDT_TERMINALINO, TABLE_NAME_EXT);
		addAttributeOnTable("CertificatiDaPortale", CERTIFICATI_DA_PORTALE, TABLE_NAME_EXT);
		

	}

}

