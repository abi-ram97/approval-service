package com.example.approvalservice.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.approvalservice.entity.ApprovalRequests;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ApprovalRequestRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private ApprovalRequestRepository repository;

	@Test
	void checkObjectsLoaded() {
		assertThat(entityManager).isNotNull();
		assertThat(repository).isNotNull();
	}
	
	@Test
	void testFindByApprovalIdAndRequestId() {
		ApprovalRequests request = new ApprovalRequests();
		request.setRequestId("request1");
		
		String approvalId = entityManager.persistAndGetId(request, String.class);
		entityManager.flush();
		
		Optional<ApprovalRequests> optionalReq = repository.findByApprovalIdAndRequestId(approvalId, "request1");
		ApprovalRequests found = optionalReq.get();
		assertTrue(optionalReq.isPresent());
		assertNotNull(found, "ApprovalRequest is null");
		assertEquals("request1", found.getRequestId(), "RequestId not matched");
	}

}
