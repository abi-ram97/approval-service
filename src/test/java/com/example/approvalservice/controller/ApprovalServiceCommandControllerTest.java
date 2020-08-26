package com.example.approvalservice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.approvalservice.model.ApprovalDTO;
import com.example.approvalservice.service.ApprovalService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
class ApprovalServiceCommandControllerTest {
	
	private MockMvc mockMvc;
	
	private ObjectMapper mapper;
	
	@MockBean
	private ApprovalService approvalService;
	
	private ApprovalDTO approvalDto;
	
	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ApprovalServiceCommandController(approvalService)).build();
		this.mapper = new ObjectMapper();
		
		approvalDto = new ApprovalDTO();
		approvalDto.setApprovalId("approval1");
		approvalDto.setRequestId("request1");
		approvalDto.setStatus("APPROVED");
	}

	@Test
	void testProcessApproval() throws Exception {
		approvalDto.setCustomerId("customer1");
		when(approvalService.beginApproval(Mockito.any(ApprovalDTO.class))).thenReturn("approval1");
		MvcResult result = this.mockMvc.perform(post("/v1/approval/createApproval")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(approvalDto)))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse(), "Create approval response is null");
		assertEquals("Create approval response not matched", "approval1", result.getResponse().getContentAsString());
	}
	
	@Test
	void testProcessApprovalEmptyBody() throws Exception {
		this.mockMvc.perform(post("/v1/approval/createApproval"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	void testProcessApprovalCheckValidations() throws Exception {
		this.mockMvc.perform(post("/v1/approval/createApproval")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(approvalDto)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

	@Test
	void testUpdateApprovalRequestValidations() throws Exception {
		this.mockMvc.perform(put("/v1/approval/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(approvalDto)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andReturn();
	}

}
