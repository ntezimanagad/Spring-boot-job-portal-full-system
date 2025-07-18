package com.learn.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDto {
    private Long id;
    private Long userId;
    private String companyName;
    private String logoPath;
    private String description;
    private String website;
    private String approved;
}
