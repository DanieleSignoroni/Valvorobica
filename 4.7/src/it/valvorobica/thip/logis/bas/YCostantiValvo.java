package it.valvorobica.thip.logis.bas;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.thera.thermfw.base.CacheController;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.generale.IntegrazioneThipLogis;
import it.thera.thip.logis.bas.ParametriLogis;
import it.thera.thip.logis.fis.Operatore;
import it.thera.thip.logis.fis.Postazione;
import it.thera.thip.logis.lgb.TestataLista;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Thomas Brescianini<br>
 * Date: 01/01/2022
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72183    24/10/2025  DSSOF3   Aggiunto metodo media mensile
 * 72375	25/02/2026	DSSOF3	 Nuovi metodi di utils.
 */

public class YCostantiValvo {

	protected static final String NULL_VALUE = "-";
	protected static final String DELIM = ";";

	@SuppressWarnings("rawtypes")
	protected static Map valoriParametri = new HashMap();

	public static final String CHIAVE_SEZIONE = "VALVOROBICA";
	public static final String PERCORSO_FILE_XML = "PERCORSO_FILE_XML";

	private static final String STMT_MEDIA_MENSILE_ART = "SELECT MEDIA_MENSILE FROM SOFTRE.MEDIA_MENSILE_VENDITA_ART "
			+ "WHERE ID_AZIENDA = ? "
			+ "AND R_ARTICOLO = ?";
	protected static CachedStatement cMediaMensileArt = new CachedStatement(STMT_MEDIA_MENSILE_ART);

	static {
		try {
			CacheController.registerCache(YCostantiValvo.class, "clearValoriParametri");
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace(Trace.excStream);
		}
	}  

	@SuppressWarnings("unchecked")
	public static String getValoreParametro(String chiave) {
		String valore = (String)valoriParametri.get(chiave);
		if (valore == null) {
			valore = ParametriLogis.valoreDiSezioneChiave(CHIAVE_SEZIONE, chiave);
			if (valore == null)
				valore = NULL_VALUE;
			valoriParametri.put(chiave, valore);
		}
		if (NULL_VALUE.equals(valore)) {
			Trace.println("Valore non trovato per la chiave " + CHIAVE_SEZIONE + "/" + chiave);
			return null;
		}
		return valore;
	}
	public synchronized static void clearValoriParametri() {
		if (valoriParametri != null)
			valoriParametri.clear();
	}

	public static String getFileXmlValvo() {
		String value = getValoreParametro(PERCORSO_FILE_XML);
		return value;
	}

	public static BigDecimal mediaMensileVenditaArticolo(String idAzienda, String idArticolo) {
		BigDecimal media = BigDecimal.ZERO;
		ResultSet rs = null;
		try{
			PreparedStatement ps = cMediaMensileArt.getStatement();
			Database db = ConnectionManager.getCurrentDatabase();
			db.setString(ps, 1, idAzienda);
			db.setString(ps, 2, idArticolo);
			rs = ps.executeQuery();
			if (rs.next()){
				return rs.getBigDecimal(1) != null ? rs.getBigDecimal(1) : media;
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		finally{
			try{
				rs.close();
			}
			catch(SQLException e){
				e.printStackTrace(Trace.excStream);
			}
		}
		return media;
	}

	//72375 <
	
	public static DocumentoVendita documentoVenditaTestataLista(TestataLista testataLista, int lockType) {
		DocumentoVendita dv = (DocumentoVendita) Factory.createObject(DocumentoVendita.class);
		if (testataLista.getCodice().length() > 5) {
			dv.setAnnoDocumento(testataLista.getCodice().substring(1, 5));
			dv.setNumeroDocumento(testataLista.getCodice().substring(5));
		}
		else
			dv.setAnnoDocumento(testataLista.getCodice());
		boolean res = false;
		try {
			res = dv.retrieve(lockType);
		}
		catch (SQLException ex) {
			ex.printStackTrace(Trace.excStream);
		}
		if(!res) {
			dv = null;
		}
		return dv;
	}

	public static TestataLista testataListaDocumentoVendita(DocumentoVendita docVendita, int lockType) {
		TestataLista ttl = (TestataLista) Factory.createObject(TestataLista.class);
		String AnnoDocumento="";
		String NumeroDocumento="";
		ttl.setCodiceSocieta(docVendita.getIdAzienda());
		if (docVendita.getAnnoDocumento() != null &&
				!docVendita.getAnnoDocumento().equals("")) {
			AnnoDocumento = docVendita.getAnnoDocumento();
			if (AnnoDocumento.length() < 4) {
				for(int i=AnnoDocumento.length(); i < 4; i++)
					AnnoDocumento =" "+AnnoDocumento;
			}
		}
		if(docVendita.getNumeroDocumento() != null && !docVendita.getNumeroDocumento().equals("")) {
			NumeroDocumento = docVendita.getNumeroDocumento();
		}
		AnnoDocumento = AnnoDocumento.trim();
		ttl.setCodice(IntegrazioneThipLogis.VENDITA + AnnoDocumento + NumeroDocumento);
		boolean res = false;
		try {
			res = ttl.retrieve(lockType);
		}
		catch (SQLException ex) {
			ex.printStackTrace(Trace.excStream);
		}
		if(!res) {
			ttl = null;
		}
		return ttl;
	}

	public static String codTipoListaTrasferimentoFincantieri() {
		String cod = ParametriLogis.valoreDiSezioneChiave("YTrasferimentoFincan", "CodTipoLista");
		return cod != null ? cod : "";
	}

	public static Operatore getOperatoreGenerico(String codiceMagFisico) {
		try {
			return (Operatore) Operatore.elementWithKey(Operatore.class, 
					KeyHelper.buildObjectKey(new String[] {codiceMagFisico, "GENERICA"}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	public static Postazione getPostazioneNonGestita(String codiceMagFisico) {
		try {
			return (Postazione) Postazione.elementWithKey(Postazione.class, 
					KeyHelper.buildObjectKey(new String[] {codiceMagFisico, "NONGEST"}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}
	
	//72375 >

}