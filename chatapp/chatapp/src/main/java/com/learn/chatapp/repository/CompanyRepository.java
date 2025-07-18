package com.learn.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.chatapp.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
