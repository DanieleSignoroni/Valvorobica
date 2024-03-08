package it.valvorobica.thip.magazzino.generalemag;

import java.sql.*;
import it.thera.thip.magazzino.generalemag.*;
import com.thera.thermfw.base.*;

/**
 * <h1>Softre Solutions</h1>
 * @author Daniele Signoroni 05/09/2022
 * <br></br>	
 *	<b>70649	DSSOF3	05/09/2022</b>	<p>Entità generata da Alex.</p>
 *	<b>70858	AGSOF3	12/01/2023</b>	<p>aggiunta sigla</p>
 *	<b>71406	DSSOF3	31/01/2024</b>
 *  <p>
 *  Aggiunta colonne:<br>
 *  <ul>
 *  	<li>{@link #NUMERO_BOLLA_DOGANALE}</li>
 *  	<li>{@link #DATA_BOLLA_DOGANALE}</li>
 *  	<li>{@link #DOGANA}</li>
 *  </ul>
 *  </p>
 *	<b>71448	DSSOF3	28/02/2024</b>
 *  <p>
 *  Aggiunta colonne:<br>
 *  <ul>
 *  	<li>{@link #PRODUTTORE}</li>
 *  	<li>{@link #CITTA_DI_PRODUZIONE}</li>
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

	//Inizio 71448

	public static final String PRODUTTORE = "PRODUTTORE";

	public static final String CITTA_DI_PRODUZIONE = "CITTA_DI_PRODUZIONE";

	//Fine 71448
	
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
		//Inizio 71448
		addAttributeOnTable("Produttore", PRODUTTORE, TABLE_NAME_EXT);
		addAttributeOnTable("CittaDiProduzione", CITTA_DI_PRODUZIONE, TABLE_NAME_EXT);
		
		addAttributeOnTable("CertificatiDaPortale", CERTIFICATI_DA_PORTALE, TABLE_NAME_EXT);
	}

}

