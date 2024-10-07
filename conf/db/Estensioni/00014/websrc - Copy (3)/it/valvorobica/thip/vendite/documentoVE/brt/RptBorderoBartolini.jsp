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
  BODataCollector RptBorderoBartoliniBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm RptBorderoBartoliniForm =  
     new com.thera.thermfw.web.WebForm(request, response, "RptBorderoBartoliniForm", "RptBorderoBartolini", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/vendite/documentoVE/brt/RptBorderoBartolini.js"); 
  RptBorderoBartoliniForm.setServletEnvironment(se); 
  RptBorderoBartoliniForm.setJSTypeList(jsList); 
  RptBorderoBartoliniForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  RptBorderoBartoliniForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  RptBorderoBartoliniForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = RptBorderoBartoliniForm.getMode(); 
  String key = RptBorderoBartoliniForm.getKey(); 
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
        RptBorderoBartoliniForm.outTraceInfo(getClass().getName()); 
        String collectorName = RptBorderoBartoliniForm.findBODataCollectorName(); 
                RptBorderoBartoliniBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (RptBorderoBartoliniBODC instanceof WebDataCollector) 
            ((WebDataCollector)RptBorderoBartoliniBODC).setServletEnvironment(se); 
        RptBorderoBartoliniBODC.initialize("RptBorderoBartolini", true, 0); 
        RptBorderoBartoliniForm.setBODataCollector(RptBorderoBartoliniBODC); 
        int rcBODC = RptBorderoBartoliniForm.initSecurityServices(); 
        mode = RptBorderoBartoliniForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           RptBorderoBartoliniForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = RptBorderoBartoliniBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              RptBorderoBartoliniForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(RptBorderoBartoliniForm); 
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
  myToolBarTB.setParent(RptBorderoBartoliniForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=RptBorderoBartoliniForm.getBodyOnBeforeUnload()%>" onload="<%=RptBorderoBartoliniForm.getBodyOnLoad()%>" onunload="<%=RptBorderoBartoliniForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   RptBorderoBartoliniForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = RptBorderoBartoliniForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", RptBorderoBartoliniBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=RptBorderoBartoliniForm.getServlet()%>" method="post" name="RptBorderoBartoliniForm" style="height:100%"><%
  RptBorderoBartoliniForm.writeFormStartElements(out); 
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
  WebTextInput RptBorderoBartoliniBatchJobId =  
     new com.thera.thermfw.web.WebTextInput("RptBorderoBartolini", "BatchJobId"); 
  RptBorderoBartoliniBatchJobId.setParent(RptBorderoBartoliniForm); 
%>
<input class="<%=RptBorderoBartoliniBatchJobId.getClassType()%>" id="<%=RptBorderoBartoliniBatchJobId.getId()%>" maxlength="<%=RptBorderoBartoliniBatchJobId.getMaxLength()%>" name="<%=RptBorderoBartoliniBatchJobId.getName()%>" size="<%=RptBorderoBartoliniBatchJobId.getSize()%>" type="hidden"><% 
  RptBorderoBartoliniBatchJobId.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput RptBorderoBartoliniRigaJobId =  
     new com.thera.thermfw.web.WebTextInput("RptBorderoBartolini", "RigaJobId"); 
  RptBorderoBartoliniRigaJobId.setParent(RptBorderoBartoliniForm); 
%>
<input class="<%=RptBorderoBartoliniRigaJobId.getClassType()%>" id="<%=RptBorderoBartoliniRigaJobId.getId()%>" maxlength="<%=RptBorderoBartoliniRigaJobId.getMaxLength()%>" name="<%=RptBorderoBartoliniRigaJobId.getName()%>" size="<%=RptBorderoBartoliniRigaJobId.getSize()%>" type="hidden"><% 
  RptBorderoBartoliniRigaJobId.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput RptBorderoBartoliniReportNr =  
     new com.thera.thermfw.web.WebTextInput("RptBorderoBartolini", "ReportNr"); 
  RptBorderoBartoliniReportNr.setParent(RptBorderoBartoliniForm); 
%>
<input class="<%=RptBorderoBartoliniReportNr.getClassType()%>" id="<%=RptBorderoBartoliniReportNr.getId()%>" maxlength="<%=RptBorderoBartoliniReportNr.getMaxLength()%>" name="<%=RptBorderoBartoliniReportNr.getName()%>" size="<%=RptBorderoBartoliniReportNr.getSize()%>" type="hidden"><% 
  RptBorderoBartoliniReportNr.write(out); 
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
  mytabbed.setParent(RptBorderoBartoliniForm); 
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
  errorList.setParent(RptBorderoBartoliniForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  RptBorderoBartoliniForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = RptBorderoBartoliniForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", RptBorderoBartoliniBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              RptBorderoBartoliniForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, RptBorderoBartoliniBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, RptBorderoBartoliniBODC.getErrorList().getErrors()); 
           if(RptBorderoBartoliniBODC.getConflict() != null) 
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
     if(RptBorderoBartoliniBODC != null && !RptBorderoBartoliniBODC.close(false)) 
        errors.addAll(0, RptBorderoBartoliniBODC.getErrorList().getErrors()); 
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
     String errorPage = RptBorderoBartoliniForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", RptBorderoBartoliniBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = RptBorderoBartoliniForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
