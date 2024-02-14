package it.valvorobica.thip.base.articolo;

import com.thera.thermfw.persist.*;
import it.thera.thip.base.articolo.*;
import it.thera.thip.base.azienda.Azienda;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 13/02/2024
 * <br><br>
 * <b>XXXXX	DSSOF3	13/02/2024</b>
 * <p>
 * Aggiungere flag per gestire l'upload dei certificati fornitore da portale.<br>
 * Flag: {@link #iCertificatiDaPortale}
 * </p>
 */

public class YMicroFamiglia extends MicroFamiglia {

	protected boolean iCertificatiDaPortale = false;

	public YMicroFamiglia() {
		setCertificatiDaPortale(false);
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setCertificatiDaPortale(boolean certificatiDaPortale) {
		this.iCertificatiDaPortale = certificatiDaPortale;
		setDirty();
	}

	public boolean getCertificatiDaPortale() {
		return iCertificatiDaPortale;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

}

