/*
 * @(#)YEtichettaSusaPO.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 29/08/2025 at 12:09:19
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 29/08/2025    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.susa;
import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import com.thera.thermfw.security.*;

public abstract class YEtichettaSusaPO extends PersistentObjectDCE implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static YEtichettaSusa cInstance;

  /**
   * Attributo iEtichettaBase64
   */
  protected BLOB iEtichettaBase64;

  /**
   * Attributo iTestatauds
   */
  protected Proxy iTestatauds = new Proxy(it.thera.thip.logis.fis.TestataUds.class);

  
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
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (cInstance == null)
      cInstance = (YEtichettaSusa)Factory.createObject(YEtichettaSusa.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return YEtichettaSusa
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static YEtichettaSusa elementWithKey(String key, int lockType) throws SQLException {
    return (YEtichettaSusa)PersistentObject.elementWithKey(YEtichettaSusa.class, key, lockType);
  }

  /**
   * YEtichettaSusaPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public YEtichettaSusaPO() {
    iEtichettaBase64 = new BLOB(this, "ETICHETTA_BASE_64");
    
  }

  /**
   * Restituisce l'attributo. 
   * @return BLOB
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public BLOB getEtichettaBase64() {
    return iEtichettaBase64;
  }

  /**
   * Valorizza l'attributo. 
   * @param testatauds
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setTestatauds(TestataUds testatauds) {
    this.iTestatauds.setObject(testatauds);
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return TestataUds
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public TestataUds getTestatauds() {
    return (TestataUds)iTestatauds.getObject();
  }

  /**
   * setTestataudsKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setTestataudsKey(String key) {
    iTestatauds.setKey(key);
    setDirty();
    setOnDB(false);
  }

  /**
   * getTestataudsKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getTestataudsKey() {
    return iTestatauds.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param codUds
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setCodUds(String codUds) {
    iTestatauds.setKey(codUds);
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
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getCodUds() {
    String key = iTestatauds.getKey();
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
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setEqual(Copyable obj) throws CopyException {
    super.setEqual(obj);
    YEtichettaSusaPO yEtichettaSusaPO = (YEtichettaSusaPO)obj;
    iEtichettaBase64.setEqual(yEtichettaSusaPO.iEtichettaBase64);
    iTestatauds.setEqual(yEtichettaSusaPO.iTestatauds);
  }

  /**
   * checkAll
   * @param components
   * @return Vector
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setKey(String key) {
    setCodUds(key);
  }

  /**
   *  getKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getKey() {
    return getCodUds();
  }

  /**
   * isDeletable
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected TableManager getTableManager() throws SQLException {
    return YEtichettaSusaTM.getInstance();
  }

}

