package com.example.approvalservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.approvalservice.entity.ApprovalRequests;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequests, String> {
	Optional<ApprovalRequests> findByApprovalIdAndRequestId(String approvalId, String requestId);
}
