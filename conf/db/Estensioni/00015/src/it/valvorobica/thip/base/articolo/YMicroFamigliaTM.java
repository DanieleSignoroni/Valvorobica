/*
 * @(#)YMicroFamigliaTM.java
 */

/**
 * YMicroFamigliaTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 13/02/2024 at 09:00:57
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 13/02/2024    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.base.articolo;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import it.thera.thip.base.articolo.*;
import com.thera.thermfw.base.*;

public class YMicroFamigliaTM extends MicroFamigliaTM {

  
  /**
   * Attributo CERTIFICATI_DA_PORTALE
   */
  public static final String CERTIFICATI_DA_PORTALE = "CERTIFICATI_DA_PORTALE";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YMICROFAMIGLIE";

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.base.articolo.YMicroFamiglia.class.getName();

  
  /**
   *  YMicroFamigliaTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 13/02/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YMicroFamigliaTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 13/02/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected void initialize() throws SQLException {
    super.initialize();
    setObjClassName(CLASS_NAME);
  }

  /**
   *  initializeRelation
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 13/02/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    linkTable(TABLE_NAME_EXT);
    addAttributeOnTable("CertificatiDaPortale", CERTIFICATI_DA_PORTALE, TABLE_NAME_EXT);
    
    

  }

}

