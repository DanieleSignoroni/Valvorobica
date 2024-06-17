package it.valvorobica.thip.base.matricole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;

/**
 * Rappresenta un legame tra un range di 'Matricole' e un 'Articolo'.<br>
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 17/06/2024
 * <br><br>
 * <b>71XXX	DSSOF3	17/06/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 */

public class YMatchMatricolaArticolo extends YMatchMatricolaArticoloPO {

	public static final String STMT_CHECK_RANGE_DB = "SELECT * FROM "+YMatchMatricolaArticoloTM.TABLE_NAME+" "
			+ "WHERE "+YMatchMatricolaArticoloTM.ID_AZIENDA+" = ? "
			+ "AND "+YMatchMatricolaArticoloTM.ID_MATRICOLA_DA+" = ? "
			+ "AND "+YMatchMatricolaArticoloTM.ID_MATRICOLA_A+" = ? ";
	
	public static CachedStatement cCheckRangeDb = new CachedStatement(STMT_CHECK_RANGE_DB);

	public ErrorMessage checkDelete() {

		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = super.checkAll(components);
		ErrorMessage checkMatch = checkMatchPerRange();
		if(checkMatch != null)
			errors.add(checkMatch);
		return errors;
	}

	protected ErrorMessage checkMatchPerRange(){
		ErrorMessage em = null;
		if(!isOnDB()) {
			ResultSet rs = null;
		    try{
		      PreparedStatement ps = cCheckRangeDb.getStatement();
		      Database db = ConnectionManager.getCurrentDatabase();
		      db.setString(ps, 1, getIdAzienda());
		      db.setString(ps, 2, getIdMatricolaDa());
		      db.setString(ps, 3, getIdMatricolaA());
		      rs = ps.executeQuery();
		      if (rs.next()){
		        return new ErrorMessage("YVALVO_007", new String[] {rs.getString(YMatchMatricolaArticoloTM.ID_ARTICOLO),getIdMatricolaDa(),getIdMatricolaA()});
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
		}
		return em;
	}

}
