package com.example.approvalservice.controller;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.approvalservice.model.ServiceError;

/**
 * CustomExceptionHandler
 * @author javadevopsmc06
 *
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRunTimeException(RuntimeException rte){
		return ResponseEntity.badRequest().body(ServiceError.builder().errorMessage(rte.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
		return ResponseEntity.badRequest().body(ServiceError.builder().errorMessage(errorMessage).status(HttpStatus.BAD_REQUEST.value()).build());
	}
	
}
