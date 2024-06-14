package it.valvorobica.thip.base.matricole;

import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.EntitaAzienda;

public abstract class YMatricolaValvoPO extends EntitaAzienda
		implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static YMatricolaValvo cInstance;

	protected String iIdMatricola;

	protected String iBody;

	protected String iDisc;

	protected String iShaft;

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YMatricolaValvo) Factory.createObject(YMatricolaValvo.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YMatricolaValvo elementWithKey(String key, int lockType) throws SQLException {
		return (YMatricolaValvo) PersistentObject.elementWithKey(YMatricolaValvo.class, key, lockType);
	}

	public YMatricolaValvoPO() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setIdMatricola(String idMatricola) {
		this.iIdMatricola = idMatricola;
		setDirty();
		setOnDB(false);
	}

	public String getIdMatricola() {
		return iIdMatricola;
	}

	public void setBody(String body) {
		this.iBody = body;
		setDirty();
	}

	public String getBody() {
		return iBody;
	}

	public void setDisc(String disc) {
		this.iDisc = disc;
		setDirty();
	}

	public String getDisc() {
		return iDisc;
	}

	public void setShaft(String shaft) {
		this.iShaft = shaft;
		setDirty();
	}

	public String getShaft() {
		return iShaft;
	}

	public void setIdAzienda(String idAzienda) {
		iAzienda.setKey(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setIdMatricola(KeyHelper.getTokenObjectKey(key, 2));
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idMatricola = getIdMatricola();
		Object[] keyParts = { idAzienda, idMatricola };
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YMatricolaValvoTM.getInstance();
	}

}
