package it.valvorobica.thip.susa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.Column;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.Factory;

/**
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
 * 72XXX    29/08/2025  DSSOF3   Prima stesura
 */

public class AlgoritmoInstradamentoSusa {

	private static final String STMT_CODICE_INSTRADAMENTO = "SELECT "+CapparioSusaTM.CODICE_INSTRADAMENTO+" "
			+ "FROM "+CapparioSusaTM.TABLE_NAME+" "
			+ "WHERE "+CapparioSusaTM.CAP+" = ? "
			+ "AND "+CapparioSusaTM.LOCALITA+" = ? "
			+ "AND "+CapparioSusaTM.ID_PROVINCIA+" = ? ";
	public static CachedStatement cSelectCodiceInstradamento = new CachedStatement(STMT_CODICE_INSTRADAMENTO);

	private static final String STMT_CODICE_LINEA_CODICE_ZONA = "SELECT "+RoutesSusaTM.CODICE_LINEA+","+RoutesSusaTM.CODICE_ZONA+" "
			+ "FROM "+RoutesSusaTM.TABLE_NAME+" "
			+ "WHERE "+RoutesSusaTM.DESCRIZIONE_SERVZIO+" = ? "
			+ "AND "+RoutesSusaTM.FILIALE_PARTENZA+" ? "
			+ "AND "+RoutesSusaTM.CODICE_INSTRADAMENTO+" = ? ";
	public static CachedStatement cSelectCodiceLineaCodiceZona = new CachedStatement(STMT_CODICE_LINEA_CODICE_ZONA);

	private static AlgoritmoInstradamentoSusa instance;

	public static AlgoritmoInstradamentoSusa getInstance() {
		if(instance == null) {
			instance = (AlgoritmoInstradamentoSusa) Factory.createObject(AlgoritmoInstradamentoSusa.class);
		}
		return instance;
	}

	/**
	 * Tramite i parametri passati vado nella tabella {@value CapparioSusaTM#TABLE_NAME} e determino il codice di instradamento.
	 * @param CAP
	 * @param localita
	 * @param idProvincia
	 * @return CodiceInstradamento
	 */
	public String selezionaCodiceInstradamento(String CAP, String localita, String idProvincia) {
		String codiceInstradamento = null;
		Database db = ConnectionManager.getCurrentDatabase();
		ResultSet rs = null;
		try {
			PreparedStatement ps = cSelectCodiceInstradamento.getStatement();
			db.setString(ps, 1, CAP);
			db.setString(ps, 2, localita);
			db.setString(ps, 3, idProvincia);
			rs = ps.executeQuery();
			if(rs.next()) {
				codiceInstradamento = Column.rightTrim(rs.getString(CapparioSusaTM.CODICE_INSTRADAMENTO));
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return codiceInstradamento;
	}

	public String[] determinaCodiceLineaCodiceZona(String CAP, String localita, String idProvincia) {
		String codiceInstradamento = selezionaCodiceInstradamento(CAP, localita, idProvincia);
		if(codiceInstradamento != null && !codiceInstradamento.isEmpty()) {
			String servizio = ""; //dati da Susa
			String filiale = ""; //dati da Susa
			return determinaCodiceLineaCodiceZonaInternal(codiceInstradamento, servizio, filiale);
		}else {
			return new String[] {"XXXXX","XXX"};
		}
	}

	public String[] determinaCodiceLineaCodiceZonaInternal(String codiceInstradamento, String servizio, String filiale) {
		String[] codiceLineaCodiceZona = new String[2];

		//..Cerco i codice linea e codice zona con i dati passati
		codiceLineaCodiceZona = selezionaCodiceLineaCodiceZona(codiceInstradamento, servizio, filiale);
		if(codiceLineaCodiceZona[0] != null && codiceLineaCodiceZona[1] != null) {
			return codiceLineaCodiceZona;
		}

		//..Se non trovo la prima occorrenza cerco con standard/filiale/instradamento
		codiceLineaCodiceZona = selezionaCodiceLineaCodiceZona(codiceInstradamento, "Standard", filiale);
		if(codiceLineaCodiceZona[0] != null && codiceLineaCodiceZona[1] != null) {
			return codiceLineaCodiceZona;
		}

		//..Se non trovo la seconda occorrenza cerco con standard/00/instradamento
		codiceLineaCodiceZona = selezionaCodiceLineaCodiceZona(codiceInstradamento, "Standard", "00");
		if(codiceLineaCodiceZona[0] != null && codiceLineaCodiceZona[1] != null) {
			return codiceLineaCodiceZona;
		}

		//..Se non ho trovato nulla ritorno codice linea e zona di default
		codiceLineaCodiceZona[0] = "XXXXX";
		codiceLineaCodiceZona[1] = "XXX";

		return codiceLineaCodiceZona;
	}

	public String[] selezionaCodiceLineaCodiceZona(String codiceInstradamento, String servizio, String filiale) {
		String[] codiceLineaCodiceZona = new String[2];
		Database db = ConnectionManager.getCurrentDatabase();
		ResultSet rs = null;
		try {
			PreparedStatement ps = cSelectCodiceLineaCodiceZona.getStatement();
			db.setString(ps, 1, servizio);
			db.setString(ps, 2, filiale);
			db.setString(ps, 3, codiceInstradamento);
			rs = ps.executeQuery();
			if(rs.next()) {
				codiceLineaCodiceZona[0] = Column.rightTrim(rs.getString(RoutesSusaTM.CODICE_LINEA));
				codiceLineaCodiceZona[1] = Column.rightTrim(rs.getString(RoutesSusaTM.CODICE_ZONA));
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return codiceLineaCodiceZona;
	}
}
