package it.valvorobica.thip.base.portal;

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

import it.thera.thip.base.agentiProvv.Agente;
import it.thera.thip.base.azienda.AziendaEstesa;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.dipendente.Dipendente;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.base.profilo.UtenteAzienda;

/**
 * <h1>Softre Solutions</h1>
 * @author Andrea Gatta 24/03/2023
 * <br><br>
 * <b>71008	AGSOF3	24/03/2023</b>	<p>Nuova anagrafica personalizzata per il login nel portale</p>
 * <b>71162	DSSOF3	04/07/2023</b>	<p>Aggiunto fornitore</p>
 */

public abstract class YAziendeUserPortalPO extends PersistentObject implements BusinessObject, Authorizable, Deletable, Child, Conflictable {

	private static YAziendeUserPortal cInstance;

	protected char iTipoUser = 'C';

	protected Proxy iParent = new Proxy(it.valvorobica.thip.base.portal.YUserPortal.class);

	protected Proxy iRelagente = new Proxy(it.thera.thip.base.agentiProvv.Agente.class);

	protected Proxy iRelcliente = new Proxy(it.thera.thip.base.cliente.ClienteVendita.class);

	protected Proxy iReldipendente = new Proxy(it.thera.thip.base.dipendente.Dipendente.class);

	protected Proxy iRelutentepth = new Proxy(it.thera.thip.base.profilo.UtenteAzienda.class);

	protected Proxy iRelazienda = new Proxy(it.thera.thip.base.azienda.AziendaEstesa.class);
	
	protected Proxy iRelfornitore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);
	
	protected boolean iECommerce;

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YAziendeUserPortal)Factory.createObject(YAziendeUserPortal.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YAziendeUserPortal elementWithKey(String key, int lockType) throws SQLException {
		return (YAziendeUserPortal)PersistentObject.elementWithKey(YAziendeUserPortal.class, key, lockType);
	}

	public YAziendeUserPortalPO() {
		setTipoUser('C');
		//    setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setTipoUser(char tipoUser) {
		this.iTipoUser = tipoUser;
		setDirty();
	}

	public char getTipoUser() {
		return iTipoUser;
	}

	public void setParent(YUserPortal parent) {
		this.iParent.setObject(parent);
		setDirty();
		setOnDB(false);
	}

	public YUserPortal getParent() {
		return (YUserPortal)iParent.getObject();
	}

	public void setParentKey(String key) {
		iParent.setKey(key);
		setDirty();
		setOnDB(false);
	}

	public String getParentKey() {
		return iParent.getKey();
	}



	public void setIdUtente(String idUtente) {
		iParent.setKey(idUtente);
		setDirty();
		setOnDB(false);
	}

	public String getIdUtente() {
		String key = iParent.getKey();
		return key;
	}

	public void setRelagente(Agente relagente) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relagente != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relagente.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelagente.setObject(relagente);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public Agente getRelagente() {
		return (Agente)iRelagente.getObject();
	}

	public void setRelagenteKey(String key) {
		String oldObjectKey = getKey();
		iRelagente.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelagenteKey() {
		return iRelagente.getKey();
	}

	public void setRAgente(String rAgente) {
		String key = iRelagente.getKey();
		iRelagente.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rAgente));
		setDirty();
	}

	public String getRAgente() {
		String key = iRelagente.getKey();
		String objRAgente = KeyHelper.getTokenObjectKey(key,2);
		return objRAgente;
	}

	public void setRelcliente(ClienteVendita relcliente) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relcliente != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relcliente.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelcliente.setObject(relcliente);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public ClienteVendita getRelcliente() {
		return (ClienteVendita)iRelcliente.getObject();
	}

	public void setRelclienteKey(String key) {
		String oldObjectKey = getKey();
		iRelcliente.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelclienteKey() {
		return iRelcliente.getKey();
	}

	public void setRCliente(String rCliente) {
		String key = iRelcliente.getKey();
		iRelcliente.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rCliente));
		setDirty();
	}

	public String getRCliente() {
		String key = iRelcliente.getKey();
		String objRCliente = KeyHelper.getTokenObjectKey(key,2);
		return objRCliente;
	}
	
	public void setRelfornitore(FornitoreAcquisto relfORN) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relfORN != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relfORN.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelfornitore.setObject(relfORN);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public FornitoreAcquisto getRelfornitore() {
		return (FornitoreAcquisto)iRelfornitore.getObject();
	}

	public void setRelfornitoreKey(String key) {
		String oldObjectKey = getKey();
		iRelfornitore.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelfornitoreKey() {
		return iRelfornitore.getKey();
	}

	public void setRFornitore(String forn) {
		String key = iRelfornitore.getKey();
		iRelfornitore.setKey(KeyHelper.replaceTokenObjectKey(key , 2, forn));
		setDirty();
	}

	public String getRFornitore() {
		String key = iRelfornitore.getKey();
		String rForn = KeyHelper.getTokenObjectKey(key,2);
		return rForn;
	}

	public void setReldipendente(Dipendente reldipendente) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (reldipendente != null) {
			idAzienda = KeyHelper.getTokenObjectKey(reldipendente.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iReldipendente.setObject(reldipendente);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public Dipendente getReldipendente() {
		return (Dipendente)iReldipendente.getObject();
	}

	public void setReldipendenteKey(String key) {
		String oldObjectKey = getKey();
		iReldipendente.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getReldipendenteKey() {
		return iReldipendente.getKey();
	}

	public void setRDipendente(String rDipendente) {
		String key = iReldipendente.getKey();
		iReldipendente.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rDipendente));
		setDirty();
	}

	public String getRDipendente() {
		String key = iReldipendente.getKey();
		String objRDipendente = KeyHelper.getTokenObjectKey(key,2);
		return objRDipendente;
	}

	public void setRelutentepth(UtenteAzienda relutentepth) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relutentepth != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relutentepth.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelutentepth.setObject(relutentepth);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public UtenteAzienda getRelutentepth() {
		return (UtenteAzienda)iRelutentepth.getObject();
	}

	public void setRelutentepthKey(String key) {
		String oldObjectKey = getKey();
		iRelutentepth.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelutentepthKey() {
		return iRelutentepth.getKey();
	}

	public void setRUtentePth(String rUtentePth) {
		String key = iRelutentepth.getKey();
		iRelutentepth.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rUtentePth));
		setDirty();
	}

	public String getRUtentePth() {
		String key = iRelutentepth.getKey();
		String objRUtentePth = KeyHelper.getTokenObjectKey(key,2);
		return objRUtentePth;
	}

	public void setRelazienda(AziendaEstesa relazienda) {
		setIdAziendaInternal(relazienda.getKey());
		this.iRelazienda.setObject(relazienda);
		setDirty();
		setOnDB(false);
	}

	public AziendaEstesa getRelazienda() {
		return (AziendaEstesa)iRelazienda.getObject();
	}

	public void setRelaziendaKey(String key) {
		iRelazienda.setKey(key);
		setIdAziendaInternal(key);
		setDirty();
		setOnDB(false);
	}

	public String getRelaziendaKey() {
		return iRelazienda.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getIdAzienda() {
		String key = iRelagente.getKey();
		String objIdAzienda = KeyHelper.getTokenObjectKey(key,1);
		return objIdAzienda;

	}
	
	public boolean isECommerce() {
		return iECommerce;
	}

	public void setECommerce(boolean iECommerce) {
		this.iECommerce = iECommerce;
		setDirty();
	}

	@Override
	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YAziendeUserPortalPO yAziendeUserPortalPO = (YAziendeUserPortalPO)obj;
		iParent.setEqual(yAziendeUserPortalPO.iParent);
		iRelagente.setEqual(yAziendeUserPortalPO.iRelagente);
		iRelcliente.setEqual(yAziendeUserPortalPO.iRelcliente);
		iReldipendente.setEqual(yAziendeUserPortalPO.iReldipendente);
		iRelutentepth.setEqual(yAziendeUserPortalPO.iRelutentepth);
		iRelazienda.setEqual(yAziendeUserPortalPO.iRelazienda);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	@Override
	public void setKey(String key) {
		setIdUtente(KeyHelper.getTokenObjectKey(key, 1));
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 2));
	}

	@Override
	public String getKey() {
		String idUtente = getIdUtente();
		String idAzienda = getIdAzienda();
		Object[] keyParts = {idUtente, idAzienda};
		return KeyHelper.buildObjectKey(keyParts);
	}

	@Override
	public boolean isDeletable() {
		return checkDelete() == null;
	}

	@Override
	public String getFatherKey() {
		return getParentKey();
	}

	@Override
	public void setFatherKey(String key) {
		setParentKey(key);
	}

	@Override
	public void setFather(PersistentObject father) {
		iParent.setObject(father);
	}

	@Override
	public String getOrderByClause() {
		return "";
	}

	@Override
	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	@Override
	protected TableManager getTableManager() throws SQLException {
		return YAziendeUserPortalTM.getInstance();
	}

	protected void setIdAziendaInternal(String idAzienda) {
		String key1 = iRelagente.getKey();
		iRelagente.setKey(KeyHelper.replaceTokenObjectKey(key1, 1, idAzienda));
		String key2 = iRelcliente.getKey();
		iRelcliente.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iReldipendente.getKey();
		iReldipendente.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		String key4 = iRelutentepth.getKey();
		iRelutentepth.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		iRelazienda.setKey(idAzienda);
	}

}