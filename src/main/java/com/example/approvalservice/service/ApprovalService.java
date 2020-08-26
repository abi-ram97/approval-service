package com.example.approvalservice.service;

import com.example.approvalservice.model.ApprovalDTO;

public interface ApprovalService {
	public String beginApproval(ApprovalDTO approvalDto);
	public String processApprovalUpdate(ApprovalDTO approvalDto);
}
