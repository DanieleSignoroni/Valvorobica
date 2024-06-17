package it.valvorobica.thip.base;

import java.math.BigDecimal;

import com.thera.thermfw.common.*;
import com.thera.thermfw.persist.Factory;

import it.thera.thip.base.azienda.Azienda;
import it.valvorobica.thip.base.YImportazioneMisureValvFarfBatch.DataRow;
import it.valvorobica.thip.base.matricole.YMatchMatricolaArticolo;
import it.valvorobica.thip.base.matricole.YMatricolaValvo;

/**
 * <h1>Softre Solutions</h1>
 * @author Daniele Signoroni	14/04/2023
 * <br><br>
 *	<b>71045	DSSOF3	14/04/2023</b>	<p>Prima stesura.</p>
 */

public class YMisureValFarfNew extends YMisureValFarfNewPO {

	public ErrorMessage checkDelete() {
		return null;
	}

	public static YMisureValFarfNew misuraValvolaFarfallaDaRow(String idFornitore,
			YMatchMatricolaArticolo match,
			YMatricolaValvo matricola, DataRow row) throws ExcelFieldFormatException {
		YMisureValFarfNew misura = (YMisureValFarfNew) Factory.createObject(YMisureValFarfNew.class);
		misura.setIdAzienda(Azienda.getAziendaCorrente());
		misura.setIdFornitore(idFornitore);
		misura.setIdArticolo(match.getIdArticolo());
		misura.setIdLotto(match.getIdLotto());
		misura.setIdMatricola(matricola.getIdMatricola());
		misura.setManStamp(row.getValueAtPosition(2));
		misura.setPercC(convertToBigDecimal(row.getValueAtPosition(3),"Percentuale C"));
		misura.setPercMn(convertToBigDecimal(row.getValueAtPosition(4),"Percentuale MN"));
		misura.setPercSi(convertToBigDecimal(row.getValueAtPosition(5),"Percentuale SI"));
		misura.setPercP(convertToBigDecimal(row.getValueAtPosition(6),"Percentuale P"));
		misura.setPercS(convertToBigDecimal(row.getValueAtPosition(7),"Percentuale S"));
		misura.setRotturaMpa(convertToInteger(row.getValueAtPosition(8),"Rottura MPA"));
		String snervMpa = row.getValueAtPosition(9);
		if(snervMpa.contains(".")) {
			snervMpa = snervMpa.substring(0, snervMpa.indexOf("."));
		}
		misura.setPercAllung(convertToBigDecimal(row.getValueAtPosition(10),"Percentuale Allungamento"));
		misura.setDurezzaHb(((row.getValueAtPosition(11))));
		return misura;
	}
	
	public static BigDecimal convertToBigDecimal(String value, String fieldName) throws ExcelFieldFormatException {
	    try {
	        return new BigDecimal(value);
	    } catch (NumberFormatException e) {
	        throw new ExcelFieldFormatException(fieldName, value, "Errore di formattazione");
	    }
	}

	public static Integer convertToInteger(String value, String fieldName) throws ExcelFieldFormatException {
	    try {
	        return Integer.valueOf(value);
	    } catch (NumberFormatException e) {
	        throw new ExcelFieldFormatException(fieldName, value, "Errore di formattazione");
	    }
	}

}

