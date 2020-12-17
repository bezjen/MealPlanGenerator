package com.bezjen.whattoeat.model;

import javax.validation.constraints.Size;

public class FeedbackModel {
	@Size(max = 100)
	private String name;
	@Size(max = 100)
	private String email;
	@Size(max = 50)
	private String subject;
	@Size(max = 10000)
	private String message;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
