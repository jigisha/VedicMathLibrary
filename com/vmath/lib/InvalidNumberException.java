package com.vmath.lib;

public class InvalidNumberException extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidNumberException() {
		super();
	}
	
	public InvalidNumberException(String message){
		super(message);
	}
	
	public InvalidNumberException(String message, Throwable cause){
		super(message,cause);
	}
	
	public InvalidNumberException(Throwable cause){
		super(cause);
	}

}
