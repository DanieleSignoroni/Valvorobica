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
  BODataCollector YEXPDDTSusaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YEXPDDTSusaForm =  
     new com.thera.thermfw.web.WebFormForBatchForm(request, response, "YEXPDDTSusaForm", "YEXPDDTSusa", "Arial,10", "com.thera.thermfw.batch.web.BatchFormActionAdapter", false, false, false, true, true, true, null, 0, false, null); 
  YEXPDDTSusaForm.setServletEnvironment(se); 
  YEXPDDTSusaForm.setJSTypeList(jsList); 
  YEXPDDTSusaForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YEXPDDTSusaForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  ((WebFormForBatchForm)  YEXPDDTSusaForm).setGenerateThReportId(true); 
  ((WebFormForBatchForm)  YEXPDDTSusaForm).setGenerateSSDEnabled(true); 
  YEXPDDTSusaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YEXPDDTSusaForm.getMode(); 
  String key = YEXPDDTSusaForm.getKey(); 
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
        YEXPDDTSusaForm.outTraceInfo(getClass().getName()); 
        String collectorName = YEXPDDTSusaForm.findBODataCollectorName(); 
				 YEXPDDTSusaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YEXPDDTSusaBODC instanceof WebDataCollector) 
            ((WebDataCollector)YEXPDDTSusaBODC).setServletEnvironment(se); 
        YEXPDDTSusaBODC.initialize("YEXPDDTSusa", true, 0); 
        int rcBODC; 
        if (YEXPDDTSusaBODC.getBo() instanceof BatchRunnable) 
          rcBODC = YEXPDDTSusaBODC.initSecurityServices("RUN", mode, true, false, true); 
        else 
          rcBODC = YEXPDDTSusaBODC.initSecurityServices(mode, true, true, true); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YEXPDDTSusaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YEXPDDTSusaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YEXPDDTSusaForm.setBODataCollector(YEXPDDTSusaBODC); 
              YEXPDDTSusaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<title></title>
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YEXPDDTSusaForm); 
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
<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YEXPDDTSusaForm.getBodyOnBeforeUnload()%>" onload="<%=YEXPDDTSusaForm.getBodyOnLoad()%>" onunload="<%=YEXPDDTSusaForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YEXPDDTSusaForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YEXPDDTSusaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YEXPDDTSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YEXPDDTSusaForm.getServlet()%>" method="post" name="YEXPDDTSusa" style="height:100%"><%
  YEXPDDTSusaForm.writeFormStartElements(out); 
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
							<td colspan="2"><p style="font-weight: bold; font-size: 15px;">
									Indicare l'intervallo di date per l'estrazione dei 'DDT di
									Vendita' <br>
								</p></td>
						</tr>
						<tr>
							<td nowrap><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YEXPDDTSusa", "DateFrom", null); 
   label.setParent(YEXPDDTSusaForm); 
%><label class="<%=label.getClassType()%>" for="DateFrom"><%label.write(out);%></label><%}%></td>
							<td><% 
  WebTextInput YEXPDDTSusaDateFrom =  
     new com.thera.thermfw.web.WebTextInput("YEXPDDTSusa", "DateFrom"); 
  YEXPDDTSusaDateFrom.setShowCalendarBtn(true); 
  YEXPDDTSusaDateFrom.setParent(YEXPDDTSusaForm); 
%>
<input class="<%=YEXPDDTSusaDateFrom.getClassType()%>" id="<%=YEXPDDTSusaDateFrom.getId()%>" maxlength="<%=YEXPDDTSusaDateFrom.getMaxLength()%>" name="<%=YEXPDDTSusaDateFrom.getName()%>" size="<%=YEXPDDTSusaDateFrom.getSize()%>" type="text"><% 
  YEXPDDTSusaDateFrom.write(out); 
%>
</td>
						</tr>
						<tr>
							<td nowrap><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YEXPDDTSusa", "DateTo", null); 
   label.setParent(YEXPDDTSusaForm); 
%><label class="<%=label.getClassType()%>" for="DateTo"><%label.write(out);%></label><%}%></td>
							<td><% 
  WebTextInput YEXPDDTSusaDateTo =  
     new com.thera.thermfw.web.WebTextInput("YEXPDDTSusa", "DateTo"); 
  YEXPDDTSusaDateTo.setShowCalendarBtn(true); 
  YEXPDDTSusaDateTo.setParent(YEXPDDTSusaForm); 
%>
<input class="<%=YEXPDDTSusaDateTo.getClassType()%>" id="<%=YEXPDDTSusaDateTo.getId()%>" maxlength="<%=YEXPDDTSusaDateTo.getMaxLength()%>" name="<%=YEXPDDTSusaDateTo.getName()%>" size="<%=YEXPDDTSusaDateTo.getSize()%>" type="text"><% 
  YEXPDDTSusaDateTo.write(out); 
%>
</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YEXPDDTSusaForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YEXPDDTSusaForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YEXPDDTSusaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YEXPDDTSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YEXPDDTSusaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YEXPDDTSusaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YEXPDDTSusaBODC.getErrorList().getErrors()); 
           if(YEXPDDTSusaBODC.getConflict() != null) 
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
     if(YEXPDDTSusaBODC != null && !YEXPDDTSusaBODC.close(false)) 
        errors.addAll(0, YEXPDDTSusaBODC.getErrorList().getErrors()); 
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
     String errorPage = YEXPDDTSusaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YEXPDDTSusaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YEXPDDTSusaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
