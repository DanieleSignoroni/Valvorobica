package it.valvorobica.thip.base.generale;

import it.thera.thip.base.generale.CollegamentoThipLogis;
import it.thera.thip.base.generale.MovimentoThipLogis;
import it.thera.thip.logis.fis.RigaMovimento;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 24/02/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    24/02/2026  DSSOF3   Prima stesura
 */

public class YCollegamentoThipLogis extends CollegamentoThipLogis {

	@Override
	protected RigaMovimento settaAttributiRigaMovimentoLogis(RigaMovimento rigaMovimLogis, MovimentoThipLogis mtl) {
		RigaMovimento rm = super.settaAttributiRigaMovimentoLogis(rigaMovimLogis, mtl);
		return rm;
	}
}
