package it.valvorobica.thip.susa;

import java.sql.Date;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.security.Authorizable;

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
public class YEXPDDTSusa extends BatchRunnable implements Authorizable {

	protected Date iDateFrom;

	protected Date iDateTo;

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

	public YEXPDDTSusa() {
		setDateFrom(TimeUtils.getCurrentDate());
		setDateTo(TimeUtils.getCurrentDate());
	}

	@Override
	protected boolean run() {
		return false;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YEXPDDTSusa";
	}

}
