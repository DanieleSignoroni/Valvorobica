/*
 * @(#)EtichetteBartoliniTM.java
 */

/**
 * EtichetteBartoliniTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 05/08/2024 at 16:32:47
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 05/08/2024    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.base.vendite.documentoVE;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

public class EtichetteBartoliniTM extends TableManager {

  
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
   * Attributo ID_ANNO_DDT
   */
  public static final String ID_ANNO_DDT = "ID_ANNO_DDT";

  /**
   * Attributo ID_NUMERO_DDT
   */
  public static final String ID_NUMERO_DDT = "ID_NUMERO_DDT";

  /**
   * Attributo TIPO_DDT
   */
  public static final String TIPO_DDT = "TIPO_DDT";

  /**
   * Attributo ID
   */
  public static final String ID = "ID";

  /**
   * Attributo ETICHETTA_BASE_64
   */
  public static final String ETICHETTA_BASE_64 = "ETICHETTA_BASE_64";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "ETICHETTE_BARTOLINI";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.base.vendite.documentoVE.EtichetteBartolini.class.getName();

  
  /**
   *  getInstance
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 05/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public synchronized static TableManager getInstance() throws SQLException {
    if (cInstance == null) {
      cInstance = (TableManager)Factory.createObject(EtichetteBartoliniTM.class);
    }
    return cInstance;
  }

  /**
   *  EtichetteBartoliniTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 05/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public EtichetteBartoliniTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 05/08/2024    CodeGen     Codice generato da CodeGenerator
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
   * 05/08/2024    Wizard     Codice generato da Wizard
   *
   */
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

  /**
   *  init
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 05/08/2024    Wizard     Codice generato da Wizard
   *
   */
  private void init() throws SQLException {
    configure(ID + ", " + ETICHETTA_BASE_64 + ", " + ID_AZIENDA + ", " + ID_ANNO_DDT
         + ", " + ID_NUMERO_DDT + ", " + TIPO_DDT + ", " + STATO + ", " + R_UTENTE_CRZ
         + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
  }

}

