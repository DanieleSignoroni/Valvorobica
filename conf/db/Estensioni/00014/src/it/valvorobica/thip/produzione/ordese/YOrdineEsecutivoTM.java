/*
 * @(#)YOrdineEsecutivoTM.java
 */

/**
 * YOrdineEsecutivoTM
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 07/06/2024 at 08:50:32
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 07/06/2024    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.produzione.ordese;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.common.*;
import java.sql.*;
import it.thera.thip.produzione.ordese.*;
import com.thera.thermfw.base.*;

public class YOrdineEsecutivoTM extends OrdineEsecutivoTM {

  
  /**
   * Attributo MARCATURA
   */
  public static final String MARCATURA = "MARCATURA";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YORD_ESEC";

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.produzione.ordese.YOrdineEsecutivo.class.getName();

  
  /**
   *  YOrdineEsecutivoTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 07/06/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public YOrdineEsecutivoTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 07/06/2024    CodeGen     Codice generato da CodeGenerator
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
   * 07/06/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    linkTable(TABLE_NAME_EXT);
    addAttributeOnTable("Marcatura", MARCATURA, TABLE_NAME_EXT);
    
    

  }

}

