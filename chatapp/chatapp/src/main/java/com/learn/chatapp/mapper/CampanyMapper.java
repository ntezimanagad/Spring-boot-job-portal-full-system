package com.learn.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.chatapp.dto.CompanyDto;
import com.learn.chatapp.model.Company;

@Mapper(componentModel = "spring")
public interface CampanyMapper {
    @Mapping(target = "userId", ignore = true)
    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);
}
