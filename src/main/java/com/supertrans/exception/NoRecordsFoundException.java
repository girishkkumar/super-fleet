package com.supertrans.exception;

public class NoRecordsFoundException extends RuntimeException {
	public NoRecordsFoundException(String exception) {
		super(exception);
	}

	private static final long serialVersionUID = 1L;

}
