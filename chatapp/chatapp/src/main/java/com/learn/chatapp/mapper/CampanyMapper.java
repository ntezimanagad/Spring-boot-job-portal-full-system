package com.learn.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.learn.chatapp.dto.CompanyDto;
import com.learn.chatapp.model.Company;

@Mapper(componentModel = "spring")
public interface CampanyMapper {
    @Mapping(source = "user.id", target = "userId")
    CompanyDto toDto(Company company);

    @Mapping(target = "user", ignore = true)
    Company toEntity(CompanyDto companyDto);

    @Mapping(target = "user", ignore = true)
    void update(CompanyDto companyDto, @MappingTarget Company company);
}
