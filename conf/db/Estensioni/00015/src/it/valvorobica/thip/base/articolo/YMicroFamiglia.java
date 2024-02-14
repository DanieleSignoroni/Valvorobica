/*
 * @(#)YMicroFamiglia.java
 */

/**
 * null
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
import java.sql.*;
import java.util.*;
import it.thera.thip.base.articolo.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;

public class YMicroFamiglia extends MicroFamiglia {

  
  /**
   * Attributo iCertificatiDaPortale
   */
  protected boolean iCertificatiDaPortale = false;

  
  /**
   * YMicroFamiglia
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 13/02/2024    Wizard     Codice generato da Wizard
   *
   */
  public YMicroFamiglia() {
    setCertificatiDaPortale(false);
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param certificatiDaPortale
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 13/02/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setCertificatiDaPortale(boolean certificatiDaPortale) {
    this.iCertificatiDaPortale = certificatiDaPortale;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 13/02/2024    Wizard     Codice generato da Wizard
   *
   */
  public boolean getCertificatiDaPortale() {
    return iCertificatiDaPortale;
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 13/02/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setEqual(Copyable obj) throws CopyException {
    super.setEqual(obj);
  }

}

