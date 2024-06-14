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
  BODataCollector YMatricolaValvoBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YMatricolaValvoForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YMatricolaValvoForm", "YMatricolaValvo", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/valvorobica/thip/base/matricole/YMatricolaValvo.js"); 
  YMatricolaValvoForm.setServletEnvironment(se); 
  YMatricolaValvoForm.setJSTypeList(jsList); 
  YMatricolaValvoForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YMatricolaValvoForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YMatricolaValvoForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YMatricolaValvoForm.getMode(); 
  String key = YMatricolaValvoForm.getKey(); 
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
        YMatricolaValvoForm.outTraceInfo(getClass().getName()); 
        String collectorName = YMatricolaValvoForm.findBODataCollectorName(); 
                YMatricolaValvoBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YMatricolaValvoBODC instanceof WebDataCollector) 
            ((WebDataCollector)YMatricolaValvoBODC).setServletEnvironment(se); 
        YMatricolaValvoBODC.initialize("YMatricolaValvo", true, 0); 
        YMatricolaValvoForm.setBODataCollector(YMatricolaValvoBODC); 
        int rcBODC = YMatricolaValvoForm.initSecurityServices(); 
        mode = YMatricolaValvoForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YMatricolaValvoForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YMatricolaValvoBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YMatricolaValvoForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YMatricolaValvoForm); 
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
  myToolBarTB.setParent(YMatricolaValvoForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=YMatricolaValvoForm.getBodyOnBeforeUnload()%>" onload="<%=YMatricolaValvoForm.getBodyOnLoad()%>" onunload="<%=YMatricolaValvoForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YMatricolaValvoForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YMatricolaValvoForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YMatricolaValvoBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YMatricolaValvoForm.getServlet()%>" method="post" name="YMatricolaValvoForm" style="height:100%"><%
  YMatricolaValvoForm.writeFormStartElements(out); 
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
  WebTextInput YMatricolaValvoIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YMatricolaValvo", "IdAzienda"); 
  YMatricolaValvoIdAzienda.setParent(YMatricolaValvoForm); 
%>
<input class="<%=YMatricolaValvoIdAzienda.getClassType()%>" id="<%=YMatricolaValvoIdAzienda.getId()%>" maxlength="<%=YMatricolaValvoIdAzienda.getMaxLength()%>" name="<%=YMatricolaValvoIdAzienda.getName()%>" size="<%=YMatricolaValvoIdAzienda.getSize()%>" type="hidden"><% 
  YMatricolaValvoIdAzienda.write(out); 
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
  mytabbed.setParent(YMatricolaValvoForm); 
 mytabbed.addTab("tab1", "it.valvorobica.thip.base.matricole.resources.YMatricolaValvo", "tab1", "YMatricolaValvo", null, null, null, null); 
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
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatricolaValvo", "IdMatricola", null); 
   label.setParent(YMatricolaValvoForm); 
%><label class="<%=label.getClassType()%>" for="IdMatricola"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMatricolaValvoIdMatricola =  
     new com.thera.thermfw.web.WebTextInput("YMatricolaValvo", "IdMatricola"); 
  YMatricolaValvoIdMatricola.setParent(YMatricolaValvoForm); 
%>
<input class="<%=YMatricolaValvoIdMatricola.getClassType()%>" id="<%=YMatricolaValvoIdMatricola.getId()%>" maxlength="<%=YMatricolaValvoIdMatricola.getMaxLength()%>" name="<%=YMatricolaValvoIdMatricola.getName()%>" size="<%=YMatricolaValvoIdMatricola.getSize()%>"><% 
  YMatricolaValvoIdMatricola.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatricolaValvo", "Body", null); 
   label.setParent(YMatricolaValvoForm); 
%><label class="<%=label.getClassType()%>" for="Body"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMatricolaValvoBody =  
     new com.thera.thermfw.web.WebTextInput("YMatricolaValvo", "Body"); 
  YMatricolaValvoBody.setParent(YMatricolaValvoForm); 
%>
<input class="<%=YMatricolaValvoBody.getClassType()%>" id="<%=YMatricolaValvoBody.getId()%>" maxlength="<%=YMatricolaValvoBody.getMaxLength()%>" name="<%=YMatricolaValvoBody.getName()%>" size="<%=YMatricolaValvoBody.getSize()%>"><% 
  YMatricolaValvoBody.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatricolaValvo", "Disc", null); 
   label.setParent(YMatricolaValvoForm); 
%><label class="<%=label.getClassType()%>" for="Disc"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMatricolaValvoDisc =  
     new com.thera.thermfw.web.WebTextInput("YMatricolaValvo", "Disc"); 
  YMatricolaValvoDisc.setParent(YMatricolaValvoForm); 
%>
<input class="<%=YMatricolaValvoDisc.getClassType()%>" id="<%=YMatricolaValvoDisc.getId()%>" maxlength="<%=YMatricolaValvoDisc.getMaxLength()%>" name="<%=YMatricolaValvoDisc.getName()%>" size="<%=YMatricolaValvoDisc.getSize()%>"><% 
  YMatricolaValvoDisc.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YMatricolaValvo", "Shaft", null); 
   label.setParent(YMatricolaValvoForm); 
%><label class="<%=label.getClassType()%>" for="Shaft"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebTextInput YMatricolaValvoShaft =  
     new com.thera.thermfw.web.WebTextInput("YMatricolaValvo", "Shaft"); 
  YMatricolaValvoShaft.setParent(YMatricolaValvoForm); 
%>
<input class="<%=YMatricolaValvoShaft.getClassType()%>" id="<%=YMatricolaValvoShaft.getId()%>" maxlength="<%=YMatricolaValvoShaft.getMaxLength()%>" name="<%=YMatricolaValvoShaft.getName()%>" size="<%=YMatricolaValvoShaft.getSize()%>"><% 
  YMatricolaValvoShaft.write(out); 
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
  errorList.setParent(YMatricolaValvoForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  YMatricolaValvoForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YMatricolaValvoForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YMatricolaValvoBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YMatricolaValvoForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YMatricolaValvoBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YMatricolaValvoBODC.getErrorList().getErrors()); 
           if(YMatricolaValvoBODC.getConflict() != null) 
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
     if(YMatricolaValvoBODC != null && !YMatricolaValvoBODC.close(false)) 
        errors.addAll(0, YMatricolaValvoBODC.getErrorList().getErrors()); 
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
     String errorPage = YMatricolaValvoForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YMatricolaValvoBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YMatricolaValvoForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
