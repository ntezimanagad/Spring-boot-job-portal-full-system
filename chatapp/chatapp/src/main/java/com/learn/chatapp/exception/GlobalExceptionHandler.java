package com.learn.chatapp.exception;

import com.learn.chatapp.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(DuplicateUserException.class)
        public ResponseEntity<ApiResponse<String>> handleDuplicateUser(DuplicateUserException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                                ApiResponse.<String>builder()
                                                .status("error")
                                                .message(e.getMessage())
                                                .data(null)
                                                .build());
        }

        @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<ApiResponse<String>> handleUserNotFound(UserNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                ApiResponse.<String>builder()
                                                .status("error")
                                                .message(e.getMessage())
                                                .data(null)
                                                .build());
        }

        @ExceptionHandler(InvalidOtpException.class)
        public ResponseEntity<ApiResponse<String>> handleInvalidOtp(InvalidOtpException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                                ApiResponse.<String>builder()
                                                .status("error")
                                                .message(e.getMessage())
                                                .data(null)
                                                .build());
        }

        @ExceptionHandler(CampanyNotFoundException.class)
        public ResponseEntity<ApiResponse<String>> handleCompanyNotFound(CampanyNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                ApiResponse.<String>builder()
                                                .status("error")
                                                .message(e.getMessage())
                                                .data(null)
                                                .build());
        }

        @ExceptionHandler(JobPostNotFoundException.class)
        public ResponseEntity<ApiResponse<String>> handleJobNotFound(JobPostNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                ApiResponse.<String>builder()
                                                .status("error")
                                                .message(e.getMessage())
                                                .data(null)
                                                .build());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<String>> handleGeneric(Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                ApiResponse.<String>builder()
                                                .status("error")
                                                .message("An unexpected error occurred")
                                                .data(null)
                                                .build());
        }
}
