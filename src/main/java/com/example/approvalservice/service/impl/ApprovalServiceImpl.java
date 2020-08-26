package com.example.approvalservice.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.approvalservice.entity.ApprovalRequests;
import com.example.approvalservice.model.ApprovalDTO;
import com.example.approvalservice.repository.ApprovalRequestRepository;
import com.example.approvalservice.service.ApprovalService;
import com.example.approvalservice.util.ServiceConnector;

import lombok.extern.slf4j.Slf4j;


/**
 * To process the approval of credit card requests
 * @author javadevopsmc06
 *
 */
@Service
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {
	@Value("${service.creditCard}")
	private String creditCardServiceUrl;
	
	private ApprovalRequestRepository approvalRequestRepository;
	
	private ModelMapper modelMapper;
	
	private ServiceConnector serviceConnector;
	
	@Autowired
	public ApprovalServiceImpl(ApprovalRequestRepository approvalRequestRepository, ModelMapper modelMapper,
			ServiceConnector serviceConnector) {
		this.approvalRequestRepository = approvalRequestRepository;
		this.modelMapper = modelMapper;
		this.serviceConnector = serviceConnector;
	}

	/**
	 * generates approval request
	 * @param approvalDto
	 * @return approvalId
	 */
	@Override
	public String beginApproval(ApprovalDTO approvalDto) {
		log.info("Begin {}", approvalDto);
		ApprovalRequests request = modelMapper.map(approvalDto, ApprovalRequests.class);
		request = approvalRequestRepository.save(request);
		return request.getApprovalId();
	}

	/**
	 * updates the approval record
	 * @param approvalDto
	 * @return message
	 */
	@Override
	public String processApprovalUpdate(ApprovalDTO approvalDto) {
		Optional<ApprovalRequests> optionalReq = approvalRequestRepository.findByApprovalIdAndRequestId(approvalDto.getApprovalId(), approvalDto.getRequestId());
		if (optionalReq.isPresent()) {
			ApprovalRequests request = optionalReq.get();
			request.setStatus(approvalDto.getStatus());
			approvalRequestRepository.save(request);
			updateRequest(request);
			return "Updated Sucessfully";
		}
		return "No details found";
	}

	private void updateRequest(ApprovalRequests request) {
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(creditCardServiceUrl)
				.path("update/")
				.path(request.getRequestId())
				.queryParam("status", request.getStatus());
		serviceConnector.exchange(uri.build().toUriString(), 
				HttpMethod.PUT, null, String.class, Collections.emptyMap());
	}
}
