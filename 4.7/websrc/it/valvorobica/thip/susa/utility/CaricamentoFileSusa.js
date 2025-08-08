var enctypeDefault;
var encodingDefault;
var enctypeMultipart = "multipart/form-data";

function YCaricamentoFileSusaOL() {
	window.resizeTo(700,500);
	enctypeDefault = document.forms[0].enctype;
	encodingDefault = document.forms[0].encoding;
	document.getElementById('NomeFile').style.background = mCo; //mettiamolo giallo va'
}

function enableMultipart() {
	document.forms[0].enctype = enctypeMultipart;
	document.forms[0].encoding = enctypeMultipart;
}
function disableMultipart() {
	document.forms[0].enctype = enctypeDefault;
	document.forms[0].encoding = encodingDefault;
}

var oldRunActionDirect = runActionDirect;
runActionDirect = function(action, type, classhdr, key, target, toolbar) {
	if (action != 'SAVE' && action != 'SAVE_AND_CLOSE' && action != 'SAVE_AND_NEW')
		disableMultipart();
	else
		enableMultipart();
	oldRunActionDirect(action, type, classhdr, key, target, toolbar);
}
