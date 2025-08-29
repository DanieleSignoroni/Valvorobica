package it.valvorobica.thip.susa;

import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.BLOB;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.thera.thip.cs.PersistentObjectDCE;
import it.thera.thip.logis.fis.TestataUds;

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
 * 72102    29/08/2025  DSSOF3   Prima stesura
 */

public abstract class YEtichettaSusaPO extends PersistentObjectDCE implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static YEtichettaSusa cInstance;

	protected BLOB iEtichettaBase64;

	protected Proxy iTestatauds = new Proxy(it.thera.thip.logis.fis.TestataUds.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YEtichettaSusa) Factory.createObject(YEtichettaSusa.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YEtichettaSusa elementWithKey(String key, int lockType) throws SQLException {
		return (YEtichettaSusa) PersistentObject.elementWithKey(YEtichettaSusa.class, key, lockType);
	}

	public YEtichettaSusaPO() {
		iEtichettaBase64 = new BLOB(this, "ETICHETTA_BASE_64");
	}

	public BLOB getEtichettaBase64() {
		return iEtichettaBase64;
	}

	public void setTestatauds(TestataUds testatauds) {
		this.iTestatauds.setObject(testatauds);
		setDirty();
		setOnDB(false);
	}

	public TestataUds getTestatauds() {
		return (TestataUds) iTestatauds.getObject();
	}

	public void setTestataudsKey(String key) {
		iTestatauds.setKey(key);
		setDirty();
		setOnDB(false);
	}

	public String getTestataudsKey() {
		return iTestatauds.getKey();
	}

	public void setCodUds(String codUds) {
		iTestatauds.setKey(codUds);
		setDirty();
		setOnDB(false);
	}

	public String getCodUds() {
		String key = iTestatauds.getKey();
		return key;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YEtichettaSusaPO yEtichettaSusaPO = (YEtichettaSusaPO) obj;
		iEtichettaBase64.setEqual(yEtichettaSusaPO.iEtichettaBase64);
		iTestatauds.setEqual(yEtichettaSusaPO.iTestatauds);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setCodUds(key);
	}

	public String getKey() {
		return getCodUds();
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YEtichettaSusaTM.getInstance();
	}

}
