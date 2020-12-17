var signupPaths = {
	validateUsername : '/signup/validateUsername',
	validateEmail : '/signup/validateEmail'
}

function validateSignupInputModal(inputValue, path) {
	$.ajax({
		type: 'POST',
		traditional: true,
		url: window.location.origin + path,
		data: { 'inputValue' : inputValue.val() }, 
		success: function() {
			inputValue.removeClass();
			inputValue.addClass('form-control is-valid')
		},
	}).fail(function($xhr) {
	    var result = $xhr.responseJSON;
	    inputValue.removeClass();
		inputValue.addClass('form-control is-invalid')
		var resultMessage = inputValue.parent().find('div[class="invalid-feedback"]');
		if(typeof result != "undefined") {
		    resultMessage.text(result.message);
		}
	});
}

function validatePasswordModal() {
	$('#su-password').keyup(function(e) {
	    if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
		var suPassword = $(this);
		suPassword.removeClass();
		suPassword.addClass('form-control');
		if (suPassword.val().length < 5) {
			suPassword.addClass('is-invalid');
		}
		
		validatePasswordsActionModal($(this), $('#su-password-confirm'));
	});
}

function validatePasswordsModal() {
	$('#su-password-confirm').keyup(function(e) {
	    if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
	    validatePasswordsActionModal($('#su-password'), $(this));
	});
}

function validatePasswordsActionModal(suPassword, suPasswordConfirm) {
	suPasswordConfirm.removeClass();
	suPasswordConfirm.addClass('form-control');
	if (suPassword.val() == suPasswordConfirm.val()) {
		suPasswordConfirm.addClass('is-valid');
	} else {
		suPasswordConfirm.addClass('is-invalid');
	}
}

function validateFormModal() {
	$('#su-name').keyup(function(e) {
		if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
		var suUsername = $(this);
		validateSignupInputModal(suUsername, signupPaths.validateUsername);
	});
	$('#su-email').keyup(function(e) {
		if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
		var suEmail = $(this);
		validateSignupInputModal(suEmail, signupPaths.validateEmail);
	});
	validatePasswordModal();
	validatePasswordsModal();
}

function submitHandlerModal() {
	$('#signup-tab').submit(function(event) {
		event.preventDefault();
		validateSignupInputModal($('#su-name'), signupPaths.validateUsername);
		validateSignupInputModal($('#su-email'), signupPaths.validateEmail);
		var suPassword = $('#su-password');
		if (suPassword.val().length < 5 && !suPassword.hasClass('is-invalid')) {
			suPassword.addClass('is-invalid');
		}
		var suPasswordConfirm = $('#su-password-confirm');
		if (suPasswordConfirm.val() != $('#su-password').val() 
				&& !suPasswordConfirm.hasClass('is-invalid')) {
			suPasswordConfirm.addClass('is-invalid');
		}
		var signupForm = $(this);
		setTimeout(() => {
			if (signupForm.find('.is-invalid').length == 0) {
				signupForm.unbind('submit').submit();
			} else {
				signupForm.removeClass('was-validated');
			}
		}, 500);
	});
}

$(document).ready(function() {
	validateFormModal();
	submitHandlerModal();
});