package it.valvorobica.thip.susa;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.AvailableReport;
import com.thera.thermfw.batch.CrystalReportsInterface;
import com.thera.thermfw.batch.ElaboratePrintRunnable;
import com.thera.thermfw.batch.PrintingToolInterface;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.documenti.TipoDestinatario;
import it.thera.thip.base.generale.IntegrazioneThipLogis;
import it.thera.thip.logis.fis.TestataUds;
import it.thera.thip.logis.lgb.TestataLista;
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

	protected String iCodiceUds;

	protected AvailableReport iAvailableRpt;

	protected YRptSegnacolloSusa iRptEtichetta;

	public String getCodiceUds() {
		return iCodiceUds;
	}

	public void setCodiceUds(String iCodiceUds) {
		this.iCodiceUds = iCodiceUds;
	}

	public YStampaEticSusaBatch() {

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
				TestataUds tu = (TestataUds) TestataUds.elementWithKey(TestataUds.class, iCodiceUds, PersistentObject.NO_LOCK);
				if(tu != null) {
					isOk = createReportInternal(tu);
					if(isOk) {
						String nomef = getPrintToolInterface().exportAvailableReport(iAvailableRpt); 
						if(nomef != null) {
							File f = new File(nomef);
							if(f.exists()) {
								YEtichettaSusa etic = (YEtichettaSusa) Factory.createObject(YEtichettaSusa.class);
								etic.setCodUds(tu.getCodice());
								try {
									int rc = etic.save();
									if (rc > 0) {
										rc = etic.getEtichettaBase64().setBytes(Files.readAllBytes(f.toPath()));
										if (rc > 0) {
											ConnectionManager.commit();
										} else {
											ConnectionManager.rollback();
										}
									}
								} catch (SQLException e) {
									e.printStackTrace(Trace.excStream);
								}
							}
						}
					}
				}else {
					output.println("Non esiste nessuna testata UDS con codice "+iCodiceUds);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return isOk;
	}

	protected boolean createReportInternal(TestataUds tu) {
		boolean isOk = true;
		isOk = controllaUds(tu);
		if(isOk) {
			DocumentoVendita dv = ((YTestataLista)tu.getTestataLista()).getDocumentoVendita();
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
					iRptEtichetta = (YRptSegnacolloSusa) Factory.createObject(YRptSegnacolloSusa.class);
					iRptEtichetta.setCodUds(tu.getCodice());

					String[] codiceLineaCodiceZona = AlgoritmoInstradamentoSusa.getInstance().determinaCodiceLineaCodiceZona(CAP, localita, idProvincia);
					iRptEtichetta.setCodiceLinea(codiceLineaCodiceZona[0]);
					iRptEtichetta.setCodiceZona(codiceLineaCodiceZona[1]);

					iRptEtichetta.setInteger01(tu.getRigheUds().size()); //..I colli contenuti in questo UDS sono le righe

					iRptEtichetta.setDecimal01(tu.getVolumeIngombro());
					iRptEtichetta.setDecimal02(tu.getPesoLordo());

					iRptEtichetta.setVarchar02(localita);
					iRptEtichetta.setVarchar03(""); //.Sarebbe il mittente, valvorobica o allebi?

					iRptEtichetta.setVarchar04(clienteIndirizzoProvincia);

					try {
						int rc = iRptEtichetta.save();
						if(rc > 0) {
							ConnectionManager.commit();
							isOk = true;
						}else {
							ConnectionManager.rollback();
						}
					}catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}else {
					output.println("L'uds appartiene ad un documento di vendita che non ha come vettore Susa");
				}
			}else {
				output.println("Lista non collegata ad un documento vendita");
			}
		}else {
			output.println("Impossibile stampare l'UDS perche' non rispetta le condizioni sopra indicate");
		}
		return isOk;
	}

	protected boolean controllaUds(TestataUds tu) {
		if(tu == null)
			return false;
		if(tu.getStatoAllestimento() != TestataUds.CHIUSO) {
			output.println("Uds non chiusa");
			return false;
		}
		TestataLista tl = tu.getTestataLista();
		if(tl != null) {
			char tipo = tl.getCodice().charAt(0);
			if(tipo != IntegrazioneThipLogis.VENDITA) {
				output.println("Uds non di vendita");
				return false;
			}
		}else {
			output.println("Uds senza testata lista");
			return false;
		}
		return false;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YStampaEticSusaBatch";
	}

}
