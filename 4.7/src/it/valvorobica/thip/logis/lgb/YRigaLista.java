package it.valvorobica.thip.logis.lgb;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.ErrorCodes;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.generale.IntegrazioneThipLogis;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.logis.fis.Missione;
import it.thera.thip.logis.fis.MissioneTM;
import it.thera.thip.logis.fis.Operatore;
import it.thera.thip.logis.fis.PianificazioneLista;
import it.thera.thip.logis.fis.Postazione;
import it.thera.thip.logis.fis.Saldo;
import it.thera.thip.logis.fis.SaldoQtaAssegnata;
import it.thera.thip.logis.fis.SaldoTM;
import it.thera.thip.logis.lgb.RigaLista;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.valvorobica.thip.base.articolo.YMacroFamiglia;
import it.valvorobica.thip.logis.bas.YCostantiValvo;
import it.valvorobica.thip.logis.fis.YMissione;
import it.valvorobica.thip.magazzino.generalemag.YPersDatiMagazzino;

/**
 * 
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Thomas Brescianini 26/03/2024 <br>
 *         <br>
 *         <b>70963 21/02/2023 TBSOF3</b>
 *         <p>
 *         Gestione magazzino turbo prima stesura
 *         </p>
 *         <b>70975 01/03/2023 TBSOF3</b>
 *         <p>
 *         Ommissione missioni tubing
 *         </p>
 *         <b>71481 26/03/2024 TBSOF3</b>
 *         <p>
 *         Modificata completamente la generazione delle missioni standard
 *         </p>
 */

public class YRigaLista extends RigaLista {

	private static final String STMT_SALDO_DIFF_ART_SAME_UDC = "SELECT "
			+ "	* "
			+ "FROM "
			+ "	LOGIS.LSALDO "
			+ "WHERE "
			+ "	COD_ARTICOLO <> ? "
			+ "	AND COD_SOCIETA = ? "
			+ "	AND COD_MAG_LOGICO = ? "
			+ "	AND COD_MAG_FISICO = ? "
			+ "	AND COD_MAPPA_UDC = ? AND COD_UBICAZIONE = ? "
			+ "";
	public static CachedStatement cSaldoDiversoArticoloStessaUDC = new CachedStatement(STMT_SALDO_DIFF_ART_SAME_UDC);

	private static final String STMT_SALDO_PIU_MAGAZ_STESSA_UBIC = "SELECT "
			+ "	* "
			+ "FROM "
			+ "	LOGIS.LSALDO "
			+ "WHERE "
			+ "	COD_SOCIETA = ? "
			+ "	AND COD_MAG_LOGICO IN (?, ?) "
			+ "	AND COD_MAG_FISICO = ? "
			+ "	AND COD_UBICAZIONE = ? ";
	public static CachedStatement cEsistonoSaldiPiuMagazziniStessaUbicazione = new CachedStatement(STMT_SALDO_PIU_MAGAZ_STESSA_UBIC);

	protected int numeroMissioni = 0;

	@SuppressWarnings("rawtypes")
	@Override
	protected Vector settaAttributiMissione(Missione m) {
		Vector errors = super.settaAttributiMissione(m);
		if(errors.isEmpty() && m instanceof YMissione) {
			if(getCodiceTestataLista().startsWith(IntegrazioneThipLogis.VENDITA+"")) {
				((YMissione)m).setDocumentoVenditaRigaKey(KeyHelper.buildObjectKey(new String[] {
						getCodiceSocieta(),getCodiceTestataLista().substring(1, 5),getCodiceTestataLista().substring(5),getNumeroRigaHost().toString()
				}));
			}
			if(getCodiceTestataLista().startsWith(IntegrazioneThipLogis.ACQUISTO+"")) {
				((YMissione)m).setDocumentoAcquistoRigaKey(KeyHelper.buildObjectKey(new String[] {
						getCodiceSocieta(),getCodiceTestataLista().substring(1, 5),getCodiceTestataLista().substring(5),getNumeroRigaHost().toString()
				}));
			}
		}
		return errors;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public Vector generaMissioniSuElencoSaldi(Vector eS, PianificazioneLista pl, Postazione pt, Operatore op) {
		Vector errori = new Vector(); // Elenco degli errori
		int numMis = 0; // Numero di missioni create.
		elencoMissioni.removeAllElements();
		boolean checkMissioni = checkMissioniOmesse(); // 70975
		if (checkMissioni) {
			YPersDatiMagazzino persDatimagaz = (YPersDatiMagazzino) YPersDatiMagazzino.getCurrentPersDatiMagazzino();
			if (persDatimagaz.getGestioneMagazzinoTurbo() == 'Y') {
				errori = generazioneMissioniPersonalizzata(eS, pl, pt, op);
				numMis = numeroMissioni;
				numeroMissioni = 0;
			} else if (persDatimagaz.getGestioneMagazzinoTurbo() == 'S') {
				try {
					String parametroPers = ParametroPsn.getValoreParametroPsn("YGestioneMagazzinoTurbo",
							"Gestione magazzino turbo");
					Integer param = Integer.valueOf(parametroPers);
					String select = "SELECT DAY('" + TimeUtils.getCurrentTimestamp() + "')";
					CachedStatement cs = new CachedStatement(select);
					ResultSet rs = cs.executeQuery();
					if (rs.next()) {
						Integer i = rs.getInt(1);
						errori = generazioneMissioniPersonalizzata(eS, pl, pt, op);
						numMis = numeroMissioni;
						numeroMissioni = 0;
					} else {
						generazioneMissioniStandard(eS, pl, pt, op);
						numMis = numeroMissioni;

					}
					cs.free();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				generazioneMissioniStandard(eS, pl, pt, op);
				numMis = numeroMissioni;
			}
			try {
				ConnectionManager.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (errori.size() == 0) // Se non ci sono errori, aggiorna livello estrazione sulla RL.
				if (pl == null && aggiornaQtaEstrazioneRL() < ErrorCodes.NO_ROWS_UPDATED) { // Errore su save().
					ErrorMessage err = new ErrorMessage("LOGIST0582",
							new String[] { KeyHelper.formatKeyString(getKey()) });
					errori.addElement(err);
					return errori;
				}
			errori.addElement(numMis + "");
		}
		return errori; // Ritorna l'esito della generazione e dell'aggiornamento quantitÃ .
	}

	protected BigDecimal getQtaTot(Vector<SaldoQtaAssegnata> eS) {
		BigDecimal qta = BigDecimal.ZERO;
		for (int i = 0; i < eS.size(); i++) {
			SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) eS.elementAt(i);
			qta = qta.add(sqa.getQta());
		}
		return qta;
	}

	@SuppressWarnings("unused")
	protected Saldo getSaldoOttimale() {
		Saldo s = null;
		try {
			String select = "SELECT * FROM SOFTRE.Y_UBICAZ_PREL_V01 WHERE " + "COD_SOCIETA = '"
					+ Azienda.getAziendaCorrente() + "' AND " + "COD_LISTA = '" + this.getCodiceTestataLista() + "' "
					+ "AND QTA1_DISP > 0 AND CODICE = '" + this.getCodice() + "'";
			CachedStatement cs = new CachedStatement(select);
			ResultSet rs = cs.executeQuery();
			String saldo = "";
			BigDecimal qtaDisp = BigDecimal.ZERO;
			if (rs.next()) {
				saldo = rs.getString("COD_SALDO").trim();
				qtaDisp = rs.getBigDecimal("QTA1_DISP");
			}
			cs.free();
			String key = KeyHelper.buildObjectKey(new String[] { Azienda.getAziendaCorrente(), saldo });
			s = (Saldo) Saldo.elementWithKey(Saldo.class, key, 0);
			if (s != null) {
				BigDecimal disp = s.calcolaDisponibile();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Vector generazioneMissioniPersonalizzata(Vector eS, PianificazioneLista pl, Postazione pt, Operatore op) {
		Vector errori = new Vector();
		BigDecimal qtaTot = getQtaTot(eS);
		Saldo sal = getSaldoOttimale();
		if (sal != null && sal.getQtaDisponibile().compareTo(qtaTot) >= 0) {
			errori = generaMissione(sal, qtaTot, pl, pt, op, qtaTot, BigDecimal.ZERO);
			if (errori.size() == 0) { // Studia l'esito della creazione ...
				numeroMissioni++; // Incrementa il numero di missioni create.
			}

		} else if (sal != null && sal.getQtaDisponibile().compareTo(BigDecimal.ZERO) > 0) {
			errori = generaMissione(sal, sal.getQtaDisponibile(), pl, pt, op, sal.getQtaDisponibile(), BigDecimal.ZERO);
			for (int i = 0; i < eS.size(); i++) {
				SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) eS.elementAt(i);
				if (sqa.getQta().compareTo(new BigDecimal(0)) > 0) { // Se la qta Ã¨ != 0 allora crea missione.
					errori = generaMissione(sqa.getSaldo(), sqa.getQta(), pl, pt, op, sqa.getQta2(), sqa.getQta3());
					if (errori.size() != 0) { // Studia l'esito della creazione ...
						if (errori.elementAt(0) instanceof ErrorMessage) // ... errore.
							break;
					} else {
						numeroMissioni++; // Incrementa il numero di missioni create.
					}
				}
			}
		} else {
			for (int i = 0; i < eS.size(); i++) {
				SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) eS.elementAt(i);
				if (sqa.getQta().compareTo(new BigDecimal(0)) > 0) { // Se la qta Ã¨ != 0 allora crea missione.
					errori = generaMissione(sqa.getSaldo(), sqa.getQta(), pl, pt, op, sqa.getQta2(), sqa.getQta3());
					if (errori.size() != 0) { // Studia l'esito della creazione ...
						if (errori.elementAt(0) instanceof ErrorMessage) // ... errore.
							break;
					} else {
						numeroMissioni++; // Incrementa il numero di missioni create.
					}
				}
			}
		}
		return errori;
	}

	/**
	 * 71481 TBSOF3 Modificata la generazione delle missioni standard. Se dovesse
	 * esser presente un saldo con qta disponibile = qtaRimanente prelievo, allora
	 * viene SEMPRE utilizzato quel saldo. Se non dovesse esserci un saldo che
	 * soddisfa la prima condizione, allora si verifica se l'articolo ha il flag
	 * "gestioneMissioni" attivo sulla macrofamiglia. Se così fosse, allora si
	 * devono definire la o le colate che potrebbero coprire per intero il saldo.
	 * Nel caso in cui nessuna colata possa coprire per intero il saldo, allora si
	 * vanno ad usare i saldi della colata che ricoprirebbero per la maggiore la qta
	 * rimanente del prelievo. Dopo aver effettuato questi controlli, se vi fosse
	 * ancora della qta da prelevare, allora si prende il saldo con qta disponibile
	 * più alta ma minore di quella richiesta. Infine, se ci fosse ancora qta da
	 * prelevare, viene usato il saldo con qta più alta ma più bassa della qta
	 * richiesta.
	 * 
	 * @param eS
	 * @param pl
	 * @param pt
	 * @param op
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Vector generazioneMissioniStandard(Vector eS, PianificazioneLista pl, Postazione pt, Operatore op) {
		if(getTestataLista() != null && getTestataLista().getCodiceTipoLista().equals(YCostantiValvo.codTipoListaTrasferimentoFincantieri())) {
			gestisciSaldiTrasferimentoFincantieri(eS);
		}
		Vector errori = new Vector();
		elencoMissioni.clear();
		// 71481 vengono ordinate le missioni per colata e quantità
		Collections.sort(eS, new YComparatorSaldiMissioni());
		String keyArticolo = Azienda.getAziendaCorrente() + KeyHelper.KEY_SEPARATOR + getCodiceArticolo();
		Articolo articolo = null;
		try {
			articolo = (Articolo) Articolo.elementWithKey(Articolo.class, keyArticolo, PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (articolo != null) {
			// 71481 viene verificato se sulla macrofamiglia è attivo il flag
			// "GestioneMissioni"
			boolean macro = articolo.getMacroFamiglia() instanceof YMacroFamiglia
					&& ((YMacroFamiglia) articolo.getMacroFamiglia()).getGestioneMissioni();
			BigDecimal qtaRic = getQta1EstrRes(); // qta richiesta dalla lista per questo articolo
			// Viene verificato se è presente un saldo con la qta uguale a quella richiesta
			// se dovesse venir trovato, verrà creata una sola missione tutta su quel saldo
			for (Object element : eS) {
				SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) element;
				BigDecimal qtaDisponibileSaldo = sqa.getQtaDisponibileSaldo();
				if (qtaDisponibileSaldo.compareTo(qtaRic) == 0) {
					errori = generaMissione(sqa.getSaldo(), qtaRic, pl, pt, op, qtaRic, qtaRic);
					if (errori.isEmpty()) {
						qtaRic = BigDecimal.ZERO;
						numeroMissioni++;
						break;
					} else if (errori.elementAt(0) instanceof ErrorMessage) {
						break; // Se si verifica un errore, interrompi il ciclo
					}
				}
			}
			if (qtaRic.compareTo(BigDecimal.ZERO) > 0 && macro) {
				// le colate vengono ordinate per la qta totale della colata in ordine
				// crescente,m così da prendere quella con qta più bassa ma più alta della qta
				// richiesta
				List<YOggettoSaldoColata> saldiColate = getSaldiColate(eS);
				Comparator<SaldoQtaAssegnata> comp = Comparator.comparing(SaldoQtaAssegnata::getQtaDisponibileSaldo)// questo
						// ordina
						// in
						// maniera
						// decrescente
						// i
						// saldi
						// all'interno
						// della
						// colata
						.reversed();
				Comparator<YOggettoSaldoColata> comparatore = Comparator.comparing(o -> o.qtaTotale);
				Collections.sort(saldiColate, comparatore);
				for (YOggettoSaldoColata saldoColata : saldiColate) {
					if (qtaRic.compareTo(BigDecimal.ZERO) <= 0) {
						break;
					}
					if (saldoColata.qtaTotale.compareTo(qtaRic) >= 0) {
						// Collections.sort(saldoColata.saldi, comp);
						List<SaldoQtaAssegnata> listaSaldiNoACC = getListaSaldiNoACC(saldoColata.saldi);
						for (SaldoQtaAssegnata sqa : listaSaldiNoACC) {
							BigDecimal qtaSaldo = sqa.getQtaDisponibileSaldo();
							if (qtaSaldo.compareTo(BigDecimal.ZERO) > 0) {
								BigDecimal qtaUtilizzata = BigDecimal.ZERO;
								if (qtaSaldo.compareTo(qtaRic) >= 0) {
									qtaUtilizzata = qtaRic;
								} else
									qtaUtilizzata = qtaSaldo;
								errori = generaMissione(sqa.getSaldo(), qtaUtilizzata, pl, pt, op, qtaUtilizzata,
										qtaUtilizzata);
								if (errori.isEmpty()) {
									qtaRic = qtaRic.subtract(qtaUtilizzata);
									numeroMissioni++;
								} else if (errori.elementAt(0) instanceof ErrorMessage) {
									break; // Se si verifica un errore, interrompi il ciclo
								}
							}
							if (qtaRic.compareTo(BigDecimal.ZERO) <= 0) {
								break;
							}
						}
						if (qtaRic.compareTo(BigDecimal.ZERO) > 0) {
							for (SaldoQtaAssegnata sqa : saldoColata.saldi) {
								BigDecimal qtaSaldo = sqa.getQtaDisponibileSaldo();
								if (qtaSaldo.compareTo(BigDecimal.ZERO) > 0) {
									BigDecimal qtaUtilizzata = BigDecimal.ZERO;
									if (qtaSaldo.compareTo(qtaRic) >= 0) {
										qtaUtilizzata = qtaRic;
									} else
										qtaUtilizzata = qtaSaldo;
									errori = generaMissione(sqa.getSaldo(), qtaUtilizzata, pl, pt, op, qtaUtilizzata,
											qtaUtilizzata);
									if (errori.isEmpty()) {
										qtaRic = qtaRic.subtract(qtaUtilizzata);
										numeroMissioni++;
									} else if (errori.elementAt(0) instanceof ErrorMessage) {
										break; // Se si verifica un errore, interrompi il ciclo
									}
								}
								if (qtaRic.compareTo(BigDecimal.ZERO) <= 0) {
									break;
								}
							}
						}
					}
				}
				if (qtaRic.compareTo(BigDecimal.ZERO) > 0 && macro) {
					// viene ribaltata la lista per avere prima quelli con un saldo più alto (questo
					// perchè visto che nessuno copriva per intero la qta richiesta,
					// quindi uso la colata con qta maggiore
					Collections.sort(saldiColate, comparatore);
					Collections.reverse(saldiColate);
					for (YOggettoSaldoColata saldoColata : saldiColate) {
						if (qtaRic.compareTo(BigDecimal.ZERO) <= 0) {
							break;
						}
						Collections.sort(saldoColata.saldi, comp);
						for (SaldoQtaAssegnata sqa : saldoColata.saldi) {
							BigDecimal qtaSaldo = sqa.getQtaDisponibileSaldo();
							if (qtaSaldo.compareTo(BigDecimal.ZERO) > 0) {
								BigDecimal qtaUtilizzata = BigDecimal.ZERO;
								if (qtaSaldo.compareTo(qtaRic) >= 0) {
									qtaUtilizzata = qtaRic;
								} else
									qtaUtilizzata = qtaSaldo;
								errori = generaMissione(sqa.getSaldo(), qtaUtilizzata, pl, pt, op, qtaUtilizzata,
										qtaUtilizzata);
								if (errori.isEmpty()) {
									qtaRic = qtaRic.subtract(qtaUtilizzata);
									numeroMissioni++;
								} else if (errori.elementAt(0) instanceof ErrorMessage) {
									break; // Se si verifica un errore, interrompi il ciclo
								}
							}
							if (qtaRic.compareTo(BigDecimal.ZERO) <= 0) {
								break;
							}
						}
					}
				}
			}
			if (qtaRic.compareTo(BigDecimal.ZERO) > 0) {
				// se vi fosse ancora della qta per cui generare delle missioni, si prende il
				// saldo con qta maggiore ma minore di quella richiesta rimanente
				for (int i = 0; i < eS.size(); i++) {
					SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) eS.get(i);
					if (sqa.getQtaDisponibileSaldo().compareTo(qtaRic) > 0 || i == eS.size() - 1) {
						if (i == 0)
							i++;
						// essendo ordinate per qta crescente, nel caso in cui si dovesse arrivare
						// all'ultimo saldo e, la qta
						// fosse minore di quella richiesta, vorrebbe dire che il maggiore dei minori è
						// proprio quest'ultimo
						else if (i == eS.size() - 1 && sqa.getQtaDisponibileSaldo().compareTo(qtaRic) < 0)
							i++;
						sqa = (SaldoQtaAssegnata) eS.get(i - 1);
						eS.remove(i - 1);
						BigDecimal qtaSaldo = sqa.getQtaDisponibileSaldo();
						if (qtaSaldo.compareTo(BigDecimal.ZERO) > 0) {
							BigDecimal qtaUtilizzata = BigDecimal.ZERO;
							if (qtaSaldo.compareTo(qtaRic) >= 0) {
								qtaUtilizzata = qtaRic;
							} else
								qtaUtilizzata = qtaSaldo;
							errori = generaMissione(sqa.getSaldo(), qtaUtilizzata, pl, pt, op, qtaUtilizzata,
									qtaUtilizzata);
							if (errori.isEmpty()) {
								qtaRic = qtaRic.subtract(qtaUtilizzata);
								numeroMissioni++;
							} else if (errori.elementAt(0) instanceof ErrorMessage) {
								break; // Se si verifica un errore, interrompi il ciclo
							}
							if (qtaRic.compareTo(BigDecimal.ZERO) <= 0) {
								break;
							}
							i = -1;
						}
					}
				}
			}
			// infine, se non si dovessero più avere saldi minori della qta rimanente,
			// allora viene preso il saldo con qta maggiore della qta rimanente
			// ma minore dei saldi maggiori
			if (qtaRic.compareTo(BigDecimal.ZERO) > 0) {
				Comparator<SaldoQtaAssegnata> comp = Comparator.comparing(SaldoQtaAssegnata::getQtaDisponibileSaldo)
						.reversed();
				Collections.sort(eS, comp);
				for (Object element : eS) {
					SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) element;
					boolean qtaMaggiore = sqa.getQtaDisponibileSaldo().compareTo(qtaRic) > 0;
					if (qtaMaggiore) {
						errori = generaMissione(sqa.getSaldo(), qtaRic, pl, pt, op, qtaRic, qtaRic);
						if (errori.isEmpty()) {
							numeroMissioni++;
							if (qtaMaggiore)
								break; // Se la quantità è uguale, non è necessario continuare il ciclo
						} else if (errori.elementAt(0) instanceof ErrorMessage) {
							break; // Se si verifica un errore, interrompi il ciclo
						}
					}
				}
			}
		}
		return errori;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void gestisciSaldiTrasferimentoFincantieri(Vector eS) {
		//..Rimuovo i saldi che hanno UDC contententi piu' articoli
		for (Iterator<SaldoQtaAssegnata> it = eS.iterator(); it.hasNext();) {
			SaldoQtaAssegnata sqa = it.next();
			if (sqa.getSaldo() != null && sqa.getSaldo().getCodiceMappaUdc() != null) {
				Saldo altroSaldo = trovaSaldoStessaUdcAltroArticolo(sqa.getSaldo());
				if (altroSaldo != null) it.remove();
			}
		}
		//..Ordino i saldi dando priorità ad ubicazioni gia contenenti merce del medesimo magazzino di quello di arrivo del documento
		DocumentoVendita dv = YCostantiValvo.documentoVenditaTestataLista(getTestataLista(), PersistentObject.NO_LOCK);
		if (dv != null && dv.getIdMagazzinoTra() != null) {
			String idMagazzinoTra = dv.getIdMagazzinoTra();
			Map<SaldoQtaAssegnata, Boolean> prioritaMap = new HashMap<>();

			for (Object obj : eS) {
				SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) obj;

				boolean priorita = false;
				if (sqa.getSaldo() != null) {
					priorita = esistonoSaldiPiuMagazzini(sqa.getSaldo(), idMagazzinoTra);
				}

				prioritaMap.put(sqa, priorita);
			}
			eS.sort((o1, o2) -> Boolean.compare(
					prioritaMap.get(o2),
					prioritaMap.get(o1)
					));
		}
	}

	public boolean esistonoSaldiPiuMagazzini(Saldo saldoPartenza, String idMagazzinoExtra) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Database db = ConnectionManager.getCurrentDatabase();
			ps = cEsistonoSaldiPiuMagazziniStessaUbicazione.getStatement();
			db.setString(ps, 1, getCodiceSocieta());
			db.setString(ps, 2, saldoPartenza.getCodiceMagLogico());
			db.setString(ps, 3, idMagazzinoExtra);
			db.setString(ps, 4, saldoPartenza.getCodiceMagFisico());
			db.setString(ps, 5, saldoPartenza.getCodiceUbicazione());
			rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return false;
	}

	public Saldo trovaSaldoStessaUdcAltroArticolo(Saldo saldoPartenza) {
		Saldo sal = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Database db = ConnectionManager.getCurrentDatabase();
			ps = cSaldoDiversoArticoloStessaUDC.getStatement();
			db.setString(ps, 1, getCodiceArticolo());
			db.setString(ps, 2, getCodiceSocieta());
			db.setString(ps, 3, getCodiceMagLogico());
			db.setString(ps, 4, getCodiceMagFisico());
			db.setString(ps, 5, saldoPartenza.getCodiceMappaUdc());
			db.setString(ps, 6, saldoPartenza.getCodiceUbicazione());
			rs = ps.executeQuery();
			if(rs.next()) {
				sal = Saldo.elementWithKey(KeyHelper.buildObjectKey(new String[] {
						rs.getString(SaldoTM.COD_SOCIETA),
						rs.getString(SaldoTM.CODICE)
				}), PersistentObject.NO_LOCK);
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return sal;
	}

	private List<SaldoQtaAssegnata> getListaSaldiNoACC(List<SaldoQtaAssegnata> saldi) {
		List<SaldoQtaAssegnata> listaNoAcc = new ArrayList<SaldoQtaAssegnata>();
		for (int i = 0; i < saldi.size(); i++) {
			SaldoQtaAssegnata saldo = saldi.get(i);
			if (saldo != null && saldo.getUbicazioneSaldo() != null && saldo.getUbicazioneSaldo().equals("ACC")) {
				continue;
			} else
				listaNoAcc.add(saldo);
		}
		return listaNoAcc;
	}

	@SuppressWarnings({ "rawtypes" })
	private List<YOggettoSaldoColata> getSaldiColate(Vector eS) {
		List<YOggettoSaldoColata> listaSaldiColata = new ArrayList<YOggettoSaldoColata>();
		String colata = "";
		YOggettoSaldoColata saldiColata = new YOggettoSaldoColata();
		boolean first = true;
		for (Object element : eS) {
			SaldoQtaAssegnata sqa = (SaldoQtaAssegnata) element;
			if (!sqa.getLotto5().equals(colata) || first) {
				first = false;
				colata = sqa.getLotto5();
				saldiColata = new YOggettoSaldoColata();
				listaSaldiColata.add(saldiColata);
			}
			saldiColata.saldi.add(sqa);
			saldiColata.qtaTotale = saldiColata.qtaTotale.add(sqa.getQtaDisponibileSaldo());
		}
		return listaSaldiColata;
	}

	protected boolean checkMissioniOmesse() { // 70975
		boolean check = true;
		try {
			String select = "SELECT * FROM SOFTRE.Y_OMETTI_MISSIONI_V01 " + "WHERE ID_AZIENDA = '"
					+ Azienda.getAziendaCorrente() + "' AND " + "COD_LISTA = '" + this.getCodiceTestataLista()
					+ "' AND " + "ID_RIGA_DOC = '" + this.getNumeroRigaHost() + "' AND " + "ID_DET_RIGA_DOC = '"
					+ this.getDettaglioRigaHost() + "'";
			CachedStatement cs = new CachedStatement(select);
			ResultSet rs = cs.executeQuery();
			if (rs.next()) {
				check = false;
			}
			cs.free();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return check;
	}

	class YOggettoSaldoColata {
		List<SaldoQtaAssegnata> saldi = new ArrayList<SaldoQtaAssegnata>();
		BigDecimal qtaTotale = BigDecimal.ZERO;
	}

	@SuppressWarnings("rawtypes")
	public Vector getMissioniTerminate(Postazione pt, Operatore op, PianificazioneLista pl) {
		Vector elencoMissioniInEsec = new Vector();
		String whereEsec = null;
		if (pl == null)
			whereEsec = MissioneTM.COD_SOCIETA + " = '" + getCodiceSocieta() + "' AND " +
					MissioneTM.COD_LISTA + " = '" + getCodiceTestataLista() + "' AND " +
					MissioneTM.COD_RIGA + " = " + codice + " AND " +
					MissioneTM.STATO_MISSIONE + " = '" + Missione.TERMINATA + "'";
		else
			whereEsec = MissioneTM.COD_MAG_FISICO + " = '" + pl.getCodiceMagFisico() + "' AND " +
					MissioneTM.COD_PIANIF_LISTA + " = " + pl.getCodice() + " AND " +
					MissioneTM.STATO_MISSIONE + " = '" + Missione.ESECUZIONE + "'";
		if (pl == null &&
				(pt != null || op != null))
			whereEsec += "AND " + MissioneTM.COD_MAG_FISICO + " = '" + getCodiceMagFisico() + "'";
		if (pt != null)
			whereEsec += "AND " + MissioneTM.COD_POSTAZIONE + " = '" + pt.getCodice() + "'";
		if (op != null)
			whereEsec += "AND " + MissioneTM.COD_OPERATORE + " = '" + op.getCodice() + "'";
		try {
			elencoMissioniInEsec = YMissione.retrieveList(YMissione.class, whereEsec, "",false);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return elencoMissioniInEsec;
	}

}