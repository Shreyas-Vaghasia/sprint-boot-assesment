package com.example.Assessment.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Assessment.dtos.ApiResponse;
import com.example.Assessment.dtos.ErrorDetail;
import com.example.Assessment.dtos.ErrorResponse;
import com.example.Assessment.dtos.LeadRequest;
import com.example.Assessment.exceptions.LeadAlreadyExistsException;
import com.example.Assessment.models.Lead;
import com.example.Assessment.service.LeadService;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

	@Autowired
	private LeadService leadService;

	@PostMapping(value = "/create", produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> createLead(@RequestBody LeadRequest leadRequest) {
		try {
			leadService.createLead(leadRequest);
			return ResponseEntity.ok(new ApiResponse("success", "Created Leads Successfully"));
		} catch (LeadAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ErrorResponse("error", new ErrorDetail("E10010", Collections.singletonList(e.getMessage()))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ErrorResponse("error", new ErrorDetail("E10010", Collections.singletonList(e.getMessage()))));
		}
	}

	@GetMapping("/")
	public ResponseEntity<?> getLeadsByMobileNumber(@RequestParam String mobileNumber) {
		List<Lead> leads = leadService.getLeadsByMobileNumber(Long.parseLong(mobileNumber));

		if (leads.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("error",
					new ErrorDetail("E10011", Collections.singletonList("No Lead found with the Mobile Number"))));
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", leads));
	}
}
