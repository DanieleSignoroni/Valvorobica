<%@page import="com.thera.thermfw.base.Trace"%>
<%@page import="it.valvorobica.thip.base.generale.ws.YRetrieveGiacenzeContoDep"%>
<%@page import="com.thera.thermfw.persist.ConnectionManager"%>
<%@page import="com.thera.thermfw.persist.ConnectionDescriptor"%>
<%@page import="com.thera.thermfw.web.SessionEnvironment"%>
<%@page import="com.thera.thermfw.security.Security"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.thera.thermfw.base.IniFile"%>
<%@page import="it.valvorobica.thip.base.portal.YUserPortalSession"%>
<%
YUserPortalSession userPortalSession = (YUserPortalSession) request.getSession().getAttribute("YUserPortal");
String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");
Map values = new LinkedHashMap();
boolean isopen = false;
Object[] info = SessionEnvironment.getDBInfoFromIniFile();
String dbName = (String) info[0];
String newsHtml = null;
try {
	if (!Security.isCurrentDatabaseSetted()) {
		Security.setCurrentDatabase(dbName, null);
	}
	Security.openDefaultConnection();
	isopen = true;
	ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
	YRetrieveGiacenzeContoDep ws = new YRetrieveGiacenzeContoDep();
	ws.setUseAuthentication(false);
	ws.setUseAuthorization(false);
	ws.setUseLicence(false);
	ws.setConnectionDescriptor(cd);
	ws.setUserPortalSession(userPortalSession);
	values = ws.send();
} catch (Throwable t) {
	t.printStackTrace(Trace.excStream);
} finally {
	if (isopen) {
		Security.closeDefaultConnection();
	}
}
%>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>Giacenze</title>

<!-- CSS Files -->
<link href="css/frames.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="/<%=webAppPath%>/it/valvorobica/thip/base/portal/js/jquery.js"></script>
<link href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/css/modal_flusso_vendite.css?v=1.00" rel="stylesheet">
<link href="/<%=webAppPath%>/it/valvorobica/thip/assets/DataTables/datatables.css?v=1.00" rel="stylesheet">
<link href="/<%=webAppPath%>/it/valvorobica/thip/assets/DataTables/Select/css/select.dataTables.css" rel="stylesheet">
<link href="/<%=webAppPath%>/it/valvorobica/thip/assets/DataTables/Buttons/css/buttons.dataTables.css" rel="stylesheet">
<script src="/<%=webAppPath%>/it/valvorobica/thip/assets/DataTables/datatables.js?v=1.00"></script>
<script src="/<%=webAppPath%>/it/valvorobica/thip/assets/DataTables/Select/js/dataTables.select.js"></script>
<script src="/<%=webAppPath%>/it/valvorobica/thip/assets/DataTables/Buttons/js/dataTables.buttons.js"></script>
</head>
<body class="noscrollbar" onload="rimuoviSpinner()">
	<input type="hidden" name="urlWS" id="urlWS" value="">
	<input type="hidden" name="webAppPath" id="webAppPath"
		value="<%=webAppPath%>">
	<input type="hidden" name="Azienda" id="Azienda"
		value="<%=userPortalSession.getIdAzienda()%>">
	<input type="hidden" name="token" id="token"
		value="<%=userPortalSession.getTokecUID()%>">

	<div class="table-responsive">
		<table class="table table-striped table-hover table-bordered"
			id="tableOrdini" style="width:100%">
			<thead class="thead-dark">
				<tr>
					<th scope="col"></th>	<!-- Lasciare vuota, sede della checkbox -->
					<th scope="col">N.ro Ordine</th>
					<th scope="col">Key</th>
					<th scope="col">Data</th>
					<th scope="col">Sede di</th>
					<th scope="col">Rif Vs Ord</th>
					<th scope="col">Rag. Sociale</th>
					<th scope="col">Consegna</th>
					<th scope="col">Imponibile</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>

	<script type="text/javascript" src="js/main_1.js"></script>
	<script>
		$(document).ready(function() {
			table = new DataTable('#tableOrdini', {
				dom: '<"top"B>frt<"bottom"lip><"clear">',
				        buttons: [
				        	{
				            	className: 'btn btn-secondary btn-sm',
				                text: 'Flusso Vendite',
				                action: function () {
				                	if(table.rows('.selected').data().length > 1){
				                		table.rows('.selected').deselect();
				                		var txt = "Selezionare solo un ordine";
				        				$('#txtWarning',parent.document)[0].innerHTML = txt;
				        				$('#modalWarningClick',parent.document)[0].click();
				                	}else if (table.rows('.selected').data().length < 1){
				                		table.rows('.selected').deselect();
				                		var txt = "Selezionare un ordine per vedere i documenti di trasporto collegati";
				        				$('#txtWarning',parent.document)[0].innerHTML = txt;
				        				$('#modalWarningClick',parent.document)[0].click();
				                	}else{
				                		flussoVendite(table.rows('.selected').data());
				                	}
				                }
				            }
				        ], 
				        initComplete: function() { 
				            var btns = $('.dt-button');
				            btns.removeClass('dt-button');
				          },
					select: {
						style: 'multi',
				        selector: 'td:first-child'
				    },
				    columnDefs : [
				    	{
				            orderable: false,
				            className: 'select-checkbox',
				            targets: 0
			       	 	},
			       	 	{
			                target: 2,
			                visible: false
			            },
					],
					 columns: [
						 	{ data : 'Checkbox'},
						 	{ data: 'Numero ordine'},
						 	{ data: 'Key'},
					        { data: 'Data' },
					        { data: 'Sede di' },
					        { data: 'Rif vs Ord' },
					        { data: 'Rag.Sociale' },
					        { data: 'Consegna' },
					        { data: 'Imponibile' }
					    ]
				});
			compilaURLWS();
		});

		function compilaURLWS() {
			var ris;
			var url = window.location.href;
			var wbAppPth = parent.document.getElementById('webAppPath').value;
			var cut = url.indexOf(wbAppPth);
			ris = url.substring(0, cut);
			ris += wbAppPth;
			ris += "/ws";
			document.getElementById('urlWS').value = ris;
		}
	</script>

</body>
</html>