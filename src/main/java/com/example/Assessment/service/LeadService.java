package com.example.Assessment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Assessment.dtos.LeadRequest;
import com.example.Assessment.exceptions.LeadAlreadyExistsException;
import com.example.Assessment.models.Lead;
import com.example.Assessment.repository.LeadRepository;

@Service
public class LeadService {

	@Autowired
	private LeadRepository leadRepository;

	public void createLead(LeadRequest leadRequest) {
		validateLeadRequest(leadRequest);

		if (leadRepository.existsByLeadId(leadRequest.getLeadId())) {
			throw new LeadAlreadyExistsException("Lead Already Exists in the database with the lead id");
		}

		ModelMapper modelMapper = new ModelMapper();
		Lead lead = modelMapper.map(leadRequest, Lead.class);

		try {
			Lead savedLead = leadRepository.save(lead);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public List<Lead> getLeadsByMobileNumber(long l) {
		return leadRepository.findByMobileNumber(l);
	}

	private void validateLeadRequest(LeadRequest leadRequest) {
		List<String> validationErrors = new ArrayList<>();

		// Validation for leadId
		if (leadRequest.getLeadId() == null) {
			validationErrors.add("LeadId is mandatory.");
		}

		// Validation for firstName
		if (StringUtils.isBlank(leadRequest.getFirstName()) || !StringUtils.isAlpha(leadRequest.getFirstName())) {
			validationErrors.add("FirstName should strictly contain alphabets and be mandatory.");
		}

		// Validation for middleName
		if (StringUtils.isNotBlank(leadRequest.getMiddleName()) && !StringUtils.isAlpha(leadRequest.getMiddleName())) {
			validationErrors.add("MiddleName should strictly contain alphabets.");
		}

		// Validation for lastName
		if (StringUtils.isBlank(leadRequest.getLastName()) || !StringUtils.isAlpha(leadRequest.getLastName())) {
			validationErrors.add("LastName should strictly contain alphabets and be mandatory.");
		}

		// Validation for mobileNumber
		if (leadRequest.getMobileNumber() == null || !isValidMobileNumber(leadRequest.getMobileNumber())) {
			validationErrors.add(
					"MobileNumber should strictly contain an integer, and the first digit should be greater than 5, and it is mandatory.");
		}

		// Validation for gender
		if (StringUtils.isBlank(leadRequest.getGender()) || !isValidGender(leadRequest.getGender())) {
			validationErrors.add("Gender should be Male/Female/Others and mandatory.");
		}

		// Validation for DOB
		if (leadRequest.getDob() == null || !isValidDate(leadRequest.getDob())) {
			validationErrors.add("DOB should strictly be in Date Format and mandatory.");
		}

		// Validation for email
		if (StringUtils.isBlank(leadRequest.getEmail()) || !isValidEmail(leadRequest.getEmail())) {
			validationErrors.add("Email should be valid and mandatory.");
		}

		if (!validationErrors.isEmpty()) {
			throw new IllegalArgumentException("Invalid LeadRequest: " + String.join(", ", validationErrors));
		}
	}

	private boolean isValidMobileNumber(Long mobileNumber) {
		// Validate that mobile number is not null and the first digit is greater than 5
		if (mobileNumber == null) {
			return false;
		}

		String mobileString = String.valueOf(mobileNumber);
		char firstDigit = mobileString.charAt(0);

		return Character.isDigit(firstDigit) && Character.getNumericValue(firstDigit) > 5;
	}

	private boolean isValidGender(String gender) {
		// Validate that gender is not null and is one of the specified values
		return StringUtils.isNotBlank(gender) && Arrays.asList("Male", "Female", "Others").contains(gender);
	}

	private boolean isValidDate(LocalDate date) {

		return date != null;
	}

	private boolean isValidEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return false;
		}

		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		return email.matches(emailRegex);
	}

}
