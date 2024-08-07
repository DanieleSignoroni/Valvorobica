package it.valvorobica.thip.vendite.documentoVE.brt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.batch.Printer;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.DdtTes;
import it.thera.thip.base.comuniVenAcq.DdtTesTM;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;

/**
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Daniele Signoroni 05/08/2024 <br>
 *         <br>
 *         <b>71XXX DSSOF3 05/08/2024</b>
 *         <p>
 *         Prima stesura.<br>
 * 
 *         </p>
 */

public class YExpDDTBartolini extends BatchRunnable implements Authorizable {

	public static final char PROCESSATO = 'P';

	public static final String RES = "it.valvorobica.thip.vendite.documentoVE.brt.resources.EstremiBartolini";

	protected Date iDateFrom;

	protected Date iDateTo;

	protected Proxy iStampante = new Proxy(com.thera.thermfw.batch.Printer.class);

	public Date getDateFrom() {
		return iDateFrom;
	}

	public void setDateFrom(Date iDateFrom) {
		this.iDateFrom = iDateFrom;
	}

	public Date getDateTo() {
		return iDateTo;
	}

	public void setDateTo(Date iDateTo) {
		this.iDateTo = iDateTo;
	}

	public void setStampante(Printer stampante) {
		this.iStampante.setObject(stampante);
		setDirty();
	}

	public Printer getStampante() {
		return (Printer) iStampante.getObject();
	}

	public void setStampanteKey(String key) {
		iStampante.setKey(key);
		setDirty();
	}

	public String getStampanteKey() {
		return iStampante.getKey();
	}

	public void setIdStampante(String idStampante) {
		iStampante.setKey(idStampante);
	}

	public String getIdStampante() {
		String key = iStampante.getKey();
		return key;
	}

	public YExpDDTBartolini() {
		setDateFrom(TimeUtils.getCurrentDate());
		setDateTo(TimeUtils.getCurrentDate());
	}

	@Override
	protected boolean run() {
		EstremiBartolini estremiBrt = EstremiBartolini.estremiBartolini(RES);
		output.println(" ** Esportazione dei DDT verso Bartolini **");
		boolean isOk = true;
		for (DdtTes ddt : recuperaListaDDT()) {
			output.println(" --> Processo DDT {" + ddt.getKey() + "} ");

			if(ddt.getFlagRisUte4() != PROCESSATO) {
				JSONObject json = estremiBrt.recuperaJsonDDT(ddt, estremiBrt);
				JSONObject bodyAsJSON = estremiBrt.sendShipmentBartolini(ddt,json, estremiBrt);
				if(bodyAsJSON.has("labels")) {
					JSONArray labels = bodyAsJSON.getJSONArray("labels");
					for(int i = 0; i < labels.length(); i ++) {
						JSONObject label = labels.getJSONObject(i);
						EtichetteBartolini etichetta = (EtichetteBartolini) label.get("etichetta");
						PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
						PrintService selectedPrintService = null;
						for (PrintService printService : printServices) {
							if (printService.getName().equalsIgnoreCase(getIdStampante())) {
								selectedPrintService = printService;
								break;
							}
						}
						if(selectedPrintService != null) {
							DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
							if(etichetta != null && getIdStampante() != null) {
								InputStream textStream = null;
								try {
									textStream = new ByteArrayInputStream(etichetta.getEtichettaBase64().getBytes());
								} catch (SQLException e) {
									e.printStackTrace(Trace.excStream);
								}
								DocPrintJob printJob = selectedPrintService.createPrintJob();
								PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
								try {
									printJob.print(new SimpleDoc(textStream, flavor, null), attributes);
								} catch (PrintException e) {
									e.printStackTrace(Trace.excStream);
								};
							}
						}
					}
				}
			}else {
				output.println(" ## DDT gia' flaggato come processato, non verra' analizzato");
			}
			//Flaggo il ddt come processato e non lo ri-processero' piu'
			try {
				ddt.setFlagRisUte4(PROCESSATO);
				int rcSave = ddt.save();
				if(rcSave <= 0) {
					ConnectionManager.rollback();
				}else {
					ConnectionManager.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}


			output.println();
		}
		output.println(" ** Termine... **");
		return isOk;
	}

	@SuppressWarnings("unchecked")
	public List<DdtTestataVendita> recuperaListaDDT() {
		List<DdtTestataVendita> ddts = new ArrayList<DdtTestataVendita>();
		String WHERE_DDT_TES = " " + DdtTesTM.ID_AZIENDA + " = '" + Azienda.getAziendaCorrente() + "' " + "AND "
				+ DdtTesTM.DATA_DDT + " BETWEEN '" + getDateFrom() + "' AND '" + getDateTo() + "' ";
		String ORDER_BY = " " + DdtTesTM.DATA_DDT + " ASC ";
		WHERE_DDT_TES = " " + DdtTesTM.ID_AZIENDA + " = '" + Azienda.getAziendaCorrente() + "' AND "
				+ DdtTesTM.ID_NUMERO_DDT + " = 'DT  007701' ";
		try {
			ddts = DdtTestataVendita.retrieveList(DdtTestataVendita.class, WHERE_DDT_TES, ORDER_BY, false);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return ddts;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YExpDDTBRT";
	}

}
