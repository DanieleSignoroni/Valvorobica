/*
 * @(#)YMatchMatricolaArticoloTM.java
 */

/**
 * YMatchMatricolaArticoloTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 17/06/2024 at 16:45:44
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 17/06/2024    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.base.matricole;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

public class YMatchMatricolaArticoloTM extends TableManager {

  
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
   * Attributo ID_ARTICOLO
   */
  public static final String ID_ARTICOLO = "ID_ARTICOLO";

  /**
   * Attributo ID_LOTTO
   */
  public static final String ID_LOTTO = "ID_LOTTO";

  /**
   * Attributo ID_MATRICOLA_DA
   */
  public static final String ID_MATRICOLA_DA = "ID_MATRICOLA_DA";

  /**
   * Attributo ID_MATRICOLA_A
   */
  public static final String ID_MATRICOLA_A = "ID_MATRICOLA_A";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YMATCH_MATRICOLA_ARTICOLO";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.base.matricole.YMatchMatricolaArticolo.class.getName();

  
  /**
   *  getInstance
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 17/06/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public synchronized static TableManager getInstance() throws SQLException {
    if (cInstance == null) {
      cInstance = (TableManager)Factory.createObject(YMatchMatricolaArticoloTM.class);
    }
    return cInstance;
  }

  /**
   *  YMatchMatricolaArticoloTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 17/06/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YMatchMatricolaArticoloTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 17/06/2024    CodeGen     Codice generato da CodeGenerator
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
   * 17/06/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    addAttribute("IdAzienda", ID_AZIENDA);
    addAttribute("IdArticolo", ID_ARTICOLO);
    addAttribute("IdMatricolaDa", ID_MATRICOLA_DA);
    addAttribute("IdMatricolaA", ID_MATRICOLA_A);
    addAttribute("IdLotto", ID_LOTTO);
    
    addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
    setKeys(ID_AZIENDA + "," + ID_ARTICOLO + "," + ID_MATRICOLA_DA + "," + ID_MATRICOLA_A + "," + ID_LOTTO);

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
   * 17/06/2024    Wizard     Codice generato da Wizard
   *
   */
  private void init() throws SQLException {
    configure(ID_AZIENDA + ", " + ID_ARTICOLO + ", " + ID_MATRICOLA_DA + ", " + ID_MATRICOLA_A
         + ", " + ID_LOTTO + ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ
         + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
  }

}

