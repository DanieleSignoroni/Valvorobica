package it.valvorobica.thip.vendite.documentoVE.susa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;

import it.thera.thip.base.articolo.ArticoloBase;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.DdtTes;
import it.thera.thip.base.comuniVenAcq.DdtTesTM;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.documentoVE.DocumentoVenditaTM;
import it.valvorobica.thip.vendite.documentoVE.brt.EstremiBartolini;
import it.valvorobica.thip.vendite.documentoVE.brt.YExpDDTBartolini;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 11/04/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 71XXX    11/04/2025  DSSOF3   Prima stesura
 */

public class IntegrazioneCorriereSusa {

	public static final String RES = "it.valvorobica.thip.vendite.documentoVE.susa.resources.IntegrazioneCorriereSusa";

	protected String userID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public IntegrazioneCorriereSusa() {
		String RES_FILE_COMPANY = RES + "_" + Azienda.getAziendaCorrente();
		setUserID(ResourceLoader.getString(RES_FILE_COMPANY, "userID"));
	}

	public static IntegrazioneCorriereSusa newInstance() {
		return (IntegrazioneCorriereSusa) Factory.createObject(IntegrazioneCorriereSusa.class);
	}

	public String generaRecordBO(DdtTes ddt) {
		char[] buffer = new char[550];
		java.util.Arrays.fill(buffer, ' ');
		StringBuilder bo = new StringBuilder(new String(buffer));

		// Compila campi con valori di esempio (metti i tuoi dati reali)
		setString(bo, "BO", 1, 2);                          // Tipo record (BO)
		setString(bo, "90", 3, 4);                          // WDTFILMIT
		setString(bo, getUserID(), 5, 10);                  // WDTCODMIT+WDTCONMIT
		if(ddt.getIdAzienda().equals("002") && ddt.getIdVettore1().equals("000037")) {
			setString(bo, "1", 11, 13);
		}else if(ddt.getIdAzienda().equals("002") && ddt.getIdVettore1().equals("000022")) {
			setString(bo, "100", 11, 13);
		}else {
			setString(bo, "1", 11, 13);
		}
		setString(bo, ddt.getIdNumeroDdt(), 14, 33);                   // WDTRIFDOC
		setString(bo, formatDataSusa(ddt.getDataDdt()), 34, 47);            // WDTDATRIF (Data DDT esempio)
		setString(bo, "", 48, 67);              // WDTRIFCOM
		setString(bo, ddt.getIdCliente(), 68, 82);                    // WDTRI2DES
		setString(bo, ddt.getRagioneSocDen(), 83, 117);   // WDTRA2DES
		setString(bo, "", 118, 152);                        // WDTRA2DE2 (vuoto se non usato)
		setString(bo, ddt.getIndirizzoDen(), 153, 187);           // WDTIN2DES
		setString(bo, ddt.getLocalitaDen(), 188, 222);          // WDTLO2DES
		setString(bo, ddt.getCapDen(), 223, 229);                   // WDTCA2DES
		setString(bo, ddt.getIdProvinciaDen(), 230, 231);                      // WDTPR2DES
		setString(bo, "", 232, 251);                        // WDTCO2FIS (opzionale)
		setNumeric(bo, formatNumeric(ddt.getPesoLordo(), 8, 3), 255, 262);               // WDTPESLOR
		setNumeric(bo, formatNumeric(new BigDecimal("0.01"), 8, 3), 263, 269);                // WDTVOLUME
		setNumeric(bo, String.valueOf(ddt.getNumeroColli()), 270, 274);              // WDTCOLBOL (esempio: 2 colli)
		setNumeric(bo, "000000000000000", 275, 289);        // WDTIMPASS
		setString(bo, "EURO", 290, 293);                    // WDTVALVME
		setNumeric(bo, formatNumeric(BigDecimal.ZERO, 15, 3), 294, 308);      // WDTIMPVME (valore merce)
		setNumeric(bo, "000000000000000", 309, 323);        // WDTIMPVMA (assicurazione)

		// Campo opzionale (ad esempio prenotazione telefonica richiesta)
		setString(bo, "0", 326, 326);                       // BLCNTGIA (richiesta telefonica)

		/*setString(bo, "2", 330, 330);                       // WDTTIPCON (tipo consegna)
		setString(bo, "20250406", 331, 338);                // WDTDATCO1
		setString(bo, "20250406", 339, 346);                // WDTDATCO2
		setString(bo, "080000", 347, 352);                  // WDTORACO1 (ora da)
		setString(bo, "120000", 353, 358);                  // WDTORACO2 (ora a)

		setString(bo, "SAB", 359, 361);                  // WDTGGCHI1 (esempio)
		setString(bo, "DOM", 362, 364);                  // WDTGGCHI2 (esempio)
		setString(bo, "ZONA NORD 1", 365, 394);          // WDTDESZOC (descrizione zona)
		setString(bo, "PRODOTTO001", 395, 409);          // WDTRIFPRO (codice prodotto)
		setNumeric(bo, "005", 410, 412);                 // WDTBANCAL (numero bancali)
		setString(bo, "", 413, 447); // WDTRAGVET
		setString(bo, "", 448, 454);                // WDTCAPVET
		setString(bo, "", 455, 456);                   // WDTPROVET
		setString(bo, "", 457, 460);                   // WDTNAZVET
		 */

		EstremiBartolini estremiBrt = EstremiBartolini.estremiBartolini();
		String email = "";
		boolean isIntercompany = YExpDDTBartolini.isDdtIntercompany(ddt);
		if(!isIntercompany)
			email = estremiBrt.getIndirizzoMailDaDestinatarioDigitale(ddt.getIdCliente(), ddt.getIdAzienda());
		else
			email = estremiBrt.getIndirizzoMailDaDestinatarioDigitaleIntercompany(ddt.getRagioneSocDen(),ddt.getIdAzienda());
		setString(bo, email, 461, 550);     // DSTEMLADD (tracking mail)

		// Ritorna la stringa completa
		return bo.toString();
	}

	public String generaRecordNO(DdtTes ddt) {
		// Crea una riga di 77 spazi
		char[] buffer = new char[77];
		java.util.Arrays.fill(buffer, ' ');
		StringBuilder no = new StringBuilder(new String(buffer));

		// Fissa "NO" come tipo record
		setString(no, "NO", 1, 2);

		// Inserisce il riferimento DDT (uguale a WDTRIFDOC del BO)
		setString(no, ddt.getIdNumeroDdt(), 3, 17);

		// Inserisce la nota (massimo 60 caratteri)
		setString(no, "", 18, 77);

		return no.toString();
	}

	protected void setString(StringBuilder builder, String value, int start, int end) {
		int idx = start - 1;
		int len = end - start + 1;
		value = String.format("%-" + len + "s", value); // Allineato a sinistra
		builder.replace(idx, idx + len, value.substring(0, len));
	}

	protected void setNumeric(StringBuilder builder, String value, int start, int end) {
		int idx = start - 1;
		int len = end - start + 1;
		value = String.format("%" + len + "s", value).replace(' ', '0'); // Allineato a destra con 0
		builder.replace(idx, idx + len, value.substring(0, len));
	}

	protected String formatNumeric(BigDecimal value, int totalLength, int decimalPlaces) {
		// Moltiplica per 10^decimalPlaces per rimuovere la virgola
		BigDecimal scaled = value.movePointRight(decimalPlaces);
		// Rimuove eventuali simboli decimali residui e formattalo con zeri a sinistra
		String raw = scaled.setScale(0, RoundingMode.HALF_UP).toPlainString();
		return String.format("%0" + totalLength + "d", Long.parseLong(raw));
	}

	protected String formatDataSusa(Date sqlDate) {
		// sqlDate non contiene orario, solo data, quindi imposta manualmente un orario predefinito se necessario
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		// Qui imposto l'orario alle 00:00:00 per default (o metti quello che vuoi)
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());

		// Formatta nel modo richiesto
		return sdf.format(utilDate);
	}

	public static int flaggaComeProcessatiDocumentiVenditaAppartenentiDDT(String keyDDT, char flagRisUte4) {
		String[] parts = KeyHelper.unpackObjectKey(keyDDT);

		List<String> keysDocVenTes = new ArrayList<String>();

		String stmt = "SELECT  " + "ID_AZIENDA ,ID_ANNO_DOC ,ID_NUMERO_DOC  " + "FROM THIP.DOC_VEN_TES "
				+ "WHERE ID_AZIENDA = '" + parts[0] + "' " + "AND ANNO_BOLLA = '" + parts[1] + "' "
				+ "AND NUMERO_BOLLA = '" + parts[2] + "' " + "AND TIPO_BOLLA = '" + parts[3] + "' ";

		ResultSet rs = null;
		CachedStatement cs = null;

		try {
			cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			while(rs.next()) {
				keysDocVenTes.add(KeyHelper.buildObjectKey(new String[] {
						rs.getString(DocumentoVenditaTM.ID_AZIENDA),
						rs.getString(DocumentoVenditaTM.ID_ANNO_DOC),
						rs.getString(DocumentoVenditaTM.ID_NUMERO_DOC)
				}));
			}
			rs.close();
			cs.free();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			for(String key : keysDocVenTes) {

				parts = KeyHelper.unpackObjectKey(key);

				String updtStmt = "UPDATE THIP.DOC_VEN_TES SET FLAG_RIS_UTE_3 = '"+flagRisUte4+"' "
						+","+DocumentoVenditaTM.STRINGA_RIS_UTE_1+" = '"+sdf.format(TimeUtils.getCurrentTimestamp())+"' "
						+ "WHERE ID_AZIENDA = '"+parts[0]+"' AND ID_ANNO_DOC = '"+parts[1]+"' AND ID_NUMERO_DOC = '"+parts[2]+"' ";

				cs = new CachedStatement(updtStmt);

				int res = cs.executeUpdate();

				if(res < 0) {
					return ArticoloBase.UPDATE_KO;
				}

				cs.free();
			}

			parts = KeyHelper.unpackObjectKey(keyDDT);


			String stmtUpdtDDT = "UPDATE THIP.DDT_TES "
					+ "SET FLAG_RIS_UTE_3 = '"+flagRisUte4+"'"
					+ ","+DdtTesTM.STRINGA_RIS_UTE_1+" = '"+sdf.format(TimeUtils.getCurrentTimestamp())+"'" 
					+ "WHERE ID_AZIENDA = '"+parts[0]+"' "
					+ "AND ID_ANNO_DDT = '"+parts[1]+"' "
					+ "AND ID_NUMERO_DDT = '"+parts[2]+"' "
					+ "AND TIPO_DDT = '"+parts[3]+"' ";

			cs = new CachedStatement(stmtUpdtDDT);

			int res = cs.executeUpdate();

			if(res < 0) {
				return ArticoloBase.UPDATE_KO;
			}

		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}

		return ArticoloBase.UPDATE_OK;
	}

	public static boolean isDocumentoVenditaVettoreSusa(DocumentoVendita dv) {
		String idAzienda = dv.getIdAzienda();
		if (idAzienda.equals("001") && !"001682".equals(dv.getIdVettore1())) {
			return false;
		} else if (idAzienda.equals("002") && (!"000037".equals(dv.getIdVettore1())
				&& !"000022".equals(dv.getIdVettore1()))) {
			return false;
		}
		return true;
	}

}
