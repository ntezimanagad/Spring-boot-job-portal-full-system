package com.learn.chatapp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.chatapp.dto.ApplicantDto;
import com.learn.chatapp.response.ApiResponse;
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

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ApplicantDto>> update(
            @RequestBody ApplicantDto applicantDto,
            @PathVariable Long id) {

        System.out.println("Updating applicant with ID: " + id);
        ApplicantDto updatedDto = applicantService.updateApplicant(applicantDto, id);
        System.out.println("Updated data: " + updatedDto);

        ApiResponse<ApplicantDto> response = ApiResponse.<ApplicantDto>builder()
                .status("success")
                .message("Update successful")
                .data(updatedDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        applicantService.deleteApplicant(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get")
    public ResponseEntity<List<ApplicantDto>> getAll() {
        return ResponseEntity.ok(applicantService.getApplicant());
    }

    @GetMapping("/getapplicantbyid/{id}")
    public ResponseEntity<ApplicantDto> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(applicantService.getApplicant(id));
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
