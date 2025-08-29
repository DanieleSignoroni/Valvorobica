/*
 * @(#)YRptSegnacolloSusaTM.java
 */

/**
 * YRptSegnacolloSusaTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 29/08/2025 at 10:43:09
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 29/08/2025    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.susa;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

public class YRptSegnacolloSusaTM extends TableManager {

  
  /**
   * Attributo ID_AZIENDA
   */
  public static final String ID_AZIENDA = "ID_AZIENDA";

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
   * Attributo BATCH_JOB_ID
   */
  public static final String BATCH_JOB_ID = "BATCH_JOB_ID";

  /**
   * Attributo REPORT_NR
   */
  public static final String REPORT_NR = "REPORT_NR";

  /**
   * Attributo RIGA_JOB_ID
   */
  public static final String RIGA_JOB_ID = "RIGA_JOB_ID";

  /**
   * Attributo CODICE_LINEA
   */
  public static final String CODICE_LINEA = "CODICE_LINEA";

  /**
   * Attributo CODICE_ZONA
   */
  public static final String CODICE_ZONA = "CODICE_ZONA";

  /**
   * Attributo COD_UDS
   */
  public static final String COD_UDS = "COD_UDS";

  /**
   * Attributo VARCHAR01
   */
  public static final String VARCHAR01 = "VARCHAR01";

  /**
   * Attributo VARCHAR02
   */
  public static final String VARCHAR02 = "VARCHAR02";

  /**
   * Attributo VARCHAR03
   */
  public static final String VARCHAR03 = "VARCHAR03";

  /**
   * Attributo VARCHAR04
   */
  public static final String VARCHAR04 = "VARCHAR04";

  /**
   * Attributo VARCHAR05
   */
  public static final String VARCHAR05 = "VARCHAR05";

  /**
   * Attributo FLAG01
   */
  public static final String FLAG01 = "FLAG01";

  /**
   * Attributo FLAG02
   */
  public static final String FLAG02 = "FLAG02";

  /**
   * Attributo FLAG03
   */
  public static final String FLAG03 = "FLAG03";

  /**
   * Attributo FLAG04
   */
  public static final String FLAG04 = "FLAG04";

  /**
   * Attributo FLAG05
   */
  public static final String FLAG05 = "FLAG05";

  /**
   * Attributo DECIMAL01
   */
  public static final String DECIMAL01 = "DECIMAL01";

  /**
   * Attributo DECIMAL02
   */
  public static final String DECIMAL02 = "DECIMAL02";

  /**
   * Attributo DECIMAL03
   */
  public static final String DECIMAL03 = "DECIMAL03";

  /**
   * Attributo DECIMAL04
   */
  public static final String DECIMAL04 = "DECIMAL04";

  /**
   * Attributo DECIMAL05
   */
  public static final String DECIMAL05 = "DECIMAL05";

  /**
   * Attributo INTEGER01
   */
  public static final String INTEGER01 = "INTEGER01";

  /**
   * Attributo INTEGER02
   */
  public static final String INTEGER02 = "INTEGER02";

  /**
   * Attributo INTEGER03
   */
  public static final String INTEGER03 = "INTEGER03";

  /**
   * Attributo INTEGER04
   */
  public static final String INTEGER04 = "INTEGER04";

  /**
   * Attributo INTEGER05
   */
  public static final String INTEGER05 = "INTEGER05";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "RPT_SEGNACOLLO_SUSA";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.susa.YRptSegnacolloSusa.class.getName();

  
  /**
   *  getInstance
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public synchronized static TableManager getInstance() throws SQLException {
    if (cInstance == null) {
      cInstance = (TableManager)Factory.createObject(YRptSegnacolloSusaTM.class);
    }
    return cInstance;
  }

  /**
   *  YRptSegnacolloSusaTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YRptSegnacolloSusaTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
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
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    addAttribute("BatchJobId", BATCH_JOB_ID, "getIntegerObject");
    addAttribute("ReportNr", REPORT_NR, "getIntegerObject");
    addAttribute("RigaJobId", RIGA_JOB_ID, "getIntegerObject");
    addAttribute("CodiceLinea", CODICE_LINEA);
    addAttribute("CodiceZona", CODICE_ZONA);
    addAttribute("CodUds", COD_UDS);
    addAttribute("Varchar01", VARCHAR01);
    addAttribute("Varchar02", VARCHAR02);
    addAttribute("Varchar03", VARCHAR03);
    addAttribute("Varchar04", VARCHAR04);
    addAttribute("Varchar05", VARCHAR05);
    addAttribute("Flag01", FLAG01);
    addAttribute("Flag02", FLAG02);
    addAttribute("Flag03", FLAG03);
    addAttribute("Flag04", FLAG04);
    addAttribute("Flag05", FLAG05);
    addAttribute("Decimal01", DECIMAL01);
    addAttribute("Decimal02", DECIMAL02);
    addAttribute("Decimal03", DECIMAL03);
    addAttribute("Decimal04", DECIMAL04);
    addAttribute("Decimal05", DECIMAL05);
    addAttribute("Integer01", INTEGER01, "getIntegerObject");
    addAttribute("Integer02", INTEGER02, "getIntegerObject");
    addAttribute("Integer03", INTEGER03, "getIntegerObject");
    addAttribute("Integer04", INTEGER04, "getIntegerObject");
    addAttribute("Integer05", INTEGER05, "getIntegerObject");
    addAttribute("IdAzienda", ID_AZIENDA);
    
    addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
    setKeys(ID_AZIENDA + "," + BATCH_JOB_ID + "," + REPORT_NR + "," + RIGA_JOB_ID);

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
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  private void init() throws SQLException {
    configure(BATCH_JOB_ID + ", " + REPORT_NR + ", " + RIGA_JOB_ID + ", " + CODICE_LINEA
         + ", " + CODICE_ZONA + ", " + COD_UDS + ", " + VARCHAR01 + ", " + VARCHAR02
         + ", " + VARCHAR03 + ", " + VARCHAR04 + ", " + VARCHAR05 + ", " + FLAG01
         + ", " + FLAG02 + ", " + FLAG03 + ", " + FLAG04 + ", " + FLAG05
         + ", " + DECIMAL01 + ", " + DECIMAL02 + ", " + DECIMAL03 + ", " + DECIMAL04
         + ", " + DECIMAL05 + ", " + INTEGER01 + ", " + INTEGER02 + ", " + INTEGER03
         + ", " + INTEGER04 + ", " + INTEGER05 + ", " + ID_AZIENDA + ", " + STATO
         + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG
        );
  }

}

