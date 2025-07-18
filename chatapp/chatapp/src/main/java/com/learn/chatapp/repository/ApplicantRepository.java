package com.learn.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.chatapp.model.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}
