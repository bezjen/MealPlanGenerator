function accountSettingsValidatePassword() {
	$('#account-pass').keyup(function(e) {
	    if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
		var suPassword = $(this);
		suPassword.removeClass();
		suPassword.addClass('form-control');
		if (suPassword.val().length < 5) {
			suPassword.addClass('is-invalid');
		}
		
		accountSettingsValidatePasswordsAction($(this), $('#account-confirm-pass'));
	});
}

function accountSettingsValidatePasswords() {
	$('#account-confirm-pass').keyup(function(e) {
	    if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
	    accountSettingsValidatePasswordsAction($('#account-pass'), $(this));
	});
}

function accountSettingsValidatePasswordsAction(suPassword, suPasswordConfirm) {
	suPasswordConfirm.removeClass();
	suPasswordConfirm.addClass('form-control');
	if (suPassword.val() == suPasswordConfirm.val()) {
		suPasswordConfirm.addClass('is-valid');
	} else {
		suPasswordConfirm.addClass('is-invalid');
	}
}

function accountSettingsValidateForm() {
	accountSettingsValidatePassword();
	accountSettingsValidatePasswords();
}

function accountSettingsSubmitHandler() {
	$('#account-settings-form').submit(function(event) {
		event.preventDefault();
		var suPassword = $('#account-pass');
		if (suPassword.val().length < 5 && !suPassword.hasClass('is-invalid')) {
			suPassword.addClass('is-invalid');
		}
		var suPasswordConfirm = $('#account-confirm-pass');
		if (suPasswordConfirm.val() != suPassword.val() 
				&& !suPasswordConfirm.hasClass('is-invalid')) {
			suPasswordConfirm.addClass('is-invalid');
		}
		var signupForm = $(this);
		if (signupForm.find('.is-invalid').length == 0) {
			signupForm.unbind('submit').submit();
		} else {
			signupForm.removeClass('was-validated');
		}
	});
}

$(document).ready(function() {
	accountSettingsValidateForm();
	accountSettingsSubmitHandler();
});