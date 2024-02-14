package it.valvorobica.thip.magazzino.generalemag;

import java.sql.*;
import it.thera.thip.magazzino.generalemag.*;
import com.thera.thermfw.base.*;

/**
 * 
 * @author daniele.signoroni	
 *	70649	DSSOF3	05/09/2022	Entità generata da Alex.
 *	70858	AGSOF3	12/01/2023	aggiunta sigla	
 *	<b>71406	DSSOF3	31/01/2024</b>
 *  <p>
 *  Aggiunta colonne:<br>
 *  <ul>
 *  	<li>{@link #NUMERO_BOLLA_DOGANALE}</li>
 *  	<li>{@link #DATA_BOLLA_DOGANALE}</li>
 *  	<li>{@link #DOGANA}</li>
 *  </ul>
 *  </p>
 */

public class YLottoTM extends LottoTM {

	public static final String NAZIONEOR = "NAZIONEOR";

	public static final String SIGLA = "SIGLA";

	//Inizio

	public static final String NUMERO_BOLLA_DOGANALE = "NUMERO_BOLLA_DOGANALE";

	public static final String DATA_BOLLA_DOGANALE = "DATA_BOLLA_DOGANALE";

	public static final String DOGANA = "DOGANA";

	//Fine
	
	public static final String CERTIFICATI_DA_PORTALE = "CERTIFICATI_DA_PORTALE";

	public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YLOTTI";

	private static final String CLASS_NAME = it.valvorobica.thip.magazzino.generalemag.YLotto.class.getName();

	public YLottoTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		super.initialize();
		setObjClassName(CLASS_NAME);
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		linkTable(TABLE_NAME_EXT);
		addAttributeOnTable("Nazioneor", NAZIONEOR, TABLE_NAME_EXT);
		addAttributeOnTable("IdSigla", SIGLA,TABLE_NAME_EXT);
		//Inizio
		addAttributeOnTable("NumeroBollaDoganale", NUMERO_BOLLA_DOGANALE, TABLE_NAME_EXT);
		addAttributeOnTable("DataBollaDoganale", DATA_BOLLA_DOGANALE, TABLE_NAME_EXT);
		addAttributeOnTable("Dogana", DOGANA, TABLE_NAME_EXT);
		addAttributeOnTable("CertificatiDaPortale", CERTIFICATI_DA_PORTALE, TABLE_NAME_EXT);
	}

}

