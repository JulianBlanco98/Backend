package com.Backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.FORBIDDEN) // 403
public class InvalidTokenException extends RuntimeException{

	public InvalidTokenException(String message) {
		super(message);
	}
}
