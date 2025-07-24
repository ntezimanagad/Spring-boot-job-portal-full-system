package com.learn.chatapp.services;

import java.util.List;
import java.util.Optional;

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

import jakarta.transaction.Transactional;
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

    @Transactional
    public ApplicantDto updateApplicant(ApplicantDto dto, Long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Applicant Not Found"));

        applicantMapper.update(dto, applicant);
        applicant.setId(id);

        applicant = applicantRepository.save(applicant);

        return applicantMapper.toDto(applicant);
    }

    public void deleteApplicant(Long id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);
        if (applicant.isPresent()) {
            applicantRepository.deleteById(id);
        }
    }

    public List<ApplicantDto> getApplicant() {
        return applicantRepository.findAll()
                .stream()
                .map(applicantMapper::toDto)
                .toList();
    }

    public ApplicantDto getApplicant(Long id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return applicantMapper.toDto(applicant);
    }

    public Page<ApplicantDto> getApplicant(Pageable pageable) {
        return applicantRepository.findAll(pageable)
                .map(applicantMapper::toDto);
    }
}
