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
  BODataCollector PsnDatiFirmaDigitaleBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm PsnDatiFirmaDigitaleForm =  
     new com.thera.thermfw.web.WebForm(request, response, "PsnDatiFirmaDigitaleForm", "PsnDatiFirmaDigitale", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/softre/thip/base/firmadigitale/PsnDatiFirmaDigitale.js"); 
  PsnDatiFirmaDigitaleForm.setServletEnvironment(se); 
  PsnDatiFirmaDigitaleForm.setJSTypeList(jsList); 
  PsnDatiFirmaDigitaleForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  PsnDatiFirmaDigitaleForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  PsnDatiFirmaDigitaleForm.setWebFormModifierClass("it.softre.thip.base.firmadigitale.web.PsnDatiFirmaDigitaleFormModifier"); 
  PsnDatiFirmaDigitaleForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = PsnDatiFirmaDigitaleForm.getMode(); 
  String key = PsnDatiFirmaDigitaleForm.getKey(); 
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
        PsnDatiFirmaDigitaleForm.outTraceInfo(getClass().getName()); 
        String collectorName = PsnDatiFirmaDigitaleForm.findBODataCollectorName(); 
                PsnDatiFirmaDigitaleBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (PsnDatiFirmaDigitaleBODC instanceof WebDataCollector) 
            ((WebDataCollector)PsnDatiFirmaDigitaleBODC).setServletEnvironment(se); 
        PsnDatiFirmaDigitaleBODC.initialize("PsnDatiFirmaDigitale", true, 0); 
        PsnDatiFirmaDigitaleForm.setBODataCollector(PsnDatiFirmaDigitaleBODC); 
        int rcBODC = PsnDatiFirmaDigitaleForm.initSecurityServices(); 
        mode = PsnDatiFirmaDigitaleForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           PsnDatiFirmaDigitaleForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = PsnDatiFirmaDigitaleBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              PsnDatiFirmaDigitaleForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(PsnDatiFirmaDigitaleForm); 
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
  myToolBarTB.setParent(PsnDatiFirmaDigitaleForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>

<body onbeforeunload="<%=PsnDatiFirmaDigitaleForm.getBodyOnBeforeUnload()%>" onload="<%=PsnDatiFirmaDigitaleForm.getBodyOnLoad()%>" onunload="<%=PsnDatiFirmaDigitaleForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   PsnDatiFirmaDigitaleForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = PsnDatiFirmaDigitaleForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", PsnDatiFirmaDigitaleBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=PsnDatiFirmaDigitaleForm.getServlet()%>" method="post" name="PsnDatiFirmaDigitaleForm" style="height:100%"><%
  PsnDatiFirmaDigitaleForm.writeFormStartElements(out); 
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
  WebTextInput PsnDatiFirmaDigitaleIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("PsnDatiFirmaDigitale", "IdAzienda"); 
  PsnDatiFirmaDigitaleIdAzienda.setParent(PsnDatiFirmaDigitaleForm); 
%>
<input class="<%=PsnDatiFirmaDigitaleIdAzienda.getClassType()%>" id="<%=PsnDatiFirmaDigitaleIdAzienda.getId()%>" maxlength="<%=PsnDatiFirmaDigitaleIdAzienda.getMaxLength()%>" name="<%=PsnDatiFirmaDigitaleIdAzienda.getName()%>" size="<%=PsnDatiFirmaDigitaleIdAzienda.getSize()%>" type="hidden"><% 
  PsnDatiFirmaDigitaleIdAzienda.write(out); 
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
  mytabbed.setParent(PsnDatiFirmaDigitaleForm); 
 mytabbed.addTab("tab1", "it.softre.thip.base.firmadigitale.resources.PsnDatiFirmaDigitale", "tab1", "PsnDatiFirmaDigitale", null, null, null, null); 
 mytabbed.addTab("tab2", "it.softre.thip.base.firmadigitale.resources.PsnDatiFirmaDigitale", "tab2", "PsnDatiFirmaDigitale", null, null, null, null); 
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
										<%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "PsnDatiFirmaDigitale", "Abilitata", null); 
   label.setParent(PsnDatiFirmaDigitaleForm); 
%><label class="<%=label.getClassType()%>" for="Abilitata"><%label.write(out);%></label><%}%>
									</td>
									<td valign="top">
										<% 
  WebComboBox PsnDatiFirmaDigitaleAbilitata =  
     new com.thera.thermfw.web.WebComboBox("PsnDatiFirmaDigitale", "Abilitata", null); 
  PsnDatiFirmaDigitaleAbilitata.setParent(PsnDatiFirmaDigitaleForm); 
%>
<select id="<%=PsnDatiFirmaDigitaleAbilitata.getId()%>" name="<%=PsnDatiFirmaDigitaleAbilitata.getName()%>"><% 
  PsnDatiFirmaDigitaleAbilitata.write(out); 
%> 
</select>
									</td>
								</tr>
								<tr>
									<td valign="top">
									</td>
									<td valign="top">
									</td>
								</tr>
								<tr>
									<td colspan="2" valign="top">
										<%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "PsnDatiFirmaDigitale", null, "AssociazioneDocumenti"); 
   label.setParent(PsnDatiFirmaDigitaleForm); 
%><label class="<%=label.getClassType()%>" for="AssociazioneDocumenti"><%label.write(out);%></label><%}%>
									</td>
								</tr>
								<tr>
									<td colspan="2" valign="top">
										<!--<span class="editgrid" id="AssociazioneDocumenti">--><% 
  WebEditGrid PsnDatiFirmaDigitaleAssociazioneDocumenti =  
     new com.thera.thermfw.web.WebEditGrid("PsnDatiFirmaDigitale", "AssociazioneDocumenti", 8, new String[]{"IdTipoDocumento", "IdAzienda", "TipoDocVen", "TipoDocAcq", "XPosition", "YPosition", "Width", "Height", "Azienda.Descrizione", "TipoDocumento.Descrizione.Descrizione", "StampaAbilitata", "CopyNumber"}, 1, null, null,false,"com.thera.thermfw.web.servlet.GridActionAdapterForIndependentRow"); 
 PsnDatiFirmaDigitaleAssociazioneDocumenti.setParent(PsnDatiFirmaDigitaleForm); 
 PsnDatiFirmaDigitaleAssociazioneDocumenti.setNoControlRowKeys(false); 
 PsnDatiFirmaDigitaleAssociazioneDocumenti.addHideAsDefault("TipoDocumento.Descrizione.Descrizione"); 
 PsnDatiFirmaDigitaleAssociazioneDocumenti.addHideAsDefault("Azienda.Descrizione"); 
 PsnDatiFirmaDigitaleAssociazioneDocumenti.write(out); 
%>
<BR><% 
   request.setAttribute("parentForm", PsnDatiFirmaDigitaleForm); 
   String CDForAssociazioneDocumenti = "AssociazioneDocumenti"; 
%>
<jsp:include page="/it/softre/thip/base/firmadigitale/AssociazioneTipoDocFirma.jsp" flush="true"> 
<jsp:param name="EditGridCDName" value="<%=CDForAssociazioneDocumenti%>"/> 
<jsp:param name="Mode" value="NEW"/> 
</jsp:include> 
<!--</span>-->
									</td>
								</tr>
							</table>
						<% mytabbed.endTab(); %> 
</div>
						<div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab2")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab2"); %>
							<table style="width: 100%;">
								<tr>
									<td colspan="2" valign="top"><!--<span class="editgrid" id="ListaVettoriEsclusi">--><% 
  WebEditGrid PsnDatiFirmaDigitaleListaVettoriEsclusi =  
     new com.thera.thermfw.web.WebEditGrid("PsnDatiFirmaDigitale", "ListaVettoriEsclusi", 8, new String[]{"IdVettore", "IdAzienda", "Azienda.Descrizione", "Vettore.RagioneSociale"}, 0, null, null,false,"com.thera.thermfw.web.servlet.GridActionAdapterForIndependentRow"); 
 PsnDatiFirmaDigitaleListaVettoriEsclusi.setParent(PsnDatiFirmaDigitaleForm); 
 PsnDatiFirmaDigitaleListaVettoriEsclusi.setNoControlRowKeys(false); 
 PsnDatiFirmaDigitaleListaVettoriEsclusi.addHideAsDefault("Vettore.RagioneSociale"); 
 PsnDatiFirmaDigitaleListaVettoriEsclusi.addHideAsDefault("Azienda.Descrizione"); 
 PsnDatiFirmaDigitaleListaVettoriEsclusi.write(out); 
%>
<!--</span>--></td>
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
  errorList.setParent(PsnDatiFirmaDigitaleForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>-->
				</td>
			</tr>
		</table>
	<%
  PsnDatiFirmaDigitaleForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = PsnDatiFirmaDigitaleForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", PsnDatiFirmaDigitaleBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              PsnDatiFirmaDigitaleForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, PsnDatiFirmaDigitaleBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, PsnDatiFirmaDigitaleBODC.getErrorList().getErrors()); 
           if(PsnDatiFirmaDigitaleBODC.getConflict() != null) 
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
     if(PsnDatiFirmaDigitaleBODC != null && !PsnDatiFirmaDigitaleBODC.close(false)) 
        errors.addAll(0, PsnDatiFirmaDigitaleBODC.getErrorList().getErrors()); 
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
     String errorPage = PsnDatiFirmaDigitaleForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", PsnDatiFirmaDigitaleBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = PsnDatiFirmaDigitaleForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>

</html>
