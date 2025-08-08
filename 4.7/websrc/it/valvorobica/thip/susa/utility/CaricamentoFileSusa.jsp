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
  BODataCollector YCaricamentoFileSusaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YCaricamentoFileSusaForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YCaricamentoFileSusaForm", "YCaricamentoFileSusa", "Arial,10", "it.valvorobica.thip.susa.utility.web.CaricamentoFileSusaFormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/susa/utility/CaricamentoFileSusa.js"); 
  YCaricamentoFileSusaForm.setServletEnvironment(se); 
  YCaricamentoFileSusaForm.setJSTypeList(jsList); 
  YCaricamentoFileSusaForm.setHeader("it.thera.thip.cs.Header.jsp"); 
  YCaricamentoFileSusaForm.setFooter("it.thera.thip.cs.Footer.jsp"); 
  YCaricamentoFileSusaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YCaricamentoFileSusaForm.getMode(); 
  String key = YCaricamentoFileSusaForm.getKey(); 
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
        YCaricamentoFileSusaForm.outTraceInfo(getClass().getName()); 
        String collectorName = YCaricamentoFileSusaForm.findBODataCollectorName(); 
                YCaricamentoFileSusaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YCaricamentoFileSusaBODC instanceof WebDataCollector) 
            ((WebDataCollector)YCaricamentoFileSusaBODC).setServletEnvironment(se); 
        YCaricamentoFileSusaBODC.initialize("YCaricamentoFileSusa", true, 0); 
        YCaricamentoFileSusaForm.setBODataCollector(YCaricamentoFileSusaBODC); 
        int rcBODC = YCaricamentoFileSusaForm.initSecurityServices(); 
        mode = YCaricamentoFileSusaForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YCaricamentoFileSusaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YCaricamentoFileSusaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YCaricamentoFileSusaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YCaricamentoFileSusaForm); 
   request.setAttribute("menuBar", menuBar); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="menuBar"/> 
</jsp:include> 
<% 
  menuBar.write(out); 
  menuBar.writeChildren(out); 
%> 
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YCaricamentoFileSusaForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>

<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YCaricamentoFileSusaForm.getBodyOnBeforeUnload()%>" onload="<%=YCaricamentoFileSusaForm.getBodyOnLoad()%>" onunload="<%=YCaricamentoFileSusaForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YCaricamentoFileSusaForm.writeBodyStartElements(out); 
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
<% String hdr = YCaricamentoFileSusaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YCaricamentoFileSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YCaricamentoFileSusaForm.getServlet()%>" method="post" name="myForm" style="height:100%"><%
  YCaricamentoFileSusaForm.writeFormStartElements(out); 
%>

		<!-- Fix 18758 -->
		<table cellpadding="2" cellspacing="0" height="98%" id="emptyborder" width="100%">
			<tr>
				<td style="height: 0"><% menuBar.writeElements(out); %> 
</td>
			</tr>
			<tr>
				<td style="height: 0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<!--tr><td-->
			<!--table height="100%" width="100%" border="0" Fix 12105-->
			<tr>
				<td colspan="2" style="height: 0"><% 
  WebTextInput YCaricamentoFileSusaIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YCaricamentoFileSusa", "IdAzienda"); 
  YCaricamentoFileSusaIdAzienda.setParent(YCaricamentoFileSusaForm); 
%>
<input class="<%=YCaricamentoFileSusaIdAzienda.getClassType()%>" id="<%=YCaricamentoFileSusaIdAzienda.getId()%>" maxlength="<%=YCaricamentoFileSusaIdAzienda.getMaxLength()%>" name="<%=YCaricamentoFileSusaIdAzienda.getName()%>" size="<%=YCaricamentoFileSusaIdAzienda.getSize()%>" type="hidden"><% 
  YCaricamentoFileSusaIdAzienda.write(out); 
%>
</td>
			</tr>
			<tr>
				<td colspan="2" style="height: 0"><% 
  WebTextInput YCaricamentoFileSusaTemporaryFileName =  
     new com.thera.thermfw.web.WebTextInput("YCaricamentoFileSusa", "TemporaryFileName"); 
  YCaricamentoFileSusaTemporaryFileName.setParent(YCaricamentoFileSusaForm); 
%>
<input class="<%=YCaricamentoFileSusaTemporaryFileName.getClassType()%>" id="<%=YCaricamentoFileSusaTemporaryFileName.getId()%>" maxlength="<%=YCaricamentoFileSusaTemporaryFileName.getMaxLength()%>" name="<%=YCaricamentoFileSusaTemporaryFileName.getName()%>" size="<%=YCaricamentoFileSusaTemporaryFileName.getSize()%>" type="hidden"><% 
  YCaricamentoFileSusaTemporaryFileName.write(out); 
%>
</td>
			</tr>
			<tr>
				<td colspan="2" style="height: 0"><% 
  WebTextInput YCaricamentoFileSusaClassNameOrigine =  
     new com.thera.thermfw.web.WebTextInput("YCaricamentoFileSusa", "ClassNameOrigine"); 
  YCaricamentoFileSusaClassNameOrigine.setParent(YCaricamentoFileSusaForm); 
%>
<input class="<%=YCaricamentoFileSusaClassNameOrigine.getClassType()%>" id="<%=YCaricamentoFileSusaClassNameOrigine.getId()%>" maxlength="<%=YCaricamentoFileSusaClassNameOrigine.getMaxLength()%>" name="<%=YCaricamentoFileSusaClassNameOrigine.getName()%>" size="<%=YCaricamentoFileSusaClassNameOrigine.getSize()%>" type="hidden"><% 
  YCaricamentoFileSusaClassNameOrigine.write(out); 
%>
</td>
			</tr>
			<tr>
				<td>
					<table border="0" style="margin: 7 7 7 7;" width="98%">
						<tr>
							<td><label id="SceltaFile">File CSV.</label></td>
							<td><input id="NomeFile" name="NomeFile" size="90" style="width: 300px; height: 100px;" type="file"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YCaricamentoFileSusaForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YCaricamentoFileSusaForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YCaricamentoFileSusaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YCaricamentoFileSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YCaricamentoFileSusaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YCaricamentoFileSusaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YCaricamentoFileSusaBODC.getErrorList().getErrors()); 
           if(YCaricamentoFileSusaBODC.getConflict() != null) 
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
     if(YCaricamentoFileSusaBODC != null && !YCaricamentoFileSusaBODC.close(false)) 
        errors.addAll(0, YCaricamentoFileSusaBODC.getErrorList().getErrors()); 
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
     String errorPage = YCaricamentoFileSusaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YCaricamentoFileSusaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YCaricamentoFileSusaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
