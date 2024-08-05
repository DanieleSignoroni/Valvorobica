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
  BODataCollector YExpDDTBRTBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YExpDDTBRTForm =  
     new com.thera.thermfw.web.WebFormForBatchForm(request, response, "YExpDDTBRTForm", "YExpDDTBRT", "Arial,10", "com.thera.thermfw.batch.web.BatchFormActionAdapter", false, false, false, true, true, true, null, 0, false, null); 
  YExpDDTBRTForm.setServletEnvironment(se); 
  YExpDDTBRTForm.setJSTypeList(jsList); 
  YExpDDTBRTForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YExpDDTBRTForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  ((WebFormForBatchForm)  YExpDDTBRTForm).setGenerateThReportId(true); 
  ((WebFormForBatchForm)  YExpDDTBRTForm).setGenerateSSDEnabled(true); 
  YExpDDTBRTForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YExpDDTBRTForm.getMode(); 
  String key = YExpDDTBRTForm.getKey(); 
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
        YExpDDTBRTForm.outTraceInfo(getClass().getName()); 
        String collectorName = YExpDDTBRTForm.findBODataCollectorName(); 
				 YExpDDTBRTBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YExpDDTBRTBODC instanceof WebDataCollector) 
            ((WebDataCollector)YExpDDTBRTBODC).setServletEnvironment(se); 
        YExpDDTBRTBODC.initialize("YExpDDTBRT", true, 0); 
        int rcBODC; 
        if (YExpDDTBRTBODC.getBo() instanceof BatchRunnable) 
          rcBODC = YExpDDTBRTBODC.initSecurityServices("RUN", mode, true, false, true); 
        else 
          rcBODC = YExpDDTBRTBODC.initSecurityServices(mode, true, true, true); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YExpDDTBRTForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YExpDDTBRTBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YExpDDTBRTForm.setBODataCollector(YExpDDTBRTBODC); 
              YExpDDTBRTForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

	<title></title>
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YExpDDTBRTForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/com/thera/thermfw/batch/BatchRunnableMenu.jsp" flush="true"> 
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
<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YExpDDTBRTForm.getBodyOnBeforeUnload()%>" onload="<%=YExpDDTBRTForm.getBodyOnLoad()%>" onunload="<%=YExpDDTBRTForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YExpDDTBRTForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YExpDDTBRTForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YExpDDTBRTBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YExpDDTBRTForm.getServlet()%>" method="post" name="YExpDDTBRT" style="height:100%"><%
  YExpDDTBRTForm.writeFormStartElements(out); 
%>

		<table cellpadding="2" cellspacing="2" width="100%">
			<tr><td style="height:0"><% myToolBarTB.writeChildren(out); %> 
</td></tr>
			<tr>
				<td>
					<table>	
					<tr>
                  	<td colspan="2"><p style="font-weight:bold;font-size:15px;">Indicare l'intervallo di date per l'estrazione dei 'DDT di Vendita' <br>
                  		Indicare inoltre la stampante sulla quale verra' eseguita la stampa dei bordero'
                  	</p></td>
                  </tr>
            			<tr>
							<td nowrap><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YExpDDTBRT", "DateFrom", null); 
   label.setParent(YExpDDTBRTForm); 
%><label class="<%=label.getClassType()%>" for="DateFrom"><%label.write(out);%></label><%}%></td>
							<td><% 
  WebTextInput YExpDDTBRTDateFrom =  
     new com.thera.thermfw.web.WebTextInput("YExpDDTBRT", "DateFrom"); 
  YExpDDTBRTDateFrom.setShowCalendarBtn(true); 
  YExpDDTBRTDateFrom.setParent(YExpDDTBRTForm); 
%>
<input class="<%=YExpDDTBRTDateFrom.getClassType()%>" id="<%=YExpDDTBRTDateFrom.getId()%>" maxlength="<%=YExpDDTBRTDateFrom.getMaxLength()%>" name="<%=YExpDDTBRTDateFrom.getName()%>" size="<%=YExpDDTBRTDateFrom.getSize()%>" type="text"><% 
  YExpDDTBRTDateFrom.write(out); 
%>
</td>
						</tr>
						<tr>
						<td nowrap><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YExpDDTBRT", "DateTo", null); 
   label.setParent(YExpDDTBRTForm); 
%><label class="<%=label.getClassType()%>" for="DateTo"><%label.write(out);%></label><%}%></td>
							<td><% 
  WebTextInput YExpDDTBRTDateTo =  
     new com.thera.thermfw.web.WebTextInput("YExpDDTBRT", "DateTo"); 
  YExpDDTBRTDateTo.setShowCalendarBtn(true); 
  YExpDDTBRTDateTo.setParent(YExpDDTBRTForm); 
%>
<input class="<%=YExpDDTBRTDateTo.getClassType()%>" id="<%=YExpDDTBRTDateTo.getId()%>" maxlength="<%=YExpDDTBRTDateTo.getMaxLength()%>" name="<%=YExpDDTBRTDateTo.getName()%>" size="<%=YExpDDTBRTDateTo.getSize()%>" type="text"><% 
  YExpDDTBRTDateTo.write(out); 
%>
</td>
						</tr>
						<tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YExpDDTBRT", "IdStampante", null); 
   label.setParent(YExpDDTBRTForm); 
%><label class="<%=label.getClassType()%>" for="Stampante"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YExpDDTBRTStampante =  
     new com.thera.thermfw.web.WebMultiSearchForm("YExpDDTBRT", "Stampante", false, false, true, 1, null, null); 
  YExpDDTBRTStampante.setParent(YExpDDTBRTForm); 
  YExpDDTBRTStampante.write(out); 
%>
<!--<span class="multisearchform" id="Stampante"></span>-->
                    </td>
                  </tr>
                  
					</table>
				</td>
			</tr>
			<tr>
				<td style="height:0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YExpDDTBRTForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YExpDDTBRTForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YExpDDTBRTForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YExpDDTBRTBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YExpDDTBRTForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YExpDDTBRTBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YExpDDTBRTBODC.getErrorList().getErrors()); 
           if(YExpDDTBRTBODC.getConflict() != null) 
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
     if(YExpDDTBRTBODC != null && !YExpDDTBRTBODC.close(false)) 
        errors.addAll(0, YExpDDTBRTBODC.getErrorList().getErrors()); 
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
     String errorPage = YExpDDTBRTForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YExpDDTBRTBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YExpDDTBRTForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
