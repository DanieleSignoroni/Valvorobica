package it.valvorobica.thip.vendite.documentoVE.brt;

import com.thera.thermfw.persist.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

public class EtichetteBartoliniTM extends TableManager {
	
	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String ID_ANNO_DDT = "ID_ANNO_DDT";

	public static final String ID_NUMERO_DDT = "ID_NUMERO_DDT";

	public static final String TIPO_DDT = "TIPO_DDT";

	public static final String ID = "ID";

	public static final String ETICHETTA_BASE_64 = "ETICHETTA_BASE_64";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "ETICHETTE_BARTOLINI";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.vendite.documentoVE.brt.EtichetteBartolini.class.getName();


	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager)Factory.createObject(EtichetteBartoliniTM.class);
		}
		return cInstance;
	}

	public EtichetteBartoliniTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("Id", ID, "getIntegerObject");

		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("AnnoDDT", ID_ANNO_DDT);
		addAttribute("NumeroDDT", ID_NUMERO_DDT);
		addAttribute("TipoDDT", TIPO_DDT);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + ID);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM)getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure();
	}

}

