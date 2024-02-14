package it.valvorobica.thip.base.articolo;

import java.sql.*;
import it.thera.thip.base.articolo.*;
import com.thera.thermfw.base.*;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 13/02/2024
 * <br><br>
 * <b>XXXXX	DSSOF3	13/02/2024</b>
 * <p>
 * Aggiungere colonna {@value #CERTIFICATI_DA_PORTALE}
 * </p>
 */

public class YMicroFamigliaTM extends MicroFamigliaTM {

	public static final String CERTIFICATI_DA_PORTALE = "CERTIFICATI_DA_PORTALE";

	public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YMICROFAMIGLIE";

	private static final String CLASS_NAME = it.valvorobica.thip.base.articolo.YMicroFamiglia.class.getName();

	public YMicroFamigliaTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		super.initialize();
		setObjClassName(CLASS_NAME);
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		linkTable(TABLE_NAME_EXT);
		addAttributeOnTable("CertificatiDaPortale", CERTIFICATI_DA_PORTALE, TABLE_NAME_EXT);

	}

}

