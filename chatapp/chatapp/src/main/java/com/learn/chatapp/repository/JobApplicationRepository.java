package com.learn.chatapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.chatapp.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByApplicantId(Long id, Pageable pageable);

}