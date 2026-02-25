package it.valvorobica.thip.vendite.documentoVE;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.ErrorCodes;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.ModalitaConsegna;
import it.thera.thip.base.comuniVenAcq.AzioneMagazzino;
import it.thera.thip.base.comuniVenAcq.TipoRiga;
import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.base.documentoDgt.DocumentoDigitale;
import it.thera.thip.base.generale.CfgLogTxMov;
import it.thera.thip.base.generale.CfgLogTxMovTM;
import it.thera.thip.base.generale.IntegrazioneThipLogis;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.cs.ThipException;
import it.thera.thip.logis.fis.InserimentoMovimenti;
import it.thera.thip.logis.fis.Missione;
import it.thera.thip.logis.fis.Operatore;
import it.thera.thip.logis.fis.OperazioneMovimento;
import it.thera.thip.logis.fis.Postazione;
import it.thera.thip.logis.fis.RigaMovimento;
import it.thera.thip.logis.fis.RigaMovimentoTM;
import it.thera.thip.logis.fis.Saldo;
import it.thera.thip.logis.fis.TestataMovimento;
import it.thera.thip.logis.fis.Ubicazione;
import it.thera.thip.logis.lgb.MagLogico;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.magazzino.movimenti.MovimentoMagazzino;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.generaleVE.CausaleRigaDocVen;
import it.thera.thip.vendite.generaleVE.TipoDocumento;
import it.thera.thip.vendite.proposteEvasione.CreaMessaggioErrore;
import it.valvorobica.thip.base.cliente.YClienteVendita;
import it.valvorobica.thip.logis.bas.YCostantiValvo;
import it.valvorobica.thip.logis.fis.YRigaMovimento;
import it.valvorobica.thip.logis.lgb.YRigaLista;
import it.valvorobica.thip.vendite.generaleVE.YGestioneSpeseRigheVendita;
import it.valvorobica.thip.vendite.ordineVE.YOrdineVendita;

/**
 * <h1>Softre Solutions</h1>
 * <br></br>
 * @author Thomas Brescianini	
 * <br></br>
 * <b>	70695	TBSOF3	06/10/2022</b>	<p>Creazione della riga con le spese di trasporto ad ogni modifica effettuata al documento di vendita</p>
 * <b>			DSSOF3	20/10/2022</b>	<p>FLAG STRINGA_RIS_UTE_1 (AlfanumRiservatoUtente1) GIA' OCCUPATO, NON TOCCARE</p>
 * <b>			TBSOF3	20/10/2022</b>	<p>FLAG STRINGA_RIS_UTE_2 (AlfanumRiservatoUtente2) GIA' OCCUPATO, NON TOCCARE</p>
 * <b>	70754	AGSOF3	16/11/2022</b>	<p>Setto data richieta confermata sulla riga di spesa</p>
 * <b>	70760	AGSOF3	19/11/2022</b>	<p>Merge metodi portale valvo</p>
 * <b>	70821	TBSOF3	29/12/2022</b>	<p>Spostato la tariffa quintale sulla testata del documento, quindi non serve più andare sul cliente. Rimossa la maggiorazione</p>
 * <b>	70823	TBSOF3	30/12/2022</b>	<p>Sistemato il calcolo per il pesoLordo</p>
 * <b>	70829	TBSOF3	04/01/2022</b>	<p>Rimossa l'assegnazione della tariffa alla creazione del documento dalla save e messo nel dataCollector</p>
 * <b>	70877	DSSOF3	23/01/2023</b>	<p>Aggiornati i requisiti per creare la riga spesa trasporto, ora nella causale deve esserci come AzioneMagazzino = USCITA, e TipoDocumento = FATTURA.</p>
 * <b>	70880	DSSOF3	25/01/2023</b>	<p>Rimosse <code>commit()</code> e <code>rollBack()</code> dal metodo <code>creaRigaSpesa()</code> .
 *										Lascio fare al flusso standard della <code>save()</code> altrimenti eseguo rollback indesiderate in altri flussi.</p>
 * <b>	70886	DSSOF3	27/01/2023</b>	<p>Nuova gestione righe spese di trasporto, identificate tamite un parametro di personalizzazione.</p>
 * <b>	70892	DSSOF3	30/01/2023</b>	<p>Riportat la gestione precedente del salvataggio righe spese trasporto, tramite stmt e non tramite <code>save()</code> causa timeout.</p>
 * <b>	71526	DSSOF3	14/05/2024</b>
 * <p>
 *  Sistemare il reperimento della tariffa al quintale ridefinendo {@link #completaBO()}.<br>
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72375	25/02/2026	DSSOF3	 Trasferimento fincantieri.
 */

public class YDocumentoVendita extends DocumentoVendita {

	//72375 <
	public static final String STMT_MOVIMENTO_INGFIN = "SELECT\r\n"
			+ "	*\r\n"
			+ "FROM\r\n"
			+ "	LOGIS.LMOVIM_RIGA R\r\n"
			+ "INNER JOIN LOGIS.LMOVIM_TESTA T\r\n"
			+ "ON\r\n"
			+ "	R.COD_SOCIETA = T.COD_SOCIETA\r\n"
			+ "	AND R.COD_MOVIM = T.CODICE\r\n"
			+ "WHERE\r\n"
			+ "	R.COD_SOCIETA = ?\r\n"
			+ "	AND R.PARTITA = ?\r\n"
			+ "	AND T.COD_OPERAZ_MOVIM = ?\r\n"
			+ "ORDER BY\r\n"
			+ "	R.TS_AGGIORNAMENTO DESC";
	public static CachedStatement cTrovaMovimentoINGFIN = new CachedStatement(STMT_MOVIMENTO_INGFIN);

	public static final String STMT_MOVIMENTO_INGFIN_STORNO = "SELECT\r\n"
			+ "	*\r\n"
			+ "FROM\r\n"
			+ "	LOGIS.LMOVIM_RIGA R\r\n"
			+ "INNER JOIN LOGIS.LMOVIM_TESTA T\r\n"
			+ "ON\r\n"
			+ "	R.COD_SOCIETA = T.COD_SOCIETA\r\n"
			+ "	AND R.COD_MOVIM = T.CODICE\r\n"
			+ "WHERE\r\n"
			+ "	R.COD_SOCIETA = ?\r\n"
			+ "	AND R.PARTITA = ?\r\n"
			+ "	AND T.COD_OPERAZ_MOVIM = ?\r\n AND R.NATURA = '"+RigaMovimento.STORNO+"' "
			+ "ORDER BY\r\n"
			+ "	R.TS_AGGIORNAMENTO DESC";
	public static CachedStatement cTrovaMovimentoINGFINStorno = new CachedStatement(STMT_MOVIMENTO_INGFIN_STORNO);
	//72375 >

	protected BigDecimal iTariffaQuintale;

	protected boolean iCancellaRigaSpesa = false;//70850

	public YDocumentoVendita() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setTariffaQuintale(BigDecimal tariffaQuintale) {
		this.iTariffaQuintale = tariffaQuintale;
		setDirty();
	}

	public BigDecimal getTariffaQuintale() {
		return iTariffaQuintale;
	}

	public boolean isCancellaRigaSpesa() {
		return iCancellaRigaSpesa;
	}

	public void setCancellaRigaSpesa(boolean iCancellaRigaSpesa) {
		this.iCancellaRigaSpesa = iCancellaRigaSpesa;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

	/**
	 * @author Daniele Signoroni 14/05/2024
	 * <p>
	 * Prima stesura.<br>
	 * </p>
	 */
	@Override
	public void completaBO() {
		super.completaBO();
		YClienteVendita cv = (YClienteVendita) getCliente();
		if(cv != null) {
			if(getTariffaQuintale() == null && cv.getTariffaQuint() != null) {
				setTariffaQuintale(cv.getTariffaQuint());
			}
		}
	}

	@Override
	public ErrorMessage convalida(int rc) {
		ErrorMessage em = super.convalida(rc);
		//72375 <
		TestataLista tl = YCostantiValvo.testataListaDocumentoVendita(this, PersistentObject.NO_LOCK);
		if(tl != null && tl.getCodiceTipoLista().equals(YCostantiValvo.codTipoListaTrasferimentoFincantieri())) {
			try {
				rc = trasferimentoFincantieri(tl);
				if(rc <= ErrorCodes.OK) {
					em = CreaMessaggioErrore.daRcAErrorMessage(rc, null);
				}
			} catch (ThipException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		//72375 >
		return em;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List regressione(int rc) {
		List errori = super.regressione(rc);
		if(errori == null || errori.isEmpty()) {
			//72375 <
			TestataLista tl = YCostantiValvo.testataListaDocumentoVendita(this, PersistentObject.NO_LOCK);
			if(tl != null && tl.getCodiceTipoLista().equals(YCostantiValvo.codTipoListaTrasferimentoFincantieri())) {
				try {
					rc = regressioneTrasferimentoFincantieri(tl);
					if(rc <= ErrorCodes.OK) {
						if(errori == null)
							errori = new ArrayList();
						errori.add(CreaMessaggioErrore.daRcAErrorMessage(rc, null));
					}
				} catch (ThipException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
			//72375 >
		}
		return errori;
	}

	@Override
	public int save() throws SQLException {
		int rc = super.save();
		//		creaRigaSpesaTrasporto(); //AGSOF3 deciso di rimuovere spese tr automatiche in save, solo in stampa bolla
		return rc;
	}

	/**
	 * @author TBSOF3
	 * <h1>Creazione della riga spesa di trasporto</h1>
	 * <ul>
	 * 				<li>1.Se sono in cancellazione della riga spesa NON vado a crearne un'altra.</li>
	 * 				<li>2.Se il TipoConsegna della modalita' di consegna NON e' FRANCO_ARRIVO non la creo.</li>
	 * 				<li>3.Se la modalita' di pagamento rientra in quelle contenute nel parametro "YEvitaCalcoloSpeseRigheVendita\ModalitaPagamento" non la creo.</li>
	 * 				<li>4.Se la modalita' di spezione NON e' VE, non la creo.</li>
	 * 				<li>5.Se vengo da proposte di evasione non creo la riga.</li>
	 * 				<li>6.Solo se l'azione magazzino della causale equivale ad USCITA. (23/01/2023)</li>
	 * 				<li>7.Solo se il tipo documento della causale equivale a FATTURA. (23/01/2023)</li>
	 * </ul>
	 */
	public void creaRigaSpesaTrasporto() {
		if(!this.isCancellaRigaSpesa()) { //70850 se sono in cancellazione della riga spesa di trasporto non ricreo la spesa 
			if(this.getModalitaConsegna()!= null) {
				if(this.getModalitaConsegna().getTipoConsegna() == ModalitaConsegna.TP_CNG_F_ARRIVO 
						&& this.isCalcoloRigheSpeseTrasportoAbilitato()
						&& (this.getIdModSpedizione() != null && this.getIdModSpedizione().equals("VE"))
						&& !this.isSalvaDaProposte()
						&& ( this.getCausale() != null && (
								this.getCausale().getAzioneMagazzino() == AzioneMagazzino.USCITA
								&& this.getCausale().getTipoDocumento() == TipoDocumento.FATTURA)
								)
						) {
					BigDecimal totale = YGestioneSpeseRigheVendita.getTotaleSpesaTrasporto(this.getTariffaQuintale(), this.getPesoLordo());
					if(totale.compareTo(BigDecimal.ZERO) >= 1)
						creaRigaSpesa(totale);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected void creaRigaSpesa(BigDecimal totale) {
		String idCausaleSpesaTrasporto = ParametroPsn.getValoreParametroPsn("YCauRigSpeseTrasportoAutomatica", "CausaleSpesaTrasporto");
		if(idCausaleSpesaTrasporto != null) {
			try {
				String where = "ID_ANNO_DOC = '" + this.getAnnoDocumento() + "' "
						+ " AND ID_NUMERO_DOC = '" + this.getNumeroDocumento() + "' "
						+ " AND R_CAU_RIG_DOCVEN = '"+idCausaleSpesaTrasporto+"' "
						+ " AND ID_AZIENDA = '" + Azienda.getAziendaCorrente() + "' ";
				Vector righe = YDocumentoVenRigaPrm.retrieveList(YDocumentoVenRigaPrm.class, where, "", false);
				if(righe.size() > 0) {
					YDocumentoVenRigaPrm docVenRig = (YDocumentoVenRigaPrm)righe.get(0);
					if(docVenRig != null && !docVenRig.isRicalcoloSpese()) {
						//70831
						//DSSOF3 Ri-integrata la UPDATE dato che la save() su oggetto non nuovo andava ad assegnare un
						//nuovo timestamp all'oggetto e questo non era congruo con il timestamp a DB.
						String update = "UPDATE THIP.DOC_VEN_RIG SET IMP_PRC_SPESA = '"+totale+"' " +
								"WHERE ID_AZIENDA = '"+docVenRig.getIdAzienda()+"' AND ID_ANNO_DOC = '"+docVenRig.getAnnoDocumento()+"' " +
								"AND ID_NUMERO_DOC = '"+docVenRig.getNumeroDocumento()+"' AND ID_RIGA_DOC = '"+docVenRig.getNumeroRigaDocumento()+"' " +
								"AND ID_DET_RIGA_DOC = '0' ";
						CachedStatement cs = new CachedStatement(update);
						cs.executeUpdate();
						cs.free();
					}
				}else {
					Articolo speseTrasporto = (Articolo)
							Articolo.elementWithKey(Articolo.class, 
									KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), "SPESE_TR"}), 0);
					YDocumentoVenRigaPrm docVenRig = (YDocumentoVenRigaPrm) Factory.createObject(YDocumentoVenRigaPrm.class);
					docVenRig.setAnnoDocumento(this.getAnnoDocumento());
					docVenRig.setNumeroDocumento(this.getNumeroDocumento());
					docVenRig.setSequenzaRiga(9999);
					docVenRig.setIdCauRig(idCausaleSpesaTrasporto);
					docVenRig.setIdSpesa("001");
					docVenRig.setIdAssogIVA("22");
					docVenRig.setIdArticolo("SPESE_TR");
					docVenRig.setTipoRiga(TipoRiga.SPESE_MOV_VALORE);
					docVenRig.setImportoPercentualeSpesa(totale);
					docVenRig.setTestata(this);
					docVenRig.setSalvaTestata(false);
					docVenRig.setQtaInUMVen(new BigDecimal(1));
					docVenRig.setIdUMRif("nr");
					docVenRig.setAlfanumRiservatoUtente2("Y");
					docVenRig.setDataConsegnaRichiesta(this.getDataConsegnaRichiesta()); //70754
					docVenRig.setDescrizioneArticolo(speseTrasporto.getDescrizioneArticoloNLS() != null ? speseTrasporto.getDescrizioneArticoloNLS().getDescrizione() : "");
					docVenRig.setDataConsegnaConfermata(this.getDataConsegnaConfermata());
					docVenRig.setStatoAvanzamento(StatoAvanzamento.DEFINITIVO);
					//docVenRig.impostaRigaSaldata(); DSSOF3 Per ora non piu saldate
					docVenRig.save();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}else {
			Trace.excStream.println("#### DOCUMENTO -- Eccezione nella creazione della riga spesa trasporto automatica, causale nel parametro null ####" + this.getKey());
			System.out.println("#### DOCUMENTO -- Eccezione nella creazione della riga spesa trasporto automatica, causale nel parametro null ####" + this.getKey());
		}
	}

	/**
	 * 70760 portale
	 * @param ddt
	 * @return
	 */
	public String recuperaChiaveDocDgt(DdtTestataVendita ddt) {
		String key = "";
		ResultSet rs = null;
		try {
			String select = "SELECT ID_AZIENDA , ID_DOCUMENTO_DGT , ID_VERSIONE "
					+ "FROM THIP.DOC_DGT  "
					+ "WHERE R_TIPO_DOC_DGT  = 'DDT_VEN' "
					+ "AND ID_AZIENDA = '"+ddt.getIdAzienda()+"' "
					+ "AND R_ANNO_DOC = '"+ddt.getIdAnnoDdt()+"' "
					+ "AND R_NUMERO_DOC = '"+ddt.getIdNumeroDdt()+"' "
					+ "ORDER BY ID_VERSIONE DESC ";
			CachedStatement cs = new CachedStatement(select);
			rs = cs.executeQuery();
			while(rs.next()) {
				String[] c = {rs.getString(1),rs.getString(2),rs.getString(3),"1"};
				key = KeyHelper.buildObjectKey(c);
			}
			cs.free();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return key;
	}

	/**
	 * 70760 portale
	 * @return
	 */
	public String recuperaChiaveDocDgtFatturaVendita() {
		String key = "";
		ResultSet rs = null;
		try {
			String select = "SELECT ID_AZIENDA , ID_DOCUMENTO_DGT , ID_VERSIONE "
					+ "FROM THIP.DOC_DGT  "
					+ "WHERE R_TIPO_DOC_DGT  = 'FATT_VEN' "
					+ "AND ID_AZIENDA = '"+this.getIdAzienda()+"' "
					+ "AND R_ANNO_DOC = '"+this.getIdAnnoDoc()+"' "
					+ "AND R_NUMERO_DOC = '"+this.getIdNumeroDoc()+"' "
					+ "ORDER BY ID_VERSIONE DESC ";
			CachedStatement cs = new CachedStatement(select);
			rs = cs.executeQuery();
			while(rs.next()) {
				String[] c = {rs.getString(1),rs.getString(2),rs.getString(3),"1"};
				key = KeyHelper.buildObjectKey(c);
			}
			cs.free();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return key;
	}

	/**
	 * 70760 portale
	 * @param ddt
	 * @return
	 */
	public ArrayList<DocumentoDigitale> getListaDocDgtCertificati(DdtTestataVendita ddt){
		ArrayList<DocumentoDigitale> ret = new ArrayList<DocumentoDigitale>();
		ResultSet rs = null;
		try {
			String select = "SELECT ID_AZIENDA , ID_DOCUMENTO_DGT , ID_VERSIONE "
					+ "FROM THIP.DOC_DGT  "
					+ "WHERE R_TIPO_DOC_DGT LIKE '%CERT%' "
					+ "AND ID_AZIENDA = '" + ddt.getIdAzienda() + "' "
					+ "AND R_ANNO_DOC = '" + ddt.getIdAnnoDdt() + "' "
					+ "AND R_NUMERO_DOC = '" + ddt.getIdNumeroDdt() + "' "
					+ "ORDER BY ID_VERSIONE DESC ";
			CachedStatement cs = new CachedStatement(select);
			rs = cs.executeQuery();
			while(rs.next()) {
				String[] c = {rs.getString(1),rs.getString(2),rs.getString(3)};
				String key = KeyHelper.buildObjectKey(c);
				DocumentoDigitale docDgt = (DocumentoDigitale)
						DocumentoDigitale.elementWithKey(DocumentoDigitale.class,
								key, PersistentObject.NO_LOCK);
				if(docDgt != null) {
					ret.add(docDgt);
				}
			}
			cs.free();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return ret;
	}

	/**
	 * 70760 Portale
	 * @return
	 */
	public String getIndirizzoDiConsegna() {
		String ret = "";
		ResultSet rs = null;
		try {
			YOrdineVendita ord = (YOrdineVendita)
					YOrdineVendita.elementWithKey(YOrdineVendita.class,
							this.getKey(), PersistentObject.NO_LOCK);
			if(ord != null) {
				rs = null;
				String stmt = "SELECT LOCA_D, INDI_D, CAP_D, NAZI_D, PROV_D "
						+ "FROM SOFTRE.Y_DOC_VEN_DES_V01 "
						+ "WHERE AZI = '" + ord.getIdAzienda() + "' "
						+ "AND ANNO_DOC = '" + ord.getAnnoDocumento() + "' "
						+ "AND NUM_DOC = '" + ord.getNumeroDocumento() + "'";
				CachedStatement cs = new CachedStatement(stmt);
				rs = cs.executeQuery();
				if(rs.next()) {
					String localita = rs.getString("LOCA_D") != null ? rs.getString("LOCA_D").trim() : "";
					String indirizzo = rs.getString("INDI_D") != null ? rs.getString("INDI_D").trim() : "";
					String cap = rs.getString("CAP_D") != null ? rs.getString("CAP_D").trim() : "";
					String provincia = rs.getString("PROV_D") != null ? rs.getString("PROV_D").trim() : "";
					String nazione = rs.getString("NAZI_D") != null ? rs.getString("NAZI_D").trim() : "";
					ret = indirizzo + " - " + cap + " - " + localita + "(" + provincia + ")" + " - " + nazione;
				}
				cs.free();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (rs != null) {
					rs.close();
				}
			}
			catch (Throwable t) {
				t.printStackTrace(Trace.excStream);
			}
		}
		return ret;
	}

	/**
	 * DSSOF3	12/01/2023	Se la modalita di pagamento dell'ordine vendita rientra in quelle presenti nel parametro
	 * 						non permetto di creare/calcolare la spesa riga trasporto.
	 * @return
	 */
	public boolean isCalcoloRigheSpeseTrasportoAbilitato() {
		String parametroDiDisattivazione = ParametroPsn.getValoreParametroPsn("YEvitaCalcoloSpeseRigheVendita", "ModalitaPagamento");
		if(parametroDiDisattivazione != null) {
			String[] listaModalitaDiConsegna = parametroDiDisattivazione.split("@");
			for(int i = 0 ; i < listaModalitaDiConsegna.length; i++) {
				String modalitaDiConsegna = listaModalitaDiConsegna[i];
				if(modalitaDiConsegna.equals(this.getIdModPagamento()))
					return false;
			}
		}else {
			return true;
		}
		return true;
	}

	//72375 <
	@SuppressWarnings("rawtypes")
	protected int regressioneTrasferimentoFincantieri(TestataLista tl)throws ThipException {
		int rc = ErrorCodes.OK;
		try {
			YRigaMovimento rm = (YRigaMovimento) rigaMovimentoSpostaMerceFincantieri();
			if(rm != null) {
				Vector errori = rm.storna();
				if(errori != null && !errori.isEmpty()) {
					throw new ThipException(errori);
				}else {
					rc = ErrorCodes.OK;
					YRigaMovimento rms = (YRigaMovimento) rigaMovimentoStornoSpostaMerceFincantieri();
					if(rms != null) {
						TestataMovimento mt = rms.getTestataMovimento();
						rc = mt.delete();
					}
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return rc;
	}

	public RigaMovimento rigaMovimentoStornoSpostaMerceFincantieri() {
		YRigaMovimento rm = null;
		String codiceOperazioneMovimentoLogis = ParametroPsn.getValoreParametroPsn("YOpMovScaricoTrasfUds", "IdOperazioneMovimentoScarico");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Database db = ConnectionManager.getCurrentDatabase();
			ps = cTrovaMovimentoINGFINStorno.getStatement();
			db.setString(ps, 1, getIdAzienda());
			String partita = IntegrazioneThipLogis.chiaveDocToString(getAnnoDocumento(), 
					getNumeroDocumento(), 
					String.valueOf(MovimentoMagazzino.AREA_VENDITA)+ String.valueOf(getTipoDocumento()));
			db.setString(ps, 2, partita);
			db.setString(ps, 3, codiceOperazioneMovimentoLogis);
			rs = ps.executeQuery();
			if(rs.next()) {
				rm = (YRigaMovimento) YRigaMovimento.elementWithKey(YRigaMovimento.class, KeyHelper.buildObjectKey(new String[] {
						rs.getString(RigaMovimentoTM.CODICE_SOCIETA),
						rs.getString(RigaMovimentoTM.CODICE_TESTATA),
						rs.getString(RigaMovimentoTM.CODICE_RIGA),
						rs.getString(RigaMovimentoTM.CODICE_DETTAGLIO),
				}), NO_LOCK);
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return rm;
	}

	public RigaMovimento rigaMovimentoSpostaMerceFincantieri() {
		YRigaMovimento rm = null;
		String codiceOperazioneMovimentoLogis = ParametroPsn.getValoreParametroPsn("YOpMovScaricoTrasfUds", "IdOperazioneMovimentoScarico");
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Database db = ConnectionManager.getCurrentDatabase();
			ps = cTrovaMovimentoINGFIN.getStatement();
			db.setString(ps, 1, getIdAzienda());
			String partita = IntegrazioneThipLogis.chiaveDocToString(getAnnoDocumento(), 
					getNumeroDocumento(), 
					String.valueOf(MovimentoMagazzino.AREA_VENDITA)+ String.valueOf(getTipoDocumento()));
			db.setString(ps, 2, partita);
			db.setString(ps, 3, codiceOperazioneMovimentoLogis);
			rs = ps.executeQuery();
			if(rs.next()) {
				rm = (YRigaMovimento) YRigaMovimento.elementWithKey(YRigaMovimento.class, KeyHelper.buildObjectKey(new String[] {
						rs.getString(RigaMovimentoTM.CODICE_SOCIETA),
						rs.getString(RigaMovimentoTM.CODICE_TESTATA),
						rs.getString(RigaMovimentoTM.CODICE_RIGA),
						rs.getString(RigaMovimentoTM.CODICE_DETTAGLIO),
				}), NO_LOCK);
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return rm;
	}

	@SuppressWarnings("rawtypes")
	protected int trasferimentoFincantieri(TestataLista tl) throws ThipException {
		int rc = ErrorCodes.OK;
		Iterator iterRighe = tl.getRigheLista().iterator();
		while(iterRighe.hasNext()) {
			YRigaLista rl = (YRigaLista) iterRighe.next();
			Postazione pt = YCostantiValvo.getPostazioneNonGestita(rl.getCodiceMagFisico());
			Operatore op = YCostantiValvo.getOperatoreGenerico(rl.getCodiceMagFisico());
			Vector missioniTerminate = rl.getMissioniTerminate(pt, op ,null);
			for (Iterator iterator = missioniTerminate.iterator(); iterator.hasNext();) {
				Missione m = (Missione) iterator.next();
				Vector errori = spostaMerceFincantieri(m, tl);
				if(errori != null && !errori.isEmpty()) {
					throw new ThipException(errori);
				}
			}
		}
		return rc;
	}

	@SuppressWarnings("rawtypes")
	public Ubicazione trovaUbicazioneEntMagazzinoTrasferimento() {
		Ubicazione ub = null;
		try {
			CausaleRigaDocVen cauRigDef = getCausale().getCausaleRigaDocumVen();
			if(cauRigDef != null && cauRigDef.getIdCauMagTrasferim() != null && getIdMagazzinoTra() != null) {
				Vector elencoFiltri = new Vector();
				String where = CfgLogTxMovTM.ID_AZIENDA + "='" + getIdAzienda() + "' AND " +
						CfgLogTxMovTM.ID_CAU_MOV_MAG + "='" + cauRigDef.getIdCauMagTrasferim() +"' AND " +
						CfgLogTxMovTM.ID_MAGAZZINO + "='" + getIdMagazzinoTra() +"' AND " +
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
	public Vector spostaMerceFincantieri(Missione m , TestataLista tl) {
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
			Ubicazione ub = trovaUbicazioneEntMagazzinoTrasferimento();
			insMov.setUbicazione(ub);
			insMov.setUbicazioneScelta(m.getUbicazione());
			insMov.setUbicazioneDestinataria(m.getUbicazione());
			Saldo saldo = trovaSaldoCorrenteMerce(m, ub);
			if(saldo != null) {
				insMov.setQuantita(m.getQta1Evasa());
				insMov.setQuantitaPrmErp(m.getQta1Evasa());
				insMov.setSaldoPrimario(saldo);
				insMov.setArticolo(m.getArticolo());
				insMov.setLotto(m.getLotto());
				insMov.setCodiceCausale(codiceCausaleScarico);
				insMov.setCodiceCausaleInversa(codiceCausaleCarico);
				MagLogico mag = (MagLogico) MagLogico.elementWithKey(MagLogico.class, KeyHelper.buildObjectKey(new String[] { Azienda.getAziendaCorrente(), getIdMagazzinoTra()}), 0);
				insMov.setMagLogicoCarico(mag);
				insMov.setMagLogicoScarico(mag);
				insMov.setMagFisico(m.getMagFisico());
				insMov.setDataPartita(TimeUtils.getCurrentDate());
				insMov.setPartita(IntegrazioneThipLogis.chiaveDocToString(getAnnoDocumento(), 
						getNumeroDocumento(), 
						String.valueOf(MovimentoMagazzino.AREA_VENDITA)+ String.valueOf(getTipoDocumento())));
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
	public Saldo trovaSaldoCorrenteMerce(Missione m, Ubicazione ub) {
		String partita = IntegrazioneThipLogis.chiaveDocToString(getAnnoDocumento(), 
				getNumeroDocumento(), 
				String.valueOf(MovimentoMagazzino.AREA_VENDITA)+ String.valueOf(getTipoDocumento()));
		String where = " LOTTO01 = '" + m.getLotto1() + "'"
				+ " AND COD_ARTICOLO = '" + m.getCodiceArticolo() + "' "
				+ " AND COD_MAG_LOGICO = '" + getIdMagazzinoTra() + "'"
				+ " AND COD_SOCIETA = '" + Azienda.getAziendaCorrente() + "'"
				+ " AND COD_UBICAZIONE = '" + ub.getCodice() + "'"
				+ " AND COD_MAG_FISICO = '"+m.getCodiceMagFisico()+"'"
				+ " AND COD_GRUPPO = '"+Azienda.getAziendaCorrente()+"'"
				+ " AND PARTITA = '"+partita+"'"
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
	//72375 >

}
