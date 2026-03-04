package it.valvorobica.thip.base.generale.ws;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.Column;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.magazzino.saldi.SaldoMagLottoClienteTM;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 04/03/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    04/03/2026  DSSOF3   Prima stesura
 */

public class YRetrieveGiacenzeContoDep extends YPortalGenRequestJSON {

	public static final String STMT_ESTRAZ_GIAC = "SELECT\r\n"
			+ "	ID_AZIENDA ,\r\n"
			+ "	ID_MAGAZZINO ,\r\n"
			+ "	ID_ARTICOLO ,\r\n"
			+ "	ID_VERSIONE,\r\n"
			+ "	ID_CONFIG ,\r\n"
			+ "	ID_OPERAZIONE,\r\n"
			+ "	SUM(QTA_GIAC_PRM) AS GIACENZA\r\n"
			+ "FROM\r\n"
			+ "	THIP.SALDI_CLIENTE\r\n"
			+ "WHERE\r\n"
			+ "	ID_AZIENDA = ?\r\n"
			+ "	AND ID_MAGAZZINO = ?\r\n"
			+ "	AND ID_CLIENTE = ?\r\n AND QTA_GIAC_PRM > 0"
			+ "GROUP BY\r\n"
			+ "	ID_AZIENDA ,\r\n"
			+ "	ID_MAGAZZINO ,\r\n"
			+ "	ID_ARTICOLO ,\r\n"
			+ "	ID_VERSIONE,\r\n"
			+ "	ID_CONFIG ,\r\n"
			+ "	ID_OPERAZIONE\r\n"
			+ "";
	public CachedStatement cEstrazioneGiacenze = new CachedStatement(STMT_ESTRAZ_GIAC);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		Map res = super.execute(m);
		res.put("listaGiacenze", retrieveListaGiacenze());
		return res;
	}

	public JSONObject retrieveListaGiacenze() {
		JSONObject result = new JSONObject();
		PreparedStatement ps = null;
		ResultSet rs = null;
		JSONArray records = new JSONArray();
		try {
			Database db = ConnectionManager.getCurrentDatabase();
			ps = cEstrazioneGiacenze.getStatement();
			db.setString(ps, 1, getUserPortalSession().getIdAzienda());
			db.setString(ps, 2, "DEP");
			db.setString(ps, 3, getUserPortalSession().getIdCliente());
			rs = ps.executeQuery();
			while(rs.next()) {
				JSONObject record = new JSONObject();
				record.put("idArticolo", Column.rightTrim(rs.getString(SaldoMagLottoClienteTM.ID_ARTICOLO)));
				record.put("qtaGiac", rs.getBigDecimal("GIACENZA"));
				records.put(record);
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
		}
		for (int i = 0; i < records.length(); i++) {
			JSONObject record = records.getJSONObject(i);
			Articolo art;
			try {
				art = (Articolo) Articolo.elementWithKey(Articolo.class, KeyHelper.buildObjectKey(new String[] {
						getUserPortalSession().getIdAzienda(), record.getString("idArticolo")
				}), PersistentObject.NO_LOCK);
				if(art != null) {
					record.put("descrizione", art.getDescrizioneArticoloNLS().getDescrizione());
					record.put("descrizioneEstesa", art.getDescrizioneArticoloNLS().getDescrizioneEstesa());
					record.put("descrizioneRidotta", art.getDescrizioneArticoloNLS().getDescrizioneRidotta());

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.put("records", records);
		return result;
	}
}
