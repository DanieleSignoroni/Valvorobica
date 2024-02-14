package it.valvorobica.thip.vendite.proposteEvasione;

import java.sql.Date;
import java.util.GregorianCalendar;

import com.thera.thermfw.base.TimeUtils;

import it.thera.thip.base.generale.CalendarioContinuo;
import it.thera.thip.vendite.proposteEvasione.LspGenGestore;
import it.thera.thip.vendite.proposteEvasione.LspVenModSped;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 12/02/2024
 * <br><br>
 * <b>XXXXX	DSSOF3	12/02/2024</b>    
 * <p>Prima stesura.<br>
 * Modificare la gestione delle date di estrazione.<br></br>
 * Vado a considerare la data minore tra:<br>
 * <ul>
 * 	<li>Data odierna + giorni orizzonte</li>
 *  <li>Data fine mese corrente</li>
 * </ul>
 * La minore data tra le due andra' poi a far lavorare il processo.
 * </p>
 */

public class YLspGenGestore extends LspGenGestore {

	@Override
	public void inizializza(LspVenModSped modello) {
		if(attivareFineMese(modello)) {
			modello.setConsideraFineMese(true);
		}else {
			modello.setConsideraFineMese(false);
		}
		super.inizializza(modello);
	}
	
	/**
	 * Se:<br>
	 * ( Data odierna + orizzonte ) < ( Data fine mese ), allora va considerato il fine mese.<br>
	 * In caso contrario lascio lavorare ( Data odierna + orizzonte ).
	 * @param modello
	 * @return booleano per attivare o no il flag {@link LspVenModSped#setConsideraFineMese(boolean)}
	 */
	protected static boolean attivareFineMese(LspVenModSped modello) {
		java.sql.Date dataFineMese = calcolaDataFineMeseDaModello(modello);
		java.sql.Date dataOrizzionte = calcolaDataOrizzonteDaModello(modello);
		if(dataOrizzionte.compareTo(dataFineMese) < 0) {
			return false;
		}
		return true;
	}

	protected static Date calcolaDataOrizzonteDaModello(LspVenModSped modello) {
		CalendarioContinuo cc = new CalendarioContinuo(false);
		java.sql.Date cdate = TimeUtils.getCurrentDate();
		cdate = cc.aggiungeGiorniLavorativi(cdate, modello.getOrizzonteDate());
		return cdate;
	}

	protected static Date calcolaDataFineMeseDaModello(LspVenModSped modello) {
		CalendarioContinuo cc = new CalendarioContinuo(false);
		GregorianCalendar gc = new GregorianCalendar();
		java.sql.Date cdate = TimeUtils.getCurrentDate();
		int mese = gc.get(GregorianCalendar.MONTH);
		while (mese == gc.get(GregorianCalendar.MONTH)) {
			cdate = cc.aggiungeGiorniLavorativi(cdate, 1);
			gc.setTime(cdate);
		}
		cdate = cc.rimuoveGiorniLavorativi(cdate, 1);
		cdate = cc.rimuoveGiorniLavorativi(cdate, modello.getGiorniAnticipo());
		return cdate;
	}
}
