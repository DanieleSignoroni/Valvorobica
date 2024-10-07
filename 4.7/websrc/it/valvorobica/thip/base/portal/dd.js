function sendMailSalesManager() {
	var nome = document.getElementById('Nome').value;
	var cognome = document.getElementById('Cognome').value;
	var email = document.getElementById('Email').value;
	var note = document.getElementById('Note').value;

	var payload = {
		nome: nome,
		cognome: cognome,
		email: email,
		note: note
	};

	$.ajax({
		url: $('#urlWS').val() + '?id=YADDC&tokenUID=' + $('#token').val(),
		method: 'POST',
		dataType: 'json',
		data: json,
		contentType: 'application/json; charset=utf-8',
		success: function(response) {

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