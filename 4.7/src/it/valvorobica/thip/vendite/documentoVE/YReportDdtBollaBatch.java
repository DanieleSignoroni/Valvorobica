package it.valvorobica.thip.vendite.documentoVE;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchOptions;
import com.thera.thermfw.batch.BatchService;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.ErrorCodes;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.ModalitaConsegna;
import it.thera.thip.base.comuniVenAcq.AzioneMagazzino;
import it.thera.thip.base.comuniVenAcq.ReportDdtBollaTestata;
import it.thera.thip.base.documenti.StatoAttivita;
import it.thera.thip.base.generale.CfgLogTxMov;
import it.thera.thip.base.generale.CfgLogTxMovTM;
import it.thera.thip.base.generale.IntegrazioneThipLogis;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.cs.ThipException;
import it.thera.thip.logis.fis.AnagraficaUdc;
import it.thera.thip.logis.fis.InserimentoMovimenti;
import it.thera.thip.logis.fis.MappaUdc;
import it.thera.thip.logis.fis.Missione;
import it.thera.thip.logis.fis.Operatore;
import it.thera.thip.logis.fis.OperazioneMovimento;
import it.thera.thip.logis.fis.Postazione;
import it.thera.thip.logis.fis.Saldo;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.logis.fis.TestataUdsTM;
import it.thera.thip.logis.fis.TipoUdc;
import it.thera.thip.logis.fis.Ubicazione;
import it.thera.thip.logis.lgb.MagLogico;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.documentoVE.DocumentoVenditaTM;
import it.thera.thip.vendite.documentoVE.ReportDdtBollaBatch;
import it.thera.thip.vendite.generaleVE.CausaleRigaDocVen;
import it.thera.thip.vendite.generaleVE.CausaleRigaVendita;
import it.thera.thip.vendite.generaleVE.TipoDocumento;
import it.valvorobica.thip.base.articolo.cambioArticolo.web.YCambioArticoloSave;
import it.valvorobica.thip.base.azienda.YOggettinoUdsStampaDDT;
import it.valvorobica.thip.base.cliente.YClienteVendita;
import it.valvorobica.thip.logis.bas.YCostantiValvo;
import it.valvorobica.thip.logis.lgb.YRigaLista;
import it.valvorobica.thip.logis.lgb.YTestataLista;
import it.valvorobica.thip.logis.rf.gui.YProcessaListeCompattoRF;
import it.valvorobica.thip.vendite.documentoVE.susa.IntegrazioneCorriereSusa;
import it.valvorobica.thip.vendite.generaleVE.YGestioneSpeseRigheVendita;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Thomas Brescianini 13/01/2023
 *	<br></br>
 *	<b>70860	TBSOF3	13/01/2023</b>	<p>Trasformazione UDS in UDC in stampa DDT.</p>
 *	<b>70877	DSSOF3	23/01/2023</b>	<p>Codifico l'oggetto OperazioneMovimento tramite il codice dei parametro e l'azienda corrente,
 *											necessario dato che per le aziende ho oggetti differenti.
 *											Aggiunti finally per chiusura CachedStatement e ResultSet.</p>
 *	<b>70879	AGSOF3	25/01/2023</b>	<p>Desc nei commenti</p>
 *	<b>70899	AGSOF3	31/01/2023</b>	<p>Eseguo l'azione <code>gestioneSpese()</code> solo in (STAMPA,RISTAMPA) e solo se il parametro lo consente.
 *											Cancello righe spese o certificato gia' esistenti dal documento vendita.
 *											Cancello righe spese o certificato gia' eisstenti dall'rpt.</p>
 *	<b>70900	DSSOF3	01/02/2023</b>	<p>Eseguo l'azione <code>gestioneSpese()</code> anche in caso di (RISTAMPA_COLLEGATI).
 *											Aggiunto controllo che il cliente di vendita NON escluda le spese certificato (In questo caso non creo la riga).</p>
 *  <b>70903	DSSOF3	02/02/2023</b>	<p>Desc sotto.</p>
 *	<b>70940	TBSOF3	14/02/2023</b>	<p>Aggiunto delay di 2 secondi prima della stampa da terminalino</p>
 *  <b>70967	AGSOF3	23/02/2023</b>	<p>Se la stampa bolla ï¿½ stata sottomessa da terminalino allora sottometto anche la stampa packing list</p>
 *  <b>70973	DSSOF3	28/02/2023</b>	<p>La stampa del packing list e' stata sostituita con la stampa dell'UDS.</p>
 *  <b>71051	TBSOF3	19/04/2023</b>	<p>Gestione dati inizio pagamento.</p>
 *  <b>71054	DSSOF3	24/04/2023</b>	<p>Introdotta soglia minima sul cliente vendita:</p>
 *  										{@link YClienteVendita#getSogliaMinimaSpeseTrasp()},per innescare la generazione delle spese di trasporto automatiche.</p>
 *  <b>71058	TBSOF3	27/04/2023</b>	<p>Modifiche alla gestione dati inizio pagamento</p>
 *  <b>71068	DSSOF3	04/05/2023</b>	<p>Gestione data inizio pagamento - aggiunto controllo sulla modalita di pagamento in seguito all'aggiunta della collezione:</p>
 *  										{@link YPsnDatiGestDataIniPagam#getYListaModPagamDataIniP()}.</p>
 *  										Soglia minima - se il campo sul cliente e' null o zero cerco lo stesso di creare la spesa di trasporto.</p>
 *  <b>71076	DSSOF3	10/05/2023</b>	<p>Gestione UDS - UDC, nella creazione di movimento, anagrafica e mappa udc recupero il codice ubicazione dalla tabella
 *  										LOGIS.LUDS_TESTA, nel caso in cui questo valore sia = FCTRA prendo il valore dell'ubicazione comunque dal parametro
 *  										,in caso contrario prendo il valore della colonna e lo uso per il movimento, per la mappa e per l'anagrafica.<br>
 *  										<b>NB:</b> Il saldo viene comunque reperito con il valore del parametro pers.</p>
 *  <b>71193	DSSOF3	07/08/2023</b>	<p>Nella {@link #gestioneDataInizioPagamento()} il controllo viene fatto sulla data consegna confermata di testata<br>
 *  										 e non piu' sulla data consegna confermata delle righe.
 *  									</p>
 *  <b>71273	DSSOF3	25/10/2023</b>	<p><h1>Generazione righe spese certificati</h1>
 *  										Se il documento ha tutte righe con microfamiglia = NONRICH non inserisco la spesa certificato.<br>
 *  										Implementato con metodo {@link #isAbilitatoPerGenerazioneRigaSpesaCertificato(YDocumentoVendita)}
 *  									</p>
 *  <b>71472	DSSOF3	15/03/2024</b>
 *  <p>
 *  Se {@link #isDaTerminalino()} setto {@link #setStampaInLingua(boolean)} a true.<br>
 *  </p>
 */

public class YReportDdtBollaBatch extends ReportDdtBollaBatch{

	protected boolean iDaTerminalino;

	protected String iCodiceUds;

	public String getCodiceUds() {
		return iCodiceUds;
	}

	public void setCodiceUds(String iCodiceUds) {
		this.iCodiceUds = iCodiceUds;
	}

	public boolean isDaTerminalino() {//70949
		boolean is = false;
		if(getBatchJob() != null && getBatchJob().getDescription() != null)
			if(getBatchJob().getDescription().contains("da terminalino"))
				is = true;
		return is;
		//		return iDaTerminalino;
	}

	public void setDaTerminalino(boolean iDaTerminalino) {
		this.iDaTerminalino = iDaTerminalino;
	}

	@Override
	protected void gestioneOutputBatchPers(DocumentoVendita docVen) {
		super.gestioneOutputBatchPers(docVen);
		try {
			//gestioneUDC(docVen);//70860 TBSOF3 Trasformazione UDS in UDC in stampa DDT
			if(docVen != null) {
				TestataLista tl = YCostantiValvo.testataListaDocumentoVendita(docVen, PersistentObject.NO_LOCK);
				if(tl != null && tl.getCodiceTipoLista().equals(YCostantiValvo.codTipoListaTrasferimentoFincantieri())) {
					trasferimentoFincantieri(docVen, tl);
				}
			}
		}catch (Exception e) {//catturo tutto
			e.printStackTrace(Trace.excStream);
		}
	}

	@Override
	public boolean createReportInternal(String w) {
		boolean isOk = true;
		try {
			if(isDaTerminalino()) {
				//se vengo da terminalino stampo sempre in lingua
				setStampaInLingua(true);
				try {
					Thread.sleep(10000);
					//sottomettiStampaUds();
				} catch (InterruptedException e1) {
					e1.printStackTrace(Trace.excStream);
				}
			}
		}catch(Exception e) {
			e.printStackTrace(Trace.excStream);
		}finally{
			if(this.getAzione() == ReportDdtBollaTestata.STAMPA) {
				//71051 gestione data ini. pagamento
				gestioneDataInizioPagamento();
			}
			isOk = super.createReportInternal(w);
			try {
				String gestioneSpese = ParametroPsn.getValoreParametroPsn("YValvo.SpeseAutomatiche", "GestioneSpeseStampaBolla");
				if(gestioneSpese != null && gestioneSpese.equals("Y"))//se il param psn ï¿½ abilitato
					if(this.getAzione() ==  ReportDdtBollaTestata.STAMPA || 
					this.getAzione() ==  ReportDdtBollaTestata.RISTAMPA ||
					this.getAzione() ==  ReportDdtBollaTestata.RIS_COL ) //se sono in stampa o ristampa o ristampa collegati
						gestioneSpese(isOk);
			}catch(Exception e) {//catturo tutto
				e.printStackTrace(Trace.excStream);
			}
			if(isDaTerminalino()) {
				try {
					Thread.sleep(10000);
					sottomettiStampaUds();
				} catch (InterruptedException e1) {
					e1.printStackTrace(Trace.excStream);
				} catch (Exception e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return isOk;
	}


	/**
	 * 71051 TBSOF3 gestione compilazione data inizio pagamento nella testata dei documenti di vendita
	 */
	@SuppressWarnings({ "rawtypes" })
	protected void gestioneDataInizioPagamento() {
		YPsnDatiGestDataIniPagam gestioneDataIniPagamento = YPsnDatiGestDataIniPagam.getCurrentPersDatiDataIniPagam();
		if(gestioneDataIniPagamento != null){
			if(gestioneDataIniPagamento.getAttivaCalcolo()) {	//controllo che la personalizzaizone sia attiva
				Vector elencoDocVen = getDocumentiVendita();	//prendo la lista dei documenti di vendita a di cui si deve stampare la bolla
				Iterator listaDocs = elencoDocVen.iterator();
				while(listaDocs.hasNext()) {
					try {
						String c = (String) listaDocs.next();
						if(c != null && !c.equals("")) {
							YDocumentoVendita docVen = (YDocumentoVendita) YDocumentoVendita.elementWithKey(YDocumentoVendita.class, c, PersistentObject.NO_LOCK);
							if(docVen != null) {
								if(primoControllo(docVen, gestioneDataIniPagamento)) {	
									if(checkCliente(docVen, gestioneDataIniPagamento)) {
										//if(checkArea(docVen, gestioneDataIniPagamento)) { 71058 rimosso il cotrnollo sull'area
										if(checkCausale(docVen, gestioneDataIniPagamento)) {	//71085 aggiunto controllo sulla causale
											if(checkModConsegna(docVen, gestioneDataIniPagamento)) {	//71085 aggiunto controllo sulla modalità di consegna
												if(checkModalitaPagamento(docVen,gestioneDataIniPagamento)) { //aggiunto contrllo sulla modalità di pagamento
													Date dataInizioPagamento = getPrimoDelMese(docVen);  //prendo il primo giorno del mese successivo rispetto alla data di creazione del documento
													docVen.setDataInizioPagamento(dataInizioPagamento);
													if(docVen.save() >= 0)
														ConnectionManager.commit();
												}
											}
										}
										//										}
									}
								}else if(secondoControllo(docVen, gestioneDataIniPagamento)){
									Date dataInizioPagamento = getPrimoDelMese(docVen);
									docVen.setDataInizioPagamento(dataInizioPagamento);
									if(docVen.save() >= 0)
										ConnectionManager.commit();
								}
							}
						}
					}catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
			}
		}
	}

	/**
	 * Check su modalita di pagamento
	 * @param docVen
	 * @param gestioneDataIniPagamento
	 * @return
	 */
	protected boolean checkModalitaPagamento(YDocumentoVendita docVen, YPsnDatiGestDataIniPagam gestioneDataIniPagamento) {
		boolean check = false;
		for(int i = 0; i < gestioneDataIniPagamento.getYListaModPagamDataIniP().size(); i++) {
			YLstModPagGesDataIniP causale = (YLstModPagGesDataIniP) gestioneDataIniPagamento.getYListaModPagamDataIniP().get(i);
			if(causale != null) {
				if(causale.getRModPagamento().equals(docVen.getIdModPagamento()))    
					return true;
			}
		}
		return check;
	}

	/**
	 * 71051 TBSOF3 vado a controllare che la data in cui sta venendo lanciata la stampa sia l'ultimo giorno del mese e, nel caso in cui fosse vero, faccio tutto il resto dei controlli
	 * sui culiente esclusi, sulle aree CEE abilitate e, sullo stato prelievo del documento di vendita che deve essere diverso da "chiuso senza prelievo"
	 */

	@SuppressWarnings({ })
	protected boolean secondoControllo(DocumentoVendita docVen, YPsnDatiGestDataIniPagam gestioneDataIniPagamento) {
		boolean check = false;
		//long millis=System.currentTimeMillis();  
		//LocalDate givenDate = LocalDate.parse(getDataStampa().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//LocalDate lastDayOfMonth  = givenDate.withDayOfMonth(
		//		givenDate.getMonth().length(givenDate.isLeapYear()));
		LocalDate lastDayOfMonth = getDataStampa().toLocalDate().with(TemporalAdjusters.lastDayOfMonth());
		int giorniAllUltimo = 0;
		Date dataStampa = getDataStampa();
		//String dataIniz = getDataStampa().toString();
		for(int i = dataStampa.toLocalDate().getDayOfMonth() + 1; i < lastDayOfMonth.getDayOfMonth()+1; i++) {
			LocalDate localDate = dataStampa.toLocalDate(); 
			localDate = localDate.withDayOfMonth(i);
			dataStampa = Date.valueOf(localDate);
			boolean isDomenica = isDomenica(dataStampa);
			boolean isSabato = isSabato(dataStampa);
			if(!isDomenica && !isSabato) { // 71058 se la data doc è sabato o domenica aggiungio giorno (quindi è come se rimuovessi dal calcolo i feriali
				giorniAllUltimo++;
			}
		}
		//setDataStampa(Date.valueOf(dataIniz)); rimossa perche' insensata
		if(giorniAllUltimo == 0) {
			if(!isDomenica(getDataStampa()) && !isSabato(getDataStampa())) {
				if(checkCliente(docVen, gestioneDataIniPagamento)) {
					//				if(checkArea(docVen, gestioneDataIniPagamento)) {
					if(checkCausale(docVen, gestioneDataIniPagamento)) {
						if(checkStatoPrelievo(docVen))
							return true;
					}
					//				}
				}
			}
		}
		return check;
	}

	/**
	 * 71058 TBSOF3 controllo che la causale sia inclusa 
	 */
	protected boolean checkCausale(DocumentoVendita docVen, YPsnDatiGestDataIniPagam gestioneDataIniPagamento) {
		boolean check = false;
		for(int i = 0; i < gestioneDataIniPagamento.getYListaCausali().size(); i++) {
			YListaCausaliPsnGesDataIn causale = (YListaCausaliPsnGesDataIn) gestioneDataIniPagamento.getYListaCausali().get(i);
			if(causale != null) {
				if(causale.getIdCausale().equals(docVen.getIdCau()))    
					return true;
			}
		}
		return check;
	}

	/**
	 * 71058 TBSOF3 controllo che la modalità di consegna non sia escluso 
	 */
	protected boolean checkModConsegna(DocumentoVendita docVen, YPsnDatiGestDataIniPagam gestioneDataIniPagamento) {
		boolean check = true;
		for(int i = 0; i < gestioneDataIniPagamento.getYListaModConsegna().size(); i++) {
			YListaModConsegnaGesPsn modConsegna = (YListaModConsegnaGesPsn) gestioneDataIniPagamento.getYListaModConsegna().get(i);
			if(modConsegna != null) {
				if(modConsegna.getIdModConsegna().equals(docVen.getIdModConsegna()))    
					return false;
			}
		}
		return check;
	}

	/**
	 * 71051 TBSOF3 controllo che lo stato del prelievo del documento di vendita sia diverso da "chiuso senza prelievo"
	 */
	protected boolean checkStatoPrelievo(DocumentoVendita docVen) {
		boolean check = false;
		CachedStatement cs = null;
		ResultSet rs = null;
		try {
			String select = "SELECT * FROM SOFTRE.DOC_VEN_TES_PRL_LOGIS_V01 AS D " + 
					"INNER JOIN THIP.DOC_VEN_TES AS T " + 
					"ON " + 
					"T.ID_AZIENDA = D.ID_AZIENDA " + 
					"AND T.ID_ANNO_DOC = D.ID_ANNO_DOC AND " + 
					"T.ID_NUMERO_DOC = D.ID_NUMERO_DOC " + 
					"WHERE T.ID_ANNO_DOC = '" + docVen.getAnnoDocumento() + "' AND T.ID_NUMERO_DOC = '" + docVen.getNumeroDocumento() + "' AND D.STATO_PRL!='3' AND T.ID_AZIENDA = '" + Azienda.getAziendaCorrente() + "'";
			cs = new CachedStatement(select);
			rs = cs.executeQuery();
			if(rs.next())
				check = true;
		}catch(SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(cs != null)
					cs.free();
				if(rs != null)
					rs = null;
			}catch(SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return check;
	}

	/**
	 * 71051 TBSOF3 ritorno il primo giorno del mese successivo
	 */
	protected Date getPrimoDelMese(DocumentoVendita docVen) {
		Date date = docVen.getDatiComuniEstesi().getDataCrz();
		return Date.valueOf(date.toLocalDate().plusMonths(1).withDayOfMonth(1));

	}

	/**
	 * <h1>Gestione data inizio pagamento - Primo Controllo</h1>
	 * <br>
	 * <b>71051 TBSOF3 </b>	<p>Nel primo controllo vado a verificare che la data di consegna sia il mese dopo rispetto alla data di creazione del documento e, che il documento sia stato creato
	 * n giorni prima della fine del mese di creazione (questo n lo troviamo in YPsnDatiGestDataIniPagam come giorni per salto mese).</p>
	 * <b>71193	DSSOF3	07/08/2023</b>	<p>Rimosso controllo su data consegna confermata righe, fatto ora su data di testata</p>
	 */
	protected boolean primoControllo(DocumentoVendita docVen, YPsnDatiGestDataIniPagam gestioneDataIniPagamento) {
		boolean check = false;
		//List righeDoc = docVen.getRighe();
		Date dataCreazioneDocVen = docVen.getDatiComuniEstesi().getDataCrz();
		int monthCreazione = dataCreazioneDocVen.toLocalDate().getMonthValue() + 1;
		//for(int i = 0; i < righeDoc.size(); i++) {
		//DocumentoVenRigaPrm docVenRig = (DocumentoVenRigaPrm) righeDoc.get(i);
		//if(docVenRig != null) {
		Date dataConsegna = docVen.getDataConsegnaConfermata();//docVenRig.getDataConsegnaConfermata();
		if(dataConsegna != null) {
			int monthConsegna = dataConsegna.toLocalDate().getMonthValue() + 1;
			if(monthCreazione == monthConsegna -1) {
				//String date = dataCreazioneDocVen.toString();
				//LocalDate givenDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				//LocalDate lastDayOfMonth  = givenDate.withDayOfMonth(
				//		givenDate.getMonth().length(givenDate.isLeapYear()));
				int ultimoGiornoMeseCrz = dataCreazioneDocVen.toLocalDate().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();//lastDayOfMonth.getDayOfMonth();
				int giorniTotali = gestioneDataIniPagamento.getGiorniPerSaltoMese().intValue();
				for(int j = dataCreazioneDocVen.toLocalDate().getDayOfMonth(); j < ultimoGiornoMeseCrz+1; j++) {
					LocalDate localDate = dataCreazioneDocVen.toLocalDate(); 
					localDate = localDate.withDayOfMonth(j);
					dataCreazioneDocVen = Date.valueOf(localDate);
					boolean isDomenica = isDomenica(dataCreazioneDocVen);
					boolean isSabato = isSabato(dataCreazioneDocVen);
					if(isDomenica || isSabato) { // se la data doc è sabato o domenica aggiungio giorno (quindi è come se rimuovessi dal calcolo i feriali
						giorniTotali++;
					}
				}
				if((ultimoGiornoMeseCrz - giorniTotali) <= dataCreazioneDocVen.toLocalDate().getDayOfMonth()) {
					return true;
				}
			}
		}
		//}
		//}
		return check;
	}


	public boolean isDomenica(Date data) {
		if(data != null && data.toLocalDate().getDayOfWeek() == DayOfWeek.SUNDAY) //getDay 0 = DOMENICA
			return true;
		return false;
	}

	public boolean isSabato(Date data) {	//getDay 6 = SABATO
		if(data != null && data.toLocalDate().getDayOfWeek() == DayOfWeek.SATURDAY)
			return true;
		return false;
	}

	/**
	 * 71051 TBSOF3 controllo che l'area CEE sia una di quelle abilitate al calcolo
	 */
	protected boolean checkArea(DocumentoVendita docVen, YPsnDatiGestDataIniPagam gestioneDataIniPagamento) {
		boolean check = false;
		for(int i = 0; i < gestioneDataIniPagamento.getYListaAreeCEEPsnDataIniP().size(); i++) {
			YLstAreeCeePsnGesDataIni area = (YLstAreeCeePsnGesDataIni) gestioneDataIniPagamento.getYListaAreeCEEPsnDataIniP().get(i);
			if(area != null) {
				if(area.getIdAreaCee() == docVen.getCausale().getAreaCEE())    
					return true;
			}
		}
		return check;
	}

	/**
	 * 71051 TBSOF3 controllo che il cliente del documento di vendita non sia uno di quelli esclusi dal calcolo
	 */
	protected boolean checkCliente(DocumentoVendita docVen, YPsnDatiGestDataIniPagam gestioneDataIniPagamento) {
		boolean check = true;
		for(int i = 0; i < gestioneDataIniPagamento.getYListaClientiPsnDataIniP().size(); i++) {
			YLstCliPsnDatainiP cliente = (YLstCliPsnDatainiP) gestioneDataIniPagamento.getYListaClientiPsnDataIniP().get(i);
			if(cliente != null) {
				if(cliente.getRCliente().equals(docVen.getIdCliente()))    
					check = false;
			}
		}
		return check;
	}

	/**
	 * 71051 TBSOF3 prendo i documenti di vendita di cui devo stampare la bolla
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Vector getDocumentiVendita() {
		Vector elencoDocVen = new Vector();
		CachedStatement cs = null;
		ResultSet rs = null;
		String where = this.getCondizioniWhere().getCondizioneWhere();
		try {
			String select = "SELECT * FROM THIP.DOC_VEN_TES WHERE " + where + "AND ID_AZIENDA = '" + Azienda.getAziendaCorrente() + "'";
			cs = new CachedStatement(select);
			rs = cs.executeQuery();
			while(rs.next()) {
				String anno = rs.getString("ID_ANNO_DOC") != null ? rs.getString("ID_ANNO_DOC").trim() : "";
				String numero = rs.getString("ID_NUMERO_DOC") != null ? rs.getString("ID_NUMERO_DOC").trim() : "";
				String key = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), anno, numero});
				elencoDocVen.add(key);
			}
		}catch(SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}catch(SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return elencoDocVen;
	}

	/**
	 * @author Andrea Gatta
	 * <br><br>
	 * <b>70967	AGSOF3	23/02/2023</b>	<br>Sottometto la stampa del packing list, in futuro sarï¿½ la stampa dell'etichetta uds ma
	 * 									ancora non esiste.<br>
	 * <b>70973	DSSOF3	28/02/2023</b>	<br>Implementata la stampa dell'UDS, da terminalino<br>
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	protected void sottomettiStampaUds() throws Exception {
		//		StampaDocumentoVenBatch stp = (StampaDocumentoVenBatch) Factory.createObject(StampaDocumentoVenBatch.class);
		//		BatchOptions batchOptions = (BatchOptions) Factory.createObject(BatchOptions.class);
		//		boolean ok = batchOptions.initDefaultValues(StampaDocumentoVenBatch.class, "RptStpDocVen", "RUN");
		//		stp.setBatchJob(batchOptions.getBatchJob());
		//		stp.setScheduledJob(batchOptions.getScheduledJob());
		//		String report = "";
		//		if(Azienda.getAziendaCorrente().equals("001"))
		//			report  = ParametroPsn.getValoreParametroPsn("YStampaDoc001", "Stampa Documento vendita");
		//		else
		//			report = ParametroPsn.getValoreParametroPsn("YStampaDoc002", "Stampa Documento vendita");
		//		stp.setReportId(report);
		//		stp.setExecutePrint(true);
		//		String keydocVen = this.getBatchJob().getLog();
		//		String[] c = null;
		//		if(keydocVen != null)
		//			c = keydocVen.split(KeyHelper.KEY_SEPARATOR);
		//		if(c != null && c.length > 2) {
		//			stp.getBatchJob().setDescription("St. Packing List da ddt term.ino");
		//			stp.getBatchJob().setUserDescription("St. Packing List da ddt term.ino");
		//			stp.getBatchJob().setBatchQueueId("DefQueue");
		//			stp.setDocDgtEnabled(false);
		//			stp.setSSDEnabled(false);
		//			String where = " (THIP.DOC_VEN_TES.ID_NUMERO_DOC "
		//					+ "BETWEEN '" + c[2] + "' "
		//					+ "AND '" + c[2] + "' "
		//					+ "AND THIP.DOC_VEN_TES.ID_ANNO_DOC "
		//					+ "BETWEEN '" + c[1] + "' "
		//					+ "AND '" + c[1] + "')";
		//			stp.getFiltri().setWhereString(where);
		//			stp.getFiltri().setWhereStringForCR(where);
		//			Thread.sleep(1000);
		//			if(stp.save() >= 0) {
		//				ConnectionManager.commit();
		//				BatchService.submitJob(stp.getBatchJob());
		//			}		
		//		}
		YStampaUdsBatch stampaUds = (YStampaUdsBatch) Factory.createObject(YStampaUdsBatch.class);
		BatchOptions batchOptions = (BatchOptions) Factory.createObject(BatchOptions.class);
		boolean ok = batchOptions.initDefaultValues(YStampaUdsBatch.class, "YStampaUDS", "RUN");
		stampaUds.setBatchJob(batchOptions.getBatchJob());
		stampaUds.setScheduledJob(batchOptions.getScheduledJob());
		String report = "";
		if(Azienda.getAziendaCorrente().equals("001"))
			report  = ParametroPsn.getValoreParametroPsn("YStampaUds001", "IdReport");
		else
			report = ParametroPsn.getValoreParametroPsn("YStampaUds002", "IdReport");
		stampaUds.setReportId(report);
		stampaUds.setExecutePrint(true);
		stampaUds.getBatchJob().setDescription("St. UDS da ddt term.ino");
		stampaUds.getBatchJob().setUserDescription("St. UDS ddt term.ino");
		stampaUds.getBatchJob().setBatchQueueId("DefQueue");
		stampaUds.setCodiceUds(getCodiceUds());
		Thread.sleep(1000);
		if(stampaUds.save() >= 0) {
			ConnectionManager.commit();
			BatchService.submitJob(stampaUds.getBatchJob());
		}	
		//72102
		TestataUds tu = (TestataUds) TestataUds.elementWithKey(TestataUds.class, getCodiceUds(), PersistentObject.NO_LOCK);
		if(tu != null) {
			YTestataLista tl = (YTestataLista) tu.getTestataLista();
			if(IntegrazioneCorriereSusa.isDocumentoVenditaVettoreSusa(((YTestataLista)tl).getDocumentoVendita())) {
				YProcessaListeCompattoRF.stampaEtichettaSusaUds(null,tl.getCodice(),true);
			}
		}
		//72102
	}

	/**
	 * @author Daniele Signoroni 30/01/2023
	 * <h1>Gestione righe spese in stampa DDT:</h1>
	 * <b>70899	DSSOF3	30/01/2023</b>	Gestione spese dei documenti vendita.
	 * 									Qui vengono gestite le spese certificato e le spese trasporto.<br></br>
	 * 									Nel metodo <code>getListaOggettiSpeseCertificati()</code> vengono gestite SOLO le spese certificato.
	 * 									Nel metodo <code>gestioneSpeseTrasporto()</code> vengono gestite SOLO le spese di trasporto.<br></br>
	 * @param batchTerminatoConSucc 
	 */
	private void gestioneSpese(boolean batchTerminatoConSucc) {
		String idCausaleSpesaCertificati = ParametroPsn.getValoreParametroPsn("YCausaleSpesaCertificatiAutomatiche", "IdSpesa");
		String idCausaleSpesaTrasporto = ParametroPsn.getValoreParametroPsn("YCauRigSpeseTrasportoAutomatica", "CausaleSpesaTrasporto");
		HashMap<String,YOggettoRptBollaSpeseCert> map = new HashMap<String, YOggettoRptBollaSpeseCert>();
		if(idCausaleSpesaCertificati != null && idCausaleSpesaTrasporto != null) {
			map = getListaOggettiSpeseCertificati();
			if(map.size() > 0) {
				gestioneSpeseCertificati(map, idCausaleSpesaCertificati, idCausaleSpesaTrasporto,batchTerminatoConSucc);
			}
			map = getListaOggettiSpeseTrasporto();
			if(map.size() > 0) {
				gestioneSpeseTrasporto(map,idCausaleSpesaTrasporto,batchTerminatoConSucc);
			}
		}else {
			String msg = "### Uno dei due parametri di personalizzazione, o entrambi, e' vuoto: "
					+ "('YCausaleSpesaCertificatiAutomatiche') OR ('YCauRigSpeseTrasportoAutomatica')"
					+ " la gestione delle spese viene saltata ###";
			Trace.excStream.println(msg);
		}
	}

	/**
	 * @author Daniele Signoroni 30/01/2023
	 * <h1>Gestione spese trasporto (Documento NON Certificato): </h1>
	 * <b>70899	DSSOF3	30/01/2023</b>	Gestione spese trasporto per tutti i documenti.
	 * 									Da questi documenti vado a cacellare tutte le spese trasporto che hanno come causale,
	 * 									il valore contenuto nei due parametri di personalizzazione.
	 *
	 * 									Se ho spese righe trasporto creo una riga nel doc.
	 * @param batchTerminatoConSucc 
	 */
	private void gestioneSpeseTrasporto(HashMap<String, YOggettoRptBollaSpeseCert> map, String idCausaleSpesaTrasporto, boolean batchTerminatoConSucc) {
		try {
			for (HashMap.Entry<String, YOggettoRptBollaSpeseCert> mappa : map.entrySet()) {
				YOggettoRptBollaSpeseCert oggetto = map.get(mappa.getKey());
				if(oggetto != null) {
					List<YDocumentoVendita> listaDocVen = recuperaListaDocumentiVenditaDaBolla(oggetto);
					BigDecimal totBolla = recuperaTotaleBolla(listaDocVen); //71054 somma della colonna VLR_TOT_DOC
					boolean isOk = true;	
					YDocumentoVendita docVen = listaDocVen.get(0);
					YClienteVendita cliente = (YClienteVendita) docVen.getCliente();
					boolean isSogliaMinimaBloccante = false;
					BigDecimal sogliaMinima = cliente.getSogliaMinimaSpeseTrasp() ;// != null ? cliente.getSogliaMinimaSpeseTrasp() : BigDecimal.ZERO; //71054
					if(sogliaMinima == null || sogliaMinima.compareTo(BigDecimal.ZERO) == 0) { //se e' null o zero genero comunque le righe spese
						isSogliaMinimaBloccante = false;
					}else if(totBolla.compareTo(sogliaMinima) > 0) { //altrimenti se la soglia e' piccola del tot bolla non genero
						isSogliaMinimaBloccante = true;
					}
					//if(totBolla.compareTo(sogliaMinima) < 0) { //71054 se il tot doc e' minore della soglia minima innesco la gestione spese tr
					if(!isSogliaMinimaBloccante) {
						if(docVen.getStatoStampaFattura() != '2') {
							if(isOk && docVen.getModalitaConsegna().getTipoConsegna() == ModalitaConsegna.TP_CNG_F_ARRIVO 
									&& docVen.isCalcoloRigheSpeseTrasportoAbilitato()
									&& (docVen.getIdModSpedizione() != null && !docVen.getIdModSpedizione().equals("DE"))//agsof3 spedizione deve essere diversa da DE
									&& !docVen.isSalvaDaProposte()
									&& ( docVen.getCausale() != null && (
											docVen.getCausale().getAzioneMagazzino() == AzioneMagazzino.USCITA
											&& docVen.getCausale().getTipoDocumento() == TipoDocumento.FATTURA)
											)
									) {
								BigDecimal totale = YGestioneSpeseRigheVendita.getTotaleSpesaTrasporto(docVen.getTariffaQuintale(), oggetto.getPesoLordo());
								if(totale.compareTo(BigDecimal.ZERO) >= 1) {
									YDocumentoVenRigaPrm rigaGiaEsistente = YGestioneSpeseRigheVendita.cercaRigaEsistente(docVen.getKey(), idCausaleSpesaTrasporto, "9999");
									if(rigaGiaEsistente != null) {
										if(rigaGiaEsistente.delete() >= 0)
											YGestioneSpeseRigheVendita.cancellaDaRptDdtBolRig(this.getBatchJob().getBatchJobId(), rigaGiaEsistente.getKey());

									}
									YDocumentoVenRigaPrm docVenRig = YGestioneSpeseRigheVendita.getRigaSpesaTrasporto(docVen, totale, idCausaleSpesaTrasporto, oggetto.getPesoLordo());
									if(docVenRig.save() < 0) {
										isOk = false;
									}
								}
							}
							if(docVen.save() < 0) {
								isOk = false;
							}
							if(batchTerminatoConSucc && isOk)
								ConnectionManager.commit();
						}
					}else {
						output.println("-------------------------------------------------------");
						output.println("Il valore totale della bolla "+docVen.getNumeroBolla()+" e' maggiore o uguale della soglia minima, le spese TRASPORTO non verranno aggiunte");
						output.println("Soglia minima : "+sogliaMinima.toString()+" e valore tot DDT : "+totBolla.toString());
						output.println("-------------------------------------------------------");
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}

	}

	/**
	 * @author Daniele Signoroni	24/04/2023
	 * <br><br>
	 * <b>71054	DSSOF3	24/04/2023</b>	<br>Una volta accorpati i documenti per NUMERO_BOLLA calcolo il totale della bolla,
	 * 										in questo caso sommo la colonna {@value DocumentoVenditaTM#VLR_TOT_DOC} di {@value DocumentoVenditaTM#TABLE_NAME}
	 * @param listaDocVen
	 * @return
	 */
	protected BigDecimal recuperaTotaleBolla(List<YDocumentoVendita> listaDocVen) {
		BigDecimal tot = BigDecimal.ZERO;
		Iterator<YDocumentoVendita> iterDocs = listaDocVen.iterator();
		while(iterDocs.hasNext()) {
			YDocumentoVendita docVen = iterDocs.next();
			tot = tot.add(docVen.getTotaleDocumento());
		}
		return tot;
	}

	/**
	 * @author Daniele Signoroni 30/01/2023
	 * <h1>Gestione spese (Documento Certificato): </h1>
	 * <b>70899	DSSOF3	30/01/2023</b>	Gestione spese certificato, trasporto per tutti i documenti che hanno colonna 'Y',
	 * 									nella vista Y_NOTE_CERT_DDT_V01, ovvero hanno un commento con id = CERTIFICATI in testata.
	 * 									Una volta recuperate le bolle satmpate in questo lavoro le scorro e per ogni bolla recupero tutti
	 * 									i documenti collegati.
	 * 									Da questi documenti vado a cacellare tutte le spese certificato e trasporto che hanno come causale,
	 * 									i valori contenuti nei due parametri di personalizzazione.
	 * 									
	 * 									Per le spese certificati mi salvo il valore della riga, ovvero l'importo percentuale spesa, esso sara'	
	 * 									oggetto della nuova riga certificato nell'unico/ultimo documento di vendita.
	 * 									
	 * 									Una volta cancellate le righe se e' tutto ok, se ho spese certificato, creo una riga nel doc.
	 * 									Se ho spese righe trasporto creo una riga nel doc.
	 * @param batchTerminatoConSucc 
	 */
	private void gestioneSpeseCertificati(HashMap<String, YOggettoRptBollaSpeseCert> map,
			String idCausaleSpesaCertificati, String idCausaleSpesaTrasporto, boolean batchTerminatoConSucc) {
		try {
			for (HashMap.Entry<String, YOggettoRptBollaSpeseCert> mappa : map.entrySet()) {
				YOggettoRptBollaSpeseCert oggetto = map.get(mappa.getKey());
				if(oggetto != null) {
					List<YDocumentoVendita> listaDocVen = recuperaListaDocumentiVenditaDaBolla(oggetto);
					boolean isOk = true;
					YDocumentoVendita docVen = listaDocVen.get(0);
					if(docVen.getStatoStampaFattura() != StatoAttivita.ESEGUITO
							&& isAbilitatoPerGenerazioneRigaSpesaCertificato(docVen)) {
						YClienteVendita clienteVen = (YClienteVendita) docVen.getCliente();
						if(isOk && !clienteVen.isEscludiSpeseCert()) { //se il cliente ha il flag "Escludi spese cert" a true non creo la riga
							CausaleRigaDocVen cauSpeseCer = (CausaleRigaDocVen)
									CausaleRigaVendita.elementWithKey(CausaleRigaDocVen.class, 
											KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), idCausaleSpesaCertificati}), 0);
							YDocumentoVenRigaPrm rigaGiaEsistente = YGestioneSpeseRigheVendita.cercaRigaEsistente(docVen.getKey(), idCausaleSpesaCertificati, "9997");
							if(rigaGiaEsistente != null) {
								if(rigaGiaEsistente.delete() >= 0)
									YGestioneSpeseRigheVendita.cancellaDaRptDdtBolRig(this.getBatchJob().getBatchJobId(), rigaGiaEsistente.getKey());
							}
							YDocumentoVenRigaPrm docVenRig = YGestioneSpeseRigheVendita.getRigaSpesaCertificato(docVen, cauSpeseCer.getSpesa().getImporto(), idCausaleSpesaCertificati);
							if(docVenRig.save() < 0) {
								isOk = false;
							}
						}
						if(docVen.save() < 0) {
							isOk = false;
						}
						if(batchTerminatoConSucc && isOk)
							ConnectionManager.commit();
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}

	}

	/**
	 * <h1>Is documento abilitato per riga spesa certificato?</h1>
	 * <br>
	 * Daniele Signoroni 25/10/2023
	 * <br>
	 * <p>
	 * </p>
	 * @param docVen
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isAbilitatoPerGenerazioneRigaSpesaCertificato(YDocumentoVendita docVen) {
		Iterator<YDocumentoVenRigaPrm> iterRighe = docVen.getRighe().iterator();
		boolean isAllNonRich = true;
		while (iterRighe.hasNext()) {
			YDocumentoVenRigaPrm riga = (YDocumentoVenRigaPrm) iterRighe.next();
			if(riga.getArticolo().getMicroFamiglia() == null) { 
				isAllNonRich = false;
			}else if(!riga.getArticolo().getMicroFamiglia().getIdMicroFamiglia().equals("NONRICH")){
				return true;
			}
		}
		if(isAllNonRich)
			return false;
		return true;
	}

	/**
	 * @author Daniele Signoroni 30/01/2023	
	 * <h1>Recupero lista documenti vendita:</h1>
	 * <b>70899	DSSOF3	30/01/2023</b>	Tramite NUMERO_BOLLA,DATA_BOLLA,ANNO_BOLLA recupero i documenti vendita.
	 * 									Ordinati per ID_NUMERO_DOC decrescente.
	 * @param oggetto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<YDocumentoVendita> recuperaListaDocumentiVenditaDaBolla(YOggettoRptBollaSpeseCert oggetto) {
		SimpleDateFormat formatTimeStampSQL = new SimpleDateFormat("yyyyMMdd");
		String where = "ID_AZIENDA = '"+oggetto.getIdAzienda()+"' "
				+ "AND DATA_BOLLA = '"+formatTimeStampSQL.format(oggetto.getDataBolla())+"' "
				+ "AND NUMERO_BOLLA = '"+oggetto.getNumeroBolla()+"' "
				+ "AND ANNO_BOLLA = '"+oggetto.getAnnoBolla()+"' ";
		List<YDocumentoVendita> lst = new ArrayList<YDocumentoVendita>();
		try {
			lst = YDocumentoVendita.retrieveList(YDocumentoVendita.class, where, "ID_NUMERO_DOC DESC", false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(Trace.excStream);
		} catch (InstantiationException e) {
			e.printStackTrace(Trace.excStream);
		} catch (IllegalAccessException e) {
			e.printStackTrace(Trace.excStream);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return lst;
	}

	/**
	 * @author Daniele Signoroni 29/01/2023
	 * <br></br>
	 * <ul>
	 * 		<li>
	 * 			Recupero tutte le bolle stampate dall'inizio della sottomissione del lavoro ad ora.
	 * 			Le bolle devono avere nella vista Y_NOTE_CERT_DDT_V01 la colonna PRES_CERT = 'Y', ovvero
	 * 			devono essere quei documenti che richiedono la presenza di certificati.
	 * 			Questi vengono presi e aggiunti ad una mappa, che per comoditï¿½ contiene la chiave, per evitare duplicati	
	 * 			e una serie di dati utili in seguito per la creazione della riga di spesa certificato.<br></br>
	 * 			<b>NB</b>: Nella where sottraggo un tot di secondi al timestamp di sottomissione dato che con timestamp = a quello di lancio,
	 * 					   non vedevo il record in THIP.RTP_DDTBOL_TES.
	 * 		</li>
	 * </ul>
	 * 
	 * @return
	 */
	public HashMap<String,YOggettoRptBollaSpeseCert> getListaOggettiSpeseCertificati(){
		String stmt = "";
		CachedStatement cs = null;
		ResultSet rs = null;
		HashMap<String, YOggettoRptBollaSpeseCert> map = new HashMap<String, YOggettoRptBollaSpeseCert>();
		try {
			stmt = "SELECT * FROM SOFTRE.Y_NOTE_CERT_DDT_V01 V "
					+ "INNER JOIN THIP.RPT_DDTBOL_TES B "
					+ "INNER JOIN THERA.BATCH_JOB T "
					+ "ON B.BATCH_JOB_ID = T.BATCH_JOB_ID "
					+ "ON B.R_AZIENDA = V.ID_AZIENDA "
					+ "AND B.NUMERO_BOLLA = V.NUMERO_BOLLA AND YEAR(B.DATA_BOLLA) = V.ANNO_BOLLA " //aggiunta clausola sull'anno NECESSARIA 
					//qui sotto sottraggo un po' di secondi altrimenti il batch non era valido
					+ "WHERE B.BATCH_JOB_ID = '"+this.getBatchJob().getBatchJobId()+"' AND V.PRES_CERT = 'Y' ";
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery(); 
			while(rs.next()) {
				YOggettoRptBollaSpeseCert oggetto = new YOggettoRptBollaSpeseCert();
				String idAzienda = rs.getString("ID_AZIENDA");
				String annoBolla = rs.getString("ANNO_BOLLA").trim(); //trim aggiunta
				String numeroBolla = rs.getString("NUMERO_BOLLA");
				Date dataBolla = rs.getDate("DATA_BOLLA");
				String idCliente = rs.getString("COD_CLIFOR");
				BigDecimal pesoLordo = rs.getBigDecimal("PESO_LORDO");
				boolean speseCertifcatiAmmesse = rs.getString("PRES_CERT").equals("Y") ? true : false;
				String key = KeyHelper.buildObjectKey(new String[] {idAzienda,annoBolla,numeroBolla});
				oggetto.setKey(key);
				oggetto.setIdAzienda(idAzienda);
				oggetto.setAnnoBolla(annoBolla);
				oggetto.setNumeroBolla(numeroBolla);
				oggetto.setDataBolla(dataBolla);
				oggetto.setIdCliente(idCliente);
				oggetto.setPesoLordo(pesoLordo);
				oggetto.setSpeseCertAmmesse(speseCertifcatiAmmesse);
				map.put(key, oggetto);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return map;
	}

	/**
	 * @author Daniele Signoroni 29/01/2023
	 * <br></br>
	 * <ul>
	 * 		<li>
	 * 			Recupero tutte le bolle stampate dall'inizio della sottomissione del lavoro ad ora.
	 * 			Questi vengono presi e aggiunti ad una mappa, che per comoditï¿½ contiene la chiave, per evitare duplicati	
	 * 			e una serie di dati utili in seguito per la creazione della riga di spesa trasporto.<br></br>
	 * 			Differisce dal metodo <code>getListaOggettiSpeseCertificati()</code> in quanto il seguente viene chiamato
	 * 			solo nel caso il primo ritorni 0 record.<br></br>
	 * 			<b>NB</b>: Nella where sottraggo un tot di secondi al timestamp di sottomissione dato che con timestamp = a quello di lancio,
	 * 					   non vedevo il record in THIP.RTP_DDTBOL_TES.
	 * 		       			
	 * 		</li>
	 * </ul>
	 * 
	 * @return
	 */
	public HashMap<String,YOggettoRptBollaSpeseCert> getListaOggettiSpeseTrasporto(){
		String stmt = "";
		CachedStatement cs = null;
		ResultSet rs = null;
		HashMap<String, YOggettoRptBollaSpeseCert> map = new HashMap<String, YOggettoRptBollaSpeseCert>();
		try {
			stmt = "SELECT * FROM THIP.RPT_DDTBOL_TES B "
					+ "INNER JOIN THERA.BATCH_JOB T "
					+ "ON B.BATCH_JOB_ID = T.BATCH_JOB_ID "
					+ "WHERE B.BATCH_JOB_ID = '"+this.getBatchJob().getBatchJobId()+"' ";
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery(); 
			while(rs.next()) {
				YOggettoRptBollaSpeseCert oggetto = new YOggettoRptBollaSpeseCert();
				String idAzienda = rs.getString("R_AZIENDA");
				String annoBolla = rs.getString("ID_ANNO_DOC");
				String numeroBolla = rs.getString("NUMERO_BOLLA");
				Date dataBolla = rs.getDate("DATA_BOLLA");
				String idCliente = rs.getString("COD_CLIFOR");
				BigDecimal pesoLordo = rs.getBigDecimal("PESO_LORDO");
				String key = KeyHelper.buildObjectKey(new String[] {idAzienda,annoBolla,numeroBolla});
				oggetto.setKey(key);
				oggetto.setIdAzienda(idAzienda);
				oggetto.setAnnoBolla(annoBolla);
				oggetto.setNumeroBolla(numeroBolla);
				oggetto.setDataBolla(dataBolla);
				oggetto.setIdCliente(idCliente);
				oggetto.setPesoLordo(pesoLordo);
				map.put(key, oggetto);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return map;
	}

	/*
	@SuppressWarnings({ "rawtypes" })
	protected void gestioneUDC(DocumentoVendita docVen) {	//TBSOF3 metodo per gestire la creazione della UDC dalla UDS
		if(docVen != null) {
			if(docVen.getMagazzinoTrasferimento() != null) {
				if(((YMagazzino)docVen.getMagazzinoTrasferimento()).getIngressoAutomaticoUdc()) { //TBSOF3 controllo che sul magazzino di trasferimento ci sia il flag personalizzato attivo
					Vector elencoUds = getElencoUds(docVen);	//TBSOF3 prendo tutte le uds collegate a questo DV che non sono ancora state spedite
					if(elencoUds.size() > 0) {
						for(int i = 0; i < elencoUds.size(); i++) {
							YOggettinoUdsStampaDDT ogg = (YOggettinoUdsStampaDDT) elencoUds.get(i);
							if(ogg != null)
								creaMovimento(ogg, docVen);	//TBSOF3 creo il movimento su logis
						}
					}
				}
			}
		}
	}
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Vector getElencoUds(DocumentoVendita docVen) {
		Vector elencoUds = new Vector();
		ResultSet rs = null;
		CachedStatement cs = null;
		try {
			String codLista = docVen.getAnnoDocumento().trim() + docVen.getIdNumeroDoc(); //TBSOF3 creo il codice della lista(manca un carattere davanti che puï¿½ variare, perï¿½ tutto il resto ï¿½ univoco)
			String select = "SELECT * FROM LOGIS.LUDS_RIGA AS R " +
					"INNER JOIN LOGIS.LUDS_TESTA AS T " +
					"ON R.COD_SOCIETA = T.COD_SOCIETA " +
					"AND T.CODICE=R.COD_UDS " +
					"WHERE R.COD_SOCIETA='" + Azienda.getAziendaCorrente() + "' AND COD_LISTA LIKE '%" + codLista + "' " +
					"AND T.FLAG_SPEDITO = 'N' ";
			cs = new CachedStatement(select);
			rs = cs.executeQuery();
			elencoUds = new Vector();
			while(rs.next()) {
				String articolo = rs.getString("COD_ARTICOLO").trim();
				String lotto = rs.getString("LOTTO01").trim();
				BigDecimal qta = rs.getBigDecimal("QTA1_CONF");
				String codiceUds = rs.getString("COD_UDS").trim();
				YOggettinoUdsStampaDDT oggettino = new YOggettinoUdsStampaDDT();
				oggettino.setCodiceArticolo(articolo);
				oggettino.setLotto(lotto);
				oggettino.setQta(qta);
				oggettino.setCodiceUds(codiceUds);
				elencoUds.add(oggettino);	//TBSOF3 creo il mio oggettino per rimanere comodo quando mi serviranno i dati
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}catch(Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return elencoUds;
	}
	@SuppressWarnings("rawtypes")
	protected void creaMovimento(YOggettinoUdsStampaDDT ogg, DocumentoVendita docVen) {	//TBSOF3 creo il movimento su logis
		InserimentoMovimenti insMov = (InserimentoMovimenti) Factory.createObject(InserimentoMovimenti.class);
		try {
			String codiceOperatoreMovimentoLogis = ParametroPsn.getValoreParametroPsn("YCodiceOperatoreTrasfUds", "CodiceOperatore"); 
			String codiceOperazioneMovimentoLogis = ParametroPsn.getValoreParametroPsn("YOpMovScaricoTrasfUds", "IdOperazioneMovimentoScarico");
			String codicePostazioneMovimentoLogis = ParametroPsn.getValoreParametroPsn("YPostazioneTrasfUds", "CodicePostazione");
			String codiceUbicazione = ParametroPsn.getValoreParametroPsn("YCodiceUbicazioneUdsTrasf", "Codice ubicazione x trasf UDS");
			codiceUbicazione = getCodiceUbicazione(ogg,docVen, codiceUbicazione);
			String codiceCausaleScarico = ParametroPsn.getValoreParametroPsn("YCausaleMovimTrasfUdsScar", "CausaleMovimentoTrasferimentoUdsScarico");
			String codiceCausaleCarico = ParametroPsn.getValoreParametroPsn("YCausaleMovimTrasfUdsCar", "CausaleMovimentoTrasferimentoUdsCarico");
			OperazioneMovimento operazioneMovim = (OperazioneMovimento)
					OperazioneMovimento.elementWithKey(OperazioneMovimento.class, 
							KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), codiceOperazioneMovimentoLogis}), 0);	
			insMov.setOperazMovim(operazioneMovim); //DSSOF3 70877, necessario fare elementWithKey con l'azienda dato che poi sbagliava a settare COD_GRUPPO etc.
			//insMov.setCodiceOperazMovim(codiceOperazioneMovimentoLogis);
			insMov.setCodicePostazione(codicePostazioneMovimentoLogis);
			insMov.setCodiceOperatore(codiceOperatoreMovimentoLogis);
			insMov.settaCampiOperazMovim();
			insMov.setCodiceGruppo(Azienda.getAziendaCorrente()); //DSSOF3	70877 
			//			insMov.setUbicazioneDestinataria(getUbicazione(docVen.getIdMagazzinoTra(), codiceUbicazione));
			//			insMov.setUbicazioneScelta(getUbicazione(docVen.getIdMagazzinoTra(), codiceUbicazione));
			insMov.setCodiceUbicazione(codiceUbicazione);//agsof3, dice arturo che l'ubicazione ï¿½ sempre quella nel parametro quindi la metto fissa secondo sua indicazione
			insMov.setCodiceUbicazioneScelta(codiceUbicazione); //agsof3, dice arturo che l'ubicazione ï¿½ sempre quella nel parametro quindi la metto fissa secondo sua indicazione
			insMov.setCodiceUbicazDest(codiceUbicazione);//agsof3, dice arturo che l'ubicazione ï¿½ sempre quella nel parametro quindi la metto fissa secondo sua indicazione
			Saldo saldo = getSaldo(docVen, ogg);
			if(saldo != null) {//AGSOF3 se non ho trovato il saldo non faccio niente
				insMov.setQuantita(ogg.getQta());
				insMov.setQuantitaPrmErp(ogg.getQta());
				insMov.setSaldoPrimario(saldo);
				insMov.setArticolo(YCambioArticoloSave.getArticoloLogis(ogg.getCodiceArticolo()));
				insMov.setLotto1(ogg.getLotto()); 
				insMov.setCodiceCausale(codiceCausaleScarico);
				insMov.setCodiceCausaleInversa(codiceCausaleCarico);
				MagLogico mag = (MagLogico) MagLogico.elementWithKey(MagLogico.class, KeyHelper.buildObjectKey(new String[] { Azienda.getAziendaCorrente(), docVen.getIdMagazzinoTra()}), 0);
				insMov.setMagLogicoCarico(mag);
				insMov.setMagLogicoScarico(mag);
				insMov.setChiaveMagFisico(Azienda.getAziendaCorrente());////dice arturo che il mag fisico in valvo corrisponde all'azienda
				AnagraficaUdc udc = getUdc(ogg);
				if(udc != null) {
					String mappaUdc = udc.creaMappaUdc();
					if(mappaUdc != null) {
						MappaUdc mapUdc = creaMappaUdc(mappaUdc, docVen, codiceUbicazione);
						if(mapUdc != null)
							insMov.setMappaUdc(mapUdc);
					}
				}
				Vector errori = insMov.fine();
				if(errori.size() == 0) {
					rimuoviSpeditoUds(ogg);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	/**
	 * <h1>Recupero codice ubicazione:</h1>
	 * <br><br>
	 * <b>XXXXX	DSSOF3	10/05/2023</b>	<p>Recupero il codice ubicazione da usare per la creazione del movimento e della mappa UDC,
	 * 										dalla tabella LOGIS.LUDS_TESTA, usando come chiave il CODICE_UDS.<br>
	 * 									   In questo caso se COD_UBICAZIONE trovata e' =! da FCTRA prendo il valore della colonna,
	 * 										altrimenti tengo il valore presente nel parametro di pers. YCodiceUbicazioneUdsTrasf.<br>
	 * 									</p>
	 * @param ogg
	 * @param docVen
	 * @param codiceUbicazione
	 * @return
	 */
	protected String getCodiceUbicazione(YOggettinoUdsStampaDDT ogg, DocumentoVendita docVen,String codiceUbicazione) {
		String stmt = "SELECT COD_UBICAZIONE FROM "+TestataUdsTM.NOME_TABELLA+" "
				+ "WHERE "+TestataUdsTM.CODICE+"='"+ogg.getCodiceUds()+"' "; //la chiave e' solo il codiceUds
		CachedStatement cs = null;
		ResultSet rs = null;
		try {
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			if(rs.next()) {
				String codUbicazione = rs.getString(1).trim();
				if(!codUbicazione.toUpperCase().equals("FCTRA")) {
					codiceUbicazione = codUbicazione;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return codiceUbicazione;
	}

	protected MappaUdc creaMappaUdc(String mappaUdc, DocumentoVendita docVen, String codiceUbicazione) {
		MappaUdc mapUdc = null;
		try {
			mapUdc = (MappaUdc) Factory.createObject(MappaUdc.class);
			mapUdc.setCodice(mappaUdc);
			mapUdc.setCodiceAnagraficaUdc(mappaUdc);
			mapUdc.setCodiceMagFisico(Azienda.getAziendaCorrente());
			mapUdc.setCodiceUbicazione(codiceUbicazione);
			if(mapUdc.save() >= 0)
				ConnectionManager.commit();
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return mapUdc;
	}

	protected void rimuoviSpeditoUds(YOggettinoUdsStampaDDT ogg) {	//TBSOF3 per evitare di creare piï¿½ volte lo stesso movimento, vado a rendere come spedita la uds
		CachedStatement cs = null;
		try {
			String update = "UPDATE LOGIS.LUDS_TESTA SET FLAG_SPEDITO = 'Y' WHERE " 
					+ "COD_SOCIETA='" + Azienda.getAziendaCorrente() + "' AND "
					+ "CODICE = '" + ogg.getCodiceUds() + "'";
			cs = new CachedStatement(update);
			int rs = cs.executeUpdate();
			if(rs > 0)
				ConnectionManager.commit();
			//			else
			//				ConnectionManager.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(cs != null) {
					cs.free();
				}
			}catch(Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
	}

	protected AnagraficaUdc getUdc(YOggettinoUdsStampaDDT ogg) {	//TBSOF3 creo la udc uguale alla uds	
		AnagraficaUdc udc = null;
		try {
			TestataUds uds = TestataUds.elementWithKey(ogg.getCodiceUds(), 0);
			TipoUdc tipoUdc = null;
			if(uds != null) {
				tipoUdc = (TipoUdc) TipoUdc.elementWithKey(TipoUdc.class, uds.getTipoUds().getCodice(), 0);
			}
			if(tipoUdc == null) {
				String tipoUdcDefault = ParametroPsn.getValoreParametroPsn("YTipoUdcDefault", "Tipo Udc default");
				tipoUdc = (TipoUdc) TipoUdc.elementWithKey(TipoUdc.class, tipoUdcDefault, 0);
			}
			udc = (AnagraficaUdc) Factory.createObject(AnagraficaUdc.class);
			udc.setCodice(ogg.getCodiceUds());
			udc.setTipoUdc(tipoUdc);
			udc.setDescrizioneBr(tipoUdc.getDescrizioneBr());
			if(udc.save() >= 0) {
				ConnectionManager.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return udc;
	}

	protected Saldo getSaldo(DocumentoVendita docVen,YOggettinoUdsStampaDDT ogg) {	//TBSOF3 metodo per prendere il saldo in base alla lista
		Saldo saldo = null;
		ResultSet rs = null;
		CachedStatement cs = null;
		String codUbicazione = ParametroPsn.getValoreParametroPsn("YCodiceUbicazioneUdsTrasf", "Codice ubicazione x trasf UDS");
		try {
			//String partita = docVen.getAnnoDocumento().trim() + "  " +docVen.getNumeroDocumento() + "V" + docVen.getTipoDocumento(); DSSOF3 REMMATA 70903
			String partita = docVen.getAnnoDocumento().trim() + "%" + docVen.getNumeroDocumento() + "V" + docVen.getTipoDocumento(); //DSSOF3 LIKE SALDO 70903
			String select = "SELECT * FROM LOGIS.LSALDO AS S "
					+ " WHERE S.LOTTO01 = '" + ogg.getLotto() + "' "
					+ " AND S.COD_ARTICOLO = '" + ogg.getCodiceArticolo() + "' "
					+ " AND S.COD_MAG_LOGICO = '" + docVen.getIdMagazzinoTra() + "' "
					+ " AND S.COD_SOCIETA = '" + Azienda.getAziendaCorrente() + "'"
					+ " AND S.COD_UBICAZIONE = '" + codUbicazione + "'"
					+ " AND S.COD_MAG_FISICO = '"+Azienda.getAziendaCorrente()+"' "
					+ " AND S.COD_GRUPPO = '"+Azienda.getAziendaCorrente()+"' "
					+ " AND S.PARTITA LIKE  '"+partita+"' "
					+ " AND S.COD_MAPPA_UDC IS NULL ";
			cs = new CachedStatement(select);
			rs = cs.executeQuery();
			if(rs.next()) {
				String codSocieta = rs.getString("COD_SOCIETA").trim();
				String codice = rs.getString("CODICE");
				String key = KeyHelper.buildObjectKey(new String[] { codSocieta, codice});
				saldo = (Saldo) Saldo.elementWithKey(Saldo.class, key, 0);
			}else {
				output.println("Non ï¿½ stato trovato il saldo logis: " + select);//agsof3 se non trovo il saldo lo stampo nei log del batch
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}catch(Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return saldo;
	}

	//	public static Ubicazione getUbicazione(String codiceMag, String codiceOperazioneMov) {
	//		String key = codiceMag + KeyHelper.KEY_SEPARATOR + codiceOperazioneMov;
	//		Ubicazione ret = null;
	//		try {
	//			ret = (Ubicazione) Ubicazione.elementWithKey(Ubicazione.class,
	//					key, PersistentObject.NO_LOCK);
	//		}catch (SQLException e) {
	//			e.printStackTrace(Trace.excStream);
	//		}
	//		return ret;
	//	}

	@SuppressWarnings("rawtypes")
	protected int trasferimentoFincantieri(DocumentoVendita docVen, TestataLista tl) throws ThipException {
		int rc = ErrorCodes.OK;
		Iterator iterRighe = tl.getRigheLista().iterator();
		while(iterRighe.hasNext()) {
			YRigaLista rl = (YRigaLista) iterRighe.next();
			Postazione pt = YCostantiValvo.getPostazioneNonGestita(rl.getCodiceMagFisico());
			Operatore op = YCostantiValvo.getOperatoreGenerico(rl.getCodiceMagFisico());
			Vector missioniTerminate = rl.getMissioniTerminate(pt, op ,null);
			for (Iterator iterator = missioniTerminate.iterator(); iterator.hasNext();) {
				Missione m = (Missione) iterator.next();
				Vector errori = spostaMerceFincantieri(m, docVen, tl);
				if(!errori.isEmpty()) {
					throw new ThipException(errori);
				}
			}
		}
		return rc;
	}

	@SuppressWarnings("rawtypes")
	public Ubicazione trovaUbicazioneEntMagazzinoTrasferimento(DocumentoVendita dv) {
		Ubicazione ub = null;
		try {
			CausaleRigaDocVen cauRigDef = dv.getCausale().getCausaleRigaDocumVen();
			if(cauRigDef != null && cauRigDef.getIdCauMagTrasferim() != null && dv.getIdMagazzinoTra() != null) {
				Vector elencoFiltri = new Vector();
				String where = CfgLogTxMovTM.ID_AZIENDA + "='" + dv.getIdAzienda() + "' AND " +
						CfgLogTxMovTM.ID_CAU_MOV_MAG + "='" + cauRigDef.getIdCauMagTrasferim() +"' AND " +
						CfgLogTxMovTM.ID_MAGAZZINO + "='" + dv.getIdMagazzinoTra() +"' AND " +
						CfgLogTxMovTM.ABILITATO + "='Y'";
				elencoFiltri = CfgLogTxMov.retrieveList(where,"",false);
				if(elencoFiltri.size() > 0) {
					CfgLogTxMov filtro = (CfgLogTxMov)elencoFiltri.get(0);
					if(filtro.getOperazioneLogisSenzaUDC() != null) {
						ub = filtro.getOperazioneLogisSenzaUDC().getUbicazioneDefault();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}

		return ub;
	}

	@SuppressWarnings("rawtypes")
	public Vector spostaMerceFincantieri(Missione m , DocumentoVendita dv, TestataLista tl) {
		Vector errori = null;
		InserimentoMovimenti insMov = (InserimentoMovimenti) Factory.createObject(InserimentoMovimenti.class);
		try {
			String codiceOperazioneMovimentoLogis = ParametroPsn.getValoreParametroPsn("YOpMovScaricoTrasfUds", "IdOperazioneMovimentoScarico");
			String codiceCausaleScarico = ParametroPsn.getValoreParametroPsn("YCausaleMovimTrasfUdsScar", "CausaleMovimentoTrasferimentoUdsScarico");
			String codiceCausaleCarico = ParametroPsn.getValoreParametroPsn("YCausaleMovimTrasfUdsCar", "CausaleMovimentoTrasferimentoUdsCarico");
			OperazioneMovimento operazioneMovim = (OperazioneMovimento)
					OperazioneMovimento.elementWithKey(OperazioneMovimento.class, 
							KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), codiceOperazioneMovimentoLogis}), 0);	
			insMov.setOperazMovim(operazioneMovim);
			insMov.setPostazione(YCostantiValvo.getPostazioneNonGestita(m.getCodiceMagFisico()));
			insMov.setOperatore(YCostantiValvo.getOperatoreGenerico(m.getCodiceMagFisico()));
			insMov.settaCampiOperazMovim();
			insMov.setCodiceGruppo(Azienda.getAziendaCorrente());
			Ubicazione ub = trovaUbicazioneEntMagazzinoTrasferimento(dv);
			insMov.setUbicazione(ub);
			insMov.setUbicazioneScelta(m.getUbicazione());
			insMov.setUbicazioneDestinataria(m.getUbicazione());
			Saldo saldo = trovaSaldoCorrenteMerce(m, ub, dv);
			if(saldo != null) {
				insMov.setQuantita(m.getQta1Evasa());
				insMov.setQuantitaPrmErp(m.getQta1Evasa());
				insMov.setSaldoPrimario(saldo);
				insMov.setArticolo(m.getArticolo());
				insMov.setLotto(m.getLotto());
				insMov.setCodiceCausale(codiceCausaleScarico);
				insMov.setCodiceCausaleInversa(codiceCausaleCarico);
				MagLogico mag = (MagLogico) MagLogico.elementWithKey(MagLogico.class, KeyHelper.buildObjectKey(new String[] { Azienda.getAziendaCorrente(), dv.getIdMagazzinoTra()}), 0);
				insMov.setMagLogicoCarico(mag);
				insMov.setMagLogicoScarico(mag);
				insMov.setMagFisico(m.getMagFisico());
				if(m.getMappaUdc() != null)
					insMov.setMappaUdc(m.getMappaUdc());
				errori = insMov.fine();
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return errori;
	}

	@SuppressWarnings("rawtypes")
	public Saldo trovaSaldoCorrenteMerce(Missione m, Ubicazione ub, DocumentoVendita dv) {
		String partita = dv.getAnnoDocumento().trim() + "%" + dv.getNumeroDocumento() + IntegrazioneThipLogis.VENDITA + dv.getTipoDocumento();
		String where = " LOTTO01 = '" + m.getLotto1() + "' "
				+ " AND COD_ARTICOLO = '" + m.getCodiceArticolo() + "' "
				+ " AND COD_MAG_LOGICO = '" + dv.getIdMagazzinoTra() + "' "
				+ " AND COD_SOCIETA = '" + Azienda.getAziendaCorrente() + "'"
				+ " AND COD_UBICAZIONE = '" + ub.getCodice() + "'"
				+ " AND COD_MAG_FISICO = '"+m.getCodiceMagFisico()+"' "
				+ " AND COD_GRUPPO = '"+Azienda.getAziendaCorrente()+"' "
				+ " AND PARTITA LIKE  '"+partita+"' "
				+ " AND COD_MAPPA_UDC IS NULL ";
		try {
			Vector saldi = Saldo.retrieveList(Saldo.class, where, "", false);
			if(saldi.size() > 0) {
				return (Saldo) saldi.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

}
