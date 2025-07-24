package com.learn.chatapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learn.chatapp.dto.JobApplicationDto;
import com.learn.chatapp.exception.CampanyNotFoundException;
import com.learn.chatapp.exception.JobPostNotFoundException;
import com.learn.chatapp.exception.UserNotFoundException;
import com.learn.chatapp.mapper.JobApplicationMapper;
import com.learn.chatapp.model.Applicant;
import com.learn.chatapp.model.JobApplication;
import com.learn.chatapp.model.JobPost;
import com.learn.chatapp.model.User;
import com.learn.chatapp.repository.ApplicantRepository;
import com.learn.chatapp.repository.JobApplicationRepository;
import com.learn.chatapp.repository.JobPostRepository;
import com.learn.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository jRepository;
    private final JobPostRepository jPostRepository;
    private final ApplicantRepository aRepository;
    private final UserRepository userRepository;
    private final JobApplicationMapper jMapper;
    private final EmailService emailService;

    public JobApplicationDto applay(JobApplicationDto jDto) {
        Optional<JobApplication> jobApplication = jRepository.findByJobPostIdAndApplicantId(jDto.getJobPostId(),
                jDto.getApplicantId());
        if (jobApplication.isPresent()) {
            throw new RuntimeException("Already Applied to this Job");
        }

        JobApplication application = jMapper.toEntity(jDto);
        Applicant applicant = aRepository.findById(jDto.getApplicantId())
                .orElseThrow(() -> new UsernameNotFoundException("Applicant Not Found"));
        application.setApplicant(applicant);

        application.setCampanyId(jDto.getCampanyId());

        JobPost jPost = jPostRepository.findById(jDto.getJobPostId())
                .orElseThrow(() -> new JobPostNotFoundException("JobPost Not Found"));
        application.setJobPost(jPost);

        JobApplication saved = jRepository.save(application);
        return jMapper.toDto(saved);
    }

    public JobApplicationDto update(JobApplicationDto jobApplicationDto, Long id) {
        JobApplication jobApplication = jRepository.findById(id)
                .orElseThrow(() -> new CampanyNotFoundException("campany not found"));
        jMapper.update(jobApplicationDto, jobApplication);
        jobApplication.setId(id);
        jobApplication = jRepository.save(jobApplication);
        return jMapper.toDto(jobApplication);
    }

    public JobApplicationDto updateStatus(JobApplicationDto jobApplicationDto, Long id, Long applicantId) {
        JobApplication jobApplication = jRepository.findById(id)
                .orElseThrow(() -> new CampanyNotFoundException("campany not found"));
        User user = userRepository.findById(applicantId)
                .orElseThrow(() -> new UserNotFoundException("applicant not found"));
        emailService.sendEmail(user.getEmail(), "Your Application Status",
                "your Status" + jobApplicationDto.getStatus());
        jobApplication.setId(id);
        jobApplication.setStatus(jobApplicationDto.getStatus());
        jobApplication = jRepository.save(jobApplication);
        return jMapper.toDto(jobApplication);
    }

    public void delete(Long id) {
        Optional<JobApplication> job = jRepository.findById(id);
        if (job.isPresent()) {
            jRepository.deleteById(id);
        }
    }

    public List<JobApplicationDto> getApplication() {
        return jRepository.findAll()
                .stream()
                .map(jMapper::toDto)
                .toList();
    }

    public Page<JobApplicationDto> getApplication(Pageable pageable) {
        return jRepository.findAll(pageable)
                .map(jMapper::toDto);
    }

}
