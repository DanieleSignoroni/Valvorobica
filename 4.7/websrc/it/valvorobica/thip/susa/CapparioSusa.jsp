<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.1.0/websrcsvil/dtd/xhtml1-transitional.dtd">
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
  BODataCollector CapparioSusaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm CapparioSusaForm =  
     new com.thera.thermfw.web.WebForm(request, response, "CapparioSusaForm", "CapparioSusa", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/susa/CapparioSusa.js"); 
  CapparioSusaForm.setServletEnvironment(se); 
  CapparioSusaForm.setJSTypeList(jsList); 
  CapparioSusaForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  CapparioSusaForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  CapparioSusaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = CapparioSusaForm.getMode(); 
  String key = CapparioSusaForm.getKey(); 
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
        CapparioSusaForm.outTraceInfo(getClass().getName()); 
        String collectorName = CapparioSusaForm.findBODataCollectorName(); 
                CapparioSusaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (CapparioSusaBODC instanceof WebDataCollector) 
            ((WebDataCollector)CapparioSusaBODC).setServletEnvironment(se); 
        CapparioSusaBODC.initialize("CapparioSusa", true, 0); 
        CapparioSusaForm.setBODataCollector(CapparioSusaBODC); 
        int rcBODC = CapparioSusaForm.initSecurityServices(); 
        mode = CapparioSusaForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           CapparioSusaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = CapparioSusaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              CapparioSusaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(CapparioSusaForm); 
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
  myToolBarTB.setParent(CapparioSusaForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=CapparioSusaForm.getBodyOnBeforeUnload()%>" onload="<%=CapparioSusaForm.getBodyOnLoad()%>" onunload="<%=CapparioSusaForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   CapparioSusaForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = CapparioSusaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", CapparioSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=CapparioSusaForm.getServlet()%>" method="post" name="CapparioSusaForm" style="height:100%"><%
  CapparioSusaForm.writeFormStartElements(out); 
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
  mytabbed.setParent(CapparioSusaForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.susa.resources.CapparioSusa", "tab1", "CapparioSusa", null, null, null, null); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "CapparioSusa", "Cap", null); 
   label.setParent(CapparioSusaForm); 
%><label class="<%=label.getClassType()%>" for="Cap"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput CapparioSusaCap =  
     new com.thera.thermfw.web.WebTextInput("CapparioSusa", "Cap"); 
  CapparioSusaCap.setParent(CapparioSusaForm); 
%>
<input class="<%=CapparioSusaCap.getClassType()%>" id="<%=CapparioSusaCap.getId()%>" maxlength="<%=CapparioSusaCap.getMaxLength()%>" name="<%=CapparioSusaCap.getName()%>" size="<%=CapparioSusaCap.getSize()%>"><% 
  CapparioSusaCap.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "CapparioSusa", "Localita", null); 
   label.setParent(CapparioSusaForm); 
%><label class="<%=label.getClassType()%>" for="Localita"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput CapparioSusaLocalita =  
     new com.thera.thermfw.web.WebTextInput("CapparioSusa", "Localita"); 
  CapparioSusaLocalita.setParent(CapparioSusaForm); 
%>
<input class="<%=CapparioSusaLocalita.getClassType()%>" id="<%=CapparioSusaLocalita.getId()%>" maxlength="<%=CapparioSusaLocalita.getMaxLength()%>" name="<%=CapparioSusaLocalita.getName()%>" size="<%=CapparioSusaLocalita.getSize()%>"><% 
  CapparioSusaLocalita.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "CapparioSusa", "CodiceInstradamento", null); 
   label.setParent(CapparioSusaForm); 
%><label class="<%=label.getClassType()%>" for="CodiceInstradamento"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput CapparioSusaCodiceInstradamento =  
     new com.thera.thermfw.web.WebTextInput("CapparioSusa", "CodiceInstradamento"); 
  CapparioSusaCodiceInstradamento.setParent(CapparioSusaForm); 
%>
<input class="<%=CapparioSusaCodiceInstradamento.getClassType()%>" id="<%=CapparioSusaCodiceInstradamento.getId()%>" maxlength="<%=CapparioSusaCodiceInstradamento.getMaxLength()%>" name="<%=CapparioSusaCodiceInstradamento.getName()%>" size="<%=CapparioSusaCodiceInstradamento.getSize()%>"><% 
  CapparioSusaCodiceInstradamento.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "CapparioSusa", "IdProvincia", null); 
   label.setParent(CapparioSusaForm); 
%><label class="<%=label.getClassType()%>" for="IdProvincia"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput CapparioSusaIdProvincia =  
     new com.thera.thermfw.web.WebTextInput("CapparioSusa", "IdProvincia"); 
  CapparioSusaIdProvincia.setParent(CapparioSusaForm); 
%>
<input class="<%=CapparioSusaIdProvincia.getClassType()%>" id="<%=CapparioSusaIdProvincia.getId()%>" maxlength="<%=CapparioSusaIdProvincia.getMaxLength()%>" name="<%=CapparioSusaIdProvincia.getName()%>" size="<%=CapparioSusaIdProvincia.getSize()%>"><% 
  CapparioSusaIdProvincia.write(out); 
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
  errorList.setParent(CapparioSusaForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  CapparioSusaForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = CapparioSusaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", CapparioSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              CapparioSusaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, CapparioSusaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, CapparioSusaBODC.getErrorList().getErrors()); 
           if(CapparioSusaBODC.getConflict() != null) 
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
     if(CapparioSusaBODC != null && !CapparioSusaBODC.close(false)) 
        errors.addAll(0, CapparioSusaBODC.getErrorList().getErrors()); 
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
     String errorPage = CapparioSusaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", CapparioSusaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = CapparioSusaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
