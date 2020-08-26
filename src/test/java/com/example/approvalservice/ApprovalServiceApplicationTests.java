package com.example.approvalservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.approvalservice.controller.ApprovalServiceCommandController;
import com.example.approvalservice.service.ApprovalService;

@SpringBootTest
@EnableAutoConfiguration
@ActiveProfiles("test")
class ApprovalServiceApplicationTests {
	
	@Autowired
	private ApprovalServiceCommandController controller;
	
	@Autowired
	private ApprovalService service;

	@Test
	void contextLoads() {
		assertNotNull(controller, "Controller is null");
		assertNotNull(service, "Service is null");
	}

}
