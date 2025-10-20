package it.thera.thip.vendite.contrattiVE;

import it.thera.thip.base.comuniVenAcq.StatoEvasione;
import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.base.profilo.*;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.cs.GestoreCommit;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaPrmTM;
import it.thera.thip.vendite.generaleVE.*;
import it.thera.thip.vendite.ordineVE.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import com.thera.thermfw.base.*;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.*;
import com.thera.thermfw.security.*;



/**
 *
 * @author NICODEMO
 * 06016  07/11/06  MN  Gestione piano in stato CORRENTE ,
 *                      Trasformazione piano da nuovo/corrente/storico
 * 06452  03/01/07  MN  Implementazione Ricezione piano consegne.
 * 06462  11/01/07  MN  Nel salvataggio della riga ordine vengono ricalcolate le qta.
 *                      Nel salvataggio della riga consegna viene disabilitata la gestione
 *                      della riga ordine collegata (in questo modo non viene nuovamente salvata)
 *                      Corretto il settaggio della data conf e della data produzione della riga ordine.
 * 06582 25/11/07   MN  Quando viene creata una consegna da una riga ordine , deve essere impostato
 *                      il flag di ricalcolo quantità a true.
 * 06833 06/03/07   MN  In fase di creazioen delle righe ordine, la causale di riga
 *                      deve essere quella indicata sul contratto di vendita.
 * 07467 02/07/07   MN  Il campo rifCommessaCliente viene settato sulle righe dell'ordine confermato
 * 08508  14/01/08  MN   Implementazioni varie piani consegne.
 * 08617  29/01/08  MN  Verifica delle quantità del piano in base al totale della
 *                      quantità del piano già spedita ma che lui non ha ancora registrato.
 * 08743  20/02/08  MN  - Nel caso di reallinamento delle quantità in seguiti a DDT non ancora registrato dal cliente
 *                        deve essere considerato il caso in cui la qta della consegna è minore della qta ddt.
 *                        In questo caso la qta della consegna diventa zero, ma la qta dell'ordine resta invariata.
 *                      - Modifiche varie di ottimizzazione.
 * 08815  06/03/08  MN  - L'algoritmo di nettificazione della qtaUltimoDDTNoReg deve essere
 *                        eseguito solo se la dataUltimoDDT è significativa e se la qtaUltimoDDTNoReg
 *                        è maggiore di zero.
 *                      - Durante la fasatura devono essere allinati lo stato e lo statoAvanzamento
 *                        della riga consegna e della riga ordine.
 * 09176  07/05/08  MN   Nel processo di conferma del piano consegne la qtaOrdinata di una nuova riga
 *                       del piano deve essere calcolata come QtaPiano = QtaPiano+QtaSpedRigaOrdine.
 *                       - Modificato l'algoritmo di nettificazione della QtaDDT non ancora registrata
 *                       dal cliente.
 * 10332  12/01/09  MN   Modificato il metodo calcolaQuantitaSpeditaDDTNonRegistrata(), la query di estrazione
 *                       dei DDT non deve considerare il numero ultimo DDT, devono essere estratti i DDT con dataDDT
 *                       strattamente maggiore della DataUltimoDDT.
 * 16440  19/10/12  Linda  Fix anticipato 16439.
 * 17496  28/02/13  AYM  Modifica il ErrorMessage "THIP300283" per aggiungere indicazione della  riga in errore.
 * 25991  27/09/17  DB   Quando la sincronizzazione preve la cancellazione di una riga ordine questa non si cancella
 * 29862  16/09/19  DB   Ricalcolo delle condizioni di vendita anche ad aggiornamento della riga ordine 
 * 30487  09/01/20  DB   Spostato in classe specifica la classe innestata WrapperPianoOrdine
 * 32086  21/10/20  DB   Aggiunte nuove colonne
 * 32227  30/11/20  DB   Non funzionava correttamente il passaggio a storico 
 * 33018  25/02/21  DB   Reso protected metodo
 * 33059  03/03/21  DB   Gancio personalizzazioni
 * 33122  11/03/21  DB   Gancio personalizzazioni gestione DDT
 * 33638  20/05/21  DB   Gancio personalizzazioni  
 * 34442  12/10/21  DB   Ricezione piano consegne con consegne aventi quantità zero. 
 *                       Le righe consegna in fase di sincronizzazione non creano la riga ordine con quantità zero, 
 *                       ma riportano il riferimento alla riga ordine della consegna con data precedente.
 * 
 */
public class GestoreWrapperPianoOrdine {

  public static final BigDecimal ZERO_DEC = new BigDecimal(0);

  public static final String SQL_COUNT_RIGHE_PGM = "SELECT COUNT(*) FROM "+
    PianoConsegneRigaConsegnaTM.TABLE_NAME+" WHERE "+
    PianoConsegneRigaConsegnaTM.ID_AZIENDA+"=? AND "+
    PianoConsegneRigaConsegnaTM.ID_ANNO_PNC+"=? AND "+
    PianoConsegneRigaConsegnaTM.ID_NUMERO_PNC+"=? AND "+
    PianoConsegneRigaConsegnaTM.ID_RIGA_PNC+"=? AND "+
    PianoConsegneRigaConsegnaTM.TIPO_SCADENZA+"<> '"+PianoConsegneRigaConsegna.TIPO_SCAD_INTERVALLO+"' AND "+
    PianoConsegneRigaConsegnaTM.ID_ANNO_ORD+" IS NOT NULL AND "+
    PianoConsegneRigaConsegnaTM.ID_NUMERO_ORD+" IS NOT NULL AND "+
    PianoConsegneRigaConsegnaTM.ID_RIGA_ORD+" IS NOT NULL ";
  public static final CachedStatement csCountRighePgm = new CachedStatement(SQL_COUNT_RIGHE_PGM);

  public static final String SQL_COUNT_RIGHE_ORD = "SELECT COUNT(*) FROM "+OrdineVenditaRigaPrmTM.TABLE_NAME+
    " WHERE "+OrdineVenditaRigaPrmTM.ID_AZIENDA+"=? AND "+
    OrdineVenditaRigaPrmTM.ID_ANNO_ORD+"=? AND "+
    OrdineVenditaRigaPrmTM.ID_NUMERO_ORD+"=? AND ("+
    OrdineVenditaRigaPrmTM.STATO_EVASIONE+"<>'"+StatoEvasione.SALDATO+"' OR "+
    " ( "+OrdineVenditaRigaPrmTM.STATO_EVASIONE+"='"+StatoEvasione.SALDATO+"' AND "+
    OrdineVenditaRigaPrmTM.DATA_CONSEG_RCS+">= ? ))";
  public static final CachedStatement csCountRigheOrd = new CachedStatement(SQL_COUNT_RIGHE_ORD);



  // La riga articolo dovrebbe già avere la lista delle consegne e la lista dei mesi
  // valorizzate correttamente.
  protected PianoConsegneRigaArticolo iPCRigaArticolo;
  protected List iListaConsegne = new ArrayList();
  protected String iIdAzienda;
  protected String iIdAnnoOrd;
  protected String iIdNumeroOrd;

  protected List iRigheOrdine = new ArrayList();
  protected List iRigheWrapperOrdine = new ArrayList();
  protected Map iMapWrapperPianoOrdine = new TreeMap();
  protected GestoreCommit gestoreCommit = null;
  // Errori
  public List errorsPianiArticolo = new ArrayList();
  public List errorsPianiConsegna = new ArrayList();

  // Errori generati al salla save delle consegne
  public Map iMapErrorsConsegne = new HashMap();

  protected boolean iAggiornaAncheConWarning = true;

  // Fix 6833
  protected ContrattoVendita iContrattoVendita;

  // Inizio 8617
  protected java.sql.Date iDataUltimoDDT;
  protected String iNumeroUltDDT;
  protected BigDecimal iQtaUltimoDDT;
  protected BigDecimal iQtaDDTSpedTotNoReg ;

  protected CachedStatement iCsEstrazioneDDT;
  protected List iErrorsRigheCons = new ArrayList();

  protected java.sql.Date iDataSinc ;

  // Fine 8617


  public GestoreWrapperPianoOrdine(){

  }

  public void initialize(PianoConsegneRigaArticolo rigaArticolo ,boolean eseguiCommit){
    if (eseguiCommit){
      try{
        gestoreCommit = new GestoreCommit("GestorePianoCons");
      }catch(SQLException ex){
        ex.printStackTrace(Trace.excStream);
      }
    }
    if (rigaArticolo != null){
      this.iPCRigaArticolo = rigaArticolo;
      if (rigaArticolo.iRigheConsegna.isEmpty())
        rigaArticolo.getRigheConsegnaOneToMany();
      iListaConsegne = rigaArticolo.iRigheConsegna;
      iIdAzienda = rigaArticolo.getIdAzienda();
      iIdAnnoOrd = rigaArticolo.getIdAnnoOrd();
      iIdNumeroOrd = rigaArticolo.getIdNumeroOrd();
      // Inizio 6833
      iContrattoVendita = rigaArticolo.getContrattoVendita();
      // Fine 6833
      // Inizio 8617
      iDataUltimoDDT = rigaArticolo.getDataUltDdt();
      iQtaUltimoDDT = rigaArticolo.getQtaUltimoDDT().getQuantitaInUMRif();
      iNumeroUltDDT = rigaArticolo.getNumeroUltDdt();
      // Fine 8617
      // Inizio mmm
      iDataSinc = rigaArticolo.getDataUltDdt();
      if (iDataSinc == null)
      	iDataSinc = getDataInizioPgm();
      // Fine mmm
    }
  }

  public java.sql.Date getDataSinc(){
  	return iDataSinc;
  }

  public String getIdAzienda(){
    return iIdAzienda;
  }
  public String getIdAnnoOrd(){
    return iIdAnnoOrd;
  }
  public String getIdNumeroOrd(){
    return iIdNumeroOrd;
  }

  public PianoConsegneRigaArticolo getPCRigaArticolo(){
    return iPCRigaArticolo;
  }
  public java.sql.Date getDataInizioPgm(){
    return getPCRigaArticolo().getDataIniPgm();
  }
  public List getListaConsegne(){
    return iListaConsegne;
  }
  public List getRigheWrapperOrdine(){
    return iRigheWrapperOrdine;
  }
  public List getRigheOrdine(){
    return iRigheOrdine;
  }

  public void setAggiornaAncheConWarning(boolean aggAncheConWarning){
    this.iAggiornaAncheConWarning = aggAncheConWarning;
  }
  public boolean getAggiornaAncheConWarning(){
    return iAggiornaAncheConWarning;
  }


  public boolean verificaSincPianoOrdine(){
    int countRighePgm = eseguiCountRighePgm();
    int countRigheOrd = eseguiCountRigheOrdine();
    Trace.println(Trace.XML,"Righe consegne collegate a righe ordine: "+countRighePgm);
    Trace.println(Trace.XML,"Righe ordine: "+countRigheOrd);
    return (countRighePgm == countRigheOrd);
  }


  public void sincPianoOrdine(){
  	boolean errorPresent = false; // 8617
  	caricaWrapperPianoOrdine();
  	// Inizio 8617
  	try{
  		calcolaQuantitaSpeditaDDTNonRegistrata();
  	}catch(SQLException ex){
  		ex.printStackTrace(Trace.excStream);
  	}
  	runFaseA();
  	runFaseB();
    // Inizio 8815 : L'algoritmo di nettificazione della qta ddt deve essere
  	// eseguito solo se questa è maggiore di zero.
  	if (iQtaDDTSpedTotNoReg != null && iQtaDDTSpedTotNoReg.compareTo(ZERO_DEC)>0){
    // Fine 8815
  		List errors = aggiornaMappaWPOConDDTNoReg();
  		if (!errors.isEmpty()){
  			errorPresent = true;
  			iErrorsRigheCons.addAll(errors);
  		}
  	}
  	// Inizio mmm
  	// Elimina le righe ordine che hanno una qtaNewPgm <=0 (ovvero che non risultano più nel piano consegne)
  	riorganizzaListaWPO();
  	// Fine mmm
  	// Fine 8617
  	if (!errorPresent)
  		salvaWrapperPianoOrdine();
  	fine();
  }

  public void fine(){
    try{
      if (gestoreCommit != null)
        gestoreCommit.fine();
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
  }

  public void caricaWrapperPianoOrdine(){
    caricaRigheOrdine();
    caricaWrapperDaPiano();
    caricaMappaWrapperPianoOrdine();
  }

  // fix 33122
  public boolean isRigaDaStornare(WrapperPianoOrdine wpo) {
	  return wpo.isRigaPgm;
  }
  // fine fix 33122
  // Inizio 8617
  public List aggiornaMappaWPOConDDTNoReg(){
    List errors = new ArrayList();
    BigDecimal qtaDDTNoReg = iQtaDDTSpedTotNoReg;
    Iterator iterKey = iMapWrapperPianoOrdine.keySet().iterator();
    while (iterKey.hasNext()){
      java.sql.Date dataRich = (java.sql.Date)iterKey.next();
      List listaWPO = (List)iMapWrapperPianoOrdine.get(dataRich);
      Iterator iter = listaWPO.iterator();
      while (iter.hasNext() && qtaDDTNoReg.compareTo(ZERO_DEC)>0){
        WrapperPianoOrdine wpo = (WrapperPianoOrdine)iter.next();
        // Inizio 9176
        // La nettificazione deve essere eseguita sono per elementi WPO
        // che sono righe consegne e che non derivano da MERGE.
        // fix 33122
        if (isRigaDaStornare(wpo)){
        //if (wpo.isRigaPgm){
        // fine fix 33122 	
          BigDecimal newQtaPgmOrd = wpo.qtaPgm;
          BigDecimal qtaPgmOrdSped = wpo.qtaSpedita;
          // Quantità movimentata QtaSped+QtaPrp+QtaAts
          BigDecimal qtaMovim = wpo.qtaSpedita.add(wpo.qtaPrpEvaOrd).add(wpo.qtaAtsEvaOrd);
          BigDecimal qtaDisp = newQtaPgmOrd.subtract(qtaMovim);
          if (qtaDisp.compareTo(ZERO_DEC)>0){
            if (qtaDisp.compareTo(qtaDDTNoReg)>=0){
              newQtaPgmOrd = newQtaPgmOrd.subtract(qtaDDTNoReg);
              qtaDDTNoReg = ZERO_DEC;
            }
            else{
              qtaDDTNoReg = qtaDDTNoReg.subtract(qtaDisp);
              newQtaPgmOrd = qtaMovim;
            }
          }
          // Fine 9176
          Trace.println(Trace.XML,"Consegna del: "+wpo.dataRic+" QtaOrd "+newQtaPgmOrd+" QtaSped "+qtaPgmOrdSped);
          Trace.println(Trace.XML,"Consegna del: "+wpo.dataRic+" TotaleQtaDDtNoreg "+qtaDDTNoReg);
          wpo.qtaPgm = newQtaPgmOrd;
          Trace.println(Trace.XML,">>Consegna del: "+wpo.dataRic+" QtaOrd "+wpo.qtaPgm+" QtaSped "+qtaPgmOrdSped);
        }
      }
    }
    if (qtaDDTNoReg.compareTo(ZERO_DEC)>0){
      //Fix 16440 inizio
      //ErrorMessage err = new ErrorMessage("THIP300034", new String[]{"", "", ""});
      //ErrorMessage err = new ErrorMessage("THIP300283", qtaDDTNoReg.toString());//Fix 17496
      String idRigaPnc = (getPCRigaArticolo()!=null && getPCRigaArticolo().getIdRigaPnc()!= null) ? getPCRigaArticolo().getIdRigaPnc().toString():"" ; //Fix 17496
      ErrorMessage err = new ErrorMessage("THIP300283", new String[]{qtaDDTNoReg.toString(), idRigaPnc});//Fix 17496
      //Fix 16440 fine
      errorsPianiConsegna.add(err);
      errors.add(err);
    }

    return errors;
  }
  // Fine 8617


  public void caricaMappaWrapperPianoOrdine(){
    Iterator iter = getRigheWrapperOrdine().iterator();
    java.sql.Date dataRich;
    List listaWPO = null;
    while (iter.hasNext()){
      WrapperPianoOrdine wpo = (WrapperPianoOrdine)iter.next();
      dataRich = wpo.dataRic;
      if (!iMapWrapperPianoOrdine.containsKey(dataRich)){
        listaWPO = new ArrayList();
        listaWPO.add(wpo);
        iMapWrapperPianoOrdine.put(dataRich, listaWPO);
      }
      else{
        listaWPO = (List)iMapWrapperPianoOrdine.get(dataRich);
        listaWPO.add(wpo);
      }
    }
  }

  /**
   * Prendo considerazione le righe ordine che hanno una data
   * consegna richiesta minore della data inizio pgm
   *
   */
  public void runFaseA(){
    Iterator iter = getRigheOrdine().iterator();
    List listaWPO = null;
    while (iter.hasNext()){
      OrdineVenditaRigaPrm rigaOrd = (OrdineVenditaRigaPrm)iter.next();
      java.sql.Date dataRich = rigaOrd.getDataConsegnaRichiesta();
      if (!iMapWrapperPianoOrdine.containsKey(dataRich)){
        if (dataRich.compareTo(getDataInizioPgm())<0){
          listaWPO = new ArrayList();
          // fix 30487
          WrapperPianoOrdine wpo = (WrapperPianoOrdine)Factory.createObject(WrapperPianoOrdine.class);
          wpo.inizializza(rigaOrd);
          // fine fix 30487
          // Inizio 23433
          if (isAttivaGestioneSaldaRigheOrd() && rigaOrd.getStatoEvasione() != StatoEvasione.SALDATO){
          	if (getQuantitaMovimentata(rigaOrd).compareTo(ZERO_DEC)>0){
              rigaOrd.setQtaInUMRif(getQuantitaMovimentata(rigaOrd)); 
              rigaOrd.setRicalcoloQtaFattoreConv(true);
              //Azione di saldo riga ordine
              wpo.azioneOrd = WrapperPianoOrdine.AZ_SALDA_RIGAORD;
          	}
          	else{
          		wpo.azioneOrd = WrapperPianoOrdine.AZ_ORD_DELETE;
              wpo.azione = WrapperPianoOrdine.AZ_ORD_DELETE;
          	}
          }
          // Fine 23433
          listaWPO.add(wpo);
          iMapWrapperPianoOrdine.put(dataRich, listaWPO);
          // Rimuovo la riga ordine già processata dalla fase A
          iter.remove();

        }
      }
      else{
        listaWPO = (List)iMapWrapperPianoOrdine.get(dataRich);
        Iterator iterWPO = listaWPO.iterator();
        while (iterWPO.hasNext()){
          WrapperPianoOrdine wpo = (WrapperPianoOrdine)iterWPO.next();
          if (wpo.numRigaOrd == null){
            wpo.numRigaOrd = rigaOrd.getNumeroRigaDocumento();
            // Inizio 8617
            wpo.qtaSpedita = rigaOrd.getQuantitaSpedita().getQuantitaInUMRif();
            wpo.qtaPrpEvaOrd = rigaOrd.getQtaPropostaEvasione().getQuantitaInUMRif();
            wpo.qtaAtsEvaOrd = rigaOrd.getQtaAttesaEvasione().getQuantitaInUMRif();
            // Fine 8617
            // Inizio 9176
            // Se la riga del WPO deriva da un merge tra riga ordine e riga
            // consegna della stessa data, allora la qtaPgm deve essere
            // incrementata della qtaSpediota della riga ordine.
            if (wpo.azione != WrapperPianoOrdine.AZ_PGM_MERGE)
            	wpo.qtaPgm = this.calcoloQtaFase(wpo); //wpo.qtaPgm.add(wpo.qtaSpedita.add(wpo.qtaAtsEvaOrd).add(wpo.qtaPrpEvaOrd)); // fix 33059
            // Fine 9176
            wpo.isRigaOrd = true;
            wpo.azioneOrd = WrapperPianoOrdine.AZ_ORD_UPDATE;
            wpo.azionePgm = WrapperPianoOrdine.AZ_NESSUNA;
            wpo.azione = WrapperPianoOrdine.AZ_ORD_UPDATE;
          }
        }
        // Rimuovo la riga ordine già processata dalla fase A
        iter.remove();
      }
    }
  }

  // Inizio fix 9176
  /**
   * Per ogni riga del WPO , se l'azione è MERGE (quindi creazione
   * di una consegna da riga ordine ) e la qta del nuovo programma è
   * maggiore della qta movimentata della riga ordine, la qta della riga Pgm
   * deve essere abbassata alla qtaMovimentata e devono essere aggiornate sia la riga ordine
   * che la riga consegna.
   * Se la qtaPgm risulta minore o uguale a zero, la riga deve essere eliminata.
   */
  public void riorganizzaListaWPO(){
  	List listaWPO = leggiListaPianoOrdine();
    Iterator iter = listaWPO.iterator();
    while (iter.hasNext()){
      WrapperPianoOrdine wpo = (WrapperPianoOrdine)iter.next();
      BigDecimal qtaMov = wpo.qtaSpedita.add(wpo.qtaPrpEvaOrd).add(wpo.qtaAtsEvaOrd);
      if (wpo.azione == WrapperPianoOrdine.AZ_PGM_MERGE ){
      	if (wpo.qtaPgm.compareTo(qtaMov)>0){
      		wpo.qtaPgm = qtaMov;
      		wpo.qtaPgmRigaOrd = qtaMov;
      		wpo.azione = WrapperPianoOrdine.AZ_ORD_UPDATE;
      	}
      	if (wpo.qtaPgm.compareTo(ZERO_DEC)<=0)
      		wpo.azione = WrapperPianoOrdine.AZ_ORD_DELETE;
      }
    }
  }
  // Fine 9176


  // fix 33059 
  public BigDecimal calcoloQtaFase(WrapperPianoOrdine wpo) {
	  return wpo.qtaPgm.add(wpo.qtaSpedita.add(wpo.qtaAtsEvaOrd).add(wpo.qtaPrpEvaOrd)); 

  }
  // fine fix 33059

  // fix 33638
  public boolean recuperoQuestaConsegna(WrapperPianoOrdine wpo, OrdineVenditaRigaPrm rigaOrd) {
	  return !wpo.isRigaOrd;
  }
  // fine fix 33638

  /**
   * Prendo in considerazione le righe ordine che hanno una data
   * consegna richiesta maggiore o uguale alla data inizio pgm
   *
   */
  public void runFaseB(){
    Iterator iter = getRigheOrdine().iterator();
    List listaWPO = null;
    while (iter.hasNext()){
      OrdineVenditaRigaPrm rigaOrd = (OrdineVenditaRigaPrm)iter.next();
      java.sql.Date dataRich = rigaOrd.getDataConsegnaRichiesta();
      if (!iMapWrapperPianoOrdine.containsKey(dataRich)){
      	if (dataRich.compareTo(getDataInizioPgm())>=0){
          boolean rigaOrdInserita = false;
          Iterator iterWPO = iMapWrapperPianoOrdine.values().iterator();
          while (iterWPO.hasNext() && !rigaOrdInserita){
            List l = (List)iterWPO.next();
            Iterator it = l.iterator();
            while (it.hasNext() && !rigaOrdInserita){
              WrapperPianoOrdine wpo = (WrapperPianoOrdine)it.next();
              if (recuperoQuestaConsegna(wpo, rigaOrd)){  // fine fix 33638
              //if (!wpo.isRigaOrd){   // fix 33638
                // 9176 Solo se la data cons rich. è maggiore della data ultima bolla,
              	// la riga ordine puoò essere sincronizzata
                if (!isAttivaGestioneSaldaRigheOrd() &&   // Fix 23433
                		(getDataUltimoDDT() == null ||
                		dataRich.compareTo(getDataUltimoDDT())>0)  ){
                	wpo.isRigaOrd = true;
                  wpo.numRigaOrd = rigaOrd.getNumeroRigaDocumento();
                  // Inizio 8617
                  wpo.qtaSpedita = rigaOrd.getQuantitaSpedita().getQuantitaInUMRif();
                  wpo.qtaPrpEvaOrd = rigaOrd.getQtaPropostaEvasione().getQuantitaInUMRif();
                  wpo.qtaAtsEvaOrd = rigaOrd.getQtaAttesaEvasione().getQuantitaInUMRif();
                  // Fine 8617
                  // Inizio 9176
                  // Se la riga WPO deriva da un UPDATE (RigaConsegna->RigaOrdine)
                  // la qta del nuovo programma deve essere incrementata della
                  // qta movimentata della riga ordine
                  // fix 33059
                  wpo.qtaPgm = calcoloQtaFase(wpo); 
                  //wpo.qtaPgm = wpo.qtaPgm.add(wpo.qtaSpedita.add(wpo.qtaAtsEvaOrd).add(wpo.qtaPrpEvaOrd)); // 9176
                  // fix 33059
                  // Fine 9176
                  wpo.azioneOrd = WrapperPianoOrdine.AZ_ORD_UPDATE;
                  wpo.azionePgm = WrapperPianoOrdine.AZ_NESSUNA;
                  wpo.azione = WrapperPianoOrdine.AZ_ORD_UPDATE;
                  rigaOrdInserita = true;
                }
              }
            }
          }
          // Tutte righe del wrapper sono già state assegnate a righe d'ordine.
          if (!rigaOrdInserita){
            BigDecimal qtaMov = getQuantitaMovimentata(rigaOrd);
            WrapperPianoOrdine wpo = null;
            // Se la riga d'ordine non è stata ancora movimentata, può
            // essere eliminata fisicamente. Per fare ciò, vien creata una riga
            // wrapper piano ordine che ha come rif ordine la riga ordine da cancellare,
            // e come azione su ordine la DELETE.
            if (qtaMov.compareTo(ZERO_DEC)<=0){
              listaWPO = new ArrayList();
              //wpo = new WrapperPianoOrdine(rigaOrd);
              // fix 30487
              wpo = (WrapperPianoOrdine)Factory.createObject(WrapperPianoOrdine.class);
              wpo.inizializza(rigaOrd);
              // fine fix 30487
              wpo.azioneOrd = WrapperPianoOrdine.AZ_ORD_DELETE;
              wpo.azione = WrapperPianoOrdine.AZ_ORD_DELETE;
            }
            // La riga ordine risulta movimentata, quindi deve essere creata una riga
            // wrapper piano ordine , associata alla riga ordine, con azione MERGE.
            else if (!isAttivaGestioneSaldaRigheOrd()){ // Fix 23433
              listaWPO = new ArrayList();
              //wpo = new WrapperPianoOrdine(rigaOrd);
              // fix 30487
              wpo = (WrapperPianoOrdine)Factory.createObject(WrapperPianoOrdine.class);
              wpo.inizializza(rigaOrd);
              // fine fix 30487
            }
            // Inizio 23433
            else{ // isAttivaGestioneSaldaRigheOrd() // Fix 23433
            	// le righe ordine non associate devono essere saldate.
              listaWPO = new ArrayList();
              //wpo = new WrapperPianoOrdine(rigaOrd);
              // fix 30487
              wpo = (WrapperPianoOrdine)Factory.createObject(WrapperPianoOrdine.class);
              wpo.inizializza(rigaOrd);
              // fine fix 30487
              if (rigaOrd.getStatoEvasione() != StatoEvasione.SALDATO && getQuantitaMovimentata(rigaOrd).compareTo(ZERO_DEC)>0){
                // Imposta la qta ordinata della riga ordine con la qta movimentata
                rigaOrd.setQtaInUMRif(getQuantitaMovimentata(rigaOrd)); 
                rigaOrd.setRicalcoloQtaFattoreConv(true);
                //Azione di saldo riga ordine
                wpo.azioneOrd = WrapperPianoOrdine.AZ_SALDA_RIGAORD;
              }
            }
            // Fine 23433
            listaWPO.add(wpo);
            iMapWrapperPianoOrdine.put(dataRich, listaWPO);
          }
        }
      }
      // Dovrebbe contenere eventuali righe ordine da eliminare
      else{
        listaWPO = (List)iMapWrapperPianoOrdine.get(dataRich);
        BigDecimal qtaMov = getQuantitaMovimentata(rigaOrd);
        WrapperPianoOrdine wpo = null;
        if (qtaMov.compareTo(ZERO_DEC)<=0){
          //wpo = new WrapperPianoOrdine(rigaOrd);
            // fix 30487
            wpo = (WrapperPianoOrdine)Factory.createObject(WrapperPianoOrdine.class);
            wpo.inizializza(rigaOrd);
            // fine fix 30487
          wpo.azioneOrd = WrapperPianoOrdine.AZ_ORD_DELETE;
          wpo.azione = WrapperPianoOrdine.AZ_ORD_DELETE;
          listaWPO.add(wpo);
        }
        else{
          //wpo = new WrapperPianoOrdine(rigaOrd);
            // fix 30487
            wpo = (WrapperPianoOrdine)Factory.createObject(WrapperPianoOrdine.class);
            wpo.inizializza(rigaOrd);
            // fine fix 30487
          listaWPO.add(wpo);
        }
      }
    }
  }

  protected BigDecimal getQuantitaMovimentata(OrdineVenditaRigaPrm rigaOrd){  // fix 33018
    BigDecimal qtaMov = ZERO_DEC;
    BigDecimal qtaSpe = rigaOrd.getQuantitaSpedita().getQuantitaInUMRif();
    BigDecimal qtaProp = rigaOrd.getQtaPropostaEvasione().getQuantitaInUMRif();
    BigDecimal qtaAts = rigaOrd.getQtaAttesaEvasione().getQuantitaInUMRif();
    qtaMov = qtaSpe.add(qtaProp).add(qtaAts);
    return qtaMov;

  }



  /**
   * Estrazione delle righe ordine che hanno azienda, anno, numero del contratto,
   * che sono saldate, e quelle non saldate con data richiesta maggiore o uguale
   * alla data inizio programma della riga articolo.
   *
   *
   */
  public void caricaRigheOrdine(){
    Database db = ConnectionManager.getCurrentDatabase();

    String where = OrdineVenditaRigaPrmTM.ID_AZIENDA+"='"+getIdAzienda()+"' AND "+
    OrdineVenditaRigaPrmTM.ID_ANNO_ORD+"='"+getIdAnnoOrd()+"' AND "+
    OrdineVenditaRigaPrmTM.ID_NUMERO_ORD+"='"+getIdNumeroOrd()+"' AND "+
    " ( "+OrdineVenditaRigaPrmTM.STATO_EVASIONE+"<>'"+StatoEvasione.SALDATO+"' OR "+
    "( "+OrdineVenditaRigaPrmTM.STATO_EVASIONE+"='"+StatoEvasione.SALDATO+"' AND "+
    OrdineVenditaRigaPrmTM.DATA_CONSEG_RCS+">="+db.getLiteral(getDataInizioPgm())+" ))";
    try{
      iRigheOrdine =  OrdineVenditaRigaPrm.retrieveList(OrdineVenditaRigaPrm.class, where,"",false);
    }catch(Exception ex){
      ex.printStackTrace(Trace.excStream);
    }
  }

  public void caricaWrapperDaPiano(){
    Iterator iter = getListaConsegne().iterator();
    WrapperPianoOrdine wpo = null;
    while (iter.hasNext()){
      PianoConsegneRigaConsegna cons = (PianoConsegneRigaConsegna)iter.next();
      if (cons.getTipoScadenza() != PianoConsegneRigaConsegna.TIPO_SCAD_INTERVALLO){
        //wpo = new WrapperPianoOrdine(cons);
          // fix 30487
         wpo = (WrapperPianoOrdine)Factory.createObject(WrapperPianoOrdine.class);
          wpo.inizializza(cons);
          // fine fix 30487
    	  
        iRigheWrapperOrdine.add(wpo);
      }
    }
  }

  public void printRigheWrapperPianoOrdine(){
    Iterator iter = iMapWrapperPianoOrdine.values().iterator();
    //Trace.println(Trace.XML, Utils.format("DataRic", 10)+Utils.format("QtaPgm", 10)+Utils.format("KRigOrd", 10)+Utils.format("RigaOrd", 10)+Utils.format("RigaPgm", 10)+Utils.format("AzOrd", 10)+Utils.format("AzPgm", 10)+Utils.format("Azione", 10));
    Trace.println(Trace.XML, Utils.format("DataRic", 10)+Utils.format("QtaPgm", 10)+Utils.format("KRigOrd", 10)+Utils.format("RigaOrd", 10)+Utils.format("RigaPgm", 10)+Utils.format("Azione", 10));
    while (iter.hasNext()){
      List l = (List)iter.next();
      Iterator it = l.iterator();
      while (it.hasNext()){
        WrapperPianoOrdine wpo = (WrapperPianoOrdine)it.next();
        wpo.printWrapper();
      }
    }
  }


  /**
   * Azione = I : Creazione di una riga ordine da WPO
   * Azione = U : Aggiornamento della riga ordine collegata al WPO
   * Azione = M : Creazione riga consegna da WPO
   * Azione = D : Cancellazione della riga ordine collegata al WPO
   *
   */
  public void salvaWrapperPianoOrdine(){
    OrdineVenditaRigaPrm rigaOrd = null;
    List righeOrdineSinc = new ArrayList();
    List righeConsegna = new ArrayList();
    printRigheWrapperPianoOrdine();
    List listaWPO = leggiListaPianoOrdine();
    Iterator iter = listaWPO.iterator();
    while (iter.hasNext()){
      WrapperPianoOrdine wpo = (WrapperPianoOrdine)iter.next();
      rigaOrd = null;  // fix 34442
      switch (wpo.azione){
        case WrapperPianoOrdine.AZ_ORD_INSERT:
          Trace.println(Trace.XML, "\nCreazione riga ordine da WPO");
          // Inizio 8743
          // Se la qta pgm è maggiore di zero , la riga ordine deve essere creata
          if (wpo.qtaPgm.compareTo(ZERO_DEC)>0)
          	rigaOrd = creaRigaOrdineDaWPO(wpo);
          // Se la qta Pgm è uguale a zero la riga ordine non deve essere creata
          // e la riga consegna deve essere annullata.
          else{
          	wpo.statoRigaPgm = DatiComuniEstesi.ANNULLATO;
          	aggiornaRigaConsegna(wpo);
          }
          // Fine 8743
          if (rigaOrd != null){
            salvaRigaOrdine(rigaOrd);
            wpo.numRigaOrd = rigaOrd.getNumeroRigaDocumento();
            aggiornaRigaConsegna(wpo);
            righeOrdineSinc.add(rigaOrd);
          }
          break;
        case WrapperPianoOrdine.AZ_ORD_UPDATE:
        	//70988 AGSOF3 se la riga ordine che lo standard sta provando ad aggiornare è in stato definitivo 
        	//non faccio fare niente
        	String yChiaveRiga = KeyHelper.buildObjectKey(new String[]{getIdAzienda(),
                    getIdAnnoOrd(), getIdNumeroOrd(), String.valueOf(wpo.numRigaOrd)});
        	try{
                rigaOrd = (OrdineVenditaRigaPrm)OrdineVenditaRigaPrm.elementWithKey(OrdineVenditaRigaPrm.class, yChiaveRiga, PersistentObject.NO_LOCK );
              }catch(SQLException ex){
                ex.printStackTrace(Trace.excStream);
              }
              if (rigaOrd != null && rigaOrd.getStatoAvanzamento() == StatoAvanzamento.DEFINITIVO){
            	  
              }else {
          // Inizio 23433
        	if (isAttivaGestioneSaldaRigheOrd() && wpo.azioneOrd == WrapperPianoOrdine.AZ_SALDA_RIGAORD){
          	Trace.println(Trace.XML, "\nSalda riga ordine non esistente nel piano consegne");
            String keyRigaOrd = KeyHelper.buildObjectKey(new String[]{getIdAzienda(),
                getIdAnnoOrd(), getIdNumeroOrd(), String.valueOf(wpo.numRigaOrd)});
            try{
              rigaOrd = (OrdineVenditaRigaPrm)OrdineVenditaRigaPrm.elementWithKey(OrdineVenditaRigaPrm.class, keyRigaOrd, PersistentObject.NO_LOCK );
            }catch(SQLException ex){
              ex.printStackTrace(Trace.excStream);
            }
            if (rigaOrd != null){
            	rigaOrd.setQtaInUMRif(wpo.qtaPgm);
            	rigaOrd.setRicalcoloQtaFattoreConv(true);
            	rigaOrd.setSaldoManuale(true);
            }
        	}
          else{
          	Trace.println(Trace.XML, "\nAggiornamento riga ordine collegata al WPO");
            rigaOrd = aggiornaRigaOrdineDaWPO(wpo);
          }
          if (rigaOrd != null  && rigaOrd.getQtaInUMRif().compareTo(new BigDecimal("0"))>0){   // fix 34442
            salvaRigaOrdine(rigaOrd);
            righeOrdineSinc.add(rigaOrd);
            int ret = aggiornaRigaConsegna(wpo);
            if (ret == 0)
              righeConsegna.add(creaRigaConsegnaDaWPO(wpo));
          }
          // fix 34442
          // Questo è il caso in cui la quantità della riga ordine sia zero allora cancello la riga ordine
          else if (rigaOrd!=null) {
        	  eliminaRigaOrdineDaWPO(wpo);
        	  wpo.numRigaOrd = null;
        	  int ret = aggiornaRigaConsegna(wpo);
              if (ret == 0)
                righeConsegna.add(creaRigaConsegnaDaWPO(wpo));    
              Trace.println(Trace.XML, "\nCancellazione riga ordine collegata al WPO");
          }
          // fine fix 34442
          // Fine 23433
              }//70988 AGSOF3 fin
          break;
        case WrapperPianoOrdine.AZ_PGM_MERGE:
        	Trace.println(Trace.XML, "\nCreazione riga consegna da WPO");
          righeConsegna.add(creaRigaConsegnaDaWPO(wpo));
          break;
        case WrapperPianoOrdine.AZ_ORD_DELETE:
          Trace.println(Trace.XML, "\nCancellazione riga ordine collegata al WPO");
          eliminaRigaOrdineDaWPO(wpo);
          break;
      }
      Trace.println(Trace.XML, "------------------------------------------------");
      wpo.printWrapper();
      Trace.println(Trace.XML, "------------------------------------------------");
    }
    try{
      //salvaRigheOrdine(righeOrdineSinc);
      salvaRigheConsegna(righeConsegna);
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
  }

  public int salvaRigaOrdine(OrdineVenditaRigaPrm rigaOrd){
    int res = 0;
    try{
      // Inizio 6462
      //rigaOrd.setRicalcoloQtaFattoreConv(getRicalcoloQta());
      rigaOrd.ricalcoloQuantitaRiga();
      // Fine 6462
      rigaOrd.setSaveFromPDC(true); //  8743
      res = rigaOrd.save();
      if (res>0)
        executeCommit();
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
    return res;
  }

  public void salvaRigheConsegna(List righeConsegna) throws SQLException{
    int ret = 0;
    Iterator iter = righeConsegna.iterator();
    while (iter.hasNext()){
      PianoConsegneRigaConsegna rigaCons = (PianoConsegneRigaConsegna)iter.next();
      if (rigaCons.getQtaInUMVen() != null && rigaCons.getQtaInUMVen().compareTo(ZERO_DEC)>0){
        // Inizio 6462
        // Le righe ordine collegate sono già state salvate dal processo
        // di sincronizzazione.
        rigaCons.setAbilitaGesRigaOrdCollegata(false);
        // Fine 6462
        ret = rigaCons.save();
      }
      else{
        ret = rigaCons.delete();
      }
      if (ret >0)
        executeCommit();
    }
  }


  public int aggiornaRigaConsegna(WrapperPianoOrdine wpo){
    int res = 0;
    String annoPiano = getPCRigaArticolo().getIdAnnoPnc();
    String numPiano = getPCRigaArticolo().getIdNumeroPnc();
    Integer rigaPiano = getPCRigaArticolo().getIdRigaPnc();
    String keyConsegna = KeyHelper.buildObjectKey(new String[]{getIdAzienda(),
        annoPiano, numPiano, String.valueOf(rigaPiano), String.valueOf(wpo.numCons)});
    PianoConsegneRigaConsegna rigaCons = null;
    try{
      rigaCons = PianoConsegneRigaConsegna.elementWithKey(keyConsegna, PersistentObject.NO_LOCK);
      if (rigaCons != null){
        rigaCons.setIdAnnoOrd(getIdAnnoOrd());
        rigaCons.setIdNumeroOrd(getIdNumeroOrd());
        rigaCons.setIdRigaOrd(wpo.numRigaOrd);
        rigaCons.setStatoCnfPrv(wpo.statoConfPrev);
        rigaCons.setQtaInUMVen(wpo.qtaPgm);// 8617
        rigaCons.getDatiComuniEstesi().setStato(wpo.statoRigaPgm); // Fix 8743
        ErrorMessage err = rigaCons.checkSaveConsegna();
        if (err != null){
          iMapErrorsConsegne.put(rigaCons.getKey(),err);
        }
        // Fine 6462
        if (okSave(err)){
          // Inizio 6462
          // La riga ordine è già stata salvata dal processo di sincronizzazione,
          // quindi per evitare un ulteriore salvataggio dalla gestione della
          // riga consegna, viene settato un booleano.
          rigaCons.setAbilitaGesRigaOrdCollegata(false);
          // Fine 6462
          res = rigaCons.save();
          if (res >0)
            executeCommit();
        }
      }
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
    return res;
  }

  /**
   * Verifica se la consegna deve essere salvata
   * @param err
   * @return
   */
  private boolean okSave(ErrorMessage err){
    boolean ok = true;
    if (!getAggiornaAncheConWarning() && err != null)
      ok = false;
    return ok;
  }

  public OrdineVenditaRigaPrm creaRigaOrdineDaWPO(WrapperPianoOrdine wpo){
    OrdineVenditaRigaPrm rigaOrd = (OrdineVenditaRigaPrm)Factory.createObject(OrdineVenditaRigaPrm.class);
    // Chiave Azienda/Anno/Numero
    rigaOrd.setIdAzienda(getIdAzienda());
    rigaOrd.setAnnoDocumento(getIdAnnoOrd());
    rigaOrd.setNumeroDocumento(getIdNumeroOrd());
    // Inizio 6833
    ContrattoVendita contrattoVen = getContrattoVendita();
    if (contrattoVen != null){
      String idCausaleRiga = contrattoVen.getIdCauRigaOrdVen();
      rigaOrd.setIdCauRig(idCausaleRiga);
    }
    // Fine 6833
    rigaOrd.completaBO();
    rigaOrd.setDataConsegnaRichiesta(wpo.dataRic);
    // Inizio 6462
    rigaOrd.setDataConsegnaConfermata(wpo.dataConf);
    rigaOrd.setDataConsegnaProduzione(wpo.dataConf);
    // Fine 6462
    // Inizio 8743
    if (wpo.qtaPgm.compareTo(ZERO_DEC)>0)
    	rigaOrd.setQtaInUMRif(wpo.qtaPgm);
    else
    	rigaOrd.setQtaInUMRif(wpo.qtaPgmRigaOrd);

    // Fine 8743
    rigaOrd.setStatoAvanzamento(wpo.statoAvOrdine);
    rigaOrd.setRifCommessaCli(wpo.rifCommessaCli); // Fix 7467
    rigaOrd.getDatiComuniEstesi().setStato(wpo.statoRigaPgm); // Fix 8815
    // fix 32086
    rigaOrd.setStringaEDI1(wpo.stringaEDI1);
    rigaOrd.setStringaEDI2(wpo.stringaEDI2);
    rigaOrd.setDecimaleEDI1(wpo.decimaleEDI1);
    rigaOrd.setDecimaleEDI2(wpo.decimaleEDI2);
    // fine fix 32086
    return rigaOrd;
  }

  public OrdineVenditaRigaPrm aggiornaRigaOrdineDaWPO(WrapperPianoOrdine wpo){
    String keyRigaOrd = KeyHelper.buildObjectKey(new String[]{getIdAzienda(),
          getIdAnnoOrd(), getIdNumeroOrd(), String.valueOf(wpo.numRigaOrd)});
    OrdineVenditaRigaPrm rigaOrd = null;
    try{
      rigaOrd = (OrdineVenditaRigaPrm)OrdineVenditaRigaPrm.elementWithKey(OrdineVenditaRigaPrm.class, keyRigaOrd, PersistentObject.NO_LOCK );
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
    if (rigaOrd != null){
      if (wpo.dataRic.compareTo(getDataSinc())>0){
      	rigaOrd.setDataConsegnaRichiesta(wpo.dataRic);
        // Inizio 6462
        rigaOrd.setDataConsegnaConfermata(wpo.dataConf);
        rigaOrd.setDataConsegnaProduzione(wpo.dataConf);
        // Fine 6462
      }
      // Inizio 6515
      BigDecimal qtaMovim = getQuantitaMovimentata(rigaOrd);
      if (wpo.qtaPgm.compareTo(qtaMovim)>=0 ||  getAggiornaAncheConWarning()){
      	rigaOrd.setQtaInUMRif(wpo.qtaPgm);
      }
      // Fine 6515
      rigaOrd.setStatoAvanzamento(wpo.statoAvOrdine);
      rigaOrd.setRifCommessaCli(wpo.rifCommessaCli); // Fix 7467
      rigaOrd.getDatiComuniEstesi().setStato(wpo.statoRigaPgm);// Fix 8815
      // fix 29862
    	if (getContrattoVendita()!=null && getContrattoVendita().getIdCondVen()== ContrattoVendita.DA_LISTINO ){
    		try {
    			rigaOrd.forzaRicalcoloDatiVendita();
    		}
    		catch(Exception ex){
    			ex.printStackTrace();
    		}
    	}
      
      // fine fix 29862
      // fix 32086
        rigaOrd.setStringaEDI1(wpo.stringaEDI1);
        rigaOrd.setStringaEDI2(wpo.stringaEDI2);
        rigaOrd.setDecimaleEDI1(wpo.decimaleEDI1);
        rigaOrd.setDecimaleEDI2(wpo.decimaleEDI2);
      // fine fix 32086	
    }
    return rigaOrd;
  }

  public PianoConsegneRigaConsegna creaRigaConsegnaDaWPO(WrapperPianoOrdine wpo){
    String annoPiano = getPCRigaArticolo().getIdAnnoPnc();
    String numPiano = getPCRigaArticolo().getIdNumeroPnc();
    Integer rigaPiano = getPCRigaArticolo().getIdRigaPnc();
    PianoConsegneRigaConsegna cons = (PianoConsegneRigaConsegna)Factory.createObject(PianoConsegneRigaConsegna.class);
    cons.setIdAzienda(getIdAzienda());
    cons.setIdAnnoPnc(annoPiano);
    cons.setIdNumeroPnc(numPiano);
    cons.setIdRigaPnc(rigaPiano);
    cons.setDataConsegRcs(wpo.dataRic);
    cons.setDataConsEdi(wpo.dataRic);
    cons.setDataConsegCfm(wpo.dataConf); // Fix 8508
    // Inizio 6582
    cons.setRicalcoloQta(true);
    // Fine 6582
    cons.setQtaInUMVen(wpo.qtaPgm);
    cons.setIdAnnoOrd(getIdAnnoOrd());
    cons.setIdNumeroOrd(getIdNumeroOrd());
    cons.setIdRigaOrd(wpo.numRigaOrd);
    cons.setStatoCnfPrv(wpo.statoConfPrev);
    cons.setTipoScadenza(PianoConsegneRigaConsegna.TIPO_SCAD_GEN_PIANO_RIGHE_ORD);
    // Inizio 8815
    cons.getDatiComuniEstesi().setStato(wpo.statoRigaPgm);
    // Fine 8815
    // fix 32086
    cons.setStringaEDI1(wpo.stringaEDI1);
    cons.setStringaEDI2(wpo.stringaEDI2);
    cons.setDecimaleEDI1(wpo.decimaleEDI1);
    cons.setDecimaleEDI2(wpo.decimaleEDI2);
  // fine fix 32086	
    return cons;
  }

  public void eliminaRigaOrdineDaWPO(WrapperPianoOrdine wpo){
    String keyRigaOrd = KeyHelper.buildObjectKey(new String[]{getIdAzienda(),
          getIdAnnoOrd(), getIdNumeroOrd(), String.valueOf(wpo.numRigaOrd)});
    OrdineVenditaRigaPrm rigaOrd = null;
    try{
      rigaOrd = (OrdineVenditaRigaPrm)OrdineVenditaRigaPrm.elementWithKey(OrdineVenditaRigaPrm.class, keyRigaOrd, PersistentObject.NO_LOCK );
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
    if (rigaOrd != null){
      try{
    	  // questa istruzione la devo mettere perchè è un cane che si morde la coda. ossia non mi cancella la riga ordine
    	  // perchè ho una riga del piano, ma io questa riga la sto cancellando
    	  // Fix 25991
    	  rigaOrd.setSaveFromPDC(true);
        int res = rigaOrd.delete();
        if (res >0)
          executeCommit();
      }catch(SQLException ex){
        ex.printStackTrace(Trace.excStream);
      }
    }
  }

  public List updatePianoCorrenteInStorico(){
    int ret = 0;
    List keyPianiDaAggiornare = new ArrayList();
    try{
      String keyRigaArticolo = getPCRigaArticolo().getKey();
      List pianiArtCorr = ricercaPianoArticoloCorrente();
      Iterator iter = pianiArtCorr.iterator();
      keyPianiDaAggiornare.add(getPCRigaArticolo().getPianoConsegneKey());
      while (iter.hasNext()){
        PianoConsegneRigaArticolo rigaArt = (PianoConsegneRigaArticolo)iter.next();
        if (rigaArt.getKey().equals(keyRigaArticolo)){
          continue;
        }
        passaPianoAStorico(rigaArt);
        ret = rigaArt.save();
        if (ret >0){
          String keyPiano = rigaArt.getPianoConsegneKey();
          if (!keyPianiDaAggiornare.contains(keyPiano))
            keyPianiDaAggiornare.add(keyPiano);
          executeCommit();
        }
        else
          setErrorPianoArticolo(rigaArt.getKey());
      }
      salvaRigaArticoloCorrente();
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
    return keyPianiDaAggiornare;
  }

  public void salvaRigaArticoloCorrente() throws SQLException{
    int rc = 0;
    PianoConsegneRigaArticolo rigaArt = getPCRigaArticolo();
    if (rigaArt != null){
      rc = rigaArt.save();
      if (rc >0)
        executeCommit();
      else
        setErrorPianoArticolo(rigaArt.getKey());
    }
  }

  public void setErrorPiano(String keyPiano){
    if (!errorsPianiConsegna.contains(keyPiano))
      errorsPianiConsegna.add(keyPiano);
  }

  public Map getMapErrorsConsegne(){
    return iMapErrorsConsegne;
  }


  public void setErrorPianoArticolo(String keyPianoArticolo){
    if (!errorsPianiArticolo.contains(keyPianoArticolo))
      errorsPianiArticolo.add(keyPianoArticolo);
  }


  public void updatePianoConsegne(List keyPiano) {
    try{
      PianoConsegne pc = null;
      Iterator iter = keyPiano.iterator();
      while (iter.hasNext()){
        String key = (String)iter.next();
        pc = PianoConsegne.elementWithKey(key,PersistentObject.NO_LOCK);
        if (pc != null){
          char statoPianoOld = pc.getStatoAvanzamento();
          char statoPianoNew = pc.calcolaStatoPiano();
          if (statoPianoNew != statoPianoOld){
            pc.setStatoAvanzamento(statoPianoNew);
            int retSave = pc.save();
            if (retSave >0)
              executeCommit();
            else
              setErrorPiano(pc.getKey());
          }
        }
      }
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
  }

  public void executeCommit() throws SQLException{
    if (gestoreCommit != null)
      gestoreCommit.commit(true);
  }


  public List ricercaPianoArticoloCorrente(){
    List listaPianiArt = new ArrayList();
    String annoPiano = getPCRigaArticolo().getIdAnnoPnc();
    String numeroPiano = getPCRigaArticolo().getIdNumeroPnc();

    String where = PianoConsegneRigaArticoloTM.ID_AZIENDA+"='"+getIdAzienda()+"' AND "+
      PianoConsegneRigaArticoloTM.ID_ANNO_ORD+"='"+getIdAnnoOrd()+"' AND "+
      PianoConsegneRigaArticoloTM.ID_NUMERO_ORD+"='"+getIdNumeroOrd()+"' AND "+
      PianoConsegneRigaArticoloTM.STATO_RIGA_PNC+"='"+PianoConsegne.ST_PIANO_CORRENTE+"'";
    try{
      listaPianiArt = PianoConsegneRigaArticolo.retrieveList(where, "",false);
    }catch(Exception ex){
      ex.printStackTrace(Trace.excStream);
    }
    return listaPianiArt;
  }

  public void passaPianoAStorico(PianoConsegneRigaArticolo rigaArt){
    rigaArt.setStatoRigaPiano(PianoConsegne.ST_PIANO_STORICO);
    // fix 32227
    List righeCons =  rigaArt.getRigheConsegnaOneToMany();
    //List righeCons = rigaArt.getRigheConsegna();
    // fine fix 32227
    if (!righeCons.isEmpty())
      rigaArt.setAttivaSaveOwnedObject(true);
    Iterator iter = righeCons.iterator();
    while (iter.hasNext()){
      PianoConsegneRigaConsegna cons = (PianoConsegneRigaConsegna)iter.next();
      cons.setIdAnnoOrd(null);
      cons.setIdNumeroOrd(null);
      cons.setIdRigaOrd(null);
    }
  }
 //Fix 16440 inizio
 // private List leggiListaPianoOrdine(){
 public List leggiListaPianoOrdine(){
 //Fix 16440 fine
    List listaWPO = new ArrayList();
    Iterator iter = iMapWrapperPianoOrdine.values().iterator();
    while (iter.hasNext()){
      List l = (List)iter.next();
      Iterator it = l.iterator();
      while (it.hasNext()){
        WrapperPianoOrdine wpo = (WrapperPianoOrdine)it.next();
        listaWPO.add(wpo);
      }
    }
    return listaWPO;
  }

  public int eseguiCountRighePgm(){
    int numRighe = 0;
    Database db = ConnectionManager.getCurrentDatabase();
    String idAnnoPiano = getPCRigaArticolo().getIdAnnoPnc();
    String idNumeroPiano = getPCRigaArticolo().getIdNumeroPnc();
    Integer rigaPiano = getPCRigaArticolo().getIdRigaPnc();
    try{
      PreparedStatement ps = csCountRighePgm.getStatement();
      synchronized (ps) {
        db.setString(ps,1,getIdAzienda());
        db.setString(ps,2,idAnnoPiano);
        db.setString(ps,3,idNumeroPiano);
        ps.setInt(4, rigaPiano.intValue());
      }
      ResultSet rs = csCountRighePgm.executeQuery();
      if (rs.next()){
        numRighe = rs.getInt(1);
      }
      rs.close();
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
    return  numRighe;
  }

  public int eseguiCountRigheOrdine(){
    int numRigheOrd = 0;
    Database db = ConnectionManager.getCurrentDatabase();
    try{
      PreparedStatement ps = csCountRigheOrd.getStatement();
      synchronized (ps) {
        db.setString(ps,1,getIdAzienda());
        db.setString(ps,2,getIdAnnoOrd());
        db.setString(ps,3,getIdNumeroOrd());
        ps.setDate(4,getDataInizioPgm());
      }
      ResultSet rs = csCountRigheOrd.executeQuery();
      if (rs.next()){
        numRigheOrd = rs.getInt(1);
      }
      rs.close();
    }catch(SQLException ex){
      ex.printStackTrace(Trace.excStream);
    }
    return numRigheOrd;
  }

  private String booleanToString(boolean bool){
    return bool ? "true" : "false";
  }

  // Inizio 6833
  public ContrattoVendita getContrattoVendita(){
    return iContrattoVendita;
  }
  // Fine 6833

  // Inizio 8617
  public java.sql.Date getDataUltimoDDT(){
    return iDataUltimoDDT;
  }

  public BigDecimal getQtaUltimoDDT(){
    return iQtaUltimoDDT;
  }

  public List getErrorsRigheCons(){
  	return iErrorsRigheCons;
  }

  public void calcolaQuantitaSpeditaDDTNonRegistrata() throws SQLException{
    if (getDataUltimoDDT() != null){
      Database db = ConnectionManager.getCurrentDatabase();
      iQtaDDTSpedTotNoReg = ZERO_DEC;
      String sqlEstrazioneDDT = "SELECT * FROM "+DocumentoVenRigaPrmTM.TABLE_NAME+
        " WHERE "+
        DocumentoVenRigaPrmTM.COL_MAGAZZINO+"='2' AND "+
        DocumentoVenRigaPrmTM.ID_AZIENDA+"='"+getIdAzienda()+"'  AND "+
        DocumentoVenRigaPrmTM.R_ANNO_ORD+"='"+getIdAnnoOrd()+"' AND "+
        DocumentoVenRigaPrmTM.R_NUMERO_ORD+"='"+getIdNumeroOrd()+"' AND ("+
        DocumentoVenRigaPrmTM.DATA_BOLLA+">"+db.getLiteral(getDataUltimoDDT());
      // Inizio 10332
      /*
      if (iNumeroUltDDT != null){
        sqlEstrazioneDDT += " OR ("+DocumentoVenRigaPrmTM.DATA_BOLLA+"="+db.getLiteral(getDataUltimoDDT());
        sqlEstrazioneDDT += " AND "+DocumentoVenRigaPrmTM.NUMERO_BOLLA+">='"+iNumeroUltDDT+"')";
      }
      */
      // Fine 10332
      sqlEstrazioneDDT +=" )";
      iCsEstrazioneDDT = new CachedStatement(sqlEstrazioneDDT);
      ResultSet rs = iCsEstrazioneDDT.executeQuery();
      while (rs.next()){
        iQtaDDTSpedTotNoReg = iQtaDDTSpedTotNoReg.add(rs.getBigDecimal(DocumentoVenRigaPrmTM.QTA_SPE_UM_VEN));
      }
      rs.close();
    }
  }
  // Fine 8617

  //Fix 16440 inizio
  public BigDecimal getQtaDDTSpedTotNoReg() {
    return iQtaDDTSpedTotNoReg;
  }  
  //Fix 16440 fine

  /**
   *
   * @author NICODEMO
   *
   */
  /*
  public class WrapperPianoOrdine{

    public static final char AZ_NESSUNA    = '-';
    // Creazione riga ordine da consegna
    public static final char AZ_ORD_INSERT = 'I';
    // Aggiornamento riga ordine da consegna
    public static final char AZ_ORD_UPDATE = 'U';
    // Cancellazione riga ordine
    public static final char AZ_ORD_DELETE = 'D';
    // Creazione consegna da riga ordine
    public static final char AZ_PGM_MERGE  = 'M';
    
    public static final char AZ_SALDA_RIGAORD  = 'S'; // Fix 23433

    protected java.sql.Date dataRic;
    // Inizio 6462
    protected java.sql.Date dataConf;
    // Fine 6462
    protected BigDecimal qtaPgm;
    protected BigDecimal qtaSpedita = ZERO_DEC;
    protected Integer numRigaOrd;
    protected boolean isRigaOrd;
    protected boolean isRigaPgm;
    protected Integer numCons;
    protected char statoAvOrdine = StatoAvanzamento.DEFINITIVO;
    protected char statoConfPrev = PianoConsegneRigaConsegna.STATO_CNFPRV_CONFERMATO;
    protected char azioneOrd = AZ_NESSUNA;
    protected char azionePgm = AZ_NESSUNA;
    protected char azione = AZ_NESSUNA;
    protected String rifCommessaCli; // Fix 7467
    // Inizio 8617
    protected BigDecimal qtaPrpEvaOrd = ZERO_DEC;
    protected BigDecimal qtaAtsEvaOrd = ZERO_DEC;
    // Fine 8617
    // Inizio 8743
    protected BigDecimal qtaPgmRigaOrd;
    protected char statoRigaPgm = DatiComuniEstesi.VALIDO;
    // Fine 8743
    protected String stringaRisUte1;  // fix 30487


    public WrapperPianoOrdine(){

    }

    public WrapperPianoOrdine(PianoConsegneRigaConsegna rigaCons){
      dataRic = rigaCons.getDataConsegRcs();
      dataConf = rigaCons.getDataConsegCfm();
      qtaPgm = rigaCons.getQtaInUMVen();
      qtaPgmRigaOrd = rigaCons.getQtaInUMVen(); // 8743
      numCons = rigaCons.getIdDetRigaPnc();
      isRigaOrd = false;
      isRigaPgm = true;
      azioneOrd = AZ_ORD_INSERT;
      azione = AZ_ORD_INSERT;
      qtaSpedita = rigaCons.getQtaSpedita();// fix 16440

      if (rigaCons.getStatoCnfPrv() != PianoConsegneRigaConsegna.STATO_CNFPRV_CONFERMATO){
        statoAvOrdine = StatoAvanzamento.PROVVISORIO;
        statoConfPrev = rigaCons.getStatoCnfPrv();
      }
      if (rigaCons.getIdRigaOrd() != null){
        numRigaOrd = rigaCons.getIdRigaOrd();
        isRigaOrd = true;
        azioneOrd = AZ_ORD_UPDATE;
        azione = AZ_ORD_UPDATE;
      }
      rifCommessaCli = rigaCons.getRifCommessaCli(); // Fix 7467
      this.stringaRisUte1 = rigaCons.getStringaRisUte1(); // fix 30487  
    }

    public WrapperPianoOrdine(OrdineVenditaRigaPrm rigaOrd){
      dataRic = rigaOrd.getDataConsegnaRichiesta();
      dataConf = rigaOrd.getDataConsegnaConfermata(); // 8743
      numRigaOrd = rigaOrd.getNumeroRigaDocumento();
      qtaPgm = rigaOrd.getQtaInUMRif();
      qtaPgmRigaOrd = rigaOrd.getQtaInUMRif();// 8743
      isRigaOrd = true;
      isRigaPgm = false;
      azionePgm = AZ_PGM_MERGE;
      azione = AZ_PGM_MERGE;
      // Inizio 8815
      statoAvOrdine = rigaOrd.getStatoAvanzamento();
      if (rigaOrd.getStatoAvanzamento() != StatoAvanzamento.DEFINITIVO)
        statoConfPrev = PianoConsegneRigaConsegna.STATO_CNFPRV_NON_APPLICABILE;
      if (statoAvOrdine == StatoAvanzamento.DEFINITIVO)
        statoConfPrev = PianoConsegneRigaConsegna.STATO_CNFPRV_CONFERMATO;
      else
        statoConfPrev = PianoConsegneRigaConsegna.STATO_CNFPRV_PREV_1;
      // Fine 8815


      // Inizio 8617
      qtaSpedita = rigaOrd.getQuantitaSpedita().getQuantitaInUMRif();
      qtaPrpEvaOrd = rigaOrd.getQtaPropostaEvasione().getQuantitaInUMRif();
      qtaAtsEvaOrd = rigaOrd.getQtaAttesaEvasione().getQuantitaInUMRif();
      // Fine 8617
      statoRigaPgm = rigaOrd.getDatiComuniEstesi().getStato();// Fix 8743
    }

    //Fix 16440 inizio
    public java.sql.Date getDataRic() {
      return dataRic;
    }

    public void setDataRic(java.sql.Date dataRic) {
      this.dataRic = dataRic;
    }

    public java.sql.Date getDataConf() {
      return dataConf;
    }

    public void setDataConf(java.sql.Date dataConf) {
      this.dataConf = dataConf;
    }

    public BigDecimal getQtaPgm() {
      return qtaPgm;
    }

    public void setQtaPgm(BigDecimal qtaPgm) {
      this.qtaPgm = qtaPgm;
    }

    public BigDecimal getQtaSpedita() {
      return qtaSpedita;
    }

    public void setQtaSpedita(BigDecimal qtaSpedita) {
      this.qtaSpedita = qtaSpedita;
    }

    public Integer getNumRigaOrd() {
      return numRigaOrd;
    }

    public void setNumRigaOrd(Integer numRigaOrd) {
      this.numRigaOrd = numRigaOrd;
    }

    public boolean isRigaOrd() {
      return isRigaOrd;
    }

    public void setRigaOrd(boolean isRigaOrd) {
      this.isRigaOrd = isRigaOrd;
    }

    public boolean isRigaPgm() {
      return isRigaPgm;
    }

    public void setRigaPgm(boolean isRigaPgm) {
      this.isRigaPgm = isRigaPgm;
    }

    public Integer getNumCons() {
      return numCons;
    }

    public void setNumCons(Integer numCons) {
      this.numCons = numCons;
    }

    public char getStatoAvOrdine() {
      return statoAvOrdine;
    }

    public void setStatoAvOrdine(char statoAvOrdine) {
      this.statoAvOrdine = statoAvOrdine;
    }

    public char getStatoConfPrev() {
      return statoConfPrev;
    }

    public void setStatoConfPrev(char statoConfPrev) {
      this.statoConfPrev = statoConfPrev;
    }

    public char getAzioneOrd() {
      return azioneOrd;
    }

    public void setAzioneOrd(char azioneOrd) {
      this.azioneOrd = azioneOrd;
    }

    public char getAzionePgm() {
      return azionePgm;
    }

    public void setAzionePgm(char azionePgm) {
      this.azionePgm = azionePgm;
    }

    public char getAzione() {
      return azione;
    }

    public void setAzione(char azione) {
      this.azione = azione;
    }

    public String getRifCommessaCli() {
      return rifCommessaCli;
    }

    public void setRifCommessaCli(String rifCommessaCli) {
      this.rifCommessaCli = rifCommessaCli;
    }

    public BigDecimal getQtaPrpEvaOrd() {
      return qtaPrpEvaOrd;
    }

    public void setQtaPrpEvaOrd(BigDecimal qtaPrpEvaOrd) {
      this.qtaPrpEvaOrd = qtaPrpEvaOrd;
    }

    public BigDecimal getQtaAtsEvaOrd() {
      return qtaAtsEvaOrd;
    }

    public void setQtaAtsEvaOrd(BigDecimal qtaAtsEvaOrd) {
      this.qtaAtsEvaOrd = qtaAtsEvaOrd;
    }

    public BigDecimal getQtaPgmRigaOrd() {
      return qtaPgmRigaOrd;
    }

    public void setQtaPgmRigaOrd(BigDecimal qtaPgmRigaOrd) {
      this.qtaPgmRigaOrd = qtaPgmRigaOrd;
    }

    public char getStatoRigaPgm() {
      return statoRigaPgm;
    }

    public void setStatoRigaPgm(char statoRigaPgm) {
      this.statoRigaPgm = statoRigaPgm;
    }
    //Fix 16440 fine
    
    // fix 30487
    public String getStringaRisUte1() {
        return this.stringaRisUte1;
      }

      public void setStringaRisUte1(String stringaRisUte1) {
        this.stringaRisUte1 = stringaRisUte1;
      }
    
    // fine fix 30487

    public void printWrapper(){
      Trace.println(Trace.XML, Utils.format(dataRic, 10)+Utils.format(qtaPgm, 10)+ Utils.format(numRigaOrd,10)+Utils.format(booleanToString(isRigaOrd) , 10)+ Utils.format(booleanToString(isRigaPgm), 10)+ Utils.format(String.valueOf(azione), 10)+Utils.format("", 10));
    }
  }
  */  

  // Inizio 23433
  public boolean isAttivaGestioneSaldaRigheOrd(){
  	return PersDatiVen.getCurrentPersDatiVen().getPianiConsSaldaRigheOrd();
  }
  // Fine 23433





  public static void main(String[] args) {
    try{
      Security.setCurrentDatabase("loc20s",null);
      Security.openDefaultConnection();
      try{
        // Inizio connessione Panthera
        Utente utente = Utente.elementWithKey("ADMIN", PersistentObject.NO_LOCK);
        String key = KeyHelper.buildObjectKey(new String[]{"001", "ADMIN"});
        UtenteAzienda utAzienda = UtenteAzienda.elementWithKey(key, PersistentObject.NO_LOCK);
        User us = User.elementWithKey("ADMIN_001", PersistentObject.NO_LOCK);
        Security.updateSessionInfo(us);
        Security.openSession(utAzienda.getIdUtenteTherm(), "ADMIN1");
        // Fine connessione Panthera

        String idAzienda = "001";
        String idAnnoDoc = "2006";
        String idNumeroDoc = "    000003";
        Integer idRiga = new Integer(1);
        String keyRigaArticolo = KeyHelper.buildObjectKey(new String[]{idAzienda, idAnnoDoc, idNumeroDoc, String.valueOf(idRiga)});
        PianoConsegneRigaArticolo pcArt = null;
        try{
          pcArt = PianoConsegneRigaArticolo.elementWithKey(keyRigaArticolo, PersistentObject.NO_LOCK);
        }catch(SQLException ex){
          ex.printStackTrace();
        }
        if (pcArt == null){
          System.out.println("Errore");
          return;
        }

        GestoreWrapperPianoOrdine  test = (GestoreWrapperPianoOrdine)Factory.createObject(GestoreWrapperPianoOrdine.class);
        test.initialize(pcArt, true);
        test.caricaWrapperPianoOrdine();
        test.printRigheWrapperPianoOrdine();
        test.runFaseA();
        test.printRigheWrapperPianoOrdine();
        test.runFaseB();
        test.printRigheWrapperPianoOrdine();

        //ConnectionManager.commit();
      }catch(Throwable ex){
        ex.printStackTrace(Trace.excStream);
      }
    }
    catch(Exception exp){
      exp.printStackTrace();
    }
    Security.closeDefaultConnection();
  }

}
