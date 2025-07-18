package com.learn.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.chatapp.model.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

}