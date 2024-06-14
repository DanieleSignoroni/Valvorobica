package it.valvorobica.thip.base;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.OneToMany;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.cs.EntitaAzienda;
import it.thera.thip.magazzino.generalemag.Lotto;
import it.valvorobica.thip.base.matricole.YMatricolaValvo;

/**
 * <h1>Softre Solutions</h1>
 * 
 * @author Daniele Signoroni 14/04/2023 <br>
 *         <br>
 *         <b>71045 DSSOF3 14/04/2023</b>
 *         <p>
 *         Prima stesura.
 *         </p>
 */

public abstract class YMisureValFarfNewPO extends EntitaAzienda
		implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static YMisureValFarfNew cInstance;

	protected String iManStamp;

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

	protected char iSampleDimT = '0';

	protected char iTrattamentoT = '0';

	protected Integer iTempTt;

	protected char iSampleDirR = '0';

	protected char iSampleDimR = '0';

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

	protected Proxy iRelfornitore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);

	protected Proxy iRelarticolo = new Proxy(it.thera.thip.base.articolo.Articolo.class);

	protected Proxy iRelnormativa = new Proxy(it.valvorobica.thip.base.YNormative.class);

	protected Proxy iRelprovenienza = new Proxy(it.valvorobica.thip.base.YProvenienze.class);

	protected Proxy iLotto = new Proxy(it.thera.thip.magazzino.generalemag.Lotto.class);

	protected Proxy iMatricola = new Proxy(YMatricolaValvo.class);

	protected OneToMany iYMisureValvFarfComp = new OneToMany(it.valvorobica.thip.base.YMisureValFarfComp.class, this,
			21, false);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YMisureValFarfNew) Factory.createObject(YMisureValFarfNew.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YMisureValFarfNew elementWithKey(String key, int lockType) throws SQLException {
		return (YMisureValFarfNew) PersistentObject.elementWithKey(YMisureValFarfNew.class, key, lockType);
	}

	public YMisureValFarfNewPO() {
		setSampleDirT('0');
		setSampleDimT('0');
		setTrattamentoT('0');
		setSampleDirR('0');
		setSampleDimR('0');
		setNace('0');
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setManStamp(String manStamp) {
		this.iManStamp = manStamp;
		setDirty();
	}

	public String getManStamp() {
		return iManStamp;
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

	public void setSampleDimT(char sampleDimT) {
		this.iSampleDimT = sampleDimT;
		setDirty();
	}

	public char getSampleDimT() {
		return iSampleDimT;
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

	public void setSampleDimR(char sampleDimR) {
		this.iSampleDimR = sampleDimR;
		setDirty();
	}

	public char getSampleDimR() {
		return iSampleDimR;
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

	public void setRelfornitore(FornitoreAcquisto relfornitore) {
		String idAzienda = getIdAzienda();
		if (relfornitore != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relfornitore.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelfornitore.setObject(relfornitore);
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public FornitoreAcquisto getRelfornitore() {
		return (FornitoreAcquisto) iRelfornitore.getObject();
	}

	public void setRelfornitoreKey(String key) {
		iRelfornitore.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public String getRelfornitoreKey() {
		return iRelfornitore.getKey();
	}

	public void setIdFornitore(String idFornitore) {
		String key = iRelfornitore.getKey();
		iRelfornitore.setKey(KeyHelper.replaceTokenObjectKey(key, 2, idFornitore));
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public String getIdFornitore() {
		String key = iRelfornitore.getKey();
		String objIdFornitore = KeyHelper.getTokenObjectKey(key, 2);
		return objIdFornitore;
	}

	public void setRelarticolo(Articolo relarticolo) {
		String idAzienda = getIdAzienda();
		if (relarticolo != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relarticolo.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idArticolo = getIdArticolo();
		if (relarticolo != null) {
			idArticolo = KeyHelper.getTokenObjectKey(relarticolo.getKey(), 2);
		}
		setIdArticoloInternal(idArticolo);
		this.iRelarticolo.setObject(relarticolo);
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public Articolo getRelarticolo() {
		return (Articolo) iRelarticolo.getObject();
	}

	public void setRelarticoloKey(String key) {
		iRelarticolo.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idArticolo = KeyHelper.getTokenObjectKey(key, 2);
		setIdArticoloInternal(idArticolo);
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public String getRelarticoloKey() {
		return iRelarticolo.getKey();
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
			iYMisureValvFarfComp.setFatherKeyChanged();
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
			iYMisureValvFarfComp.setFatherKeyChanged();
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

	public void setRelprovenienza(YProvenienze relprovenienza) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relprovenienza != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relprovenienza.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelprovenienza.setObject(relprovenienza);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iYMisureValvFarfComp.setFatherKeyChanged();
		}
	}

	public YProvenienze getRelprovenienza() {
		return (YProvenienze) iRelprovenienza.getObject();
	}

	public void setRelprovenienzaKey(String key) {
		String oldObjectKey = getKey();
		iRelprovenienza.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iYMisureValvFarfComp.setFatherKeyChanged();
		}
	}

	public String getRelprovenienzaKey() {
		return iRelprovenienza.getKey();
	}

	public void setRProvenienza(String rProvenienza) {
		String key = iRelprovenienza.getKey();
		iRelprovenienza.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rProvenienza));
		setDirty();
	}

	public String getRProvenienza() {
		String key = iRelprovenienza.getKey();
		String objRProvenienza = KeyHelper.getTokenObjectKey(key, 2);
		return objRProvenienza;
	}

	public void setLotto(Lotto lotto) {
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
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public Lotto getLotto() {
		return (Lotto) iLotto.getObject();
	}

	public void setLottoKey(String key) {
		iLotto.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idArticolo = KeyHelper.getTokenObjectKey(key, 2);
		setIdArticoloInternal(idArticolo);
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public String getLottoKey() {
		return iLotto.getKey();
	}

	// Inizio nuova parte chiave Matricola

	public void setMatricola(YMatricolaValvo relprovenienza) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relprovenienza != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relprovenienza.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iMatricola.setObject(relprovenienza);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iYMisureValvFarfComp.setFatherKeyChanged();
		}
	}

	public YMatricolaValvo getMatricola() {
		return (YMatricolaValvo) iMatricola.getObject();
	}

	public void setMatricolaKey(String key) {
		String oldObjectKey = getKey();
		iMatricola.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iYMisureValvFarfComp.setFatherKeyChanged();
		}
	}

	public String getMatricolaKey() {
		return iMatricola.getKey();
	}

	public void setIdMatricola(String rProvenienza) {
		String key = iMatricola.getKey();
		iMatricola.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rProvenienza));
		setDirty();
	}

	public String getIdMatricola() {
		String key = iMatricola.getKey();
		String objRProvenienza = KeyHelper.getTokenObjectKey(key, 2);
		return objRProvenienza;
	}

	// Fine nuova parte chiave Matricola

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	public void setIdArticolo(String idArticolo) {
		setIdArticoloInternal(idArticolo);
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public String getIdArticolo() {
		String key = iRelarticolo.getKey();
		String objIdArticolo = KeyHelper.getTokenObjectKey(key, 2);
		return objIdArticolo;
	}

	public void setIdLotto(String idLotto) {
		String key = iLotto.getKey();
		iLotto.setKey(KeyHelper.replaceTokenObjectKey(key, 3, idLotto));
		setDirty();
		setOnDB(false);
		iYMisureValvFarfComp.setFatherKeyChanged();
	}

	public String getIdLotto() {
		String key = iLotto.getKey();
		String objIdLotto = KeyHelper.getTokenObjectKey(key, 3);
		return objIdLotto;
	}

	@SuppressWarnings("rawtypes")
	public List getYMisureValvFarfComp() {
		return getYMisureValvFarfCompInternal();
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YMisureValFarfNewPO yMisureValFarfNewPO = (YMisureValFarfNewPO) obj;
		iRelfornitore.setEqual(yMisureValFarfNewPO.iRelfornitore);
		iRelarticolo.setEqual(yMisureValFarfNewPO.iRelarticolo);
		iRelnormativa.setEqual(yMisureValFarfNewPO.iRelnormativa);
		iRelprovenienza.setEqual(yMisureValFarfNewPO.iRelprovenienza);
		iLotto.setEqual(yMisureValFarfNewPO.iLotto);
		iMatricola.setEqual(yMisureValFarfNewPO.iMatricola);
		iYMisureValvFarfComp.setEqual(yMisureValFarfNewPO.iYMisureValvFarfComp);
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
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idFornitore = getIdFornitore();
		String idArticolo = getIdArticolo();
		String idLotto = getIdLotto();
		String idMatricola = getIdMatricola();
		Object[] keyParts = { idAzienda, idFornitore, idArticolo, idLotto, idMatricola };
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public int saveOwnedObjects(int rc) throws SQLException {
		rc = iYMisureValvFarfComp.save(rc);
		return rc;
	}

	public int deleteOwnedObjects() throws SQLException {
		return getYMisureValvFarfCompInternal().delete();
	}

	public boolean initializeOwnedObjects(boolean result) {
		result = iYMisureValvFarfComp.initialize(result);
		return result;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YMisureValFarfNewTM.getInstance();
	}

	protected OneToMany getYMisureValvFarfCompInternal() {
		if (iYMisureValvFarfComp.isNew())
			iYMisureValvFarfComp.retrieve();
		return iYMisureValvFarfComp;
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iRelfornitore.getKey();
		iRelfornitore.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iRelarticolo.getKey();
		iRelarticolo.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		String key4 = iRelnormativa.getKey();
		iRelnormativa.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		String key5 = iRelprovenienza.getKey();
		iRelprovenienza.setKey(KeyHelper.replaceTokenObjectKey(key5, 1, idAzienda));
		String key6 = iLotto.getKey();
		iLotto.setKey(KeyHelper.replaceTokenObjectKey(key6, 1, idAzienda));
		String key7 = iMatricola.getKey();
		iMatricola.setKey(KeyHelper.replaceTokenObjectKey(key7, 1, idAzienda));
	}

	protected void setIdArticoloInternal(String idArticolo) {
		String key1 = iRelarticolo.getKey();
		iRelarticolo.setKey(KeyHelper.replaceTokenObjectKey(key1, 2, idArticolo));
		String key2 = iLotto.getKey();
		iLotto.setKey(KeyHelper.replaceTokenObjectKey(key2, 2, idArticolo));
	}

}
