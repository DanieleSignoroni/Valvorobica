function YMatchMatricolaArticoloOL(){
  window.resizeTo(616,640);
}

function importaFile(){
    runActionDirect('IMPORTA','action_submit',document.forms[0].thClassName.value, document.forms[0].thKey.value,'same','no')
}
