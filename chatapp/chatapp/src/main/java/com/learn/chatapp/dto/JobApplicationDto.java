package com.learn.chatapp.dto;

import java.time.LocalDateTime;

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
    @Builder.Default
    private LocalDateTime appliedAt = LocalDateTime.now();
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;
}
