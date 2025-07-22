package com.learn.chatapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.chatapp.model.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    Page<JobPost> findById(Long id, Pageable pageable);

    Page<JobPost> findByCompanyId(Long id, Pageable pageable);

    Page<JobPost> findByIsApprovedTrue(Pageable pageable);

    Page<JobPost> findByIsApproved(String string, Pageable pageable);

}
