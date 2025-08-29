package it.valvorobica.thip.susa;

import com.thera.thermfw.persist.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 12/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72102    12/08/2025  DSSOF3   Prima stesura
 */

public class RoutesSusaTM extends TableManager {

  
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
   * Attributo DESCRIZIONE_SERVZIO
   */
  public static final String DESCRIZIONE_SERVZIO = "DESCRIZIONE_SERVZIO";

  /**
   * Attributo FILIALE_PARTENZA
   */
  public static final String FILIALE_PARTENZA = "FILIALE_PARTENZA";

  /**
   * Attributo CODICE_INSTRADAMENTO
   */
  public static final String CODICE_INSTRADAMENTO = "CODICE_INSTRADAMENTO";

  /**
   * Attributo CODICE_LINEA
   */
  public static final String CODICE_LINEA = "CODICE_LINEA";

  /**
   * Attributo CODICE_ZONA
   */
  public static final String CODICE_ZONA = "CODICE_ZONA";

  /**
   * Attributo DESCRZIONE_LINEA
   */
  public static final String DESCRZIONE_LINEA = "DESCRZIONE_LINEA";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "ROUTES_SUSA";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.susa.RoutesSusa.class.getName();

  
  /**
   *  getInstance
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public synchronized static TableManager getInstance() throws SQLException {
    if (cInstance == null) {
      cInstance = (TableManager)Factory.createObject(RoutesSusaTM.class);
    }
    return cInstance;
  }

  /**
   *  RoutesSusaTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public RoutesSusaTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    CodeGen     Codice generato da CodeGenerator
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
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    addAttribute("DescrizioneServzio", DESCRIZIONE_SERVZIO);
    addAttribute("FilialePartenza", FILIALE_PARTENZA);
    addAttribute("CodiceInstradamento", CODICE_INSTRADAMENTO);
    addAttribute("CodiceLinea", CODICE_LINEA);
    addAttribute("CodiceZona", CODICE_ZONA);
    addAttribute("DescrzioneLinea", DESCRZIONE_LINEA);
    
    addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
    setKeys(DESCRIZIONE_SERVZIO + "," + CODICE_INSTRADAMENTO);

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
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  private void init() throws SQLException {
    configure(DESCRIZIONE_SERVZIO + ", " + FILIALE_PARTENZA + ", " + CODICE_INSTRADAMENTO + ", " + CODICE_LINEA
         + ", " + CODICE_ZONA + ", " + DESCRZIONE_LINEA + ", " + STATO + ", " + R_UTENTE_CRZ
         + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
  }

}

