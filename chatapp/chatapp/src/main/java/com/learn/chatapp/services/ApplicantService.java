package com.learn.chatapp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.learn.chatapp.dto.ApplicantDto;
import com.learn.chatapp.exception.UserNotFoundException;
import com.learn.chatapp.mapper.ApplicantMapper;
import com.learn.chatapp.model.Applicant;
import com.learn.chatapp.model.User;
import com.learn.chatapp.repository.ApplicantRepository;
import com.learn.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicantService {
    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;

    public ApplicantDto createApplicant(ApplicantDto applicantDto) {
        Applicant applicant = applicantMapper.toEntity(applicantDto);

        User user = userRepository.findById(applicantDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("user not found"));
        applicant.setUser(user);

        Applicant saved = applicantRepository.save(applicant);
        return applicantMapper.toDto(saved);
    }

    public List<ApplicantDto> getApplicant() {
        return applicantRepository.findAll()
                .stream()
                .map(applicantMapper::toDto)
                .toList();
    }

    public Page<ApplicantDto> getApplicant(Pageable pageable) {
        return applicantRepository.findAll(pageable)
                .map(applicantMapper::toDto);
    }
}
