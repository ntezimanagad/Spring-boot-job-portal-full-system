package com.learn.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.learn.chatapp.dto.ApplicantDto;
import com.learn.chatapp.model.Applicant;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {
    @Mapping(source = "user.id", target = "userId")
    ApplicantDto toDto(Applicant applicant);

    @Mapping(target = "user", ignore = true)
    Applicant toEntity(ApplicantDto applicantDto);

    @Mapping(target = "user", ignore = true)
    void update(ApplicantDto dto, @MappingTarget Applicant applicant);
}
