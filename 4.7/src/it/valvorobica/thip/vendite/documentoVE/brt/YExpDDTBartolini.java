package it.valvorobica.thip.vendite.documentoVE;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.batch.Printer;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.api.client.ApiClient;
import it.thera.thip.api.client.ApiRequest;
import it.thera.thip.api.client.ApiResponse;
import it.thera.thip.api.client.ApiRequest.Method;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.DdtTes;
import it.thera.thip.base.comuniVenAcq.DdtTesTM;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;
import it.valvorobica.thip.base.vendite.documentoVE.EtichetteBartolini;

/**
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Daniele Signoroni 05/08/2024 <br>
 *         <br>
 *         <b>71XXX DSSOF3 05/08/2024</b>
 *         <p>
 *         Prima stesura.<br>
 * 
 *         </p>
 */

public class YExpDDTBartolini extends BatchRunnable implements Authorizable {

	public static final char PROCESSATO = 'P';

	private static final String RES = "it.valvorobica.thip.vendite.documentoVE.resources.EstremiBartolini";

	protected Date iDateFrom;

	protected Date iDateTo;

	protected Proxy iStampante = new Proxy(com.thera.thermfw.batch.Printer.class);

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
	protected boolean run() {
		EstremiBartolini estremiBrt = EstremiBartolini.estremiBartolini(RES);
		output.println(" ** Esportazione dei DDT verso Bartolini **");
		boolean isOk = true;
		for (DdtTes ddt : recuperaListaDDT()) {
			output.println(" --> Processo DDT {" + ddt.getKey() + "} ");

			if(ddt.getFlagRisUte4() != PROCESSATO) {
				JSONObject json = recuperaJsonDDT(ddt, estremiBrt);

				ApiClient c = new ApiClient("");
				ApiRequest r = new ApiRequest(Method.POST, estremiBrt.getURL() + "/shipment");
				r.setContentType(MediaType.APPLICATION_JSON);
				r.setBody(json);
				try {
					ApiResponse response = c.send(r);
					if(response.success()) {
						String body = response.getBodyAsString();
						JSONObject bodyAsJSON = new JSONObject(body);
						if(bodyAsJSON.has("createResponse")) {
							JSONObject createResponse = bodyAsJSON.getJSONObject("createResponse");
							if(createResponse.has("labels")) {
								JSONArray labels = createResponse.getJSONObject("labels").getJSONArray("label");
								for(int i = 0; i < labels.length(); i ++) {
									JSONObject label = labels.getJSONObject(i);
									String stream = label.getString("stream");
									byte[] decodedBytes = Base64.getDecoder().decode(stream);
									//qui ho la stream con i bordero', quindi li stampo e li salvo a database cosi ho la ri-stampa
									EtichetteBartolini etichetta = (EtichetteBartolini) Factory.createObject(EtichetteBartolini.class);
									etichetta.setIdAzienda(Azienda.getAziendaCorrente());
									etichetta.setAnnoDDT(ddt.getIdAnnoDdt());
									etichetta.setNumeroDDT(ddt.getIdNumeroDdt());
									etichetta.setTipoDDT(ddt.getTipoDdt());
									try {
										int rc = etichetta.save();
										if(rc > 0) {
											rc = etichetta.getEtichettaBase64().setBytes(decodedBytes);
											if(rc > 0) {
												ConnectionManager.commit();
											}else {
												ConnectionManager.rollback();
											}

										}
									} catch (SQLException e) {
										e.printStackTrace(Trace.excStream);
									}
								}
							}
						}
					}else {
						output.println(" ## Chiamata terminata con code : "+response.getStatus().getStatusCode());
						output.println(" ** Body richiesto : "+json.toString());
					}
				} catch (KeyManagementException | NoSuchAlgorithmException e) {
					e.printStackTrace(Trace.excStream);
				}

				//Flaggo il ddt come processato e non lo ri-processero' piu'
				try {
					ddt.setFlagRisUte4(PROCESSATO);
					int rcSave = ddt.save();
					if(rcSave <= 0) {
						ConnectionManager.rollback();
					}else {
						ConnectionManager.commit();
					}
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}

			}else {
				output.println(" ## DDT gia' flaggato come processato, non verra' analizzato");
			}
			output.println();
		}
		output.println(" ** Termine... **");
		return isOk;
	}

	public JSONObject recuperaJsonDDT(DdtTes ddt, EstremiBartolini estremi) {

		JSONObject createData = new JSONObject();

		createData.put("network", "");
		createData.put("departureDepot", 173);
		//		createData.put("senderCustomerCode", 01731626);
		createData.put("senderCustomerCode", 1020119);
		if (ddt.getIdModConsegna() != null && ddt.getIdModConsegna().equals("01")) {
			createData.put("deliveryFreightTypeCode", "DAP");
		}
		if (ddt.getIdModConsegna() != null && ddt.getIdModConsegna().equals("02")) {
			createData.put("deliveryFreightTypeCode", "EXW");
		}
		if (ddt.getIdModConsegna() != null && ddt.getIdModConsegna().equals("05")) {
			createData.put("deliveryFreightTypeCode", "DAP");
		}
		createData.put("consigneeCompanyName", ddt.getCliente().getRagioneSociale());
		createData.put("consigneeAddress", ddt.getIndirizzoDen());
		createData.put("consigneeCountryAbbreviationISOAlpha2", ddt.getIdNazioneDen());
		createData.put("consigneeTelephone", ddt.getIndirizzo() != null ? ddt.getIndirizzo().getNumeroTelefono() : "");
		createData.put("consigneeEMail", ddt.getIndirizzo() != null ? ddt.getIndirizzo().getIndirizzoEmail() : "");
		createData.put("isAlertRequired", 0);
		createData.put("insuranceAmount", 0);
		createData.put("quantityToBeInvoiced", 0.0);
		createData.put("cashOnDelivery", 0);
		createData.put("isCODMandatory", "0");
		createData.put("notes", "DEBORA DAL SANTO");
		createData.put("declaredParcelValue", 0);
		createData.put("palletType1Number", 0);
		createData.put("palletType2Number", 0);
		createData.put("numericSenderReference", ddt.getBatchJobId());
		createData.put("alphanumericSenderReference", ddt.getIdNumeroDdt());
		createData.put("numberOfParcels", ddt.getNumeroColli());
		createData.put("weightKG", ddt.getPesoLordo());
		//		createData.put("volumeM3", ddt.getVolume().intValue());
		createData.put("consigneeZIPCode", ddt.getCapDen());
		createData.put("consigneeCity", ddt.getLocalitaDen());
		createData.put("consigneeProvinceAbbreviation", ddt.getIdProvinciaDen());
		createData.put("pudoId", "");

		JSONObject labelParameters = new JSONObject();
		labelParameters.put("outputType", "PDF");
		labelParameters.put("offsetX", 0);
		labelParameters.put("offsetY", 0);
		labelParameters.put("isBorderRequired", "1");
		labelParameters.put("isLogoRequired", "1");
		labelParameters.put("isBarcodeControlRowRequired", "0");

		JSONObject mainObject = new JSONObject();
		mainObject.put("account", estremi.getAccount());
		mainObject.put("createData", createData);
		mainObject.put("isLabelRequired", 1);
		mainObject.put("labelParameters", labelParameters);
		return mainObject;
	}

	@SuppressWarnings("unchecked")
	public List<DdtTestataVendita> recuperaListaDDT() {
		List<DdtTestataVendita> ddts = new ArrayList<DdtTestataVendita>();
		String WHERE_DDT_TES = " " + DdtTesTM.ID_AZIENDA + " = '" + Azienda.getAziendaCorrente() + "' " + "AND "
				+ DdtTesTM.DATA_DDT + " BETWEEN '" + getDateFrom() + "' AND '" + getDateTo() + "' ";
		String ORDER_BY = " " + DdtTesTM.DATA_DDT + " ASC ";
		WHERE_DDT_TES = " " + DdtTesTM.ID_AZIENDA + " = '" + Azienda.getAziendaCorrente() + "' AND "
				+ DdtTesTM.ID_NUMERO_DDT + " = 'DT  007701' ";
		try {
			ddts = DdtTestataVendita.retrieveList(DdtTestataVendita.class, WHERE_DDT_TES, ORDER_BY, false);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return ddts;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YExpDDTBRT";
	}

}
