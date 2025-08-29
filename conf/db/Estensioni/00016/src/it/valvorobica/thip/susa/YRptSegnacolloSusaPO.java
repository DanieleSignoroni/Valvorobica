/*
 * @(#)YRptSegnacolloSusaPO.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 29/08/2025 at 10:43:09
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
import it.thera.thip.base.azienda.AziendaEstesa;
import java.math.*;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

public abstract class YRptSegnacolloSusaPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static YRptSegnacolloSusa cInstance;

  /**
   * Attributo iBatchJobId
   */
  protected Integer iBatchJobId;

  /**
   * Attributo iReportNr
   */
  protected Integer iReportNr;

  /**
   * Attributo iRigaJobId
   */
  protected Integer iRigaJobId;

  /**
   * Attributo iCodiceLinea
   */
  protected String iCodiceLinea;

  /**
   * Attributo iCodiceZona
   */
  protected String iCodiceZona;

  /**
   * Attributo iCodUds
   */
  protected String iCodUds;

  /**
   * Attributo iVarchar01
   */
  protected String iVarchar01;

  /**
   * Attributo iVarchar02
   */
  protected String iVarchar02;

  /**
   * Attributo iVarchar03
   */
  protected String iVarchar03;

  /**
   * Attributo iVarchar04
   */
  protected String iVarchar04;

  /**
   * Attributo iVarchar05
   */
  protected String iVarchar05;

  /**
   * Attributo iFlag01
   */
  protected boolean iFlag01 = false;

  /**
   * Attributo iFlag02
   */
  protected boolean iFlag02 = false;

  /**
   * Attributo iFlag03
   */
  protected boolean iFlag03 = false;

  /**
   * Attributo iFlag04
   */
  protected boolean iFlag04 = false;

  /**
   * Attributo iFlag05
   */
  protected boolean iFlag05 = false;

  /**
   * Attributo iDecimal01
   */
  protected BigDecimal iDecimal01;

  /**
   * Attributo iDecimal02
   */
  protected BigDecimal iDecimal02;

  /**
   * Attributo iDecimal03
   */
  protected BigDecimal iDecimal03;

  /**
   * Attributo iDecimal04
   */
  protected BigDecimal iDecimal04;

  /**
   * Attributo iDecimal05
   */
  protected BigDecimal iDecimal05;

  /**
   * Attributo iInteger01
   */
  protected Integer iInteger01;

  /**
   * Attributo iInteger02
   */
  protected Integer iInteger02;

  /**
   * Attributo iInteger03
   */
  protected Integer iInteger03;

  /**
   * Attributo iInteger04
   */
  protected Integer iInteger04;

  /**
   * Attributo iInteger05
   */
  protected Integer iInteger05;

  
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
      cInstance = (YRptSegnacolloSusa)Factory.createObject(YRptSegnacolloSusa.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return YRptSegnacolloSusa
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static YRptSegnacolloSusa elementWithKey(String key, int lockType) throws SQLException {
    return (YRptSegnacolloSusa)PersistentObject.elementWithKey(YRptSegnacolloSusa.class, key, lockType);
  }

  /**
   * YRptSegnacolloSusaPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public YRptSegnacolloSusaPO() {
    setFlag01(false);
    setFlag02(false);
    setFlag03(false);
    setFlag04(false);
    setFlag05(false);
    setIdAzienda(Azienda.getAziendaCorrente());
  }

  /**
   * Valorizza l'attributo. 
   * @param batchJobId
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setBatchJobId(Integer batchJobId) {
    this.iBatchJobId = batchJobId;
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getBatchJobId() {
    return iBatchJobId;
  }

  /**
   * Valorizza l'attributo. 
   * @param reportNr
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setReportNr(Integer reportNr) {
    this.iReportNr = reportNr;
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getReportNr() {
    return iReportNr;
  }

  /**
   * Valorizza l'attributo. 
   * @param rigaJobId
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setRigaJobId(Integer rigaJobId) {
    this.iRigaJobId = rigaJobId;
    setDirty();
    setOnDB(false);
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getRigaJobId() {
    return iRigaJobId;
  }

  /**
   * Valorizza l'attributo. 
   * @param codiceLinea
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public String getCodiceZona() {
    return iCodiceZona;
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
    this.iCodUds = codUds;
    setDirty();
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
    return iCodUds;
  }

  /**
   * Valorizza l'attributo. 
   * @param varchar01
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setVarchar01(String varchar01) {
    this.iVarchar01 = varchar01;
    setDirty();
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
  public String getVarchar01() {
    return iVarchar01;
  }

  /**
   * Valorizza l'attributo. 
   * @param varchar02
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setVarchar02(String varchar02) {
    this.iVarchar02 = varchar02;
    setDirty();
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
  public String getVarchar02() {
    return iVarchar02;
  }

  /**
   * Valorizza l'attributo. 
   * @param varchar03
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setVarchar03(String varchar03) {
    this.iVarchar03 = varchar03;
    setDirty();
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
  public String getVarchar03() {
    return iVarchar03;
  }

  /**
   * Valorizza l'attributo. 
   * @param varchar04
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setVarchar04(String varchar04) {
    this.iVarchar04 = varchar04;
    setDirty();
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
  public String getVarchar04() {
    return iVarchar04;
  }

  /**
   * Valorizza l'attributo. 
   * @param varchar05
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setVarchar05(String varchar05) {
    this.iVarchar05 = varchar05;
    setDirty();
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
  public String getVarchar05() {
    return iVarchar05;
  }

  /**
   * Valorizza l'attributo. 
   * @param flag01
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setFlag01(boolean flag01) {
    this.iFlag01 = flag01;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public boolean getFlag01() {
    return iFlag01;
  }

  /**
   * Valorizza l'attributo. 
   * @param flag02
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setFlag02(boolean flag02) {
    this.iFlag02 = flag02;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public boolean getFlag02() {
    return iFlag02;
  }

  /**
   * Valorizza l'attributo. 
   * @param flag03
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setFlag03(boolean flag03) {
    this.iFlag03 = flag03;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public boolean getFlag03() {
    return iFlag03;
  }

  /**
   * Valorizza l'attributo. 
   * @param flag04
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setFlag04(boolean flag04) {
    this.iFlag04 = flag04;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public boolean getFlag04() {
    return iFlag04;
  }

  /**
   * Valorizza l'attributo. 
   * @param flag05
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setFlag05(boolean flag05) {
    this.iFlag05 = flag05;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public boolean getFlag05() {
    return iFlag05;
  }

  /**
   * Valorizza l'attributo. 
   * @param decimal01
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setDecimal01(BigDecimal decimal01) {
    this.iDecimal01 = decimal01;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getDecimal01() {
    return iDecimal01;
  }

  /**
   * Valorizza l'attributo. 
   * @param decimal02
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setDecimal02(BigDecimal decimal02) {
    this.iDecimal02 = decimal02;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getDecimal02() {
    return iDecimal02;
  }

  /**
   * Valorizza l'attributo. 
   * @param decimal03
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setDecimal03(BigDecimal decimal03) {
    this.iDecimal03 = decimal03;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getDecimal03() {
    return iDecimal03;
  }

  /**
   * Valorizza l'attributo. 
   * @param decimal04
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setDecimal04(BigDecimal decimal04) {
    this.iDecimal04 = decimal04;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getDecimal04() {
    return iDecimal04;
  }

  /**
   * Valorizza l'attributo. 
   * @param decimal05
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setDecimal05(BigDecimal decimal05) {
    this.iDecimal05 = decimal05;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getDecimal05() {
    return iDecimal05;
  }

  /**
   * Valorizza l'attributo. 
   * @param integer01
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setInteger01(Integer integer01) {
    this.iInteger01 = integer01;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getInteger01() {
    return iInteger01;
  }

  /**
   * Valorizza l'attributo. 
   * @param integer02
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setInteger02(Integer integer02) {
    this.iInteger02 = integer02;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getInteger02() {
    return iInteger02;
  }

  /**
   * Valorizza l'attributo. 
   * @param integer03
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setInteger03(Integer integer03) {
    this.iInteger03 = integer03;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getInteger03() {
    return iInteger03;
  }

  /**
   * Valorizza l'attributo. 
   * @param integer04
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setInteger04(Integer integer04) {
    this.iInteger04 = integer04;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getInteger04() {
    return iInteger04;
  }

  /**
   * Valorizza l'attributo. 
   * @param integer05
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public void setInteger05(Integer integer05) {
    this.iInteger05 = integer05;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
   *
   */
  public Integer getInteger05() {
    return iInteger05;
  }

  /**
   * Valorizza l'attributo. 
   * @param idAzienda
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    Wizard     Codice generato da Wizard
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
   * 29/08/2025    Wizard     Codice generato da Wizard
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
    setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
    setBatchJobId(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 2)));
    setReportNr(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 3)));
    setRigaJobId(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 4)));
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
    String idAzienda = getIdAzienda();
    Integer batchJobId = getBatchJobId();
    Integer reportNr = getReportNr();
    Integer rigaJobId = getRigaJobId();
    Object[] keyParts = {idAzienda, batchJobId, reportNr, rigaJobId};
    return KeyHelper.buildObjectKey(keyParts);
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
    return YRptSegnacolloSusaTM.getInstance();
  }

}

