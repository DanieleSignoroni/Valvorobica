
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
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.EntitaAzienda;
import it.thera.thip.magazzino.generalemag.Lotto;

public abstract class YMatchMatricolaArticoloPO extends EntitaAzienda
		implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static YMatchMatricolaArticolo cInstance;

	protected Proxy iArticolo = new Proxy(it.thera.thip.base.articolo.Articolo.class);

	protected Proxy iMatricolainizio = new Proxy(it.valvorobica.thip.base.matricole.YMatricolaValvo.class);

	protected Proxy iMatricolafine = new Proxy(it.valvorobica.thip.base.matricole.YMatricolaValvo.class);

	protected Proxy iLotto = new Proxy(it.thera.thip.magazzino.generalemag.Lotto.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YMatchMatricolaArticolo) Factory.createObject(YMatchMatricolaArticolo.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YMatchMatricolaArticolo elementWithKey(String key, int lockType) throws SQLException {
		return (YMatchMatricolaArticolo) PersistentObject.elementWithKey(YMatchMatricolaArticolo.class, key, lockType);
	}

	public YMatchMatricolaArticoloPO() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setArticolo(Articolo articolo) {
		String idAzienda = getIdAzienda();
		if (articolo != null) {
			idAzienda = KeyHelper.getTokenObjectKey(articolo.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idArticolo = getIdArticolo();
		if (articolo != null) {
			idArticolo = KeyHelper.getTokenObjectKey(articolo.getKey(), 2);
		}
		setIdArticoloInternal(idArticolo);
		this.iArticolo.setObject(articolo);
		setDirty();
		setOnDB(false);
	}

	public Articolo getArticolo() {
		return (Articolo) iArticolo.getObject();
	}

	public void setArticoloKey(String key) {
		iArticolo.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idArticolo = KeyHelper.getTokenObjectKey(key, 2);
		setIdArticoloInternal(idArticolo);
		setDirty();
		setOnDB(false);
	}

	public String getArticoloKey() {
		return iArticolo.getKey();
	}

	public void setMatricolainizio(YMatricolaValvo matricolainizio) {
		String idAzienda = getIdAzienda();
		if (matricolainizio != null) {
			idAzienda = KeyHelper.getTokenObjectKey(matricolainizio.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iMatricolainizio.setObject(matricolainizio);
		setDirty();
		setOnDB(false);
	}

	public YMatricolaValvo getMatricolainizio() {
		return (YMatricolaValvo) iMatricolainizio.getObject();
	}

	public void setMatricolainizioKey(String key) {
		iMatricolainizio.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getMatricolainizioKey() {
		return iMatricolainizio.getKey();
	}

	public void setIdMatricolaDa(String idMatricolaDa) {
		String key = iMatricolainizio.getKey();
		iMatricolainizio.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idMatricolaDa));
		setDirty();
		setOnDB(false);
	}

	public String getIdMatricolaDa() {
		String key = iMatricolainizio.getKey();
		String objIdMatricolaDa = KeyHelper.getTokenObjectKey(key, 2);
		return objIdMatricolaDa;
	}

	public void setMatricolafine(YMatricolaValvo matricolafine) {
		String idAzienda = getIdAzienda();
		if (matricolafine != null) {
			idAzienda = KeyHelper.getTokenObjectKey(matricolafine.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iMatricolafine.setObject(matricolafine);
		setDirty();
		setOnDB(false);
	}

	public YMatricolaValvo getMatricolafine() {
		return (YMatricolaValvo) iMatricolafine.getObject();
	}

	public void setMatricolafineKey(String key) {
		iMatricolafine.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getMatricolafineKey() {
		return iMatricolafine.getKey();
	}

	public void setIdMatricolaA(String idMatricolaA) {
		String key = iMatricolafine.getKey();
		iMatricolafine.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idMatricolaA));
		setDirty();
		setOnDB(false);
	}

	public String getIdMatricolaA() {
		String key = iMatricolafine.getKey();
		String objIdMatricolaA = KeyHelper.getTokenObjectKey(key, 2);
		return objIdMatricolaA;
	}

	public void setLotto(Lotto lotto) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (lotto != null) {
			idAzienda = KeyHelper.getTokenObjectKey(lotto.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idArticolo = getIdArticolo();
		if (lotto != null) {
			idArticolo = KeyHelper.getTokenObjectKey(lotto.getKey(), 2);
		}
		setIdArticoloInternal(idArticolo);
		this.iLotto.setObject(lotto);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public Lotto getLotto() {
		return (Lotto) iLotto.getObject();
	}

	public void setLottoKey(String key) {
		String oldObjectKey = getKey();
		iLotto.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idArticolo = KeyHelper.getTokenObjectKey(key, 2);
		setIdArticoloInternal(idArticolo);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getLottoKey() {
		return iLotto.getKey();
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

	public void setIdArticolo(String idArticolo) {
		setIdArticoloInternal(idArticolo);
		setDirty();
		setOnDB(false);
	}

	public String getIdArticolo() {
		String key = iArticolo.getKey();
		String objIdArticolo = KeyHelper.getTokenObjectKey(key, 2);
		return objIdArticolo;
	}

	public void setIdLotto(String idLotto) {
		String key = iLotto.getKey();
		iLotto.setKey(KeyHelper.replaceTokenObjectKey(key, 3, idLotto));
		setDirty();
	}

	public String getIdLotto() {
		String key = iLotto.getKey();
		String objIdLotto = KeyHelper.getTokenObjectKey(key, 3);
		return objIdLotto;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YMatchMatricolaArticoloPO yMatchMatricolaArticoloPO = (YMatchMatricolaArticoloPO) obj;
		iArticolo.setEqual(yMatchMatricolaArticoloPO.iArticolo);
		iMatricolainizio.setEqual(yMatchMatricolaArticoloPO.iMatricolainizio);
		iMatricolafine.setEqual(yMatchMatricolaArticoloPO.iMatricolafine);
		iLotto.setEqual(yMatchMatricolaArticoloPO.iLotto);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setIdArticolo(KeyHelper.getTokenObjectKey(key, 2));
		setIdMatricolaDa(KeyHelper.getTokenObjectKey(key, 3));
		setIdMatricolaA(KeyHelper.getTokenObjectKey(key, 4));
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idArticolo = getIdArticolo();
		String idMatricolaDa = getIdMatricolaDa();
		String idMatricolaA = getIdMatricolaA();
		Object[] keyParts = { idAzienda, idArticolo, idMatricolaDa, idMatricolaA };
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YMatchMatricolaArticoloTM.getInstance();
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iArticolo.getKey();
		iArticolo.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iMatricolainizio.getKey();
		iMatricolainizio.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		String key4 = iMatricolafine.getKey();
		iMatricolafine.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		String key5 = iLotto.getKey();
		iLotto.setKey(KeyHelper.replaceTokenObjectKey(key5, 1, idAzienda));
	}

	protected void setIdArticoloInternal(String idArticolo) {
		String key1 = iArticolo.getKey();
		iArticolo.setKey(KeyHelper.replaceTokenObjectKey(key1, 2, idArticolo));
		String key2 = iLotto.getKey();
		iLotto.setKey(KeyHelper.replaceTokenObjectKey(key2, 2, idArticolo));
	}

}
