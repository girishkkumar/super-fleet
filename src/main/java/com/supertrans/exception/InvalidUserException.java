package com.supertrans.exception;

public class InvalidUserException extends RuntimeException {
	public InvalidUserException(String exception) {
		super(exception);
	}

	private static final long serialVersionUID = 1L;

}
