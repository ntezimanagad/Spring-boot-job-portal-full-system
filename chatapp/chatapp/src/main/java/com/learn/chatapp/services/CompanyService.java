package com.learn.chatapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.learn.chatapp.dto.CompanyDto;
import com.learn.chatapp.exception.CampanyNotFoundException;
import com.learn.chatapp.exception.UserNotFoundException;
import com.learn.chatapp.mapper.CampanyMapper;
import com.learn.chatapp.model.Company;
import com.learn.chatapp.model.User;
import com.learn.chatapp.repository.CompanyRepository;
import com.learn.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final CampanyMapper campanyMapper;
    private final UserRepository userRepository;

    public CompanyDto create(CompanyDto companyDto) {
        Company company = campanyMapper.toEntity(companyDto);
        User user = userRepository.findById(companyDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        company.setUser(user);

        Company saved = companyRepository.save(company);
        return campanyMapper.toDto(saved);
    }

    public CompanyDto update(CompanyDto companyDto, Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CampanyNotFoundException("campany not found"));
        campanyMapper.update(companyDto, company);
        company.setId(id);
        company = companyRepository.save(company);
        return campanyMapper.toDto(company);
    }

    public void deleteCampany(Long id) {
        Optional<Company> comOptional = companyRepository.findById(id);
        if (comOptional.isPresent()) {
            companyRepository.deleteById(id);
        }
    }

    public List<CompanyDto> getCampany() {
        return companyRepository.findAll()
                .stream()
                .map(campanyMapper::toDto)
                .toList();
    }

    public CompanyDto getCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Companyy not found"));
        return campanyMapper.toDto(company);
    }

    public Page<CompanyDto> getCampany(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(campanyMapper::toDto);
    }
}
