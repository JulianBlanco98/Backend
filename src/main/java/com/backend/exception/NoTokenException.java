package com.backend.exception;

//@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
public class NoTokenException extends RuntimeException{

	public NoTokenException(String message) {
		super(message);
	}
	
}
