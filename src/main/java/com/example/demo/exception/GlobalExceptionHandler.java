package com.example.demo.exception;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	private ProblemDetail resourceNotFoundException(ResourceNotFoundException exception) {
		 logger.error("Resource not found: {}", exception.getMessage());
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		logger.error("Method argument not valid: {}", exception.getMessage());
		   
		Map<String, String> exceptionMessage = new HashMap<>();
		List<ObjectError> errors = exception.getBindingResult().getAllErrors();
		errors.forEach(error -> {
			FieldError err = (FieldError) error;
			exceptionMessage.put(err.getField(), err.getDefaultMessage());
		});
		return exceptionMessage;
	}
}
