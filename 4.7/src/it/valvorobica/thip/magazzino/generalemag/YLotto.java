package it.valvorobica.thip.magazzino.generalemag;

import java.sql.Date;

import com.thera.thermfw.persist.*;

import it.thera.thip.base.partner.NazionePrimrose;
import it.thera.thip.magazzino.generalemag.*;
import it.thera.thip.base.azienda.Azienda;

/**
 * 
 * @author daniele.signoroni	
 *	70649	DSSOF3	05/09/2022	Entità generata da Alex.
 *	<b>71406	DSSOF3	31/01/2024</b>
 *  <p>
 *  Aggiunta campi:<br>
 *  <ul>
 *  	<li>{@link #iNumeroBollaDoganale}</li>
 *  	<li>{@link #iDataBollaDoganale}</li>
 *  	<li>{@link #iDogana}</li>
 *  </ul>
 *  </p>
 */

public class YLotto extends Lotto {

	protected Proxy iRelnazione = new Proxy(it.thera.thip.base.partner.NazionePrimrose.class);

	protected String iIdSigla;
	
	//Inizio
	
	protected String iNumeroBollaDoganale;
	
	protected Date iDataBollaDoganale;
	
	protected String iDogana;
	
	protected boolean iCertificatiDaPortale = false;
	
	public YLotto() {
		setCodiceAzienda(Azienda.getAziendaCorrente());
	}

	public void setRelnazione(NazionePrimrose relnazione) {
		this.iRelnazione.setObject(relnazione);
		setDirty();
	}

	public NazionePrimrose getRelnazione() {
		return (NazionePrimrose)iRelnazione.getObject();
	}

	public void setRelnazioneKey(String key) {
		iRelnazione.setKey(key);
		setDirty();
	}

	public String getRelnazioneKey() {
		return iRelnazione.getKey();
	}

	public void setNazioneor(String nazioneor) {
		iRelnazione.setKey(nazioneor);
		setDirty();
	}

	public String getNazioneor() {
		String key = iRelnazione.getKey();
		return key;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YLotto yLotto = (YLotto)obj;
		iRelnazione.setEqual(yLotto.iRelnazione);
	}

	public String getIdSigla() {
		return iIdSigla;
	}

	public void setIdSigla(String iIdSigla) {
		this.iIdSigla = iIdSigla;
	}
	
	//Inizio
	
	public String getNumeroBollaDoganale() {
		return iNumeroBollaDoganale;
	}

	public void setNumeroBollaDoganale(String iNumeroBollaDoganale) {
		this.iNumeroBollaDoganale = iNumeroBollaDoganale;
	}

	public Date getDataBollaDoganale() {
		return iDataBollaDoganale;
	}

	public void setDataBollaDoganale(Date iDataBollaDoganale) {
		this.iDataBollaDoganale = iDataBollaDoganale;
	}

	public String getDogana() {
		return iDogana;
	}

	public void setDogana(String iDogana) {
		this.iDogana = iDogana;
	}
	
	public boolean isCertificatiDaPortale() {
		return iCertificatiDaPortale;
	}

	public void setCertificatiDaPortale(boolean iCertificatiDaPortale) {
		this.iCertificatiDaPortale = iCertificatiDaPortale;
		setDirty();
	}

}