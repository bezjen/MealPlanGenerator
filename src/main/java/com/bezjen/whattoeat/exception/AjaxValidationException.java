package com.bezjen.whattoeat.exception;

import javax.validation.ValidationException;

public class AjaxValidationException extends ValidationException {
	private static final long serialVersionUID = -2686860105284455802L;

	public AjaxValidationException(String message) {
		super(message);
	}
}
