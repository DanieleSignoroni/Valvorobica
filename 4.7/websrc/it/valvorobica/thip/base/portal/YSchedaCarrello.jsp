<%@page import="org.json.JSONObject"%>
<%@page import="com.thera.thermfw.web.WebJSTypeList"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="it.valvorobica.thip.base.generale.ws.YCarrello"%>
<%@page import="it.valvorobica.thip.base.portal.YUserPortalSession"%>
<%@page import="it.valvorobica.thip.base.generale.ws.dati.YClientePortale"%>
<%@page import="com.thera.thermfw.base.Trace"%>
<%@page import="com.thera.thermfw.persist.ConnectionManager"%>
<%@page import="java.util.Map"%>
<%@page import="com.thera.thermfw.web.SessionEnvironment"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.thera.thermfw.security.Security"%>
<%@page import="com.thera.thermfw.persist.ConnectionDescriptor"%>
<%@page import="com.thera.thermfw.base.IniFile"%>
<%@page import="it.thera.thip.base.profilo.UtenteAzienda"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.thera.thermfw.persist.Factory"%>
<%@page import="com.thera.thermfw.web.ServletEnvironment"%>
<%
YUserPortalSession userPortalSession = (YUserPortalSession) request.getSession().getAttribute("YUserPortal");
String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");
String token = userPortalSession.getTokecUID();
ArrayList<YCarrello.ItemCarrello> items = new ArrayList<YCarrello.ItemCarrello>();
Map values = new LinkedHashMap();
boolean isopen = false;
Object[] info = SessionEnvironment.getDBInfoFromIniFile();
String dbName = (String) info[0];
String newsHtml = null;
BigDecimal total = BigDecimal.ZERO;

JSONObject deliveryMethods = null;
JSONObject shipmentMethods = null;
JSONObject shippers = null;
JSONObject shipmentDefaultMethod = null;
JSONObject defaultShipper = null;
JSONObject deafultDeliveryMethod = null;

String salesConditionsPDFKey = null;
try {
	if (!Security.isCurrentDatabaseSetted()) {
		Security.setCurrentDatabase(dbName, null);
	}
	Security.openDefaultConnection();
	isopen = true;
	ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
	YCarrello ws = new YCarrello();
	ws.setUseAuthentication(false);
	ws.setUseAuthorization(false);
	ws.setUseLicence(false);
	ws.setUserPortalSession(userPortalSession);
	ws.setConnectionDescriptor(cd);
	values = ws.send();
	items = (ArrayList<YCarrello.ItemCarrello>) values.get("items");
	total = (BigDecimal) values.get("total");
	deliveryMethods = (JSONObject) values.get("deliveryMethods");
	salesConditionsPDFKey = (String) values.get("salesConditionsPDFKey");
	deliveryMethods = (JSONObject) values.get("deliveryMethods");
	shipmentMethods = (JSONObject) values.get("shipmentMethods");
	shippers = (JSONObject) values.get("shippers");
	shipmentDefaultMethod = (JSONObject) values.get("shipmentDefaultMethod");
	defaultShipper = (JSONObject) values.get("defaultShipper");
	deafultDeliveryMethod = (JSONObject) values.get("deafultDeliveryMethod");
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
<title>Carrello</title>
<!-- CSS Files -->
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/bootstrap.min.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/frames.css", request)%>
<%=WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/cart.css", request)%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<%=WebJSTypeList.getImportForCSS("it/valvorobica/thip/assets/DataTables/datatables.css", request)%>
<%=WebJSTypeList.getImportForCSS("it/valvorobica/thip/assets/DataTables/Select/css/select.dataTables.css", request)%>
<%=WebJSTypeList.getImportForCSS("it/valvorobica/thip/assets/DataTables/Buttons/css/buttons.dataTables.css", request)%>
<%=WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/carrello.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/portal/js/jquery.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/portal/js/modal_utils.js", request)%>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/portal/js/main_1.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/assets/DataTables/datatables.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/assets/DataTables/Select/js/dataTables.select.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/assets/DataTables/Buttons/js/dataTables.buttons.js", request)%>
</head>
<body class="noscrollbar" onload="rimuoviSpinner()">
			<input type="hidden" name="urlWS" id="urlWS" value=""> <input
			type="hidden" name="webAppPath" id="webAppPath"
			value="<%=webAppPath%>">
			<input
				type="hidden" name="Azienda" id="Azienda"
				value="<%=userPortalSession.getIdAzienda()%>"> <input type="hidden" name="token" id="token" value="<%=userPortalSession.getTokecUID()%>"> 
		<div class="row">
			<div class="col-sm-2 mb-2">
				<h3 class="pointer">Carrello</h3>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-striped table-hover table-bordered"
				id="table">
				<thead class="thead-dark">
					<tr>
						<th></th>
						<th scope="col">Articolo</th>
						<th scope="col">Descr.</th>
						<th scope="col">Unita misura</th>
						<th scope="col">Disponibilita</th>
						<th scope="col">Quantita</th>
						<th scope="col">Prezzo</th>
						<th scope="col">Comandi</th>
					</tr>
				</thead>
				<tbody>
					<%
					Iterator iter = items.iterator();
					while (iter.hasNext()) {
						YCarrello.ItemCarrello item = (YCarrello.ItemCarrello) iter.next();
					%>
					<tr>
						<td></td> <!-- Lasciare vuota, sede della checkbox -->
						<input type="hidden" name="key" value="<%=item.getKey()%>">
						<input type="hidden" name="prezzo" value="<%=item.getPrezzo()%>">
						<td><%=item.getIdArticolo()%><input type="hidden" name="articolo" value="<%=item.getIdArticolo()%>"/></td>
						<td><%=item.getDescrExtArticolo()%><input type="hidden" name="descr" value="<%=item.getDescrExtArticolo()%>"/></td>
						<td style="width: 15%;"><%=item.getUmDefVen()%></td>
						<td style="width: 15%;"><%=item.getDisponibilita() %></td>
						<td style="width: 15%;" data-sort="<%=item.getQuantita()%>">
							<input type="number" class="form-control"
							onChange="ricalcolaTotale(this)"
							readonly name="quantita" value="<%=item.getQuantita()%>"></input>
						</td>
						<td><%=item.getPrezzo() %></td>
						<td></td>
					</tr>
					<%
					}
					%>
				</tbody>
				<tfoot>
          			  <tr>
               			 <th colspan="6" style="text-align:right">Totale Provvisorio:</th>
              			 <th id="total"><%=total.setScale(2, RoundingMode.DOWN).toString() %></th>
            		</tr>
        		</tfoot>
			</table>
		</div>
		<div style="display: none" class="text-center">
			<a href="#modalRemoveItem" id="removeItemClick" class="trigger-btn"
				data-toggle="modal"></a>
		</div>
		<div id="modalRemoveItem" class="modal fade">
			<div class="modal-dialog modal-confirm">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Attenzione!</h4>
					</div>
					<div class="modal-body">
						<p id="removeItemTxt" class="text-center"></p>
					</div>
					<div class="modal-footer" style="flex-wrap:unset;">
						<button type="button" class="btn btn-block" id="removeItemBtn"
							data-dismiss="modal">SI</button>
							<button type="button" class="btn btn-block" onclick="$('#modalRemoveItem').modal('toggle');"
							data-dismiss="modal">NO</button>
					</div>
				</div>
			</div>
		</div>
		<div style="display: none" class="text-center">
			<a href="#modalWarningCheckOut" id="warningCheckOutClick" class="trigger-btn"
				data-toggle="modal"></a>
		</div>
		<div id="modalWarningCheckOut" class="modal fade">
			<div class="modal-dialog modal-confirm">
				<form id="checkoutForm">
					<div class="modal-content">
						<div class="text-end">
							<span class="danger">Condizioni di vendita</span>
							<i class="fa-regular fa-file-lines fa-2x" style="cursor:pointer;" onclick="parent.download('<%=userPortalSession.getTokecUID()%>','<%=salesConditionsPDFKey %>')"></i>
						</div>
						<div class="modal-header">
							<h4 class="modal-title">Attenzione!</h4>
						</div>
						<div class="modal-body">
							<p id="warningTxt" class="text-center"></p>
							<div class="row">
								<div class="col">
									<label class="form-label">Modalità di consegna</label>
									<select class="form-select mb-2" name="deliveryMethod" id="deliveryMethod" required disabled></select>
									<input type="hidden" name="IdModalitaConsegna" id="IdModalitaConsegnaHiddenInputValue">
								</div>
								<div class="col">
									<label class="form-label">Modalità di spedizione</label>
									<select class="form-select mb-2" name="IdModalitaSpedizione" id="shipmentMethod" required></select>
								</div>
							</div>
							<label class="form-label">Vettore</label>
							<select class="form-select mb-2" name="IdVettore1" id="shipper" required></select>
							<input name="vsNr" id="vsNr" class="form-control mt-2" placeholder="Vs. Numero" required></input>
							<textarea name="note" id="note" class="form-control mt-2" placeholder="Note"></textarea>
						</div>
						<div class="modal-footer" style="flex-wrap:unset;">
							<button type="submit" class="btn btn-block" id="warningCheckOut">Conferma ordine</button>
								<button type="button" class="btn btn-block" onclick="annullaCheckoutOrder()"
								data-dismiss="modal">Annulla</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div style="display: none" class="text-center">
			<a href="#modalSuccessCheckOut" id="successCheckOutClick" class="trigger-btn"
				data-toggle="modal"></a>
		</div>
		<div id="modalSuccessCheckOut" class="modal fade">
			<div class="modal-dialog modal-confirm">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">Congratulazioni!</h4>
					</div>
					<div class="modal-body">
						<p id="successTxt" class="text-center"></p>
					</div>
					<div class="modal-footer" style="flex-wrap:unset;">
						<button type="button" class="btn btn-block" onClick="window.location.reload();"
							data-dismiss="modal">OK</button>
					</div>
				</div>
			</div>
		</div>

	<script>
	var deliveryMethods = <%=deliveryMethods%>;
	var shipmentMethods = <%=shipmentMethods%>;
	var shippers = <%=shippers%>;
	var shipmentDefaultMethod = <%=shipmentDefaultMethod%>;
	var defaultShipper = <%=defaultShipper%>;
	var deafultDeliveryMethod = <%=deafultDeliveryMethod%>;
	
	var cart = $('#cart', parent.document);
	
	cart.addClass('shake').attr('data-totalitems', <%=YUserPortalSession.getNumeroItemsCarrelloUtente(userPortalSession.getIdAzienda(), userPortalSession.getIdUtente()) %>);
	setTimeout(function() {
		cart.removeClass('shake');
	}, 1000)
	
	const checkoutForm = document.getElementById('checkoutForm');
	
	checkoutForm.addEventListener('submit', function(event) {
		event.preventDefault();

		if (checkoutForm.checkValidity() === false) {
			checkoutForm.classList.add('was-validated');
		} else {
			checkOutOrder();
		}
	});
	
	$(document).ready(function() {
		table = new DataTable('#table', {
			dom: 'Blfrtip',
			buttons: [
				{
					className: 'btn btn-secondary btn-sm',
					text: 'Procedi',
					action: function() {
						if (table.rows('.selected').data().length == 0) {
							let txt = "Per procedere selezionare almeno un prodotto";
							$('#txtWarning', parent.document)[0].innerHTML = txt;
							$('#modalWarningClick', parent.document)[0].click();
						} else {
							confirmCheckOutOrder();
						}
					}
				},
				{
					className: 'btn btn-secondary btn-sm',
					text: 'Selez. Tutto',
					action: function() {
						if (table.rows('.selected').data().length == 0) {
							table.rows().select();
						} else {
							table.rows().deselect();
						}
					}
				},
				{
					className: 'btn btn-danger btn-sm',
					text: 'Rimuovi',
					action: function(e, dt, node, config) {
						if (table.rows('.selected').data().length == 0) {
							let txt = "Per procedere selezionare almeno un prodotto";
							$('#txtWarning', parent.document)[0].innerHTML = txt;
							$('#modalWarningClick', parent.document)[0].click();
						} else {
							confirmDelete();
						}
					}

				}
			],
			initComplete: function() {
				let btns = $('.dt-button');
				btns.removeClass('dt-button');
			},
			select: {
				style: 'multi',
				selector: 'td:first-child'
			},
			columnDefs: [{
				orderable: false,
				className: 'select-checkbox',
				targets: 0
			},
			{
				targets: -1,
				render: function(data, type, row, meta) {
					return "<a class='btn btn-success btn-sm mr-2' onclick='modifyRow(this)'>Modifica</a>";
				}
			}
			],
		});
		compilaURLWS();
		populateDeliveryMethods();
		populateShipmentMethods();
		populateShippers();
	});
	
	function populateShippers(){
		let select = document.getElementById('shipper');
		let data = JSON.stringify(shippers);
		data = JSON.parse(data);
		
		select.innerHTML = '';
		
		let defaultOption = document.createElement('option');
		defaultOption.value = ''; 
		defaultOption.textContent = 'Seleziona un vettore'; 
		defaultOption.disabled = true; 
		defaultOption.selected = true; 
		select.appendChild(defaultOption);

		data.shippers.forEach(function(method) {
			let option = document.createElement('option');
			option.value = method.Id; 
			option.textContent = method.Description; 
			select.appendChild(option); 
		});
	}

	function populateShipmentMethods(){
		let select = document.getElementById('shipmentMethod');
		let data = JSON.stringify(shipmentMethods);
		data = JSON.parse(data);
		
		select.innerHTML = '';
		
		let defaultOption = document.createElement('option');
		defaultOption.value = ''; 
		defaultOption.textContent = 'Seleziona una modalità di spedizione'; 
		defaultOption.disabled = true; 
		defaultOption.selected = true; 
		select.appendChild(defaultOption);

		data.shipmentMethods.forEach(function(method) {
			let option = document.createElement('option');
			option.value = method.Id; 
			option.textContent = method.Description; 
			select.appendChild(option); 
		});
	}
	
	function populateDeliveryMethods() {
		let select = document.getElementById('deliveryMethod');
		let data = JSON.stringify(deafultDeliveryMethod);
		data = JSON.parse(data);
		
		select.innerHTML = '';
		
		let defaultOption = document.createElement('option');
		defaultOption.value = data.Id; 
		defaultOption.textContent = data.Description; 
		defaultOption.disabled = true; 
		defaultOption.selected = true; 
		select.appendChild(defaultOption);
		
		$('#IdModalitaConsegnaHiddenInputValue').val(data.Id);

		// 		data.deliveryMethods.forEach(function(method) {
		// 			let option = document.createElement('option');
		// 			option.value = method.Id; 
		// 			option.textContent = method.Description; 
		// 			select.appendChild(option); 
		// 		});
	}

	function modifyRow(btn) {
		btn.parentElement.parentNode.querySelector('[name=quantita]').removeAttribute('readonly');
	}

	function ricalcolaTotale(input) {
		let key = [];
		key = input.parentElement.parentNode.querySelector('[name=key]').value.split('\x16');
		if (parseFloat(input.value) > parseFloat(input.parentNode.previousElementSibling.innerHTML)) {
			let txt = "Non e' possibile ordinare piu di quanto disponibile \n Sistemare le quantita'";
			openModal('txtWarning', $('#modalWarningClick', parent.document)[0], txt);
			input.value = '';
			return;
		}
		if (parseFloat(input.value) <= 0) {
			let txt = "Inserire una quantita positiva, o rimuovere l'articolo";
			openModal('txtWarning', $('#modalWarningClick', parent.document)[0], txt);
			return;
		}
		parent.mostraSpinner();
		let idArticolo = input.parentElement.parentNode.querySelector('[name=articolo]').value;
		input.value = parseFloat(input.value).toFixed(4);
		getPrezzo($('#urlWS').val(), idArticolo, input.value).done(function(response) {
			let responseBody = response;
			input.parentNode.nextElementSibling.innerHTML = parseFloat(response['prezzo']).toFixed(4);
			//parent.rimuoviSpinner();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			jqXHR.responseJSON.errors.forEach(function(obj) {
				if (obj[0].includes('token expired')) {
					parent.document.getElementById('tokenExpiredClick').click();
				}
			});
			//parent.rimuoviSpinner();
		});
		let queryUpdate = "UPDATE THIPPERS.YCARRELLO_PORTALE SET QUANTITA = '" + input.value + "' WHERE ID_AZIENDA = '" + key[0] + "' AND R_UTENTE_PORTALE = '" + key[1] + "' AND PROGRESSIVO = '" + key[2] + "' ";
		let url = $('#urlWS').val();
		url += '?id=GSQ&tokenUID=' + $('#token').val();
		url += '&company=<%=userPortalSession.getIdAzienda()%>';
		url += '&query=' + queryUpdate;
		$.ajax({
			url: url,
			method: 'POST',
			dataType: 'json',
			data: '',
			contentType: 'application/json; charset=utf-8',
			success: function(response) {
				window.location.reload();
			},
			error: function(xhr, status, error) {
				xhr.responseJSON.errors.forEach(function(obj) {
					if (obj[0].includes('token expired')) {
						parent.document.getElementById('tokenExpiredClick').click();
					} else {
						openModal('txtWarning', $('#modalWarningClick',
							parent.parent.document)[0], obj[0]);
					}
				});
				parent.rimuoviSpinner();
			}
		});
		let tot = 0;
		let total = $('#total');
		table.rows().every(function(rowIdx, tableLoop, rowLoop) {
			let trNode = table.row(rowIdx).node();
			let prezzo = 0;
			if (trNode.querySelector('[name=prezzo]') != null)
				prezzo = trNode.querySelector('[name=prezzo]').value;
			let qta = trNode.querySelector('[name=quantita]').value;
			let mlt = parseFloat(prezzo) * parseFloat(qta);
			tot = tot + mlt;
		});
		total[0].innerHTML = tot.toFixed(4);
	}

	function toggle() {
		$('#modalRemoveItem').modal('toggle');
	}

	function confirmCheckOutOrder() {
		if (table.rows('.selected').data().length > 0) { //solo se ne ho selezionata almeno 1
			let txt = "Vuoi procedere alla creazione dell'ordine? ";
			$('#warningTxt')[0].innerHTML = txt;
			$('#warningCheckOutClick')[0].click();
			//$('#warningCheckOut').attr('onClick', 'checkOutOrder()');
		}
	}
	
	function annullaCheckoutOrder(){
		$('#modalWarningCheckOut').modal('toggle');
		table.rows().deselect();
	}

	function checkOutOrder() {
		parent.mostraSpinner();
		//Loop tra le righe selezionate per costruire un json che contiene le chiavi dei record da cancellare
		let keys = [];
		let ret = false;
		table.rows('.selected').every(function(rowIdx, tableLoop, rowLoop) {
			let trNode = table.row(rowIdx).node();
			let key = trNode.querySelector('[name=key]').value;
			let qta = trNode.querySelector('[name=quantita]');
			if (parseFloat(qta.value) > parseFloat(qta.parentNode.previousElementSibling.innerHTML)) {
				let txt = "Non e' possibile ordinare piu di quanto disponibile";
				openModal('txtWarning', $('#modalWarningClick', parent.document)[0], txt);
				ret = true;
			}
			keys.push({
				'id': rowIdx,
				'key': key,
				'quantita': qta.value
			});
		});
		if (ret) {
			parent.rimuoviSpinner();
			return;
		}
		let formData = {};
		$('#checkoutForm').serializeArray().forEach(function(field) {
		    formData[field.name] = field.value;
		});
		//Chiamata al WebService
		$.ajax({
			url: $('#urlWS').val() + '?id=YCKOC&tokenUID=' + $('#token').val(),
			method: 'POST',
			dataType: 'json',
			data : "{items : '" + JSON.stringify(keys) + "', formData : '"+JSON.stringify(formData)+"'}",
			contentType: 'application/json; charset=utf-8',
			success: function(response) {
				$('#successTxt')[0].innerHTML = "Grazie per aver effettuato l'ordine, a breve sarà visualizzabile nella voce 'Ordini'";
				$('#successCheckOutClick')[0].click();
				parent.rimuoviSpinner();
			},
			error: function(xhr, status, error) {
				xhr.responseJSON.errors.forEach(function(obj) {
					if (obj[0].includes('token expired')) {
						parent.document.getElementById('tokenExpiredClick').click();
					} else {
						openModal('txtWarning', $('#modalWarningClick',
							parent.parent.document)[0], obj[0]);
					}
				});
				parent.rimuoviSpinner();
			}
		});
	}

	function removeItems() {
		parent.mostraSpinner();
		//Loop tra le righe selezionate per costruire un json che contiene le chiavi dei record da cancellare
		let keys = [];
		if (table.rows('.selected').data().length > 0) {
			table.rows('.selected').every(function(rowIdx, tableLoop, rowLoop) {
				let trNode = table.row(rowIdx).node();
				let key = trNode.querySelector('[name=key]').value;
				keys.push({
					'id': rowIdx,
					'value': key
				});
			});
		}
		//Chiamta al WebService
		$.ajax({
			url: $('#urlWS').val() + '?id=YREMC&tokenUID=' + $('#token').val(),
			method: 'POST',
			dataType: 'json',
			data: "{keys : '" + JSON.stringify(keys) + "'}",
			contentType: 'application/json; charset=utf-8',
			success: function(response) {
				if (response.errors.length > 0) {
					parent.rimuoviSpinner();
				} else {
					window.location.reload();
				}
			},
			error: function(xhr, status, error) {
				xhr.responseJSON.errors.forEach(function(obj) {
					try {
						if (obj[0].includes('token expired')) {
							parent.document.getElementById('tokenExpiredClick').click();
						}
					} catch (e) {
						window.location.reload();
					}
				});
				parent.rimuoviSpinner();
			}
		});
	}

	function getPrezzo(url, articolo, qta) {
		return $.ajax({
			url: encodeURI(url + '?id=RPEC&token=<%=userPortalSession.getTokecUID()%>&company=<%=userPortalSession.getIdAzienda()%>&tipoUMVendita=V&codCliente=<%=userPortalSession.getIdCliente()%>&codArticolo=' + articolo + '&qtaRichiesta=' + qta), // Replace with the actual URL
			method: 'GET',
			dataType: 'json'
		});
	}

	function confirmDelete(btn) {
		if (table.rows('.selected').data().length > 0) { //solo se ne ho selezionata almeno 1
			let txt = "Sei sicuro di rimuovere gli articoli selezionati dal carrello? ";
			$('#removeItemBtn').attr('onClick', 'removeItems()');
			openModal('removeItemTxt', $('#removeItemClick')[0], txt);
		}
	}

	function compilaURLWS() {
		let ris;
		let url = window.location.href;
		let wbAppPth = parent.document.getElementById('webAppPath').value;
		let cut = url.indexOf(wbAppPth);
		ris = url.substring(0, cut);
		ris += wbAppPth;
		ris += "/ws";
		document.getElementById('urlWS').value = ris;
	}
	</script>

</body>
</html>