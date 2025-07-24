package com.learn.chatapp.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.learn.chatapp.dto.ApplicantDto;
import com.learn.chatapp.dto.CompanyDto;
import com.learn.chatapp.exception.UserNotFoundException;
import com.learn.chatapp.mapper.CampanyMapper;
import com.learn.chatapp.model.Company;
import com.learn.chatapp.model.User;
import com.learn.chatapp.repository.CompanyRepository;
import com.learn.chatapp.repository.UserRepository;
import com.learn.chatapp.response.ApiResponse;
import com.learn.chatapp.services.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CampanyMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CompanyDto>> createCompany(@RequestBody CompanyDto companyDto) {
        CompanyDto saved = companyService.create(companyDto);

        ApiResponse<CompanyDto> response = ApiResponse.<CompanyDto>builder()
                .status("Success")
                .message("Company Submitted successfull")
                .data(saved)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

        // JobPostDto saved =
        // jobPostService.createJob(jobPostDto);
        // return ResponseEntity.ok(
        // ApiResponse.<JobPostDto>builder()
        // .status("success")
        // .message("Job post created successfully")
        // .data(saved)
        // .build()
        // );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CompanyDto>> update(
            @RequestBody CompanyDto companyDto,
            @PathVariable Long id) {
        CompanyDto companyDto2 = companyService.update(companyDto, id);
        ApiResponse<CompanyDto> response = ApiResponse.<CompanyDto>builder()
                .status("success")
                .message("successfull updated")
                .data(companyDto2)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        companyService.deleteCampany(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get")
    public ResponseEntity<List<CompanyDto>> getAll() {
        return ResponseEntity.ok(companyService.getCampany());
    }

    @GetMapping("/getcampanybyid/{id}")
    public ResponseEntity<CompanyDto> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompany(id));
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CompanyDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyDto> page2 = companyService.getCampany(pageable);
        return ResponseEntity.ok(page2);
    }

    public ResponseEntity<CompanyDto> getCompanyByLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Company company = companyRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Company not found for user ID: " + user.getId()));

        CompanyDto companyDto = mapper.toDto(company);

        return ResponseEntity.ok(companyDto);
    }
}
