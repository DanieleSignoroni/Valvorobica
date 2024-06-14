package it.valvorobica.thip.base;

import java.math.BigDecimal;

import com.thera.thermfw.common.*;
import com.thera.thermfw.persist.Factory;

import it.valvorobica.thip.base.YImportazioneMisureValvFarfBatch.DataRow;

/**
 * <h1>Softre Solutions</h1>
 * @author Daniele Signoroni	14/04/2023
 * <br><br>
 *	<b>71045	DSSOF3	14/04/2023</b>	<p>Prima stesura.</p>
 */

public class YMisureValFarfComp extends YMisureValFarfCompPO {

	public ErrorMessage checkDelete() {
		return null;
	}
	
	public static YMisureValFarfComp misuraValvolaFarfallaCompDaRow
	(YMisureValFarfNew misura, DataRow row, String idComponente) throws NumberFormatException{
		YMisureValFarfComp farfalla = (YMisureValFarfComp) Factory.createObject(YMisureValFarfComp.class);
		farfalla.setParent(misura);
		farfalla.setIdTpComp(idComponente);
		farfalla.setColataComp(row.getValueAtPosition(2));
		farfalla.setPercC(new BigDecimal((row.getValueAtPosition(3))));
		farfalla.setPercMn(new BigDecimal((row.getValueAtPosition(4))));
		farfalla.setPercSi(new BigDecimal((row.getValueAtPosition(5))));
		if(row.data.size() > 12) {
			farfalla.setPercP(new BigDecimal((row.getValueAtPosition(9))));
			farfalla.setPercS(new BigDecimal((row.getValueAtPosition(10))));
			farfalla.setRotturaMpa(new Integer((row.getValueAtPosition(11))));
			String snervMpa = row.getValueAtPosition(12);
			if(snervMpa.contains(".")) {
				snervMpa = snervMpa.substring(0, snervMpa.indexOf("."));
			}
			farfalla.setPercAllung(new BigDecimal((row.getValueAtPosition(13))));
			farfalla.setDurezzaHb(((row.getValueAtPosition(14))));
		}else {
			farfalla.setPercP(new BigDecimal((row.getValueAtPosition(6))));
			farfalla.setPercS(new BigDecimal((row.getValueAtPosition(7))));
			farfalla.setRotturaMpa(new Integer((row.getValueAtPosition(8))));
			String snervMpa = row.getValueAtPosition(9);
			if(snervMpa.contains(".")) {
				snervMpa = snervMpa.substring(0, snervMpa.indexOf("."));
			}
			farfalla.setPercAllung(new BigDecimal((row.getValueAtPosition(10))));
			farfalla.setDurezzaHb(((row.getValueAtPosition(11))));
		}
		return farfalla;
	}

}

