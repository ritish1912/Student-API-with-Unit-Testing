package com.project.handler;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAndValidationHandler extends ResponseEntityExceptionHandler {

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BindingResult br = ex.getBindingResult();
		List<ObjectError> errors = br.getAllErrors();
		List<String> list = new ArrayList<>();
		ResponseError responseError = new ResponseError("Validation failed", list);
		for (ObjectError error : errors) {
			list.add(error.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
	}
	
	@ExceptionHandler(StudentNotFound.class)
	ResponseEntity<ResponseError> studentNotFoundException(StudentNotFound ex){
	
		  List<String> list=new ArrayList<>();
		  list.add(ex.getMessage());
		 ResponseError error=new ResponseError("Invalid Request Payload",list);
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	

	 @ExceptionHandler(EnrollmentIdAlreadyExist.class)
	    public ResponseEntity<Object> handleDoctorNotFoundEXception(EnrollmentIdAlreadyExist ex)
	    {
	        List<String> list=new ArrayList<>();
	        list.add(ex.getMessage());
	        ResponseError error=new ResponseError("Invalid Request Payload",list);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	    } 

	
	
}
