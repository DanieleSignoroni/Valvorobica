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
  BODataCollector YMatchMatricolaArticoloBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YMatchMatricolaArticoloForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YMatchMatricolaArticoloForm", "YMatchMatricolaArticolo", null, "it.valvorobica.thip.base.matricole.web.YCaricamentoMatchFormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/matricole/YCaricamentoMatch.js"); 
  YMatchMatricolaArticoloForm.setServletEnvironment(se); 
  YMatchMatricolaArticoloForm.setJSTypeList(jsList); 
  YMatchMatricolaArticoloForm.setHeader(null); 
  YMatchMatricolaArticoloForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YMatchMatricolaArticoloForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YMatchMatricolaArticoloForm.getMode(); 
  String key = YMatchMatricolaArticoloForm.getKey(); 
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
        YMatchMatricolaArticoloForm.outTraceInfo(getClass().getName()); 
        String collectorName = YMatchMatricolaArticoloForm.findBODataCollectorName(); 
                YMatchMatricolaArticoloBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YMatchMatricolaArticoloBODC instanceof WebDataCollector) 
            ((WebDataCollector)YMatchMatricolaArticoloBODC).setServletEnvironment(se); 
        YMatchMatricolaArticoloBODC.initialize("YMatchMatricolaArticolo", true, 0); 
        YMatchMatricolaArticoloForm.setBODataCollector(YMatchMatricolaArticoloBODC); 
        int rcBODC = YMatchMatricolaArticoloForm.initSecurityServices(); 
        mode = YMatchMatricolaArticoloForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YMatchMatricolaArticoloForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YMatchMatricolaArticoloBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YMatchMatricolaArticoloForm.writeHeadElements(out); 
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
<body onbeforeunload="<%=YMatchMatricolaArticoloForm.getBodyOnBeforeUnload()%>" onload="<%=YMatchMatricolaArticoloForm.getBodyOnLoad()%>" onunload="<%=YMatchMatricolaArticoloForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden; background-color: rgb(232, 232, 232);"><%
   YMatchMatricolaArticoloForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YMatchMatricolaArticoloForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YMatchMatricolaArticoloBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YMatchMatricolaArticoloForm.getServlet()%>" enctype="multipart/form-data" method="post" name="YCaricamentoMatchForm" style="height:100%"><%
  YMatchMatricolaArticoloForm.writeFormStartElements(out); 
%>

		<table cellpadding="0" cellspacing="0" id="emptyborder" style="background-color: rgb(232, 232, 232);">
			<tr>
				<td>
					<table style="height: 70%;">
						<tr>
							<td class="cell">
								<h1 class="title">IMPORTAZIONE MATCH MATRICOLE - ARTICOLO</h1>
							</td>
						</tr>
						<tr>
							<td class="cell"><input class="file" id="file" name="file" type="file"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table>
			<tr>
                  <td valign="top">
                    <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatchMatricolaArticolo", "IdLotto", null); 
   label.setParent(YMatchMatricolaArticoloForm); 
%><label class="<%=label.getClassType()%>" for="Lotto"><%label.write(out);%></label><%}%>
                  </td>
                  <td valign="top">
                    <% 
  WebMultiSearchForm YMatchMatricolaArticoloLotto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMatchMatricolaArticolo", "Lotto", false, false, true, 1, null, null); 
  YMatchMatricolaArticoloLotto.setParent(YMatchMatricolaArticoloForm); 
  YMatchMatricolaArticoloLotto.setSpecificDOList("it.valvorobica.thip.base.matricole.web.YCaricamentoMatchLottoDOList"); 
  YMatchMatricolaArticoloLotto.write(out); 
%>
<!--<span class="multisearchform" id="Lotto"></span>-->
                  </td>
                </tr>
                
						<tr>
							<td class="cell">
								<button class="importa" id="importa" name="importa" onclick="importaFile()" type="button">IMPORTA</button>
							</td>
						</tr>
						<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YMatchMatricolaArticoloForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YMatchMatricolaArticoloForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YMatchMatricolaArticoloForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YMatchMatricolaArticoloBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YMatchMatricolaArticoloForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YMatchMatricolaArticoloBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YMatchMatricolaArticoloBODC.getErrorList().getErrors()); 
           if(YMatchMatricolaArticoloBODC.getConflict() != null) 
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
     if(YMatchMatricolaArticoloBODC != null && !YMatchMatricolaArticoloBODC.close(false)) 
        errors.addAll(0, YMatchMatricolaArticoloBODC.getErrorList().getErrors()); 
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
     String errorPage = YMatchMatricolaArticoloForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YMatchMatricolaArticoloBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YMatchMatricolaArticoloForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
