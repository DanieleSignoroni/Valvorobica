<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///W:\PthDev\Projects\Panthera\Valvorobica\WebContent\dtd/xhtml1-transitional.dtd">
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
  BODataCollector YFirmaDigitVettoriEsclusiBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YFirmaDigitVettoriEsclusiForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YFirmaDigitVettoriEsclusiForm", "YFirmaDigitVettoriEsclusi", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/firmadigitale/YFirmaDigitVettoriEsclusi.js"); 
  YFirmaDigitVettoriEsclusiForm.setServletEnvironment(se); 
  YFirmaDigitVettoriEsclusiForm.setJSTypeList(jsList); 
  YFirmaDigitVettoriEsclusiForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YFirmaDigitVettoriEsclusiForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YFirmaDigitVettoriEsclusiForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YFirmaDigitVettoriEsclusiForm.getMode(); 
  String key = YFirmaDigitVettoriEsclusiForm.getKey(); 
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
        YFirmaDigitVettoriEsclusiForm.outTraceInfo(getClass().getName()); 
        String collectorName = YFirmaDigitVettoriEsclusiForm.findBODataCollectorName(); 
                YFirmaDigitVettoriEsclusiBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YFirmaDigitVettoriEsclusiBODC instanceof WebDataCollector) 
            ((WebDataCollector)YFirmaDigitVettoriEsclusiBODC).setServletEnvironment(se); 
        YFirmaDigitVettoriEsclusiBODC.initialize("YFirmaDigitVettoriEsclusi", true, 0); 
        YFirmaDigitVettoriEsclusiForm.setBODataCollector(YFirmaDigitVettoriEsclusiBODC); 
        int rcBODC = YFirmaDigitVettoriEsclusiForm.initSecurityServices(); 
        mode = YFirmaDigitVettoriEsclusiForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YFirmaDigitVettoriEsclusiForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YFirmaDigitVettoriEsclusiBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YFirmaDigitVettoriEsclusiForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YFirmaDigitVettoriEsclusiForm); 
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
  myToolBarTB.setParent(YFirmaDigitVettoriEsclusiForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YFirmaDigitVettoriEsclusiForm.getBodyOnBeforeUnload()%>" onload="<%=YFirmaDigitVettoriEsclusiForm.getBodyOnLoad()%>" onunload="<%=YFirmaDigitVettoriEsclusiForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YFirmaDigitVettoriEsclusiForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YFirmaDigitVettoriEsclusiForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YFirmaDigitVettoriEsclusiBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YFirmaDigitVettoriEsclusiForm.getServlet()%>" method="post" name="YFirmaDigitVettoriEsclusiForm" style="height:100%"><%
  YFirmaDigitVettoriEsclusiForm.writeFormStartElements(out); 
%>

      <table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" width="100%">
        <tr>
          <td style="height:0">
            <% menuBar.writeElements(out); %> 

          </td>
        </tr>
        <tr>
          <td style="height:0">
            <% myToolBarTB.writeChildren(out); %> 

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput YFirmaDigitVettoriEsclusiIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YFirmaDigitVettoriEsclusi", "IdAzienda"); 
  YFirmaDigitVettoriEsclusiIdAzienda.setParent(YFirmaDigitVettoriEsclusiForm); 
%>
<input class="<%=YFirmaDigitVettoriEsclusiIdAzienda.getClassType()%>" id="<%=YFirmaDigitVettoriEsclusiIdAzienda.getId()%>" maxlength="<%=YFirmaDigitVettoriEsclusiIdAzienda.getMaxLength()%>" name="<%=YFirmaDigitVettoriEsclusiIdAzienda.getName()%>" size="<%=YFirmaDigitVettoriEsclusiIdAzienda.getSize()%>" type="hidden"><% 
  YFirmaDigitVettoriEsclusiIdAzienda.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput YFirmaDigitVettoriEsclusiIdVettore =  
     new com.thera.thermfw.web.WebTextInput("YFirmaDigitVettoriEsclusi", "IdVettore"); 
  YFirmaDigitVettoriEsclusiIdVettore.setParent(YFirmaDigitVettoriEsclusiForm); 
%>
<input class="<%=YFirmaDigitVettoriEsclusiIdVettore.getClassType()%>" id="<%=YFirmaDigitVettoriEsclusiIdVettore.getId()%>" maxlength="<%=YFirmaDigitVettoriEsclusiIdVettore.getMaxLength()%>" name="<%=YFirmaDigitVettoriEsclusiIdVettore.getName()%>" size="<%=YFirmaDigitVettoriEsclusiIdVettore.getSize()%>" type="hidden"><% 
  YFirmaDigitVettoriEsclusiIdVettore.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td height="100%">
            <!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(YFirmaDigitVettoriEsclusiForm); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;">
            </div><% mytabbed.endTabbed();%> 

     </td>
   </tr>
</table><!--</span>-->
          </td>
        </tr>
        <tr>
          <td style="height:0">
            <% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YFirmaDigitVettoriEsclusiForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YFirmaDigitVettoriEsclusiForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YFirmaDigitVettoriEsclusiForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YFirmaDigitVettoriEsclusiBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YFirmaDigitVettoriEsclusiForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YFirmaDigitVettoriEsclusiBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YFirmaDigitVettoriEsclusiBODC.getErrorList().getErrors()); 
           if(YFirmaDigitVettoriEsclusiBODC.getConflict() != null) 
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
     if(YFirmaDigitVettoriEsclusiBODC != null && !YFirmaDigitVettoriEsclusiBODC.close(false)) 
        errors.addAll(0, YFirmaDigitVettoriEsclusiBODC.getErrorList().getErrors()); 
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
     String errorPage = YFirmaDigitVettoriEsclusiForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YFirmaDigitVettoriEsclusiBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YFirmaDigitVettoriEsclusiForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
