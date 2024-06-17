package it.valvorobica.thip.base;

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
	
	public static YMisureValFarfComp misuraValvolaFarfallaCompDaRow(YMisureValFarfNew misura, DataRow row, String idComponente)
			throws ExcelFieldFormatException {
	    YMisureValFarfComp farfalla = (YMisureValFarfComp) Factory.createObject(YMisureValFarfComp.class);
	    farfalla.setParent(misura);
	    farfalla.setIdTpComp(idComponente);
	    
	    try {
	        farfalla.setColataComp(row.getValueAtPosition(2));
	        farfalla.setPercC(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(3), "Percentuale C"));
	        farfalla.setPercMn(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(4), "Percentuale MN"));
	        farfalla.setPercSi(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(5), "Percentuale SI"));

	        if(row.data.size() > 12) {
	            farfalla.setPercP(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(9), "Percentuale P"));
	            farfalla.setPercS(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(10), "Percentuale S"));
	            farfalla.setRotturaMpa(YMisureValFarfNew.convertToInteger(row.getValueAtPosition(11), "Rottura Mpa"));
	            String snervMpa = row.getValueAtPosition(12);
	            if(snervMpa.contains(".")) {
	                snervMpa = snervMpa.substring(0, snervMpa.indexOf("."));
	            }
	            farfalla.setPercAllung(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(13), "Percentuale Allung"));
	            farfalla.setDurezzaHb(row.getValueAtPosition(14));
	        } else {
	            farfalla.setPercP(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(6), "Percentuale P"));
	            farfalla.setPercS(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(7), "Percentuale S"));
	            farfalla.setRotturaMpa(YMisureValFarfNew.convertToInteger(row.getValueAtPosition(8), "Rottura Mpa"));
	            String snervMpa = row.getValueAtPosition(9);
	            if(snervMpa.contains(".")) {
	                snervMpa = snervMpa.substring(0, snervMpa.indexOf("."));
	            }
	            farfalla.setPercAllung(YMisureValFarfNew.convertToBigDecimal(row.getValueAtPosition(10), "Percentuale Allung"));
	            farfalla.setDurezzaHb(row.getValueAtPosition(11));
	        }
	    } catch (ExcelFieldFormatException e) {
	        throw new ExcelFieldFormatException(e.fieldName, e.fieldValue, "Errore di formattazione per il campo " + e.fieldName + " con valore " + e.fieldValue + ": " + e.getMessage());
	    }
	    
	    return farfalla;
	}


}

