package it.valvorobica.thip.base.generale.ws;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.magazzino.generalemag.Lotto;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaLottoPrm;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaPrm;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.generaleVE.CondizioniDiVendita;
import it.thera.thip.vendite.proposteEvasione.CreaMessaggioErrore;
import it.valvorobica.thip.base.generale.ws.YCheckOutCarrello.Item;
import it.valvorobica.thip.base.portal.YCarrelloPortale;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 05/03/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    05/03/2026  DSSOF3   Prima stesura
 */

public class YConfermaReintegroContoDep extends YPortalGenRequestJSON {

	@SuppressWarnings("rawtypes")
	public Map risultatoWs = new HashMap();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		List errors = new ArrayList();
		JSONObject body = new JSONObject(toOutputJSON(getAppParams()));

		Gson gson = new Gson();
		Item[] items = gson.fromJson(this.getAppParams().get("items").toString(), Item[].class);
		if(items.length == 0) {
			errors.add("Non e' presente nessun articolo da reintegrare, aggiungerne almeno uno !");
		}
		String errorQta = YCheckOutCarrello.checkQuantita(items);
		if(errorQta != null) 
			errors.add(errorQta);

		if(errors.isEmpty()) {
			generaDocumentoReintegro(body, items);
		}

		m.put("errors", risultatoWs);
		return m;
	}

	@SuppressWarnings({ "unchecked" })
	public void generaDocumentoReintegro(JSONObject body, Item[] items) {
		DocumentoVendita dv = creaDocVenDaTestataJson(body);
		try {
			if(dv != null) {
				for (int i = 0; i < items.length; i++) {
					Item itemCheckOut = items[i];


					YCarrelloPortale item = (YCarrelloPortale) YCarrelloPortale.elementWithKey(
							YCarrelloPortale.class, itemCheckOut.getKey(), PersistentObject.NO_LOCK);
					if(item != null) {
						int rcDvr = creaDocVenRigDaDettaglioJson(dv, item);
						if(rcDvr > 0) {
							rcDvr = item.delete();
							if(rcDvr < 0) {
								((ArrayList<String>)risultatoWs.get("errori")).add(CreaMessaggioErrore.daRcAErrorMessage(rcDvr, null).getLongText());
								break;
							}
						}
					}
				}
				if(dv.getRighe().size() > 0) {
					int rc = dv.save();
					if(rc > 0) {
						ConnectionManager.commit();
					}else {
						((ArrayList<String>)risultatoWs.get("errori")).add(CreaMessaggioErrore.daRcAErrorMessage(rc, null).getLongText());
					}
				}else {
					((ArrayList<String>)risultatoWs.get("errori")).add("Il documento non ha righe");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	@SuppressWarnings("unchecked")
	protected int creaDocVenRigDaDettaglioJson(DocumentoVendita docVen, YCarrelloPortale item)
			throws SQLException {
		int rc = -1;
		DocumentoVenRigaPrm docVenRig = (DocumentoVenRigaPrm) Factory.createObject(DocumentoVenRigaPrm.class);
		docVenRig.setIdAzienda(Azienda.getAziendaCorrente());
		docVenRig.setTestata(docVen);
		docVenRig.setIdCauRig(docVen.getCausale().getIdCausaleRigaDocumVen());
		docVenRig.setStatoAvanzamento(StatoAvanzamento.DEFINITIVO);
		docVenRig.completaBO();
		docVenRig.setIdArticolo(item.getRArticolo());
		docVenRig.getQtaAttesaEvasione().setQuantitaInUMRif(item.getQuantita());
		docVenRig.setIdUMRif(docVenRig.getArticolo().getIdUMRiferimento());
		docVenRig.setIdUMPrm(docVenRig.getArticolo().getUMDefaultVendita().getIdUnitaMisura());
		docVenRig.ricalcoloQuantitaRiga();
		if (docVenRig.getArticolo() != null) {
			CondizioniDiVendita cdv = YGetDDTContoDep.recuperaCondizioniDiVendita(docVen, docVenRig, docVenRig.getArticolo().getIdUMRiferimento());
			if (cdv != null) {
				docVenRig.setScontoArticolo1(cdv.getScontoArticolo1());
				docVenRig.setScontoArticolo2(cdv.getScontoArticolo2());
				docVenRig.setMaggiorazione(cdv.getMaggiorazione());
				docVenRig.setPrezzo(cdv.getPrezzo());
				docVenRig.setPrezzoExtra(cdv.getPrezzoExtra());
				docVenRig.setSconto(cdv.getSconto());
				docVenRig.setRiferimentoUMPrezzo(cdv.getUMPrezzo());
				docVenRig.setNumeroImballo(cdv.getNumeroImballo());
				docVenRig.setProvenienzaPrezzo(cdv.getTipoTestata());
				docVenRig.setProvvigione1Agente(cdv.getProvvigioneAgente1()); // 71003
				docVenRig.setProvvigione1Subagente(cdv.getProvvigioneSubagente1()); // 71003
				docVenRig.setProvvigione2Agente(cdv.getProvvigioneAgente2()); // 71003
				docVenRig.setProvvigione2Subagente(cdv.getProvvigioneSubagente2()); // 71003
			}
			docVenRig.setIdAssogIVA(docVenRig.getArticolo().getIdAssoggettamentoIVA());
			YGetDDTContoDep.sistemaLotti(docVenRig.getIdArticolo(), docVenRig);
			docVen.getRighe().add(docVenRig);
			rc = docVenRig.save();
			if (rc >= 0) {
				Iterator<DocumentoVenRigaLottoPrm> iterRigheLotto = docVenRig.getRigheLotto().iterator();
				while (iterRigheLotto.hasNext()) {
					DocumentoVenRigaLottoPrm rigaLotto = iterRigheLotto.next();
					if (rigaLotto.getIdLotto().equals(Lotto.LOTTO_DUMMY))
						rigaLotto.delete();
				}
			}
		}
		return rc;
	}

	@SuppressWarnings("unchecked")
	protected DocumentoVendita creaDocVenDaTestataJson(JSONObject body) {
		JSONObject formData = new JSONObject(body.get("formData").toString());
		ArrayList<String> errori = (ArrayList<String>) risultatoWs.get("errori");
		if (errori == null)
			errori = new ArrayList<String>();
		DocumentoVendita docVen = null;
		try {
			//			String note = formData.getString("note");
			String numeroRiferimentoCliente = formData.getString("vsNr");
			//			String email = formData.getString("Email");
			docVen = (DocumentoVendita) Factory.createObject(DocumentoVendita.class);
			docVen.setIdAzienda(Azienda.getAziendaCorrente());
			docVen.setIdCau("VDP");
			docVen.setIdCliente(getUserPortalSession().getIdCliente());
			docVen.setNumeroOrdineIntestatario(numeroRiferimentoCliente);
			docVen.setDataOrdineIntestatario(TimeUtils.getCurrentDate());
			docVen.getNumeratoreHandler().setIdSerie("IT");
			docVen.completaBO();
			docVen.setDataConsegnaRichiesta(TimeUtils.getCurrentDate());
			docVen.setDataConsegnaConfermata(TimeUtils.getCurrentDate());
		} catch (Exception e) {
			errori.add("Eccezione :"+ e.getMessage());
			try {
				ConnectionManager.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace(Trace.excStream);
			}
			e.printStackTrace(Trace.excStream);
			docVen = null;
		}
		risultatoWs.put("errori", errori);
		return docVen;
	}
}
