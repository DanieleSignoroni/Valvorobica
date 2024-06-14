package it.valvorobica.thip.base.matricole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

public class YMatricolaValvo extends YMatricolaValvoPO {

	public static final String STMT_MATCH_ART_MATRICOLA = "SELECT "
			+ ""+YMatchMatricolaArticoloTM.ID_AZIENDA+","+YMatchMatricolaArticoloTM.ID_ARTICOLO+","
			+ ""+YMatchMatricolaArticoloTM.ID_MATRICOLA_DA+","+YMatchMatricolaArticoloTM.ID_MATRICOLA_A+" "
			+ "FROM "+YMatchMatricolaArticoloTM.TABLE_NAME+" "
			+ "WHERE "+YMatchMatricolaArticoloTM.ID_AZIENDA+" = ? "
			+ "AND ? BETWEEN "+YMatchMatricolaArticoloTM.ID_MATRICOLA_DA+" AND "+YMatchMatricolaArticoloTM.ID_MATRICOLA_A+" ";

	public static final CachedStatement cMatchArticoloMatricola = new CachedStatement(STMT_MATCH_ART_MATRICOLA);

	protected YMatchMatricolaArticolo matchArticolo = null;

	public YMatchMatricolaArticolo getMatchArticolo() {
		return matchArticolo;
	}

	public void setMatchArticolo(YMatchMatricolaArticolo matchArticolo) {
		this.matchArticolo = matchArticolo;
	}

	public ErrorMessage checkDelete() {
		return null;
	}

	@Override
	public boolean initializeOwnedObjects(boolean retFather) {
		retFather = super.initializeOwnedObjects(retFather);
		if(retFather) {
			setMatchArticolo(recuperaMatchArticoloDaMatricola(getIdAzienda(),getIdMatricola()));
		}
		return retFather;
	}

	public static YMatchMatricolaArticolo recuperaMatchArticoloDaMatricola(String idAzienda,String idMatricola) {
		ResultSet rs = null;
		try{
			PreparedStatement ps = cMatchArticoloMatricola.getStatement();
			Database db = ConnectionManager.getCurrentDatabase();
			db.setString(ps, 1, idAzienda);
			db.setString(ps, 2, idMatricola);
			rs = ps.executeQuery();
			if (rs.next()){
				return (YMatchMatricolaArticolo) YMatchMatricolaArticolo.elementWithKey(YMatchMatricolaArticolo.class, 
						KeyHelper.buildObjectKey(new String[] {
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4)
						}), PersistentObject.NO_LOCK);
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
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Vector recuperaMatricoleByRange(String idAzienda, String matricolaDa, String matricolaA) {
		Vector matricole = null;
		String where = " "+YMatricolaValvoTM.ID_AZIENDA+" = '"+idAzienda+"' "
				+ "AND "+YMatricolaValvoTM.ID_MATRICOLA+" BETWEEN '"+matricolaDa+"' AND '"+matricolaA+"' ";
		try {
			matricole = YMatricolaValvo.retrieveList(YMatricolaValvo.class, where, "", false);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return matricole;
	}

}
