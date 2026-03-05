package it.valvorobica.thip.base.generale.ws;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.Column;
import com.thera.thermfw.persist.ConnectionDescriptor;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.security.Security;
import com.thera.thermfw.web.SessionEnvironment;

import it.valvorobica.thip.base.portal.YCarrelloPortale;
import it.valvorobica.thip.base.portal.YCarrelloPortaleTM;

/**
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Daniele Signoroni 31/08/2023 <br>
 *         <br>
 *         <b>71392 DSSOF3 31/08/2023</b>
 *         <p>
 *         Prima stesura.<br>
 *         WebService usato per inserire un'articolo nel carrello: <br>
 *         </br>
 *         {@value YCarrelloPortaleTM#TABLE_NAME} <br>
 *         </p>
 * <b>71478	DSSOF3	22/03/2024</b>
 * <p>
 * Ereditare da {@link YPortalGenRequestJSON}.<br>
 * </p>
 */
public class YAggiungiAlCarrello extends YPortalGenRequestJSON {

	private boolean iGestioneContoDeposito;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		if(this.getAppParams().get("contoDeposito") != null && ((Boolean)this.getAppParams().get("contoDeposito"))) {
			iGestioneContoDeposito = true;
		}
		String idArticolo = (String) this.getAppParams().get("articolo");
		if (idArticolo == null || idArticolo.isEmpty())
			m.put("errors", "Articolo vuoto");
		String quantita = (String) this.getAppParams().get("quantita");
		if (quantita == null)
			m.put("errors", "Quantita vuota");
		String idUtente = (String) this.getAppParams().get("idUtente");
		if (idUtente == null)
			m.put("errors", "Problemi con l'utente");
		String idAzienda = (String) this.getAppParams().get("idAzienda");
		String idCliente = (String) this.getAppParams().get("idCliente");
		if (idCliente == null)
			m.put("errors", "Cliente non selezionato, riloggarsi");
		if (m.get("errors") == null)
			addToCart(idArticolo, idAzienda, idUtente, quantita, idCliente, m);
		int nrItemsCarrello = getNumeroItemsCarrelloUtente(idAzienda, idUtente);
		m.put("nrItemsCart", nrItemsCarrello);
		return m;
	}

	@SuppressWarnings({ "rawtypes" })
	protected void addToCart(String idArticolo, String idAzienda, String idUtente, String quantita, String idCliente,
			Map m) {
		boolean isopen = false;
		Object[] info = SessionEnvironment.getDBInfoFromIniFile();
		String dbName = (String) info[0];
		try {
			if (!Security.isCurrentDatabaseSetted()) {
				Security.setCurrentDatabase(dbName, null);
			}
			Security.openDefaultConnection();
			isopen = true;
			ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
			YCarrelloPortale item = (YCarrelloPortale) Factory.createObject(YCarrelloPortale.class);
			item.setIdAzienda(idAzienda);
			item.setRUtentePortale(idUtente);
			item.setRCliente(idCliente);
			item.setQuantita(new BigDecimal(quantita));
			item.setRArticolo(idArticolo);
			if(iGestioneContoDeposito)
				item.setGestioneContoDep(true);
			if (item.save() >= 0) {
				cd.commit();
			} else {
				cd.rollback();
			}
		} catch (Throwable t) {
			t.printStackTrace(Trace.excStream);
		} finally {
			if (isopen) {
				Security.closeDefaultConnection();
			}
		}
	}

	public int getNumeroItemsCarrelloUtente(String idAzienda, String idUtente) {
		String select = "SELECT COUNT(*) FROM " + YCarrelloPortaleTM.TABLE_NAME + " C ";
		String where = "WHERE C." + YCarrelloPortaleTM.ID_AZIENDA + " = '" + idAzienda + "'" + " AND C."
				+ YCarrelloPortaleTM.R_UTENTE_PORTALE + " = '" + idUtente + "' ";
		if(iGestioneContoDeposito) {
			where += " AND C."+YCarrelloPortaleTM.GES_CONTO_DEP+" = '"+Column.TRUE_CHAR+"' ";
		}else {
			where += " AND C."+YCarrelloPortaleTM.GES_CONTO_DEP+" = '"+Column.FALSE_CHAR+"' ";
		}
		ResultSet rs = null;
		CachedStatement cs = new CachedStatement(select+where);
		try {
			rs = cs.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (Throwable t) {
			t.printStackTrace(Trace.excStream);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return 0;
	}

}
