package com.vmath.lib;

public class InvalidMultiplicationArgException extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidMultiplicationArgException() {
		super();
	}
	
	public InvalidMultiplicationArgException(String message){
		super(message);
	}
	
	public InvalidMultiplicationArgException(String message, Throwable cause){
		super(message,cause);
	}
	
	public InvalidMultiplicationArgException(Throwable cause){
		super(cause);
	}

}
