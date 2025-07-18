package com.learn.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.chatapp.dto.JobApplicationDto;
import com.learn.chatapp.model.JobApplication;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {
    @Mapping(target = "applicantId", ignore = true)
    @Mapping(target = "jobPostId", ignore = true)
    JobApplicationDto toDto(JobApplication jobApplication);

    @Mapping(target = "applicant", ignore = true)
    @Mapping(target = "jobPost", ignore = true)
    JobApplication toEntity(JobApplicationDto jobApplicationDto);
}
