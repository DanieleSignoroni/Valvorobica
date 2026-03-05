<%@page import="it.valvorobica.thip.base.generale.ws.utils.YUtilsPortal"%>
<%@page import="it.valvorobica.thip.base.portal.YUserPortalSession"%>
<%@page import="com.thera.thermfw.base.IniFile"%>
<%@page import="com.thera.thermfw.persist.KeyHelper"%>
<%@page import="it.thera.thip.base.cliente.Cliente"%>
<%@page import="it.valvorobica.thip.base.generale.ws.utils.YUserToken"%>
<%
String token = response.getHeader("authorization");
YUserPortalSession userPortalSession = (YUserPortalSession) request.getSession().getAttribute("YUserPortal");
String idClienteAttivo = (String) session.getAttribute("YClienteAttivo"); //dssof3 cliente attivo
String idClienteAttivoRagSoc = (String) session.getAttribute("YClienteAttivoRagSoc");
boolean login = Boolean.TRUE.equals(session.getAttribute("YUserPortalLogin"));
session.removeAttribute("YUserPortalLogin"); //72041
Integer items = null;
if(userPortalSession.isCatalogoAttivo){
	items = YUserPortalSession.getNumeroItemsCarrelloUtente(userPortalSession.getIdAzienda(), userPortalSession.getIdUtente());
}
%>
<html>
<head>
<title>Portale</title>
<link
	href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/img/logoSoftre.png"
	rel="icon">
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<link
	href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/css/home.css"
	rel="stylesheet">
<link
	href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/css/modal_home.css"
	rel="stylesheet">
<link
	href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/css/preloader.css"
	rel="stylesheet">
<link
	href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/css/bootstrap.min.css"
	rel="stylesheet">
	<link rel="stylesheet"
	href="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/css/cart.css" />
	<!-- Deprecare per aggiornare con nuova library -->
	<!--  <link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet"> -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"> <!-- DSSOF3 08/09/2025 -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script
	src="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/js/home.js"></script>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("it/valvorobica/thip/base/portal/js/main.js", request)%>
<script 
	src="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/js/modal_utils.js"></script>
<style type="text/css">
.menu-icon-wrapper {
    width: 6rem;
    height: 6rem;
    line-height: 6rem;
    border-radius: 6rem;
    transition: all 0.3s ease-out;
    z-index: 10;
}
</style>
</head>
<%
if (!userPortalSession.getTipoUser().equals(YUserPortalSession.FORNITORE)) {
%>
<body onload="apriSchedaAnagrafica()">
	<%
	} else {
	%>

<body onload="apriSupplierOffer()">
	<%
	}
	%>

	<!-- DSSOF3 Sull'onload del body apro l'anagrafica, 1° schermata -->

	<div id="preloader" class="preloader" style="display: none">
		<div class="spinner"></div>
	</div>

	<%
	String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");
	%>
	<input type="hidden" id="webAppPath" value="<%=webAppPath%>">

	<div class="wrapper d-flex align-items-stretch">

		<div id="sidebar"
			class="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark h-100 sidenav"
			style="width: 280px; min-height: 100vh">
			<a href="#" class="nav-link text-white pl-2" id="10"
				aria-current="page" onclick="submitJFun(this)"><img
				src="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/img/logoImpostazioni.png"
				style="height: 26px;"></a>
			<hr>

			<ul class="nav nav-pills flex-column mb-auto">
				<%
				if (!userPortalSession.getTipoUser().equals(YUserPortalSession.FORNITORE)) {
					if (userPortalSession.getTipoUser().equals(YUserPortalSession.AGENTE)
					|| userPortalSession.getTipoUser().equals(YUserPortalSession.DIPENDENTE)) {
				%>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="0"
					aria-current="page" onclick="submitJFun(this)"> Clienti </a></li>
				<%
				} else if (userPortalSession.getTipoUser().equals(YUserPortalSession.CLIENTE)) {
				%>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="1"
					aria-current="page" onclick="submitJFun(this)"> Anagrafica </a></li>
				<%
				}
				%>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="2"
					onclick="submitJFun(this)"> Manuali/Dich. Conformità </a></li>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="3"
					onclick="submitJFun(this)"> Offerte </a></li>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="4"
					onclick="submitJFun(this)"> Ordini </a></li>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="5"
					onclick="submitJFun(this)"> Ddt </a></li>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="6"
					onclick="submitJFun(this)"> Fatture/Note cr. </a></li>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="7"
					onclick="submitJFun(this)"> Inevaso </a></li>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="8"
					onclick="submitJFun(this)"> Certificati </a></li>
				<%
				if (userPortalSession.getTipoUser().equals(YUserPortalSession.CLIENTE) && userPortalSession.isCatalogoAttivo) {
				%>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="14"
					onclick="submitJFun(this)"> E-commerce </a></li>
				<li class="highlight-menu-items"><a href="#" class="nav-link text-white pl-2" id="15"
					onclick="submitJFun(this)"> Carrello </a></li>
				<%
				}
				if (userPortalSession.getTipoUser().equals(YUserPortalSession.CLIENTE) 
				        && userPortalSession.isContoDepositoAttivo()) {
				%>
				<li>
				    <a href="#contoDepositoMenu" data-toggle="collapse" aria-expanded="false"
				       class="nav-link text-white pl-2 dropdown-toggle">
				        Conto deposito
				    </a>
				    <ul class="collapse list-unstyled pl-3" id="contoDepositoMenu">
				        <li class="highlight-menu-items">
				            <a href="#" class="nav-link text-white pl-2" id="16"
				               onclick="submitJFun(this)">
				                Giacenze
				            </a>
				        </li>
				        <li class="highlight-menu-items">
				            <a href="#" class="nav-link text-white pl-2" id="17"
				               onclick="submitJFun(this)">
				                Conferma Reintegro
				            </a>
				        </li>
				    </ul>
				</li>
				<%
				}
				%>
			</ul>
			<hr>
			<%
			}
			if (userPortalSession.isMultipleEnv()) { //se l'utente ha piu di un azienda, mostro il bottone 'Cambia azienda'
			%>
			<div class="row">
				<div class="col-6">
					<a href="#" class="nav-link text-white pl-2" id="11"
						aria-current="page" onclick="submitJFun(this)">Logout <img
						src="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/img/logoLogout.png"
						style="height: 24px;"></a>
				</div>
				<div class="col-6">
					<a href="#" class="nav-link text-white pl-2" id="12"
						aria-current="page" onclick="submitJFun(this)">Cambia azienda</a>
				</div>
			</div>

			<%
			} else { //altrimenti potra' solo sloggarsi
			%>
			<a href="#" class="nav-link text-white pl-2" id="11"
				aria-current="page" onclick="submitJFun(this)"> Logout <img
				src="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/img/logoLogout.png"
				style="height: 24px;"></a>
			<%
			}
			if (userPortalSession.getTipoUser().equals(YUserPortalSession.FORNITORE)) {
			%>
			<div class="col-6" style="display: none;">
				<a href="#" class="nav-link text-white pl-2" id="13"
					aria-current="page" onclick="submitJFun(this)"></a>
			</div>
			<%
			}
			%>
		</div>



		<!-- Page Content  -->
		<div id="content">
			<div class="row" style="-bs-gutter-x: 0;">
				<!-- Toggler -->
				<div class="col-1">
					<button type="button" id="sidebarCollapse"
						class="btn burgerSoftre p-1 m-1"
						style="height: fit-content; border: 1px solid black; border-radius: 10px;">
						<!-- 				<img src="it/valvorobica/thip/base/portal/img/logoSoftre.png" -->
						<!-- 					style="height: 64px;"> -->
						<img
							src="/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/img/logo<%=userPortalSession.getIdAzienda()%>.png"
							style="height: 64px;">
					</button>
				</div>
				<div class="col-10 mt-1" id="semaforiCarrello" style="display:none;">
					<div class="row">
				        <div class="col-6">
					      <div class="row">
					    		<div class="col-1">
							      <span class="fa-stack" style="font-size: 1em;">
			 				 			<i class="fa-solid fa-circle fa-stack-1x" style="color: green;"></i>
			  					  		<span class="fa-stack-1x" style="color: white; font-weight: bold; font-size: 1em;"></span>
								  </span>	
								 </div>
								 <div class="col-11">
								  <i> Consegna a saldo</i>
					      		</div>
					     	</div>
					    </div>
					    <div class="col-6">
						  <div class="row">
					    		<div class="col-1">
							      <span class="fa-stack" style="font-size: 1em;">
			 				 			<i class="fa-solid fa-circle fa-stack-1x" style="color: green;"></i>
			  					  		<span class="fa-stack-1x" style="color: white; font-weight: bold; font-size: 1em;">A</span>
								  </span>	
								 </div>
								 <div class="col-11">
								  <i> Materiale disponibile nel magazzino di Brescia, consegna in 2/3 gg</i>
					      		</div>
					     	</div>
					    </div>
					    <div class="col-6">
					     	<div class="row">
					    		<div class="col-1">
							      <span class="fa-stack" style="font-size: 1em;">
			 				 			<i class="fa-solid fa-circle fa-stack-1x" style="color: yellow;"></i>
			  					  		<span class="fa-stack-1x" style="color: white; font-weight: bold; font-size: 1em;"></span>
								  </span>	
								 </div>
								 <div class="col-11">
							      <i> Disponibilità parziale, procedere all'aggiunta al carrello per ricevere ulteriori informazioni in conferma d'ordine</i>
					      		</div>
					     	</div>
					    </div>
					    <div class="col-6">
					    	<div class="row">
					    		<div class="col-1">
							      <span class="fa-stack" style="font-size: 1em;">
			 				 			<i class="fa-solid fa-circle fa-stack-1x" style="color: orange;"></i>
			  					  		<span class="fa-stack-1x" style="color: white; font-weight: bold; font-size: 1em;"></span>
								  </span>	
								 </div>
								 <div class="col-11">
							     	<i> L'articolo è in fase di riassortimento, procedere all'aggiunta al carrello per ricevere informazioni precise sulla data di consegna</i>
							     </div>
					     	</div>
					    </div>
				    </div>
			    </div>
				<!-- DSSOF3 Aggiunto titolo con valore del cliente attivato in sessione, funziona come filtro su ogni voce -->
				<%
				if (idClienteAttivo != null && (userPortalSession.getTipoUser().equals(YUserPortalSession.AGENTE)
						|| userPortalSession.getTipoUser().equals(YUserPortalSession.DIPENDENTE))) {
				%>
				<div class="col-1 mt-auto">
					<h1
						style="display: inline; font-size: 1rem; font-weight: bold; color: #DC3545; text-decoration: underline;"><%=idClienteAttivo + " - " + idClienteAttivoRagSoc%></h1>
					<span style="cursor: pointer;" onclick="disattivaClienteAttivo()"
						title="Rimuovi cliente attivo">&#10060;</span>
				</div>
				<%
				} else {
				session.removeAttribute("YClienteAttivo");
				session.removeAttribute("YClienteAttivoRagSoc");
				}
				%>
				<%
				if (userPortalSession.getTipoUser().equals(YUserPortalSession.CLIENTE) && userPortalSession.isCatalogoAttivo) {
				%>
					<div id="cart" style="width: 80px;cursor:pointer" class="col-2 ml-auto p-1 mr-3 mt-2 cart" title="Apri carrello" data-totalitems="0" style="cursor:pointer;" onclick="open_cart()">
						<i class="fa fa-shopping-cart fa-3x"></i>
					</div>
				<%
				}				
				%>
			</div>
			<div class="cont">
				<iframe style="height: 88%; width: 100%; padding: 0; margin: 0;"
					id="frameContent" class="px-4 py-3"> </iframe>
			</div>
			<div style="display: none" class="text-center">
				<a href="#modalTokenExpired" id="tokenExpiredClick"
					class="trigger-btn" data-toggle="modal"></a>
			</div>
			<div id="modalTokenExpired" class="modal fade">
				<div class="modal-dialog modal-confirm">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Attenzione!</h4>
						</div>
						<div class="modal-body">
							<p class="text-center">Token expired, ripetere il login</p>
						</div>
						<div class="modal-footer" style="flex-wrap: unset;">
							<button class="btn btn-block"
								onClick="submitJFun(document.getElementById('11'))"
								data-dismiss="modal">OK</button>
						</div>
					</div>
				</div>
			</div>
			<div style="display: none" class="text-center">
				<a href="#modalWarning" id="modalWarningClick"
					class="trigger-btn" data-toggle="modal"></a>
			</div>
			<div id="modalWarning" class="modal fade">
				<div class="modal-dialog modal-confirm">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Attenzione!</h4>
						</div>
						<div class="modal-body">
							<p id="txtWarning" class="text-center"></p>
						</div>
						<div class="modal-footer" style="flex-wrap: unset;">
							<button class="btn btn-block"
								onClick="$('#modalWarning').modal('toggle');"
								data-dismiss="modal">OK</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
	var daLogin = '<%=login%>';
	<%
	if(items != null){
		%>
		var cart = $('#cart',document);
		cart.addClass('shake').attr('data-totalitems', <%=items%>);
		setTimeout(function(){
	        cart.removeClass('shake');
	      },1000)			
		<%
	}
	%>
$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
});

		function submitJFun(element){
			if(screen.width < 500)
				document.getElementById('sidebarCollapse').click();
			gestioneSpinner(element);
			//if(!element.innerHTML.includes('Anagrafica'))
			mostraSpinner();
			if(element.innerHTML.includes('Logout')){
				setTimeout(()=>{
					evidenziaBottoneCliccato(element);	
					var frameContent = document.getElementById('frameContent');	
					var url = "/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/JFUN.jsp?fun="+element.id;
					url += "&token=<%=token%>";
					url += "&webAppPath="+ document.getElementById('webAppPath').value;
					frameContent.src = url;
				},5000);
			}else{
				if(!element.innerHTML.includes('Catalogo')){
					document.getElementById('semaforiCarrello').style.display = 'none';
				}
				evidenziaBottoneCliccato(element);	
				var frameContent = document.getElementById('frameContent');	
				var url = "/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/JFUN.jsp?fun="+element.id;
				url += "&token=<%=token%>";
				url += "&webAppPath="+ document.getElementById('webAppPath').value;
				frameContent.src = url;
				
			}
		}
		
		function mostraSpinner(){
			document.getElementById('preloader').style.display = "block";
		}
		
		function gestioneSpinner(element){
			if(element != undefined){
				if(/*element.innerHTML.includes('Anagrafica')
						|| element.innerHTML.includes('Clienti')
						|| */element.innerHTML.includes('Logout')){
					document.getElementById("preloader").childNodes[1].classList.add('spinnerSoftre');
					document.getElementById("preloader").childNodes[1].classList.remove('spinner');
				}else{
					document.getElementById("preloader").childNodes[1].classList.add('spinner');
					document.getElementById("preloader").childNodes[1].classList.remove('spinnerSoftre');
				}
				if(daLogin != null && daLogin != undefined && daLogin){
					document.getElementById("preloader").childNodes[1].classList.add('spinnerSoftre');
					document.getElementById("preloader").childNodes[1].classList.remove('spinner');
					daLogin = false;
				}
			}
		}
		
		function evidenziaBottoneCliccato(element) {
		    var selected = document.querySelectorAll(".li-selected");
		    for (var i = 0; i < selected.length; i++) {
		        selected[i].classList.remove("li-selected");
		    }
		    element.parentNode.classList.add("li-selected");
		}

		function apriSchedaAnagrafica() {
			//document.getElementById("preloader").childNodes[1].classList.add('spinnerSoftre');
			//document.getElementById("preloader").childNodes[1].classList.remove('spinner');
			submitJFun(document.getElementsByClassName('nav-link text-white pl-2')[1]);
		}
		
		function open_cart(){
			document.getElementById('15').click();
		}
		
		function apriSupplierOffer(){
			document.getElementById("preloader").childNodes[1].classList.add('spinnerSoftre');
			document.getElementById("preloader").childNodes[1].classList.remove('spinner');
			submitJFun(document.getElementById("<%=YUtilsPortal.FUNZIONE_COMPILAZIONE_OFFERTA_SUPPLIER%>"));
		}
		
		function disattivaClienteAttivo(){
			var frameContent = parent.document.getElementById('frameContent');
			var url = "/<%=YUserPortalSession.WEB_APP_PATH%>/it/valvorobica/thip/base/portal/YRimuoviClienteAttivo.jsp";
			frameContent.src = url;
		}
		</script>
</body>
</html>