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
  BODataCollector EtichetteBartoliniBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm EtichetteBartoliniForm =  
     new com.thera.thermfw.web.WebForm(request, response, "EtichetteBartoliniForm", "EtichetteBartolini", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/vendite/documentoVE/brt/EtichetteBartolini.js"); 
  EtichetteBartoliniForm.setServletEnvironment(se); 
  EtichetteBartoliniForm.setJSTypeList(jsList); 
  EtichetteBartoliniForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  EtichetteBartoliniForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  EtichetteBartoliniForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = EtichetteBartoliniForm.getMode(); 
  String key = EtichetteBartoliniForm.getKey(); 
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
        EtichetteBartoliniForm.outTraceInfo(getClass().getName()); 
        String collectorName = EtichetteBartoliniForm.findBODataCollectorName(); 
                EtichetteBartoliniBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (EtichetteBartoliniBODC instanceof WebDataCollector) 
            ((WebDataCollector)EtichetteBartoliniBODC).setServletEnvironment(se); 
        EtichetteBartoliniBODC.initialize("EtichetteBartolini", true, 0); 
        EtichetteBartoliniForm.setBODataCollector(EtichetteBartoliniBODC); 
        int rcBODC = EtichetteBartoliniForm.initSecurityServices(); 
        mode = EtichetteBartoliniForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           EtichetteBartoliniForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = EtichetteBartoliniBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              EtichetteBartoliniForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(EtichetteBartoliniForm); 
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
  myToolBarTB.setParent(EtichetteBartoliniForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=EtichetteBartoliniForm.getBodyOnBeforeUnload()%>" onload="<%=EtichetteBartoliniForm.getBodyOnLoad()%>" onunload="<%=EtichetteBartoliniForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   EtichetteBartoliniForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = EtichetteBartoliniForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", EtichetteBartoliniBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=EtichetteBartoliniForm.getServlet()%>" method="post" name="EtichetteBartoliniForm" style="height:100%"><%
  EtichetteBartoliniForm.writeFormStartElements(out); 
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
  WebTextInput EtichetteBartoliniIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("EtichetteBartolini", "IdAzienda"); 
  EtichetteBartoliniIdAzienda.setParent(EtichetteBartoliniForm); 
%>
<input class="<%=EtichetteBartoliniIdAzienda.getClassType()%>" id="<%=EtichetteBartoliniIdAzienda.getId()%>" maxlength="<%=EtichetteBartoliniIdAzienda.getMaxLength()%>" name="<%=EtichetteBartoliniIdAzienda.getName()%>" size="<%=EtichetteBartoliniIdAzienda.getSize()%>" type="hidden"><% 
  EtichetteBartoliniIdAzienda.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput EtichetteBartoliniId =  
     new com.thera.thermfw.web.WebTextInput("EtichetteBartolini", "Id"); 
  EtichetteBartoliniId.setParent(EtichetteBartoliniForm); 
%>
<input class="<%=EtichetteBartoliniId.getClassType()%>" id="<%=EtichetteBartoliniId.getId()%>" maxlength="<%=EtichetteBartoliniId.getMaxLength()%>" name="<%=EtichetteBartoliniId.getName()%>" size="<%=EtichetteBartoliniId.getSize()%>" type="hidden"><% 
  EtichetteBartoliniId.write(out); 
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
  mytabbed.setParent(EtichetteBartoliniForm); 
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
  errorList.setParent(EtichetteBartoliniForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  EtichetteBartoliniForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = EtichetteBartoliniForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", EtichetteBartoliniBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              EtichetteBartoliniForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, EtichetteBartoliniBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, EtichetteBartoliniBODC.getErrorList().getErrors()); 
           if(EtichetteBartoliniBODC.getConflict() != null) 
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
     if(EtichetteBartoliniBODC != null && !EtichetteBartoliniBODC.close(false)) 
        errors.addAll(0, EtichetteBartoliniBODC.getErrorList().getErrors()); 
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
     String errorPage = EtichetteBartoliniForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", EtichetteBartoliniBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = EtichetteBartoliniForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
