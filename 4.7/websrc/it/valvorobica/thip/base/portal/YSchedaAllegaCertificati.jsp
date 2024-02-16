<%@page import="java.io.IOException"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page
	import="it.valvorobica.thip.base.generale.ws.YRetrieveLottiFornitore"%>
<%@page import="com.thera.thermfw.web.SessionEnvironment"%>
<%@page import="com.thera.thermfw.base.Trace"%>
<%@page import="com.thera.thermfw.persist.ConnectionManager"%>
<%@page import="com.thera.thermfw.persist.ConnectionDescriptor"%>
<%@page import="com.thera.thermfw.security.Security"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.thera.thermfw.base.IniFile"%>
<%@page import="it.valvorobica.thip.base.portal.YUserPortalSession"%>
<%
YUserPortalSession userPortalSession = (YUserPortalSession) request.getSession().getAttribute("YUserPortal");
String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");
String token = userPortalSession.getTokecUID();
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
	YRetrieveLottiFornitore ws = new YRetrieveLottiFornitore();
	ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
	ws.setUseAuthentication(false);
	ws.setUseAuthorization(false);
	ws.setUseLicence(false);
	ws.setUserPortal(userPortalSession);
	ws.setConnectionDescriptor(cd);
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
<title>Allega Certificati</title>
<!-- CSS Files -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/frames.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link
	href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/css/modal_home.css"
	rel="stylesheet">
<script
	src="/<%=webAppPath%>/it/valvorobica/thip/base/portal/js/jquery.js"></script>
<script
	src="/<%=webAppPath%>/it/valvorobica/thip/base/portal/js/modal_utils.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="js/main_1.js"></script>
</head>
<body class="noscrollbar" onload="parent.rimuoviSpinner()">
	<%
	JSONArray objs = (JSONArray) values.get("dati");
	for (int i = 0; i < objs.length(); i++) {
		JSONObject jsonObject = (JSONObject) objs.getJSONObject(i);
	%>
	<div class="card mt-2">
		<div class="card-body">
			<form id="form<%=i%>" method="post"
				onsubmit="return confirmSubmit('<%=i%>')">
				<input type="hidden" name="urlWS" id="urlWS" value=""> <input
					type="hidden" name="webAppPath" id="webAppPath"
					value="<%=webAppPath%>"> <input type="hidden"
					name="IdAzienda" id="IdAzienda"
					value="<%=userPortalSession.getIdAzienda()%>"> <input
					type="hidden" name="token" id="token"
					value="<%=userPortalSession.getTokecUID()%>"> <input
					type="hidden" name="IdLotto"
					value="<%=jsonObject.get("id_lotto")%>"> <input
					type="hidden" name="IdFornitore"
					value="<%=userPortalSession.getIdFornitore()%>">
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-4">
								<label for="CodiceArticolo">Articolo</label><input type="text"
									class="form-control" readonly name="IdArticolo"
									value="<%=jsonObject.get("articolo")%>">
							</div>
							<div class="col-md-4">
								<label for="CodiceArticolo">Articolo fornitore</label><input
									type="text" class="form-control" readonly
									value="<%=jsonObject.get("articolo_fornitore")%>">
							</div>
							<div class="col-md-4">
								<label for="CodiceArticolo">Lotto acquisto</label><input
									type="text" class="form-control" readonly
									value="<%=jsonObject.get("lotto_acquisto")%>">
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<label for="CodiceArticolo">Data bolla</label><input type="text"
									class="form-control" readonly
									value="<%=jsonObject.get("data_bolla")%>">
							</div>
							<div class="col-md-4">
								<label for="CodiceArticolo">Numero bolla</label><input
									type="text" class="form-control" readonly
									value="<%=jsonObject.get("numero_bolla")%>">
							</div>
							<div class="col-md-4">
								<label for="CodiceArticolo">Riga bolla</label><input type="text"
									class="form-control" readonly
									value="<%=jsonObject.get("riga_bolla")%>">
							</div>
							<div class="col-md-4">
								<label for="CodiceArticolo">Quantita</label><input type="text"
									class="form-control" readonly
									value="<%=jsonObject.get("quantita")%>">
							</div>
							<div class="col-md-4">
								<label for="fileInput">Tipo certificato</label> <select
									class="form-control" id="tipoCertificato"
									name="tipoCertificato">
									<option>(Click per scegliere)</option>
									<option>3.1</option>
									<option>2.1</option>
									<option>2.2</option>
								</select>
							</div>
						</div>
						<div class="row mt-4">
							<div class="col-md-4">
								<button type="submit"
									style="background-color: #A6192E; border-color: #A6192E;"
									class="btn btn-primary">Salva</button>
							</div>
							<div class="col-md-2">
								<button type="button" disabled
									style="background-color: #A6192E; border-color: #A6192E;"
									name="showDocDgtOgg" class="btn btn-primary">Visualizza file</button>
							</div>
							<div class="col-md-2">
								<button type="button" disabled
									style="background-color: #A6192E; border-color: #A6192E;"
									name="deleteDocDgtOgg" class="btn btn-primary">Elimina allegato</button>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-md-12">
								<label for="CodiceArticolo">Descrizione articolo</label>
								<textarea class="form-control" readonly><%=jsonObject.get("descrizione_estesa")%></textarea>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<label for="fileInput">Scegli file</label> <input type="file"
									style="height: 150px;background-color:#e4e491;" name="file" id="fileInput"
									class="form-control">
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%
	}
	%>

	<div style="display: none" class="text-center">
		<a href="#modalConfirm" id="modalConfirmClick" class="trigger-btn"
			data-toggle="modal"></a>
	</div>
	<div id="modalConfirm" class="modal fade">
		<div class="modal-dialog modal-confirm">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">Attenzione!</h4>
				</div>
				<div class="modal-body">
					<p id="confirmText" class="text-center"></p>
				</div>
				<div class="modal-footer" style="flex-wrap: unset;">
					<button type="button" class="btn btn-block" id="confirmButton"
						data-dismiss="modal">SI</button>
					<button type="button" class="btn btn-block"
						onclick="$('#modalConfirm').modal('toggle');" data-dismiss="modal">NO</button>
				</div>
			</div>
		</div>
	</div>

</body>
<script>
	function confirmSubmit(formIndex) {
		var txt = "Sei sicuro di voler salvare? ";
		$('#confirmText')[0].innerHTML = txt;
		$('#modalConfirmClick')[0].click();
		$('#confirmButton').attr('onClick', 'myOnSubmit(' + formIndex + ')');
		return false;
	}

	function myOnSubmit(formIndex) {
		compilaURLWS();
		var form = document.forms[formIndex];
		var fileInput = form.querySelector('input[type="file"]');
		if (fileInput.files.length === 0) {
			openModal('txtWarning',
					$('#modalWarningClick', parent.document)[0],
					'File non selezionato');
			return false;
		}
		var selectElement = form
				.querySelector('select[name="tipoCertificato"]');
		var selectedValue = selectElement.value;
		if (selectedValue === '(Click per scegliere)') {
			openModal('txtWarning',
					$('#modalWarningClick', parent.document)[0],
					'Selezionare un tipo certificato');
			return false;
		}

		var formData = new FormData(form);
		$.ajax({
			url : $('#urlWS').val() + '/portale/upload',
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			success : function(response) {
				console.log('File uploaded successfully:', response);
			},
			error : function(xhr, status, error) {
				if(xhr.status != 200){
					openModal('txtWarning',
							$('#modalWarningClick', parent.document)[0],
							xhr.responseText);
				}else if(xhr.status == 200){
					var btn = form.querySelector('[name=showDocDgtOgg]');
					var token = document.getElementById('token').value;
					btn.setAttribute('onclick','parent.download(\'' + token + '\', \'' + xhr.responseText + '\')');
					btn.removeAttribute('disabled');
				}
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
		ris += "/api";
		document.getElementById('urlWS').value = ris;
	}
</script>