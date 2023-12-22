package com.example.Assessment.dtos;

public class ErrorResponse {
	private String status;
	private ErrorDetail errorResponse;

	public ErrorResponse(String status, ErrorDetail errorResponse) {
		this.status = status;
		this.errorResponse = errorResponse;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ErrorDetail getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorDetail errorResponse) {
		this.errorResponse = errorResponse;
	}

}
