package it.valvorobica.thip.base;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

/**
 * <h1>Softre Solutions</h1>
 * 
 * @author Daniele Signoroni 14/04/2023 <br>
 *         <br>
 *         <b>71045 DSSOF3 14/04/2023</b>
 *         <p>
 *         Prima stesura.
 *         </p>
 */

public class YMisureValFarfNewTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String ID_FORNITORE = "ID_FORNITORE";

	public static final String ID_ARTICOLO = "ID_ARTICOLO";

	public static final String R_NORMATIVA = "R_NORMATIVA";

	public static final String R_PROVENIENZA = "R_PROVENIENZA";

	public static final String MAN_STAMP = "MAN_STAMP";

	public static final String PERC_C = "PERC_C";

	public static final String PERC_MN = "PERC_MN";

	public static final String PERC_SI = "PERC_SI";

	public static final String PERC_P = "PERC_P";

	public static final String PERC_S = "PERC_S";

	public static final String PERC_NI = "PERC_NI";

	public static final String PERC_CR = "PERC_CR";

	public static final String PERC_MO = "PERC_MO";

	public static final String PERC_TI = "PERC_TI";

	public static final String PERC_AL = "PERC_AL";

	public static final String PERC_V = "PERC_V";

	public static final String PERC_CU = "PERC_CU";

	public static final String PERC_N = "PERC_N";

	public static final String PERC_NB = "PERC_NB";

	public static final String PERC_PB = "PERC_PB";

	public static final String PERC_FE = "PERC_FE";

	public static final String PERC_CE = "PERC_CE";

	public static final String PERC_RE = "PERC_RE";

	public static final String PERC_MG = "PERC_MG";

	public static final String PERC_SN = "PERC_SN";

	public static final String PERC_B = "PERC_B";

	public static final String PERC_ZN = "PERC_ZN";

	public static final String ROTTURA_MPA = "ROTTURA_MPA";

	public static final String SNERV_MPA = "SNERV_MPA";

	public static final String PERC_ALLUNG = "PERC_ALLUNG";

	public static final String PERC_CONTRAZ = "PERC_CONTRAZ";

	public static final String SAMPLE_DIR_T = "SAMPLE_DIR_T";

	public static final String SAMPLE_DIM_T = "SAMPLE_DIM_T";

	public static final String TRATTAMENTO_T = "TRATTAMENTO_T";

	public static final String TEMP_TT = "TEMP_TT";

	public static final String SAMPLE_DIR_R = "SAMPLE_DIR_R";

	public static final String SAMPLE_DIM_R = "SAMPLE_DIM_R";

	public static final String TEMP_RE = "TEMP_RE";

	public static final String TEST1 = "TEST1";

	public static final String TEST2 = "TEST2";

	public static final String TEST3 = "TEST3";

	public static final String NACE = "NACE";

	public static final String DUREZZA_HB = "DUREZZA_HB";

	public static final String MILL = "MILL";

	public static final String ADDITIONAL = "ADDITIONAL";

	public static final String TESTREPORT = "TESTREPORT";

	public static final String LOTTO_PROD = "LOTTO_PROD";

	public static final String ID_LOTTO = "ID_LOTTO";

	public static final String ID_MATRICOLA = "ID_MATRICOLA";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YMISURE_VAL_FARF_NEW";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.valvorobica.thip.base.YMisureValFarfNew.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YMisureValFarfNewTM.class);
		}
		return cInstance;
	}

	public YMisureValFarfNewTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("ManStamp", MAN_STAMP);
		addAttribute("PercC", PERC_C);
		addAttribute("PercMn", PERC_MN);
		addAttribute("PercSi", PERC_SI);
		addAttribute("PercP", PERC_P);
		addAttribute("PercS", PERC_S);
		addAttribute("PercNi", PERC_NI);
		addAttribute("PercCr", PERC_CR);
		addAttribute("PercMo", PERC_MO);
		addAttribute("PercTi", PERC_TI);
		addAttribute("PercAl", PERC_AL);
		addAttribute("PercV", PERC_V);
		addAttribute("PercCu", PERC_CU);
		addAttribute("PercN", PERC_N);
		addAttribute("PercNb", PERC_NB);
		addAttribute("PercPb", PERC_PB);
		addAttribute("PercFe", PERC_FE);
		addAttribute("PercCe", PERC_CE);
		addAttribute("PercRe", PERC_RE);
		addAttribute("PercMg", PERC_MG);
		addAttribute("PercSn", PERC_SN);
		addAttribute("PercB", PERC_B);
		addAttribute("PercZn", PERC_ZN);
		addAttribute("RotturaMpa", ROTTURA_MPA, "getIntegerObject");
		addAttribute("SnervMpa", SNERV_MPA, "getIntegerObject");
		addAttribute("PercAllung", PERC_ALLUNG);
		addAttribute("PercContraz", PERC_CONTRAZ);
		addAttribute("SampleDirT", SAMPLE_DIR_T);
		addAttribute("SampleDimT", SAMPLE_DIM_T);
		addAttribute("TrattamentoT", TRATTAMENTO_T);
		addAttribute("TempTt", TEMP_TT, "getIntegerObject");
		addAttribute("SampleDirR", SAMPLE_DIR_R);
		addAttribute("SampleDimR", SAMPLE_DIM_R);
		addAttribute("TempRe", TEMP_RE, "getIntegerObject");
		addAttribute("Test1", TEST1, "getIntegerObject");
		addAttribute("Test2", TEST2, "getIntegerObject");
		addAttribute("Test3", TEST3, "getIntegerObject");
		addAttribute("Nace", NACE);
		addAttribute("DurezzaHb", DUREZZA_HB);
		addAttribute("Mill", MILL);
		addAttribute("Additional", ADDITIONAL);
		addAttribute("Testreport", TESTREPORT);
		addAttribute("LottoProd", LOTTO_PROD);
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("IdFornitore", ID_FORNITORE);
		addAttribute("IdArticolo", ID_ARTICOLO);
		addAttribute("RNormativa", R_NORMATIVA);
		addAttribute("RProvenienza", R_PROVENIENZA);
		addAttribute("IdLotto", ID_LOTTO);
		addAttribute("IdMatricola", ID_MATRICOLA);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + ID_FORNITORE + "," + ID_ARTICOLO + "," + ID_LOTTO + "," + ID_MATRICOLA);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure();
	}

}
