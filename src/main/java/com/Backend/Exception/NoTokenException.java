package com.Backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
public class NoTokenException extends RuntimeException{

	public NoTokenException(String message) {
		super(message);
	}
	
}
