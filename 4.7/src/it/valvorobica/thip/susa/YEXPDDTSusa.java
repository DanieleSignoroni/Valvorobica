package it.valvorobica.thip.susa;

import java.io.File;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.batch.BatchRunnable;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.DdtTes;
import it.thera.thip.base.comuniVenAcq.DdtTesTM;
import it.thera.thip.base.generale.IntegrazioneThipLogis;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.documentoVE.DocumentoVenditaTM;
import it.valvorobica.thip.vendite.documentoVE.brt.YExpDDTBartolini;
import it.valvorobica.thip.vendite.documentoVE.susa.IntegrazioneCorriereSusa;

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
 * 72102    29/08/2025  DSSOF3   Prima stesura
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
		boolean isOk = true;
		try {
			isOk = esportaBolleVersoFtpSusa();
		}catch (Exception e) {
			output.println("Eccezzione non gestita, controllare i log");
			e.printStackTrace(Trace.excStream);
		}
		return isOk;
	}

	@SuppressWarnings("rawtypes")
	protected boolean esportaBolleVersoFtpSusa() {
		List ddts = recuperaListaDDT();
		if(ddts.size() > 0) {
			File file = IntegrazioneCorriereSusa.generaFileTracciato2_3_Susa(ddts);
			if(file != null) {
				
				//..Esporto il file verso l'FTP di Susa
				
				//..In seguito se ho correttamente posizionato il file nell'area FTP flaggo i DDT come processati
				
				Iterator iterDdts = ddts.iterator();
				int rcSave = 0;
				while(rcSave >= 0 && iterDdts.hasNext()) {
					DdtTestataVendita ddt = (DdtTestataVendita) iterDdts.next();
					int rc = IntegrazioneCorriereSusa.flaggaComeProcessatiDocumentiVenditaAppartenentiDDT(ddt.getKey(), YExpDDTBartolini.PROCESSATO);
					if(rc < 0)
						rcSave = rc;
					else
						rcSave += rc;
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<DdtTestataVendita> recuperaListaDDT() {
		SimpleDateFormat formatSQL = new SimpleDateFormat("yyyyMMdd");
		List<DdtTestataVendita> ddts = new ArrayList<DdtTestataVendita>();
		String WHERE_DDT_TES = " " + DdtTesTM.ID_AZIENDA + " = '" + Azienda.getAziendaCorrente() + "' " + "AND "
				+ DdtTesTM.DATA_DDT + " BETWEEN '" + formatSQL.format(getDateFrom()) + "' AND '"
				+ formatSQL.format(getDateTo()) + "' ";
		if (Azienda.getAziendaCorrente().equals("001")) {// se valvorobica
			WHERE_DDT_TES += " AND " + DdtTesTM.VETTORE1 + " = '001682' ";
		} else if (Azienda.getAziendaCorrente().equals("002")) {// se aellebi
			WHERE_DDT_TES += " AND " + DdtTesTM.VETTORE1 + " IN ('000037','000022') ";
		}
		WHERE_DDT_TES += " AND "+DdtTesTM.TIPO_DDT+" = '"+DdtTes.TIPO_DDT_VEN+"' AND FLAG_RIS_UTE_3 != '"+YExpDDTBartolini.PROCESSATO+"' ";
		String ORDER_BY = " " + DdtTesTM.DATA_DDT + " ASC ";
		try {
			ddts = DdtTestataVendita.retrieveList(DdtTestataVendita.class, WHERE_DDT_TES, ORDER_BY, false);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		output.println("Where per DDT : " + WHERE_DDT_TES + " \n, trovati :" + ddts.size() + " DDT");
		return ddts;
	}

	@Override
	protected String getClassAdCollectionName() {
		return "YEXPDDTSusa";
	}

}
