<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/4.7.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<html>
<!-- WIZGEN Therm 2.0.0 as Form riga indipendente - multiBrowserGen = true -->
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
  BODataCollector YMisureValFarfCompBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebFormForIndipendentRowForm YMisureValFarfCompForm =  
     new com.thera.thermfw.web.WebFormForIndipendentRowForm(request, response, "YMisureValFarfCompForm", "YMisureValFarfComp", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, false, null); 
  YMisureValFarfCompForm.setServletEnvironment(se); 
  YMisureValFarfCompForm.setJSTypeList(jsList); 
  YMisureValFarfCompForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YMisureValFarfCompForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YMisureValFarfCompForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YMisureValFarfCompForm.getMode(); 
  String key = YMisureValFarfCompForm.getKey(); 
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
        YMisureValFarfCompForm.outTraceInfo(getClass().getName()); 
        String collectorName = YMisureValFarfCompForm.findBODataCollectorName(); 
	     YMisureValFarfCompBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YMisureValFarfCompBODC instanceof WebDataCollector) 
            ((WebDataCollector)YMisureValFarfCompBODC).setServletEnvironment(se); 
        YMisureValFarfCompBODC.initialize("YMisureValFarfComp", true, 0); 
        YMisureValFarfCompForm.setBODataCollector(YMisureValFarfCompBODC); 
        int rcBODC = YMisureValFarfCompForm.initSecurityServices(); 
        mode = YMisureValFarfCompForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YMisureValFarfCompForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YMisureValFarfCompBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YMisureValFarfCompForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YMisureValFarfCompForm); 
   request.setAttribute("menuBar", menuBar); 
%> 
<jsp:include page="/com/thera/thermfw/common/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="menuBar"/> 
</jsp:include> 
<% 
  menuBar.write(out); 
  menuBar.writeChildren(out); 
%> 
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YMisureValFarfCompForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/com/thera/thermfw/common/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YMisureValFarfCompForm.getBodyOnBeforeUnload()%>" onload="<%=YMisureValFarfCompForm.getBodyOnLoad()%>" onunload="<%=YMisureValFarfCompForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YMisureValFarfCompForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YMisureValFarfCompForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YMisureValFarfCompBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YMisureValFarfCompForm.getServlet()%>" method="post" name="YMisureValFarfCompForm" style="height:100%"><%
  YMisureValFarfCompForm.writeFormStartElements(out); 
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
  WebTextInput YMisureValFarfCompIdFornitore =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "IdFornitore"); 
  YMisureValFarfCompIdFornitore.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompIdFornitore.getClassType()%>" id="<%=YMisureValFarfCompIdFornitore.getId()%>" maxlength="<%=YMisureValFarfCompIdFornitore.getMaxLength()%>" name="<%=YMisureValFarfCompIdFornitore.getName()%>" size="<%=YMisureValFarfCompIdFornitore.getSize()%>" type="hidden"><% 
  YMisureValFarfCompIdFornitore.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput YMisureValFarfCompIdArticolo =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "IdArticolo"); 
  YMisureValFarfCompIdArticolo.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompIdArticolo.getClassType()%>" id="<%=YMisureValFarfCompIdArticolo.getId()%>" maxlength="<%=YMisureValFarfCompIdArticolo.getMaxLength()%>" name="<%=YMisureValFarfCompIdArticolo.getName()%>" size="<%=YMisureValFarfCompIdArticolo.getSize()%>" type="hidden"><% 
  YMisureValFarfCompIdArticolo.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput YMisureValFarfCompIdLotto =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "IdLotto"); 
  YMisureValFarfCompIdLotto.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompIdLotto.getClassType()%>" id="<%=YMisureValFarfCompIdLotto.getId()%>" maxlength="<%=YMisureValFarfCompIdLotto.getMaxLength()%>" name="<%=YMisureValFarfCompIdLotto.getName()%>" size="<%=YMisureValFarfCompIdLotto.getSize()%>" type="hidden"><% 
  YMisureValFarfCompIdLotto.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput YMisureValFarfCompIdMatricola =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "IdMatricola"); 
  YMisureValFarfCompIdMatricola.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompIdMatricola.getClassType()%>" id="<%=YMisureValFarfCompIdMatricola.getId()%>" maxlength="<%=YMisureValFarfCompIdMatricola.getMaxLength()%>" name="<%=YMisureValFarfCompIdMatricola.getName()%>" size="<%=YMisureValFarfCompIdMatricola.getSize()%>" type="hidden"><% 
  YMisureValFarfCompIdMatricola.write(out); 
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
  mytabbed.setParent(YMisureValFarfCompForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.base.resources.YMisureValFarfComp", "tab1", "YMisureValFarfComp", null, null, null, null); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "IdTpComp", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="RelTipoComponente"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfCompRelTipoComponente =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfComp", "RelTipoComponente", false, false, true, 1, null, null); 
  YMisureValFarfCompRelTipoComponente.setParent(YMisureValFarfCompForm); 
  YMisureValFarfCompRelTipoComponente.write(out); 
%>
<!--<span class="multisearchform" id="RelTipoComponente"></span>-->
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "ColataComp", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="ColataComp"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompColataComp =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "ColataComp"); 
  YMisureValFarfCompColataComp.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompColataComp.getClassType()%>" id="<%=YMisureValFarfCompColataComp.getId()%>" maxlength="<%=YMisureValFarfCompColataComp.getMaxLength()%>" name="<%=YMisureValFarfCompColataComp.getName()%>" size="<%=YMisureValFarfCompColataComp.getSize()%>"><% 
  YMisureValFarfCompColataComp.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "RNormativa", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="RelNormativa"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YMisureValFarfCompRelNormativa =  
     new com.thera.thermfw.web.WebMultiSearchForm("YMisureValFarfComp", "RelNormativa", false, false, true, 1, null, null); 
  YMisureValFarfCompRelNormativa.setParent(YMisureValFarfCompForm); 
  YMisureValFarfCompRelNormativa.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercC", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercC"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercMn", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercMn"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercSi", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercSi"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercP", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercP"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercS", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercS"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercNi", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercNi"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercCr", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercCr"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercMo", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercMo"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercTi", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercTi"><%label.write(out);%></label><%}%>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercC =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercC"); 
  YMisureValFarfCompPercC.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercC.getClassType()%>" id="<%=YMisureValFarfCompPercC.getId()%>" maxlength="<%=YMisureValFarfCompPercC.getMaxLength()%>" name="<%=YMisureValFarfCompPercC.getName()%>" size="<%=YMisureValFarfCompPercC.getSize()%>"><% 
  YMisureValFarfCompPercC.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercMn =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercMn"); 
  YMisureValFarfCompPercMn.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercMn.getClassType()%>" id="<%=YMisureValFarfCompPercMn.getId()%>" maxlength="<%=YMisureValFarfCompPercMn.getMaxLength()%>" name="<%=YMisureValFarfCompPercMn.getName()%>" size="<%=YMisureValFarfCompPercMn.getSize()%>"><% 
  YMisureValFarfCompPercMn.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercSi =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercSi"); 
  YMisureValFarfCompPercSi.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercSi.getClassType()%>" id="<%=YMisureValFarfCompPercSi.getId()%>" maxlength="<%=YMisureValFarfCompPercSi.getMaxLength()%>" name="<%=YMisureValFarfCompPercSi.getName()%>" size="<%=YMisureValFarfCompPercSi.getSize()%>"><% 
  YMisureValFarfCompPercSi.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercP =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercP"); 
  YMisureValFarfCompPercP.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercP.getClassType()%>" id="<%=YMisureValFarfCompPercP.getId()%>" maxlength="<%=YMisureValFarfCompPercP.getMaxLength()%>" name="<%=YMisureValFarfCompPercP.getName()%>" size="<%=YMisureValFarfCompPercP.getSize()%>"><% 
  YMisureValFarfCompPercP.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercS =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercS"); 
  YMisureValFarfCompPercS.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercS.getClassType()%>" id="<%=YMisureValFarfCompPercS.getId()%>" maxlength="<%=YMisureValFarfCompPercS.getMaxLength()%>" name="<%=YMisureValFarfCompPercS.getName()%>" size="<%=YMisureValFarfCompPercS.getSize()%>"><% 
  YMisureValFarfCompPercS.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercNi =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercNi"); 
  YMisureValFarfCompPercNi.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercNi.getClassType()%>" id="<%=YMisureValFarfCompPercNi.getId()%>" maxlength="<%=YMisureValFarfCompPercNi.getMaxLength()%>" name="<%=YMisureValFarfCompPercNi.getName()%>" size="<%=YMisureValFarfCompPercNi.getSize()%>"><% 
  YMisureValFarfCompPercNi.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercCr =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercCr"); 
  YMisureValFarfCompPercCr.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercCr.getClassType()%>" id="<%=YMisureValFarfCompPercCr.getId()%>" maxlength="<%=YMisureValFarfCompPercCr.getMaxLength()%>" name="<%=YMisureValFarfCompPercCr.getName()%>" size="<%=YMisureValFarfCompPercCr.getSize()%>"><% 
  YMisureValFarfCompPercCr.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercMo =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercMo"); 
  YMisureValFarfCompPercMo.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercMo.getClassType()%>" id="<%=YMisureValFarfCompPercMo.getId()%>" maxlength="<%=YMisureValFarfCompPercMo.getMaxLength()%>" name="<%=YMisureValFarfCompPercMo.getName()%>" size="<%=YMisureValFarfCompPercMo.getSize()%>"><% 
  YMisureValFarfCompPercMo.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercTi =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercTi"); 
  YMisureValFarfCompPercTi.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercTi.getClassType()%>" id="<%=YMisureValFarfCompPercTi.getId()%>" maxlength="<%=YMisureValFarfCompPercTi.getMaxLength()%>" name="<%=YMisureValFarfCompPercTi.getName()%>" size="<%=YMisureValFarfCompPercTi.getSize()%>"><% 
  YMisureValFarfCompPercTi.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercAl", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercAl"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercV", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercV"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercCu", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercCu"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercN", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercN"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercNb", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercNb"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercPb", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercPb"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercFe", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercFe"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercCe", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercCe"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercRe", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercRe"><%label.write(out);%></label><%}%>
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercAl =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercAl"); 
  YMisureValFarfCompPercAl.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercAl.getClassType()%>" id="<%=YMisureValFarfCompPercAl.getId()%>" maxlength="<%=YMisureValFarfCompPercAl.getMaxLength()%>" name="<%=YMisureValFarfCompPercAl.getName()%>" size="<%=YMisureValFarfCompPercAl.getSize()%>"><% 
  YMisureValFarfCompPercAl.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercV =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercV"); 
  YMisureValFarfCompPercV.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercV.getClassType()%>" id="<%=YMisureValFarfCompPercV.getId()%>" maxlength="<%=YMisureValFarfCompPercV.getMaxLength()%>" name="<%=YMisureValFarfCompPercV.getName()%>" size="<%=YMisureValFarfCompPercV.getSize()%>"><% 
  YMisureValFarfCompPercV.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercCu =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercCu"); 
  YMisureValFarfCompPercCu.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercCu.getClassType()%>" id="<%=YMisureValFarfCompPercCu.getId()%>" maxlength="<%=YMisureValFarfCompPercCu.getMaxLength()%>" name="<%=YMisureValFarfCompPercCu.getName()%>" size="<%=YMisureValFarfCompPercCu.getSize()%>"><% 
  YMisureValFarfCompPercCu.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercN =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercN"); 
  YMisureValFarfCompPercN.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercN.getClassType()%>" id="<%=YMisureValFarfCompPercN.getId()%>" maxlength="<%=YMisureValFarfCompPercN.getMaxLength()%>" name="<%=YMisureValFarfCompPercN.getName()%>" size="<%=YMisureValFarfCompPercN.getSize()%>"><% 
  YMisureValFarfCompPercN.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercNb =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercNb"); 
  YMisureValFarfCompPercNb.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercNb.getClassType()%>" id="<%=YMisureValFarfCompPercNb.getId()%>" maxlength="<%=YMisureValFarfCompPercNb.getMaxLength()%>" name="<%=YMisureValFarfCompPercNb.getName()%>" size="<%=YMisureValFarfCompPercNb.getSize()%>"><% 
  YMisureValFarfCompPercNb.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercPb =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercPb"); 
  YMisureValFarfCompPercPb.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercPb.getClassType()%>" id="<%=YMisureValFarfCompPercPb.getId()%>" maxlength="<%=YMisureValFarfCompPercPb.getMaxLength()%>" name="<%=YMisureValFarfCompPercPb.getName()%>" size="<%=YMisureValFarfCompPercPb.getSize()%>"><% 
  YMisureValFarfCompPercPb.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercFe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercFe"); 
  YMisureValFarfCompPercFe.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercFe.getClassType()%>" id="<%=YMisureValFarfCompPercFe.getId()%>" maxlength="<%=YMisureValFarfCompPercFe.getMaxLength()%>" name="<%=YMisureValFarfCompPercFe.getName()%>" size="<%=YMisureValFarfCompPercFe.getSize()%>"><% 
  YMisureValFarfCompPercFe.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercCe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercCe"); 
  YMisureValFarfCompPercCe.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercCe.getClassType()%>" id="<%=YMisureValFarfCompPercCe.getId()%>" maxlength="<%=YMisureValFarfCompPercCe.getMaxLength()%>" name="<%=YMisureValFarfCompPercCe.getName()%>" size="<%=YMisureValFarfCompPercCe.getSize()%>"><% 
  YMisureValFarfCompPercCe.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercRe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercRe"); 
  YMisureValFarfCompPercRe.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercRe.getClassType()%>" id="<%=YMisureValFarfCompPercRe.getId()%>" maxlength="<%=YMisureValFarfCompPercRe.getMaxLength()%>" name="<%=YMisureValFarfCompPercRe.getName()%>" size="<%=YMisureValFarfCompPercRe.getSize()%>"><% 
  YMisureValFarfCompPercRe.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercMg", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercMg"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercSn", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercSn"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercB", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercB"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercZn", null); 
   label.setParent(YMisureValFarfCompForm); 
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
  WebTextInput YMisureValFarfCompPercMg =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercMg"); 
  YMisureValFarfCompPercMg.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercMg.getClassType()%>" id="<%=YMisureValFarfCompPercMg.getId()%>" maxlength="<%=YMisureValFarfCompPercMg.getMaxLength()%>" name="<%=YMisureValFarfCompPercMg.getName()%>" size="<%=YMisureValFarfCompPercMg.getSize()%>"><% 
  YMisureValFarfCompPercMg.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercSn =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercSn"); 
  YMisureValFarfCompPercSn.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercSn.getClassType()%>" id="<%=YMisureValFarfCompPercSn.getId()%>" maxlength="<%=YMisureValFarfCompPercSn.getMaxLength()%>" name="<%=YMisureValFarfCompPercSn.getName()%>" size="<%=YMisureValFarfCompPercSn.getSize()%>"><% 
  YMisureValFarfCompPercSn.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercB =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercB"); 
  YMisureValFarfCompPercB.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercB.getClassType()%>" id="<%=YMisureValFarfCompPercB.getId()%>" maxlength="<%=YMisureValFarfCompPercB.getMaxLength()%>" name="<%=YMisureValFarfCompPercB.getName()%>" size="<%=YMisureValFarfCompPercB.getSize()%>"><% 
  YMisureValFarfCompPercB.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercZn =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercZn"); 
  YMisureValFarfCompPercZn.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercZn.getClassType()%>" id="<%=YMisureValFarfCompPercZn.getId()%>" maxlength="<%=YMisureValFarfCompPercZn.getMaxLength()%>" name="<%=YMisureValFarfCompPercZn.getName()%>" size="<%=YMisureValFarfCompPercZn.getSize()%>"><% 
  YMisureValFarfCompPercZn.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "RotturaMpa", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="RotturaMpa"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "SnervMpa", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="SnervMpa"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercAllung", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercAllung"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "PercContraz", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="PercContraz"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "SampleDirT", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="SampleDirT"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "SampleDimR", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="SampleDimR"><%label.write(out);%></label><%}%>
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
  WebTextInput YMisureValFarfCompRotturaMpa =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "RotturaMpa"); 
  YMisureValFarfCompRotturaMpa.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompRotturaMpa.getClassType()%>" id="<%=YMisureValFarfCompRotturaMpa.getId()%>" maxlength="<%=YMisureValFarfCompRotturaMpa.getMaxLength()%>" name="<%=YMisureValFarfCompRotturaMpa.getName()%>" size="<%=YMisureValFarfCompRotturaMpa.getSize()%>"><% 
  YMisureValFarfCompRotturaMpa.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompSnervMpa =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "SnervMpa"); 
  YMisureValFarfCompSnervMpa.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompSnervMpa.getClassType()%>" id="<%=YMisureValFarfCompSnervMpa.getId()%>" maxlength="<%=YMisureValFarfCompSnervMpa.getMaxLength()%>" name="<%=YMisureValFarfCompSnervMpa.getName()%>" size="<%=YMisureValFarfCompSnervMpa.getSize()%>"><% 
  YMisureValFarfCompSnervMpa.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercAllung =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercAllung"); 
  YMisureValFarfCompPercAllung.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercAllung.getClassType()%>" id="<%=YMisureValFarfCompPercAllung.getId()%>" maxlength="<%=YMisureValFarfCompPercAllung.getMaxLength()%>" name="<%=YMisureValFarfCompPercAllung.getName()%>" size="<%=YMisureValFarfCompPercAllung.getSize()%>"><% 
  YMisureValFarfCompPercAllung.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompPercContraz =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "PercContraz"); 
  YMisureValFarfCompPercContraz.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompPercContraz.getClassType()%>" id="<%=YMisureValFarfCompPercContraz.getId()%>" maxlength="<%=YMisureValFarfCompPercContraz.getMaxLength()%>" name="<%=YMisureValFarfCompPercContraz.getName()%>" size="<%=YMisureValFarfCompPercContraz.getSize()%>"><% 
  YMisureValFarfCompPercContraz.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfCompSampleDirT =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfComp", "SampleDirT", null); 
  YMisureValFarfCompSampleDirT.setParent(YMisureValFarfCompForm); 
%>
<select id="<%=YMisureValFarfCompSampleDirT.getId()%>" name="<%=YMisureValFarfCompSampleDirT.getName()%>"><% 
  YMisureValFarfCompSampleDirT.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfCompSampleDimR =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfComp", "SampleDimR", null); 
  YMisureValFarfCompSampleDimR.setParent(YMisureValFarfCompForm); 
%>
<select id="<%=YMisureValFarfCompSampleDimR.getId()%>" name="<%=YMisureValFarfCompSampleDimR.getName()%>"><% 
  YMisureValFarfCompSampleDimR.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "TrattamentoT", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="TrattamentoT"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "TempTt", null); 
   label.setParent(YMisureValFarfCompForm); 
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
  WebComboBox YMisureValFarfCompTrattamentoT =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfComp", "TrattamentoT", null); 
  YMisureValFarfCompTrattamentoT.setParent(YMisureValFarfCompForm); 
%>
<select id="<%=YMisureValFarfCompTrattamentoT.getId()%>" name="<%=YMisureValFarfCompTrattamentoT.getName()%>"><% 
  YMisureValFarfCompTrattamentoT.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompTempTt =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "TempTt"); 
  YMisureValFarfCompTempTt.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompTempTt.getClassType()%>" id="<%=YMisureValFarfCompTempTt.getId()%>" maxlength="<%=YMisureValFarfCompTempTt.getMaxLength()%>" name="<%=YMisureValFarfCompTempTt.getName()%>" size="<%=YMisureValFarfCompTempTt.getSize()%>"><% 
  YMisureValFarfCompTempTt.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "SampleDirR", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="SampleDirR"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "SampleDimT", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="SampleDimT"><%label.write(out);%></label><%}%>
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
  WebComboBox YMisureValFarfCompSampleDirR =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfComp", "SampleDirR", null); 
  YMisureValFarfCompSampleDirR.setParent(YMisureValFarfCompForm); 
%>
<select id="<%=YMisureValFarfCompSampleDirR.getId()%>" name="<%=YMisureValFarfCompSampleDirR.getName()%>"><% 
  YMisureValFarfCompSampleDirR.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                      <% 
  WebComboBox YMisureValFarfCompSampleDimT =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfComp", "SampleDimT", null); 
  YMisureValFarfCompSampleDimT.setParent(YMisureValFarfCompForm); 
%>
<select id="<%=YMisureValFarfCompSampleDimT.getId()%>" name="<%=YMisureValFarfCompSampleDimT.getName()%>"><% 
  YMisureValFarfCompSampleDimT.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "TempRe", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="TempRe"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "Test1", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="Test1"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "Test2", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="Test2"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "Test3", null); 
   label.setParent(YMisureValFarfCompForm); 
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
  WebTextInput YMisureValFarfCompTempRe =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "TempRe"); 
  YMisureValFarfCompTempRe.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompTempRe.getClassType()%>" id="<%=YMisureValFarfCompTempRe.getId()%>" maxlength="<%=YMisureValFarfCompTempRe.getMaxLength()%>" name="<%=YMisureValFarfCompTempRe.getName()%>" size="<%=YMisureValFarfCompTempRe.getSize()%>"><% 
  YMisureValFarfCompTempRe.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompTest1 =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "Test1"); 
  YMisureValFarfCompTest1.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompTest1.getClassType()%>" id="<%=YMisureValFarfCompTest1.getId()%>" maxlength="<%=YMisureValFarfCompTest1.getMaxLength()%>" name="<%=YMisureValFarfCompTest1.getName()%>" size="<%=YMisureValFarfCompTest1.getSize()%>"><% 
  YMisureValFarfCompTest1.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompTest2 =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "Test2"); 
  YMisureValFarfCompTest2.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompTest2.getClassType()%>" id="<%=YMisureValFarfCompTest2.getId()%>" maxlength="<%=YMisureValFarfCompTest2.getMaxLength()%>" name="<%=YMisureValFarfCompTest2.getName()%>" size="<%=YMisureValFarfCompTest2.getSize()%>"><% 
  YMisureValFarfCompTest2.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompTest3 =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "Test3"); 
  YMisureValFarfCompTest3.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompTest3.getClassType()%>" id="<%=YMisureValFarfCompTest3.getId()%>" maxlength="<%=YMisureValFarfCompTest3.getMaxLength()%>" name="<%=YMisureValFarfCompTest3.getName()%>" size="<%=YMisureValFarfCompTest3.getSize()%>"><% 
  YMisureValFarfCompTest3.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "Nace", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="Nace"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "DurezzaHb", null); 
   label.setParent(YMisureValFarfCompForm); 
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
  WebComboBox YMisureValFarfCompNace =  
     new com.thera.thermfw.web.WebComboBox("YMisureValFarfComp", "Nace", null); 
  YMisureValFarfCompNace.setParent(YMisureValFarfCompForm); 
%>
<select id="<%=YMisureValFarfCompNace.getId()%>" name="<%=YMisureValFarfCompNace.getName()%>"><% 
  YMisureValFarfCompNace.write(out); 
%> 
</select>
                    </td>
                    <td valign="top">
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompDurezzaHb =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "DurezzaHb"); 
  YMisureValFarfCompDurezzaHb.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompDurezzaHb.getClassType()%>" id="<%=YMisureValFarfCompDurezzaHb.getId()%>" maxlength="<%=YMisureValFarfCompDurezzaHb.getMaxLength()%>" name="<%=YMisureValFarfCompDurezzaHb.getName()%>" size="<%=YMisureValFarfCompDurezzaHb.getSize()%>"><% 
  YMisureValFarfCompDurezzaHb.write(out); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "Mill", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="Mill"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "Additional", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="Additional"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "Testreport", null); 
   label.setParent(YMisureValFarfCompForm); 
%><label class="<%=label.getClassType()%>" for="Testreport"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMisureValFarfComp", "LottoProd", null); 
   label.setParent(YMisureValFarfCompForm); 
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
  WebTextInput YMisureValFarfCompMill =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "Mill"); 
  YMisureValFarfCompMill.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompMill.getClassType()%>" id="<%=YMisureValFarfCompMill.getId()%>" maxlength="<%=YMisureValFarfCompMill.getMaxLength()%>" name="<%=YMisureValFarfCompMill.getName()%>" size="<%=YMisureValFarfCompMill.getSize()%>"><% 
  YMisureValFarfCompMill.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompAdditional =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "Additional"); 
  YMisureValFarfCompAdditional.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompAdditional.getClassType()%>" id="<%=YMisureValFarfCompAdditional.getId()%>" maxlength="<%=YMisureValFarfCompAdditional.getMaxLength()%>" name="<%=YMisureValFarfCompAdditional.getName()%>" size="<%=YMisureValFarfCompAdditional.getSize()%>"><% 
  YMisureValFarfCompAdditional.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompTestreport =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "Testreport"); 
  YMisureValFarfCompTestreport.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompTestreport.getClassType()%>" id="<%=YMisureValFarfCompTestreport.getId()%>" maxlength="<%=YMisureValFarfCompTestreport.getMaxLength()%>" name="<%=YMisureValFarfCompTestreport.getName()%>" size="<%=YMisureValFarfCompTestreport.getSize()%>"><% 
  YMisureValFarfCompTestreport.write(out); 
%>

                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMisureValFarfCompLottoProd =  
     new com.thera.thermfw.web.WebTextInput("YMisureValFarfComp", "LottoProd"); 
  YMisureValFarfCompLottoProd.setParent(YMisureValFarfCompForm); 
%>
<input class="<%=YMisureValFarfCompLottoProd.getClassType()%>" id="<%=YMisureValFarfCompLottoProd.getId()%>" maxlength="<%=YMisureValFarfCompLottoProd.getMaxLength()%>" name="<%=YMisureValFarfCompLottoProd.getName()%>" size="<%=YMisureValFarfCompLottoProd.getSize()%>"><% 
  YMisureValFarfCompLottoProd.write(out); 
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
  errorList.setParent(YMisureValFarfCompForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YMisureValFarfCompForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YMisureValFarfCompForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YMisureValFarfCompBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YMisureValFarfCompForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YMisureValFarfCompBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YMisureValFarfCompBODC.getErrorList().getErrors()); 
           if(YMisureValFarfCompBODC.getConflict() != null) 
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
     if(YMisureValFarfCompBODC != null && !YMisureValFarfCompBODC.close(false)) 
        errors.addAll(0, YMisureValFarfCompBODC.getErrorList().getErrors()); 
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
     String errorPage = YMisureValFarfCompForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YMisureValFarfCompBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YMisureValFarfCompForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
