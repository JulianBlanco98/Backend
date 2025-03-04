package com.backend.exception;

//@ResponseStatus(HttpStatus.FORBIDDEN) // 403
public class InvalidTokenException extends RuntimeException{

	public InvalidTokenException(String message) {
		super(message);
	}
}
