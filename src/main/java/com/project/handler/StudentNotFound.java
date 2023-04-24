package com.project.handler;

public class StudentNotFound extends RuntimeException{
	private final String message;
    @Override
	public String getMessage() {
		return message;
	}

	public StudentNotFound(String message) {
		this.message = message;
	}
	
	
}
