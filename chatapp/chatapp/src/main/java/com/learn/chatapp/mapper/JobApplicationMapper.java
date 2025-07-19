package com.learn.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.learn.chatapp.dto.JobApplicationDto;
import com.learn.chatapp.model.JobApplication;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {
    // @Mapping(target = "applicantId", ignore = true)
    // @Mapping(target = "jobPostId", ignore = true)
    @Mapping(source = "applicant.id", target = "applicantId")
    @Mapping(source = "jobPost.id", target = "jobPostId")
    JobApplicationDto toDto(JobApplication jobApplication);

    @Mapping(target = "applicant", ignore = true)
    @Mapping(target = "jobPost", ignore = true)
    JobApplication toEntity(JobApplicationDto jobApplicationDto);

    @Mapping(source = "applicantId", target = "applicant.id")
    @Mapping(source = "jobPostId", target = "jobPost.id")
    void update(JobApplicationDto jApplicationDto, @MappingTarget JobApplication jobApplication);
}
