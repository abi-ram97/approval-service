package com.example.approvalservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.approvalservice.entity.ApprovalRequests;
import com.example.approvalservice.model.ApprovalDTO;
import com.example.approvalservice.repository.ApprovalRequestRepository;
import com.example.approvalservice.service.impl.ApprovalServiceImpl;
import com.example.approvalservice.util.ServiceConnector;

@RunWith(SpringRunner.class)
class ApprovalServiceImplTest {
	
	@Mock
	private ApprovalRequestRepository approvalRequestRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private ServiceConnector serviceConnector;
	
	@InjectMocks
	private ApprovalServiceImpl service;
	
	private ApprovalDTO approvalDto;
	private ApprovalRequests request;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FieldUtils.writeField(service, "creditCardServiceUrl", "http://localhost:8081", true);
		approvalDto = new ApprovalDTO();
		approvalDto.setApprovalId("approval1");
		approvalDto.setRequestId("requestId");
		approvalDto.setStatus("SUCCESS");
		request = new ApprovalRequests();
		request.setApprovalId("approval1");
		request.setRequestId("request1");
	}

	@Test
	void testBeginApproval() {
		when(approvalRequestRepository.save(Mockito.any(ApprovalRequests.class))).thenReturn(request);
		when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(request);
		String result = service.beginApproval(approvalDto);
		assertNotNull(result, "Approval Result is null");
		assertEquals("Approval Id is not matching", "approval1", result);
		verify(approvalRequestRepository, times(1)).save(Mockito.any(ApprovalRequests.class));
	}
	
	@Test
	void testProcessApprovalUpdate() {
		when(approvalRequestRepository.findByApprovalIdAndRequestId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(request));
		when(serviceConnector.exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("Updated successfully");
		String result = service.processApprovalUpdate(approvalDto);
		assertNotNull(result, "Approval Result is null");
		assertEquals("Update msg is not matching", "Updated Sucessfully", result);
		verify(approvalRequestRepository, times(1)).findByApprovalIdAndRequestId(Mockito.anyString(), Mockito.anyString());
		verify(serviceConnector, times(1)).exchange(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
	}
	
	@Test
	void testProcessApprovalUpdateNotFound() {
		when(approvalRequestRepository.findByApprovalIdAndRequestId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
		String result = service.processApprovalUpdate(approvalDto);
		assertNotNull(result, "Approval Result is null");
		assertEquals("Update msg is not matching", "No details found", result);
		verify(approvalRequestRepository, times(1)).findByApprovalIdAndRequestId(Mockito.anyString(), Mockito.anyString());
	}

}
