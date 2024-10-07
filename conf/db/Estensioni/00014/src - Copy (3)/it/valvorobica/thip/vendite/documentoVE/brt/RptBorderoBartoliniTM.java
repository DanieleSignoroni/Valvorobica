/*
 * @(#)RptBorderoBartoliniTM.java
 */

/**
 * RptBorderoBartoliniTM
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
import com.thera.thermfw.common.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

public class RptBorderoBartoliniTM extends TableManager {

  
  /**
   * Attributo BATCH_JOB_ID
   */
  public static final String BATCH_JOB_ID = "BATCH_JOB_ID";

  /**
   * Attributo RIGA_JOB_ID
   */
  public static final String RIGA_JOB_ID = "RIGA_JOB_ID";

  /**
   * Attributo REPORT_NR
   */
  public static final String REPORT_NR = "REPORT_NR";

  /**
   * Attributo NUMERIC_SENDER_REF
   */
  public static final String NUMERIC_SENDER_REF = "NUMERIC_SENDER_REF";

  /**
   * Attributo ALPHANUM_SEND_REF
   */
  public static final String ALPHANUM_SEND_REF = "ALPHANUM_SEND_REF";

  /**
   * Attributo CONSIGNEE_COMPANY_NAME
   */
  public static final String CONSIGNEE_COMPANY_NAME = "CONSIGNEE_COMPANY_NAME";

  /**
   * Attributo CONSIGNEE_ADRESS
   */
  public static final String CONSIGNEE_ADRESS = "CONSIGNEE_ADRESS";

  /**
   * Attributo CONSIGNEE_ZIP_CODE
   */
  public static final String CONSIGNEE_ZIP_CODE = "CONSIGNEE_ZIP_CODE";

  /**
   * Attributo CONSIGNEE_CITY
   */
  public static final String CONSIGNEE_CITY = "CONSIGNEE_CITY";

  /**
   * Attributo CONSIGNEE_PROV_ABBREV
   */
  public static final String CONSIGNEE_PROV_ABBREV = "CONSIGNEE_PROV_ABBREV";

  /**
   * Attributo CONSIGNEE_CONTRY_ABB
   */
  public static final String CONSIGNEE_CONTRY_ABB = "CONSIGNEE_CONTRY_ABB";

  /**
   * Attributo NUMBER_PARCELS
   */
  public static final String NUMBER_PARCELS = "NUMBER_PARCELS";

  /**
   * Attributo WEIGHT_KG
   */
  public static final String WEIGHT_KG = "WEIGHT_KG";

  /**
   * Attributo CASH_ON_DELIVERY
   */
  public static final String CASH_ON_DELIVERY = "CASH_ON_DELIVERY";

  /**
   * Attributo COD_CURRENCY
   */
  public static final String COD_CURRENCY = "COD_CURRENCY";

  /**
   * Attributo COD_PAYMENT_TYPE
   */
  public static final String COD_PAYMENT_TYPE = "COD_PAYMENT_TYPE";

  /**
   * Attributo PARCEL_NUMBER_FROM
   */
  public static final String PARCEL_NUMBER_FROM = "PARCEL_NUMBER_FROM";

  /**
   * Attributo PARCEL_NUMBER_TO
   */
  public static final String PARCEL_NUMBER_TO = "PARCEL_NUMBER_TO";

  /**
   * Attributo TOT_COLLI
   */
  public static final String TOT_COLLI = "TOT_COLLI";

  /**
   * Attributo TOT_PESO
   */
  public static final String TOT_PESO = "TOT_PESO";

  /**
   * Attributo TOT_SPEDIZIONI
   */
  public static final String TOT_SPEDIZIONI = "TOT_SPEDIZIONI";

  /**
   * Attributo STATO
   */
  public static final String STATO = "STATO";

  /**
   * Attributo R_UTENTE_CRZ
   */
  public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

  /**
   * Attributo TIMESTAMP_CRZ
   */
  public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

  /**
   * Attributo R_UTENTE_AGG
   */
  public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

  /**
   * Attributo TIMESTAMP_AGG
   */
  public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

  /**
   *  TABLE_NAME
   */
  public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "RPT_BORDERO_BRT";

  /**
   *  instance
   */
  private static TableManager cInstance;

  /**
   *  CLASS_NAME
   */
  private static final String CLASS_NAME = it.valvorobica.thip.vendite.documentoVE.brt.RptBorderoBartolini.class.getName();

  
  /**
   *  getInstance
   * @return TableManager
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public synchronized static TableManager getInstance() throws SQLException {
    if (cInstance == null) {
      cInstance = (TableManager)Factory.createObject(RptBorderoBartoliniTM.class);
    }
    return cInstance;
  }

  /**
   *  RptBorderoBartoliniTM
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  public RptBorderoBartoliniTM() throws SQLException {
    super();
  }

  /**
   *  initialize
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    CodeGen     Codice generato da CodeGenerator
   *
   */
  protected void initialize() throws SQLException {
    setTableName(TABLE_NAME);
    setObjClassName(CLASS_NAME);
    init();
  }

  /**
   *  initializeRelation
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  protected void initializeRelation() throws SQLException {
    super.initializeRelation();
    addAttribute("BatchJobId", BATCH_JOB_ID, "getIntegerObject");
    addAttribute("RigaJobId", RIGA_JOB_ID, "getIntegerObject");
    addAttribute("ReportNr", REPORT_NR, "getIntegerObject");
    addAttribute("NumericSenderRef", NUMERIC_SENDER_REF);
    addAttribute("AlphanumSendRef", ALPHANUM_SEND_REF);
    addAttribute("ConsigneeCompanyName", CONSIGNEE_COMPANY_NAME);
    addAttribute("ConsigneeAdress", CONSIGNEE_ADRESS);
    addAttribute("ConsigneeZipCode", CONSIGNEE_ZIP_CODE);
    addAttribute("ConsigneeCity", CONSIGNEE_CITY);
    addAttribute("ConsigneeProvAbbrev", CONSIGNEE_PROV_ABBREV);
    addAttribute("ConsigneeContryAbb", CONSIGNEE_CONTRY_ABB);
    addAttribute("NumberParcels", NUMBER_PARCELS, "getIntegerObject");
    addAttribute("WeightKg", WEIGHT_KG);
    addAttribute("CashOnDelivery", CASH_ON_DELIVERY);
    addAttribute("CodCurrency", COD_CURRENCY);
    addAttribute("CodPaymentType", COD_PAYMENT_TYPE);
    addAttribute("ParcelNumberFrom", PARCEL_NUMBER_FROM);
    addAttribute("ParcelNumberTo", PARCEL_NUMBER_TO);
    addAttribute("TotColli", TOT_COLLI, "getIntegerObject");
    addAttribute("TotPeso", TOT_PESO);
    addAttribute("TotSpedizioni", TOT_SPEDIZIONI, "getIntegerObject");
    
    addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
    setKeys(BATCH_JOB_ID + "," + RIGA_JOB_ID + "," + REPORT_NR);

    setTimestampColumn("TIMESTAMP_AGG");
    ((it.thera.thip.cs.DatiComuniEstesiTTM)getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
  }

  /**
   *  init
   * @throws SQLException
   */
  /*
   * Revisions:
   * Date          Owner      Description
   * 28/08/2024    Wizard     Codice generato da Wizard
   *
   */
  private void init() throws SQLException {
    configure(BATCH_JOB_ID + ", " + RIGA_JOB_ID + ", " + REPORT_NR + ", " + NUMERIC_SENDER_REF
         + ", " + ALPHANUM_SEND_REF + ", " + CONSIGNEE_COMPANY_NAME + ", " + CONSIGNEE_ADRESS + ", " + CONSIGNEE_ZIP_CODE
         + ", " + CONSIGNEE_CITY + ", " + CONSIGNEE_PROV_ABBREV + ", " + CONSIGNEE_CONTRY_ABB + ", " + NUMBER_PARCELS
         + ", " + WEIGHT_KG + ", " + CASH_ON_DELIVERY + ", " + COD_CURRENCY + ", " + COD_PAYMENT_TYPE
         + ", " + PARCEL_NUMBER_FROM + ", " + PARCEL_NUMBER_TO + ", " + TOT_COLLI + ", " + TOT_PESO
         + ", " + TOT_SPEDIZIONI + ", " + STATO + ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ
         + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
  }

}

