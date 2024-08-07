package it.valvorobica.thip.vendite.documentoVE.brt;

import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.vendite.documentoVE.DdtTestataVendita;
import it.thera.thip.cs.*;
import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

public abstract class EtichetteBartoliniPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {


	/**
	 *  instance
	 */
	private static EtichetteBartolini cInstance;

	/**
	 * Attributo iId
	 */
	protected Integer iId;

	/**
	 * Attributo iEtichettaBase64
	 */
	protected BLOB iEtichettaBase64;

	/**
	 * Attributo iDdtvenditates
	 */
	protected Proxy iDdtvenditates = new Proxy(it.thera.thip.vendite.documentoVE.DdtTestataVendita.class);


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
	 * 05/08/2024    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (EtichetteBartolini)Factory.createObject(EtichetteBartolini.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	/**
	 *  elementWithKey
	 * @param key
	 * @param lockType
	 * @return EtichetteBartolini
	 * @throws SQLException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	public static EtichetteBartolini elementWithKey(String key, int lockType) throws SQLException {
		return (EtichetteBartolini)PersistentObject.elementWithKey(EtichetteBartolini.class, key, lockType);
	}

	/**
	 * EtichetteBartoliniPO
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public EtichetteBartoliniPO() {
		iEtichettaBase64 = new BLOB(this, "ETICHETTA_BASE_64");

		setId(new Integer(0));
		setTipoDDT('1');
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	/**
	 * Valorizza l'attributo. 
	 * @param id
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setId(Integer id) {
		this.iId = id;
		setDirty();
		setOnDB(false);
	}

	/**
	 * Restituisce l'attributo. 
	 * @return Integer
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public Integer getId() {
		return iId;
	}

	/**
	 * Restituisce l'attributo. 
	 * @return BLOB
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public BLOB getEtichettaBase64() {
		return iEtichettaBase64;
	}

	/**
	 * Valorizza l'attributo. 
	 * @param ddtvenditates
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setDdtvenditates(DdtTestataVendita ddtvenditates) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (ddtvenditates != null) {
			idAzienda = KeyHelper.getTokenObjectKey(ddtvenditates.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iDdtvenditates.setObject(ddtvenditates);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	/**
	 * Restituisce l'attributo. 
	 * @return DdtTestataVendita
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public DdtTestataVendita getDdtvenditates() {
		return (DdtTestataVendita)iDdtvenditates.getObject();
	}

	/**
	 * setDdtvenditatesKey
	 * @param key
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setDdtvenditatesKey(String key) {
		String oldObjectKey = getKey();
		iDdtvenditates.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	/**
	 * getDdtvenditatesKey
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public String getDdtvenditatesKey() {
		return iDdtvenditates.getKey();
	}

	/**
	 * Valorizza l'attributo. 
	 * @param idAzienda
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
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
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	/**
	 * Valorizza l'attributo. 
	 * @param annoDDT
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setAnnoDDT(String annoDDT) {
		String key = iDdtvenditates.getKey();
		iDdtvenditates.setKey(KeyHelper.replaceTokenObjectKey(key , 2, annoDDT));
		setDirty();
	}

	/**
	 * Restituisce l'attributo. 
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public String getAnnoDDT() {
		String key = iDdtvenditates.getKey();
		String objAnnoDDT = KeyHelper.getTokenObjectKey(key,2);
		return objAnnoDDT;

	}

	/**
	 * Valorizza l'attributo. 
	 * @param numeroDDT
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setNumeroDDT(String numeroDDT) {
		String key = iDdtvenditates.getKey();
		iDdtvenditates.setKey(KeyHelper.replaceTokenObjectKey(key , 3, numeroDDT));
		setDirty();
	}

	/**
	 * Restituisce l'attributo. 
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public String getNumeroDDT() {
		String key = iDdtvenditates.getKey();
		String objNumeroDDT = KeyHelper.getTokenObjectKey(key,3);
		return objNumeroDDT;

	}

	/**
	 * Valorizza l'attributo. 
	 * @param tipoDDT
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setTipoDDT(char tipoDDT) {
		String key = iDdtvenditates.getKey();
		Character tipoDDTTmp = new Character(tipoDDT);
		iDdtvenditates.setKey(KeyHelper.replaceTokenObjectKey(key , 4, tipoDDTTmp));
		setDirty();
	}

	/**
	 * Restituisce l'attributo. 
	 * @return char
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public char getTipoDDT() {
		String key = iDdtvenditates.getKey();
		String objTipoDDT = KeyHelper.getTokenObjectKey(key,4);
		return KeyHelper.stringToChar(objTipoDDT);
	}

	/**
	 * setEqual
	 * @param obj
	 * @throws CopyException
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		EtichetteBartoliniPO etichetteBartoliniPO = (EtichetteBartoliniPO)obj;
		iEtichettaBase64.setEqual(etichetteBartoliniPO.iEtichettaBase64);
		iDdtvenditates.setEqual(etichetteBartoliniPO.iDdtvenditates);
	}

	/**
	 * checkAll
	 * @param components
	 * @return Vector
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		if (!isOnDB()) {
			setId(new Integer(0));
		}
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
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setId(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 2)));
	}

	/**
	 *  getKey
	 * @return String
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	public String getKey() {
		String idAzienda = getIdAzienda();
		Integer id = getId();
		Object[] keyParts = {idAzienda, id};
		return KeyHelper.buildObjectKey(keyParts);
	}

	/**
	 * isDeletable
	 * @return boolean
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
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
	 * 05/08/2024    Wizard     Codice generato da Wizard
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
	 * 05/08/2024    CodeGen     Codice generato da CodeGenerator
	 *
	 */
	protected TableManager getTableManager() throws SQLException {
		return EtichetteBartoliniTM.getInstance();
	}

	/**
	 * setIdAziendaInternal
	 * @param idAzienda
	 */
	/*
	 * Revisions:
	 * Date          Owner      Description
	 * 05/08/2024    Wizard     Codice generato da Wizard
	 *
	 */
	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iDdtvenditates.getKey();
		iDdtvenditates.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
	}

}

