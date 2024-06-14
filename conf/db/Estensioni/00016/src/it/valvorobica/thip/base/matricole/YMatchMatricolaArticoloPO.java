/*
 * @(#)YMatchMatricolaArticoloPO.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 14/06/2024 at 10:00:47
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
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.magazzino.generalemag.Lotto;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

public abstract class YMatchMatricolaArticoloPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static YMatchMatricolaArticolo cInstance;

  /**
   * Attributo iArticolo
   */
  protected Proxy iArticolo = new Proxy(it.thera.thip.base.articolo.Articolo.class);

  /**
   * Attributo iMatricolainizio
   */
  protected Proxy iMatricolainizio = new Proxy(it.valvorobica.thip.base.matricole.YMatricolaValvo.class);

  /**
   * Attributo iMatricolafine
   */
  protected Proxy iMatricolafine = new Proxy(it.valvorobica.thip.base.matricole.YMatricolaValvo.class);

  /**
   * Attributo iLotto
   */
  protected Proxy iLotto = new Proxy(it.thera.thip.magazzino.generalemag.Lotto.class);

  
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
      cInstance = (YMatchMatricolaArticolo)Factory.createObject(YMatchMatricolaArticolo.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return YMatchMatricolaArticolo
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static YMatchMatricolaArticolo elementWithKey(String key, int lockType) throws SQLException {
    return (YMatchMatricolaArticolo)PersistentObject.elementWithKey(YMatchMatricolaArticolo.class, key, lockType);
  }

  /**
   * YMatchMatricolaArticoloPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public YMatchMatricolaArticoloPO() {
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param articolo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setArticolo(Articolo articolo) {
    String idAzienda = getIdAzienda();
    if (articolo != null) {
      idAzienda = KeyHelper.getTokenObjectKey(articolo.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    String idArticolo = getIdArticolo();
    if (articolo != null) {
      idArticolo = KeyHelper.getTokenObjectKey(articolo.getKey(), 2);
    }
    setIdArticoloInternal(idArticolo);
    this.iArticolo.setObject(articolo);
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return Articolo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public Articolo getArticolo() {
    return (Articolo)iArticolo.getObject();
  }

  /**
   * setArticoloKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setArticoloKey(String key) {
    iArticolo.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    String idArticolo = KeyHelper.getTokenObjectKey(key, 2);
    setIdArticoloInternal(idArticolo);
    setDirty();
    setOnDB(false);
  }

  /**
   * getArticoloKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getArticoloKey() {
    return iArticolo.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param matricolainizio
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setMatricolainizio(YMatricolaValvo matricolainizio) {
    String idAzienda = getIdAzienda();
    if (matricolainizio != null) {
      idAzienda = KeyHelper.getTokenObjectKey(matricolainizio.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    this.iMatricolainizio.setObject(matricolainizio);
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return YMatricolaValvo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public YMatricolaValvo getMatricolainizio() {
    return (YMatricolaValvo)iMatricolainizio.getObject();
  }

  /**
   * setMatricolainizioKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setMatricolainizioKey(String key) {
    iMatricolainizio.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    setDirty();
    setOnDB(false);
  }

  /**
   * getMatricolainizioKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getMatricolainizioKey() {
    return iMatricolainizio.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param idMatricolaDa
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdMatricolaDa(String idMatricolaDa) {
    String key = iMatricolainizio.getKey();
    iMatricolainizio.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idMatricolaDa));
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
  public String getIdMatricolaDa() {
    String key = iMatricolainizio.getKey();
    String objIdMatricolaDa = KeyHelper.getTokenObjectKey(key,2);
    return objIdMatricolaDa;
  }

  /**
   * Valorizza l'attributo. 
   * @param matricolafine
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setMatricolafine(YMatricolaValvo matricolafine) {
    String idAzienda = getIdAzienda();
    if (matricolafine != null) {
      idAzienda = KeyHelper.getTokenObjectKey(matricolafine.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    this.iMatricolafine.setObject(matricolafine);
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return YMatricolaValvo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public YMatricolaValvo getMatricolafine() {
    return (YMatricolaValvo)iMatricolafine.getObject();
  }

  /**
   * setMatricolafineKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setMatricolafineKey(String key) {
    iMatricolafine.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    setDirty();
    setOnDB(false);
  }

  /**
   * getMatricolafineKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getMatricolafineKey() {
    return iMatricolafine.getKey();
  }

  /**
   * Valorizza l'attributo. 
   * @param idMatricolaA
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdMatricolaA(String idMatricolaA) {
    String key = iMatricolafine.getKey();
    iMatricolafine.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idMatricolaA));
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
  public String getIdMatricolaA() {
    String key = iMatricolafine.getKey();
    String objIdMatricolaA = KeyHelper.getTokenObjectKey(key,2);
    return objIdMatricolaA;
  }

  /**
   * Valorizza l'attributo. 
   * @param lotto
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setLotto(Lotto lotto) {
    String oldObjectKey = getKey();
    String idAzienda = getIdAzienda();
    if (lotto != null) {
      idAzienda = KeyHelper.getTokenObjectKey(lotto.getKey(), 1);
    }
    setIdAziendaInternal(idAzienda);
    String idArticolo = getIdArticolo();
    if (lotto != null) {
      idArticolo = KeyHelper.getTokenObjectKey(lotto.getKey(), 2);
    }
    setIdArticoloInternal(idArticolo);
    this.iLotto.setObject(lotto);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * Restituisce l'attributo. 
   * @return Lotto
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public Lotto getLotto() {
    return (Lotto)iLotto.getObject();
  }

  /**
   * setLottoKey
   * @param key
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setLottoKey(String key) {
    String oldObjectKey = getKey();
    iLotto.setKey(key);
    String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
    setIdAziendaInternal(idAzienda);
    String idArticolo = KeyHelper.getTokenObjectKey(key, 2);
    setIdArticoloInternal(idArticolo);
    setDirty();
    if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
      setOnDB(false);
    }
  }

  /**
   * getLottoKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getLottoKey() {
    return iLotto.getKey();
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
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getIdAzienda() {
    String key = iAzienda.getKey();
    return key;
  }

  /**
   * Valorizza l'attributo. 
   * @param idArticolo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdArticolo(String idArticolo) {
    setIdArticoloInternal(idArticolo);
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
  public String getIdArticolo() {
    String key = iArticolo.getKey();
    String objIdArticolo = KeyHelper.getTokenObjectKey(key,2);
    return objIdArticolo;
  }

  /**
   * Valorizza l'attributo. 
   * @param idLotto
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setIdLotto(String idLotto) {
    String key = iLotto.getKey();
    iLotto.setKey(KeyHelper.replaceTokenObjectKey(key , 3, idLotto));
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
  public String getIdLotto() {
    String key = iLotto.getKey();
    String objIdLotto = KeyHelper.getTokenObjectKey(key,3);
    return objIdLotto;
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
    YMatchMatricolaArticoloPO yMatchMatricolaArticoloPO = (YMatchMatricolaArticoloPO)obj;
    iArticolo.setEqual(yMatchMatricolaArticoloPO.iArticolo);
    iMatricolainizio.setEqual(yMatchMatricolaArticoloPO.iMatricolainizio);
    iMatricolafine.setEqual(yMatchMatricolaArticoloPO.iMatricolafine);
    iLotto.setEqual(yMatchMatricolaArticoloPO.iLotto);
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
    setIdArticolo(KeyHelper.getTokenObjectKey(key, 2));
    setIdMatricolaDa(KeyHelper.getTokenObjectKey(key, 3));
    setIdMatricolaA(KeyHelper.getTokenObjectKey(key, 4));
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
    String idArticolo = getIdArticolo();
    String idMatricolaDa = getIdMatricolaDa();
    String idMatricolaA = getIdMatricolaA();
    Object[] keyParts = {idAzienda, idArticolo, idMatricolaDa, idMatricolaA};
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
    return YMatchMatricolaArticoloTM.getInstance();
  }

  /**
   * setIdAziendaInternal
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void setIdAziendaInternal(String idAzienda) {
    iAzienda.setKey(idAzienda);
        String key2 = iArticolo.getKey();
    iArticolo.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
    String key3 = iMatricolainizio.getKey();
    iMatricolainizio.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
    String key4 = iMatricolafine.getKey();
    iMatricolafine.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
    String key5 = iLotto.getKey();
    iLotto.setKey(KeyHelper.replaceTokenObjectKey(key5, 1, idAzienda));
  }

  /**
   * setIdArticoloInternal
   * @param idArticolo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 14/06/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void setIdArticoloInternal(String idArticolo) {
    String key1 = iArticolo.getKey();
    iArticolo.setKey(KeyHelper.replaceTokenObjectKey(key1, 2, idArticolo));
    String key2 = iLotto.getKey();
    iLotto.setKey(KeyHelper.replaceTokenObjectKey(key2, 2, idArticolo));
  }

}

