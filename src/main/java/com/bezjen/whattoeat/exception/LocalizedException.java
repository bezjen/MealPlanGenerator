package com.bezjen.whattoeat.exception;

public class LocalizedException extends Exception {
	private static final long serialVersionUID = -2686860105284455802L;
	private String key;

	public LocalizedException(String message, String key) {
		super(message);
		this.setKey(key);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
