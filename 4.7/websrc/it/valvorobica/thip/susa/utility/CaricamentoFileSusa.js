var enctypeDefault;
var encodingDefault;
var enctypeMultipart = "multipart/form-data";

function YCaricamentoFileSusaOL() {
	window.resizeTo(700, 500);
	enctypeDefault = document.forms[0].enctype;
	encodingDefault = document.forms[0].encoding;
	document.getElementById('NomeFile').style.background = mCo; //mettiamolo giallo va'

	document.getElementById('NomeFile').addEventListener('change', async () => {
		enableMultipart();
		const formData = new FormData();
		formData.append('NomeFile', document.getElementById('NomeFile').files[0]);
		var url = "/" + webAppPath + "/" + servletPath + "/it.valvorobica.thip.susa.utility.web.CaricamentoFileSusaSalvaFile";
		const xhr = new XMLHttpRequest();
		xhr.open('POST', url, true);
		xhr.onload = () => {
			if (xhr.status === 200) {
				console.log('OK:', xhr.responseText);
			} else {
				console.error('Errore:', xhr.status, xhr.responseText);
			}
		};

		xhr.onload = () => {
			if (xhr.status === 200) {
				const fullPath = xhr.responseText.trim();
				document.forms[0].TemporaryFileName.value = fullPath;
			} else {
				console.error('Errore:', xhr.status, xhr.responseText);
			}
			disableMultipart();
		};

		xhr.onerror = () => console.error('Errore di rete');
		xhr.send(formData);
	});
}

function enableMultipart() {
	document.forms[0].enctype = enctypeMultipart;
	document.forms[0].encoding = enctypeMultipart;
}
function disableMultipart() {
	document.forms[0].enctype = enctypeDefault;
	document.forms[0].encoding = encodingDefault;
}