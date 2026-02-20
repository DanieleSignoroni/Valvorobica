function chiudiListaTrasferimentoFincantieri() {
	var target = "new";
	var res = runActionDirect("CONFERMA_MISSIONI_CHIUDI_LISTA_TRASF_FINCANTIERI", "action_submit", "DocumentoVendita", "", target, "no");
	return res;
}