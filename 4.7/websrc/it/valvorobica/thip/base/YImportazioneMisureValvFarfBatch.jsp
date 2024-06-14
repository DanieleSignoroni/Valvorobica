<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/4.7.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<html>
<!-- WIZGEN Therm 2.0.0 as Batch form - multiBrowserGen = true -->
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
  BODataCollector YImportMisureVFBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YImportMisureVFForm =  
     new com.thera.thermfw.web.WebFormForBatchForm(request, response, "YImportMisureVFForm", "YImportMisureVF", "Arial,10", "it.valvorobica.thip.base.web.YImportazioneMisureValvFarfFormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/YImportazioneMisureValvFarf.js"); 
  YImportMisureVFForm.setServletEnvironment(se); 
  YImportMisureVFForm.setJSTypeList(jsList); 
  YImportMisureVFForm.setHeader("it.thera.thip.cs.Header.jsp"); 
  YImportMisureVFForm.setFooter("it.thera.thip.cs.Footer.jsp"); 
  ((WebFormForBatchForm)  YImportMisureVFForm).setGenerateThReportId(true); 
  ((WebFormForBatchForm)  YImportMisureVFForm).setGenerateSSDEnabled(true); 
  YImportMisureVFForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YImportMisureVFForm.getMode(); 
  String key = YImportMisureVFForm.getKey(); 
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
        YImportMisureVFForm.outTraceInfo(getClass().getName()); 
        String collectorName = YImportMisureVFForm.findBODataCollectorName(); 
				 YImportMisureVFBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YImportMisureVFBODC instanceof WebDataCollector) 
            ((WebDataCollector)YImportMisureVFBODC).setServletEnvironment(se); 
        YImportMisureVFBODC.initialize("YImportMisureVF", true, 0); 
        int rcBODC; 
        if (YImportMisureVFBODC.getBo() instanceof BatchRunnable) 
          rcBODC = YImportMisureVFBODC.initSecurityServices("RUN", mode, true, false, true); 
        else 
          rcBODC = YImportMisureVFBODC.initSecurityServices(mode, true, true, true); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YImportMisureVFForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YImportMisureVFBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YImportMisureVFForm.setBODataCollector(YImportMisureVFBODC); 
              YImportMisureVFForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YImportMisureVFForm); 
   request.setAttribute("menuBar", menuBar); 
%> 
<jsp:include page="/com/thera/thermfw/batch/BatchRunnableMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="menuBar"/> 
</jsp:include> 
<% 
  menuBar.write(out); 
  menuBar.writeChildren(out); 
%> 
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YImportMisureVFForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/com/thera/thermfw/batch/BatchRunnableMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>

<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YImportMisureVFForm.getBodyOnBeforeUnload()%>" onload="<%=YImportMisureVFForm.getBodyOnLoad()%>" onunload="<%=YImportMisureVFForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YImportMisureVFForm.writeBodyStartElements(out); 
%> 


	<% 
  WebScript script_0 =  
   new com.thera.thermfw.web.WebScript(); 
 script_0.setRequest(request); 
 script_0.setSrcAttribute("it/thera/thip/cs/util.js"); 
 script_0.setLanguageAttribute("JavaScript1.2"); 
  script_0.write(out); 
%>
<!--<script language="JavaScript1.2" src="it/thera/thip/cs/util.js" type="text/javascript"></script>-->
	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YImportMisureVFForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YImportMisureVFBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YImportMisureVFForm.getServlet()%>" method="post" name="myForm" style="height:100%"><%
  YImportMisureVFForm.writeFormStartElements(out); 
%>
<!-- Fix 18758 -->
		<table cellpadding="2" cellspacing="0" height="98%" id="emptyborder" width="100%">
			<tr>
				<td style="height:0"><% menuBar.writeElements(out); %> 
</td>
			</tr>
			<tr>
				<td style="height:0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<!--tr><td-->
			<!--table height="100%" width="100%" border="0" Fix 12105-->
			<tr>
				<td colspan="2" style="height:0"><% 
  WebTextInput YImportMisureVFIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YImportMisureVF", "IdAzienda"); 
  YImportMisureVFIdAzienda.setParent(YImportMisureVFForm); 
%>
<input class="<%=YImportMisureVFIdAzienda.getClassType()%>" id="<%=YImportMisureVFIdAzienda.getId()%>" maxlength="<%=YImportMisureVFIdAzienda.getMaxLength()%>" name="<%=YImportMisureVFIdAzienda.getName()%>" size="<%=YImportMisureVFIdAzienda.getSize()%>" type="hidden"><% 
  YImportMisureVFIdAzienda.write(out); 
%>
</td>
			</tr>
			<tr>
				<td height="100%">
					<!--<span class="tabbed" id="myTabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed myTabbed = new com.thera.thermfw.web.WebTabbed("myTabbed", "100%", "100%"); 
  myTabbed.setParent(YImportMisureVFForm); 
 myTabbed.addTab("generaleTab", "it.valvorobica.thip.base.resources.YImportazioneMisureValvFarfBatch", "Generale", "YImportMisureVF", null, null, null, null); 
  myTabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;">
						<div class="tabbed_page" id="<%=myTabbed.getTabPageId("generaleTab")%>" style="width:100%;height:100%;overflow:auto;"><% myTabbed.startTab("generaleTab"); %>
							<table border="0" style="margin: 7 7 7 7;" width="98%">
								<tr>
									<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YImportMisureVF", "IdFornitore", null); 
   label.setParent(YImportMisureVFForm); 
%><label class="<%=label.getClassType()%>" for="Fornitore"><%label.write(out);%></label><%}%></td>
									<td><% 
  WebMultiSearchForm YImportMisureVFFornitore =  
     new com.thera.thermfw.web.WebMultiSearchForm("YImportMisureVF", "Fornitore", false, false, true, 1, null, null); 
  YImportMisureVFFornitore.setParent(YImportMisureVFForm); 
  YImportMisureVFFornitore.write(out); 
%>
<!--<span class="multisearchform" id="Fornitore"></span>--></td>
								</tr>
								<tr>
									<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YImportMisureVF", "IdMatricolaDA", null); 
   label.setParent(YImportMisureVFForm); 
%><label class="<%=label.getClassType()%>" for="MatricolaInizio"><%label.write(out);%></label><%}%></td>
									<td><% 
  WebMultiSearchForm YImportMisureVFMatricolaInizio =  
     new com.thera.thermfw.web.WebMultiSearchForm("YImportMisureVF", "MatricolaInizio", false, false, true, 1, null, null); 
  YImportMisureVFMatricolaInizio.setParent(YImportMisureVFForm); 
  YImportMisureVFMatricolaInizio.write(out); 
%>
<!--<span class="multisearchform" id="MatricolaInizio"></span>--></td>
								</tr>
								<tr>
									<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YImportMisureVF", "IdMatricolaA", null); 
   label.setParent(YImportMisureVFForm); 
%><label class="<%=label.getClassType()%>" for="MatricolaFine"><%label.write(out);%></label><%}%></td>
									<td><% 
  WebMultiSearchForm YImportMisureVFMatricolaFine =  
     new com.thera.thermfw.web.WebMultiSearchForm("YImportMisureVF", "MatricolaFine", false, false, true, 1, null, null); 
  YImportMisureVFMatricolaFine.setParent(YImportMisureVFForm); 
  YImportMisureVFMatricolaFine.write(out); 
%>
<!--<span class="multisearchform" id="MatricolaFine"></span>--></td>
								</tr>
								<tr>
									<td><label id="SceltaFile">File CSV.</label></td>
									<td><input id="NomeFile" name="NomeFile" size="90" style=" width: 300px;height: 100px;" type="file"></td>
								</tr>
							</table>
						<% myTabbed.endTab(); %> 
</div>
					</div><% myTabbed.endTabbed();%> 

     </td>
   </tr>
</table><!--</span>-->
				</td>
			</tr>
			<tr>
				<td style="height:0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YImportMisureVFForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YImportMisureVFForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YImportMisureVFForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YImportMisureVFBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YImportMisureVFForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YImportMisureVFBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YImportMisureVFBODC.getErrorList().getErrors()); 
           if(YImportMisureVFBODC.getConflict() != null) 
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
     if(YImportMisureVFBODC != null && !YImportMisureVFBODC.close(false)) 
        errors.addAll(0, YImportMisureVFBODC.getErrorList().getErrors()); 
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
     String errorPage = YImportMisureVFForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YImportMisureVFBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YImportMisureVFForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
