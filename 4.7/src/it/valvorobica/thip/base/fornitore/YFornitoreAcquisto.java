package it.valvorobica.thip.base.fornitore;

import com.thera.thermfw.persist.*;

import it.thera.thip.base.fornitore.*;
import it.thera.thip.base.azienda.Azienda;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 20/10/2023
 * <br><br>
 * <b>71268	DSSOF3	20/10/2023</b>	Aggiunto flag {@link #iStampaAutomTerminalinoDDT} per s/bloccare la stampa automatica da terminalino.<br></br>
 * <b>XXXXX	DSSOF3	13/02/2024</b>
 * <p>
 * Aggiungere flag per gestire l'upload dei certificati fornitore da portale.<br>
 * Flag: {@link #iCertificatiDaPortale}
 * </p>
 */

public class YFornitoreAcquisto extends FornitoreAcquisto {

	protected boolean iStampaAutomTerminalinoDDT = true;
	
	protected boolean iCertificatiDaPortale = false;

	public YFornitoreAcquisto() {
		setStampaAutomTerminalinoDDT(true);
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setStampaAutomTerminalinoDDT(boolean stampaAutomTerminalinoDDT) {
		this.iStampaAutomTerminalinoDDT = stampaAutomTerminalinoDDT;
		setDirty();
	}

	public boolean isStampaAutomTerminalinoDDT() {
		return iStampaAutomTerminalinoDDT;
	}
	
	public boolean isCertificatiDaPortale() {
		return iCertificatiDaPortale;
	}

	public void setCertificatiDaPortale(boolean iCertificatiDaPortale) {
		this.iCertificatiDaPortale = iCertificatiDaPortale;
		setDirty();
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

}

