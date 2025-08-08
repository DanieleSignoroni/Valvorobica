package it.valvorobica.thip.susa.utility;

import java.io.File;

import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 08/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    08/08/2025  DSSOF3   Prima stesura
 */

public class CaricamentoFileSusa extends BusinessObjectAdapter implements Authorizable {

	protected String iIdAzienda;

	protected String iTemporaryFileName;

	protected String iClassNameOrigine;

	protected File file = null;

	public CaricamentoFileSusa() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public String getClassNameOrigine() {
		return iClassNameOrigine;
	}

	public void setClassNameOrigine(String iClassNameOrigine) {
		this.iClassNameOrigine = iClassNameOrigine;
	}

	public String getTemporaryFileName() {
		return iTemporaryFileName;
	}

	public void setTemporaryFileName(String iTemporaryFileName) {
		this.iTemporaryFileName = iTemporaryFileName;
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
	}

}
