package com.Backend.Exception;

public class EmailExistException extends RuntimeException{

	public EmailExistException(String message) {
		super(message);
	}
	
}
