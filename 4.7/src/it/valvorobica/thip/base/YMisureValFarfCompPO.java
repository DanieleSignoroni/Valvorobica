package it.valvorobica.thip.base;

import java.math.BigDecimal;
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

import it.thera.thip.base.azienda.Azienda;

/**
 * <h1>Softre Solutions</h1>
 * 
 * @author Daniele Signoroni 14/04/2023 <br>
 *         <br>
 *         <b>71045 DSSOF3 14/04/2023</b>
 *         <p>
 *         Prima stesura.
 *         </p>
 *         <b>71057 AGSOF3 26/04/2023</b>
 *         <p>
 *         Corretta chiave.
 *         </p>
 */

public abstract class YMisureValFarfCompPO extends PersistentObject
		implements BusinessObject, Authorizable, Deletable, Child, Conflictable {

	private static YMisureValFarfComp cInstance;

	protected BigDecimal iPercC;

	protected BigDecimal iPercMn;

	protected BigDecimal iPercSi;

	protected BigDecimal iPercP;

	protected BigDecimal iPercS;

	protected BigDecimal iPercNi;

	protected BigDecimal iPercCr;

	protected BigDecimal iPercMo;

	protected BigDecimal iPercTi;

	protected BigDecimal iPercAl;

	protected BigDecimal iPercV;

	protected BigDecimal iPercCu;

	protected BigDecimal iPercN;

	protected BigDecimal iPercNb;

	protected BigDecimal iPercPb;

	protected BigDecimal iPercFe;

	protected BigDecimal iPercCe;

	protected BigDecimal iPercRe;

	protected BigDecimal iPercMg;

	protected BigDecimal iPercSn;

	protected BigDecimal iPercB;

	protected BigDecimal iPercZn;

	protected Integer iRotturaMpa;

	protected Integer iSnervMpa;

	protected BigDecimal iPercAllung;

	protected BigDecimal iPercContraz;

	protected char iSampleDirT = '0';

	protected char iSampleDimR = '0';

	protected char iTrattamentoT = '0';

	protected Integer iTempTt;

	protected char iSampleDirR = '0';

	protected char iSampleDimT = '0';

	protected Integer iTempRe;

	protected Integer iTest1;

	protected Integer iTest2;

	protected Integer iTest3;

	protected char iNace = '0';

	protected String iDurezzaHb;

	protected String iMill;

	protected String iAdditional;

	protected String iTestreport;

	protected String iLottoProd;

	protected String iColataComp;

	protected Proxy iReltipocomponente = new Proxy(it.valvorobica.thip.base.YTp.class);

	protected Proxy iRelnormativa = new Proxy(it.valvorobica.thip.base.YNormative.class);

	protected Proxy iParent = new Proxy(it.valvorobica.thip.base.YMisureValFarfNew.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YMisureValFarfComp) Factory.createObject(YMisureValFarfComp.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YMisureValFarfComp elementWithKey(String key, int lockType) throws SQLException {
		return (YMisureValFarfComp) PersistentObject.elementWithKey(YMisureValFarfComp.class, key, lockType);
	}

	public YMisureValFarfCompPO() {
		setSampleDirT('0');
		setSampleDimR('0');
		setTrattamentoT('0');
		setSampleDirR('0');
		setSampleDimT('0');
		setNace('0');
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setPercC(BigDecimal percC) {
		this.iPercC = percC;
		setDirty();
	}

	public BigDecimal getPercC() {
		return iPercC;
	}

	public void setPercMn(BigDecimal percMn) {
		this.iPercMn = percMn;
		setDirty();
	}

	public BigDecimal getPercMn() {
		return iPercMn;
	}

	public void setPercSi(BigDecimal percSi) {
		this.iPercSi = percSi;
		setDirty();
	}

	public BigDecimal getPercSi() {
		return iPercSi;
	}

	public void setPercP(BigDecimal percP) {
		this.iPercP = percP;
		setDirty();
	}

	public BigDecimal getPercP() {
		return iPercP;
	}

	public void setPercS(BigDecimal percS) {
		this.iPercS = percS;
		setDirty();
	}

	public BigDecimal getPercS() {
		return iPercS;
	}

	public void setPercNi(BigDecimal percNi) {
		this.iPercNi = percNi;
		setDirty();
	}

	public BigDecimal getPercNi() {
		return iPercNi;
	}

	public void setPercCr(BigDecimal percCr) {
		this.iPercCr = percCr;
		setDirty();
	}

	public BigDecimal getPercCr() {
		return iPercCr;
	}

	public void setPercMo(BigDecimal percMo) {
		this.iPercMo = percMo;
		setDirty();
	}

	public BigDecimal getPercMo() {
		return iPercMo;
	}

	public void setPercTi(BigDecimal percTi) {
		this.iPercTi = percTi;
		setDirty();
	}

	public BigDecimal getPercTi() {
		return iPercTi;
	}

	public void setPercAl(BigDecimal percAl) {
		this.iPercAl = percAl;
		setDirty();
	}

	public BigDecimal getPercAl() {
		return iPercAl;
	}

	public void setPercV(BigDecimal percV) {
		this.iPercV = percV;
		setDirty();
	}

	public BigDecimal getPercV() {
		return iPercV;
	}

	public void setPercCu(BigDecimal percCu) {
		this.iPercCu = percCu;
		setDirty();
	}

	public BigDecimal getPercCu() {
		return iPercCu;
	}

	public void setPercN(BigDecimal percN) {
		this.iPercN = percN;
		setDirty();
	}

	public BigDecimal getPercN() {
		return iPercN;
	}

	public void setPercNb(BigDecimal percNb) {
		this.iPercNb = percNb;
		setDirty();
	}

	public BigDecimal getPercNb() {
		return iPercNb;
	}

	public void setPercPb(BigDecimal percPb) {
		this.iPercPb = percPb;
		setDirty();
	}

	public BigDecimal getPercPb() {
		return iPercPb;
	}

	public void setPercFe(BigDecimal percFe) {
		this.iPercFe = percFe;
		setDirty();
	}

	public BigDecimal getPercFe() {
		return iPercFe;
	}

	public void setPercCe(BigDecimal percCe) {
		this.iPercCe = percCe;
		setDirty();
	}

	public BigDecimal getPercCe() {
		return iPercCe;
	}

	public void setPercRe(BigDecimal percRe) {
		this.iPercRe = percRe;
		setDirty();
	}

	public BigDecimal getPercRe() {
		return iPercRe;
	}

	public void setPercMg(BigDecimal percMg) {
		this.iPercMg = percMg;
		setDirty();
	}

	public BigDecimal getPercMg() {
		return iPercMg;
	}

	public void setPercSn(BigDecimal percSn) {
		this.iPercSn = percSn;
		setDirty();
	}

	public BigDecimal getPercSn() {
		return iPercSn;
	}

	public void setPercB(BigDecimal percB) {
		this.iPercB = percB;
		setDirty();
	}

	public BigDecimal getPercB() {
		return iPercB;
	}

	public void setPercZn(BigDecimal percZn) {
		this.iPercZn = percZn;
		setDirty();
	}

	public BigDecimal getPercZn() {
		return iPercZn;
	}

	public void setRotturaMpa(Integer rotturaMpa) {
		this.iRotturaMpa = rotturaMpa;
		setDirty();
	}

	public Integer getRotturaMpa() {
		return iRotturaMpa;
	}

	public void setSnervMpa(Integer snervMpa) {
		this.iSnervMpa = snervMpa;
		setDirty();
	}

	public Integer getSnervMpa() {
		return iSnervMpa;
	}

	public void setPercAllung(BigDecimal percAllung) {
		this.iPercAllung = percAllung;
		setDirty();
	}

	public BigDecimal getPercAllung() {
		return iPercAllung;
	}

	public void setPercContraz(BigDecimal percContraz) {
		this.iPercContraz = percContraz;
		setDirty();
	}

	public BigDecimal getPercContraz() {
		return iPercContraz;
	}

	public void setSampleDirT(char sampleDirT) {
		this.iSampleDirT = sampleDirT;
		setDirty();
	}

	public char getSampleDirT() {
		return iSampleDirT;
	}

	public void setSampleDimR(char sampleDimR) {
		this.iSampleDimR = sampleDimR;
		setDirty();
	}

	public char getSampleDimR() {
		return iSampleDimR;
	}

	public void setTrattamentoT(char trattamentoT) {
		this.iTrattamentoT = trattamentoT;
		setDirty();
	}

	public char getTrattamentoT() {
		return iTrattamentoT;
	}

	public void setTempTt(Integer tempTt) {
		this.iTempTt = tempTt;
		setDirty();
	}

	public Integer getTempTt() {
		return iTempTt;
	}

	public void setSampleDirR(char sampleDirR) {
		this.iSampleDirR = sampleDirR;
		setDirty();
	}

	public char getSampleDirR() {
		return iSampleDirR;
	}

	public void setSampleDimT(char sampleDimT) {
		this.iSampleDimT = sampleDimT;
		setDirty();
	}

	public char getSampleDimT() {
		return iSampleDimT;
	}

	public void setTempRe(Integer tempRe) {
		this.iTempRe = tempRe;
		setDirty();
	}

	public Integer getTempRe() {
		return iTempRe;
	}

	public void setTest1(Integer test1) {
		this.iTest1 = test1;
		setDirty();
	}

	public Integer getTest1() {
		return iTest1;
	}

	public void setTest2(Integer test2) {
		this.iTest2 = test2;
		setDirty();
	}

	public Integer getTest2() {
		return iTest2;
	}

	public void setTest3(Integer test3) {
		this.iTest3 = test3;
		setDirty();
	}

	public Integer getTest3() {
		return iTest3;
	}

	public void setNace(char nace) {
		this.iNace = nace;
		setDirty();
	}

	public char getNace() {
		return iNace;
	}

	public void setDurezzaHb(String durezzaHb) {
		this.iDurezzaHb = durezzaHb;
		setDirty();
	}

	public String getDurezzaHb() {
		return iDurezzaHb;
	}

	public void setMill(String mill) {
		this.iMill = mill;
		setDirty();
	}

	public String getMill() {
		return iMill;
	}

	public void setAdditional(String additional) {
		this.iAdditional = additional;
		setDirty();
	}

	public String getAdditional() {
		return iAdditional;
	}

	public void setTestreport(String testreport) {
		this.iTestreport = testreport;
		setDirty();
	}

	public String getTestreport() {
		return iTestreport;
	}

	public void setLottoProd(String lottoProd) {
		this.iLottoProd = lottoProd;
		setDirty();
	}

	public String getLottoProd() {
		return iLottoProd;
	}

	public void setColataComp(String colataComp) {
		this.iColataComp = colataComp;
		setDirty();
	}

	public String getColataComp() {
		return iColataComp;
	}

	public void setReltipocomponente(YTp reltipocomponente) {
		String idAzienda = getIdAzienda();
		if (reltipocomponente != null) {
			idAzienda = KeyHelper.getTokenObjectKey(reltipocomponente.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iReltipocomponente.setObject(reltipocomponente);
		setDirty();
		setOnDB(false);
	}

	public YTp getReltipocomponente() {
		return (YTp) iReltipocomponente.getObject();
	}

	public void setReltipocomponenteKey(String key) {
		iReltipocomponente.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getReltipocomponenteKey() {
		return iReltipocomponente.getKey();
	}

	public void setIdTpComp(String idTpComp) {
		String key = iReltipocomponente.getKey();
		iReltipocomponente.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idTpComp));
		setDirty();
		setOnDB(false);
	}

	public String getIdTpComp() {
		String key = iReltipocomponente.getKey();
		String objIdTpComp = KeyHelper.getTokenObjectKey(key, 2);
		return objIdTpComp;
	}

	public void setRelnormativa(YNormative relnormativa) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relnormativa != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relnormativa.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelnormativa.setObject(relnormativa);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public YNormative getRelnormativa() {
		return (YNormative) iRelnormativa.getObject();
	}

	public void setRelnormativaKey(String key) {
		String oldObjectKey = getKey();
		iRelnormativa.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelnormativaKey() {
		return iRelnormativa.getKey();
	}

	public void setRNormativa(String rNormativa) {
		String key = iRelnormativa.getKey();
		iRelnormativa.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rNormativa));
		setDirty();
	}

	public String getRNormativa() {
		String key = iRelnormativa.getKey();
		String objRNormativa = KeyHelper.getTokenObjectKey(key, 2);
		return objRNormativa;
	}

	public void setParent(YMisureValFarfNew parent) {
		String idAzienda = getIdAzienda();
		if (parent != null) {
			idAzienda = KeyHelper.getTokenObjectKey(parent.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iParent.setObject(parent);
		setDirty();
		setOnDB(false);
	}

	public YMisureValFarfNew getParent() {
		return (YMisureValFarfNew) iParent.getObject();
	}

	public void setParentKey(String key) {
		iParent.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getParentKey() {
		return iParent.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getIdAzienda() {
		String key = iReltipocomponente.getKey();
		String objIdAzienda = KeyHelper.getTokenObjectKey(key, 1);
		return objIdAzienda;

	}

	public void setIdFornitore(String idFornitore) {
		String key = iParent.getKey();
		iParent.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idFornitore));
		setDirty();
		setOnDB(false);
	}

	public String getIdFornitore() {
		String key = iParent.getKey();
		String objIdFornitore = KeyHelper.getTokenObjectKey(key, 2);
		return objIdFornitore;

	}

	public void setIdArticolo(String idArticolo) {
		String key = iParent.getKey();
		iParent.setKey(KeyHelper.replaceTokenObjectKey(key, 3, idArticolo));
		setDirty();
		setOnDB(false);
	}

	public String getIdArticolo() {
		String key = iParent.getKey();
		String objIdArticolo = KeyHelper.getTokenObjectKey(key, 3);
		return objIdArticolo;

	}

	public void setIdLotto(String idLotto) {
		String key = iParent.getKey();
		iParent.setKey(KeyHelper.replaceTokenObjectKey(key, 4, idLotto));
		setDirty();
		setOnDB(false);
	}

	public String getIdLotto() {
		String key = iParent.getKey();
		String objIdLotto = KeyHelper.getTokenObjectKey(key, 4);
		return objIdLotto;
	}

	public void setIdMatricola(String idMatricola) {
		String key = iParent.getKey();
		iParent.setKey(KeyHelper.replaceTokenObjectKey(key, 5, idMatricola));
		setDirty();
		setOnDB(false);
	}

	public String getIdMatricola() {
		String key = iParent.getKey();
		String objIdLotto = KeyHelper.getTokenObjectKey(key, 5);
		return objIdLotto;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YMisureValFarfCompPO yMisureValFarfCompPO = (YMisureValFarfCompPO) obj;
		iReltipocomponente.setEqual(yMisureValFarfCompPO.iReltipocomponente);
		iRelnormativa.setEqual(yMisureValFarfCompPO.iRelnormativa);
		iParent.setEqual(yMisureValFarfCompPO.iParent);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setIdFornitore(KeyHelper.getTokenObjectKey(key, 2));
		setIdArticolo(KeyHelper.getTokenObjectKey(key, 3));
		setIdLotto(KeyHelper.getTokenObjectKey(key, 4));
		setIdMatricola(KeyHelper.getTokenObjectKey(key, 5));
		setIdTpComp(KeyHelper.getTokenObjectKey(key, 6));
		setColataComp(KeyHelper.getTokenObjectKey(key, 7));// 71034
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idFornitore = getIdFornitore();
		String idArticolo = getIdArticolo();
		String idLotto = getIdLotto();
		String idMatricola = getIdMatricola();
		String idTpComp = getIdTpComp();
		String colataComp = getColataComp();// 71034
		Object[] keyParts = { idAzienda, idFornitore, idArticolo, idLotto, idTpComp, colataComp, idMatricola };
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
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

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YMisureValFarfCompTM.getInstance();
	}

	protected void setIdAziendaInternal(String idAzienda) {
		String key1 = iReltipocomponente.getKey();
		iReltipocomponente.setKey(KeyHelper.replaceTokenObjectKey(key1, 1, idAzienda));
		String key2 = iRelnormativa.getKey();
		iRelnormativa.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iParent.getKey();
		iParent.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
	}

}
