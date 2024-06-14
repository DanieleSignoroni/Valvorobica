/*
 * @(#)YMatricolaValvoPO.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 14/06/2024 at 09:53:13
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 14/06/2024    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.base.matricole;
import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.base.azienda.AziendaEstesa;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

public abstract class YMatricolaValvoPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static YMatricolaValvo cInstance;

  /**
   * Attributo iIdMatricola
   */
  protected String iIdMatricola;

  /**
   * Attributo iBody
   */
  protected String iBody;

  /**
   * Attributo iDisc
   */
  protected String iDisc;

  /**
   * Attributo iShaft
   */
  protected String iShaft;

  
  /**
   *  retrieveList
   * @param where
   * @param orderBy
   * @param optimistic
   * @return Vector
   * @throws SQLException
   * @throws ClassNotFoundException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (cInstance == null)
      cInstance = (YMatricolaValvo)Factory.createObject(YMatricolaValvo.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return YMatricolaValvo
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static YMatricolaValvo elementWithKey(String key, int lockType) throws SQLException {
    return (YMatricolaValvo)PersistentObject.elementWithKey(YMatricolaValvo.class, key, lockType);
  }

  /**
   * YMatricolaValvoPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public YMatricolaValvoPO() {
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param idMatricola
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdMatricola(String idMatricola) {
    this.iIdMatricola = idMatricola;
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getIdMatricola() {
    return iIdMatricola;
  }

  /**
   * Valorizza l'attributo. 
   * @param body
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setBody(String body) {
    this.iBody = body;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getBody() {
    return iBody;
  }

  /**
   * Valorizza l'attributo. 
   * @param disc
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setDisc(String disc) {
    this.iDisc = disc;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getDisc() {
    return iDisc;
  }

  /**
   * Valorizza l'attributo. 
   * @param shaft
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setShaft(String shaft) {
    this.iShaft = shaft;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getShaft() {
    return iShaft;
  }

  /**
   * Valorizza l'attributo. 
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdAzienda(String idAzienda) {
    iAzienda.setKey(idAzienda);
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getIdAzienda() {
    String key = iAzienda.getKey();
    return key;
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setEqual(Copyable obj) throws CopyException {
    super.setEqual(obj);
  }

  /**
   * checkAll
   * @param components
   * @return Vector
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public Vector checkAll(BaseComponentsCollection components) {
    Vector errors = new Vector();
    components.runAllChecks(errors);
    return errors;
  }

  /**
   *  setKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setKey(String key) {
    setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
    setIdMatricola(KeyHelper.getTokenObjectKey(key, 2));
  }

  /**
   *  getKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getKey() {
    String idAzienda = getIdAzienda();
    String idMatricola = getIdMatricola();
    Object[] keyParts = {idAzienda, idMatricola};
    return KeyHelper.buildObjectKey(keyParts);
  }

  /**
   * isDeletable
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public boolean isDeletable() {
    return checkDelete() == null;
  }

  /**
   * toString
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String toString() {
    return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
  }

  /**
   *  getTableManager
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected TableManager getTableManager() throws SQLException {
    return YMatricolaValvoTM.getInstance();
  }

}

