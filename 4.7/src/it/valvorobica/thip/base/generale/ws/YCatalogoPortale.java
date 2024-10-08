package it.valvorobica.thip.base.generale.ws;

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
import com.thera.thermfw.ad.ClassRD;
import com.thera.thermfw.base.IniFile;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.pref.ApplicationPreferences;
import com.thera.thermfw.setting.Criterion;
import com.thera.thermfw.setting.LiteralCriterion;
import com.thera.thermfw.setting.NumericLikeCriterion;
import com.thera.thermfw.type.ComparisonOperator;
import com.thera.thermfw.type.EqualOperator;
import com.thera.thermfw.type.LikeOperator;
import com.thera.thermfw.type.NumberType;
import com.thera.thermfw.type.Type;
import com.thera.thermfw.web.ServletEnvironment;

import it.thera.thip.base.catalogo.CatalogoNavigatoreNodo;
import it.thera.thip.base.catalogo.CatalogoVista;
import it.thera.thip.base.catalogo.CatalogoVistaTM;
import it.valvorobica.thip.base.portal.YImgCatalogoPortale;
import it.valvorobica.thip.base.portal.YImgCatalogoPortaleTM;

/**
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Daniele Signoroni 31/08/2023 <br>
 *         <br>
 *         <b>71392 DSSOF3 31/08/2023</b>
 *         <p>
 *         Prima stesura.<br>
 *         L'utente sceglie a video un tipo vista.<br>
 *         Tramite questo tipo vista costruisco un JSON che rappresenta l'albero
 *         del catalogo.<br>
 *         Viene poi dato in pasto al plugin Tree.js e viene creato
 *         l'albero.<br>
 *         </p>
 * <b>71478	DSSOF3	22/03/2024</b>
 * <p>
 * Ereditare da {@link YPortalGenRequestJSON}.<br>
 * </p>
 * <b>71592	DSSOF3	26/07/2024</b>
 * <p>
 * Gestire character escape particolari.<br>
 * </p>
 */

public class YCatalogoPortale extends YPortalGenRequestJSON {

	protected ClassADCollection articoloCollection = null;

	protected ApplicationPreferences appPref = null;

	protected String idCatalogo;

	protected static String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");

	public String getIdCatalogo() {
		return idCatalogo;
	}

	public void setIdCatalogo(String idCatalogo) {
		this.idCatalogo = idCatalogo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Map execute(Map m) {
		try {
			appPref = (ApplicationPreferences) ApplicationPreferences.elementWithKey(ApplicationPreferences.class, "0",
					PersistentObject.NO_LOCK);
			articoloCollection = getClassADCollection();
			JsonArray arr = new JsonArray();
			Vector<CatalogoVista> viste = CatalogoVista.retrieveList(CatalogoVista.class,
					" " + CatalogoVistaTM.ID_AZIENDA + " = '" + getUserPortalSession().getIdAzienda() + "' ", "", false);
			for (Iterator iterator = viste.iterator(); iterator.hasNext();) {
				CatalogoVista catalogoVista = (CatalogoVista) iterator.next();
				YCatalogoNavigatorPortale yc = new YCatalogoNavigatorPortale();
				yc.setCatalogoVista(catalogoVista);
				yc.setIdAzienda(catalogoVista.getIdAzienda());
				CatalogoNavigatoreNodo c = yc.creaAlbero();
				JsonObject jsonObj = costruisciJsonAlbero(c, catalogoVista,null);
				arr.add(jsonObj);
			}
			m.put("catalogo", arr.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace(Trace.excStream);
		} catch (InstantiationException e) {
			e.printStackTrace(Trace.excStream);
		} catch (IllegalAccessException e) {
			e.printStackTrace(Trace.excStream);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return m;
	}

	/**
	 * <h1>Costruzione albero</h1> <br>
	 * Daniele Signoroni 11/08/2023 <br>
	 * <p>
	 * Dato un {@link CatalogoNavigatoreNodo}, con una serie di nodi, vado a
	 * costruire oggetto JSON che verra' poi trasformato in un albero.<br>
	 * </br>
	 * Funzione ricorsiva.
	 * </p>
	 * 
	 * @param node
	 * @param catalogoVista
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JsonObject costruisciJsonAlbero(CatalogoNavigatoreNodo node, CatalogoVista catalogoVista,CatalogoNavigatoreNodo nodoPadre) {
		JsonObject jsonObject = new JsonObject();
		try {
			if (isNodoZero(node)) {
				YImgCatalogoPortale imgCatalogo = getImgForFirstNode(catalogoVista.getIdVistaCatalogo());
				String img = null;
				if(imgCatalogo != null && imgCatalogo.getUrlImg() != null) {
					img = formatUrlImgForPortal(imgCatalogo.getUrlImg());
				}else {
					img = "https://tacm.com/wp-content/uploads/2018/01/no-image-available.jpeg";
				}
				jsonObject.addProperty("text", catalogoVista.getDescrizione().getDescrizione().trim());
				jsonObject.addProperty("img", img);
				if(imgCatalogo != null)
					jsonObject.addProperty("description", imgCatalogo.getDescrizione());
			} else {
				String desc = node.getDescrizione().trim();
				if (desc.contains("/")) {
					desc = desc.replace("/", "-");
				}
				jsonObject.addProperty("text", desc);
				jsonObject.addProperty("where", node.getCondizione());
				if(nodoPadre != null) {
					jsonObject.addProperty("nodo_padre", nodoPadre.getDescrizione().trim());
				}
				List criteria = new ArrayList();
				StringTokenizer tokenizer = new StringTokenizer(node.getCondizione(), KeyHelper.KEY_SEPARATOR);
				while (tokenizer.hasMoreTokens()) {
					String columnName = tokenizer.nextToken();
					String operatorString = tokenizer.nextToken();
					String value = tokenizer.nextToken();
					boolean isLikeOperator = operatorString.equalsIgnoreCase("like");
					ClassAD cad = getClassADWithColumnName(null, columnName);
					Criterion currentCriterion = createCriterion(cad, value, isLikeOperator);
					if (currentCriterion != null)
						criteria.add(currentCriterion);
				}
				Criterion criterion = (Criterion) criteria.get(criteria.size() - 1);
				ClassRD rel = articoloCollection.getRelationFromAttribute(criterion.getAttribute().getAttributeName());
				//String idClassificazione = criterion.getValue(0, 0);
				String tipoClassificazione = rel.getRelatedClassName();
				YImgCatalogoPortale imgCatalogo = getImgForNode(tipoClassificazione, node.getDescrizione().trim());
				String img = null;
				if(imgCatalogo != null && imgCatalogo.getUrlImg() != null) {
					img = formatUrlImgForPortal(imgCatalogo.getUrlImg());
				}else {
					img = "https://tacm.com/wp-content/uploads/2018/01/no-image-available.jpeg";
				}
				jsonObject.addProperty("img", img);
				if(imgCatalogo != null)
					jsonObject.addProperty("description", imgCatalogo.getDescrizione());
			}
			if (!node.getNodiFigli().isEmpty()) {
				JsonArray childrenArray = new JsonArray();
				for (Iterator<CatalogoNavigatoreNodo> iterator = node.getNodiFigli().iterator(); iterator.hasNext();) {
					CatalogoNavigatoreNodo figlio = (CatalogoNavigatoreNodo) iterator.next();
					childrenArray.add(costruisciJsonAlbero(figlio, catalogoVista,node));
				}
				jsonObject.add("children", childrenArray);
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return jsonObject;
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

	@SuppressWarnings("rawtypes")
	public ClassAD getClassADWithColumnName(ServletEnvironment se, String colmunName) {
		Hashtable allAttributes = articoloCollection.getAllAttributes();
		Enumeration attributesEnum = allAttributes.elements();

		ClassAD target = null;
		while (attributesEnum.hasMoreElements() && target == null) {
			ClassAD currentClassAD = (ClassAD) attributesEnum.nextElement();
			if (currentClassAD.getColumnName() != null && currentClassAD.getColumnName().equals(colmunName))
				target = currentClassAD;
		}
		return target;
	}

	@SuppressWarnings("unchecked")
	public YImgCatalogoPortale getImgForNode(String tipoClassificazione, String idClassificazione) {
		if(idClassificazione.contains("'")) {
			idClassificazione = idClassificazione.replace("'", "");
		}
		if(idClassificazione.contains("°")) {
			idClassificazione = idClassificazione.replace("°", "-");
		}
		String where = " " + YImgCatalogoPortaleTM.ID_AZIENDA + " = '" + getUserPortalSession().getIdAzienda() + "' AND "
				+ YImgCatalogoPortaleTM.TIPO_CLASSIFICAZIONE + " = '" + tipoClassificazione + "' AND "
				+ YImgCatalogoPortaleTM.ID_CLASSIFICAZIONE + " = '" + idClassificazione + "' ";
		try {
			Vector<YImgCatalogoPortale> imgs = YImgCatalogoPortale.retrieveList(YImgCatalogoPortale.class, where, "",
					false);
			if (imgs.size() > 0) {
				YImgCatalogoPortale img = imgs.get(0);
				if(img.getUrlImg() != null)
					return img;
				else
					return null;
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public YImgCatalogoPortale getImgForFirstNode(String descrizione) {
		String where = " " + YImgCatalogoPortaleTM.ID_AZIENDA + " = '" + getUserPortalSession().getIdAzienda() + "' AND "
				+ YImgCatalogoPortaleTM.TIPO_CLASSIFICAZIONE + " = '" + YImgCatalogoPortale.CLASSIFICAZIONE_CATALOGO
				+ "' AND " + YImgCatalogoPortaleTM.ID_CLASSIFICAZIONE + " = '" + descrizione + "' ";
		try {
			Vector<YImgCatalogoPortale> imgs = YImgCatalogoPortale.retrieveList(YImgCatalogoPortale.class, where, "",
					false);
			if (imgs.size() > 0) {
				YImgCatalogoPortale img = imgs.get(0);
				if(img.getUrlImg() != null)
					return img;
				else
					return null;
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	public String formatUrlImgForPortal(String urlImg) {
		String userDirectory = System.getProperty("user.dir") + "\\" + "WebContent";
		urlImg = urlImg.replace(userDirectory, "");
		String wb = "/"+IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath")+"" + "/ImmaginiPortale/";
		String fileName = null;
		fileName = urlImg.substring(urlImg.lastIndexOf("\\") + 1, urlImg.length());
		urlImg = wb + fileName;
		return urlImg;
	}

	/**
	 * <h1>Is ultimo nodo ?</h1> <br>
	 * Daniele Signoroni 11/08/2023 <br>
	 * <p>
	 * Dato un {@link CatalogoNavigatoreNodo} capire se e' l'ultimo nodo o se ha
	 * figli.
	 * </p>
	 * 
	 * @param nodo
	 * @return true se e' l'ultimo nodo, false altrimenti
	 */
	public static boolean isUltimoNodo(CatalogoNavigatoreNodo nodo) {
		if (nodo != null) {
			if (nodo.getNodiFigli().size() == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * <h1>Is nodo zero ?</h1> <br>
	 * Daniele Signoroni 11/08/2023 <br>
	 * <p>
	 * Dato un {@link CatalogoNavigatoreNodo} ritorno true se e' il primo nodo,
	 * false se non lo e' o se e' null.
	 * </p>
	 * 
	 * @param nodo
	 * @return true se primo nodo, false altrimenti
	 */
	public static boolean isNodoZero(CatalogoNavigatoreNodo nodo) {
		if (nodo != null) {
			if (nodo.getIdNodo() == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
