package it.valvorobica.thip.base.vendite.documentoVE;

import java.sql.*;
import com.thera.thermfw.common.*;
import com.thera.thermfw.base.*;

public class EtichetteBartolini extends EtichetteBartoliniPO {

	public ErrorMessage checkDelete() {
		return null;
	}

	public int save() throws SQLException {
		if (!isOnDB()) {
			try {
				setId(new Integer(Numerator.getNextInt("EtichetteBartolini")));
			}
			catch(NumeratorException e) {e.printStackTrace(Trace.excStream);}
		}
		return super.save();
	}

}

