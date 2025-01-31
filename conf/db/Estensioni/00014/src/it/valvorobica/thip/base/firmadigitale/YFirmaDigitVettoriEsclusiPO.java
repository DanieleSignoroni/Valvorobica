/*
 * @(#)YFirmaDigitVettoriEsclusiPO.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 30/01/2025 at 08:59:33
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 30/01/2025    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.base.firmadigitale;
import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.base.azienda.AziendaEstesa;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

public abstract class YFirmaDigitVettoriEsclusiPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static YFirmaDigitVettoriEsclusi cInstance;

  /**
   * Attributo iVettore
   */
  protected Proxy iVettore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);

  
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
   * 30/01/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (cInstance == null)
      cInstance = (YFirmaDigitVettoriEsclusi)Factory.createObject(YFirmaDigitVettoriEsclusi.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return YFirmaDigitVettoriEsclusi
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static YFirmaDigitVettoriEsclusi elementWithKey(String key, int lockType) throws SQLException {
    return (YFirmaDigitVettoriEsclusi)PersistentObject.elementWithKey(YFirmaDigitVettoriEsclusi.class, key, lockType);
  }

  /**
   * YFirmaDigitVettoriEsclusiPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public YFirmaDigitVettoriEsclusiPO() {
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param vettore
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setVettore(FornitoreAcquisto vettore) {
    String idAzienda = getIdAzienda();
    if (vettore != null) {
      idAzienda = KeyHelper.getTokenObjectKey(vettore.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    this.iVettore.setObject(vettore);
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return FornitoreAcquisto
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public FornitoreAcquisto getVettore() {
    return (FornitoreAcquisto)iVettore.getObject();
  }

  /**
   * setVettoreKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setVettoreKey(String key) {
    iVettore.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    setDirty();
    setOnDB(false);
  }

  /**
   * getVettoreKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getVettoreKey() {
    return iVettore.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setIdAzienda(String idAzienda) {
    setIdAziendaInternal(idAzienda);
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
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getIdAzienda() {
    String key = iAzienda.getKey();
    return key;
  }

  /**
   * Valorizza l'attributo. 
   * @param idVettore
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setIdVettore(String idVettore) {
    String key = iVettore.getKey();
    iVettore.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idVettore));
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
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getIdVettore() {
    String key = iVettore.getKey();
    String objIdVettore = KeyHelper.getTokenObjectKey(key,2);
    return objIdVettore;
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setEqual(Copyable obj) throws CopyException {
    super.setEqual(obj);
    YFirmaDigitVettoriEsclusiPO yFirmaDigitVettoriEsclusiPO = (YFirmaDigitVettoriEsclusiPO)obj;
    iVettore.setEqual(yFirmaDigitVettoriEsclusiPO.iVettore);
  }

  /**
   * checkAll
   * @param components
   * @return Vector
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
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
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setKey(String key) {
    setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
    setIdVettore(KeyHelper.getTokenObjectKey(key, 2));
  }

  /**
   *  getKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getKey() {
    String idAzienda = getIdAzienda();
    String idVettore = getIdVettore();
    Object[] keyParts = {idAzienda, idVettore};
    return KeyHelper.buildObjectKey(keyParts);
  }

  /**
   * isDeletable
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
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
   * 30/01/2025    Wizard     Codice generato da Wizard
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
   * 30/01/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected TableManager getTableManager() throws SQLException {
    return YFirmaDigitVettoriEsclusiTM.getInstance();
  }

  /**
   * setIdAziendaInternal
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 30/01/2025    Wizard     Codice generato da Wizard
   *
   */
  protected void setIdAziendaInternal(String idAzienda) {
    iAzienda.setKey(idAzienda);
        String key2 = iVettore.getKey();
    iVettore.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
  }

}

