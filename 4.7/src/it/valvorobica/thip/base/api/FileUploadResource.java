package it.valvorobica.thip.base.api;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.annotation.security.PermitAll;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONObject;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.ApiInfo;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.collector.BaseBOComponentManager;
import com.thera.thermfw.gui.cnr.DOList;
import com.thera.thermfw.gui.cnr.DisplayObject;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.rs.BaseResource;
import com.thera.thermfw.setting.Setting;
import com.thera.thermfw.type.DateType;
import com.thera.thermfw.type.DecimalType;
import com.thera.thermfw.type.Type;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebDOList;
import com.thera.thermfw.web.WebDataCollector;

import it.thera.thip.base.documentoDgt.AssociazioneEntitaTDgtRiga;
import it.thera.thip.base.documentoDgt.AssociazioneEntitaTpDgt;
import it.thera.thip.base.documentoDgt.DocumentoDgtOggetto;
import it.thera.thip.base.documentoDgt.TipoDocumentoAtt;
import it.thera.thip.base.documentoDgt.TipoDocumentoDigitale;
import it.thera.thip.base.documentoDgt.web.DocumentoDigitaleDataCollector;
import it.thera.thip.base.generale.PersDatiGen;
import it.thera.thip.magazzino.generalemag.web.LottoDataCollector;
import it.valvorobica.thip.base.documentoDgt.YDocumentoDigitale;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 14/02/2024
 * <br><br>
 * <b>XXXXX	DSSOF3	14/02/2024</b>
 * <p>Prima stesura.<br>
 * Permette la creazione di un documento digitale con allegato.<br>
 * L'utente deve passare file, lotto, articolo, azienda e tipo certificato.<br>
 * Se il file passato e' in PDF viene convertito in JPEG.<br>
 * Tutto questo poi sara' utilizzato nella stampa dei certificati, questo il perche' della conversione.<br>
 * La conversione viene fatta tramite classi di un jar esterno, ApachePDFBOX.<br>
 * </p>
 */

@Path("portale/upload")
@PermitAll
public class FileUploadResource extends BaseResource{

	@Context HttpServletRequest request;
	@Context HttpServletResponse response;

	public static String PREFIX = "Classificazione";
	public static String PREFIX_DATA = "Data";
	public static String PREFIX_VAL = "Valore";
	public static String PREFIX_NOTE = "Note";
	public static String PREFIX_CLOB = "Clob";


	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition metaData,
			@FormDataParam("token") String token,
			@FormDataParam("tipoCertificato") String tipoCertificato,
			@FormDataParam("IdLotto") String lotto,
			@FormDataParam("IdArticolo") String articolo,
			@FormDataParam("IdAzienda") String idAzienda,
			@FormDataParam("LottoAcquisto") String lottoAcquisto,
			@FormDataParam("IdFornitore") String idFornitore
			) {
		if (fileInputStream != null && metaData != null) {
			String fileName = metaData.getFileName();
			String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			if(!(extension.contains("pdf") || extension.contains("jpg"))) {
				return buildResponse(Status.BAD_REQUEST, "L'estensione di file "+extension+" non e' supportata");
			}
			DocumentoDgtOggetto oggetto = null;
			DocumentoDigitaleDataCollector docDaSettare = (DocumentoDigitaleDataCollector) createDataCollector("DocumentoDigitale");
			YDocumentoDigitale docDgt = (YDocumentoDigitale) Factory.createObject(YDocumentoDigitale.class);
			docDgt.setIdAzienda(idAzienda);
			docDgt.setTipoDocDgt(recuperaTipoDocumentoDigitale(idAzienda, "CERT_F02"));
			docDaSettare.setBo(docDgt);
			LottoDataCollector lottoBODC = (LottoDataCollector) Factory.createObject(LottoDataCollector.class);
			lottoBODC.initialize("Lotto", true, PersistentObject.NO_LOCK);
			lottoBODC.retrieve(KeyHelper.buildObjectKey(new String[] {idAzienda,articolo,lotto}));
			AssociazioneEntitaTpDgt assEntitaTipoDocDgt = recuperaAssociazioneEntitaTpDgt(idAzienda, docDgt.getIdTipoDocDgt(), "Lotto");
			if (assEntitaTipoDocDgt != null){
				Iterator<AssociazioneEntitaTDgtRiga> it = assEntitaTipoDocDgt.getAssociazioneEntitaRighe().iterator();
				while (it.hasNext()){
					AssociazioneEntitaTDgtRiga associaRiga = (AssociazioneEntitaTDgtRiga) it.next();
					valorizzaClassificazione(docDaSettare, docDgt.getTipoDocDgt(), associaRiga, lottoBODC);
				}
			}else {
				return buildResponse(Status.INTERNAL_SERVER_ERROR,"Nessuna associazione tipo entita definta per Lotto");
			}
			docDaSettare.set("Descr", "Test");
			docDaSettare.set("DescrRidotta", "Test");
			docDaSettare.setAutoCheck(true);
			int rc = docDaSettare.save();
			if(rc != BODataCollector.OK) {
				return buildResponse(Status.INTERNAL_SERVER_ERROR,docDaSettare.messages().toString());
			}else {
				docDgt = (YDocumentoDigitale) docDaSettare.getBo();
				try {
					if(docDgt.retrieve()) {
						if(extension.contains("pdf")) {
							fileName = fileName.replace("pdf", "jpg");
						}
						oggetto = (DocumentoDgtOggetto) Factory.createObject(DocumentoDgtOggetto.class);
						oggetto.setDocumentoDgt(docDgt);
						oggetto.setFilename(fileName);
						oggetto.getDescrizione().setDescrizione(fileName.length() > 35 ? fileName.substring(0,35) : fileName);
						oggetto.getDescrizione().setDescrizioneRidotta(fileName.length() > 15 ? fileName.substring(0,15) : fileName);
						oggetto.setIdTipoMime("JPEG");
						if(oggetto.save() >= 0) {
							ConnectionManager.commit();
							char memDocDgt = PersDatiGen.getCurrentPersDatiGen().getMemorizzazioneDocDgt();
							if (memDocDgt == PersDatiGen.MEM_DOC_DGT_SU_FILESYSTEM) {
								if(extension.contains("pdf")) {
									String nomeFile = oggetto.percorsoSalvataggioFile() + File.separator + oggetto.creaNomeFile();
									int DPI = 300;
									ImageType IMAGE_TYPE = ImageType.RGB;
									ImageWriter writer = null;
									byte[] bytes = IOUtils.toByteArray(fileInputStream);
									try (PDDocument document = Loader.loadPDF(bytes)) {

										try {
											writer = ImageIO.getImageWritersByFormatName("jpg").next();

											ImageWriteParam params = writer.getDefaultWriteParam();
											params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
											params.setCompressionQuality(0.6f);

											PDFRenderer pdfRenderer = new PDFRenderer(document);

											for (int page = 0; page < document.getNumberOfPages(); page++) {
												BufferedImage bim = pdfRenderer.renderImageWithDPI(page, DPI, IMAGE_TYPE);
												ImageOutputStream outputStream = new FileImageOutputStream(new File(nomeFile));
												writer.setOutput(outputStream);

												writer.write(null, new IIOImage(bim, null, null), params);
											}
										}catch (Exception e) {
											e.printStackTrace();
										}
									} catch (IOException e1) {
										e1.printStackTrace(Trace.excStream);
										return buildResponse(Status.INTERNAL_SERVER_ERROR,"Problemi nel salvataggio del file : "+e1.getMessage());
									}
								}else {
									FileOutputStream wStream = null; 
									try {
										String nomeFile = oggetto.percorsoSalvataggioFile() + File.separator + oggetto.creaNomeFile();

										wStream = new FileOutputStream(new File(nomeFile));
										this.getBytes(fileInputStream, wStream);
									}
									catch (Exception e) {
										e.printStackTrace(Trace.excStream);
									}                 
									finally {  
										if (wStream != null) {
											try {
												wStream.close();
											} catch (IOException e) {
												e.printStackTrace(Trace.excStream);
											}
										}
									}
								}
							}
						}else {
							ConnectionManager.rollback();
							if(docDgt.delete() > 0) {
								ConnectionManager.commit();
							}
							return buildResponse(Status.INTERNAL_SERVER_ERROR,"Errore nel salvataggio dell'oggetto, riprovare");
						}
					}
				} catch (SQLException | IOException e) {
					e.printStackTrace(Trace.excStream);
					return buildResponse(Status.INTERNAL_SERVER_ERROR,"Problemi con il documento digitale appena creato, chiave = "+docDgt.getKey());
				}
			}
			return buildResponse(Status.OK,oggetto.getKey());
		} else {
			return buildResponse(Status.BAD_REQUEST,"Il file e' vuoto");
		}
	}

	protected byte[] getBytes(InputStream input, OutputStream output) {
		byte[] bytes = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			int byteRead;			 
			while ((byteRead = input.read()) != -1) {
				baos.write(byteRead);
			}
			baos.flush();
			bytes = baos.toByteArray();
			output.write(bytes);            
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		finally {
			try {
				if (baos != null) {
					baos.close();
				}
			}
			catch (Throwable t) {}
		}
		return bytes;
	}

	protected static AssociazioneEntitaTpDgt recuperaAssociazioneEntitaTpDgt(String idAzienda,String tipoDocDgt,String entita) {
		try {
			return (AssociazioneEntitaTpDgt) AssociazioneEntitaTpDgt.elementWithKey(AssociazioneEntitaTpDgt.class,
					KeyHelper.buildObjectKey(new String[] {
							idAzienda,
							entita,
							tipoDocDgt
					}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	protected static TipoDocumentoDigitale recuperaTipoDocumentoDigitale(String idAzienda,String tipoDocDgt) {
		try {
			return (TipoDocumentoDigitale) TipoDocumentoDigitale.elementWithKey(TipoDocumentoDigitale.class,
					KeyHelper.buildObjectKey(new String[] {
							idAzienda,
							tipoDocDgt
					}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	public void valorizzaClassificazione(BODataCollector oggDaSettare,TipoDocumentoDigitale tipoDocumento, AssociazioneEntitaTDgtRiga associaRiga, BODataCollector dati) {
		Object value = dati.get(associaRiga.getClassAdEntita());
		switch (associaRiga.getClassificazione()){
		case AssociazioneEntitaTDgtRiga.ANNO_DOC:
			tipoDocumento.setAttivaDocumento(TipoDocumentoDigitale.ATTIVA_CLASSIF_FAC);
			oggDaSettare.set("IdAnnoDocumento", value);
			break;
		case AssociazioneEntitaTDgtRiga.NUMERO_DOC:
			tipoDocumento.setAttivaDocumento(TipoDocumentoDigitale.ATTIVA_CLASSIF_FAC);
			oggDaSettare.set("IdNumeroDocumento", value);
			break;
		case AssociazioneEntitaTDgtRiga.DATA_DOC:
			tipoDocumento.setAttivaDataDoc(TipoDocumentoDigitale.ATTIVA_CLASSIF_FAC);
			oggDaSettare.set("DataDocumento", formatDate(value));
			break;
		case AssociazioneEntitaTDgtRiga.FORNITORE:
			tipoDocumento.setAttivaFornitore(TipoDocumentoDigitale.ATTIVA_CLASSIF_FAC);
			oggDaSettare.set("IdFornitore", value);
			break;
		case AssociazioneEntitaTDgtRiga.ARTICOLO:
			tipoDocumento.setAttivaArticolo(TipoDocumentoDigitale.ATTIVA_CLASSIF_FAC);
			oggDaSettare.set("IdArticolo", value);
			break;
		default:
			if(associaRiga.getClassificazionePers() != null){
				int idx = associaRiga.getClassificazionePers().intValue();
				valorizzaClasiffPers(oggDaSettare, idx, value);
			}
			break;
		}
	}

	@SuppressWarnings("unchecked")
	protected DisplayObject getCurrentDisplayObject(ServletEnvironment se, String ownerCls, String ownerAd, String ownerKey) {
		DisplayObject ret = null;
		ClassADCollection collection;
		try {
			collection = ClassADCollectionManager.collectionWithName(ownerCls);
			Setting setting = new Setting();
			setting.setClassADCollectionName(ownerCls);
			setting.setLoad(true);
			setting.getSelectedAttributes().add(collection.getAttribute(ownerAd));
			DOList doList = null;
			if (collection.getDOListClassNameWeb() != null)
				doList = (DOList) Factory.createObject(collection.getDOListClassNameWeb());
			else
				doList = (DOList) Factory.createObject(DOList.class);
			if(doList instanceof WebDOList)
				((WebDOList)doList).setServletEnvironment(se);
			ret = doList.getKeyAccessDOForWeb(setting, ownerKey, true);
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void valorizzaClasiffPers(BODataCollector oggDaSettare,int idx, Object value){
		String classADName = buildClassADName(idx);
		if (idx <= TipoDocumentoAtt.MAX && idx >= TipoDocumentoAtt.MIN) {
			oggDaSettare.set(classADName, value);
		}
		else if (idx == TipoDocumentoAtt.MIN_DATA) {
			oggDaSettare.set(classADName, formatDate(value));
		}
		else if (idx == TipoDocumentoAtt.MID_DATA) {
			oggDaSettare.set(classADName, formatDate(value));
		}
		else if (idx == TipoDocumentoAtt.MAX_DATA) {
			oggDaSettare.set(classADName, formatDate(value));
		}
		else if (idx == TipoDocumentoAtt.MIN_VAL) {
			BigDecimal val = getBigDecimal(oggDaSettare, value, classADName);
			oggDaSettare.set(classADName, val);
		}
		else if (idx == TipoDocumentoAtt.MAX_VAL) {
			BigDecimal val = getBigDecimal(oggDaSettare, value, classADName);
			oggDaSettare.set(classADName, val);
		}
		else if (idx == TipoDocumentoAtt.MIN_CLOB) {
			oggDaSettare.set(classADName, value);
		}
	}

	public String buildClassADName(int idx)
	{
		if (idx <= TipoDocumentoAtt.MAX.intValue())
			return PREFIX + idx;
		else if (idx <= TipoDocumentoAtt.MAX_DATA)
			return PREFIX_DATA + (idx-TipoDocumentoAtt.INI_DATA);
		else if (idx <= TipoDocumentoAtt.MAX_VAL)
			return PREFIX_VAL + (idx-TipoDocumentoAtt.INI_VAL);
		else if (idx <= TipoDocumentoAtt.MAX_CLOB)
			return PREFIX_CLOB + (idx-TipoDocumentoAtt.INI_CLOB);
		else if (idx > TipoDocumentoAtt.INI_PERSONALIZZAZIONI && idx <= TipoDocumentoAtt.INI_PERSONALIZZAZIONI + TipoDocumentoAtt.INI_DATA)
			return PREFIX + idx;
		else if (idx > TipoDocumentoAtt.INI_PERSONALIZZAZIONI + TipoDocumentoAtt.INI_DATA && idx <= TipoDocumentoAtt.INI_PERSONALIZZAZIONI + TipoDocumentoAtt.INI_VAL)
			return PREFIX_DATA + idx;
		else if (idx > TipoDocumentoAtt.INI_PERSONALIZZAZIONI + TipoDocumentoAtt.INI_VAL && idx <= TipoDocumentoAtt.INI_PERSONALIZZAZIONI + TipoDocumentoAtt.INI_NOTE)
			return PREFIX_VAL + idx;
		else if (idx > TipoDocumentoAtt.INI_PERSONALIZZAZIONI + TipoDocumentoAtt.INI_NOTE)
			return PREFIX_NOTE + idx;
		return null;
	}

	@SuppressWarnings("static-access")
	public  BigDecimal getBigDecimal(BODataCollector oggDaSettare, Object value , String attributeName) {
		BaseBOComponentManager man = oggDaSettare.getComponentManager(attributeName);
		Type type = null;
		BigDecimal val = new BigDecimal("0.00");
		if(man != null){
			type = man.getType();
		}
		if(type != null && type instanceof DecimalType){
			DecimalType dt = new DecimalType();
			String s = dt.unFormat(value.toString());
			s = s.replace(dt.getDecimalSeparator(), '.');
			val = new BigDecimal(s);
		}
		return val;
	}

	public java.sql.Date formatDate(Object value){
		java.sql.Date sqlDate = null;
		String date = null;
		if(value != null){
			date = value.toString();
		}
		if(date != null && !date.trim().equals("")){
			sqlDate = (java.sql.Date)new DateType().stringToObject(date);
		}
		return sqlDate;
	}

	protected BODataCollector createDataCollector(String classname) {
		try {
			ClassADCollection hdr = ClassADCollectionManager.collectionWithName(classname);
			return createDataCollector(hdr);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected BODataCollector createDataCollector(ClassADCollection classDescriptor) {
		BODataCollector dataCollector = null;
		String collectorName = classDescriptor.getBODataCollector();
		if (collectorName != null) {
			dataCollector = (BODataCollector) Factory.createObject(collectorName);
		} else {
			dataCollector = new BODataCollector();
		}

		//PJ - ApiInfo - inizio
		ApiInfo info = new ApiInfo();
		info.doNotAddNullComponentsToGroup = true;
		dataCollector.setApiInfo(info);
		dataCollector.initialize(classDescriptor.getClassName(), true);
		//PJ - ApiInfo - fine

		//dataCollector.initialize(classDescriptor.getClassName(), true); //<39523

		if (dataCollector instanceof WebDataCollector) {
			ServletEnvironment se = buildServletEnvironment(request, response);
			((WebDataCollector) dataCollector).setServletEnvironment(se);
		}

		return dataCollector;
	}

	protected JSONObject readJSON(HttpServletRequest request) {
		StringBuilder out = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), Charset.forName("UTF-8")));
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new JSONObject(out.toString());
	}

	protected ServletEnvironment buildServletEnvironment(HttpServletRequest request, HttpServletResponse response) {
		ServletEnvironment se = null;
		se = Factory.newObject(ServletEnvironment.class);
		try {
			se.initialize(request, response);
		}
		catch (NamingException e) {
			e.printStackTrace(Trace.excStream);
		}
		return se;
	}

}