package com.example.swiggy.exception;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleOrderNotFound(
			OrderNotFoundException exception , WebRequest request){
		
		logger.warn("Order not found: {}", exception.getMessage());
		
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 
				HttpStatus.NOT_FOUND.value(),
				exception.getMessage(),
				((ServletWebRequest) request).getRequest().getRequestURI());
		
		return new ResponseEntity<>(errorResponse ,HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception,
	        WebRequest request) {

		logger.warn("Validation failed for request: {}",
		        ((ServletWebRequest) request)
		                .getRequest()
		                .getRequestURI());
		
		String message = exception.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error -> error.getField()
	                    + ": "
	                    + error.getDefaultMessage())
	            .findFirst()
	            .orElse("Validation failed");

	    ErrorResponse errorResponse =
	            new ErrorResponse(
	                    LocalDateTime.now(),
	                    HttpStatus.BAD_REQUEST.value(),
	                    message,
	                    ((ServletWebRequest) request)
	                            .getRequest()
	                            .getRequestURI()
	            );

	    return new ResponseEntity<>(
	            errorResponse,
	            HttpStatus.BAD_REQUEST
	    );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
