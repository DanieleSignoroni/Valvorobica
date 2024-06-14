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
     new com.thera.thermfw.web.WebForm(request, response, "YMatchMatricolaArticoloForm", "YMatchMatricolaArticolo", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/matricole/YMatchMatricolaArticolo.js"); 
  YMatchMatricolaArticoloForm.setServletEnvironment(se); 
  YMatchMatricolaArticoloForm.setJSTypeList(jsList); 
  YMatchMatricolaArticoloForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
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
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YMatchMatricolaArticoloForm); 
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
  myToolBarTB.setParent(YMatchMatricolaArticoloForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>

<body onbeforeunload="<%=YMatchMatricolaArticoloForm.getBodyOnBeforeUnload()%>" onload="<%=YMatchMatricolaArticoloForm.getBodyOnLoad()%>" onunload="<%=YMatchMatricolaArticoloForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
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
<form action="<%=YMatchMatricolaArticoloForm.getServlet()%>" method="post" name="YMatchMatricolaArticoloForm" style="height:100%"><%
  YMatchMatricolaArticoloForm.writeFormStartElements(out); 
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
  mytabbed.setParent(YMatchMatricolaArticoloForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.base.matricole.resources.YMatchMatricolaArticolo", "tab1", "YMatchMatricolaArticolo", null, null, null, null); 
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
                    <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatchMatricolaArticolo", "IdMatricolaDa", null); 
   label.setParent(YMatchMatricolaArticoloForm); 
%><label class="<%=label.getClassType()%>" for="MatricolaInizio"><%label.write(out);%></label><%}%>
                  </td>
                  <td valign="top">
                    <% 
  WebMultiSearchForm YMatchMatricolaArticoloMatricolaInizio =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMatchMatricolaArticolo", "MatricolaInizio", false, false, true, 1, null, null); 
  YMatchMatricolaArticoloMatricolaInizio.setParent(YMatchMatricolaArticoloForm); 
  YMatchMatricolaArticoloMatricolaInizio.write(out); 
%>
<!--<span class="multisearchform" id="MatricolaInizio"></span>-->
                  </td>
                  </tr>
                  <tr>
                  <td valign="top">
                    <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatchMatricolaArticolo", "IdMatricolaA", null); 
   label.setParent(YMatchMatricolaArticoloForm); 
%><label class="<%=label.getClassType()%>" for="MatricolaFine"><%label.write(out);%></label><%}%>
                  </td>
                  <td valign="top">
                    <% 
  WebMultiSearchForm YMatchMatricolaArticoloMatricolaFine =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMatchMatricolaArticolo", "MatricolaFine", false, false, true, 1, null, null); 
  YMatchMatricolaArticoloMatricolaFine.setParent(YMatchMatricolaArticoloForm); 
  YMatchMatricolaArticoloMatricolaFine.write(out); 
%>
<!--<span class="multisearchform" id="MatricolaFine"></span>-->
                  </td>
                </tr>
                <tr>
                  <td valign="top">
                    <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatchMatricolaArticolo", "IdArticolo", null); 
   label.setParent(YMatchMatricolaArticoloForm); 
%><label class="<%=label.getClassType()%>" for="Articolo"><%label.write(out);%></label><%}%>
                  </td>
                  <td valign="top">
                    <% 
  WebMultiSearchForm YMatchMatricolaArticoloArticolo =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMatchMatricolaArticolo", "Articolo", false, false, true, 1, null, null); 
  YMatchMatricolaArticoloArticolo.setParent(YMatchMatricolaArticoloForm); 
  YMatchMatricolaArticoloArticolo.write(out); 
%>
<!--<span class="multisearchform" id="Articolo"></span>-->
                  </td>
                </tr>
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
  YMatchMatricolaArticoloLotto.write(out); 
%>
<!--<span class="multisearchform" id="Lotto"></span>-->
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
  errorList.setParent(YMatchMatricolaArticoloForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
        </td>
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
