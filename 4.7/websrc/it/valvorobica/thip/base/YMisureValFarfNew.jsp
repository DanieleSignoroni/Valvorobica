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
  BODataCollector YMisureValFarfNewBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YMisureValFarfNewForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YMisureValFarfNewForm", "YMisureValFarfNew", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/YMisureValFarfNew.js"); 
  YMisureValFarfNewForm.setServletEnvironment(se); 
  YMisureValFarfNewForm.setJSTypeList(jsList); 
  YMisureValFarfNewForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YMisureValFarfNewForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YMisureValFarfNewForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YMisureValFarfNewForm.getMode(); 
  String key = YMisureValFarfNewForm.getKey(); 
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
        YMisureValFarfNewForm.outTraceInfo(getClass().getName()); 
        String collectorName = YMisureValFarfNewForm.findBODataCollectorName(); 
                YMisureValFarfNewBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YMisureValFarfNewBODC instanceof WebDataCollector) 
            ((WebDataCollector)YMisureValFarfNewBODC).setServletEnvironment(se); 
        YMisureValFarfNewBODC.initialize("YMisureValFarfNew", true, 0); 
        YMisureValFarfNewForm.setBODataCollector(YMisureValFarfNewBODC); 
        int rcBODC = YMisureValFarfNewForm.initSecurityServices(); 
        mode = YMisureValFarfNewForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YMisureValFarfNewForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YMisureValFarfNewBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YMisureValFarfNewForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YMisureValFarfNewForm); 
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
  myToolBarTB.setParent(YMisureValFarfNewForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YMisureValFarfNewForm.getBodyOnBeforeUnload()%>" onload="<%=YMisureValFarfNewForm.getBodyOnLoad()%>" onunload="<%=YMisureValFarfNewForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YMisureValFarfNewForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YMisureValFarfNewForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YMisureValFarfNewBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YMisureValFarfNewForm.getServlet()%>" method="post" name="YMisureValFarfNewForm" style="height:100%"><%
  YMisureValFarfNewForm.writeFormStartElements(out); 
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
  mytabbed.setParent(YMisureValFarfNewForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.base.resources.YMisureValFarfNew", "tab1", "YMisureValFarfNew", null, null, null, null); 
 mytabbed.addTab("tab2", "it.valvorobica.thip.base.resources.YMisureValFarfNew", "tab2", "YMisureValFarfNew", null, null, null, null); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "IdFornitore", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="RelFornitore"><%label.write(out);%></label><%}%>
                    </td>
                    <td colspan="2" valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfNewRelFornitore =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfNew", "RelFornitore", false, false, true, 1, null, null); 
  YMisureValFarfNewRelFornitore.setParent(YMisureValFarfNewForm); 
  YMisureValFarfNewRelFornitore.write(out); 
%>
<!--<span class="multisearchform" id="RelFornitore"></span>-->
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "IdArticolo", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="RelArticolo"><%label.write(out);%></label><%}%>
                    </td>
                    <td colspan="2" valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfNewRelArticolo =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfNew", "RelArticolo", false, false, true, 1, null, null); 
  YMisureValFarfNewRelArticolo.setParent(YMisureValFarfNewForm); 
  YMisureValFarfNewRelArticolo.write(out); 
%>
<!--<span class="multisearchform" id="RelArticolo"></span>-->
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "IdLotto", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Lotto"><%label.write(out);%></label><%}%>
                    </td>
                    <td colspan="2" valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfNewLotto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfNew", "Lotto", false, false, true, 1, null, null); 
  YMisureValFarfNewLotto.setParent(YMisureValFarfNewForm); 
  YMisureValFarfNewLotto.setAdditionalRestrictConditions("IdFornitore,CodiceFornitore;IdArticolo,CodiceArticolo"); 
  YMisureValFarfNewLotto.write(out); 
%>
<!--<span class="multisearchform" id="Lotto" name="Lotto"></span>-->
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "IdMatricola", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Matricola"><%label.write(out);%></label><%}%>
                    </td>
                    <td colspan="2" valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfNewMatricola =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfNew", "Matricola", false, false, true, 1, null, null); 
  YMisureValFarfNewMatricola.setParent(YMisureValFarfNewForm); 
  YMisureValFarfNewMatricola.write(out); 
%>
<!--<span class="multisearchform" id="Matricola" name="Matricola"></span>-->
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "RNormativa", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="RelNormativa"><%label.write(out);%></label><%}%>
                    </td>
                    <td colspan="2" valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfNewRelNormativa =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfNew", "RelNormativa", false, false, true, 1, null, null); 
  YMisureValFarfNewRelNormativa.setParent(YMisureValFarfNewForm); 
  YMisureValFarfNewRelNormativa.write(out); 
%>
<!--<span class="multisearchform" id="RelNormativa"></span>-->
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "RProvenienza", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="RelProvenienza"><%label.write(out);%></label><%}%>
                    </td>
                    <td colspan="2" valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfNewRelProvenienza =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfNew", "RelProvenienza", false, false, true, 1, null, null); 
  YMisureValFarfNewRelProvenienza.setParent(YMisureValFarfNewForm); 
  YMisureValFarfNewRelProvenienza.write(out); 
%>
<!--<span class="multisearchform" id="RelProvenienza"></span>-->
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "ManStamp", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="ManStamp"><%label.write(out);%></label><%}%>
                    </td>
                    <td colspan="2" valign="top">
                      <% 
  WebTextInput YMisureValFarfNewManStamp =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "ManStamp"); 
  YMisureValFarfNewManStamp.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewManStamp.getClassType()%>" id="<%=YMisureValFarfNewManStamp.getId()%>" maxlength="<%=YMisureValFarfNewManStamp.getMaxLength()%>" name="<%=YMisureValFarfNewManStamp.getName()%>" size="<%=YMisureValFarfNewManStamp.getSize()%>"><% 
  YMisureValFarfNewManStamp.write(out); 
%>

                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
   request.setAttribute("parentForm", YMisureValFarfNewForm); 
   String CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp = "DatiComuniEstesi"; 
%>
<jsp:include page="/it/thera/thip/cs/DatiComuniEstesi.jsp" flush="true"> 
<jsp:param name="CDName" value="<%=CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp%>"/> 
</jsp:include> 
<!--<span class="subform" id="DatiComuniEstesi"></span>-->
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercC", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercC"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercMn", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercMn"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercSi", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercSi"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercP", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercP"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercS", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercS"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercNi", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercNi"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercCr", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercCr"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercMo", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercMo"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercTi", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercTi"><%label.write(out);%></label><%}%>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercC =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercC"); 
  YMisureValFarfNewPercC.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercC.getClassType()%>" id="<%=YMisureValFarfNewPercC.getId()%>" maxlength="<%=YMisureValFarfNewPercC.getMaxLength()%>" name="<%=YMisureValFarfNewPercC.getName()%>" size="<%=YMisureValFarfNewPercC.getSize()%>"><% 
  YMisureValFarfNewPercC.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercMn =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercMn"); 
  YMisureValFarfNewPercMn.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercMn.getClassType()%>" id="<%=YMisureValFarfNewPercMn.getId()%>" maxlength="<%=YMisureValFarfNewPercMn.getMaxLength()%>" name="<%=YMisureValFarfNewPercMn.getName()%>" size="<%=YMisureValFarfNewPercMn.getSize()%>"><% 
  YMisureValFarfNewPercMn.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercSi =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercSi"); 
  YMisureValFarfNewPercSi.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercSi.getClassType()%>" id="<%=YMisureValFarfNewPercSi.getId()%>" maxlength="<%=YMisureValFarfNewPercSi.getMaxLength()%>" name="<%=YMisureValFarfNewPercSi.getName()%>" size="<%=YMisureValFarfNewPercSi.getSize()%>"><% 
  YMisureValFarfNewPercSi.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercP =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercP"); 
  YMisureValFarfNewPercP.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercP.getClassType()%>" id="<%=YMisureValFarfNewPercP.getId()%>" maxlength="<%=YMisureValFarfNewPercP.getMaxLength()%>" name="<%=YMisureValFarfNewPercP.getName()%>" size="<%=YMisureValFarfNewPercP.getSize()%>"><% 
  YMisureValFarfNewPercP.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercS =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercS"); 
  YMisureValFarfNewPercS.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercS.getClassType()%>" id="<%=YMisureValFarfNewPercS.getId()%>" maxlength="<%=YMisureValFarfNewPercS.getMaxLength()%>" name="<%=YMisureValFarfNewPercS.getName()%>" size="<%=YMisureValFarfNewPercS.getSize()%>"><% 
  YMisureValFarfNewPercS.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercNi =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercNi"); 
  YMisureValFarfNewPercNi.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercNi.getClassType()%>" id="<%=YMisureValFarfNewPercNi.getId()%>" maxlength="<%=YMisureValFarfNewPercNi.getMaxLength()%>" name="<%=YMisureValFarfNewPercNi.getName()%>" size="<%=YMisureValFarfNewPercNi.getSize()%>"><% 
  YMisureValFarfNewPercNi.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercCr =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercCr"); 
  YMisureValFarfNewPercCr.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercCr.getClassType()%>" id="<%=YMisureValFarfNewPercCr.getId()%>" maxlength="<%=YMisureValFarfNewPercCr.getMaxLength()%>" name="<%=YMisureValFarfNewPercCr.getName()%>" size="<%=YMisureValFarfNewPercCr.getSize()%>"><% 
  YMisureValFarfNewPercCr.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercMo =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercMo"); 
  YMisureValFarfNewPercMo.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercMo.getClassType()%>" id="<%=YMisureValFarfNewPercMo.getId()%>" maxlength="<%=YMisureValFarfNewPercMo.getMaxLength()%>" name="<%=YMisureValFarfNewPercMo.getName()%>" size="<%=YMisureValFarfNewPercMo.getSize()%>"><% 
  YMisureValFarfNewPercMo.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercTi =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercTi"); 
  YMisureValFarfNewPercTi.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercTi.getClassType()%>" id="<%=YMisureValFarfNewPercTi.getId()%>" maxlength="<%=YMisureValFarfNewPercTi.getMaxLength()%>" name="<%=YMisureValFarfNewPercTi.getName()%>" size="<%=YMisureValFarfNewPercTi.getSize()%>"><% 
  YMisureValFarfNewPercTi.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercAl", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercAl"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercV", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercV"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercCu", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercCu"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercN", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercN"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercNb", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercNb"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercPb", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercPb"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercFe", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercFe"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercCe", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercCe"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercRe", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercRe"><%label.write(out);%></label><%}%>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercAl =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercAl"); 
  YMisureValFarfNewPercAl.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercAl.getClassType()%>" id="<%=YMisureValFarfNewPercAl.getId()%>" maxlength="<%=YMisureValFarfNewPercAl.getMaxLength()%>" name="<%=YMisureValFarfNewPercAl.getName()%>" size="<%=YMisureValFarfNewPercAl.getSize()%>"><% 
  YMisureValFarfNewPercAl.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercV =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercV"); 
  YMisureValFarfNewPercV.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercV.getClassType()%>" id="<%=YMisureValFarfNewPercV.getId()%>" maxlength="<%=YMisureValFarfNewPercV.getMaxLength()%>" name="<%=YMisureValFarfNewPercV.getName()%>" size="<%=YMisureValFarfNewPercV.getSize()%>"><% 
  YMisureValFarfNewPercV.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercCu =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercCu"); 
  YMisureValFarfNewPercCu.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercCu.getClassType()%>" id="<%=YMisureValFarfNewPercCu.getId()%>" maxlength="<%=YMisureValFarfNewPercCu.getMaxLength()%>" name="<%=YMisureValFarfNewPercCu.getName()%>" size="<%=YMisureValFarfNewPercCu.getSize()%>"><% 
  YMisureValFarfNewPercCu.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercN =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercN"); 
  YMisureValFarfNewPercN.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercN.getClassType()%>" id="<%=YMisureValFarfNewPercN.getId()%>" maxlength="<%=YMisureValFarfNewPercN.getMaxLength()%>" name="<%=YMisureValFarfNewPercN.getName()%>" size="<%=YMisureValFarfNewPercN.getSize()%>"><% 
  YMisureValFarfNewPercN.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercNb =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercNb"); 
  YMisureValFarfNewPercNb.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercNb.getClassType()%>" id="<%=YMisureValFarfNewPercNb.getId()%>" maxlength="<%=YMisureValFarfNewPercNb.getMaxLength()%>" name="<%=YMisureValFarfNewPercNb.getName()%>" size="<%=YMisureValFarfNewPercNb.getSize()%>"><% 
  YMisureValFarfNewPercNb.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercPb =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercPb"); 
  YMisureValFarfNewPercPb.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercPb.getClassType()%>" id="<%=YMisureValFarfNewPercPb.getId()%>" maxlength="<%=YMisureValFarfNewPercPb.getMaxLength()%>" name="<%=YMisureValFarfNewPercPb.getName()%>" size="<%=YMisureValFarfNewPercPb.getSize()%>"><% 
  YMisureValFarfNewPercPb.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercFe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercFe"); 
  YMisureValFarfNewPercFe.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercFe.getClassType()%>" id="<%=YMisureValFarfNewPercFe.getId()%>" maxlength="<%=YMisureValFarfNewPercFe.getMaxLength()%>" name="<%=YMisureValFarfNewPercFe.getName()%>" size="<%=YMisureValFarfNewPercFe.getSize()%>"><% 
  YMisureValFarfNewPercFe.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercCe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercCe"); 
  YMisureValFarfNewPercCe.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercCe.getClassType()%>" id="<%=YMisureValFarfNewPercCe.getId()%>" maxlength="<%=YMisureValFarfNewPercCe.getMaxLength()%>" name="<%=YMisureValFarfNewPercCe.getName()%>" size="<%=YMisureValFarfNewPercCe.getSize()%>"><% 
  YMisureValFarfNewPercCe.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercRe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercRe"); 
  YMisureValFarfNewPercRe.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercRe.getClassType()%>" id="<%=YMisureValFarfNewPercRe.getId()%>" maxlength="<%=YMisureValFarfNewPercRe.getMaxLength()%>" name="<%=YMisureValFarfNewPercRe.getName()%>" size="<%=YMisureValFarfNewPercRe.getSize()%>"><% 
  YMisureValFarfNewPercRe.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercMg", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercMg"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercSn", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercSn"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercB", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercB"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercZn", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercZn"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercMg =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercMg"); 
  YMisureValFarfNewPercMg.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercMg.getClassType()%>" id="<%=YMisureValFarfNewPercMg.getId()%>" maxlength="<%=YMisureValFarfNewPercMg.getMaxLength()%>" name="<%=YMisureValFarfNewPercMg.getName()%>" size="<%=YMisureValFarfNewPercMg.getSize()%>"><% 
  YMisureValFarfNewPercMg.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercSn =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercSn"); 
  YMisureValFarfNewPercSn.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercSn.getClassType()%>" id="<%=YMisureValFarfNewPercSn.getId()%>" maxlength="<%=YMisureValFarfNewPercSn.getMaxLength()%>" name="<%=YMisureValFarfNewPercSn.getName()%>" size="<%=YMisureValFarfNewPercSn.getSize()%>"><% 
  YMisureValFarfNewPercSn.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercB =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercB"); 
  YMisureValFarfNewPercB.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercB.getClassType()%>" id="<%=YMisureValFarfNewPercB.getId()%>" maxlength="<%=YMisureValFarfNewPercB.getMaxLength()%>" name="<%=YMisureValFarfNewPercB.getName()%>" size="<%=YMisureValFarfNewPercB.getSize()%>"><% 
  YMisureValFarfNewPercB.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercZn =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercZn"); 
  YMisureValFarfNewPercZn.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercZn.getClassType()%>" id="<%=YMisureValFarfNewPercZn.getId()%>" maxlength="<%=YMisureValFarfNewPercZn.getMaxLength()%>" name="<%=YMisureValFarfNewPercZn.getName()%>" size="<%=YMisureValFarfNewPercZn.getSize()%>"><% 
  YMisureValFarfNewPercZn.write(out); 
%>

                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "RotturaMpa", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="RotturaMpa"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "SnervMpa", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="SnervMpa"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercAllung", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercAllung"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "PercContraz", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="PercContraz"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "SampleDirT", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="SampleDirT"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "SampleDimT", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="SampleDimT"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewRotturaMpa =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "RotturaMpa"); 
  YMisureValFarfNewRotturaMpa.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewRotturaMpa.getClassType()%>" id="<%=YMisureValFarfNewRotturaMpa.getId()%>" maxlength="<%=YMisureValFarfNewRotturaMpa.getMaxLength()%>" name="<%=YMisureValFarfNewRotturaMpa.getName()%>" size="<%=YMisureValFarfNewRotturaMpa.getSize()%>"><% 
  YMisureValFarfNewRotturaMpa.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewSnervMpa =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "SnervMpa"); 
  YMisureValFarfNewSnervMpa.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewSnervMpa.getClassType()%>" id="<%=YMisureValFarfNewSnervMpa.getId()%>" maxlength="<%=YMisureValFarfNewSnervMpa.getMaxLength()%>" name="<%=YMisureValFarfNewSnervMpa.getName()%>" size="<%=YMisureValFarfNewSnervMpa.getSize()%>"><% 
  YMisureValFarfNewSnervMpa.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercAllung =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercAllung"); 
  YMisureValFarfNewPercAllung.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercAllung.getClassType()%>" id="<%=YMisureValFarfNewPercAllung.getId()%>" maxlength="<%=YMisureValFarfNewPercAllung.getMaxLength()%>" name="<%=YMisureValFarfNewPercAllung.getName()%>" size="<%=YMisureValFarfNewPercAllung.getSize()%>"><% 
  YMisureValFarfNewPercAllung.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewPercContraz =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "PercContraz"); 
  YMisureValFarfNewPercContraz.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewPercContraz.getClassType()%>" id="<%=YMisureValFarfNewPercContraz.getId()%>" maxlength="<%=YMisureValFarfNewPercContraz.getMaxLength()%>" name="<%=YMisureValFarfNewPercContraz.getName()%>" size="<%=YMisureValFarfNewPercContraz.getSize()%>"><% 
  YMisureValFarfNewPercContraz.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfNewSampleDirT =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfNew", "SampleDirT", null); 
  YMisureValFarfNewSampleDirT.setParent(YMisureValFarfNewForm); 
%>
<select id="<%=YMisureValFarfNewSampleDirT.getId()%>" name="<%=YMisureValFarfNewSampleDirT.getName()%>"><% 
  YMisureValFarfNewSampleDirT.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfNewSampleDimT =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfNew", "SampleDimT", null); 
  YMisureValFarfNewSampleDimT.setParent(YMisureValFarfNewForm); 
%>
<select id="<%=YMisureValFarfNewSampleDimT.getId()%>" name="<%=YMisureValFarfNewSampleDimT.getName()%>"><% 
  YMisureValFarfNewSampleDimT.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "TrattamentoT", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="TrattamentoT"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "TempTt", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="TempTt"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfNewTrattamentoT =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfNew", "TrattamentoT", null); 
  YMisureValFarfNewTrattamentoT.setParent(YMisureValFarfNewForm); 
%>
<select id="<%=YMisureValFarfNewTrattamentoT.getId()%>" name="<%=YMisureValFarfNewTrattamentoT.getName()%>"><% 
  YMisureValFarfNewTrattamentoT.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewTempTt =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "TempTt"); 
  YMisureValFarfNewTempTt.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewTempTt.getClassType()%>" id="<%=YMisureValFarfNewTempTt.getId()%>" maxlength="<%=YMisureValFarfNewTempTt.getMaxLength()%>" name="<%=YMisureValFarfNewTempTt.getName()%>" size="<%=YMisureValFarfNewTempTt.getSize()%>"><% 
  YMisureValFarfNewTempTt.write(out); 
%>

                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "SampleDirR", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="SampleDirR"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "SampleDimR", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="SampleDimR"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfNewSampleDirR =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfNew", "SampleDirR", null); 
  YMisureValFarfNewSampleDirR.setParent(YMisureValFarfNewForm); 
%>
<select id="<%=YMisureValFarfNewSampleDirR.getId()%>" name="<%=YMisureValFarfNewSampleDirR.getName()%>"><% 
  YMisureValFarfNewSampleDirR.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfNewSampleDimR =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfNew", "SampleDimR", null); 
  YMisureValFarfNewSampleDimR.setParent(YMisureValFarfNewForm); 
%>
<select id="<%=YMisureValFarfNewSampleDimR.getId()%>" name="<%=YMisureValFarfNewSampleDimR.getName()%>"><% 
  YMisureValFarfNewSampleDimR.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "TempRe", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="TempRe"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "Test1", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Test1"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "Test2", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Test2"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "Test3", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Test3"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewTempRe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "TempRe"); 
  YMisureValFarfNewTempRe.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewTempRe.getClassType()%>" id="<%=YMisureValFarfNewTempRe.getId()%>" maxlength="<%=YMisureValFarfNewTempRe.getMaxLength()%>" name="<%=YMisureValFarfNewTempRe.getName()%>" size="<%=YMisureValFarfNewTempRe.getSize()%>"><% 
  YMisureValFarfNewTempRe.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewTest1 =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "Test1"); 
  YMisureValFarfNewTest1.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewTest1.getClassType()%>" id="<%=YMisureValFarfNewTest1.getId()%>" maxlength="<%=YMisureValFarfNewTest1.getMaxLength()%>" name="<%=YMisureValFarfNewTest1.getName()%>" size="<%=YMisureValFarfNewTest1.getSize()%>"><% 
  YMisureValFarfNewTest1.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewTest2 =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "Test2"); 
  YMisureValFarfNewTest2.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewTest2.getClassType()%>" id="<%=YMisureValFarfNewTest2.getId()%>" maxlength="<%=YMisureValFarfNewTest2.getMaxLength()%>" name="<%=YMisureValFarfNewTest2.getName()%>" size="<%=YMisureValFarfNewTest2.getSize()%>"><% 
  YMisureValFarfNewTest2.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewTest3 =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "Test3"); 
  YMisureValFarfNewTest3.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewTest3.getClassType()%>" id="<%=YMisureValFarfNewTest3.getId()%>" maxlength="<%=YMisureValFarfNewTest3.getMaxLength()%>" name="<%=YMisureValFarfNewTest3.getName()%>" size="<%=YMisureValFarfNewTest3.getSize()%>"><% 
  YMisureValFarfNewTest3.write(out); 
%>

                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "Nace", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Nace"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "DurezzaHb", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="DurezzaHb"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfNewNace =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfNew", "Nace", null); 
  YMisureValFarfNewNace.setParent(YMisureValFarfNewForm); 
%>
<select id="<%=YMisureValFarfNewNace.getId()%>" name="<%=YMisureValFarfNewNace.getName()%>"><% 
  YMisureValFarfNewNace.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewDurezzaHb =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "DurezzaHb"); 
  YMisureValFarfNewDurezzaHb.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewDurezzaHb.getClassType()%>" id="<%=YMisureValFarfNewDurezzaHb.getId()%>" maxlength="<%=YMisureValFarfNewDurezzaHb.getMaxLength()%>" name="<%=YMisureValFarfNewDurezzaHb.getName()%>" size="<%=YMisureValFarfNewDurezzaHb.getSize()%>"><% 
  YMisureValFarfNewDurezzaHb.write(out); 
%>

                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "Mill", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Mill"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "Additional", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Additional"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "Testreport", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="Testreport"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfNew", "LottoProd", null); 
   label.setParent(YMisureValFarfNewForm); 
%><label class="<%=label.getClassType()%>" for="LottoProd"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewMill =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "Mill"); 
  YMisureValFarfNewMill.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewMill.getClassType()%>" id="<%=YMisureValFarfNewMill.getId()%>" maxlength="<%=YMisureValFarfNewMill.getMaxLength()%>" name="<%=YMisureValFarfNewMill.getName()%>" size="<%=YMisureValFarfNewMill.getSize()%>"><% 
  YMisureValFarfNewMill.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewAdditional =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "Additional"); 
  YMisureValFarfNewAdditional.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewAdditional.getClassType()%>" id="<%=YMisureValFarfNewAdditional.getId()%>" maxlength="<%=YMisureValFarfNewAdditional.getMaxLength()%>" name="<%=YMisureValFarfNewAdditional.getName()%>" size="<%=YMisureValFarfNewAdditional.getSize()%>"><% 
  YMisureValFarfNewAdditional.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewTestreport =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "Testreport"); 
  YMisureValFarfNewTestreport.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewTestreport.getClassType()%>" id="<%=YMisureValFarfNewTestreport.getId()%>" maxlength="<%=YMisureValFarfNewTestreport.getMaxLength()%>" name="<%=YMisureValFarfNewTestreport.getName()%>" size="<%=YMisureValFarfNewTestreport.getSize()%>"><% 
  YMisureValFarfNewTestreport.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfNewLottoProd =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfNew", "LottoProd"); 
  YMisureValFarfNewLottoProd.setParent(YMisureValFarfNewForm); 
%>
<input class="<%=YMisureValFarfNewLottoProd.getClassType()%>" id="<%=YMisureValFarfNewLottoProd.getId()%>" maxlength="<%=YMisureValFarfNewLottoProd.getMaxLength()%>" name="<%=YMisureValFarfNewLottoProd.getName()%>" size="<%=YMisureValFarfNewLottoProd.getSize()%>"><% 
  YMisureValFarfNewLottoProd.write(out); 
%>

                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                    </td>
                  </tr>
                </table>
              <% mytabbed.endTab(); %> 
</div>
              <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab2")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab2"); %>
                <table style="width: 100%;">
                  <tr>
                    <td valign="top">
                      <!--<span class="editgrid" id="YMisureValvFarfComp">--><% 
  WebEditGrid YMisureValFarfNewYMisureValvFarfComp =  
     new com.thera.thermfw.web.WebEditGrid("YMisureValFarfNew", "YMisureValvFarfComp", 8, new String[]{"IdTpComp", "IdLotto", "IdArticolo", "IdFornitore", "IdAzienda","IdMatricola", "RNormativa", "PercC", "PercMn", "PercSi", "PercP", "PercS", "PercNi", "PercCr", "PercMo", "PercTi", "PercAl", "PercV", "PercCu", "PercN", "PercNb", "PercPb", "PercFe", "PercCe", "PercRe", "PercMg", "PercSn", "PercB", "PercZn", "RotturaMpa", "SnervMpa", "PercAllung", "PercContraz", "SampleDirT", "SampleDimR", "TrattamentoT", "TempTt", "SampleDirR", "SampleDimT", "TempRe", "Test1", "Test2", "Test3", "Nace", "DurezzaHb", "Mill", "Additional", "Testreport", "LottoProd", "ColataComp", "RelTipoComponente.Descrizione", "RelNormativa.Descrizione"}, 3, null, null,false,"com.thera.thermfw.web.servlet.GridActionAdapterForIndependentRow"); 
 YMisureValFarfNewYMisureValvFarfComp.setParent(YMisureValFarfNewForm); 
 YMisureValFarfNewYMisureValvFarfComp.setNoControlRowKeys(false); 
 YMisureValFarfNewYMisureValvFarfComp.addHideAsDefault("RelTipoComponente.Descrizione"); 
 YMisureValFarfNewYMisureValvFarfComp.addHideAsDefault("RelNormativa.Descrizione"); 
 YMisureValFarfNewYMisureValvFarfComp.write(out); 
%>
<!--</span>-->
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
  errorList.setParent(YMisureValFarfNewForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YMisureValFarfNewForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YMisureValFarfNewForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YMisureValFarfNewBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YMisureValFarfNewForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YMisureValFarfNewBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YMisureValFarfNewBODC.getErrorList().getErrors()); 
           if(YMisureValFarfNewBODC.getConflict() != null) 
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
     if(YMisureValFarfNewBODC != null && !YMisureValFarfNewBODC.close(false)) 
        errors.addAll(0, YMisureValFarfNewBODC.getErrorList().getErrors()); 
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
     String errorPage = YMisureValFarfNewForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YMisureValFarfNewBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YMisureValFarfNewForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
