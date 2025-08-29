<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.1.0/websrcsvil/dtd/xhtml1-transitional.dtd">
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
  BODataCollector YStampaEticSusaBatchBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YStampaEticSusaBatchForm =  
     new com.thera.thermfw.web.WebFormForBatchForm(request, response, "YStampaEticSusaBatchForm", "YStampaEticSusaBatch", "Arial,10", "com.thera.thermfw.batch.web.BatchFormActionAdapter", false, false, false, true, true, true, null, 0, true, "it/valvorobica/thip/susa/YStampaEticSusaBatch.js"); 
  YStampaEticSusaBatchForm.setServletEnvironment(se); 
  YStampaEticSusaBatchForm.setJSTypeList(jsList); 
  YStampaEticSusaBatchForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YStampaEticSusaBatchForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  ((WebFormForBatchForm)  YStampaEticSusaBatchForm).setGenerateThReportId(true); 
  ((WebFormForBatchForm)  YStampaEticSusaBatchForm).setGenerateSSDEnabled(true); 
  YStampaEticSusaBatchForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YStampaEticSusaBatchForm.getMode(); 
  String key = YStampaEticSusaBatchForm.getKey(); 
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
        YStampaEticSusaBatchForm.outTraceInfo(getClass().getName()); 
        String collectorName = YStampaEticSusaBatchForm.findBODataCollectorName(); 
				 YStampaEticSusaBatchBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YStampaEticSusaBatchBODC instanceof WebDataCollector) 
            ((WebDataCollector)YStampaEticSusaBatchBODC).setServletEnvironment(se); 
        YStampaEticSusaBatchBODC.initialize("YStampaEticSusaBatch", true, 0); 
        int rcBODC; 
        if (YStampaEticSusaBatchBODC.getBo() instanceof BatchRunnable) 
          rcBODC = YStampaEticSusaBatchBODC.initSecurityServices("RUN", mode, true, false, true); 
        else 
          rcBODC = YStampaEticSusaBatchBODC.initSecurityServices(mode, true, true, true); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YStampaEticSusaBatchForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YStampaEticSusaBatchBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YStampaEticSusaBatchForm.setBODataCollector(YStampaEticSusaBatchBODC); 
              YStampaEticSusaBatchForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<title></title>
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YStampaEticSusaBatchForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/PrintRunnableMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
<% 
  WebLink link_0 =  
   new com.thera.thermfw.web.WebLink(); 
 link_0.setHttpServletRequest(request); 
 link_0.setHRefAttribute("thermweb/css/thermGrid.css"); 
 link_0.setRelAttribute("STYLESHEET"); 
 link_0.setTypeAttribute("text/css"); 
  link_0.write(out); 
%>
<!--<link href="thermweb/css/thermGrid.css" rel="STYLESHEET" type="text/css">-->
<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YStampaEticSusaBatchForm.getBodyOnBeforeUnload()%>" onload="<%=YStampaEticSusaBatchForm.getBodyOnLoad()%>" onunload="<%=YStampaEticSusaBatchForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YStampaEticSusaBatchForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YStampaEticSusaBatchForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YStampaEticSusaBatchBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YStampaEticSusaBatchForm.getServlet()%>" method="post" name="YStampaEticSusaBatch" style="height:100%"><%
  YStampaEticSusaBatchForm.writeFormStartElements(out); 
%>

		<table cellpadding="2" cellspacing="2" width="100%">
			<tr>
				<td style="height: 0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YStampaEticSusaBatch", "CodiceTestataLista", null); 
   label.setParent(YStampaEticSusaBatchForm); 
%><label class="<%=label.getClassType()%>" for="CodiceTestataLista"><%label.write(out);%></label><%}%></td>
							<td valign="top"><% 
  WebTextInput YStampaEticSusaBatchCodiceTestataLista =  
     new com.thera.thermfw.web.WebTextInput("YStampaEticSusaBatch", "CodiceTestataLista"); 
  YStampaEticSusaBatchCodiceTestataLista.setParent(YStampaEticSusaBatchForm); 
%>
<input class="<%=YStampaEticSusaBatchCodiceTestataLista.getClassType()%>" id="<%=YStampaEticSusaBatchCodiceTestataLista.getId()%>" maxlength="<%=YStampaEticSusaBatchCodiceTestataLista.getMaxLength()%>" name="<%=YStampaEticSusaBatchCodiceTestataLista.getName()%>" size="<%=YStampaEticSusaBatchCodiceTestataLista.getSize()%>"><% 
  YStampaEticSusaBatchCodiceTestataLista.write(out); 
%>
</td>
						</tr>
						<tr>
							<td valign="top"><% 
  WebCheckBox YStampaEticSusaBatchParcheggiaEticPerInvio =  
     new com.thera.thermfw.web.WebCheckBox("YStampaEticSusaBatch", "ParcheggiaEticPerInvio"); 
  YStampaEticSusaBatchParcheggiaEticPerInvio.setParent(YStampaEticSusaBatchForm); 
%>
<input id="<%=YStampaEticSusaBatchParcheggiaEticPerInvio.getId()%>" name="<%=YStampaEticSusaBatchParcheggiaEticPerInvio.getName()%>" type="checkbox" value="Y"><%
  YStampaEticSusaBatchParcheggiaEticPerInvio.write(out); 
%>
</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YStampaEticSusaBatchForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YStampaEticSusaBatchForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YStampaEticSusaBatchForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YStampaEticSusaBatchBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YStampaEticSusaBatchForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YStampaEticSusaBatchBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YStampaEticSusaBatchBODC.getErrorList().getErrors()); 
           if(YStampaEticSusaBatchBODC.getConflict() != null) 
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
     if(YStampaEticSusaBatchBODC != null && !YStampaEticSusaBatchBODC.close(false)) 
        errors.addAll(0, YStampaEticSusaBatchBODC.getErrorList().getErrors()); 
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
     String errorPage = YStampaEticSusaBatchForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YStampaEticSusaBatchBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YStampaEticSusaBatchForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
