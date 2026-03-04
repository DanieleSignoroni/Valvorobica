function YAziendeUserPortalOL() {
	let mode = document.getElementById('thMode').value;
	if (mode === "NEW") {
		document.getElementById("TipoUser").parentNode.parentNode.style.display = "none";
		document.forms[0].ECommerce.parentNode.parentNode.style.display = "none";
				document.forms[0].GestioneContoDep.parentNode.parentNode.style.display = "none";
	} else {
		changeTipoCliente();
	}
}

function changeTipoCliente() {
	document.getElementById("RCliente").parentNode.parentNode.style.display = "none";
	document.getElementById("RAgente").parentNode.parentNode.style.display = "none";
	document.getElementById("RDipendente").parentNode.parentNode.style.display = "none";
	document.getElementById("RFornitore").parentNode.parentNode.style.display = "none";
	let tipoUtente = document.getElementById('TipoUser').value;
	let attDaAttivre = "";
	if (tipoUtente === "C") {
		attDaAttivre = "RCliente";
	} else if (tipoUtente === "A") {
		attDaAttivre = "RAgente";
	} else if (tipoUtente === "D") {
		attDaAttivre = "RDipendente";
	} else if (tipoUtente == "F") {
		attDaAttivre = "RFornitore";
	}
	document.getElementById(attDaAttivre).parentNode.parentNode.style.display = "contents";
	if (tipoUtente != "C") {
		document.forms[0].ECommerce.parentNode.parentNode.style.display = "none";
		document.forms[0].GestioneContoDep.parentNode.parentNode.style.display = "none";
	} else {
		document.forms[0].ECommerce.parentNode.parentNode.style.display = "revert";
		document.forms[0].GestioneContoDep.parentNode.parentNode.style.display = "revert";
	}
}

function parametrizzaUserPortal() {
	document.getElementById('RUtentePth').value = 'PORTALUSER';
}