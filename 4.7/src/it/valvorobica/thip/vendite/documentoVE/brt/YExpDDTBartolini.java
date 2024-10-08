package it.valvorobica.thip.vendite.documentoVE.brt;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Vector;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.AvailableReport;
import com.thera.thermfw.batch.CrystalReportsInterface;
import com.thera.thermfw.batch.ElaboratePrintRunnable;
import com.thera.thermfw.batch.Printer;
import com.thera.thermfw.batch.PrintingToolInterface;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.Column;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.acquisti.generaleAC.ModelGenDocumentoVendita;
import it.thera.thip.acquisti.generaleAC.ModelGenDocumentoVenditaTM;
import it.thera.thip.base.articolo.ArticoloBase;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.DdtTes;
import it.thera.thip.base.comuniVenAcq.DdtTesTM;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;
import it.thera.thip.vendite.documentoVE.DocumentoVenditaTM;
import it.valvorobica.thip.vendite.documentoVE.YDocumentoVendita;

/**
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Daniele Signoroni 05/08/2024 <br>
 *         <br>
 *         <b>71611 DSSOF3 05/08/2024</b>
 *         <p>
 *         Prima stesura.<br>
 * 
 *         </p>
 */

public class YExpDDTBartolini extends ElaboratePrintRunnable implements Authorizable {

	public static final char PROCESSATO = 'P';

	protected Date iDateFrom;

	protected Date iDateTo;

	protected Proxy iStampante = new Proxy(com.thera.thermfw.batch.Printer.class);

	protected AvailableReport iAvailableRpt;

	public Date getDateFrom() {
		return iDateFrom;
	}

	public void setDateFrom(Date iDateFrom) {
		this.iDateFrom = iDateFrom;
	}

	public Date getDateTo() {
		return iDateTo;
	}

	public void setDateTo(Date iDateTo) {
		this.iDateTo = iDateTo;
	}

	public void setStampante(Printer stampante) {
		this.iStampante.setObject(stampante);
		setDirty();
	}

	public Printer getStampante() {
		return (Printer) iStampante.getObject();
	}

	public void setStampanteKey(String key) {
		iStampante.setKey(key);
		setDirty();
	}

	public String getStampanteKey() {
		return iStampante.getKey();
	}

	public void setIdStampante(String idStampante) {
		iStampante.setKey(idStampante);
	}

	public String getIdStampante() {
		String key = iStampante.getKey();
		return key;
	}

	public YExpDDTBartolini() {
		setDateFrom(TimeUtils.getCurrentDate());
		setDateTo(TimeUtils.getCurrentDate());
	}

	@Override
	public boolean createReport() {
		boolean isOk = true;
		job.setReportCounter((short) 0);
		iAvailableRpt = createNewReport(getReportId());
		if (iAvailableRpt == null)
			return false;

		try {
			setPrintToolInterface((PrintingToolInterface) Factory.createObject(CrystalReportsInterface.class));
			String s = printToolInterface.generateDefaultWhereCondition(iAvailableRpt,
					RptBorderoBartoliniTM.getInstance());
			iAvailableRpt.setWhereCondition(s);
			int res = iAvailableRpt.save();
			if (res < 0) {
				System.out.println("Problema di salvataggio availableReport, errorCode = " + res);
				isOk = false;
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
			isOk = false;
		}
		if (isOk) {
			Printer stampante = getStampante();
			if (getBatchJob().getFromScheduledJob()) { // Se e' lanciato da schedulatore allora prendo date odierne
				Integer giorniApprontamento = Integer.valueOf(ParametroPsn.getValoreParametroPsn("YExpDDTBartolini", "GiorniApprontamento"));
				Date dateFrom = TimeUtils.getCurrentDate();
				setDateFrom(TimeUtils.addDays(dateFrom, -giorniApprontamento));
				setDateTo(TimeUtils.getCurrentDate());
			}
			PrintService selectedPrintService = null;
			if (stampante != null) {
				PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
				for (PrintService printService : printServices) {
					if (printService.getName().equalsIgnoreCase(stampante.getDevice())) {
						selectedPrintService = printService;
						break;
					}
				}
			}
			EstremiBartolini estremiBrt = EstremiBartolini.estremiBartolini();
			output.println(" ** Esportazione dei DDT verso Bartolini **");
			int rigaJobId = 0;
			for (DdtTes ddt : recuperaListaDDT()) {
				output.println(" --> Processo DDT {" + ddt.getKey() + "} ");

				boolean isDdtIntercompany = isDdtIntercompany(ddt);
				boolean escludereIC = false;

				if(isDdtIntercompany)
					escludereIC = isIntercompanyEscludere(ddt);

				if (ddt.getFlagRisUte3() != PROCESSATO && !escludereIC) {
					JSONObject json = estremiBrt.recuperaJsonDDT(ddt, estremiBrt);
					JSONObject bodyAsJSON = estremiBrt.sendShipmentBartolini(ddt, json, estremiBrt);
					Status status = (Status) bodyAsJSON.get("status");

					output.println(" DDT  processato con status {" + status.getStatusCode() + "} ");

					if (bodyAsJSON.has("labels")) {
						JSONArray labels = bodyAsJSON.getJSONArray("labels");
						if (labels.length() > 0) {

							output.println(" Il DDT  ha {" + labels.length() + "} etichette, procedo con la stampa ");

							/*
							 * Di seguito stampo le etichette che Bartolini mi ritorna in formato ZPL,
							 * questo perche' le etichettatrici dalla TSC non accettano input stream pdf
							 * normali. Quindi prendo la stringa e la decodifico in bytes che poi passo alla
							 * stampante.
							 */

							for (int i = 0; i < labels.length(); i++) {
								JSONObject label = labels.getJSONObject(i);
								if (selectedPrintService != null) {
									String command = label.getString("stream");
									if (label != null && getIdStampante() != null) {
										DocPrintJob printJob = selectedPrintService.createPrintJob();
										byte[] bytes = Base64.getDecoder().decode(command);
										DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
										Doc doc = new SimpleDoc(bytes, flavor, null);
										try {
											printJob.print(doc, null);
										} catch (PrintException e) {
											e.printStackTrace(Trace.excStream);
										}
									}
								}
							}
							// Flaggo il ddt come processato e non lo ri-processero' piu'
							try {
								int rcSave = flaggaComeProcessatiDocumentiVenditaAppartenentiDDT(ddt.getKey(), PROCESSATO);

								// Qui devo popolare la mia tabella RPT per poi fare il bordero'
								JSONObject createResponse = bodyAsJSON.getJSONObject("createResponse");
								if (createResponse != null) {
									// Tramite questa posso popolare la tabella RPT
									String numericSenderReference = String.valueOf(ddt.getBatchJobId());
									String alphanumericSenderReference = createResponse
											.has("alphanumericSenderReference")
											? createResponse.getString("alphanumericSenderReference")
													: "";
									String consigneeCompanyName = createResponse.has("consigneeCompanyName")
											? createResponse.getString("consigneeCompanyName")
													: "";
									String consigneeAddress = createResponse.has("consigneeAddress")
											? createResponse.getString("consigneeAddress")
													: "";
									String consigneeZIPCode = createResponse.has("consigneeZIPCode")
											? createResponse.getString("consigneeZIPCode")
													: "";
									String consigneeCity = createResponse.has("consigneeCity")
											? createResponse.getString("consigneeCity")
													: "";
									String consigneeProvinceAbbreviation = createResponse
											.has("consigneeProvinceAbbreviation")
											? createResponse.getString("consigneeProvinceAbbreviation")
													: "";
									// String consigneeCountryAbbreviationBRT =
									// createResponse.has("consigneeCountryAbbreviationBRT") ?
									// createResponse.getString("consigneeCountryAbbreviationBRT") : "";
									Integer numberOfParcels = createResponse.has("numberOfParcels")
											? (Integer) createResponse.get("numberOfParcels")
													: 0;
									Double weightKG = createResponse.has("weightKG")
											? createResponse.getDouble("weightKG")
													: 0;
									BigDecimal cashOnDelivery = null;
									String codCurrency = null;
									String codPaymentType = null;
									String parcelNumberFrom = createResponse.has("parcelNumberFrom")
											? createResponse.getString("parcelNumberFrom")
													: "";
									String parcelNumberTo = createResponse.has("parcelNumberTo")
											? createResponse.getString("parcelNumberTo")
													: "";

									RptBorderoBartolini rpt = (RptBorderoBartolini) Factory
											.createObject(RptBorderoBartolini.class);
									rpt.setBatchJobId(getBatchJob().getBatchJobId());
									rpt.setReportNr(1);
									rpt.setRigaJobId(rigaJobId);
									rpt.setNumericSenderRef(numericSenderReference);
									rpt.setAlphanumSendRef(alphanumericSenderReference);
									rpt.setConsigneeCompanyName(consigneeCompanyName);
									rpt.setConsigneeAdress(consigneeAddress);
									rpt.setConsigneeZipCode(consigneeZIPCode);
									rpt.setConsigneeCity(consigneeCity);
									rpt.setConsigneeProvAbbrev(consigneeProvinceAbbreviation);
									rpt.setConsigneeContryAbb(ddt.getCliente().getIdNazione());
									rpt.setNumberParcels(Integer.valueOf(numberOfParcels));
									rpt.setWeightKg(BigDecimal.valueOf(weightKG));
									rpt.setCashOnDelivery(cashOnDelivery);
									rpt.setCodCurrency(codCurrency);
									rpt.setCodPaymentType(codPaymentType);
									rpt.setParcelNumberFrom(parcelNumberFrom);
									rpt.setParcelNumberTo(parcelNumberTo);

									rpt.setIdAzienda(Azienda.getAziendaCorrente());

									String ragioneSociale = null;
									Azienda az = Azienda.elementWithKey(Azienda.getAziendaCorrente(),
											PersistentObject.NO_LOCK);
									if (az != null) {
										ragioneSociale = az.getDescrizione().getDescrizione();
									}

									rpt.setRagioneSociale(ragioneSociale);

									JSONObject createData = json.getJSONObject("createData");
									String customerCode = createData.getString("senderCustomerCode");

									rpt.setCustomerCode(customerCode);

									rcSave = rpt.save();
								}

								if (rcSave <= 0) {
									ConnectionManager.rollback();
								} else {
									ConnectionManager.commit();
									rigaJobId++;
								}
							} catch (SQLException e) {
								e.printStackTrace(Trace.excStream);
							}
						}else {
							output.println("DDT NON HA GENERATO ETICHETTE");
							JSONObject createResponse = bodyAsJSON.getJSONObject("createResponse");
							JSONObject executionMessage = createResponse.getJSONObject("executionMessage");
							if(executionMessage != null && executionMessage != JSONObject.NULL) {
								String codeDesc = executionMessage.getString("codeDesc");
								output.println(
										" ERRORI : " + codeDesc + ", "
												+ "}MESSAGE : " + executionMessage.getString("message"));
							}
						}
					} else {
						output.println(" ## DDT processato con status code = " + status.getStatusCode());
						JSONObject createResponse = bodyAsJSON.getJSONObject("createResponse");
						JSONObject executionMessage = createResponse.getJSONObject("executionMessage");
						String codeDesc = executionMessage.getString("codeDesc");
						output.println(
								" ERRORI : " + codeDesc + ", MESSAGE : " + executionMessage.getString("message"));
					}
				} else {
					output.println(" ## DDT gia' flaggato come processato, non verra' analizzato");
				}

				output.println();
			}
			output.println(" ** Termine... **");
		}
		return isOk;
	}

	@SuppressWarnings("rawtypes")
	public static boolean isIntercompanyEscludere(DdtTes ddt) {
		String[] parts = KeyHelper.unpackObjectKey(ddt.getKey());

		String stmt = "SELECT "+DocumentoVenditaTM.R_CAU_DOC_VEN+" FROM THIP.DOC_VEN_TES "
				+ "WHERE ID_AZIENDA = '" + parts[0] + "' " + "AND ANNO_BOLLA = '" + parts[1] + "' "
				+ "AND NUMERO_BOLLA = '" + parts[2] + "' " + "AND TIPO_BOLLA = '" + parts[3] + "' ";

		ResultSet rs = null;
		CachedStatement cs = null;
		String causale = null;
		try {
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			if(rs.next()) {
				causale = rs.getString(DocumentoVenditaTM.R_CAU_DOC_VEN);
			}
			if(causale != null) {
				String where = " "+ModelGenDocumentoVenditaTM.R_CAU_DOCACQ+" = '"+causale+"' "
						+ " AND "+ModelGenDocumentoVenditaTM.R_AZIENDA_DESTIN+" = '"+Azienda.getAziendaCorrente()+"' "
						+ " AND "+ModelGenDocumentoVenditaTM.USA_CLIENTE_ORD+" = '"+Column.TRUE_CHAR+"' ";
				Vector modelli = null;
				try {
					modelli = ModelGenDocumentoVendita.retrieveList(ModelGenDocumentoVendita.class, where, "", false);
					if(modelli.size() > 0) {
						return false;
					}
				} catch (Exception e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
					if(cs != null)
						cs.free();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * Serve per settare il FLAG_RIS_UTE_3 del DDT e di tutti i suoi DOC_VEN associati.<br>
	 * Questo perche' nel caso di ri-stampa della bolla il ddt viene cancellato e il flag ris ute 3 viene
	 * preso dal documento vendita associato, quindi provvedo a flaggare tutti i documenti vendita come processati,
	 * cosi che quando le procedure automatiche o manuali, di stampa bolla, girano il ddt ha il flag in stato congruo.<br>
	 * @author Daniele Signoroni 11/09/2024
	 * <p>
	 * Prima stesura.<br>
	 *
	 * </p>
	 * @param keyDDT
	 * @param flagRisUte3
	 * @return
	 */
	public static int flaggaComeProcessatiDocumentiVenditaAppartenentiDDT(String keyDDT, char flagRisUte3) {
		String[] parts = KeyHelper.unpackObjectKey(keyDDT);

		List<String> keysDocVenTes = new ArrayList<String>();

		String stmt = "SELECT  " + "ID_AZIENDA ,ID_ANNO_DOC ,ID_NUMERO_DOC  " + "FROM THIP.DOC_VEN_TES "
				+ "WHERE ID_AZIENDA = '" + parts[0] + "' " + "AND ANNO_BOLLA = '" + parts[1] + "' "
				+ "AND NUMERO_BOLLA = '" + parts[2] + "' " + "AND TIPO_BOLLA = '" + parts[3] + "' ";

		ResultSet rs = null;
		CachedStatement cs = null;

		try {
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			while(rs.next()) {
				keysDocVenTes.add(KeyHelper.buildObjectKey(new String[] {
						rs.getString(DocumentoVenditaTM.ID_AZIENDA),
						rs.getString(DocumentoVenditaTM.ID_ANNO_DOC),
						rs.getString(DocumentoVenditaTM.ID_NUMERO_DOC)
				}));
			}
			rs.close();
			cs.free();

			for(String key : keysDocVenTes) {

				parts = KeyHelper.unpackObjectKey(key);

				String updtStmt = "UPDATE THIP.DOC_VEN_TES SET FLAG_RIS_UTE_3 = '"+flagRisUte3+"' "
						+ "WHERE ID_AZIENDA = '"+parts[0]+"' AND ID_ANNO_DOC = '"+parts[1]+"' AND ID_NUMERO_DOC = '"+parts[2]+"' ";

				cs = new CachedStatement(updtStmt);

				int res = cs.executeUpdate();

				if(res < 0) {
					return ArticoloBase.UPDATE_KO;
				}

				cs.free();
			}

			parts = KeyHelper.unpackObjectKey(keyDDT);

			String stmtUpdtDDT = "UPDATE THIP.DDT_TES "
					+ "SET FLAG_RIS_UTE_3 = '"+flagRisUte3+"' "
					+ "WHERE ID_AZIENDA = '"+parts[0]+"' "
					+ "AND ID_ANNO_DDT = '"+parts[1]+"' "
					+ "AND ID_NUMERO_DDT = '"+parts[2]+"' "
					+ "AND TIPO_DDT = '"+parts[3]+"' ";

			cs = new CachedStatement(stmtUpdtDDT);

			int res = cs.executeUpdate();

			if(res < 0) {
				return ArticoloBase.UPDATE_KO;
			}

		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}

		return ArticoloBase.UPDATE_OK;
	}

	public static boolean isDdtIntercompany(DdtTes ddt) {
		String[] parts = KeyHelper.unpackObjectKey(ddt.getKey());

		List<String> keysDocVenTes = new ArrayList<String>();

		String stmt = "SELECT  " + "ID_AZIENDA ,ID_ANNO_DOC ,ID_NUMERO_DOC  " + "FROM THIP.DOC_VEN_TES "
				+ "WHERE ID_AZIENDA = '" + parts[0] + "' " + "AND ANNO_BOLLA = '" + parts[1] + "' "
				+ "AND NUMERO_BOLLA = '" + parts[2] + "' " + "AND TIPO_BOLLA = '" + parts[3] + "' ";

		ResultSet rs = null;
		CachedStatement cs = null;

		try {
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			while(rs.next()) {
				keysDocVenTes.add(KeyHelper.buildObjectKey(new String[] {
						rs.getString(DocumentoVenditaTM.ID_AZIENDA),
						rs.getString(DocumentoVenditaTM.ID_ANNO_DOC),
						rs.getString(DocumentoVenditaTM.ID_NUMERO_DOC)
				}));
			}
			for(String key : keysDocVenTes) {
				YDocumentoVendita docVen = (YDocumentoVendita) YDocumentoVendita.elementWithKey(YDocumentoVendita.class, key, PersistentObject.NO_LOCK);
				if(docVen != null && docVen.getCausale().isGestioneIntercompany()) {
					return true;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
					if(cs != null)
						cs.free();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<DdtTestataVendita> recuperaListaDDT() {
		SimpleDateFormat formatSQL = new SimpleDateFormat("yyyyMMdd");
		List<DdtTestataVendita> ddts = new ArrayList<DdtTestataVendita>();
		String WHERE_DDT_TES = " " + DdtTesTM.ID_AZIENDA + " = '" + Azienda.getAziendaCorrente() + "' " + "AND "
				+ DdtTesTM.DATA_DDT + " BETWEEN '" + formatSQL.format(getDateFrom()) + "' AND '"
				+ formatSQL.format(getDateTo()) + "' ";
		if (Azienda.getAziendaCorrente().equals("001")) {// se valvorobica
			WHERE_DDT_TES += " AND " + DdtTesTM.VETTORE1 + " = '001846' ";
		} else if (Azienda.getAziendaCorrente().equals("002")) {// se aellebi
			WHERE_DDT_TES += " AND " + DdtTesTM.VETTORE1 + " = '000024' ";
		}
		WHERE_DDT_TES += " AND "+DdtTesTM.TIPO_DDT+" = '"+DdtTes.TIPO_DDT_VEN+"' ";
		String ORDER_BY = " " + DdtTesTM.DATA_DDT + " ASC ";
		try {
			ddts = DdtTestataVendita.retrieveList(DdtTestataVendita.class, WHERE_DDT_TES, ORDER_BY, false);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		output.println("Where per DDT : " + WHERE_DDT_TES + " \n, trovati :" + ddts.size() + " DDT");
		return ddts;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YExpDDTBRT";
	}

}
