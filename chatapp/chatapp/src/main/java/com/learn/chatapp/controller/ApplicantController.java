package com.learn.chatapp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.chatapp.dto.ApplicantDto;
import com.learn.chatapp.services.ApplicantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applicant")
public class ApplicantController {
    private final ApplicantService applicantService;

    @PostMapping("/create")
    public ResponseEntity<?> createApplicant(@RequestBody ApplicantDto applicantDto) {
        applicantService.createApplicant(applicantDto);
        return ResponseEntity.ok("created");
    }

    @GetMapping("/get")
    public ResponseEntity<List<ApplicantDto>> getAll() {
        return ResponseEntity.ok(applicantService.getApplicant());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ApplicantDto>> getAllByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApplicantDto> page2 = applicantService.getApplicant(pageable);
        return ResponseEntity.ok(page2);
    }
}
