/*
 * @(#)YOrdineEsecutivo.java
 */

/**
 * null
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
import java.sql.*;
import java.util.*;
import it.thera.thip.produzione.ordese.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;

public class YOrdineEsecutivo extends OrdineEsecutivo {

  
  /**
   * Attributo iMarcatura
   */
  protected String iMarcatura;

  
  /**
   * YOrdineEsecutivo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 07/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public YOrdineEsecutivo() {
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param marcatura
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 07/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setMarcatura(String marcatura) {
    this.iMarcatura = marcatura;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 07/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getMarcatura() {
    return iMarcatura;
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 07/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setEqual(Copyable obj) throws CopyException {
    super.setEqual(obj);
  }

}

