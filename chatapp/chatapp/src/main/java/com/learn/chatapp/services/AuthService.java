package com.learn.chatapp.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.learn.chatapp.dto.UserDto;
import com.learn.chatapp.dto.UserRequest;
import com.learn.chatapp.exception.DuplicateUserException;
import com.learn.chatapp.exception.InvalidOtpException;
import com.learn.chatapp.jwt.JwtUtil;
//import com.learn.chatapp.mapper.UserMapper;
import com.learn.chatapp.model.Role;
import com.learn.chatapp.model.User;
import com.learn.chatapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    // private final UserMapper mapper;

    public void registerUser(UserRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            throw new DuplicateUserException("User with email " + request.getEmail() + " Exists");
        }
        otpService.generateOtp(request.getEmail(), "REGISTER");
    }

    public String confirmRegistration(UserRequest request, String otpCode) {
        boolean validOtp = otpService.validateOtp(request.getEmail(), otpCode, "REGISTER");
        if (!validOtp) {
            throw new InvalidOtpException("OTP expired or invalid");
        }
        otpService.deleteOtp(request.getEmail(), "REGISTER");

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.APPLICANT);
        user = userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public void loginUser(UserRequest userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User Not found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Passsword Combination Failed");
        }
        otpService.generateOtp(userDTO.getEmail(), "LOGIN");
    }

    // Validate login OTP and generate JWT token
    public String confirmLogin(UserRequest request, String otpCode) {
        boolean validOtp = otpService.validateOtp(request.getEmail(), otpCode, "LOGIN");
        if (!validOtp) {
            throw new RuntimeException("OTP expired or invalid");
        }
        otpService.deleteOtp(request.getEmail(), "LOGIN");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}
