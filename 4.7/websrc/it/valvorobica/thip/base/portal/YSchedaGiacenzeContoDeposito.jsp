<%@page import="org.json.JSONObject"%>
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
JSONObject result = null;
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
	result = (JSONObject) values.get("listaGiacenze");
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
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>

	<script type="text/javascript" src="js/main_1.js"></script>
	<script>
		$(document).ready(function() {
		    var dtHeaders = <%= result.get("headers") %>;
		    var dtRecords = <%= result.get("records") %>;
	
		    dtHeaders.push(
		        {
		            title: "Quantità",
		            data: null,
		            orderable: false,
		            className: "text-center align-middle",
		            render: function(data, type, row) {
		                var idArticoloPth = row.Articolo || '';
		                var giacenza = row.Giacenza || 0;
	
		                return "<input type='hidden' name='IdArticoloPth' value='" + idArticoloPth + "'>" +
		                       "<input type='hidden' name='disp' value='" + giacenza + "'>" +
		                       "<input class='form-control rcrPrz' type='number' name='quantita' onchange='handleSemaforo(this.closest(\"tr\"))' style='width: 80px; margin: auto;'>";
		            }
		        },
		        {
		            title: "Disp.",
		            data: null,
		            orderable: false,
		            className: "text-center align-middle",
		            render: function(data, type, row) {
		            	return "<span class='semaforo-container'><i class='fa fa-solid fa-circle ml-2' style='color:black;'></i></span>";
		            }
		        },
		        {
		            title: "Carrello",
		            data: null,
		            orderable: false,
		            className: "text-center align-middle",
		            render: function(data, type, row) {
		            	return "<i onclick='addToCart(this)' title='Aggiungi al carrello' class='fa fa-regular fa-plus fa-2x' style='cursor:pointer;'></i>";
		            }
		        }
		    );
	
		    $('#tableOrdini').DataTable({
		        columns: dtHeaders,
		        data: dtRecords,
		    });
		    compilaURLWS();
		});
		function handleSemaforo(row) {
		    const quantityInput = row.querySelector("input[name='quantita']");
		    const quantityValue = quantityInput.value.trim();
		    const quantity = parseFloat(quantityValue) || 0;

		    const dispInput = row.querySelector("input[name='disp']");
		    const giacenza = parseFloat(dispInput.value) || 0;

		    const semaforoCell = row.querySelector(".semaforo-container");

		    if (!quantityValue) {
		        semaforoCell.innerHTML = "<i class='fa fa-solid fa-circle ml-2' style='color:black;'></i>";
		        return; 
		    }

		    var htmlSemaforoVerdeA = 
		        "<span class='fa-stack' style='font-size: 1em;'>" +
		            "<i class='fa-solid fa-circle fa-stack-1x' style='color: green;'></i>" +
		            "<span class='fa-stack-1x' style='color: white; font-weight: bold; font-size: 0.6em;'>A</span>" +
		        "</span>";

		    if (giacenza === 0) {
		        semaforoCell.innerHTML = "<i class='fa fa-solid fa-circle ml-2' style='color:orange;'></i>";
		    } else if (quantity > giacenza) {
		        semaforoCell.innerHTML = "<i class='fa fa-solid fa-circle ml-2' style='color:red;'></i>";
		        if (giacenza + giacenzaAellebi >= quantity) {
		            semaforoCell.innerHTML = htmlSemaforoVerdeA;
		        }
		    } else {
		        semaforoCell.innerHTML = "<i class='fa fa-solid fa-circle ml-2' style='color:green;'></i>";
		    }
		}

		function addToCart(btn) {
		    var tr = btn.closest('tr');
		    var art = tr.querySelector('[name=IdArticoloPth]').value;
		    var qtaInput = tr.querySelector('[name=quantita]');
		    var qta = qtaInput.value;

		    if ($('#qtaCnf').val() != undefined && $('#qtaCnf').val() > 0) {
		        qta = $('#qtaCnf').val();
		    }

		    var idUtente = "<%=userPortalSession.getIdUtente()%>";
		    var idAzienda = "<%=userPortalSession.getIdAzienda()%>";
		    var idCliente = "<%=userPortalSession.getIdCliente()%>";

		    if (isNaN(qta) || qta == undefined || qta <= 0) {
		        var txt = "Inserire una quantita' positiva";
		        openModal('txtWarning', $('#modalWarningClick', parent.parent.document)[0], txt);
		        return;
		    }

		    parent.mostraSpinner();

		    var json = "{articolo : '" + art + "', quantita: '" + qta + "',idUtente: '" + idUtente + "',idAzienda:'" + idAzienda + "',idCliente:'" + idCliente + "', contoDeposito : true}";
		    
		    $.ajax({
		        url: $('#urlWS').val() + '?id=YADDC&tokenUID=' + $('#token').val(),
		        method: 'POST',
		        dataType: 'json',
		        data: json,
		        contentType: 'application/json; charset=utf-8',
		        success: function(response) {
		            btn.classList.add('clicked');
		            var items = parseFloat(response.nrItemsCart);
		            setTimeout(function() {
		                parent.rimuoviSpinner();
		                btn.classList.remove('clicked');
		                qtaInput.value = null;
		            }, 2000);
		        },
		        error: function(xhr, status, error) {
		            xhr.responseJSON.errors.forEach(function(obj) {
		                if (obj[0].includes('token expired')) {
		                    parent.parent.document.getElementById('tokenExpiredClick').click();
		                }
		            });
		            parent.rimuoviSpinner();
		        }
		    });
		}
		
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