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
  BODataCollector YEtichettaSusaBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YEtichettaSusaForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YEtichettaSusaForm", "YEtichettaSusa", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/susa/YEtichettaSusa.js"); 
  YEtichettaSusaForm.setServletEnvironment(se); 
  YEtichettaSusaForm.setJSTypeList(jsList); 
  YEtichettaSusaForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YEtichettaSusaForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YEtichettaSusaForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YEtichettaSusaForm.getMode(); 
  String key = YEtichettaSusaForm.getKey(); 
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
        YEtichettaSusaForm.outTraceInfo(getClass().getName()); 
        String collectorName = YEtichettaSusaForm.findBODataCollectorName(); 
                YEtichettaSusaBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YEtichettaSusaBODC instanceof WebDataCollector) 
            ((WebDataCollector)YEtichettaSusaBODC).setServletEnvironment(se); 
        YEtichettaSusaBODC.initialize("YEtichettaSusa", true, 0); 
        YEtichettaSusaForm.setBODataCollector(YEtichettaSusaBODC); 
        int rcBODC = YEtichettaSusaForm.initSecurityServices(); 
        mode = YEtichettaSusaForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YEtichettaSusaForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YEtichettaSusaBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YEtichettaSusaForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YEtichettaSusaForm); 
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
  myToolBarTB.setParent(YEtichettaSusaForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YEtichettaSusaForm.getBodyOnBeforeUnload()%>" onload="<%=YEtichettaSusaForm.getBodyOnLoad()%>" onunload="<%=YEtichettaSusaForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YEtichettaSusaForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YEtichettaSusaForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YEtichettaSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YEtichettaSusaForm.getServlet()%>" method="post" name="YEtichettaSusaForm" style="height:100%"><%
  YEtichettaSusaForm.writeFormStartElements(out); 
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
  mytabbed.setParent(YEtichettaSusaForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.susa.resources.YEtichettaSusa", "tab1", "YEtichettaSusa", null, null, null, null); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YEtichettaSusa", "CodUds", null); 
   label.setParent(YEtichettaSusaForm); 
%><label class="<%=label.getClassType()%>" for="TestataUds"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YEtichettaSusaTestataUds =  
     new com.thera.thermfw.web.WebMultiSearchForm("YEtichettaSusa", "TestataUds", false, false, true, 1, null, null); 
  YEtichettaSusaTestataUds.setParent(YEtichettaSusaForm); 
  YEtichettaSusaTestataUds.write(out); 
%>
<!--<span class="multisearchform" id="TestataUds"></span>-->
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
   request.setAttribute("parentForm", YEtichettaSusaForm); 
   String CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp = "DatiComuniEstesi"; 
%>
<jsp:include page="/it/thera/thip/cs/DatiComuniEstesi.jsp" flush="true"> 
<jsp:param name="CDName" value="<%=CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp%>"/> 
</jsp:include> 
<!--<span class="subform" id="DatiComuniEstesi"></span>-->
                    </td>
                    <td valign="top">
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
  errorList.setParent(YEtichettaSusaForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YEtichettaSusaForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YEtichettaSusaForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YEtichettaSusaBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YEtichettaSusaForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YEtichettaSusaBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YEtichettaSusaBODC.getErrorList().getErrors()); 
           if(YEtichettaSusaBODC.getConflict() != null) 
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
     if(YEtichettaSusaBODC != null && !YEtichettaSusaBODC.close(false)) 
        errors.addAll(0, YEtichettaSusaBODC.getErrorList().getErrors()); 
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
     String errorPage = YEtichettaSusaForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YEtichettaSusaBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YEtichettaSusaForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
