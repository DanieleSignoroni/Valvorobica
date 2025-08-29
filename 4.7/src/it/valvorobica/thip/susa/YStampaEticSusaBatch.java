package it.valvorobica.thip.susa;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.AvailableReport;
import com.thera.thermfw.batch.CrystalReportsInterface;
import com.thera.thermfw.batch.ElaboratePrintRunnable;
import com.thera.thermfw.batch.PrintingToolInterface;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.documenti.TipoDestinatario;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.valvorobica.thip.logis.lgb.YTestataLista;
import it.valvorobica.thip.vendite.documentoVE.susa.IntegrazioneCorriereSusa;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 29/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    29/08/2025  DSSOF3   Prima stesura
 */

public class YStampaEticSusaBatch extends ElaboratePrintRunnable implements Authorizable{

	protected String iCodiceTestataLista;
	protected boolean iParcheggiaEticPerInvio;

	protected AvailableReport iAvailableRpt;

	public String getCodiceTestataLista() {
		return iCodiceTestataLista;
	}

	public void setCodiceTestataLista(String iCodiceTestataLista) {
		this.iCodiceTestataLista = iCodiceTestataLista;
	}

	public boolean isParcheggiaEticPerInvio() {
		return iParcheggiaEticPerInvio;
	}

	public void setParcheggiaEticPerInvio(boolean iParcheggiaEticPerInvio) {
		this.iParcheggiaEticPerInvio = iParcheggiaEticPerInvio;
	}

	public YStampaEticSusaBatch() {
		setParcheggiaEticPerInvio(false);
	}

	protected boolean createAvailableReport() throws Exception{
		job.setReportCounter((short)0);
		iAvailableRpt = createNewReport(getReportId());
		if(iAvailableRpt == null)
			return false;

		try {
			setPrintToolInterface((PrintingToolInterface)Factory.createObject(CrystalReportsInterface.class));
			String s = printToolInterface.generateDefaultWhereCondition(iAvailableRpt, YRptSegnacolloSusaTM.getInstance());
			iAvailableRpt.setWhereCondition(s);
			int res = iAvailableRpt.save();
			if(res < 0) {
				output.println("Problema di salvataggio availableReport, errorCode = " + res);
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace(Trace.excStream);
			return false;
		}
		return true;
	}

	@Override
	public boolean createReport() {
		boolean isOk = false;
		try {
			isOk = createAvailableReport();
			if(isOk == false) {
				output.println("ERRORE: AvailableReport non disponibile!!!");
				return false;
			}
			if(isOk) {
				TestataLista tl = (TestataLista) TestataUds.elementWithKey(TestataLista.class, KeyHelper.buildObjectKey(new String[] {
						Azienda.getAziendaCorrente(),getCodiceTestataLista()
				}), PersistentObject.NO_LOCK);
				if(tl != null) {
					isOk = createReportInternal(tl);
				}else {
					output.println("Non esiste nessuna testata lista con codice "+getCodiceTestataLista());
				}
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return isOk;
	}

	@SuppressWarnings("rawtypes")
	protected boolean createReportInternal(TestataLista tl) throws SQLException {
		boolean isOk = true;
		if(isOk) {
			DocumentoVendita dv = ((YTestataLista)tl).getDocumentoVendita();
			if(dv != null) {
				if(IntegrazioneCorriereSusa.isDocumentoVenditaVettoreSusa(dv)) {
					String CAP = null;
					String idProvincia = null;
					String localita = null;
					String clienteIndirizzoProvincia = null;
					if (dv.getTipoDestinatario() == TipoDestinatario.NUMERO_INDIRIZZO && dv.getIndirizzo() != null) {
						localita = dv.getIndirizzo().getDatiIndirizzo().getLocalita();
						CAP = dv.getIndirizzo().getDatiIndirizzo().getCAP();
						idProvincia = dv.getIndirizzo().getDatiIndirizzo().getIdProvincia();
						clienteIndirizzoProvincia = dv.getCliente().getRagioneSociale() + "\n " + dv.getIndirizzo().getDatiIndirizzo().getIndirizzo() + "\n " + dv.getIndirizzo().getDatiIndirizzo().getIdProvincia();
					}
					if (dv.getTipoDestinatario() == TipoDestinatario.CLIENTE_VEN && dv.getClienteDestinatario() != null) {
						localita = dv.getClienteDestinatario().getLocalita();
						CAP = dv.getClienteDestinatario().getCAP();
						idProvincia = dv.getClienteDestinatario().getIdProvincia();
						clienteIndirizzoProvincia = dv.getClienteDestinatario().getRagioneSociale() + "\n " + dv.getClienteDestinatario().getIndirizzo() + "\n " + dv.getClienteDestinatario().getIdProvincia();
					}
					if (dv.getTipoDestinatario() == TipoDestinatario.MANUALE) {
						CAP = dv.getCAPDestinatario();
						localita = dv.getLocalitaDestinatario();
						idProvincia = dv.getIdProvinciaDen();
						clienteIndirizzoProvincia = dv.getRagioneSocaleDest() + "\n " + dv.getIndirizzoDestinatario() + "\n " + dv.getIdProvinciaDen();
					}
					if(CAP == null || localita == null | idProvincia == null) {
						try {
							DdtTestataVendita ddt = (DdtTestataVendita) DdtTestataVendita.elementWithKey(DdtTestataVendita.class, KeyHelper.buildObjectKey(new String[] {
									dv.getIdAzienda(),dv.getAnnoBolla(),dv.getNumeroBolla(),String.valueOf(DdtTestataVendita.TIPO_DDT_VEN)
							}), PersistentObject.NO_LOCK);
							if(ddt != null) {
								CAP = ddt.getCapDen();
								localita = ddt.getLocalitaDen();
								idProvincia = ddt.getIdProvinciaDen();
								clienteIndirizzoProvincia = ddt.getRagioneSocDen() + "\n " + ddt.getIndirizzoDen() + "\n " + ddt.getIdProvinciaDen();
							}
						} catch (SQLException e) {
							e.printStackTrace(Trace.excStream);
						}
					}
					String[] codiceLineaCodiceZona = AlgoritmoInstradamentoSusa.getInstance().determinaCodiceLineaCodiceZona(CAP, localita, idProvincia);
					Vector elencoUds = TestataLista.getElencoUds(tl);
					Iterator iterUds = elencoUds.iterator();
					int count = 0;
					int rcTot = 0;
					while(rcTot >= 0 && iterUds.hasNext()) {
						TestataUds tu = (TestataUds) TestataUds.elementWithKey(TestataUds.class, (String) iterUds.next(), PersistentObject.NO_LOCK);
						YRptSegnacolloSusa iRptEtichetta = (YRptSegnacolloSusa) Factory.createObject(YRptSegnacolloSusa.class);
						iRptEtichetta.setBatchJobId(getBatchJob().getBatchJobId());
						iRptEtichetta.setReportNr(iAvailableRpt.getReportNr());
						iRptEtichetta.setRigaJobId(count++);

						iRptEtichetta.setCodUds(tu.getCodice());

						iRptEtichetta.setCodiceLinea(codiceLineaCodiceZona[0]);
						iRptEtichetta.setCodiceZona(codiceLineaCodiceZona[1]);

						iRptEtichetta.setInteger01(tu.getRigheUds().size()); //..I colli contenuti in questo UDS sono le righe

						iRptEtichetta.setDecimal01(tu.getVolumeIngombro());
						iRptEtichetta.setDecimal02(tu.getPesoLordo());

						iRptEtichetta.setVarchar02(localita);
						iRptEtichetta.setVarchar03(""); //.Sarebbe il mittente, valvorobica o allebi?

						iRptEtichetta.setVarchar04(clienteIndirizzoProvincia);

						YEtichettaSusa etic = (YEtichettaSusa) Factory.createObject(YEtichettaSusa.class);
						etic.setCodUds(tu.getCodice());
						if(isParcheggiaEticPerInvio()) {
							etic.retrieve(); //potrebbe gia' esistere quindi provo a selezionarla
						}
						try {
							int rc = iRptEtichetta.save();
							if(rc < 0)
								rcTot = rc;
							else
								rcTot += rc;

							if(isParcheggiaEticPerInvio())
								rc += etic.save();

							if(rc < 0)
								rcTot = rc;
							else
								rcTot += rc;
						}catch (SQLException e) {
							e.printStackTrace(Trace.excStream);
						}
					}
					if(rcTot > 0) {
						ConnectionManager.commit();
					}else {
						ConnectionManager.rollback();
					}
				}else {
					isOk = false;
					output.println("L'uds appartiene ad un documento di vendita che non ha come vettore Susa");
				}
			}else {
				isOk = false;
				output.println("Lista non collegata ad un documento vendita");
			}
		}else {
			isOk = false;
			output.println("Impossibile stampare l'UDS perche' non rispetta le condizioni sopra indicate");
		}
		return isOk;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YStampaEticSusaBatch";
	}

}
