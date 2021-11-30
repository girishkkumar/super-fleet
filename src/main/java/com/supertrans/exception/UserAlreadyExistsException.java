package com.supertrans.exception;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(String exception) {
		super(exception);
	}

	private static final long serialVersionUID = 1L;

}
