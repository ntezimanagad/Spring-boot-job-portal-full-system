package com.learn.chatapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobPostDto {
    private Long id;
    private Long companyId;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private String isApproved;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private List<JobApplicationDto> applications;
}
