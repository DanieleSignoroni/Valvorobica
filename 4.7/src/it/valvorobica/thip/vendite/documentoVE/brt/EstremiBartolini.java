package it.valvorobica.thip.vendite.documentoVE.brt;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;

import it.thera.thip.api.client.ApiClient;
import it.thera.thip.api.client.ApiRequest;
import it.thera.thip.api.client.ApiResponse;
import it.thera.thip.api.client.ApiRequest.Method;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.DdtTes;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 05/08/2024
 * <br><br>
 * <b>71XXX	DSSOF3	05/08/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class EstremiBartolini {

	protected String URL;

	protected String userID;

	protected String password;

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EstremiBartolini() {

	}

	public static EstremiBartolini estremiBartolini(String res) {
		EstremiBartolini estremi = (EstremiBartolini) Factory.createObject(EstremiBartolini.class);
		estremi.setURL(ResourceLoader.getString(res, "URL"));
		estremi.setUserID(ResourceLoader.getString(res, "userID"));
		estremi.setPassword(ResourceLoader.getString(res, "password"));
		return estremi;
	}

	public JSONObject getAccount() {
		JSONObject account = new JSONObject();
		account.put("userID", getUserID());
		account.put("password", getPassword());
		return account;
	}

	public JSONObject sendShipmentBartolini(DdtTes ddt,JSONObject json, EstremiBartolini estremiBrt) {
		JSONObject result = new JSONObject();
		Status status = Status.BAD_REQUEST;
		
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
						JSONArray eticsPth = new JSONArray();
						JSONArray labels = createResponse.getJSONObject("labels").getJSONArray("label");
						for(int i = 0; i < labels.length(); i ++) {
							JSONObject label = labels.getJSONObject(i);
							
							JSONObject eticPth = new JSONObject();
							
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
										eticPth.put("etichetta", etichetta);
										eticsPth.put(eticPth);
									}else {
										ConnectionManager.rollback();
									}
								}
							} catch (SQLException e) {
								e.printStackTrace(Trace.excStream);
							}
						}
						result.put("labels", eticsPth);
					}
				}
			}
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace(Trace.excStream);
		}
		result.put("status", status);
		return result;
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

}
