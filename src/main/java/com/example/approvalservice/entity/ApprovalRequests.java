package com.example.approvalservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

/**
 * Persistence class for ApprovalRequests
 * @author javadevopsmc06
 *
 */
@Entity
@Data
@Table(name = "APPROVAL_REQUEST", schema = "MCDBO")
public class ApprovalRequests {
	
	private static final String DEFAULT_CREATEBY="SYSTEM";
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "APPROVAL_ID")
	private String approvalId;
	
	private String customerId;
	
	@Column(name = "REQUEST_ID", unique = true)
	private String requestId;
	
	private String approver;
	
	private String status;
	
	private String createdBy;
	
	private LocalDateTime createdDate;
	
	private String lastModifiedBy;
	
	private LocalDateTime lastModifiedDate;

	public ApprovalRequests() {
		
	}

	public ApprovalRequests(String customerId, String requestId, String approver) {
		this.customerId = customerId;
		this.requestId = requestId;
		this.approver = approver;
		this.createdBy = DEFAULT_CREATEBY;
		this.createdDate = LocalDateTime.now();
		this.lastModifiedBy = DEFAULT_CREATEBY;
		this.lastModifiedDate = LocalDateTime.now();
	}

	@PrePersist
	public void addDefaultCreateBy() {
		this.createdBy = DEFAULT_CREATEBY;
		this.createdDate = LocalDateTime.now();
		this.lastModifiedBy = DEFAULT_CREATEBY;
		this.lastModifiedDate = LocalDateTime.now();
	}
	
	@PreUpdate
	public void updateModifiedBy() {
		this.lastModifiedBy = DEFAULT_CREATEBY;
		this.lastModifiedDate = LocalDateTime.now();
	}
	
}
