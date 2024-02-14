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
  BODataCollector YInsCertPortaleBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YInsCertPortaleForm =  
     new com.thera.thermfw.web.WebFormForBatchForm(request, response, "YInsCertPortaleForm", "YInsCertPortale", "Arial,10", "com.thera.thermfw.batch.web.BatchFormActionAdapter", false, false, false, true, true, true, null, 0, false, null); 
  YInsCertPortaleForm.setServletEnvironment(se); 
  YInsCertPortaleForm.setJSTypeList(jsList); 
  YInsCertPortaleForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YInsCertPortaleForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  ((WebFormForBatchForm)  YInsCertPortaleForm).setGenerateThReportId(true); 
  ((WebFormForBatchForm)  YInsCertPortaleForm).setGenerateSSDEnabled(true); 
  YInsCertPortaleForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YInsCertPortaleForm.getMode(); 
  String key = YInsCertPortaleForm.getKey(); 
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
        YInsCertPortaleForm.outTraceInfo(getClass().getName()); 
        String collectorName = YInsCertPortaleForm.findBODataCollectorName(); 
				 YInsCertPortaleBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YInsCertPortaleBODC instanceof WebDataCollector) 
            ((WebDataCollector)YInsCertPortaleBODC).setServletEnvironment(se); 
        YInsCertPortaleBODC.initialize("YInsCertPortale", true, 0); 
        int rcBODC; 
        if (YInsCertPortaleBODC.getBo() instanceof BatchRunnable) 
          rcBODC = YInsCertPortaleBODC.initSecurityServices("RUN", mode, true, false, true); 
        else 
          rcBODC = YInsCertPortaleBODC.initSecurityServices(mode, true, true, true); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YInsCertPortaleForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YInsCertPortaleBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YInsCertPortaleForm.setBODataCollector(YInsCertPortaleBODC); 
              YInsCertPortaleForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

	<title>Emissione automatica certificati</title>
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YInsCertPortaleForm); 
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
<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YInsCertPortaleForm.getBodyOnBeforeUnload()%>" onload="<%=YInsCertPortaleForm.getBodyOnLoad()%>" onunload="<%=YInsCertPortaleForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YInsCertPortaleForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YInsCertPortaleForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YInsCertPortaleBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YInsCertPortaleForm.getServlet()%>" method="post" name="YInsCertPortaleForm" style="height:100%"><%
  YInsCertPortaleForm.writeFormStartElements(out); 
%>

		<table cellpadding="2" cellspacing="2" height="100%" width="100%">
			<tr><td style="height:0"><% myToolBarTB.writeChildren(out); %> 
</td></tr>
			<tr>
				<td>
					<table width="100%">	
            			<tr>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height:0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YInsCertPortaleForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YInsCertPortaleForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YInsCertPortaleForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YInsCertPortaleBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YInsCertPortaleForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YInsCertPortaleBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YInsCertPortaleBODC.getErrorList().getErrors()); 
           if(YInsCertPortaleBODC.getConflict() != null) 
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
     if(YInsCertPortaleBODC != null && !YInsCertPortaleBODC.close(false)) 
        errors.addAll(0, YInsCertPortaleBODC.getErrorList().getErrors()); 
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
     String errorPage = YInsCertPortaleForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YInsCertPortaleBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YInsCertPortaleForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
