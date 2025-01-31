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
  BODataCollector YAziendeUserPortalBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebFormForIndipendentRowForm YAziendeUserPortalForm =  
     new com.thera.thermfw.web.WebFormForIndipendentRowForm(request, response, "YAziendeUserPortalForm", "YAziendeUserPortal", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/portal/YAziendeUserPortal.js"); 
  YAziendeUserPortalForm.setServletEnvironment(se); 
  YAziendeUserPortalForm.setJSTypeList(jsList); 
  YAziendeUserPortalForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YAziendeUserPortalForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YAziendeUserPortalForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YAziendeUserPortalForm.getMode(); 
  String key = YAziendeUserPortalForm.getKey(); 
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
        YAziendeUserPortalForm.outTraceInfo(getClass().getName()); 
        String collectorName = YAziendeUserPortalForm.findBODataCollectorName(); 
	     YAziendeUserPortalBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YAziendeUserPortalBODC instanceof WebDataCollector) 
            ((WebDataCollector)YAziendeUserPortalBODC).setServletEnvironment(se); 
        YAziendeUserPortalBODC.initialize("YAziendeUserPortal", true, 0); 
        YAziendeUserPortalForm.setBODataCollector(YAziendeUserPortalBODC); 
        int rcBODC = YAziendeUserPortalForm.initSecurityServices(); 
        mode = YAziendeUserPortalForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YAziendeUserPortalForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YAziendeUserPortalBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YAziendeUserPortalForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YAziendeUserPortalForm); 
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
  myToolBarTB.setParent(YAziendeUserPortalForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/com/thera/thermfw/common/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YAziendeUserPortalForm.getBodyOnBeforeUnload()%>" onload="<%=YAziendeUserPortalForm.getBodyOnLoad()%>" onunload="<%=YAziendeUserPortalForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YAziendeUserPortalForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YAziendeUserPortalForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YAziendeUserPortalBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YAziendeUserPortalForm.getServlet()%>" method="post" name="YAziendeUserPortalForm" style="height:100%"><%
  YAziendeUserPortalForm.writeFormStartElements(out); 
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
  WebTextInput YAziendeUserPortalIdUtente =  
     new com.thera.thermfw.web.WebTextInput("YAziendeUserPortal", "IdUtente"); 
  YAziendeUserPortalIdUtente.setParent(YAziendeUserPortalForm); 
%>
<input class="<%=YAziendeUserPortalIdUtente.getClassType()%>" id="<%=YAziendeUserPortalIdUtente.getId()%>" maxlength="<%=YAziendeUserPortalIdUtente.getMaxLength()%>" name="<%=YAziendeUserPortalIdUtente.getName()%>" size="<%=YAziendeUserPortalIdUtente.getSize()%>" type="hidden"><% 
  YAziendeUserPortalIdUtente.write(out); 
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
  mytabbed.setParent(YAziendeUserPortalForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.base.portal.resources.YAziendeUserPortal", "tab1", "YAziendeUserPortal", null, null, null, null); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;">
              <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab1")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab1"); %>
                <table style="width: 100%;">
<!--                   <tr> -->
<!--                     <td valign="top"> -->
<!--                       <label for="IdAzienda"></label> -->
<!--                     </td> -->
<!--                     <td valign="top"> -->
<!--                       <input id="IdAzienda" name="IdAzienda"></input> -->
<!--                     </td> -->
<!--                   </tr> -->
					<tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAziendeUserPortal", "IdAzienda", null); 
   label.setParent(YAziendeUserPortalForm); 
%><label class="<%=label.getClassType()%>" for="RelAzienda"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebSearchComboBox YAziendeUserPortalRelAzienda =  
     new com.thera.thermfw.web.WebSearchComboBox("YAziendeUserPortal", "RelAzienda", null, 2, null, false, null); 
  YAziendeUserPortalRelAzienda.setParent(YAziendeUserPortalForm); 
  YAziendeUserPortalRelAzienda.setOnChange("parametrizzaUserPortal()"); 
  YAziendeUserPortalRelAzienda.write(out); 
%>
<!--<span class="searchcombobox" id="RelAzienda"></span>-->
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAziendeUserPortal", "RUtentePth", null); 
   label.setParent(YAziendeUserPortalForm); 
%><label class="<%=label.getClassType()%>" for="RelUtentePTH"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YAziendeUserPortalRelUtentePTH =  
     new com.thera.thermfw.web.WebMultiSearchForm("YAziendeUserPortal", "RelUtentePTH", false, false, true, 1, null, null); 
  YAziendeUserPortalRelUtentePTH.setParent(YAziendeUserPortalForm); 
  YAziendeUserPortalRelUtentePTH.setAdditionalRestrictConditions("IdAzienda,IdAzienda"); 
  YAziendeUserPortalRelUtentePTH.setSpecificDOList("it.valvorobica.thip.base.portal.web.YUserPortalUtentePTHDoList"); 
  YAziendeUserPortalRelUtentePTH.write(out); 
%>
<!--<span class="multisearchform" id="RelUtentePTH"></span>-->
                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAziendeUserPortal", "TipoUser", null); 
   label.setParent(YAziendeUserPortalForm); 
%><label class="<%=label.getClassType()%>" for="TipoUser"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebComboBox YAziendeUserPortalTipoUser =  
     new com.thera.thermfw.web.WebComboBox("YAziendeUserPortal", "TipoUser", null); 
  YAziendeUserPortalTipoUser.setParent(YAziendeUserPortalForm); 
  YAziendeUserPortalTipoUser.setOnChange("changeTipoCliente()"); 
%>
<select id="<%=YAziendeUserPortalTipoUser.getId()%>" name="<%=YAziendeUserPortalTipoUser.getName()%>"><% 
  YAziendeUserPortalTipoUser.write(out); 
%> 
</select>
                    </td>
                  </tr>
                  <tr style="display:none">
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAziendeUserPortal", "RAgente", null); 
   label.setParent(YAziendeUserPortalForm); 
%><label class="<%=label.getClassType()%>" for="RelAgente"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YAziendeUserPortalRelAgente =  
     new com.thera.thermfw.web.WebMultiSearchForm("YAziendeUserPortal", "RelAgente", false, false, true, 1, null, null); 
  YAziendeUserPortalRelAgente.setParent(YAziendeUserPortalForm); 
  YAziendeUserPortalRelAgente.setAdditionalRestrictConditions("IdAzienda,IdAzienda"); 
  YAziendeUserPortalRelAgente.setSpecificDOList("it.valvorobica.thip.base.portal.web.YAgentePortalDoList"); 
  YAziendeUserPortalRelAgente.write(out); 
%>
<!--<span class="multisearchform" id="RelAgente"></span>-->
                    </td>
                  </tr>
                  <tr style="display:none">
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAziendeUserPortal", "RCliente", null); 
   label.setParent(YAziendeUserPortalForm); 
%><label class="<%=label.getClassType()%>" for="RelCliente"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YAziendeUserPortalRelCliente =  
     new com.thera.thermfw.web.WebMultiSearchForm("YAziendeUserPortal", "RelCliente", false, false, true, 1, null, null); 
  YAziendeUserPortalRelCliente.setParent(YAziendeUserPortalForm); 
  YAziendeUserPortalRelCliente.setAdditionalRestrictConditions("IdAzienda,IdAzienda"); 
  YAziendeUserPortalRelCliente.setSpecificDOList("it.valvorobica.thip.base.portal.web.YClientePortalDoList"); 
  YAziendeUserPortalRelCliente.write(out); 
%>
<!--<span class="multisearchform" id="RelCliente"></span>-->
                    </td>
                  </tr>
                  <tr style="display:none">
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAziendeUserPortal", "RDipendente", null); 
   label.setParent(YAziendeUserPortalForm); 
%><label class="<%=label.getClassType()%>" for="RelDipendente"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YAziendeUserPortalRelDipendente =  
     new com.thera.thermfw.web.WebMultiSearchForm("YAziendeUserPortal", "RelDipendente", false, false, true, 1, null, null); 
  YAziendeUserPortalRelDipendente.setParent(YAziendeUserPortalForm); 
  YAziendeUserPortalRelDipendente.setAdditionalRestrictConditions("IdAzienda,IdAzienda"); 
  YAziendeUserPortalRelDipendente.setSpecificDOList("it.valvorobica.thip.base.portal.web.YDipendentePortalDoList"); 
  YAziendeUserPortalRelDipendente.write(out); 
%>
<!--<span class="multisearchform" id="RelDipendente"></span>-->
                    </td>
                  </tr>
                  <tr style="display:none">
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YAziendeUserPortal", "RFornitore", null); 
   label.setParent(YAziendeUserPortalForm); 
%><label class="<%=label.getClassType()%>" for="RelFornitore"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm YAziendeUserPortalRelFornitore =  
     new com.thera.thermfw.web.WebMultiSearchForm("YAziendeUserPortal", "RelFornitore", false, false, true, 1, null, null); 
  YAziendeUserPortalRelFornitore.setParent(YAziendeUserPortalForm); 
  YAziendeUserPortalRelFornitore.setAdditionalRestrictConditions("IdAzienda,IdAzienda"); 
  YAziendeUserPortalRelFornitore.setSpecificDOList("it.valvorobica.thip.base.portal.web.YFornitorePortaleDoList"); 
  YAziendeUserPortalRelFornitore.write(out); 
%>
<!--<span class="multisearchform" id="RelFornitore"></span>-->
                    </td>
                  </tr>
                  <tr>
					  <td><% 
  WebCheckBox YAziendeUserPortalECommerce =  
     new com.thera.thermfw.web.WebCheckBox("YAziendeUserPortal", "ECommerce"); 
  YAziendeUserPortalECommerce.setParent(YAziendeUserPortalForm); 
%>
<input id="<%=YAziendeUserPortalECommerce.getId()%>" name="<%=YAziendeUserPortalECommerce.getName()%>" type="checkbox" value="Y"><%
  YAziendeUserPortalECommerce.write(out); 
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
  errorList.setParent(YAziendeUserPortalForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YAziendeUserPortalForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YAziendeUserPortalForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YAziendeUserPortalBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YAziendeUserPortalForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YAziendeUserPortalBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YAziendeUserPortalBODC.getErrorList().getErrors()); 
           if(YAziendeUserPortalBODC.getConflict() != null) 
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
     if(YAziendeUserPortalBODC != null && !YAziendeUserPortalBODC.close(false)) 
        errors.addAll(0, YAziendeUserPortalBODC.getErrorList().getErrors()); 
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
     String errorPage = YAziendeUserPortalForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YAziendeUserPortalBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YAziendeUserPortalForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
