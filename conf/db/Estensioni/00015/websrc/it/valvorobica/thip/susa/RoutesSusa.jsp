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
  BODataCollector RoutesSusaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm RoutesSusaForm =  
     new com.thera.thermfw.web.WebForm(request, response, "RoutesSusaForm", "RoutesSusa", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/susa/RoutesSusa.js"); 
  RoutesSusaForm.setServletEnvironment(se); 
  RoutesSusaForm.setJSTypeList(jsList); 
  RoutesSusaForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  RoutesSusaForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  RoutesSusaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = RoutesSusaForm.getMode(); 
  String key = RoutesSusaForm.getKey(); 
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
        RoutesSusaForm.outTraceInfo(getClass().getName()); 
        String collectorName = RoutesSusaForm.findBODataCollectorName(); 
                RoutesSusaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (RoutesSusaBODC instanceof WebDataCollector) 
            ((WebDataCollector)RoutesSusaBODC).setServletEnvironment(se); 
        RoutesSusaBODC.initialize("RoutesSusa", true, 0); 
        RoutesSusaForm.setBODataCollector(RoutesSusaBODC); 
        int rcBODC = RoutesSusaForm.initSecurityServices(); 
        mode = RoutesSusaForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           RoutesSusaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = RoutesSusaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              RoutesSusaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(RoutesSusaForm); 
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
  myToolBarTB.setParent(RoutesSusaForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=RoutesSusaForm.getBodyOnBeforeUnload()%>" onload="<%=RoutesSusaForm.getBodyOnLoad()%>" onunload="<%=RoutesSusaForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   RoutesSusaForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = RoutesSusaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", RoutesSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=RoutesSusaForm.getServlet()%>" method="post" name="RoutesSusaForm" style="height:100%"><%
  RoutesSusaForm.writeFormStartElements(out); 
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
          <td height="100%">
            <!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(RoutesSusaForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.susa.resources.RoutesSusa", "tab1", "RoutesSusa", null, null, null, null); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;">
              <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab1")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab1"); %>
                <table style="width: 100%;">
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "RoutesSusa", "DescrizioneServzio", null); 
   label.setParent(RoutesSusaForm); 
%><label class="<%=label.getClassType()%>" for="DescrizioneServzio"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput RoutesSusaDescrizioneServzio =  
     new com.thera.thermfw.web.WebTextInput("RoutesSusa", "DescrizioneServzio"); 
  RoutesSusaDescrizioneServzio.setParent(RoutesSusaForm); 
%>
<input class="<%=RoutesSusaDescrizioneServzio.getClassType()%>" id="<%=RoutesSusaDescrizioneServzio.getId()%>" maxlength="<%=RoutesSusaDescrizioneServzio.getMaxLength()%>" name="<%=RoutesSusaDescrizioneServzio.getName()%>" size="<%=RoutesSusaDescrizioneServzio.getSize()%>"><% 
  RoutesSusaDescrizioneServzio.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "RoutesSusa", "FilialePartenza", null); 
   label.setParent(RoutesSusaForm); 
%><label class="<%=label.getClassType()%>" for="FilialePartenza"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput RoutesSusaFilialePartenza =  
     new com.thera.thermfw.web.WebTextInput("RoutesSusa", "FilialePartenza"); 
  RoutesSusaFilialePartenza.setParent(RoutesSusaForm); 
%>
<input class="<%=RoutesSusaFilialePartenza.getClassType()%>" id="<%=RoutesSusaFilialePartenza.getId()%>" maxlength="<%=RoutesSusaFilialePartenza.getMaxLength()%>" name="<%=RoutesSusaFilialePartenza.getName()%>" size="<%=RoutesSusaFilialePartenza.getSize()%>"><% 
  RoutesSusaFilialePartenza.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "RoutesSusa", "CodiceInstradamento", null); 
   label.setParent(RoutesSusaForm); 
%><label class="<%=label.getClassType()%>" for="CodiceInstradamento"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput RoutesSusaCodiceInstradamento =  
     new com.thera.thermfw.web.WebTextInput("RoutesSusa", "CodiceInstradamento"); 
  RoutesSusaCodiceInstradamento.setParent(RoutesSusaForm); 
%>
<input class="<%=RoutesSusaCodiceInstradamento.getClassType()%>" id="<%=RoutesSusaCodiceInstradamento.getId()%>" maxlength="<%=RoutesSusaCodiceInstradamento.getMaxLength()%>" name="<%=RoutesSusaCodiceInstradamento.getName()%>" size="<%=RoutesSusaCodiceInstradamento.getSize()%>"><% 
  RoutesSusaCodiceInstradamento.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "RoutesSusa", "CodiceLinea", null); 
   label.setParent(RoutesSusaForm); 
%><label class="<%=label.getClassType()%>" for="CodiceLinea"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput RoutesSusaCodiceLinea =  
     new com.thera.thermfw.web.WebTextInput("RoutesSusa", "CodiceLinea"); 
  RoutesSusaCodiceLinea.setParent(RoutesSusaForm); 
%>
<input class="<%=RoutesSusaCodiceLinea.getClassType()%>" id="<%=RoutesSusaCodiceLinea.getId()%>" maxlength="<%=RoutesSusaCodiceLinea.getMaxLength()%>" name="<%=RoutesSusaCodiceLinea.getName()%>" size="<%=RoutesSusaCodiceLinea.getSize()%>"><% 
  RoutesSusaCodiceLinea.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "RoutesSusa", "CodiceZona", null); 
   label.setParent(RoutesSusaForm); 
%><label class="<%=label.getClassType()%>" for="CodiceZona"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput RoutesSusaCodiceZona =  
     new com.thera.thermfw.web.WebTextInput("RoutesSusa", "CodiceZona"); 
  RoutesSusaCodiceZona.setParent(RoutesSusaForm); 
%>
<input class="<%=RoutesSusaCodiceZona.getClassType()%>" id="<%=RoutesSusaCodiceZona.getId()%>" maxlength="<%=RoutesSusaCodiceZona.getMaxLength()%>" name="<%=RoutesSusaCodiceZona.getName()%>" size="<%=RoutesSusaCodiceZona.getSize()%>"><% 
  RoutesSusaCodiceZona.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "RoutesSusa", "DescrzioneLinea", null); 
   label.setParent(RoutesSusaForm); 
%><label class="<%=label.getClassType()%>" for="DescrzioneLinea"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput RoutesSusaDescrzioneLinea =  
     new com.thera.thermfw.web.WebTextInput("RoutesSusa", "DescrzioneLinea"); 
  RoutesSusaDescrzioneLinea.setParent(RoutesSusaForm); 
%>
<input class="<%=RoutesSusaDescrzioneLinea.getClassType()%>" id="<%=RoutesSusaDescrzioneLinea.getId()%>" maxlength="<%=RoutesSusaDescrzioneLinea.getMaxLength()%>" name="<%=RoutesSusaDescrzioneLinea.getName()%>" size="<%=RoutesSusaDescrzioneLinea.getSize()%>"><% 
  RoutesSusaDescrzioneLinea.write(out); 
%>

                    </td>
                  </tr>
                </table>
              <% mytabbed.endTab(); %> 
</div>
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
  errorList.setParent(RoutesSusaForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  RoutesSusaForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = RoutesSusaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", RoutesSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              RoutesSusaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, RoutesSusaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, RoutesSusaBODC.getErrorList().getErrors()); 
           if(RoutesSusaBODC.getConflict() != null) 
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
     if(RoutesSusaBODC != null && !RoutesSusaBODC.close(false)) 
        errors.addAll(0, RoutesSusaBODC.getErrorList().getErrors()); 
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
     String errorPage = RoutesSusaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", RoutesSusaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = RoutesSusaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
