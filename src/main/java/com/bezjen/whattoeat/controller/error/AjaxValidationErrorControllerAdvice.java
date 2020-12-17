package com.bezjen.whattoeat.controller.error;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.bezjen.whattoeat.exception.AjaxValidationException;

@ControllerAdvice
public class AjaxValidationErrorControllerAdvice {
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JSONObject> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        return getResponseEntity(ex.getConstraintViolations().iterator().next().getMessage());
    }

	@ExceptionHandler(AjaxValidationException.class)
    public ResponseEntity<JSONObject> ajaxValidationExceptionHandler(AjaxValidationException ex, WebRequest request) {
        return getResponseEntity(ex.getMessage());
    }
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<JSONObject> handlefileSizeLimitExceeded(MaxUploadSizeExceededException ex, Model model) {
	    return getResponseEntity(ex.getMessage());
	}
	
	private ResponseEntity<JSONObject> getResponseEntity(String message) {
		Map<String, Object> result = new HashMap<>();
		result.put("message", message);
		return new ResponseEntity<>(new JSONObject(result), HttpStatus.BAD_REQUEST);
	}
}