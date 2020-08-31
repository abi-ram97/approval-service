package com.example.approvalservice.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.approvalservice.model.ApprovalDTO;
import com.example.approvalservice.model.validations.CreateApprovalAction;
import com.example.approvalservice.model.validations.UpdateApprovalAction;
import com.example.approvalservice.service.ApprovalService;

/**
 * Handles approval for credit card requests
 * @author javadevopsmc06
 *
 */
@RestController
@RequestMapping(value="/v1/approval")
public class ApprovalServiceCommandController {
	private final Logger logger = LoggerFactory.getLogger(ApprovalServiceCommandController.class);
	private ApprovalService approvalService;
	
	@Autowired
	public ApprovalServiceCommandController(ApprovalService approvalService) {
		this.approvalService = approvalService;
	}
	
	
	/**
	 * creates approval requests
	 * @param approvalDto
	 * @return ResponseEntity
	 */
	@PostMapping("/createApproval")
	public ResponseEntity<String> processApproval(@RequestBody @Validated(CreateApprovalAction.class) 
		ApprovalDTO approvalDto){
		logger.info("Begin approval for [{}]", approvalDto.getRequestId());
		return ResponseEntity.ok(approvalService.beginApproval(approvalDto));
	}
	
	/**
	 * updates the status of approval request
	 * @param approvalDto
	 * @return ResponseEntity
	 */
	@PutMapping("/update")
	public ResponseEntity<String> updateApprovalRequest(@Validated(UpdateApprovalAction.class) @RequestBody 
		@Valid ApprovalDTO approvalDto){
		logger.info("Updating status for [{}] with [{}]", approvalDto.getRequestId(), approvalDto.getStatus());
		return ResponseEntity.ok(approvalService.processApprovalUpdate(approvalDto));
	}
}
