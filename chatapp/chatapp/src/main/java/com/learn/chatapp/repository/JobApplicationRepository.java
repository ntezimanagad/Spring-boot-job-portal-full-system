package com.learn.chatapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.chatapp.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByApplicantId(Long id, Pageable pageable);

    Optional<JobApplication> findByJobPostId(Long jobPostId);

    Page<JobApplication> findByCampanyId(Long companyId, Pageable pageable);

    Optional<JobApplication> findByJobPostIdAndApplicantId(Long jobPostId, Long applicantId);

}