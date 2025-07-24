package com.learn.chatapp.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learn.chatapp.model.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplicationDto {
    private Long id;
    private Long applicantId;
    private Long jobPostId;
    private Long campanyId;
    @Builder.Default
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime appliedAt = LocalDateTime.now();
    private ApplicationStatus status;
}
