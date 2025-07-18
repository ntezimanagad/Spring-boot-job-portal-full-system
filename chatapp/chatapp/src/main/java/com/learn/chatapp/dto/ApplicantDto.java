package com.learn.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicantDto {
    private Long id;
    private Long userId;
    private String fullName;
    private String phone;
    private String location;
    private String education;
    private String experience;
    private String skills;
    private String resumePath;
}
