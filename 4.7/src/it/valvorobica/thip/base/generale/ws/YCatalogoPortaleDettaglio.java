package it.valvorobica.thip.base.generale.ws;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thera.thermfw.ad.ClassAD;
import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.gui.cnr.DOList;
import com.thera.thermfw.gui.cnr.DisplayObject;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.Column;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.setting.Criterion;
import com.thera.thermfw.setting.LiteralCriterion;
import com.thera.thermfw.setting.NumericLikeCriterion;
import com.thera.thermfw.setting.Setting;
import com.thera.thermfw.type.ComparisonOperator;
import com.thera.thermfw.type.EqualOperator;
import com.thera.thermfw.type.LikeOperator;
import com.thera.thermfw.type.NumberType;
import com.thera.thermfw.type.Type;
import com.thera.thermfw.web.ServletEnvironment;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.ClienteVendita;
import it.thera.thip.base.partner.Valuta;
import it.thera.thip.magazzino.saldi.SaldoMag;
import it.thera.thip.magazzino.saldi.SaldoMagTM;
import it.thera.thip.vendite.generaleVE.CondizioniDiVendita;
import it.thera.thip.vendite.generaleVE.RicercaCondizioniDiVendita;
import it.valvorobica.thip.base.articolo.YArticolo;
import it.valvorobica.thip.base.portal.YCarrelloPortaleTM;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 22/01/2024
 * <br><br>
 * <b>71392	DSSOF3	22/01/2024</b>    <p>Dettaglio catalogo portale</p>
 * <b>71478	DSSOF3	22/03/2024</b>
 * <p>
 * Ereditare da {@link YPortalGenRequestJSON}.<br>
 * </p>
 * <b>71660	DSSOF3	10/16/2024</b>
 * <p>
 * Mostrare anche righe con prezzo 0, aggiunto ora il bottone contattaci.<br>
 * </p>
 * <b>71699	DSSOF3	14/11/2024</b>
 * <p>
 * Gestione nuovo flag su anagrafica articoli per abilitare gli articoli verso l'e-commerce.<br>
 * Nascondere righe con prezzo 0.<br>
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72114    09/09/2025  DSSOF3   Introdurre gestione giacenza Aellebi
 */

public class YCatalogoPortaleDettaglio extends YPortalGenRequestJSON {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Map execute(Map m) {
		String json = null;
		JsonArray objects = new JsonArray();
		JsonArray headers = new JsonArray();
		// this.user.dettagli_caricati.clear();
		//if (!user.dettagli_caricati.containsKey(this.getIdVista().trim())) { // far si che la vista sia il fullPath e
		// replace
		// ("/",KeyHelper.KEY_SERPARATOR) in
		// modo che se ci sono titoli uguali ma
		// di diverse categorie il programma non
		// cacha sbagliato
		// es Catalogo/Accessori/Raccordo/BX_10
		// Catalogo/Flangia/Valvola/BX_10
		// in questo caso cacho due item diversi, non controllo solo BX_10
		Vector keys = new Vector();
		ClienteVendita cliente = null;
		try {
			cliente = (ClienteVendita) ClienteVendita.elementWithKey(ClienteVendita.class,
					KeyHelper.buildObjectKey(new String[] { this.getCompany(), this.getCliente() }),
					PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List criteria = getNodeCriteria(null);
		try {
			Setting setting = (Setting) Setting.elementWithKey(Setting.class, "4738", PersistentObject.NO_LOCK);
			if (setting != null) {
				DOList doList = (DOList) Factory.createObject(DOList.class);
				doList.setClassADCollection(getClassADCollection());
				for (int i = 0; i < criteria.size(); i++) {
					Criterion criterion = (Criterion) criteria.get(i);
					if (!isEmptyCriterion(criterion))
						setting.addCriterion(criterion);
				}
				doList.initSetting(setting);
				doList.openList(-1, true); //-1 per caricare tutti
				Vector detail = doList.getDetailsDO();
				for (Iterator iterator = detail.iterator(); iterator.hasNext();) {
					DisplayObject obj = (DisplayObject) iterator.next();
					keys.add(obj.getObjectKey());
				}
				doList.closeList();
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			try {
				Articolo art = (Articolo) Articolo.elementWithKey(Articolo.class, key, PersistentObject.NO_LOCK);
				if (art != null) {
					if(art instanceof YArticolo) {
						if(!((YArticolo)art).isECommerce()) {
							continue;
						}
					}
					JsonObject jsonDettaglio = new JsonObject();
					jsonDettaglio.addProperty("IdArticoloPth", art.getIdArticolo());
					jsonDettaglio.addProperty("IdArticolo",
							art.getVecchioArticolo() != null ? art.getVecchioArticolo() : art.getIdArticolo());
					jsonDettaglio.addProperty("DescrEstesa",
							art.getDescrizioneArticoloNLS().getDescrizioneEstesa() != null
							? art.getDescrizioneArticoloNLS().getDescrizioneEstesa()
									: art.getDescrizioneArticoloNLS().getDescrizione());
					BigDecimal disponibilita = getDisponibilita(art,this.getCompany());
					//72114
					BigDecimal disponibilitaAellebi = BigDecimal.ZERO;
					disponibilitaAellebi = getDisponibilita(art,"002"); //.disp di aelleb
					jsonDettaglio.addProperty("GiacenzaAellebi",disponibilitaAellebi);
					//72114
					//if(disponibilita.compareTo(BigDecimal.ZERO) < 0) {
					//	continue;
					//}
					//sottrarre ordinata nel carrello temporanea
					disponibilita = disponibilita.subtract(getOrdinatoCarrello(getUserPortalSession().getIdUtente(), art.getIdArticolo(), false));
					disponibilita = disponibilita.setScale(2, RoundingMode.DOWN); //aggiungere
					jsonDettaglio.addProperty("Giacenza", Integer.valueOf(disponibilita.toBigInteger().toString()));
					CondizioniDiVendita condVen = recuperaCondizioniDiVendita(art, cliente);
					if (condVen != null) {
						BigDecimal prezzo = condVen.getPrezzoAlNettoSconti() != null ? condVen.getPrezzoAlNettoSconti() : BigDecimal.ZERO;
						if(prezzo == null) {
							//continue;
						}
						if(prezzo.compareTo(BigDecimal.ZERO) == 0) {
							continue;
						}
						jsonDettaglio.addProperty("Prezzo",
								prezzo.toString()
								);
					} else {
						jsonDettaglio.addProperty("Prezzo", BigDecimal.ZERO.toString());
					}
					objects.add(jsonDettaglio);
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		json = objects.toString();
		//user.dettagli_caricati.put(this.getIdVista().trim(), json);
		//} else {
		//	json = user.dettagli_caricati.get(this.getIdVista().trim());
		//}
		JsonObject headArt = new JsonObject();
		headArt.addProperty("id", "Articolo");
		headers.add(headArt);
		JsonObject headDescr = new JsonObject();
		headDescr.addProperty("id", "Descrizione");
		headers.add(headDescr);
		//		JsonObject headGiac = new JsonObject();
		//		headGiac.addProperty("id", "Giacenza");
		//		headers.add(headGiac);
		JsonObject headPrice = new JsonObject();
		headPrice.addProperty("id", "Prezzo");
		headers.add(headPrice);
		JsonObject headQtaIns = new JsonObject();
		headQtaIns.addProperty("id", "Quantità");
		headers.add(headQtaIns);
		//72114
		JsonObject semaforo = new JsonObject();
		semaforo.addProperty("id", "Disp.");
		headers.add(semaforo);
		//72114
		JsonObject carello = new JsonObject();
		carello.addProperty("id", "Carrello");
		headers.add(carello);
		m.put("headers", headers.toString());
		m.put("records", json);
		return m;
	}

	public static BigDecimal getOrdinatoCarrello(String idUtente,String idArticolo, boolean contoDep) {
		String select = "SELECT "+YCarrelloPortaleTM.QUANTITA+" FROM " + YCarrelloPortaleTM.TABLE_NAME + " C ";
		String where = "WHERE C."+ YCarrelloPortaleTM.R_UTENTE_PORTALE + " = '" + idUtente + "' AND C."+YCarrelloPortaleTM.R_ARTICOLO+" = '"+idArticolo+"' ";
		if(contoDep) {
			where += " AND C."+YCarrelloPortaleTM.GES_CONTO_DEP+" = '"+Column.TRUE_CHAR+"' ";
		}else {
			where += " AND C."+YCarrelloPortaleTM.GES_CONTO_DEP+" = '"+Column.FALSE_CHAR+"' ";
		}
		ResultSet rs = null;
		try {
			rs = null;
			CachedStatement cs = null;
			cs = new CachedStatement(select+where);
			rs = cs.executeQuery();
			if(rs.next()) {
				return rs.getBigDecimal(YCarrelloPortaleTM.QUANTITA);
			}else {
				return BigDecimal.ZERO;
			}
		} catch (Throwable t) {
			t.printStackTrace(Trace.excStream);
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return BigDecimal.ZERO;
	}

	@SuppressWarnings({ "rawtypes" })
	public static BigDecimal getDisponibilita(Articolo art,String idAzienda) {
		String where = " " + SaldoMagTM.ID_AZIENDA + " = '" + idAzienda + "' " + "AND " + SaldoMagTM.ID_ARTICOLO
				+ " = '" + art.getIdArticolo() + "' AND " + SaldoMagTM.ID_MAGAZZINO + " = '001' ";
		try {
			Vector saldi = SaldoMag.retrieveList(SaldoMag.class, where, "", false);
			if (saldi.size() > 0) {
				SaldoMag saldo = (SaldoMag) saldi.get(0);
				BigDecimal zero = new BigDecimal(0);

				BigDecimal giacenza = saldo.getDatiSaldo().getQtaGiacenzaUMPrim() == null ? zero
						: saldo.getDatiSaldo().getQtaGiacenzaUMPrim();
				BigDecimal ordinatoCliente = saldo.getDatiSaldo().getQtaOrdClienteUMPrim() == null ? zero
						: saldo.getDatiSaldo().getQtaOrdClienteUMPrim();
				BigDecimal ordinatoProduzione = saldo.getDatiSaldo().getQtaOrdProduzioneUMPrim() == null ? zero
						: saldo.getDatiSaldo().getQtaOrdProduzioneUMPrim();
				BigDecimal ordinatoCtoLavoro = saldo.getDatiSaldo().getQtaOrdCtoLavoroUMPrim() == null ? zero
						: saldo.getDatiSaldo().getQtaOrdCtoLavoroUMPrim();

				BigDecimal dsp = giacenza.subtract(ordinatoCliente).subtract(ordinatoProduzione)
						.subtract(ordinatoCtoLavoro);
				return dsp;
				// bisogna anche sottrarre eventuali articoli nel carrello dell'utente
			}
		} catch (/*ClassNotFound*/Exception e) {
			e.printStackTrace(Trace.excStream);
		}/* catch (InstantiationException e) {
			e.printStackTrace(Trace.excStream);
		} catch (IllegalAccessException e) {
			e.printStackTrace(Trace.excStream);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}*/
		return BigDecimal.ZERO;
	}

	public CondizioniDiVendita recuperaCondizioniDiVendita(Articolo articolo, ClienteVendita cliente) {
		RicercaCondizioniDiVendita rcdv = new RicercaCondizioniDiVendita();
		CondizioniDiVendita cdv = null;
		try {
			cdv = rcdv.ricercaCondizioniDiVendita(Azienda.getAziendaCorrente(), // String idAzienda
					cliente.getListino(), // ListinoVendita listino
					cliente, // ClienteVendita cliente
					articolo, // Articolo articolo
					articolo.getUMDefaultVendita(), // UnitaMisura unita
					BigDecimal.ONE, // BigDecimal quantita
					null, // BigDecimal importo
					null, // ModalitaPagamento modalita
					TimeUtils.getCurrentDate(), // java.sql.Date dataValidita
					cliente.getAgente(), // Agente agente DSSOF3 71003
					cliente.getSubAgente(), // Agente subagente DSSOF3 71003
					null, // UnitaMisura unitaMag
					BigDecimal.ONE, // BigDecimal quantitaMag DSSOF3 71067
					(Valuta) Valuta.elementWithKey(Valuta.class, "EUR", 0), // Valuta valuta
					null, // UnitaMisura umSecMag
					null);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return cdv;
	}

	public boolean isEmptyCriterion(Criterion criterion) {
		return criterion.getAttributeName().equals("") && criterion.getOperator() == null
				&& criterion.getValue(0, 0).equals("");
	}

	@SuppressWarnings("rawtypes")
	protected List getNodeCriteria(ServletEnvironment se) {
		String criteriaString = this.getCondizione();
		//Aggiungere criterio su azienda altrimenti tira fuori i record di altre aziende...
		criteriaString += KeyHelper.KEY_SEPARATOR + "ID_AZIENDA" + KeyHelper.KEY_SEPARATOR + "=" + KeyHelper.KEY_SEPARATOR + Azienda.getAziendaCorrente();
		return unpackNodeCriteriaString(se, criteriaString);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List unpackNodeCriteriaString(ServletEnvironment se, String criteriaString) {
		List criteria = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(criteriaString, KeyHelper.KEY_SEPARATOR);
		while (tokenizer.hasMoreTokens()) {
			String columnName = tokenizer.nextToken();
			String operatorString = tokenizer.nextToken();
			String value = tokenizer.nextToken();
			boolean isLikeOperator = operatorString.equalsIgnoreCase("like");
			ClassAD cad = getClassADWithColumnName(se, columnName);
			Criterion currentCriterion = createCriterion(cad, value, isLikeOperator);
			if (currentCriterion != null)
				criteria.add(currentCriterion);
		}
		return criteria;
	}

	@SuppressWarnings("rawtypes")
	public ClassAD getClassADWithColumnName(ServletEnvironment se, String colmunName) {
		Hashtable allAttributes = getClassADCollection().getAllAttributes();
		Enumeration attributesEnum = allAttributes.elements();

		ClassAD target = null;
		while (attributesEnum.hasMoreElements() && target == null) {
			ClassAD currentClassAD = (ClassAD) attributesEnum.nextElement();
			if (currentClassAD.getColumnName() != null && currentClassAD.getColumnName().equals(colmunName))
				target = currentClassAD;
		}
		return target;
	}

	protected Criterion createEmptyCriterion() {
		LiteralCriterion emptyCriterion = new LiteralCriterion();
		emptyCriterion.setAttributeName("");
		emptyCriterion.setOperator(null);
		emptyCriterion.setValue(0, 0, "");
		return emptyCriterion;
	}

	protected Criterion createCriterion(ClassAD cad, String value, boolean likeOperator) {
		LiteralCriterion crit = null;
		Type type = cad.getType();

		if (likeOperator && type instanceof NumberType)
			crit = new NumericLikeCriterion();
		else {
			crit = new LiteralCriterion();
			crit.setCriterionType(Criterion.LITERAL);
		}
		crit.setAttribute(cad);
		crit.setOperator(likeOperator ? (ComparisonOperator) LikeOperator.getInstance()
				: (ComparisonOperator) EqualOperator.getInstance());
		crit.setValue(0, 0, value);
		return crit;
	}

	protected ClassADCollection getClassADCollection() {
		ClassADCollection objectCad = null;
		try {
			objectCad = ClassADCollectionManager.collectionWithName("Articolo");
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace(Trace.excStream);
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace(Trace.excStream);
		}
		return objectCad;
	}

	public String getCliente() {
		return (String) this.getAppParams().get("idCliente");
	}

	public String getCondizione() {
		return (String) this.getAppParams().get("where");
	}

	public String getIdAzienda() {
		return (String) this.getAppParams().get("company");
	}

	public String getIdUtentePortale() {
		return (String) this.getAppParams().get("idUserPortal");
	}

	public String getIdVista() {
		return (String) this.getAppParams().get("idVista").toString().replace("/", KeyHelper.KEY_SEPARATOR);
	}

}
