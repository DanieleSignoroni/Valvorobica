package it.valvorobica.thip.base.firmadigitale;

import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.Child;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.softre.thip.base.firmadigitale.PsnDatiFirmaDigitale;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.cs.EntitaAzienda;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 30/01/2025
 * <br><br>
 * <b>71805	DSSOF3	30/01/2025</b>
 * <p></p>
 */

public abstract class YFirmaDigitVettoriEsclusiPO extends EntitaAzienda
		implements BusinessObject, Authorizable, Deletable, Conflictable, Child {

	private static YFirmaDigitVettoriEsclusi cInstance;

	protected Proxy iVettore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);
	
	protected Proxy iParent = new Proxy(PsnDatiFirmaDigitale.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YFirmaDigitVettoriEsclusi) Factory.createObject(YFirmaDigitVettoriEsclusi.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YFirmaDigitVettoriEsclusi elementWithKey(String key, int lockType) throws SQLException {
		return (YFirmaDigitVettoriEsclusi) PersistentObject.elementWithKey(YFirmaDigitVettoriEsclusi.class, key,
				lockType);
	}

	public YFirmaDigitVettoriEsclusiPO() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setVettore(FornitoreAcquisto vettore) {
		String idAzienda = getIdAzienda();
		if (vettore != null) {
			idAzienda = KeyHelper.getTokenObjectKey(vettore.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iVettore.setObject(vettore);
		setDirty();
		setOnDB(false);
	}

	public FornitoreAcquisto getVettore() {
		return (FornitoreAcquisto) iVettore.getObject();
	}

	public void setVettoreKey(String key) {
		iVettore.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getVettoreKey() {
		return iVettore.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	public void setIdVettore(String idVettore) {
		String key = iVettore.getKey();
		iVettore.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idVettore));
		setDirty();
		setOnDB(false);
	}

	public String getIdVettore() {
		String key = iVettore.getKey();
		String objIdVettore = KeyHelper.getTokenObjectKey(key, 2);
		return objIdVettore;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YFirmaDigitVettoriEsclusiPO yFirmaDigitVettoriEsclusiPO = (YFirmaDigitVettoriEsclusiPO) obj;
		iVettore.setEqual(yFirmaDigitVettoriEsclusiPO.iVettore);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setIdVettore(KeyHelper.getTokenObjectKey(key, 2));
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idVettore = getIdVettore();
		Object[] keyParts = { idAzienda, idVettore };
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YFirmaDigitVettoriEsclusiTM.getInstance();
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iVettore.getKey();
		iVettore.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
	}
	
	public void setParent(PsnDatiFirmaDigitale parent) {
		setIdAziendaInternal(parent.getKey());
		this.iParent.setObject(parent);
		setDirty();
		setOnDB(false);
	}

	public PsnDatiFirmaDigitale getParent() {
		return (PsnDatiFirmaDigitale)iParent.getObject();
	}

	public void setParentKey(String key) {
		iParent.setKey(key);
		setIdAziendaInternal(key);
		setDirty();
		setOnDB(false);
	}

	public String getParentKey() {
		return iParent.getKey();
	}
	

	public String getFatherKey() {
		return getParentKey();
	}

	public void setFatherKey(String key) {
		setParentKey(key);
	}


	public void setFather(PersistentObject father) {
		iParent.setObject(father);
	}

	public String getOrderByClause() {
		return "";
	}

}
