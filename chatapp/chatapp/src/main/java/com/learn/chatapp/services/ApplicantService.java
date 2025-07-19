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
        // Load existing applicant from DB
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Applicant Not Found"));

        // Map only fields that are allowed to change
        applicantMapper.update(dto, applicant);

        // Optional: ensure the ID remains consistent
        applicant.setId(id); // in case of mismatch
        // applicant.setUser(applicant.getUser()); // not needed, just ensure you don't
        // nullify

        // Save updated entity
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

    public Page<ApplicantDto> getApplicant(Pageable pageable) {
        return applicantRepository.findAll(pageable)
                .map(applicantMapper::toDto);
    }
}
