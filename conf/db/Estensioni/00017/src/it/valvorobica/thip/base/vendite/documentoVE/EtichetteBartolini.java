/*
 * @(#)EtichetteBartolini.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera s.p.a.</b>
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
import java.sql.*;
import it.thera.thip.base.azienda.AziendaEstesa;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;
import com.thera.thermfw.common.*;
import com.thera.thermfw.base.*;

public class EtichetteBartolini extends EtichetteBartoliniPO {

  
  
  /**
   * checkDelete
   * @return ErrorMessage
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 05/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public ErrorMessage checkDelete() {
    /**@todo*/
    return null;
  }

  /**
   * save
   * @return int
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 05/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public int save() throws SQLException {
    if (!isOnDB()) {
      try {
        setId(new Integer(Numerator.getNextInt("EtichetteBartolini")));
      }
      catch(NumeratorException e) {e.printStackTrace(Trace.excStream);}
    }
    return super.save();
  }

}

