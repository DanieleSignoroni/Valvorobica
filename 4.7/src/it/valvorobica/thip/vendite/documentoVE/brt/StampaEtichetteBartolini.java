package it.valvorobica.thip.vendite.documentoVE.brt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.Printer;
import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.SaveConWarning;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 05/08/2024
 * <br><br>
 * <b>71XXX	DSSOF3	05/08/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class StampaEtichetteBartolini extends BusinessObjectAdapter implements SaveConWarning,Authorizable {

	@SuppressWarnings("rawtypes")
	protected List iWarningList = new ArrayList<>();

	protected String iIdAzienda;

	protected String iChiaveSelezionato;

	protected Proxy iStampante = new Proxy(com.thera.thermfw.batch.Printer.class);

	public StampaEtichetteBartolini() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
	}

	public String getChiaveSelezionato() {
		return iChiaveSelezionato;
	}

	public void setChiaveSelezionato(String iChiaveSelezionato) {
		this.iChiaveSelezionato = iChiaveSelezionato;
	}

	public void setStampante(Printer stampante) {
		this.iStampante.setObject(stampante);
	}

	public Printer getStampante() {
		return (Printer) iStampante.getObject();
	}

	public void setStampanteKey(String key) {
		iStampante.setKey(key);
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

	@Override
	public int save(boolean force) throws SQLException {
		setIdStampante("\\\\ITCLDSRV010.smeup.com\\ITERBPRT001");
		int rc = 1;
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
			String[] keys = getChiaveSelezionato().split("@");
			for(String key : keys) {
				EtichetteBartolini etichetta = (EtichetteBartolini) 
						EtichetteBartolini.elementWithKey(
								EtichetteBartolini.class, 
								key, PersistentObject.NO_LOCK);
				if(etichetta != null && getIdStampante() != null) {
					InputStream textStream = new ByteArrayInputStream(etichetta.getEtichettaBase64().getBytes());
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
		return rc;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getWarningList() {
		return iWarningList;
	}
}
