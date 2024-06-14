<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/4.7.0/websrcsvil/dtd/xhtml1-transitional.dtd">
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
  BODataCollector YMatricolaValvoBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YMatricolaValvoForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YMatricolaValvoForm", "YMatricolaValvo", null, "it.valvorobica.thip.base.matricole.web.YCaricamentoMatricoleFormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/matricole/YCaricamentoMatricole.js"); 
  YMatricolaValvoForm.setServletEnvironment(se); 
  YMatricolaValvoForm.setJSTypeList(jsList); 
  YMatricolaValvoForm.setHeader(null); 
  YMatricolaValvoForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YMatricolaValvoForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YMatricolaValvoForm.getMode(); 
  String key = YMatricolaValvoForm.getKey(); 
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
        YMatricolaValvoForm.outTraceInfo(getClass().getName()); 
        String collectorName = YMatricolaValvoForm.findBODataCollectorName(); 
                YMatricolaValvoBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YMatricolaValvoBODC instanceof WebDataCollector) 
            ((WebDataCollector)YMatricolaValvoBODC).setServletEnvironment(se); 
        YMatricolaValvoBODC.initialize("YMatricolaValvo", true, 0); 
        YMatricolaValvoForm.setBODataCollector(YMatricolaValvoBODC); 
        int rcBODC = YMatricolaValvoForm.initSecurityServices(); 
        mode = YMatricolaValvoForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YMatricolaValvoForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YMatricolaValvoBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YMatricolaValvoForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<style>
.importa {
	background-color: #666666;
	color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	padding: 0px;
	font-size: 18pt;
	cursor: pointer;
	font-weight: bolder;
	height: 40px;
	width: 159px;
}

.title {
	text-align: center;
	color: #FFFFFF;
	border: 1px solid black;
	background-color: #666666;
	padding-top: 1rem;
	padding-bottom: 1rem;
}

.file {
	border: 1px solid #999;
	border-radius: 3px;
	padding: 5px 8px;
	white-space: nowrap;
	cursor: pointer;
	text-shadow: 1px 1px #fff;
	font-weight: 700;
	font-size: 10pt;
	width: 500px;
	height: 350px;
}

.cell {
	text-align: center;
	padding-bottom: 5px;
}
</style>
</head>
<body onbeforeunload="<%=YMatricolaValvoForm.getBodyOnBeforeUnload()%>" onload="<%=YMatricolaValvoForm.getBodyOnLoad()%>" onunload="<%=YMatricolaValvoForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden; background-color: rgb(232, 232, 232);"><%
   YMatricolaValvoForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YMatricolaValvoForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YMatricolaValvoBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YMatricolaValvoForm.getServlet()%>" enctype="multipart/form-data" method="post" name="YCaricamentoMatricoleForm" style="height:100%"><%
  YMatricolaValvoForm.writeFormStartElements(out); 
%>

		<table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" style="background-color: rgb(232, 232, 232);" width="100%">

			<tr>
				<td>
					<table style="height: 70%;">
						<tr>
							<td class="cell">
								<h1 class="title">IMPORTAZIONE MATRICOLE VALVO</h1>
							</td>
						</tr>
						<tr>
							<td class="cell"><input class="file" id="file" name="file" type="file"></td>
						</tr>
						<tr>
							<td class="cell" style="padding-bottom: 5rem;">
								<button class="importa" id="importa" name="importa" onclick="importaFile()" type="button">IMPORTA</button>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YMatricolaValvoForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YMatricolaValvoForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YMatricolaValvoForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YMatricolaValvoBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YMatricolaValvoForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YMatricolaValvoBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YMatricolaValvoBODC.getErrorList().getErrors()); 
           if(YMatricolaValvoBODC.getConflict() != null) 
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
     if(YMatricolaValvoBODC != null && !YMatricolaValvoBODC.close(false)) 
        errors.addAll(0, YMatricolaValvoBODC.getErrorList().getErrors()); 
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
     String errorPage = YMatricolaValvoForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YMatricolaValvoBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YMatricolaValvoForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
