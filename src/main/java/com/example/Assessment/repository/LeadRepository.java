package com.example.Assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Assessment.models.Lead;

public interface LeadRepository extends JpaRepository<Lead, Integer> {
	boolean existsByLeadId(Integer leadId);

	List<Lead> findByMobileNumber(long l);

}
