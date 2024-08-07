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
  BODataCollector StampaEtichetteBartoliniBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm StampaEtichetteBartoliniForm =  
     new com.thera.thermfw.web.WebForm(request, response, "StampaEtichetteBartoliniForm", "StampaEtichetteBartolini", null, "it.valvorobica.thip.vendite.documentoVE.brt.web.StampaEtichetteBartoliniFormActionAdapter", false, false, true, true, true, true, null, 0, true, null); 
  StampaEtichetteBartoliniForm.setServletEnvironment(se); 
  StampaEtichetteBartoliniForm.setJSTypeList(jsList); 
  StampaEtichetteBartoliniForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  StampaEtichetteBartoliniForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  StampaEtichetteBartoliniForm.setWebFormModifierClass("it.valvorobica.thip.vendite.documentoVE.brt.web.StampaEtichetteBartoliniFormModifier"); 
  StampaEtichetteBartoliniForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = StampaEtichetteBartoliniForm.getMode(); 
  String key = StampaEtichetteBartoliniForm.getKey(); 
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
        StampaEtichetteBartoliniForm.outTraceInfo(getClass().getName()); 
        String collectorName = StampaEtichetteBartoliniForm.findBODataCollectorName(); 
                StampaEtichetteBartoliniBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (StampaEtichetteBartoliniBODC instanceof WebDataCollector) 
            ((WebDataCollector)StampaEtichetteBartoliniBODC).setServletEnvironment(se); 
        StampaEtichetteBartoliniBODC.initialize("StampaEtichetteBartolini", true, 0); 
        StampaEtichetteBartoliniForm.setBODataCollector(StampaEtichetteBartoliniBODC); 
        int rcBODC = StampaEtichetteBartoliniForm.initSecurityServices(); 
        mode = StampaEtichetteBartoliniForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           StampaEtichetteBartoliniForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = StampaEtichetteBartoliniBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              StampaEtichetteBartoliniForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(StampaEtichetteBartoliniForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
  <body onbeforeunload="<%=StampaEtichetteBartoliniForm.getBodyOnBeforeUnload()%>" onload="<%=StampaEtichetteBartoliniForm.getBodyOnLoad()%>" onunload="<%=StampaEtichetteBartoliniForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   StampaEtichetteBartoliniForm.writeBodyStartElements(out); 
%> 

    <table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = StampaEtichetteBartoliniForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", StampaEtichetteBartoliniBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=StampaEtichetteBartoliniForm.getServlet()%>" method="post" name="StampaEtichetteBartolini" style="height:100%"><%
  StampaEtichetteBartoliniForm.writeFormStartElements(out); 
%>

      <table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" width="100%">
        <tr>
          <td style="height:0">
            <% myToolBarTB.writeChildren(out); %> 

          </td>
        </tr>
        <tr>
          <td>
            <% 
  WebTextInput StampaEtichetteBartoliniIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("StampaEtichetteBartolini", "IdAzienda"); 
  StampaEtichetteBartoliniIdAzienda.setParent(StampaEtichetteBartoliniForm); 
%>
<input class="<%=StampaEtichetteBartoliniIdAzienda.getClassType()%>" id="<%=StampaEtichetteBartoliniIdAzienda.getId()%>" maxlength="<%=StampaEtichetteBartoliniIdAzienda.getMaxLength()%>" name="<%=StampaEtichetteBartoliniIdAzienda.getName()%>" size="<%=StampaEtichetteBartoliniIdAzienda.getSize()%>" type="hidden"><% 
  StampaEtichetteBartoliniIdAzienda.write(out); 
%>

          </td>
        </tr>
        <tr>
          <td>
                <table>
                  <tr style="display:none;">
                     <td valign="top">
                      <% 
  WebTextInput StampaEtichetteBartoliniChiaveSelezionato =  
     new com.thera.thermfw.web.WebTextInput("StampaEtichetteBartolini", "ChiaveSelezionato"); 
  StampaEtichetteBartoliniChiaveSelezionato.setParent(StampaEtichetteBartoliniForm); 
%>
<input class="<%=StampaEtichetteBartoliniChiaveSelezionato.getClassType()%>" id="<%=StampaEtichetteBartoliniChiaveSelezionato.getId()%>" maxlength="<%=StampaEtichetteBartoliniChiaveSelezionato.getMaxLength()%>" name="<%=StampaEtichetteBartoliniChiaveSelezionato.getName()%>" size="<%=StampaEtichetteBartoliniChiaveSelezionato.getSize()%>"><% 
  StampaEtichetteBartoliniChiaveSelezionato.write(out); 
%>

                    </td>
                  </tr>
                  <tr>
                    <td valign="top">
                      <%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "StampaEtichetteBartolini", "IdStampante", null); 
   label.setParent(StampaEtichetteBartoliniForm); 
%><label class="<%=label.getClassType()%>" for="Stampante"><%label.write(out);%></label><%}%>
                    </td>
                    <td valign="top">
                      <% 
  WebMultiSearchForm StampaEtichetteBartoliniStampante =  
     new com.thera.thermfw.web.WebMultiSearchForm("StampaEtichetteBartolini", "Stampante", false, false, true, 1, null, null); 
  StampaEtichetteBartoliniStampante.setParent(StampaEtichetteBartoliniForm); 
  StampaEtichetteBartoliniStampante.write(out); 
%>
<!--<span class="multisearchform" id="Stampante"></span>-->
                    </td>
                  </tr>
                </table>
          </td>
        </tr>
        <tr>
          <td style="height:0">
            <% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(StampaEtichetteBartoliniForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
          </td>
        </tr>
      </table>
    <%
  StampaEtichetteBartoliniForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = StampaEtichetteBartoliniForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", StampaEtichetteBartoliniBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


  <%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              StampaEtichetteBartoliniForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, StampaEtichetteBartoliniBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, StampaEtichetteBartoliniBODC.getErrorList().getErrors()); 
           if(StampaEtichetteBartoliniBODC.getConflict() != null) 
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
     if(StampaEtichetteBartoliniBODC != null && !StampaEtichetteBartoliniBODC.close(false)) 
        errors.addAll(0, StampaEtichetteBartoliniBODC.getErrorList().getErrors()); 
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
     String errorPage = StampaEtichetteBartoliniForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", StampaEtichetteBartoliniBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = StampaEtichetteBartoliniForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
