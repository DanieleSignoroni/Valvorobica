package it.valvorobica.thip.logis.lgb;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.Proxy;

import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaLottoPrm;
import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaPrm;
import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.generale.IntegrazioneThipLogis;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.base.generale.UnitaMisura;
import it.thera.thip.base.listini.ListinoVendita;
import it.thera.thip.base.partner.Valuta;
import it.thera.thip.base.profilo.UtenteAzienda;
import it.thera.thip.logis.fis.Missione;
import it.thera.thip.logis.fis.Operatore;
import it.thera.thip.logis.fis.Postazione;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.logis.fis.TipoUds;
import it.thera.thip.logis.fis.Ubicazione;
import it.thera.thip.logis.lgb.RigaLista;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaLottoPrm;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaPrm;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaPrmTM;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.generaleVE.CondizioniDiVendita;
import it.thera.thip.vendite.generaleVE.RicercaCondizioniDiVendita;
import it.valvorobica.thip.base.articolo.YArticoloDatiMagaz;
import it.valvorobica.thip.base.cliente.YClienteVendita;
import it.valvorobica.thip.logis.bas.YCostantiValvo;
import it.valvorobica.thip.logis.fis.YEsecuzioneMissioni;
import it.valvorobica.thip.logis.fis.YMissione;
import it.valvorobica.thip.logis.fis.YUdsArticolo;
import it.valvorobica.thip.logis.fis.YUdsArticoloTM;
import it.valvorobica.thip.logis.rf.gui.YProcessaListeCompattoRF;
import it.valvorobica.thip.vendite.documentoVE.YDocumentoVenRigaPrm;
import it.valvorobica.thip.vendite.documentoVE.YDocumentoVendita;
import it.valvorobica.thip.vendite.generaleVE.YGestioneSpeseRigheVendita;

/**
 * <h1>Softre Solutions</h1>
 * <br></br>
 * @author Daniele Signoroni
 * <br></br>
 * <b>	70874	DSSOF3	21/01/2023</b>	<p>Prima stesura, sulla chiusura di una lista cancello e creo la spesa di imballo.</p>
 * <b>	70880	DSSOF3	25/01/2023</b>	<p>Aggiunta la descrizione articolo nella creazione della riga spesa imballo.</p>
 * <b>	70886	DSSOF3	27/01/2023</b>	<p>Identifico la riga spesa imballo tramite la causale del parametro personalizzato.</p>
 * <b>	71148	DSSOF3	21/06/2023</b>	<p>Aggiunto in <code>forzaChiudi()</code> la gestione KGM, ovvero lancio stored procedure
 * 											per aggiornare i lotti.
 * 										.</p>
 * <b>71195	TBSOF3	08/08/2023</b>	<br>Gestione peso variabile prima stesura<br>
 * <b>71545	DSSOF3	06/06/2024</b>	<p>Sistemare creazione riga spesa imballo.</p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72375	25/02/2026	DSSOF3	 Trasferimento fincantieri.
 */

public class YTestataLista extends TestataLista {

	protected Proxy iDocumentoVendita = new Proxy(it.thera.thip.vendite.documentoVE.DocumentoVendita.class);
	protected Proxy iDocumentoAcquisto = new Proxy(it.thera.thip.acquisti.documentoAC.DocumentoAcquisto.class);

	public YTestataLista() {
		super();
	}

	public void setDocumentoVendita(DocumentoVendita documentoVendita) {
		String idAzienda = null;
		if (documentoVendita != null) {
			idAzienda = KeyHelper.getTokenObjectKey(documentoVendita.getKey(), 1);
		}
		setCodiceSocieta(idAzienda);
		this.iDocumentoVendita.setObject(documentoVendita);
	}

	public DocumentoVendita getDocumentoVendita() {
		return (DocumentoVendita)iDocumentoVendita.getObject();
	}

	public void setDocumentoVenditaKey(String key) {
		iDocumentoVendita.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setCodiceSocieta(idAzienda);
	}

	public String getDocumentoVenditaKey() {
		return iDocumentoVendita.getKey();
	}

	public void setAnnoDocumentoVendita(String annoDocumentoVendita) {
		String key = iDocumentoVendita.getKey();
		iDocumentoVendita.setKey(KeyHelper.replaceTokenObjectKey(key , 2, annoDocumentoVendita));
	}

	public String getAnnoDocumentoVendita() {
		String key = iDocumentoVendita.getKey();
		String objAnnoDocumentoVendita = KeyHelper.getTokenObjectKey(key,2);
		return objAnnoDocumentoVendita;
	}

	public void setNumeroDocumentoVendita(String numeroDocumentoVendita) {
		String key = iDocumentoVendita.getKey();
		iDocumentoVendita.setKey(KeyHelper.replaceTokenObjectKey(key , 3, numeroDocumentoVendita));
	}

	public String getNumeroDocumentoVendita() {
		String key = iDocumentoVendita.getKey();
		String objNumeroDocumentoVendita = KeyHelper.getTokenObjectKey(key,3);
		return objNumeroDocumentoVendita;
	}

	public void setAnnoDocumentoAcquisto(String annoDocumento){
		String key = iDocumentoAcquisto.getKey();
		iDocumentoAcquisto.setKey(KeyHelper.replaceTokenObjectKey(key , 2, annoDocumento));
	}

	public String getAnnoDocumentoAcquisto(){
		String key = iDocumentoAcquisto.getKey();
		String objRAnnoDoc = KeyHelper.getTokenObjectKey(key,2);
		return objRAnnoDoc;
	}

	public void setNumeroDocumentoAcquisto(String numeroDocumento){
		String key = iDocumentoAcquisto.getKey();
		iDocumentoAcquisto.setKey(KeyHelper.replaceTokenObjectKey(key , 3, numeroDocumento));
	}

	public String getNumeroDocumentoAcquisto(){
		String key = iDocumentoAcquisto.getKey();
		String objRNumeroDoc = KeyHelper.getTokenObjectKey(key,3);
		return objRNumeroDoc;
	}

	public void setDocumentoAcquisto(DocumentoAcquisto doc){
		this.iDocumentoAcquisto.setObject(doc);
	}

	public DocumentoAcquisto getDocumentoAcquisto(){
		return (DocumentoAcquisto)iDocumentoAcquisto.getObject();
	}

	public void setDocumentoAcquistoKey(String key){
		iDocumentoAcquisto.setKey(key);
		setDirty();
	}

	public String getDocumentoAcquistoKey(){
		return iDocumentoAcquisto.getKey();
	}

	@Override
	public void setCodiceSocieta(String cod) {
		super.setCodiceSocieta(cod);
		iDocumentoVendita.setKey(KeyHelper.replaceTokenObjectKey(getDocumentoVenditaKey(), 1, cod));
		iDocumentoAcquisto.setKey(KeyHelper.replaceTokenObjectKey(getDocumentoAcquistoKey(), 1, cod));
	}

	@Override
	public boolean initializeOwnedObjects(boolean result) {
		boolean ret = super.initializeOwnedObjects(result);
		if (getCodice() != null && getCodice().startsWith(IntegrazioneThipLogis.VENDITA+"")){    	
			iDocumentoVendita.setKey(KeyHelper.buildObjectKey(new String[] {
					getCodiceSocieta(),getCodice().substring(1, 5),getCodice().substring(5)
			}));
		}
		if (getCodice() != null && getCodice().startsWith(IntegrazioneThipLogis.ACQUISTO+"")){    	
			iDocumentoAcquisto.setKey(KeyHelper.buildObjectKey(new String[] {
					getCodiceSocieta(),getCodice().substring(1, 5),getCodice().substring(5)
			}));
		}
		return ret;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector forzaChiudi() {
		Vector errori = super.forzaChiudi();
		if(errori.isEmpty()) {
			//71148 Inizio
			if(this.getStatoLista() == CHIUSO) {
				if (getCodice().startsWith(IntegrazioneThipLogis.VENDITA+"")){  
					riportaPesoVariabileSuRigheDocumento(null, null);
				}else if (getCodice().startsWith(IntegrazioneThipLogis.ACQUISTO+"")){  
					riportaPesoVariabileSuRigheDocumento(null, null);
				}
				if(isGestioneKgmAbilitato()) {
					gestioneKGM();
				}
			}
			try {
				boolean isOk = gestisciSpeseImballoTestataLista();
				if(!isOk) {
					errori.add(new ErrorMessage("BAS0000078","Impossibile cacellare/salvare la riga spese imballo automatica"));
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}

		}
		return errori;
	}

	@SuppressWarnings("rawtypes")
	public int riportaPesoVariabileSuRigheDocumento(Postazione pt, Operatore op) {
		int rcTot = 0;
		if(getCodice().startsWith(IntegrazioneThipLogis.VENDITA+"")){  
			Iterator iterRigheLista = getRigheLista().iterator();
			while(iterRigheLista.hasNext()) {
				YRigaLista rigaLista = (YRigaLista) iterRigheLista.next();
				List listaMissioniTerminate = null;
				listaMissioniTerminate = rigaLista.getMissioniTerminate(null,null,null);
				Iterator iterMissioniTerminate = listaMissioniTerminate.iterator();
				DocumentoVenRigaPrm rigaDocVen = null;
				BigDecimal pesoVarTot = BigDecimal.ZERO;
				while(iterMissioniTerminate.hasNext()) {
					YMissione missione = (YMissione) iterMissioniTerminate.next();
					if(missione.getDocumentoVenditaRiga() != null
							&& ((YArticoloDatiMagaz)missione.getDocumentoVenditaRiga().getArticolo().getArticoloDatiMagaz()).isGestionePesoVariabile()) {
						if(rigaDocVen == null)
							rigaDocVen = missione.getDocumentoVenditaRiga();
						Iterator iterLotti = rigaDocVen.getRigheLotto().iterator();
						while(iterLotti.hasNext()) {
							DocumentoVenRigaLottoPrm rigaPrmLotto = (DocumentoVenRigaLottoPrm) iterLotti.next();
							if(rigaPrmLotto.getIdLotto().equals(missione.getLotto1())) {
								rigaPrmLotto.getQtaAttesaEvasione().setQuantitaInUMRif(BigDecimal.ZERO);
							}
						}
					}
				}
				iterMissioniTerminate = listaMissioniTerminate.iterator();
				while(iterMissioniTerminate.hasNext()) {
					YMissione missione = (YMissione) iterMissioniTerminate.next();
					if(rigaDocVen != null
							&& ((YArticoloDatiMagaz)rigaDocVen.getArticolo().getArticoloDatiMagaz()).isGestionePesoVariabile()) {
						Iterator iterLotti = rigaDocVen.getRigheLotto().iterator();
						while(iterLotti.hasNext()) {
							DocumentoVenRigaLottoPrm rigaPrmLotto = (DocumentoVenRigaLottoPrm) iterLotti.next();
							if(rigaPrmLotto.getIdLotto().equals(missione.getLotto1())) {
								BigDecimal qtaAttesaEvasione = rigaPrmLotto.getQtaAttesaEvasione().getQuantitaInUMRif();
								qtaAttesaEvasione = qtaAttesaEvasione.add(missione.getQta5Evasa());
								rigaPrmLotto.getQtaAttesaEvasione().setQuantitaInUMRif(qtaAttesaEvasione);
							}
						}
						pesoVarTot = pesoVarTot.add(missione.getQta5Evasa());
					}
				}
				if(rigaDocVen != null) {
					try {
						rigaDocVen.getQtaAttesaEvasione().setQuantitaInUMRif(pesoVarTot);
						int rc = rigaDocVen.save();
						if (rc >= 0)
							rcTot += rc;
						else
							rcTot = rc;
					} catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
			}
		}else if(getCodice().startsWith(IntegrazioneThipLogis.ACQUISTO+"")){
			Iterator iterRigheLista = getRigheLista().iterator();
			while(iterRigheLista.hasNext()) {
				YRigaLista rigaLista = (YRigaLista) iterRigheLista.next();
				List listaMissioniTerminate = null;
				listaMissioniTerminate = rigaLista.getMissioniTerminate(null,null,null);
				Iterator iterMissioniTerminate = listaMissioniTerminate.iterator();
				DocumentoAcqRigaPrm rigaDocAcq = null;
				BigDecimal pesoVarTot = BigDecimal.ZERO;
				while(iterMissioniTerminate.hasNext()) {
					YMissione missione = (YMissione) iterMissioniTerminate.next();
					if(missione.getDocumentoAcquistoRiga() != null
							&& ((YArticoloDatiMagaz)missione.getDocumentoAcquistoRiga().getArticolo().getArticoloDatiMagaz()).isGestionePesoVariabile()) {
						if(rigaDocAcq == null)
							rigaDocAcq = missione.getDocumentoAcquistoRiga();
						Iterator iterLotti = rigaDocAcq.getRigheLotto().iterator();
						while(iterLotti.hasNext()) {
							DocumentoAcqRigaLottoPrm rigaPrmLotto = (DocumentoAcqRigaLottoPrm) iterLotti.next();
							if(rigaPrmLotto.getIdLotto().equals(missione.getLotto1())) {
								rigaPrmLotto.getQtaAttesaEvasione().setQuantitaInUMRif(BigDecimal.ZERO);
							}
						}
					}
				}
				iterMissioniTerminate = listaMissioniTerminate.iterator();
				while(iterMissioniTerminate.hasNext()) {
					YMissione missione = (YMissione) iterMissioniTerminate.next();
					if(rigaDocAcq != null
							&& ((YArticoloDatiMagaz)rigaDocAcq.getArticolo().getArticoloDatiMagaz()).isGestionePesoVariabile()) {
						Iterator iterLotti = rigaDocAcq.getRigheLotto().iterator();
						while(iterLotti.hasNext()) {
							DocumentoAcqRigaLottoPrm rigaPrmLotto = (DocumentoAcqRigaLottoPrm) iterLotti.next();
							if(rigaPrmLotto.getIdLotto().equals(missione.getLotto1())) {
								BigDecimal qtaAttesaEvasione = rigaPrmLotto.getQtaAttesaEvasione().getQuantitaInUMRif();
								qtaAttesaEvasione = qtaAttesaEvasione.add(missione.getQta5Evasa());
								rigaPrmLotto.getQtaAttesaEvasione().setQuantitaInUMRif(qtaAttesaEvasione);
							}
						}
						pesoVarTot = pesoVarTot.add(missione.getQta5Evasa());
					}
				}
				if(rigaDocAcq != null) {
					try {
						rigaDocAcq.getQtaAttesaEvasione().setQuantitaInUMRif(pesoVarTot);
						int rc = rigaDocAcq.save();
						if (rc >= 0)
							rcTot += rc;
						else
							rcTot = rc;
					} catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
			}
		}
		return rcTot;
	}

	/**
	 * <h1>Gestione delle spese imballo automatiche</h1>
	 * <br>
	 * Se le seguenti condizioni sono soddisfatte : 
	 * <list>
	 * 	<li>Sono in una lista di vendita</li>
	 *  <li>Il parametro di generazione automatica spese e' acceso</li>
	 *  <li>Il parametro contiene la causale di default</li>
	 *  <li>La causale di riga default e' una di quelle selezionate nella causale di testata</li>
	 *  <li>La modalita' di pagamento non e' esclusa dal calcolo</li>
	 *  <li>Sul cliente non e' indicato di escludere la spesa</li>
	 * </list>
	 * Se le condizioni sono soddisfatte prendo l'elenco delle uds dalla lista e per ognuna di essa cerco il prezzo da listino,
	 * per dato cliente.<br>
	 * 
	 * Se il totale e' maggiore di zero allora genero la riga spesa imballo. Se questa esiste gia' prima la cancello.
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean gestisciSpeseImballoTestataLista() throws SQLException {
		boolean isOk = true;
		if(getCodice().startsWith(IntegrazioneThipLogis.VENDITA+"")) {
			Trace.excStream.println("Gestione spese imballo lista ("+getCodice()+") inizio -----> ");
			YDocumentoVendita documentoVE = (YDocumentoVendita) getDocumentoVendita();
			if(documentoVE != null) {
				if(isInserimentoSpeseImballoAutomaticoAbilitato()
						&& getCausaleSpeseImballoAutomaticaDefault() != null 
						&& documentoVE.isCausaleRigaSelezionata(KeyHelper.buildObjectKey(new String[] {
								documentoVE.getIdAzienda(),getCausaleSpeseImballoAutomaticaDefault()}
								))
						&& documentoVE.isCalcoloRigheSpeseTrasportoAbilitato()
						&& !((YClienteVendita)documentoVE.getCliente()).isEscludiSpeseImb()) {
					Trace.excStream.println("Spese imballo automatiche abilitate per lista = "+getCodice());
					Vector elencoUDS = TestataLista.getElencoUds(this);
					Trace.excStream.println("Elenco UDS = "+elencoUDS.toString());
					BigDecimal valoreTotaleRigaImballo = BigDecimal.ZERO;
					for(int i = 0; i < elencoUDS.size() ; i++) {
						String codUds = (String) elencoUDS.get(i);
						try {
							TestataUds udsTes = (TestataUds) TestataUds.elementWithKey(TestataUds.class, codUds, 0);
							if(udsTes != null && udsTes.getTipoUds() != null) {
								Articolo articoloSpesa = getArticoloThipTrascodificatoUds(udsTes.getTipoUds());
								if(articoloSpesa != null) {
									CondizioniDiVendita cdv = recuperaCondizioniDiVendita(documentoVE.getListinoPrezzi(), documentoVE.getCliente(), articoloSpesa, "nr");
									if(cdv != null) {
										valoreTotaleRigaImballo = valoreTotaleRigaImballo.add(cdv.getPrezzo() != null ? cdv.getPrezzo() : BigDecimal.ZERO);
									}
								}
							}
						}catch (Exception e) {
							e.printStackTrace(Trace.excStream);
						}
					}
					if(valoreTotaleRigaImballo.compareTo(BigDecimal.ZERO) > 0) {
						Trace.excStream.println("Valore spese imballo = " +valoreTotaleRigaImballo);
						String where = " "+DocumentoVenRigaPrmTM.ID_AZIENDA+" = '" + Azienda.getAziendaCorrente() + "' AND "+DocumentoVenRigaPrmTM.ID_ANNO_DOC+" = '"
								+ documentoVE.getAnnoDocumento() + "'" + " AND "+DocumentoVenRigaPrmTM.ID_NUMERO_DOC+" = '" + documentoVE.getNumeroDocumento()
								+ "' " + " AND "+DocumentoVenRigaPrmTM.R_CAU_RIG_DOCVEN+" = '" + getCausaleSpeseImballoAutomaticaDefault() + "'";
						List<YDocumentoVenRigaPrm> lst;
						try {
							lst = YDocumentoVenRigaPrm.retrieveList(YDocumentoVenRigaPrm.class, where,"", false);
							if (lst.size() > 0) {
								YDocumentoVenRigaPrm rigaImballoEsistente = lst.get(0);
								int rc = rigaImballoEsistente.delete();
								Trace.excStream.println("Riga spesa gia' esistente rc cancellazione = " +rc);
								if(rc < 0) {
									isOk = false;
								}
							}
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
							e.printStackTrace(Trace.excStream);
						}
						if(isOk) {
							YDocumentoVenRigaPrm rigaImballoNuova = YGestioneSpeseRigheVendita.getRigaSpesaImballo(documentoVE, valoreTotaleRigaImballo, getCausaleSpeseImballoAutomaticaDefault());
							rigaImballoNuova.setSalvaTestata(false);
							int rc = rigaImballoNuova.save();
							Trace.excStream.println("Riga spesa rc inserimento = " +rc);
							if(rc < 0) {
								isOk = false;
							}
						}
					}
				}else {
					Trace.excStream.println("Spese imballo automatiche NON abilitate per lista = "+getCodice());
				}
			}
			Trace.excStream.println("<-------- Gestione spese imballo lista ("+getCodice()+") fine");
		}
		return isOk;
	}

	@SuppressWarnings("rawtypes")
	public DocumentoVenRigaPrm trovaDocumentoVenRigaPrmSpesaImballoAutomatica(DocumentoVendita docVen) {
		Iterator iterRighe = docVen.getRighe().iterator();
		while(iterRighe.hasNext()) {
			DocumentoVenRigaPrm riga = (DocumentoVenRigaPrm) iterRighe.next();
			if(riga.getIdCauRig().equals(getCausaleSpeseImballoAutomaticaDefault())) {
				return riga;
			}
		}
		return null;
	}

	public CondizioniDiVendita recuperaCondizioniDiVendita(ListinoVendita listino, ClienteVendita cliente, Articolo articolo, String idUm) {
		CondizioniDiVendita cdv = null;
		try {
			RicercaCondizioniDiVendita rcdv = new RicercaCondizioniDiVendita();
			String key = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), idUm});
			UnitaMisura um = (UnitaMisura) UnitaMisura.elementWithKey(UnitaMisura.class, key, 0);
			cdv = rcdv.ricercaCondizioniDiVendita(		//recupero le condizioni di vendita per avere il prezzo del contenitore
					Azienda.getAziendaCorrente(),//String idAzienda
					listino,//ListinoVendita listino
					cliente,//ClienteVendita cliente
					articolo,//Articolo articolo
					um,//UnitaMisura unita
					BigDecimal.ONE,//BigDecimal quantita
					null,//BigDecimal importo
					null,//ModalitaPagamento modalita
					TimeUtils.getCurrentDate(),//java.sql.Date dataValidita
					null,//Agente agente
					null,//Agente subagente
					null,//UnitaMisura unitaMag
					null,//BigDecimal quantitaMag
					(Valuta) Valuta.elementWithKey(Valuta.class, "EUR", 0),//Valuta valuta
					null,//UnitaMisura umSecMag
					null);
		}catch(SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return cdv;
	}

	/**
	 * Controlla un parametro personalizzato per azienda, se acceso allora la generazione automatica delle spese di imballo e' abilitata
	 * @return un booleano che indica se le spese di imballo vanno generate automaticamente o no
	 */
	public boolean isInserimentoSpeseImballoAutomaticoAbilitato() {
		boolean abilitato = false;
		String attivaPersonalizzazione = ParametroPsn.getValoreParametroPsn("YAddImballoDefault"+getCodiceSocieta(), "SpeseImballo"+getCodiceSocieta());
		if(attivaPersonalizzazione != null && attivaPersonalizzazione.equals("Y")) {
			abilitato = true;
		}
		return abilitato;
	}

	public String getCausaleSpeseImballoAutomaticaDefault() {
		return ParametroPsn.getValoreParametroPsn("YcauRigSpeseImballo", "CausaleSpesaImballo");
	}

	public boolean isGestioneKgmAbilitato() {
		boolean abilitato = false;
		String attivaPersonalizzazione = getValoreParametroAbilitazioneKgm();
		if(attivaPersonalizzazione != null && attivaPersonalizzazione.equals("Y")) {
			abilitato = true;
		}
		return abilitato;
	}

	public String getValoreParametroAbilitazioneKgm() {
		return ParametroPsn.getValoreParametroPsn("YGestioneKGM", "Attivazione gestione KGM");
	}

	@SuppressWarnings("rawtypes")
	public Articolo getArticoloThipTrascodificatoUds(TipoUds tipoUds) {
		Articolo articolo = null;
		try {
			String where = " "+YUdsArticoloTM.ID_AZIENDA+" = '" + getCodiceSocieta() + "' AND "+YUdsArticoloTM.TIPO_UDS+" = '" + tipoUds.getCodice() +"'";
			Vector list = YUdsArticolo.retrieveList(where, "", false);
			if(list.size() > 0) {
				YUdsArticolo udsArticolo = (YUdsArticolo) list.get(0);
				if(udsArticolo != null) {
					articolo = udsArticolo.getRelarticolo();
				}
			}
		}catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return articolo;
	}

	/**
	 * <h1>Softre Solutions</h1>
	 * @author Daniele Signoroni	21/06/2023
	 * <br><br>
	 * <b>71148	DSSOF3	21/06/2023</b>	<p>Specchio da {@linkplain YProcessaListeCompattoRF}.</p>
	 */
	@SuppressWarnings("rawtypes")
	public void gestioneKGM() {
		if(getCodice().startsWith(IntegrazioneThipLogis.VENDITA+"")) {
			List righe = getRigheLista();
			boolean presente = false;
			for(int i = 0; i < righe.size(); i++) {
				RigaLista riga = (RigaLista) righe.get(i);
				if(riga != null) {
					if(riga.getTipoRigaLista() == RigaLista.KIT_MAG) {
						presente = true;
					}
				}
				if(presente)
					break;
			}
			if(presente) {
				CachedStatement cs = null;
				try {
					String execute = "EXEC SOFTRE.Y_GESTIONE_KGM_P01 '" + Azienda.getAziendaCorrente() + "' , '" + getCodice() + "' , '" +  UtenteAzienda.getUtenteAziendaConnesso().getId() + "'"; //71114 aggiunto il codice utente
					cs = new CachedStatement(execute);
					cs.executeUpdate();
				}catch(SQLException e) {
					e.printStackTrace(Trace.excStream);
				}finally {
					try {
						if(cs != null)
							cs.free();
					}catch(SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
			}
		}
	}

	//72375 <
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List eseguiMissioniTrasferimentoFincantieri() throws SQLException {
		YEsecuzioneMissioni esecuzioneMissioni = (YEsecuzioneMissioni) Factory.createObject(YEsecuzioneMissioni.class);
		List errors = new ArrayList();
		esecuzioneMissioni.setMagFisico(getMagFisicoImpegno());
		esecuzioneMissioni.setOperatore(YCostantiValvo.getOperatoreGenerico(getCodiceMagFisicoImpegno()));
		esecuzioneMissioni.setPostazione(YCostantiValvo.getPostazioneNonGestita(getCodiceMagFisicoImpegno()));
		esecuzioneMissioni.setAreaLavoro(esecuzioneMissioni.getPostazione().getAreaLavoro());
		esecuzioneMissioni.setTipoMacchina(esecuzioneMissioni.getPostazione().getTipoMacchina());
		esecuzioneMissioni.getElencoTestate().add(this);
		esecuzioneMissioni.creaAndEseguiMissioni();
		Vector elMiss = esecuzioneMissioni.getElencoMissioni();
		if(elMiss.size() > 0) {
			for (Iterator iterator = elMiss.iterator(); iterator.hasNext();) {
				Missione m = (Missione) iterator.next();
				errors = esecuzioneMissioni.confermaMissione(m, m.getQta1Richiesta(), m.getQta1Richiesta(), null);
			}
			if(errors.isEmpty()) {
				retrieve();
				if(getLivelloEstrazione() == PARZIALE) {
					errors = regressioneLista();
					if(errors.isEmpty()) {
						ConnectionManager.commit();
					}
					errors.add(new ErrorMessage("BAS0000078","Livello prelievo PARZIALE, le giacenze trovate non coprono totalmente le quantita' richieste, sistemare prima il documento"));
				}else {
					if(getStatoLista() == APERTO) {
						errors = forzaChiudi();
						if(errors.isEmpty()) {
							ConnectionManager.commit();
						}
					}
				}
			}
		}else {
			errors.add(new ErrorMessage("BAS0000078","Non ci sono missioni da fare per la lista selezionata"));
		}
		return errors;
	}

	@SuppressWarnings("rawtypes")
	public List regressioneLista() throws SQLException {
		Postazione pt = YCostantiValvo.getPostazioneNonGestita(getCodiceMagFisicoImpegno());
		Operatore op = YCostantiValvo.getOperatoreGenerico(getCodiceMagFisicoImpegno());
		List errors = new ArrayList();
		setStatoLista(TestataLista.APERTO);
		Iterator iterRighe = getRigheLista().iterator();
		while(iterRighe.hasNext()) {
			YRigaLista riga = (YRigaLista) iterRighe.next();
			riga.setStatoRigaLista(APERTO);
			Vector elMiss = riga.getMissioniTerminate(pt, op, null);
			for (Iterator iterator = elMiss.iterator(); iterator.hasNext();) {
				Missione missione = (Missione) iterator.next();
				String codiceUbicazione = ParametroPsn.getValoreParametroPsn("YUbicazioneAnnullamento", "Ubicazione annullamento missione");
				if(codiceUbicazione != null) {
					codiceUbicazione = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), codiceUbicazione} );
					Ubicazione ubicazione = (Ubicazione) Ubicazione.elementWithKey(Ubicazione.class, codiceUbicazione, 0);
					if(ubicazione != null) {
						missione.setMappaUdcInv(null);
						missione.setCodiceMappaUdcInv(null);
						missione.setMappaUdc(null);
						missione.setCodiceMappaUdc(null);
						errors = missione.annulloConfermata(ubicazione);
						if(!errors.isEmpty()) {
							return errors;
						}
					}
				}

			}
		}
		return errors;
	}
	//72375 >
}