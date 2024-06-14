function YMatricolaValvoOL(){
  window.resizeTo(550,650);
}

function importaFile(){
    runActionDirect('IMPORTA','action_submit',document.forms[0].thClassName.value, document.forms[0].thKey.value,'same','no')
}
