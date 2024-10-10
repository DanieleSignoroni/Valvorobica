<%@page import="com.thera.thermfw.web.SessionEnvironment"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.valvorobica.thip.base.generale.ws.YCatalogoPortale"%>
<%@page import="it.valvorobica.thip.base.portal.YUserPortalSession"%>
<%@page import="com.thera.thermfw.base.IniFile"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.thera.thermfw.persist.ConnectionManager"%>
<%@page import="java.util.Map"%>
<%@page import="com.thera.thermfw.persist.ConnectionDescriptor"%>
<%@page import="com.thera.thermfw.security.Security"%>
<%@page import="com.thera.thermfw.base.Trace"%>
<%
YUserPortalSession userPortalSession = (YUserPortalSession) request.getSession().getAttribute("YUserPortal");
String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");
Map values = null;
boolean isopen = false;
Object[] info = SessionEnvironment.getDBInfoFromIniFile();
String dbName = (String) info[0];
Integer items = 0;
if (userPortalSession.getJsonCatalogo() == null) {
	try {
		if (!Security.isCurrentDatabaseSetted()) {
			Security.setCurrentDatabase(dbName, null);
		}
		Security.openDefaultConnection();
		isopen = true;
		ConnectionDescriptor cd = ConnectionManager.getCurrentConnectionDescriptor();
		YCatalogoPortale cat = new YCatalogoPortale();
		cat.setUseAuthentication(false);
		cat.setUseAuthorization(false);
		cat.setUseLicence(false);
		cat.setConnectionDescriptor(cd);
		cat.setUserPortalSession(userPortalSession);
		values = cat.send();
		ArrayList errors = (ArrayList) values.get("errors");
		if (errors.isEmpty()) {
			userPortalSession.setJsonCatalogo((String) values.get("catalogo"));
		}
	} catch (Throwable t) {
		t.printStackTrace(Trace.excStream);
	} finally {
		if (isopen) {
			Security.closeDefaultConnection();
		}
	}
}else{
	items = null;
}
%>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<title>Scheda Catalogo</title>

<!-- CSS Files -->
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/bootstrap.min.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/frames.css", request)%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/cart.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/jquery-ui.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/modal_home.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/base/portal/css/catalogo.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/portal/js/jquery.js", request)%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.min.js"></script>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/portal/js/main_1.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("it/valvorobica/thip/assets/DataTables/datatables.css", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/assets/DataTables/datatables.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/portal/js/modal_utils.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/assets/DataTables/Select/js/dataTables.select.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/assets/DataTables/Buttons/js/dataTables.buttons.js", request)%>
</head>

<body class="noscrollbar"
	onload="rimuoviSpinner();parent.collapseSidebar();">
	<input type="hidden" name="urlWS" id="urlWS" value="">
	<input type="hidden" name="webAppPath" id="webAppPath"
		value="<%=webAppPath%>">
	<input type="hidden" name="Azienda" id="Azienda"
		value="<%=userPortalSession.getIdAzienda()%>">
	<input type="hidden" name="token" id="token"
		value="<%=userPortalSession.getTokecUID()%>">
	<nav class="navbar navbar-expand-sm sticky-top" style="border-radius:5px;border-color:#A6192E;background-color:#A6192E;">
		<div class="container-fluid">
			<div id="title-container">
				<h5 class="card-title" id="card-title">Catalogo</h5>
			</div>
		</div>
	</nav>
	<div class="row mt-5" id="root-container">
		<!-- Content will be added here -->
	</div>
	<div class="container-fluid mt-3" id="dati"></div>
	<div style="display: none" class="text-center">
				<a href="#modalAddToCart" id="modalAddToCartClick"
					class="trigger-btn" data-toggle="modal"></a>
	</div>
	
	<div style="display: none" class="text-center">
			<a href="#modalContactUds" id="modalContactUdsClick" class="trigger-btn"
				data-toggle="modal"></a>
		</div>
		<div id="modalContactUds" class="modal fade">
			<div class="modal-dialog modal-confirm">
				<form id="contactUsForm">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Contattaci!</h4>
							<h6 class="contact-product" id="contactProduct"></h6>
							<p class="contact-product-description" id="contactProductDescription"></p>
						</div>
						<div class="modal-body">
							<input type="hidden" id="ContactUsProductId"></input>
							<input id="Nome" class="form-control mt-2" placeholder="Nome" required></input>
							<input id="Cognome" class="form-control mt-2" placeholder="Cognome" required></input>
							<input id="Email" class="form-control mt-2" placeholder="Email" required></input>
							<textarea id="Note" class="form-control mt-2" placeholder="Il tuo messaggio"></textarea>
							<div id="responseMessage" class="mt-3"></div>
						</div>
						<div class="modal-footer" style="flex-wrap:unset;">
							<button type="submit" class="btn btn-block" id="contactUdsBtn">Invia richiesta</button>
								<button type="button" class="btn btn-block" onclick="$('#modalContactUds').modal('toggle');"
								data-dismiss="modal">Chiudi</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		
	<script>
	var json =<%=userPortalSession.getJsonCatalogo() %>;
	var currentLevel = json;
	var parentStack = [];

	const contactUsForm = document.getElementById('contactUsForm');

	function renderCard(item, index, container) {
	    var card = document.createElement('div');
	    card.className = 'col-xl-2 col-lg-3 col-md-4 col-sm-6 mb-4';

	    var cardContent = 
	      '<div class="card" id="' + item.text + '">' +
	        '<img onclick="toggleChildren(this)" class="img-fluid card-img" src="' + item.img + '">' +
	        '<div class="row card-img-overlaysoftre">' +
	          '<h5>' + item.text + '</h5>' +
	       	  '<span class="card-info" title="Informazioni">Informazioni aggiuntive</span>' +
	        '</div>' +
	      '</div>';

	    card.innerHTML = cardContent;

	    var infoIcon = card.querySelector('.card-info');
	    infoIcon.addEventListener('click', function() {
	        openDetailDescription(item); // Pass the item object directly
	    });

	    return card;
	}

	function openDetailDescription(item) {
		var description = item.description;
	    // Remove any existing modals
	    var existingModal = document.getElementById('modalContactUds');
	    if (existingModal) {
	        existingModal.remove();
	    }

	    // Create the modal container
	    var modalContainer = document.createElement('div');
	    modalContainer.id = 'modalContactUds';
	    modalContainer.className = 'modal fade';

	    // Modal inner content using string concatenation
	    modalContainer.innerHTML = 
	        '<div class="modal-dialog modal-confirm modal-description">' +
	            '<div class="modal-content">' +
	                '<div class="modal-header">' +
	                    '<h4 class="modal-title">'+(item.text)+'</h4>' +
	                '</div>' +
	                '<div class="modal-body modal-description-body">' +
	               		 '<p class="contact-product-description">' + (description || 'No description available.') + '</p>' +
	                '</div>' +
	                '<div class="modal-footer" style="flex-wrap:unset;">' +
	                    '<button type="button" class="btn btn-block" data-dismiss="modal">Chiudi</button>' +
	                '</div>' +
	            '</div>' +
	        '</div>';

	    // Append the modal to the body
	    document.body.appendChild(modalContainer);

	    // Show the modal using Bootstrap's modal method
	    $('#modalContactUds').modal('show');
	}

	function toggleChildren(event) {
		var img = event;
		var card = img.closest('.card');
		document.getElementById('root-container').innerHTML = '';
		parentStack.push(card.getAttribute('id'));
		var children = getChildren(currentLevel, card.getAttribute('id'));
		if (children) {
			var rootContainer = document.getElementById('root-container');
			children.forEach(function(item) {
				var card = renderCard(item, rootContainer);
				rootContainer.appendChild(card);
				var cardImage = card.querySelector('.card-img-top');
			});
		}
		currentLevel = children;
		renderBreadcrumb(false);
	}

	function renderData(data, container) {
		data.forEach(function(item) {
			var card = renderCard(item, container);
			container.appendChild(card);

			var cardImage = card.querySelector('.card-img-top');
		});
	}

	function getTitlePath(itemTitle) {
		var parentPath = '';
		if (parentStack.length > 0) {
			parentPath = parentStack.join('/');
			if (parentStack.length > 1) {
				if (parentPath !== '') {
					// 	                    parentPath += '/';
				}
			}
		}
		return parentPath;
	}

	function renderBreadcrumb(start) {
		if (!start) {
			var breadcrumbContainer = document.getElementById('title-container');
			var path = getTitlePath();
			var parts = path.split('/');
			var breadcrumbHtml = '';

			for (var i = 0; i < parts.length; i++) {
				var linkText = parts[i];
				breadcrumbHtml += "<span id='title' style='display:none;'>" + linkText + "</span>";
				breadcrumbHtml += "<a id='navigator' href='#' style='color:white' onclick='navigateToLevel(" + i + ",\"" + linkText + "\")'>" + linkText + "</a>";
				breadcrumbHtml += ' / ';
			}
			breadcrumbContainer.innerHTML = breadcrumbHtml;
		} else {
			var breadcrumbContainer = document.getElementById('title-container');
			var breadcrumbHtml = '';
			breadcrumbHtml += '<a id="navigator" href="#" style="color:white" onclick="navigateToLevel(0)">Catalogo</a>';
			breadcrumbHtml += ' / ';
			breadcrumbContainer.innerHTML = breadcrumbHtml;
		}
	}

	function openCart() {
		mostraSpinner();
		var frameContent = parent.document.getElementById('frameContent');
		var url = "/<%= YUserPortalSession.WEB_APP_PATH %>/it/valvorobica/thip/base/portal/YSchedaCarrello.jsp?";
		url += "&webAppPath=" + parent.document.getElementById('webAppPath').value;
		frameContent.src = url;
	}

	function getChildren(level, itemTitle) {
		var foundItem = find(level, itemTitle);
		if (itemTitle == 'Catalogo') {
			return foundItem; //ritorno il json padre e non i figli
		}
		if (!foundItem.children) {
			parent.mostraSpinner();
			$.ajax({
				url: encodeURI($('#urlWS').val() + "?id=YCATD&tokenUID=" + $('#token').val() + "&company=" + $('#Azienda').val() + "&idUserPortal=<%=userPortalSession.getIdUtente()%>&idCliente=<%=userPortalSession.getIdCliente()%>&idVista=" + parentStack.join('/') + "&where=" + foundItem.where),
				method: "GET",
				data: {},
				success: function(data) {
					let table = "<table id='table' class='table table-striped table-hover table-borderless'><thead class='thead-dark'></thead><tbody id='tbody'></tbody></table>";
					$('#dati').append(table);
					let record = data.records;
					let headers = data.headers;
					tableHead = $("table thead");
					const jsonArray = JSON.parse(headers);
					let header = "<tr>";
					jsonArray.forEach(function(id) {
						header += "<th>" + id.id + "</th>";
					});
					header += "</tr>";
					tableHead.append(header);
					const jsonRecords = JSON.parse(record);
					jsonRecords.forEach(function(value) {
						let markup = "<tr>";
						markup += "<input type='hidden' name='IdArticoloPth' value='" + value.IdArticoloPth + "'></input>";
						markup += "<td name='articolo'>" + value.IdArticolo + "</td>";
						markup += "<td>" + value.DescrEstesa + "</td>";
						let giacenza = parseFloat(value.Giacenza); //rimuovere ToFixed(2)
						let prezzo = parseFloat(value.Prezzo);
						if (giacenza > 0) {
							markup += "<td class='text-right'><input type='hidden' name='disp' value=" + giacenza + ">" + giacenza + "<i class='fa fa-solid fa-circle ml-2' style='color:#45f445;'></i></td>";
						} else {
							markup += "<td class='text-right' name='disp'><input type='hidden' name='disp' value=" + giacenza + ">" + giacenza + "<i class='fa fa-solid fa-circle ml-2' style='color:red;'></i></td>";
						}
						markup += "<td class='text-right' name='prezzo'>" + parseFloat(value.Prezzo).toFixed(2) + "<i class='fa fa-eur ml-1' aria-hidden='true'></i></td>";
						if (giacenza > 0) {
							markup += "<td><input class='form-control rcrPrz' type='number' name='quantita' onchange='ricercaPrezzo(this)'></input></td>";
						} else {
							markup += "<td><input class='form-control rcrPrz' type='number' name='quantita' disabled></input></td>";
						}
						if (giacenza > 0 && prezzo > 0) {
							markup += "<td style='text-align:center;cursor:pointer;'><i onclick='addToCart(this)' title='Aggiungi al carrello' class='fa fa-regular fa-plus fa-2x'></i></td>";
						} else if (giacenza == 0) {
							markup += "<td style='text-align:center;cursor:pointer;'><span onclick='contactUdsModal(this)'>Contattaci</span></td>";
						} else if (prezzo == 0) {
							markup += "<td style='text-align:center;cursor:pointer;'><span onclick='contactUdsModal(this)'>Contattaci</span></td>";
						}
						tableBody = $("table tbody");
						tableBody.append(markup);
					});

					setTimeout(table = new DataTable('#table'), {
						"autoWidth": false, // might need this
						aoColumns: [
							{ "sWidth": "20%" },
							{ "sWidth": "50%" },
							{ "sWidth": "10%" },
							{ "sWidth": "5%" },
							{ "sWidth": "10%" },
							{ "sWidth": "5%" },
						],
					}, 1000);

					parent.rimuoviSpinner();

					$("#title-container").css("display", "revert");
				},
				error: function(xhr, status, error) {
					xhr.responseJSON.errors.forEach(function(obj) {
						if (obj[0].includes('token expired')) {
							parent.document.getElementById('tokenExpiredClick').click();
						}
					});
					$("#title-container").css("display", "revert");
					parent.rimuoviSpinner();
				}
			});
		}
		return foundItem ? foundItem.children : null; //qui invece voglio ritornare sempre i figli, perche'
		//saro' sempre a livello -1
	}

	function find(source, id) {
		if (id == 'Catalogo') {
			return json;
		}
		for (key in source) {
			var item = source[key];
			if (item.text == id)
				return item;
			if (item.children) {
				var subresult = find(item.children, id);
				if (subresult)
					return subresult;
			}
		}
		return null;
	}

	function navigateToLevel(levelIndex, linkText) {
		var table = $('#table').DataTable();
		table.destroy();
		document.getElementById('dati').innerHTML = '';
		var titlesArray = [];
		var spanElements = document.querySelectorAll('#title-container span');
		for (var i = 0; i < spanElements.length; i++) {
			var spanElement = spanElements[i];
			if (spanElement.textContent != linkText) {
				titlesArray.push({ key: 'key', title: spanElement.textContent });
			}
			if (titlesArray.length === levelIndex) {
				found = true;
				break;
			}
		}
		var level = json;
		for (var i = 0; i < titlesArray.length; i++) {
			var item = titlesArray[i];
			level = getChildren(json, item.title);
		}
		currentLevel = getChildren(level, linkText);
		var index = parentStack.indexOf(linkText);
		parentStack = parentStack.slice(0, index + 1);
		document.getElementById('root-container').innerHTML = '';
		console.log(currentLevel);
		renderData(currentLevel, document.getElementById('root-container'));
		renderBreadcrumb();
	}

	parentStack.push('Catalogo');
	renderBreadcrumb(true);
	var rootContainer = document.getElementById('root-container');
	renderData(json, rootContainer);
	compilaURLWS();

	function ricercaPrezzo(input) {
		input.value = parseFloat(input.value).toFixed(2);
		parent.mostraSpinner();
		var qta = parseFloat(input.value);
		var disp = input.parentNode.parentNode.querySelector('[name="disp"]').value;
		var idArticolo = input.parentNode.parentNode.querySelector('[name="IdArticoloPth"]').value;
		var idCliente = '<%=userPortalSession.getIdCliente()%>';
		var url = $('#urlWS').val();
		var token = $('#token').val();
		var idAzienda = '<%=userPortalSession.getIdAzienda()%>';
		if (qta > disp) {
			var txt = "Non e' possibile ordinare piu di quanto disponibile in giacenza";
			openModal('txtWarning', $('#modalWarningClick',
				parent.parent.document)[0], txt);
			input.value = '';
			parent.rimuoviSpinner();
			return;
		}
		if (qta <= 0) {
			var txt = "Inserire una quantita' positiva";
			openModal('txtWarning', $('#modalWarningClick',
				parent.parent.document)[0], txt);
			input.value = '';
			parent.rimuoviSpinner();
			return;
		}
		if (isNaN(qta) || qta == undefined) {
			//input.parentNode.nextElementSibling.innerHTML = '';
			parent.rimuoviSpinner();
		} else {
			getPrezzo(url, token, idCliente, idArticolo, qta, idAzienda)
				.done(
					function(response) {
						var responseBody = response;
						input.parentNode.parentNode.querySelector('[name="prezzo"]').innerHTML = parseFloat(
							response['prezzo']).toFixed(2) + "<i class='fa fa-eur ml-1' aria-hidden='true'></i>";
						parent.rimuoviSpinner();
					}).fail(
						function(jqXHR, textStatus, errorThrown) {
							xhr.responseJSON.errors.forEach(function(
								obj) {
								if (obj[0].includes('token expired')) {
									parent.document.getElementById(
										'tokenExpiredClick')
										.click();
								}
							});
							parent.rimuoviSpinner();
						});
		}
	}

	function getPrezzo(url, token, cliente, articolo, qta, azi) {
		return $.ajax({
			url: encodeURI(url + '?id=RPEC&tokenUID=' + token + '&company='
				+ azi + '&tipoUMVendita=V&codCliente=' + cliente
				+ '&codArticolo=' + articolo + '&qtaRichiesta=' + qta), // Replace with the actual URL
			method: 'GET',
			dataType: 'json'
		});
	}


	function addToCart(btn) {
		parent.mostraSpinner();
		var tr = btn.parentNode.parentNode;
		var art = tr.querySelector('[name=IdArticoloPth]').value;
		if (tr.querySelector('[name=disp]') == null) {
			var txt = "L'articolo non e' disponibile";
			openModal('txtWarning', $('#modalWarningClick', parent.parent.document)[0], txt);
			parent.rimuoviSpinner();
			return;
		}
		var qta = tr.querySelector('[name=quantita]').value;
		if ($('#qtaCnf').val() != undefined && $('#qtaCnf').val() > 0) {
			qta = $('#qtaCnf').val();
		}
		if (parseFloat(qta) > parseFloat(tr.querySelector('[name=disp]').innerHTML)) {
			var txt = "Non e' possibile ordinare piu' di quanto disponibile \n Sistemare le quantita'";
			openModal('txtWarning', $('#modalWarningClick', parent.parent.document)[0], txt);
			parent.rimuoviSpinner();
			return;
		}
		var qtaInput = tr.querySelector('[name=quantita]');
		var przInput = tr.querySelector('[name=prezzo]');
		var idUtente = "<%=userPortalSession.getIdUtente()%>";
		var idAzienda = "<%=userPortalSession.getIdAzienda()%>";
		var idCliente = "<%=userPortalSession.getIdCliente()%>";
		var disp = tr.querySelector('[name=disp]');
		if (isNaN(qta) || qta == undefined || qta <= 0) {
			var txt = "Inserire una quantita' positiva";
			openModal('txtWarning', $('#modalWarningClick', parent.parent.document)[0], txt);
			parent.rimuoviSpinner();
			return;
		}
		var json = "{articolo : '" + art + "', quantita: '" + qta + "',idUtente: '" + idUtente + "',idAzienda:'" + idAzienda + "',idCliente:'" + idCliente + "'}";
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
					var cart = $('#cart', parent.document);
					cart.addClass('shake').attr('data-totalitems', items);
					setTimeout(function() {
						cart.removeClass('shake');
					}, 1000)
				}, 2000);
				//disp.innerHTML = parseFloat((parseFloat(disp.innerHTML)-qtaInput.value)).toFixed(2);
				//refresh 
				var href = document.getElementById('title-container').querySelectorAll('#navigator')[document.getElementById('title-container').querySelectorAll('#navigator').length - 1];
				href.click();
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

	function contactUdsModal(event) {
		const responseMessage = document.getElementById('responseMessage');
		responseMessage.innerHTML = '';
		contactUsForm.reset();
		var tr = event.parentNode.parentNode;
		var td0 = tr.querySelector('[name=articolo]');

		$('#modalContactUdsClick')[0].click();
		$('#contactProduct').html(td0.innerHTML);
		$('#contactProductDescription').html(td0.nextElementSibling.innerHTML);

		$('#ContactUsProductId').val(tr.querySelector('[name=IdArticoloPth]').value);
	}

	contactUsForm.addEventListener('submit', function(event) {
		event.preventDefault();

		if (contactUsForm.checkValidity() === false) {
			contactUsForm.classList.add('was-validated');
		} else {
			sendMailSalesManager();
		}
	});

	function sendMailSalesManager() {
		var nome = document.getElementById('Nome').value;
		var cognome = document.getElementById('Cognome').value;
		var email = document.getElementById('Email').value;
		var note = document.getElementById('Note').value;
		var productId = document.getElementById('ContactUsProductId').value;

		const responseMessage = document.getElementById('responseMessage');
		responseMessage.innerHTML = '';

		var payload = {
			nome: nome,
			cognome: cognome,
			email: email,
			note: note,
			productId: productId
		};

		$.ajax({
			url: $('#urlWS').val() + '?id=YSMSM&tokenUID=' + $('#token').val(),
			method: 'POST',
			dataType: 'json',
			data: JSON.stringify(payload),
			contentType: 'application/json; charset=utf-8',

			beforeSend: function() {
				parent.mostraSpinner();
			},

			success: function(response) {
				responseMessage.innerHTML = '<div class="alert alert-success">Richiesta inviata con successo!</div>';
				contactUsForm.reset();
			},

			error: function(xhr, status, error) {
				xhr.responseJSON.errors.forEach(function(obj) {
					if (obj[0].includes('token expired')) {
						parent.parent.document.getElementById('tokenExpiredClick').click();
					}
				});
				responseMessage.innerHTML = '<div class="alert alert-danger">Errore durante l\'invio della richiesta. Riprova.</div>';
			},

			complete: function() {
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