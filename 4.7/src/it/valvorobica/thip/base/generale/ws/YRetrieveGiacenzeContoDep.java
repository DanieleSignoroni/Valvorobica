package it.valvorobica.thip.base.generale.ws;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;

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
			+ "	AND ID_CLIENTE = ?\r\n"
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

	@SuppressWarnings("rawtypes")
	public List retrieveListaGiacenze() {
		List giacenze = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Database db = ConnectionManager.getCurrentDatabase();
			ps = cEstrazioneGiacenze.getStatement();
			db.setString(ps, 1, getUserPortalSession().getIdAzienda());
			db.setString(ps, 2, "DEP");
			db.setString(ps, 2, getUserPortalSession().getIdCliente());
			rs = ps.executeQuery();
			while(rs.next()) {

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
		return giacenze;
	}
}
