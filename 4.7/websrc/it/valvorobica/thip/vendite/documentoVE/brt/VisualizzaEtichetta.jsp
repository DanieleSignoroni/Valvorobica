<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/4.7.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<html>
<%@page import="it.thera.thip.base.azienda.Azienda"%>
<%@page import="com.thera.thermfw.persist.KeyHelper"%>
<%@page import="java.util.Collection"%>
<%@page import="com.thera.thermfw.common.ErrorMessage"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Base64"%>
<%@page import="it.valvorobica.thip.vendite.documentoVE.brt.EtichetteBartolini"%>
<%@page import="com.thera.thermfw.web.WebForm"%>
<%@page import="com.thera.thermfw.collector.BODataCollector"%>
<%@page import="com.thera.thermfw.persist.Factory"%>
<%@page import="com.thera.thermfw.base.Trace"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.thera.thermfw.web.ServletEnvironment"%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="icon" type="image/x-icon" href="favicon.ico"></link>
<title>Visualizza Etichetta</title>
</head>
<body>
<!-- 
E' una jsp dove viene visualizzata l'etichetta presente nel database.
Simulo l'inizializzazione di una form di Panthera per avere favicon e import di base.
Poi inizializzo il BODC come da std e visualizzo un frame con la stringa base64 encoded dell'etichetta.
 -->
<%
WebForm StampaEtichetteBartoliniForm = new WebForm(request,response, "StampaEtichetteBartoliniForm", "StampaEtichetteBartolini", null, "it.valvorobica.thip.vendite.documentoVE.brt.web.StampaEtichetteBartoliniFormActionAdapter", false, false, true, true, true, true, null, 0, true, null);
List<ErrorMessage> errors = new ArrayList<>(); 
ServletEnvironment se = (ServletEnvironment) Factory.createObject("com.thera.thermfw.web.ServletEnvironment");
try {
	se.initialize(request, response);
	if (se.begin()) {
		 StampaEtichetteBartoliniForm.setServletEnvironment(se);
		String key = (String) request.getAttribute("ChiaveEtichetta");
		BODataCollector EtichetteBartoliniBODC = (BODataCollector) Factory.createObject(BODataCollector.class);
		EtichetteBartoliniBODC.initialize("EtichetteBartolini", true, 0);
		int rcBODC = EtichetteBartoliniBODC.initSecurityServices("SHOW", WebForm.NEW, true, false, false);
		if (rcBODC == BODataCollector.OK) {
			String[] cs = KeyHelper.unpackObjectKey(key);
			key = Azienda.getAziendaCorrente() + KeyHelper.KEY_SEPARATOR + cs[1];
			rcBODC = EtichetteBartoliniBODC.retrieve(key);
			if (rcBODC == BODataCollector.OK) {
				StampaEtichetteBartoliniForm.write(out);
				EtichetteBartolini etichetta = (EtichetteBartolini) EtichetteBartoliniBODC.getBo();
				String base64Pdf = Base64.getEncoder().encodeToString(etichetta.getEtichettaBase64().getBytes());
				String dataUrl = "data:application/pdf;base64," + base64Pdf;
				%>
					 <iframe src="<%= dataUrl %>" width="100%" height="100%">
      						  This browser does not support PDFs. Please download the PDF to view it: 
        				<a href="<%= dataUrl %>">Download PDF</a>.
   					 </iframe>
				<% 
			}
		}else{
			 errors.addAll(0, (Collection<ErrorMessage>) EtichetteBartoliniBODC.getErrorList().getErrors()); 
		}
		  if(!errors.isEmpty())
		  { 
		     request.setAttribute("ErrorMessages", errors); 
		     String errorPage = "/it/thera/thip/cs/ErrorHandler.jsp?thClassName=EtichetteBartolini"; 
		%> 
		     <jsp:include page="<%=errorPage%>" flush="true"/> 
		<% 
		  }
	}
} catch (SQLException e) {
	e.printStackTrace(Trace.excStream);
} catch (Throwable e) {
	e.printStackTrace(Trace.excStream);
} finally {
	try {
		se.end();
	} catch (IllegalArgumentException e) {
		e.printStackTrace(Trace.excStream);
	} catch (SQLException e) {
		e.printStackTrace(Trace.excStream);
	}
}
%>
</body>
</html>