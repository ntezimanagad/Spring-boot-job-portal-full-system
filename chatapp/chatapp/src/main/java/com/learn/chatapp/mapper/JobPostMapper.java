package com.learn.chatapp.mapper;

import com.learn.chatapp.dto.JobPostDto;
import com.learn.chatapp.model.JobPost;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { JobApplicationMapper.class })
public interface JobPostMapper {

    @Mapping(source = "company.id", target = "companyId")
    JobPostDto toDto(JobPost jobPost);

    @Mapping(source = "companyId", target = "company.id")
    JobPost toEntity(JobPostDto jobPostDto);

    @Mapping(target = "company", ignore = true)
    void update(JobPostDto jPostDto, @MappingTarget JobPost jobPost);
}
