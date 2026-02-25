package it.valvorobica.thip.logis.lgb.web;

import com.thera.thermfw.web.servlet.BaseServlet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.ServletEnvironment;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.logis.fis.Missione;
import it.thera.thip.logis.fis.RigaUds;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.logis.fis.Ubicazione;
import it.thera.thip.logis.lgb.RigaLista;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.produzione.documento.DocumentoProduzione;
import it.valvorobica.thip.logis.bas.YCostantiValvo;
import it.valvorobica.thip.logis.lgb.YTestataLista;

/**
 * 
 * @author thomas.brescianini
 *
 *	71206	TBSOF3	05/09/2023	Quando viene regredita la lista, vado a settare la lista ad aperta, nel caso in cui fosse chiusa, riapro la riga della lista,
 *								elimino le uds collegate, riapro il documento di produzione (nel caso in cui fosse presente) e lo cancello. Tutte le informazioni per queste 
 *								operazioni sono prese dalla vista SOFTRE.Y_LISTE_UDS_MISSIONE_V01 di CRIART
 *
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72375	25/02/2026	DSSOF3	 Gestione regressione missioni fincantieri.
 */

public class YRegressionePrelievoLista extends BaseServlet{

	private static final long serialVersionUID = 8262728611435384659L;

	protected List<String> elencoMissioni = new ArrayList<String>();

	protected List<String> elencoUds = new ArrayList<String>();

	protected List<String> elencoDocPrd = new ArrayList<String>();

	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@Override
	protected void processAction(ServletEnvironment se) throws Exception {
		String key = se.getRequest().getParameter("key");
		Vector errori = new Vector();
		if(key != null) {
			TestataLista testataLista = (TestataLista) TestataLista.elementWithKey(TestataLista.class, key, 0);
			if(testataLista != null) {
				//72375 <
				if(testataLista != null && testataLista.getCodiceTipoLista().equals(YCostantiValvo.codTipoListaTrasferimentoFincantieri())) {
					errori = (Vector) ((YTestataLista)testataLista).regressioneLista();
				//72375 >
				}else {
					testataLista.setStatoLista(TestataLista.APERTO);
					Iterator iterRighe = testataLista.getRigheLista().iterator();
					while(iterRighe.hasNext()) {
						RigaLista riga = (RigaLista) iterRighe.next();
						riga.setStatoRigaLista(testataLista.APERTO);
						riga.save();
					}
					if(testataLista.save() >= 0) {
						getInformazioni(testataLista);
						eliminaUds();
						Vector errors = cancellaMissioni();
						if(!errors.isEmpty())
							errori.addAll(errors);
						errors = gestioneDocumentiProduzione();
						if(!errors.isEmpty())
							errori.addAll(errors);
					}
				}
			}
			if(errori.isEmpty()) {
				ConnectionManager.commit();
				String url = "it/valvorobica/thip/logis/fis/YRegressioneConfermataTestata.jsp";
				se.sendRequest(getServletContext(), url, false);
			}else {
				for(int j = 0; j < errori.size(); j++) {
					ErrorMessage er = (ErrorMessage) errori.get(j);
					se.addErrorMessage(er);
				}
				String url = "com/thera/thermfw/common/ErrorListHandler.jsp?thClassName=RigaUds";
				se.sendRequest(getServletContext(), url, false);
			}
		}
	}

	protected void getInformazioni(TestataLista tesLis) {
		CachedStatement cs = null;
		ResultSet rs = null;
		try {
			String select = "SELECT * FROM SOFTRE.Y_LISTE_UDS_MISSIONE_V01 WHERE COD_SOCIETA = '" + Azienda.getAziendaCorrente() + "' AND "
					+ "COD_LISTA = '" + tesLis.getCodice() + "'";
			cs = new CachedStatement(select);
			rs = cs.executeQuery();
			while(rs.next()) {
				String codUds = rs.getString("COD_UDS") != null ? rs.getString("COD_UDS").trim() : "";
				String rifDocPrd = rs.getString("RIF_DOC_PRD") != null ? rs.getString("RIF_DOC_PRD").trim() : "";
				String missione = rs.getString("CODICE") != null ? rs.getString("CODICE").trim() : ""; 
				if(!codUds.equals("") && !elencoUds.contains(codUds)) 
					elencoUds.add(codUds);
				if(!rifDocPrd.equals("") && !elencoDocPrd.contains(rifDocPrd))
					elencoDocPrd.add(rifDocPrd);
				if(!missione.equals("") && !elencoMissioni.contains(missione))
					elencoMissioni.add(missione);
			}

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)
					rs.close();
				cs.free();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected void eliminaUds() {
		Iterator iter = elencoUds.iterator();
		try {
			while(iter.hasNext()) {
				String codUds = (String) iter.next();
				TestataUds uds = (TestataUds) TestataUds.elementWithKey(TestataUds.class, codUds, PersistentObject.NO_LOCK);
				if(uds != null) {
					Iterator iterRighe = uds.getRigheUds().iterator();
					while(iterRighe.hasNext()) {
						RigaUds udsRiga = (RigaUds) iterRighe.next();
						udsRiga.delete();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	protected Vector gestioneDocumentiProduzione() {
		Iterator iter = elencoDocPrd.iterator();
		Vector errori = new Vector();
		try {
			while(iter.hasNext()) {
				String codDocPrd = (String) iter.next();
				String anno = "";
				String numero = "";
				if(codDocPrd.length() >= 15) {
					anno = codDocPrd.substring(0,4);
					numero = codDocPrd.substring(5, 7) + "  " + codDocPrd.substring(9);
				}
				String key = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), anno, numero});

				DocumentoProduzione docPrd = (DocumentoProduzione) DocumentoProduzione.elementWithKey(DocumentoProduzione.class, key, PersistentObject.NO_LOCK);
				if(docPrd != null) {
					if(docPrd.getStatoDocumento() == DatiComuniEstesi.VALIDO) {
						docPrd.setForzaAnnullamentoMovimentiMagazzino(true);
						errori = docPrd.passaggioStatoValidoSospeso(false);
					}
					if(errori.isEmpty()) {
						docPrd.delete();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return errori;
	}

	@SuppressWarnings("rawtypes")
	protected Vector cancellaMissioni() {
		Vector errori = new Vector();
		try {
			for(int i = 0; i < elencoMissioni.size(); i++) {
				String codiceMissione = elencoMissioni.get(i);
				if(!codiceMissione.equals("")) {
					String key = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), codiceMissione});
					Missione missione = (Missione) Missione.elementWithKey(Missione.class, key, 0);
					if(missione != null) {
						boolean checkArticolo = controlloArticoloTubing(missione);
						if(!checkArticolo) {
							String codiceUbicazione = ParametroPsn.getValoreParametroPsn("YUbicazioneAnnullamento", "Ubicazione annullamento missione");
							if(codiceUbicazione != null) {
								codiceUbicazione = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), codiceUbicazione} );
								Ubicazione ubicazione = (Ubicazione) Ubicazione.elementWithKey(Ubicazione.class, codiceUbicazione, 0);
								if(ubicazione != null) {
									missione.setMappaUdcInv(null);
									missione.setCodiceMappaUdcInv(null);
									missione.setMappaUdc(null);
									missione.setCodiceMappaUdc(null);
									errori = missione.annulloConfermata(ubicazione);
								}
							}
						}else {
							String codiceUbicazione = ParametroPsn.getValoreParametroPsn("YUbicazioneAnnullamentoTubing", "Ubicazione annullamento missione Tubing");
							codiceUbicazione = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), codiceUbicazione} );
							Ubicazione ubicazione = (Ubicazione) Ubicazione.elementWithKey(Ubicazione.class, codiceUbicazione, 0);
							if(ubicazione != null) {
								errori = missione.annulloConfermata(ubicazione);
							}
						}
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return errori;
	}

	protected boolean controlloArticoloTubing(Missione missione) {
		boolean controllo = false;
		CachedStatement cs = null;
		ResultSet rs = null;
		try {
			String select = "SELECT * FROM SOFTRE.Y_ART_TUBING_V01 "
					+ "WHERE ID_AZIENDA = '" + Azienda.getAziendaCorrente() + "' "
					+ "AND ID_ARTICOLO = '" + missione.getCodiceArticolo() + "' "
					+ "AND TUBING = 'Y' ";
			cs = new CachedStatement(select);
			rs = cs.executeQuery();
			if(rs.next())
				controllo = true;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(cs != null)
					cs.free();
				if(rs != null)
					rs = null;
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return controllo;
	}
}
