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
			YMatricolaValvo matricola, DataRow row) throws NumberFormatException {
		YMisureValFarfNew misura = (YMisureValFarfNew) Factory.createObject(YMisureValFarfNew.class);
		misura.setIdAzienda(Azienda.getAziendaCorrente());
		misura.setIdFornitore(idFornitore);
		misura.setIdArticolo(match.getIdArticolo());
		misura.setIdLotto(match.getIdLotto());
		misura.setIdMatricola(matricola.getIdMatricola());
		misura.setManStamp(row.getValueAtPosition(2));
		misura.setPercC(new BigDecimal((row.getValueAtPosition(3))));
		misura.setPercMn(new BigDecimal((row.getValueAtPosition(4))));
		misura.setPercSi(new BigDecimal((row.getValueAtPosition(5))));
		misura.setPercP(new BigDecimal((row.getValueAtPosition(6))));
		misura.setPercS(new BigDecimal((row.getValueAtPosition(7))));
		misura.setRotturaMpa(new Integer((row.getValueAtPosition(8))));
		String snervMpa = row.getValueAtPosition(9);
		if(snervMpa.contains(".")) {
			snervMpa = snervMpa.substring(0, snervMpa.indexOf("."));
		}
		misura.setPercAllung(new BigDecimal((row.getValueAtPosition(10))));
		misura.setDurezzaHb(((row.getValueAtPosition(11))));
		return misura;
	}

}

