package com.learn.chatapp.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Otp {
    private Long id;
    private String email;
    private String otpCode;
    private Instant createdAt;
    private Instant expiration;
    private String purpose;
}
