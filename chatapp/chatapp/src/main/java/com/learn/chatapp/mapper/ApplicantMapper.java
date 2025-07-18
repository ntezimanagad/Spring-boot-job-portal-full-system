package com.learn.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.chatapp.dto.ApplicantDto;
import com.learn.chatapp.model.Applicant;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {
    @Mapping(target = "userId", ignore = true)
    ApplicantDto toDto(Applicant applicant);

    @Mapping(target = "user", ignore = true)
    Applicant toEntity(ApplicantDto applicantDto);
}
