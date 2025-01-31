package it.valvorobica.thip.logis.rf.gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.ErrorCodes;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.rf.driver.Driver;
import com.thera.thermfw.rf.driver.FormCreator;
import com.thera.thermfw.rf.tc.TForm;
import com.thera.thermfw.rf.tc.TList;

import it.softre.thip.base.firmadigitale.AssociazioneTipoDocFirma;
import it.softre.thip.base.firmadigitale.DocumentiAttesaFirma;
import it.softre.thip.base.firmadigitale.InvioDocumentiUtils;
import it.softre.thip.base.firmadigitale.PsnDatiFirmaDigitale;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.documentoDgt.DocumentoDigitale;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.logis.rf.gui.CaricoCamionRF;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.valvorobica.thip.logis.bas.YCostantiValvo;
import it.valvorobica.thip.logis.fis.YCaricoCamion;

/**
 * 
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Thomas Brescianini 20/12/2024 <br>
 *         <br>
 *         <b>71752 TBSOF3 20/12/2024</b>
 *         <p>
 *         Funzione carico camion UDS prima stesura
 *         </p>
 *         <b>71787	DSSOF3	20/01/2025</b>
 *         <p>
 *         		<list>
 *         			<li>Ottimizzazione drastica della funzione</li>
 *         			<li>Una volta terminato il carico per una lista, invio il documento al tablet per essere firmato</li>
 *         		</list>
 *         </p>
 *         <b>71805	DSSOF3	30/01/2025</b>
 *         <p>
 *         Firma digitale - aggiunta check su documento escluso da vettore.<br>
 *         </p>
 */

public class YCaricoCamionUDS extends CaricoCamionRF{
	
	protected static final String RESOURCES = "it.valvorobica.thip.logis.rf.resources.LogisRf";

	public YCaricoCamionUDS(Driver drv, String xml) {
		super(drv, xml);
		try {
			FormCreator.print(YCostantiValvo.getFileXmlValvo(), this);
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	protected static final int CONFERMA_UDS = 3;

	protected String codiceUds = null;

	public ErrorMessage esegui() {
		ErrorMessage err = null;
		boolean interrompi = false;
		pagina = PRESENTAZIONE;
		while (!interrompi) {
			switch (pagina) {
			case MENU:
				interrompi = true;
				break;
			case PRESENTAZIONE:
				paginaPresentazione();
				break;
			case SELEZIONE:
				paginaInserimentoUds();
				break;
			case CONFERMA_UDS:
				paginaConfermaUds();
				break;
			default:
				try {
					messaggio(true, ResourceLoader.getString(RESOURCES,"msg0001"));
					interrompi = true;
				} catch (Exception e) {
					e.printStackTrace(Trace.excStream);
					interrompi = true;
				}
				break;
			}
		}
		return err;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void paginaInserimentoUds() {
		String errore = null;
		codiceUds = null;
		TForm form = getTForm("YPaginaUdsCaricoCamion"); 
		try {
			form.getTField("CodiceUdsLbl").setValue("UDS:");
			form.getTField("CodiceUdsField").setValue("");
			sendForm(form);
			TForm risposta = readInput();
			if (risposta.getKeyPressed().equals(TForm.KEY_ESC) ||
					risposta.getKeyPressed().equals(TForm.KEY_CTL_X)) {
				pagina = MENU;
				return;
			}
			if (risposta.getKeyPressed().equals(TForm.KEY_F5)) {
				pagina = PRESENTAZIONE;
				return;
			}
			if (risposta.getKeyPressed().equals(TForm.KEY_F2) ||
					risposta.getKeyPressed().equals(TForm.KEY_F3) ||
					risposta.getKeyPressed().equals(TForm.KEY_F4) ||
					risposta.getKeyPressed().equals(TForm.KEY_F6) ||
					risposta.getKeyPressed().equals(TForm.KEY_F7) ||
					risposta.getKeyPressed().equals(TForm.KEY_F8) ||
					risposta.getKeyPressed().equals(TForm.KEY_F9) ||
					risposta.getKeyPressed().equals(TForm.KEY_F10))
				return;
			if(risposta.getKeyPressed().equals(TForm.KEY_F1) ) {
				codiceUds = form.getTField("CodiceUdsField").getValue();
				if (codiceUds != null &&
						!codiceUds.equals("")) {
					TestataUds uds = (TestataUds) TestataUds.elementWithKey(TestataUds.class, codiceUds, PersistentObject.NO_LOCK);
					if (uds != null) {
						Vector piani = caricaElencoPianiSingolaLista(uds.getCodiceTestataLista());
						if(piani.contains(uds.getTestataLista())) {
							esecuzioneSpedizioni.getElencoTestate().removeAllElements();//uso questo per passarmi la lista nella schermata dopo, almeno evito variabili globali inutili
							esecuzioneSpedizioni.getElencoTestate().addElement(uds.getTestataLista());
							uds.setFlagSpedito(true);
							for (int z = 0; z < uds.getElencoUdSContenute().size(); z++)	//devo aggiurnare anche quelle contenute. Codice copiato da standard per comodità
								((TestataUds) uds.getElencoUdSContenute().elementAt(z)).setFlagSpedito(true);
							int res = ErrorCodes.NO_ROWS_UPDATED;
							res = uds.save();
							for (int z = 0; z < uds.getElencoUdSContenute().size() && res >= ErrorCodes.NO_ROWS_UPDATED; z++) {
								int resTmp = ((TestataUds) uds.getElencoUdSContenute().elementAt(z)).save();
								if (resTmp < ErrorCodes.NO_ROWS_UPDATED)
									res = resTmp;
								else
									res += resTmp;
							}
							if(res > ErrorCodes.NO_ROWS_UPDATED) {
								Vector elencoUdsLista = TestataLista.getElencoUds(uds.getTestataLista());
								elencoUdsLista.remove(uds.getKey()); //rimuovo quella gia' flaggata
								boolean tuttePrese = true;
								for (Iterator iterator = elencoUdsLista.iterator(); iterator.hasNext();) {
									String keyUds = (String) iterator.next();
									TestataUds udsTes = (TestataUds) TestataUds.elementWithKey(TestataUds.class, keyUds, 0);
									if(!udsTes.getFlagSpedito()) {
										tuttePrese = false;
									}
								}

								if(tuttePrese) {
									//Allora chiedo se vuole mandare l'eventuale ddt al tablet
									DocumentoVendita dv = documentoVenditaTestataLista(uds.getTestataLista());
									if(dv != null) {
										PsnDatiFirmaDigitale persDatiFirma = PsnDatiFirmaDigitale.getCurrentPersDati();
										if(persDatiFirma.getAbilitata() == '1' && !persDatiFirma.isDocumentoVenditaEsclusoFirma(dv)) {
											Vector docs = InvioDocumentiUtils.getDocumentiDigitaliCollegatiDV(dv, "DocumentoVendita"); //e' presente un doc collgato?
											if(docs.size() > 0) {
												//chiedo conferma all'operatore se vuole inviare
												boolean inviaDocumentoDevice = conferma(false, ResourceLoader.getString(RESOURCES,"msgFirmaDDT"));
												if(inviaDocumentoDevice) {
													DocumentiAttesaFirma attesaFirma = documentoAttesaFirma(dv, (DocumentoDigitale) docs.get(0));
													if(attesaFirma != null)
														res = attesaFirma.save();
												}
											}else {
												messaggio(false, "Non e' stato possibile spedire il DDT al tablet perche' il documento digitale non esiste");
											}
										}
									}
								}
							}
							if (res <= ErrorCodes.NO_ROWS_UPDATED) {
								ConnectionManager.rollback();
								errore = "Errore durante il salvataggio della UDS: " + uds.getCodice();
							} else {
								ConnectionManager.commit();
							}
							if(errore != null) {
								messaggio(true, errore);
								pagina = SELEZIONE;
							}
							pagina = CONFERMA_UDS;
							return;
						}else {
							errore = "Per l'UDS selezionata non ci sono piani di carico da effettuare";
						}
					}else {
						errore = "Il codice UDS inserito non è valido";
					}
				}
			}
			if(errore != null) {
				messaggio(true, errore);
				pagina = SELEZIONE;
				return;
			}
		} catch(Exception e) {
			e.printStackTrace(Trace.excStream);
			pagina = MENU;
		}
		return;
	}

	public static DocumentiAttesaFirma documentoAttesaFirma(DocumentoVendita dv, DocumentoDigitale docDgt) {
		DocumentiAttesaFirma attesaFirma = (DocumentiAttesaFirma) Factory.createObject(DocumentiAttesaFirma.class);
		attesaFirma.setIdAzienda(Azienda.getAziendaCorrente());
		attesaFirma.setIdDevice(ParametroPsn.getValoreParametroPsn("YCaricoCamionRf", "IdDeviceFirmaDigitale",Azienda.getAziendaCorrente()));
		String idTipoDoc = PsnDatiFirmaDigitale.getIdTipoDocumentoDigitale(dv.getCausale().getTipoDocumento(), true);
		AssociazioneTipoDocFirma associazione = PsnDatiFirmaDigitale.recuperaAssociazioneTipoDocumento(idTipoDoc, 
				dv.getCausale().getTipoDocumento(), true);
		if(associazione != null) {
			attesaFirma.setCopyNumber(associazione.getCopyNumber());
		}
		attesaFirma.setIdDocumentoDgt(docDgt.getIdDocumentoDgt());
		attesaFirma.setRVrsDocDgt(docDgt.getIdVersione());
		if(attesaFirma.getIdDevice() == null) {
			attesaFirma = null;
		}
		return attesaFirma;
	}

	public static DocumentoVendita documentoVenditaTestataLista(TestataLista testata) {
		DocumentoVendita dv = (DocumentoVendita) Factory.createObject(DocumentoVendita.class);
		dv.setIdAzienda(testata.getCodiceSocieta());
		if (testata.getCodice().length() > 5) {
			dv.setAnnoDocumento(testata.getCodice().substring(1, 5));
			dv.setNumeroDocumento(testata.getCodice().substring(5));
		}
		else
			dv.setAnnoDocumento(testata.getCodice());
		boolean res = false;
		try {
			res = dv.retrieve(PersistentObject.NO_LOCK);
			if(!res) {
				dv = null;
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace(Trace.excStream);
		}
		return dv;
	}

	/**
	 * <h1>Softre Solutions</h1>
	 * <br>
	 * @author Daniele Signoroni 20/01/2025
	 * <br><br>
	 * <b>71787	DSSOF3	20/01/2025</b>
	 * <p></p>
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Vector caricaElencoPianiSingolaLista(String codLista) {
		((YCaricoCamion)esecuzioneSpedizioni).riempiElencoPianiSpedSingolaLista(codLista);
		return esecuzioneSpedizioni.getElencoTutteTestate();
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	private void paginaConfermaUds() {
		try {
			List<String> elencoUdsCollegate = new ArrayList<String>();
			TestataLista testata = (TestataLista) esecuzioneSpedizioni.getElencoTestate().get(0);
			List<String>elencoUds = testata.getElencoUds(testata);
			if(testata != null) {
				for (String codUds : elencoUds) {
					TestataUds tl = (TestataUds)TestataUds.elementWithKey(TestataUds.class, codUds, PersistentObject.NO_LOCK);
					if (!tl.getFlagSpedito() && !tl.getCodice().equals(codiceUds)) {
						elencoUdsCollegate.add(tl.getCodice());	//creo un elenco di uds collegate alla lista non spedite
					}
				}
			}
			//			boolean conferma = conferma(false, "Sei sicuro di voler\n caricare la " + codiceUds);//prima chiedo la conferma, poi faccio il tutto
			TForm form = getTForm("YPaginaConfermaUdsCaricoCamion"); 
			caricaUds(form, elencoUdsCollegate);
			sendForm(form);
			TForm risposta = readInput();
			if (risposta.getKeyPressed().equals(TForm.KEY_ESC) ||
					risposta.getKeyPressed().equals(TForm.KEY_CTL_X)||
					risposta.getKeyPressed().equals(TForm.KEY_F3) ||
					risposta.getKeyPressed().equals(TForm.KEY_F4) ||
					risposta.getKeyPressed().equals(TForm.KEY_F5) ||
					risposta.getKeyPressed().equals(TForm.KEY_F6) ||
					risposta.getKeyPressed().equals(TForm.KEY_F7) ||
					risposta.getKeyPressed().equals(TForm.KEY_F9) ||
					risposta.getKeyPressed().equals(TForm.KEY_F10))
				return;
			if(risposta.getKeyPressed().equals(TForm.KEY_F1) ) {
				pagina = SELEZIONE;
			}
		} catch(Exception e) {
			e.printStackTrace(Trace.excStream);
			pagina = MENU;
		}
		return;
	}

	protected void caricaUds(TForm form, List<String> lista) {
		TList list = form.getTList("ElencoUdsCollegate");
		list.resetSelected();
		list.getItems().clear();
		list.addItem("UDS mancanti");
		if(lista.isEmpty()) {
			list.addItem("Nessuna UDS");
			list.addItem("mancante");
		}
		for (String codice : lista) {
			list.addItem(codice);
		}
		list.setCurrentSelectedItem(1);
		list.setCurrentTopItem(1);
	}
}
