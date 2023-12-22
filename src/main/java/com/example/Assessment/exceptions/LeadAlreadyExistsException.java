package com.example.Assessment.exceptions;

public class LeadAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LeadAlreadyExistsException(String message) {
		super(message);
	}
}
