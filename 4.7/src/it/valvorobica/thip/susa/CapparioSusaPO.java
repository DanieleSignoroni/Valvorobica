package it.valvorobica.thip.susa;

import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import com.thera.thermfw.security.*;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 08/08/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72102    08/08/2025  DSSOF3   Prima stesura
 */

public abstract class CapparioSusaPO extends PersistentObjectDCE implements BusinessObject, Authorizable, Deletable, Conflictable {


	/**
	 *  instance
	 */
	private static CapparioSusa cInstance;

	/**
	 * Attributo iCap
	 */
	protected String iCap;

	/**
	 * Attributo iLocalita
	 */
	protected String iLocalita;

	/**
	 * Attributo iCodiceInstradamento
	 */
	protected String iCodiceInstradamento;

	/**
	 * Attributo iIdProvincia
	 */
	protected String iIdProvincia;


	/**
	 *  retrieveList
	 * @param where
	 * @param orderBy
	 * @param optimistic
	 * @return Vector
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (CapparioSusa)Factory.createObject(CapparioSusa.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	/**
	 *  elementWithKey
	 * @param key
	 * @param lockType
	 * @return CapparioSusa
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	public static CapparioSusa elementWithKey(String key, int lockType) throws SQLException {
		return (CapparioSusa)PersistentObject.elementWithKey(CapparioSusa.class, key, lockType);
	}

	/**
	 * CapparioSusaPO
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public CapparioSusaPO() {

		// TO DO
	}

	/**
	 * Valorizza l'attributo. 
	 * @param cap
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public void setCap(String cap) {
		this.iCap = cap;
		setDirty();
		setOnDB(false);
	}

	/**
	 * Restituisce l'attributo. 
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public String getCap() {
		return iCap;
	}

	/**
	 * Valorizza l'attributo. 
	 * @param localita
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public void setLocalita(String localita) {
		this.iLocalita = localita;
		setDirty();
		setOnDB(false);
	}

	/**
	 * Restituisce l'attributo. 
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public String getLocalita() {
		return iLocalita;
	}

	/**
	 * Valorizza l'attributo. 
	 * @param codiceInstradamento
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public void setCodiceInstradamento(String codiceInstradamento) {
		this.iCodiceInstradamento = codiceInstradamento;
		setDirty();
	}

	/**
	 * Restituisce l'attributo. 
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public String getCodiceInstradamento() {
		return iCodiceInstradamento;
	}

	/**
	 * Valorizza l'attributo. 
	 * @param idProvincia
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public void setIdProvincia(String idProvincia) {
		this.iIdProvincia = idProvincia;
		setDirty();
	}

	/**
	 * Restituisce l'attributo. 
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public String getIdProvincia() {
		return iIdProvincia;
	}

	/**
	 * setEqual
	 * @param obj
	 * @throws CopyException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

	/**
	 * checkAll
	 * @param components
	 * @return Vector
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	/**
	 *  setKey
	 * @param key
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public void setKey(String key) {
		setCap(KeyHelper.getTokenObjectKey(key, 1));
		setLocalita(KeyHelper.getTokenObjectKey(key, 2));
	}

	/**
	 *  getKey
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public String getKey() {
		String cap = getCap();
		String localita = getLocalita();
		Object[] keyParts = {cap, localita};
		return KeyHelper.buildObjectKey(keyParts);
	}

	/**
	 * isDeletable
	 * @return boolean
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public boolean isDeletable() {
		return checkDelete() == null;
	}

	/**
	 * toString
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    Wizard     Codice generato da Wizard
	 *
	 */
	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	/**
	 *  getTableManager
	 * @return TableManager
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 08/08/2025    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	protected TableManager getTableManager() throws SQLException {
		return CapparioSusaTM.getInstance();
	}

}

