package com.example.Assessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.example.Assessment.controllers.LeadController;
import com.example.Assessment.dtos.ApiResponse;
import com.example.Assessment.dtos.ErrorResponse;
import com.example.Assessment.models.Lead;
import com.example.Assessment.service.LeadService;

public class LeadControllerTest {

	@InjectMocks
	private LeadController leadController;

	@Mock
	private LeadService leadService;

	@Test
    public void testGetLeadsByMobileNumber_Success() {
        when(leadService.getLeadsByMobileNumber(anyLong()))
                .thenReturn(Collections.singletonList(createSampleLead()));

        ResponseEntity<?> responseEntity = leadController.getLeadsByMobileNumber("8877887788");

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("success", ((ApiResponse) responseEntity.getBody()).getStatus());
    }

	@Test
    public void testGetLeadsByMobileNumber_NoLeadFound() {
        when(leadService.getLeadsByMobileNumber(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<?> responseEntity = leadController.getLeadsByMobileNumber("1234567890");

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("error", ((ApiResponse) responseEntity.getBody()).getStatus());
        assertEquals("E10011", ((ErrorResponse) responseEntity.getBody()).getClass());
    }

	private Lead createSampleLead() {
		Lead lead = new Lead();
		lead.setLeadId(5678);
		lead.setFirstName("Vineet");
		lead.setLastName("KV");
		lead.setMobileNumber(8877887788L);
		lead.setGender("Male");
		lead.setDob(LocalDate.now());
		lead.setEmail("v@gmail.com");
		return lead;
	}
}
