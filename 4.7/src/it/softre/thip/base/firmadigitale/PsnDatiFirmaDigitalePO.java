package it.softre.thip.base.firmadigitale;

import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.cs.*;
import it.valvorobica.thip.base.firmadigitale.YFirmaDigitVettoriEsclusi;

import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 21/06/2024
 * <br><br>
 * <b>71561	DSSOF3	21/06/2024</b>
 * <p>Prima stesura.<br>
 *  
 * </p>
 * <b>71805	DSSOF3	30/01/2025</b>
 * <p>
 * Aggiunta OneToMany {@link #iListaVettoriEsclusi}.<br>
 * </p>
 */

public abstract class PsnDatiFirmaDigitalePO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static PsnDatiFirmaDigitale cInstance;

	protected char iAbilitata = '0';

	protected OneToMany iAssociazioneDocumenti = new OneToMany(it.softre.thip.base.firmadigitale.AssociazioneTipoDocFirma.class, this, 1, false);

	protected OneToMany iListaVettoriEsclusi = new OneToMany(YFirmaDigitVettoriEsclusi.class, this, 1, false);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (PsnDatiFirmaDigitale)Factory.createObject(PsnDatiFirmaDigitale.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static PsnDatiFirmaDigitale elementWithKey(String key, int lockType) throws SQLException {
		return (PsnDatiFirmaDigitale)PersistentObject.elementWithKey(PsnDatiFirmaDigitale.class, key, lockType);
	}

	public PsnDatiFirmaDigitalePO() {
		setAbilitata('0');
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setAbilitata(char abilitata) {
		this.iAbilitata = abilitata;
		setDirty();
	}

	public char getAbilitata() {
		return iAbilitata;
	}

	public void setIdAzienda(String idAzienda) {
		iAzienda.setKey(idAzienda);
		setDirty();
		setOnDB(false);
		iAssociazioneDocumenti.setFatherKeyChanged();
		iListaVettoriEsclusi.setFatherKeyChanged();
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	@SuppressWarnings("rawtypes")
	public List getAssociazioneDocumenti() {
		return getAssociazioneDocumentiInternal();
	}

	@SuppressWarnings("rawtypes")
	public List getListaVettoriEsclusi() {
		return getListaVettoriEsclusiInternal();
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		PsnDatiFirmaDigitalePO psnDatiFirmaDigitalePO = (PsnDatiFirmaDigitalePO)obj;
		iAssociazioneDocumenti.setEqual(psnDatiFirmaDigitalePO.iAssociazioneDocumenti);
		iListaVettoriEsclusi.setEqual(psnDatiFirmaDigitalePO.iListaVettoriEsclusi);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(key);
	}

	public String getKey() {
		return getIdAzienda();
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public int saveOwnedObjects(int rc) throws SQLException {
		rc = iAssociazioneDocumenti.save(rc);
		rc = iListaVettoriEsclusi.save(rc);
		return rc;
	}

	public int deleteOwnedObjects() throws SQLException {
		int rcDelAssocDocumenti = getAssociazioneDocumentiInternal().delete();
		if(rcDelAssocDocumenti < ErrorCodes.NO_ROWS_UPDATED)
			return rcDelAssocDocumenti;
		int rcDelVettoriEsclusi = getListaVettoriEsclusiInternal().delete();
		if(rcDelVettoriEsclusi < ErrorCodes.NO_ROWS_UPDATED)
			return rcDelVettoriEsclusi;
		return rcDelAssocDocumenti + rcDelVettoriEsclusi;
	}

	public boolean initializeOwnedObjects(boolean result) {
		result = iAssociazioneDocumenti.initialize(result);
		result = iListaVettoriEsclusi.initialize(result);
		return result;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return PsnDatiFirmaDigitaleTM.getInstance();
	}

	protected OneToMany getAssociazioneDocumentiInternal() {
		if (iAssociazioneDocumenti.isNew())
			iAssociazioneDocumenti.retrieve();
		return iAssociazioneDocumenti;
	}

	protected OneToMany getListaVettoriEsclusiInternal() {
		if (iListaVettoriEsclusi.isNew())
			iListaVettoriEsclusi.retrieve();
		return iListaVettoriEsclusi;
	}

}

