package com.learn.chatapp.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.learn.chatapp.dto.UserDto;
import com.learn.chatapp.dto.UserRequest;
import com.learn.chatapp.exception.DuplicateUserException;
import com.learn.chatapp.mapper.UserMapper;
import com.learn.chatapp.model.User;
import com.learn.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserRequest userRequest) {
        Optional<User> userOpt = userRepository.findByEmail(userRequest.getEmail());
        if (userOpt.isPresent()) {
            throw new DuplicateUserException("User Already Exist");
        }
        User user = userMapper.toEntity(userRequest);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public Page<UserDto> getUser(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }
}