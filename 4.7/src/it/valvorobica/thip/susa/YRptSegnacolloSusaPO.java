package it.valvorobica.thip.susa;

import java.math.BigDecimal;
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

public abstract class YRptSegnacolloSusaPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static YRptSegnacolloSusa cInstance;

	protected Integer iBatchJobId;

	protected Integer iReportNr;

	protected Integer iRigaJobId;

	protected String iCodiceLinea;

	protected String iCodiceZona;

	protected String iCodUds;

	protected String iVarchar01;

	protected String iVarchar02;

	protected String iVarchar03;

	protected String iVarchar04;

	protected String iVarchar05;

	protected boolean iFlag01 = false;

	protected boolean iFlag02 = false;

	protected boolean iFlag03 = false;

	protected boolean iFlag04 = false;

	protected boolean iFlag05 = false;

	protected BigDecimal iDecimal01;

	protected BigDecimal iDecimal02;

	protected BigDecimal iDecimal03;

	protected BigDecimal iDecimal04;

	protected BigDecimal iDecimal05;

	protected Integer iInteger01;

	protected Integer iInteger02;

	protected Integer iInteger03;

	protected Integer iInteger04;

	protected Integer iInteger05;

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YRptSegnacolloSusa) Factory.createObject(YRptSegnacolloSusa.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YRptSegnacolloSusa elementWithKey(String key, int lockType) throws SQLException {
		return (YRptSegnacolloSusa) PersistentObject.elementWithKey(YRptSegnacolloSusa.class, key, lockType);
	}

	public YRptSegnacolloSusaPO() {
		setFlag01(false);
		setFlag02(false);
		setFlag03(false);
		setFlag04(false);
		setFlag05(false);
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setBatchJobId(Integer batchJobId) {
		this.iBatchJobId = batchJobId;
		setDirty();
		setOnDB(false);
	}

	public Integer getBatchJobId() {
		return iBatchJobId;
	}

	public void setReportNr(Integer reportNr) {
		this.iReportNr = reportNr;
		setDirty();
		setOnDB(false);
	}

	public Integer getReportNr() {
		return iReportNr;
	}

	public void setRigaJobId(Integer rigaJobId) {
		this.iRigaJobId = rigaJobId;
		setDirty();
		setOnDB(false);
	}

	public Integer getRigaJobId() {
		return iRigaJobId;
	}

	public void setCodiceLinea(String codiceLinea) {
		this.iCodiceLinea = codiceLinea;
		setDirty();
	}

	public String getCodiceLinea() {
		return iCodiceLinea;
	}

	public void setCodiceZona(String codiceZona) {
		this.iCodiceZona = codiceZona;
		setDirty();
	}

	public String getCodiceZona() {
		return iCodiceZona;
	}

	public void setCodUds(String codUds) {
		this.iCodUds = codUds;
		setDirty();
	}

	public String getCodUds() {
		return iCodUds;
	}

	public void setVarchar01(String varchar01) {
		this.iVarchar01 = varchar01;
		setDirty();
	}

	public String getVarchar01() {
		return iVarchar01;
	}

	public void setVarchar02(String varchar02) {
		this.iVarchar02 = varchar02;
		setDirty();
	}

	public String getVarchar02() {
		return iVarchar02;
	}

	public void setVarchar03(String varchar03) {
		this.iVarchar03 = varchar03;
		setDirty();
	}

	public String getVarchar03() {
		return iVarchar03;
	}

	public void setVarchar04(String varchar04) {
		this.iVarchar04 = varchar04;
		setDirty();
	}

	public String getVarchar04() {
		return iVarchar04;
	}

	public void setVarchar05(String varchar05) {
		this.iVarchar05 = varchar05;
		setDirty();
	}

	public String getVarchar05() {
		return iVarchar05;
	}

	public void setFlag01(boolean flag01) {
		this.iFlag01 = flag01;
		setDirty();
	}

	public boolean getFlag01() {
		return iFlag01;
	}

	public void setFlag02(boolean flag02) {
		this.iFlag02 = flag02;
		setDirty();
	}

	public boolean getFlag02() {
		return iFlag02;
	}

	public void setFlag03(boolean flag03) {
		this.iFlag03 = flag03;
		setDirty();
	}

	public boolean getFlag03() {
		return iFlag03;
	}

	public void setFlag04(boolean flag04) {
		this.iFlag04 = flag04;
		setDirty();
	}

	public boolean getFlag04() {
		return iFlag04;
	}

	public void setFlag05(boolean flag05) {
		this.iFlag05 = flag05;
		setDirty();
	}

	public boolean getFlag05() {
		return iFlag05;
	}

	public void setDecimal01(BigDecimal decimal01) {
		this.iDecimal01 = decimal01;
		setDirty();
	}

	public BigDecimal getDecimal01() {
		return iDecimal01;
	}

	public void setDecimal02(BigDecimal decimal02) {
		this.iDecimal02 = decimal02;
		setDirty();
	}

	public BigDecimal getDecimal02() {
		return iDecimal02;
	}

	public void setDecimal03(BigDecimal decimal03) {
		this.iDecimal03 = decimal03;
		setDirty();
	}

	public BigDecimal getDecimal03() {
		return iDecimal03;
	}

	public void setDecimal04(BigDecimal decimal04) {
		this.iDecimal04 = decimal04;
		setDirty();
	}

	public BigDecimal getDecimal04() {
		return iDecimal04;
	}

	public void setDecimal05(BigDecimal decimal05) {
		this.iDecimal05 = decimal05;
		setDirty();
	}

	public BigDecimal getDecimal05() {
		return iDecimal05;
	}

	public void setInteger01(Integer integer01) {
		this.iInteger01 = integer01;
		setDirty();
	}

	public Integer getInteger01() {
		return iInteger01;
	}

	public void setInteger02(Integer integer02) {
		this.iInteger02 = integer02;
		setDirty();
	}

	public Integer getInteger02() {
		return iInteger02;
	}

	public void setInteger03(Integer integer03) {
		this.iInteger03 = integer03;
		setDirty();
	}

	public Integer getInteger03() {
		return iInteger03;
	}

	public void setInteger04(Integer integer04) {
		this.iInteger04 = integer04;
		setDirty();
	}

	public Integer getInteger04() {
		return iInteger04;
	}

	public void setInteger05(Integer integer05) {
		this.iInteger05 = integer05;
		setDirty();
	}

	public Integer getInteger05() {
		return iInteger05;
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
		setBatchJobId(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 2)));
		setReportNr(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 3)));
		setRigaJobId(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 4)));
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		Integer batchJobId = getBatchJobId();
		Integer reportNr = getReportNr();
		Integer rigaJobId = getRigaJobId();
		Object[] keyParts = { idAzienda, batchJobId, reportNr, rigaJobId };
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YRptSegnacolloSusaTM.getInstance();
	}

}
