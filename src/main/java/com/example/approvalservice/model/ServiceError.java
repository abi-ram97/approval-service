package com.example.approvalservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic Error response
 * @author javadevopsmc06
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceError {
	private String errorMessage;
	
	private int status;
	
}
