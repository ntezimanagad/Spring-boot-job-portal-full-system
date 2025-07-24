package com.learn.chatapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.learn.chatapp.dto.JobPostDto;
import com.learn.chatapp.exception.CampanyNotFoundException;
import com.learn.chatapp.exception.UserNotFoundException;
import com.learn.chatapp.mapper.JobPostMapper;
import com.learn.chatapp.model.Company;
import com.learn.chatapp.model.JobPost;
import com.learn.chatapp.repository.CompanyRepository;
import com.learn.chatapp.repository.JobPostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobPostService {
    private final JobPostRepository jRepository;
    private final CompanyRepository cRepository;
    private final JobPostMapper jMapper;

    public JobPostDto createJob(JobPostDto jDto) {
        JobPost jPost = jMapper.toEntity(jDto);
        Company company = cRepository.findById(jDto.getCompanyId())
                .orElseThrow(() -> new CampanyNotFoundException("Campany Not found"));
        jPost.setCompany(company);

        JobPost saved = jRepository.save(jPost);
        return jMapper.toDto(saved);
    }

    public JobPostDto update(JobPostDto jobPostDto, Long id) {
        JobPost jobPost = jRepository.findById(id)
                .orElseThrow(() -> new CampanyNotFoundException("campany not found"));
        jMapper.update(jobPostDto, jobPost);
        jobPost.setId(id);
        jobPost.setCompany(jobPost.getCompany());
        jobPost = jRepository.save(jobPost);
        return jMapper.toDto(jobPost);
    }

    public JobPostDto updateStatus(JobPostDto jobPostDto, Long id) {
        JobPost jobPost = jRepository.findById(id)
                .orElseThrow(() -> new CampanyNotFoundException("campany not found"));
        jobPost.setId(id);
        jobPost.setIsApproved(jobPostDto.getIsApproved());
        jobPost = jRepository.save(jobPost);
        return jMapper.toDto(jobPost);
    }

    public void deleteJobPost(Long id) {
        Optional<JobPost> jobPost = jRepository.findById(id);
        if (jobPost.isPresent()) {
            jRepository.deleteById(id);
        }
    }

    public List<JobPostDto> getPost() {
        return jRepository.findAll()
                .stream()
                .map(jMapper::toDto)
                .toList();
    }

    public JobPostDto getJobById(Long id) {
        JobPost jPost = jRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Job not found"));
        return jMapper.toDto(jPost);
    }

    public Page<JobPostDto> getPost(Pageable pageable) {
        return jRepository.findAll(pageable)
                .map(jMapper::toDto);
    }
}
