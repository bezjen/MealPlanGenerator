package com.bezjen.whattoeat.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SignupModel {
	@NotBlank(message = "{messages.error.signup.username.empty}") 
	@Pattern(regexp = "^[a-zA-Z0-9_\\-.]{3,30}$", message = "{messages.error.signup.username.format}")
	private String username;
	@NotBlank(message = "{messages.error.signup.email.empty}")
	@Email(message = "{messages.error.signup.email.format}")
	private String email;
	@Size(min = 5, message = "{messages.error.signup.password.length}")
	private String password;
	private String passwordConfirm;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
