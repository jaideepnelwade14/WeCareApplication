package com.team3.wecare.exception;

public class WeCareException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message = "";

	public WeCareException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}