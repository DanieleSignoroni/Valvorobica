<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.1.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<html>
<!-- WIZGEN Therm 2.0.0 as Form - multiBrowserGen = true -->
<%=WebGenerator.writeRuntimeInfo()%>

<head>
<%@ page contentType="text/html; charset=Cp1252"%>
<%@ page import= " 
  java.sql.*, 
  java.util.*, 
  java.lang.reflect.*, 
  javax.naming.*, 
  com.thera.thermfw.common.*, 
  com.thera.thermfw.type.*, 
  com.thera.thermfw.web.*, 
  com.thera.thermfw.security.*, 
  com.thera.thermfw.base.*, 
  com.thera.thermfw.ad.*, 
  com.thera.thermfw.persist.*, 
  com.thera.thermfw.gui.cnr.*, 
  com.thera.thermfw.setting.*, 
  com.thera.thermfw.collector.*, 
  com.thera.thermfw.batch.web.*, 
  com.thera.thermfw.batch.*, 
  com.thera.thermfw.pref.* 
"%> 
<%
  ServletEnvironment se = (ServletEnvironment)Factory.createObject("com.thera.thermfw.web.ServletEnvironment"); 
  BODataCollector DocumentoVenditaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm DocumentoVenditaForm =  
     new com.thera.thermfw.web.WebForm(request, response, "DocumentoVenditaForm", "DocumentoVendita", null, "it.valvorobica.thip.vendite.documentoVE.web.YConfermaMissioniChiudiListaFincantieriServlet", false, false, true, true, true, true, null, 1, true, "it/valvorobica/thip/vendite/documentoVE/ConfermaMissioniChiudiListaFincantieri.js"); 
  DocumentoVenditaForm.setServletEnvironment(se); 
  DocumentoVenditaForm.setJSTypeList(jsList); 
  DocumentoVenditaForm.setHeader("it.thera.thip.cs.Header.jsp"); 
  DocumentoVenditaForm.setFooter("it.thera.thip.cs.Footer.jsp"); 
  DocumentoVenditaForm.setWebFormModifierClass("it.valvorobica.thip.vendite.documentoVE.web.YConfermaMissioniChiudiListaFincantieriFormModifier"); 
  DocumentoVenditaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = DocumentoVenditaForm.getMode(); 
  String key = DocumentoVenditaForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  boolean conflitPresent = false; 
  String leftClass = ""; 
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        DocumentoVenditaForm.outTraceInfo(getClass().getName()); 
        String collectorName = DocumentoVenditaForm.findBODataCollectorName(); 
                DocumentoVenditaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (DocumentoVenditaBODC instanceof WebDataCollector) 
            ((WebDataCollector)DocumentoVenditaBODC).setServletEnvironment(se); 
        DocumentoVenditaBODC.initialize("DocumentoVendita", true, 1); 
        DocumentoVenditaForm.setBODataCollector(DocumentoVenditaBODC); 
        int rcBODC = DocumentoVenditaForm.initSecurityServices(); 
        mode = DocumentoVenditaForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           DocumentoVenditaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = DocumentoVenditaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              DocumentoVenditaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

	<title>ConvalidaRegressione</title>
</head>
<script language="JavaScript">	window.name = "ConvalidaRegressione";
								window.resizeTo(500,500);</script>
<% 
  WebScript script_0 =  
   new com.thera.thermfw.web.WebScript(); 
 script_0.setRequest(request); 
 script_0.setSrcAttribute("thermweb/factory/gui/components/search.js"); 
 script_0.setLanguageAttribute("JavaScript1.2"); 
  script_0.write(out); 
%>
<!--<script language="JavaScript1.2" src="thermweb/factory/gui/components/search.js" type="text/javascript"></script>--> <!-- fix 12091-->

<body bottommargin="0" leftmargin="0" onbeforeunload="<%=DocumentoVenditaForm.getBodyOnBeforeUnload()%>" onload="<%=DocumentoVenditaForm.getBodyOnLoad()%>" onunload="<%=DocumentoVenditaForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   DocumentoVenditaForm.writeBodyStartElements(out); 
%> 



	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = DocumentoVenditaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", DocumentoVenditaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=DocumentoVenditaForm.getServlet()%>" method="post" name="DocumentoVenditaForm" style="height:100%"><%
  DocumentoVenditaForm.writeFormStartElements(out); 
%>

			<!-- **************************************************************************************************** -->
			<!-- ErrorList -->
    		<table align="center" heigth="100%" width="100%">
			<tr>
			<td>&nbsp;</td>
			</tr>
			<tr>
			<td align="center">
			<!-- Fix 41089 Inizio -->
							<img align="center" alt border="0" id="errorIcon" name="errorIcon" src="com/thera/thermfw/batch/images/error.svg" style="display:none; width:2em; height:auto;">
							<img align="center" alt border="0" id="successIcon" name="successIcon" src="com/thera/thermfw/batch/images/success.svg" style="display:none; width:2em; height:auto;">
			<!-- Fix 41089 Fine --> 
				 	<label id="thTitolo" name="thTitolo">CONVALIDA</label> 		
			</td>
			<td style="display:none">
				<label class="thLabel" id="thTerminata" name="thTerminata">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.thera.thip.vendite.documentoVE.resources.DocumentoVendita", "Terminata", null, null, null, null); 
 label.setParent(DocumentoVenditaForm); 
label.write(out); }%> 
</label>
			</td>
			<!-- Fix 41089 inizio -->
			<td style="display:none">
				<label class="thLabel" id="thTerminataErr" name="thTerminataErr">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.thera.thip.vendite.documentoVE.resources.DocumentoVendita", "TerminataError", null, null, null, null); 
 label.setParent(DocumentoVenditaForm); 
label.write(out); }%> 
</label>
			</td>
			<!-- Fix 41089 fine -->
			</tr>
	<!--Fix 36914 inizo-->
<!--    <tr>    <td>&nbsp;</td>    </tr> -->
			
			<tr>
			<td align="center">
			<img alt border="0" height="150" id="loading" name="loading" src="it/thera/thip/base/comuniVenAcq/image/loading1.gif" width="150">
			</td>
			</tr>
		<!--Fix 36914 fine-->
			<tr><td align="center" id="SezImmagine" name="SezImmagine"><!-- Fix 12091-->
			<!--<img id ="immagine" name ="immagine" src="it/thera/thip/vendite/documentoVE/images/scimmione.gif" width="150" height="150" border="0"  alt=""/>-->
				<label class="thLabel" id="thElaborazione" name="thElaborazione">
 <% { WebLabelSimple label = new com.thera.thermfw.web.WebLabelSimple("it.thera.thip.base.comuniVenAcq.resources.Documento", "Elaborazione", null, null, null, null); 
 label.setParent(DocumentoVenditaForm); 
label.write(out); }%> 
</label>

			</td></tr>
           <!-- Fix 27106<tr><td align="center" style="display:none" id="thDocsConvReg"><textarea id="DocsConvReg" name="DocsConvReg" rows="5" cols="70"/></td></tr>Fix 24719-->
           <tr><td align="center" id="thDocsConvReg" style="display:none"><textarea cols="70" id="DocsConvReg" name="DocsConvReg" readonly rows="5"></textarea></td></tr><!--Fix 27106-->
			</table>
			<table>

				<tr>
					<td><input id="thIdAzienda" name="thIdAzienda" style="display:none" type="text"></td>
				</tr>
				<tr>
					<td><input id="thAnnoDocumento" name="thAnnoDocumento" style="display:none" type="text"></td>
				</tr>
				<tr>
					<td><input id="thNumeroDocumento" name="thNumeroDocumento" style="display:none" type="text"></td>
				</tr>
				<tr>
					<td><input id="thAzione" name="thAzione" style="display:none" type="text"></td>
				</tr>

			</table>

		<table align="center" heigth="100%" width="100%">
			<tr>
    			<td style="height:0">
		    	  <% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(DocumentoVenditaForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
			    </td>
			</tr>

			<tr>
			<td align="center">
			<!-- <button name="thChiudi" id="thChiudi" type="button" onClick="chiudi()">Chiudi</button> Fix 36914-->
			
<% 
  WebButton thChiudiWebButton = new WebButton(); 
  thChiudiWebButton.setImage(null); 
  thChiudiWebButton.setResourceFile("it.thera.thip.vendite.documentoVE.resources.DocumentoVendita"); 
  thChiudiWebButton.setResourceId("Chiudi"); 
  thChiudiWebButton.setResourceTooltipId(null); 
%>
<button class=" <%=thChiudiWebButton.getButtonCSSClass()%>" id="thChiudi" name="thChiudi" onclick="chiudi()" style="display:none" title="<%=thChiudiWebButton.getTitle()%>" type="button"><%thChiudiWebButton.getBtnContent(out);%></button><!--Fix 36914-->
			</td>
			</tr>
		</table>

	<%
  DocumentoVenditaForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = DocumentoVenditaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", DocumentoVenditaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>



<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              DocumentoVenditaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, DocumentoVenditaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, DocumentoVenditaBODC.getErrorList().getErrors()); 
           if(DocumentoVenditaBODC.getConflict() != null) 
                conflitPresent = true; 
     } 
     else 
        errors.add(new ErrorMessage("BAS0000010")); 
  } 
  catch(NamingException e) { 
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("CBS000025", errorMessage));  } 
  catch(SQLException e) {
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("BAS0000071", errorMessage));  } 
  catch(Throwable e) {
     e.printStackTrace(Trace.excStream);
  }
  finally 
  {
     if(DocumentoVenditaBODC != null && !DocumentoVenditaBODC.close(false)) 
        errors.addAll(0, DocumentoVenditaBODC.getErrorList().getErrors()); 
     try 
     { 
        se.end(); 
     }
     catch(IllegalArgumentException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
     catch(SQLException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
  } 
  if(!errors.isEmpty())
  { 
      if(!conflitPresent)
  { 
     request.setAttribute("ErrorMessages", errors); 
     String errorPage = DocumentoVenditaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", DocumentoVenditaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = DocumentoVenditaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
