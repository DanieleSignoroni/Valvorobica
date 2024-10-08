package it.valvorobica.thip.base.portal.web;

import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.servlet.BaseServlet;

import it.thera.thip.base.azienda.Azienda;
import it.valvorobica.thip.base.portal.YImgCatalogoPortale;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 22/01/2024
 * <br><br>
 * <b>71392	DSSOF3	22/01/2024</b>    <p>Prima stesura.<br>
 * 										Serve per poter popolare le immagini catalogo portale.<br>
 * 										In base alla relazione scelta faccio la retrieve dei valori e
 * 										aggiungo se ce' la descrizione e ne faccio il display.
 * 										</p>
 */

public class YKeyValuesLoader extends BaseServlet{

	private static final long serialVersionUID = 1L;

	protected void processAction(ServletEnvironment se) throws Exception {
		String classHdrName = getStringParameter(se.getRequest(), "ClassName");
		if (classHdrName != null && !classHdrName.equals("")) {
			fillValues(se.getResponse().getWriter(),classHdrName);
		}
		String key = getStringParameter(se.getRequest(),KEY);
		YImgCatalogoPortale img = (YImgCatalogoPortale)
				YImgCatalogoPortale.elementWithKey(YImgCatalogoPortale.class, key, PersistentObject.NO_LOCK);
		if(img != null) {
			PrintWriter out = new PrintWriter(se.getResponse().getWriter());
			out.println("<script language='JavaScript1.2'>");
			out.println("parent.selectByDescription('"+img.getIdClassificazione()+"');");
			out.println("</script>");
		}
	}

	@SuppressWarnings("rawtypes")
	public void fillValues(Writer writer,String className)
			throws SQLException {
		String classHdr = className.substring(className.lastIndexOf(".")+1, className.length());
		PrintWriter out = new PrintWriter(writer);
		out.println("<script language='JavaScript1.2'>");
		Iterator it = getListDataObjects(className).iterator();
		out.println("parent.addBoAtt('-', '-','BORelation');");
		while (it.hasNext()) {
			String descr = null;
			PersistentObject ogg = (PersistentObject) it.next();
			try {
				BODataCollector boDc = new BODataCollector(classHdr);
				boDc.getBo().setKey(ogg.getKey());
				boDc.retrieve(boDc.getBo().getKey());

				try {
					if(boDc.getComponentManager("Descrizione.Descrizione") != null) {
						descr = boDc.getComponentManager("Descrizione.Descrizione").getComponent().getValue().toString();
						descr = descr.replace("'", "");
					}
				}catch (Exception e) {
					//e.printStackTrace(Trace.excStream);
				}
			}catch (Exception e) {
				//e.printStackTrace(Trace.excStream);
			}
			if(descr != null)
				out.println("parent.addBoAtt('" + KeyHelper.getTokenObjectKey(ogg.getKey(),2) + "', '"+ descr + "','KeyValue');");
			else
				out.println("parent.addBoAtt('" + KeyHelper.getTokenObjectKey(ogg.getKey(),2) + "', '"+ KeyHelper.getTokenObjectKey(ogg.getKey(),2) + "','KeyValue');");
		}
		out.println("parent.document.getElementById('IdClassificazione').value = parent.document.getElementById('KeyValue').selectedOptions[0].innerHTML.trim();");
		out.println("</script>");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PersistentObject> getListDataObjects(String className) {
		List contatti = new ArrayList<PersistentObject>();
		try {
			if(className.equals("Vista catalogo")) {
				className = "it.thera.thip.base.catalogo.CatalogoVista";
			}
			PersistentObject dataObject = (PersistentObject) Factory.createObject(className);
			contatti = PersistentObject.retrieveList(dataObject, " ID_AZIENDA = '"+Azienda.getAziendaCorrente()+"' ", "", false);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contatti;
	}

}
