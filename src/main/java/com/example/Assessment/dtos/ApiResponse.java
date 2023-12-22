package com.example.Assessment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ApiResponse {
	public ApiResponse(String status, Object data) {
		this.status = status;
		this.data = data;
	}

	public ApiResponse() {
	}

	private String status;
	private Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
