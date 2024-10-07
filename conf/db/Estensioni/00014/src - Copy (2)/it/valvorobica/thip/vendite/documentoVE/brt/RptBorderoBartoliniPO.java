/*
 * @(#)RptBorderoBartoliniPO.java
 */

/**
 * null
 *
 * <br></br><b>Copyright (C) : Thera SpA</b>
 * @author Wizard 28/08/2024 at 14:24:27
 */
/*
 * Revisions:
 * Date          Owner      Description
 * 28/08/2024    Wizard     Codice generato da Wizard
 *
 */
package it.valvorobica.thip.vendite.documentoVE.brt;
import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import java.math.*;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import com.thera.thermfw.security.*;

public abstract class RptBorderoBartoliniPO extends PersistentObjectDCE implements BusinessObject, Authorizable, Deletable, Conflictable {

  
  /**
   *  instance
   */
  private static RptBorderoBartolini cInstance;

  /**
   * Attributo iBatchJobId
   */
  protected Integer iBatchJobId;

  /**
   * Attributo iRigaJobId
   */
  protected Integer iRigaJobId;

  /**
   * Attributo iReportNr
   */
  protected Integer iReportNr;

  /**
   * Attributo iNumericSenderRef
   */
  protected String iNumericSenderRef;

  /**
   * Attributo iAlphanumSendRef
   */
  protected String iAlphanumSendRef;

  /**
   * Attributo iConsigneeCompanyName
   */
  protected String iConsigneeCompanyName;

  /**
   * Attributo iConsigneeAdress
   */
  protected String iConsigneeAdress;

  /**
   * Attributo iConsigneeZipCode
   */
  protected String iConsigneeZipCode;

  /**
   * Attributo iConsigneeCity
   */
  protected String iConsigneeCity;

  /**
   * Attributo iConsigneeProvAbbrev
   */
  protected String iConsigneeProvAbbrev;

  /**
   * Attributo iConsigneeContryAbb
   */
  protected String iConsigneeContryAbb;

  /**
   * Attributo iNumberParcels
   */
  protected Integer iNumberParcels;

  /**
   * Attributo iWeightKg
   */
  protected BigDecimal iWeightKg;

  /**
   * Attributo iCashOnDelivery
   */
  protected BigDecimal iCashOnDelivery;

  /**
   * Attributo iCodCurrency
   */
  protected String iCodCurrency;

  /**
   * Attributo iCodPaymentType
   */
  protected String iCodPaymentType;

  /**
   * Attributo iParcelNumberFrom
   */
  protected String iParcelNumberFrom;

  /**
   * Attributo iParcelNumberTo
   */
  protected String iParcelNumberTo;

  /**
   * Attributo iTotColli
   */
  protected Integer iTotColli;

  /**
   * Attributo iTotPeso
   */
  protected BigDecimal iTotPeso;

  /**
   * Attributo iTotSpedizioni
   */
  protected Integer iTotSpedizioni;

  
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
   * 28/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (cInstance == null)
      cInstance = (RptBorderoBartolini)Factory.createObject(RptBorderoBartolini.class);
    return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
  }

  /**
   *  elementWithKey
   * @param key
   * @param lockType
   * @return RptBorderoBartolini
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public static RptBorderoBartolini elementWithKey(String key, int lockType) throws SQLException {
    return (RptBorderoBartolini)PersistentObject.elementWithKey(RptBorderoBartolini.class, key, lockType);
  }

  /**
   * RptBorderoBartoliniPO
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public RptBorderoBartoliniPO() {
  
    // TO DO
  }

  /**
   * Valorizza l'attributo. 
   * @param batchJobId
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
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
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getBatchJobId() {
    return iBatchJobId;
  }

  /**
   * Valorizza l'attributo. 
   * @param rigaJobId
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
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
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getRigaJobId() {
    return iRigaJobId;
  }

  /**
   * Valorizza l'attributo. 
   * @param reportNr
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
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
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getReportNr() {
    return iReportNr;
  }

  /**
   * Valorizza l'attributo. 
   * @param numericSenderRef
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setNumericSenderRef(String numericSenderRef) {
    this.iNumericSenderRef = numericSenderRef;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getNumericSenderRef() {
    return iNumericSenderRef;
  }

  /**
   * Valorizza l'attributo. 
   * @param alphanumSendRef
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setAlphanumSendRef(String alphanumSendRef) {
    this.iAlphanumSendRef = alphanumSendRef;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getAlphanumSendRef() {
    return iAlphanumSendRef;
  }

  /**
   * Valorizza l'attributo. 
   * @param consigneeCompanyName
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setConsigneeCompanyName(String consigneeCompanyName) {
    this.iConsigneeCompanyName = consigneeCompanyName;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getConsigneeCompanyName() {
    return iConsigneeCompanyName;
  }

  /**
   * Valorizza l'attributo. 
   * @param consigneeAdress
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setConsigneeAdress(String consigneeAdress) {
    this.iConsigneeAdress = consigneeAdress;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getConsigneeAdress() {
    return iConsigneeAdress;
  }

  /**
   * Valorizza l'attributo. 
   * @param consigneeZipCode
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setConsigneeZipCode(String consigneeZipCode) {
    this.iConsigneeZipCode = consigneeZipCode;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getConsigneeZipCode() {
    return iConsigneeZipCode;
  }

  /**
   * Valorizza l'attributo. 
   * @param consigneeCity
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setConsigneeCity(String consigneeCity) {
    this.iConsigneeCity = consigneeCity;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getConsigneeCity() {
    return iConsigneeCity;
  }

  /**
   * Valorizza l'attributo. 
   * @param consigneeProvAbbrev
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setConsigneeProvAbbrev(String consigneeProvAbbrev) {
    this.iConsigneeProvAbbrev = consigneeProvAbbrev;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getConsigneeProvAbbrev() {
    return iConsigneeProvAbbrev;
  }

  /**
   * Valorizza l'attributo. 
   * @param consigneeContryAbb
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setConsigneeContryAbb(String consigneeContryAbb) {
    this.iConsigneeContryAbb = consigneeContryAbb;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getConsigneeContryAbb() {
    return iConsigneeContryAbb;
  }

  /**
   * Valorizza l'attributo. 
   * @param numberParcels
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setNumberParcels(Integer numberParcels) {
    this.iNumberParcels = numberParcels;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getNumberParcels() {
    return iNumberParcels;
  }

  /**
   * Valorizza l'attributo. 
   * @param weightKg
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setWeightKg(BigDecimal weightKg) {
    this.iWeightKg = weightKg;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getWeightKg() {
    return iWeightKg;
  }

  /**
   * Valorizza l'attributo. 
   * @param cashOnDelivery
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setCashOnDelivery(BigDecimal cashOnDelivery) {
    this.iCashOnDelivery = cashOnDelivery;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getCashOnDelivery() {
    return iCashOnDelivery;
  }

  /**
   * Valorizza l'attributo. 
   * @param codCurrency
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setCodCurrency(String codCurrency) {
    this.iCodCurrency = codCurrency;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getCodCurrency() {
    return iCodCurrency;
  }

  /**
   * Valorizza l'attributo. 
   * @param codPaymentType
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setCodPaymentType(String codPaymentType) {
    this.iCodPaymentType = codPaymentType;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getCodPaymentType() {
    return iCodPaymentType;
  }

  /**
   * Valorizza l'attributo. 
   * @param parcelNumberFrom
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setParcelNumberFrom(String parcelNumberFrom) {
    this.iParcelNumberFrom = parcelNumberFrom;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getParcelNumberFrom() {
    return iParcelNumberFrom;
  }

  /**
   * Valorizza l'attributo. 
   * @param parcelNumberTo
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setParcelNumberTo(String parcelNumberTo) {
    this.iParcelNumberTo = parcelNumberTo;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getParcelNumberTo() {
    return iParcelNumberTo;
  }

  /**
   * Valorizza l'attributo. 
   * @param totColli
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setTotColli(Integer totColli) {
    this.iTotColli = totColli;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getTotColli() {
    return iTotColli;
  }

  /**
   * Valorizza l'attributo. 
   * @param totPeso
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setTotPeso(BigDecimal totPeso) {
    this.iTotPeso = totPeso;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return BigDecimal
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public BigDecimal getTotPeso() {
    return iTotPeso;
  }

  /**
   * Valorizza l'attributo. 
   * @param totSpedizioni
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setTotSpedizioni(Integer totSpedizioni) {
    this.iTotSpedizioni = totSpedizioni;
    setDirty();
  }

  /**
   * Restituisce l'attributo. 
   * @return Integer
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public Integer getTotSpedizioni() {
    return iTotSpedizioni;
  }

  /**
   * setEqual
   * @param obj
   * @throws CopyException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
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
   * 28/08/2024    Wizard     Codice generato da Wizard
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
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public void setKey(String key) {
    setBatchJobId(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 1)));
    setRigaJobId(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 2)));
    setReportNr(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 3)));
  }

  /**
   *  getKey
   * @return String
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  public String getKey() {
    Integer batchJobId = getBatchJobId();
    Integer rigaJobId = getRigaJobId();
    Integer reportNr = getReportNr();
    Object[] keyParts = {batchJobId, rigaJobId, reportNr};
    return KeyHelper.buildObjectKey(keyParts);
  }

  /**
   * isDeletable
   * @return boolean
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
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
   * 28/08/2024    Wizard     Codice generato da Wizard
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
   * 28/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected TableManager getTableManager() throws SQLException {
    return RptBorderoBartoliniTM.getInstance();
  }

}

