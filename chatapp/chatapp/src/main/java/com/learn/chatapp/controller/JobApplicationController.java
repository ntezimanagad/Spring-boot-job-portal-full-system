package com.learn.chatapp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.chatapp.dto.JobApplicationDto;
import com.learn.chatapp.exception.UserNotFoundException;
import com.learn.chatapp.mapper.JobApplicationMapper;
import com.learn.chatapp.model.JobApplication;
import com.learn.chatapp.model.User;
import com.learn.chatapp.repository.JobApplicationRepository;
import com.learn.chatapp.repository.UserRepository;
import com.learn.chatapp.response.ApiResponse;
import com.learn.chatapp.services.JobApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/application")
public class JobApplicationController {
    private final JobApplicationService jApplicationService;
    private final UserRepository userRepository;
    private final JobApplicationRepository jApplicationRepository;
    private final JobApplicationMapper jMapper;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<JobApplicationDto>> apply(@RequestBody JobApplicationDto jDto) {
        JobApplicationDto dto = jApplicationService.applay(jDto);
        return ResponseEntity.ok(
                ApiResponse.<JobApplicationDto>builder()
                        .status("success")
                        .message("successfull")
                        .data(dto)
                        .build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<JobApplicationDto>> update(
            @RequestBody JobApplicationDto jobApplicationDto,
            @PathVariable Long id) {

        System.out.println("Updating applicant with ID: " + id);
        JobApplicationDto jobApplicationDto2 = jApplicationService.update(jobApplicationDto, id);
        System.out.println("Updated data: " + jobApplicationDto2);

        ApiResponse<JobApplicationDto> response = ApiResponse.<JobApplicationDto>builder()
                .status("success")
                .message("Update successful")
                .data(jobApplicationDto2)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatestatus/{id}")
    public ResponseEntity<ApiResponse<JobApplicationDto>> updateStatus(
            @RequestBody JobApplicationDto jobApplicationDto,
            @PathVariable Long id,
            @RequestParam Long applicantId) {
        JobApplicationDto jDto = jApplicationService.updateStatus(jobApplicationDto, id, applicantId);
        ApiResponse<JobApplicationDto> response = ApiResponse.<JobApplicationDto>builder()
                .status("success")
                .message("successfull")
                .data(jDto)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        jApplicationService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get")
    public ResponseEntity<List<JobApplicationDto>> getAll() {
        return ResponseEntity.ok(jApplicationService.getApplication());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<JobApplicationDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobApplicationDto> page2 = jApplicationService.getApplication(pageable);
        return ResponseEntity.ok(page2);
    }

    @GetMapping("/myapplication")
    public ResponseEntity<?> getUserApplication(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<JobApplication> jobApp = jApplicationRepository.findByApplicantId(user.getId(), pageable);

        Page<JobApplicationDto> jPage = jobApp.map(jMapper::toDto);
        return ResponseEntity.ok(jPage);
    }

    @GetMapping("/appliedjob")
    public ResponseEntity<?> getAllAppliedJob(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        Pageable pageable = PageRequest.of(page, size);

        Page<JobApplication> jPage = jApplicationRepository.findByCampanyId(user.getId(), pageable);

        Page<JobApplicationDto> jPage1 = jPage.map(jMapper::toDto);
        return ResponseEntity.ok(jPage1);
    }
}
