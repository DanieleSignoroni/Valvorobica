package it.valvorobica.thip.base.matricole.web;

import com.thera.thermfw.gui.cnr.DOList;

import it.thera.thip.magazzino.generalemag.LottoTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 17/06/2024
 * <br><br>
 * <b>71XXX	DSSOF3	17/06/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YCaricamentoMatchLottoDOList extends DOList {
	
	@Override
	protected int openDetailsList() {
		if(detailsSQLStatementStr.contains("AND (PRIM."+LottoTM.ID_ARTICOLO+" IS NULL)")) {
			detailsSQLStatementStr = detailsSQLStatementStr.replace("AND (PRIM."+LottoTM.ID_ARTICOLO+" IS NULL)", "");
		}
		return super.openDetailsList();
	}
}
