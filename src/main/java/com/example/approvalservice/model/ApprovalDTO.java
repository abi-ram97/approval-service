package com.example.approvalservice.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.approvalservice.model.validations.CreateApprovalAction;
import com.example.approvalservice.model.validations.UpdateApprovalAction;

import lombok.Data;


/**
 * Approval Request Model
 * @author javadevopsmc06
 *
 */
@Data
public class ApprovalDTO {
	@NotEmpty(groups = {CreateApprovalAction.class, UpdateApprovalAction.class}, 
			message = "CustomerId cannot be empty")
	private String customerId;
	@NotEmpty(groups = {CreateApprovalAction.class, UpdateApprovalAction.class},
			message = "RequestId cannot be empty")
	private String requestId;
	@NotNull
	private String approver;
	@NotEmpty(groups = {UpdateApprovalAction.class},message = "Status cannot be empty")
	private String status;
	@NotEmpty(groups = {UpdateApprovalAction.class},message = "ApprovalId cannot be empty")
	private String approvalId;
	
	private String reason;
}
