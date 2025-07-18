package com.learn.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.chatapp.model.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

}
