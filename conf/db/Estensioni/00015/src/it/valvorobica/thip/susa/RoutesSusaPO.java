/*
 * @(#)RoutesSusaPO.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 12/08/2025 at 14:09:00
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 12/08/2025    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.susa;
import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import com.thera.thermfw.security.*;

public abstract class RoutesSusaPO extends PersistentObjectDCE implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static RoutesSusa cInstance;

  /**
   * Attributo iDescrizioneServzio
   */
  protected String iDescrizioneServzio;

  /**
   * Attributo iFilialePartenza
   */
  protected String iFilialePartenza;

  /**
   * Attributo iCodiceInstradamento
   */
  protected String iCodiceInstradamento;

  /**
   * Attributo iCodiceLinea
   */
  protected String iCodiceLinea;

  /**
   * Attributo iCodiceZona
   */
  protected String iCodiceZona;

  /**
   * Attributo iDescrzioneLinea
   */
  protected String iDescrzioneLinea;

  
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
   * 12/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (cInstance == null)
      cInstance = (RoutesSusa)Factory.createObject(RoutesSusa.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return RoutesSusa
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static RoutesSusa elementWithKey(String key, int lockType) throws SQLException {
    return (RoutesSusa)PersistentObject.elementWithKey(RoutesSusa.class, key, lockType);
  }

  /**
   * RoutesSusaPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public RoutesSusaPO() {
  
    // TO DO
  }

  /**
   * Valorizza l'attributo. 
   * @param descrizioneServzio
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setDescrizioneServzio(String descrizioneServzio) {
    this.iDescrizioneServzio = descrizioneServzio;
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
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getDescrizioneServzio() {
    return iDescrizioneServzio;
  }

  /**
   * Valorizza l'attributo. 
   * @param filialePartenza
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setFilialePartenza(String filialePartenza) {
    this.iFilialePartenza = filialePartenza;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getFilialePartenza() {
    return iFilialePartenza;
  }

  /**
   * Valorizza l'attributo. 
   * @param codiceInstradamento
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setCodiceInstradamento(String codiceInstradamento) {
    this.iCodiceInstradamento = codiceInstradamento;
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
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getCodiceInstradamento() {
    return iCodiceInstradamento;
  }

  /**
   * Valorizza l'attributo. 
   * @param codiceLinea
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setCodiceLinea(String codiceLinea) {
    this.iCodiceLinea = codiceLinea;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getCodiceLinea() {
    return iCodiceLinea;
  }

  /**
   * Valorizza l'attributo. 
   * @param codiceZona
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setCodiceZona(String codiceZona) {
    this.iCodiceZona = codiceZona;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getCodiceZona() {
    return iCodiceZona;
  }

  /**
   * Valorizza l'attributo. 
   * @param descrzioneLinea
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setDescrzioneLinea(String descrzioneLinea) {
    this.iDescrzioneLinea = descrzioneLinea;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getDescrzioneLinea() {
    return iDescrzioneLinea;
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
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
   * 12/08/2025    Wizard     Codice generato da Wizard
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
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setKey(String key) {
    setDescrizioneServzio(KeyHelper.getTokenObjectKey(key, 1));
    setCodiceInstradamento(KeyHelper.getTokenObjectKey(key, 2));
  }

  /**
   *  getKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getKey() {
    String descrizioneServzio = getDescrizioneServzio();
    String codiceInstradamento = getCodiceInstradamento();
    Object[] keyParts = {descrizioneServzio, codiceInstradamento};
    return KeyHelper.buildObjectKey(keyParts);
  }

  /**
   * isDeletable
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 12/08/2025    Wizard     Codice generato da Wizard
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
   * 12/08/2025    Wizard     Codice generato da Wizard
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
   * 12/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected TableManager getTableManager() throws SQLException {
    return RoutesSusaTM.getInstance();
  }

}

