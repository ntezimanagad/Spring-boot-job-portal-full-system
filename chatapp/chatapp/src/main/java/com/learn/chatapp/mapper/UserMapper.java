package com.learn.chatapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.learn.chatapp.dto.UserDto;
import com.learn.chatapp.dto.UserRequest;
import com.learn.chatapp.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequest userRequest);
}
