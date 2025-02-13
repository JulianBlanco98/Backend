package com.Backend.Exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalHandler {
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail exceptionHandling(Exception exception) {
		
		ProblemDetail errorDetail = null;
		if(exception instanceof EntityNotFoundException) {
			errorDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(404));
			errorDetail.setProperty("message", exception.getMessage());
		}
		else if(exception instanceof EntityExistsException) {
			errorDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(409));
			errorDetail.setProperty("message", exception.getMessage());
		}
		else if(exception instanceof IllegalArgumentException) {
			errorDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(400));
			errorDetail.setProperty("message", exception.getMessage());			
		}
		else {
			errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());						
		}
//		if(errorDetail == null) {}
		return errorDetail;
	}
	
}
