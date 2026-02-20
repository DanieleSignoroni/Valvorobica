package it.valvorobica.thip.logis.lgb.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.documenti.StatoAttivita;
import it.thera.thip.base.documenti.StatoAvanzamento;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.logis.lgb.RigaLista;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.logis.lgb.web.TestataListaWebFormModifier;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;

/**
 * <h1>Softre Solutions</h1>
 * @author Thomas Brescianini
 * <br><br>
 * <b>71206	TBSOF3	05/09/2023</b>	<p>Nascosto il pulsante di regressione se non rispetta le condizioni per la quale può esser regredita</p>
 * <b>71426	DSSOF3	07/03/2024</b>	
 * <p>
 * Code refactor.
 * </p>
 */

public class YTestataListaWebFormModifier extends TestataListaWebFormModifier{

	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {
		try {
			boolean check = true;
			boolean checkQta = false;
			TestataLista testataLista = (TestataLista) getBODataCollector().getBo();
			if(testataLista != null) {
				Iterator iterUds = testataLista.getElencoUds(testataLista).iterator();
				while(iterUds.hasNext()) {	//se non sono presenti uds, vuol dire che non è stata prelevata nessuna quantità e, quindi è inutile fare la regressione
					String codUds = (String) iterUds.next();
					TestataUds udsTes = (TestataUds) TestataUds.elementWithKey(TestataUds.class, 
							codUds, PersistentObject.NO_LOCK);
					if(udsTes != null && udsTes.getStatoAllestimento() != TestataUds.APERTO && udsTes.getFlagSpedito() ){ //controllo che lo stato allestimento sia aperto e la uds non sia stata spedita
						check = false;
						break;
					}
					//					if(udsTes != null) {
					//						Iterator iter = udsTes.getRigheUds().iterator();
					//						while(iter.hasNext()) {
					//							RigaUds riga = (RigaUds) iter.next();
					//							if(riga.getQta1Confermata().compareTo(BigDecimal.ZERO) > 0) {	//serve almeno una uds che abbia evaso almeno una quantità
					//								checkQta = true;
					//								break;
					//							}
					//						}
					//					}
				}
				Iterator iter = testataLista.getRigheLista().iterator();
				while(iter.hasNext()) {
					RigaLista rigaLista = (RigaLista) iter.next();
					if(rigaLista.getQta1Evasa().compareTo(BigDecimal.ZERO) > 0) {
						checkQta = true;
						break;
					}
				}
				if(!testataLista.getTipoLista().getFlagUds()) {
					check = true;
				}
				if(check && checkQta) {
					String keyOrdineEsec = testataLista.getRiferimentoPartner();
					String anno = "";
					String numero = "";
					if(keyOrdineEsec != null && !keyOrdineEsec.equals("") && keyOrdineEsec.length() >= 15) {
						anno = keyOrdineEsec.substring(0, 4);
						numero = keyOrdineEsec.substring(5, 15);
					}
					OrdineEsecutivo ordEsec = (OrdineEsecutivo) OrdineEsecutivo.elementWithKey(OrdineEsecutivo.class, 
							KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), anno, numero}), PersistentObject.NO_LOCK);
					if(testataLista != null && (testataLista.getStatoLista() == TestataLista.APERTO || ordEsec != null)) {
						DocumentoVendita docVen = getDocumentoVendita(testataLista);
						DocumentoAcquisto docAcq = getDocumentoAcquisto(testataLista);
						if(docVen != null || docAcq != null ) {
							if(docVen != null && docVen.getCollegatoAMagazzino() == StatoAttivita.ESEGUITO && docVen.getStatoAvanzamento() == StatoAvanzamento.PROVVISORIO) {
								out.println("<script>");
								out.println("parent.document.getElementById('tbbtREGRESSIONE').style.display = 'none';");
								out.println("</script>");
							}else if(docAcq != null && docAcq.getCollegatoAMagazzino() == StatoAttivita.ESEGUITO && docAcq.getStatoAvanzamento() == StatoAvanzamento.PROVVISORIO) {
								out.println("<script>");
								out.println("parent.document.getElementById('tbbtREGRESSIONE').style.display = 'none';");
								out.println("</script>");
							}
						}else if(ordEsec == null){
							out.println("<script>");
							out.println("parent.document.getElementById('tbbtREGRESSIONE').style.display = 'none';");
							out.println("</script>");
						}
					}else {
						out.println("<script>");
						out.println("parent.document.getElementById('tbbtREGRESSIONE').style.display = 'none';");
						out.println("</script>");
					}
				}else {
					out.println("<script>");
					out.println("parent.document.getElementById('tbbtREGRESSIONE').style.display = 'none';");
					out.println("</script>");
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		super.writeFormEndElements(out);
	}

	/**
	 * 
	 * @param lista
	 * @return
	 * metodo per restituire il documento di vendita collegato alla lista
	 */
	protected DocumentoVendita getDocumentoVendita(TestataLista lista) {
		DocumentoVendita docVen = null;
		try {
			String anno = lista.getCodice().substring(1, 5);
			String numero = lista.getCodice().substring(5);
			String key = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), anno, numero});
			docVen = (DocumentoVendita) DocumentoVendita.elementWithKey(DocumentoVendita.class, key, 0);
			if(docVen != null) 
				return docVen;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return docVen;
	}

	/**
	 * 
	 * @param lista
	 * @return
	 * metodo per restituire il documento di acquisto collegato alla lista
	 */
	protected DocumentoAcquisto getDocumentoAcquisto(TestataLista lista) {	
		DocumentoAcquisto docAcq = null;	
		try {
			String anno = lista.getCodice().substring(1, 5);
			String numero = lista.getCodice().substring(5);
			String key = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), anno, numero});
			docAcq = (DocumentoAcquisto) DocumentoAcquisto.elementWithKey(DocumentoAcquisto.class, key, 0);
			if(docAcq != null) 
				return docAcq;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return docAcq;
	}

}
