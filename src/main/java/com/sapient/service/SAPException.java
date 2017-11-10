package com.sapient.service;


/**
 * Custom Exception class.
 * 
 * @author nrohil
 *
 */
public class SAPException extends Exception {

	private static final long serialVersionUID = 7563480806584155820L;
	
	private String message;
	private Throwable cause;

	public SAPException(String message, Exception cause) {
		this.message=message;
		this.cause =cause;
	}

	public SAPException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}


	public Throwable getCause() {
		return cause;
	}


}
