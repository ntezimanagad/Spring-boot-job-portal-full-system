package com.learn.chatapp.controller;

import com.learn.chatapp.response.ApiResponse;
import com.learn.chatapp.dto.UserDto;
import com.learn.chatapp.dto.UserRequest;
import com.learn.chatapp.services.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserRequest request) {
        authService.registerUser(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status("success")
                .message("OTP sent to email for registration")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/confirm-registration")
    public ResponseEntity<ApiResponse<UserDto>> confirmRegistration(
            @RequestBody UserRequest request,
            @RequestParam String otpCode) {
        UserDto userDto = authService.confirmRegistration(request, otpCode);
        ApiResponse<UserDto> response = ApiResponse.<UserDto>builder()
                .status("success")
                .message("User registered successfully")
                .data(userDto)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody UserRequest request) {
        authService.loginUser(request);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status("success")
                .message("OTP sent to email for login")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // @PostMapping("/confirm-login")
    // public ResponseEntity<ApiResponse<String>> confirmLogin(
    // @RequestBody UserRequest request,
    // @RequestParam String otpCode) {
    // String jwtToken = authService.confirmLogin(request, otpCode);
    // ApiResponse<String> response = ApiResponse.<String>builder()
    // .status("success")
    // .message("Login successful")
    // .data(jwtToken)
    // .build();
    // return ResponseEntity.status(HttpStatus.OK).body(response);
    // }
    @PostMapping("/confirm-login")
    public ResponseEntity<?> confirmLogin(
            @RequestBody UserRequest request,
            @RequestParam String otpCode) {
        return ResponseEntity.ok(authService.confirmLogin(request, otpCode));
    }
}
