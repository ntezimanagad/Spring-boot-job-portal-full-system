package com.learn.chatapp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.learn.chatapp.dto.JobPostDto;
import com.learn.chatapp.response.ApiResponse;
import com.learn.chatapp.services.JobPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobpost")
public class JobPostController {
    private final JobPostService jPostService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<JobPostDto>> create(@RequestBody JobPostDto jDto) {
        JobPostDto jPost = jPostService.createJob(jDto);
        ApiResponse<JobPostDto> response = ApiResponse.<JobPostDto>builder()
                .status("success")
                .message("Successfull saved")
                .data(jPost)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<JobPostDto>> update(
            @RequestBody JobPostDto jobPostDto,
            @PathVariable Long id) {

        System.out.println("Updating applicant with ID: " + id);
        JobPostDto jobPostDto2 = jPostService.update(jobPostDto, id);
        System.out.println("Updated data: " + jobPostDto2);

        ApiResponse<JobPostDto> response = ApiResponse.<JobPostDto>builder()
                .status("success")
                .message("Update successful")
                .data(jobPostDto2)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        jPostService.deleteJobPost(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get")
    public ResponseEntity<List<JobPostDto>> getAll() {
        return ResponseEntity.ok(jPostService.getPost());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<JobPostDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobPostDto> page2 = jPostService.getPost(pageable);
        return ResponseEntity.ok(page2);
    }
}
