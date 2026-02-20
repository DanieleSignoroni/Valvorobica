var chiudo;
var thChiaveDatiSessione;
var indirizzoServer;
var initialActionAdapter;
var objectKey;
var aggiorno;
var DDT_TES_VEN = "DdtTestataVendita";//Fix 26807
function DocumentoVenditaOL() {
	var target = errorsFrameName;
	var res = runActionDirect(document.DocumentoVenditaForm.thAzione.value, "action_submit", "DocumentoVendita", "", target, "no");
	return res;
}

function chiudi() {
	var className = document.DocumentoVenditaForm.thClassName.value;
	var url = indirizzoServer + initialActionAdapter + "?thInitialActionAdapter=" + initialActionAdapter +
		"&thChiaveDatiSessione=" + thChiaveDatiSessione + "&thAction=UPDATE_RIGHE" +
		"&thClassName=" + className + "&ObjectKey=" + URLEncode(objectKey);
	setLocationOnWindow(window.parent.opener.parent, url); //12091
	closeForm(); //Fix 14242 CHROME

}
