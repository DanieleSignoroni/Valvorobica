/*
 * @(#)YEtichettaSusaTM.java
 */

/**
 * YEtichettaSusaTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 29/08/2025 at 12:09:19
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

public class YEtichettaSusaTM extends TableManager {

  
  /**
   * Attributo COD_UDS
   */
  public static final String COD_UDS = "COD_UDS";

  /**
   * Attributo ETICHETTA_BASE_64
   */
  public static final String ETICHETTA_BASE_64 = "ETICHETTA_BASE_64";

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
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "ETICHETTE_SUSA";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.susa.YEtichettaSusa.class.getName();

  
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
      cInstance = (TableManager)Factory.createObject(YEtichettaSusaTM.class);
    }
    return cInstance;
  }

  /**
   *  YEtichettaSusaTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YEtichettaSusaTM() throws SQLException {
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
    
    addAttribute("CodUds", COD_UDS);
    
    addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
    setKeys(COD_UDS);

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
    configure(ETICHETTA_BASE_64 + ", " + COD_UDS + ", " + STATO + ", " + R_UTENTE_CRZ
         + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
  }

}

