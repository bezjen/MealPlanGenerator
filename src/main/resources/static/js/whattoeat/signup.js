var signupPaths = {
	validateUsername : '/signup/validateUsername',
	validateEmail : '/signup/validateEmail'
}

function validateSignupInput(inputValue, path) {
    if (inputValue.val().trim().length == 0) {
	    inputValue.removeClass();
		inputValue.addClass('form-control is-invalid')
        return false;
    }
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
		resultMessage.text(result.message);
	});;
}

function validatePasswordsAction(suPassword, suPasswordConfirm) {
	suPasswordConfirm.removeClass();
	suPasswordConfirm.addClass('form-control');
	if (suPassword.val() == suPasswordConfirm.val()) {
		suPasswordConfirm.addClass('is-valid');
	} else {
		suPasswordConfirm.addClass('is-invalid');
	}
}

function validateForm() {
	$('#su-name-general').keyup(function(e) {
		if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
		var suUsername = $(this);
		validateSignupInput(suUsername, signupPaths.validateUsername);
	});
	$('#su-email-general').keyup(function(e) {
		if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
		var suEmail = $(this);
		validateSignupInput(suEmail, signupPaths.validateEmail);
	});
	$('#su-password-general').keyup(function(e) {
    	    if (ajaxIgnoredKeys[e.keyCode]) {
    	        return;
    	    }
    		var suPassword = $(this);
    		suPassword.removeClass();
    		suPassword.addClass('form-control');
    		if (suPassword.val().length < 5) {
    			suPassword.addClass('is-invalid');
    		}
            validatePasswordsAction($(this), $('#su-password-confirm-general'));
    	});
	$('#su-password-confirm-general').keyup(function(e) {
	    if (ajaxIgnoredKeys[e.keyCode]) {
	        return;
	    }
	    validatePasswordsAction($('#su-password-general'), $(this));
	});
}

function submitHandler() {
	$('#signup-tab-general').submit(function(event) {
		event.preventDefault();
		validateSignupInput($('#su-name-general'), signupPaths.validateUsername);
		validateSignupInput($('#su-email-general'), signupPaths.validateEmail);
		var suPassword = $('#su-password-general');
		if (suPassword.val().length < 5 && !suPassword.hasClass('is-invalid')) {
			suPassword.addClass('is-invalid');
			suPasswordConfirm.addClass('is-invalid');
		} else {
            var suPasswordConfirm = $('#su-password-confirm-general');
            if (suPasswordConfirm.val() != $('#su-password-general').val()
                    && !suPasswordConfirm.hasClass('is-invalid')) {
                suPasswordConfirm.addClass('is-invalid');
            }
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
	if(activeTab) {
		$('.nav-item a[href="' + activeTab + '"]').click();
	}
	validateForm();
	submitHandler();
});