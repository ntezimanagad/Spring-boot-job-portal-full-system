package com.learn.chatapp.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.learn.chatapp.model.Otp;
import com.learn.chatapp.repository.OtpRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private static final int EXPIRE_IN = 5;

    public void generateOtp(String email, String Purpose) {
        String otpCode = String.format("%06d", new Random().nextInt(1000000));
        Instant now = Instant.now();
        Instant expired = now.plus(EXPIRE_IN, ChronoUnit.MINUTES);

        deleteOtp(email, Purpose);

        Otp otp = new Otp(null, email, otpCode, now, expired, Purpose);

        otpRepository.save(otp);

        emailService.sendEmail(email, "Your OTP Code", "Your OTP Code is " + otpCode);
    }

    public boolean validateOtp(String email, String otpCode, String Purpose) {
        Optional<Otp> otpOpt = otpRepository.findByEmailAndPurpose(email, Purpose);
        if (otpOpt.isEmpty()) {
            return false;
        }

        Otp otp = otpOpt.get();

        boolean isExpired = otp.getExpiration().isBefore(Instant.now());
        boolean isMatch = otp.getOtpCode().equals(otpCode);

        if (!isMatch || isExpired) {
            deleteOtp(email, Purpose);
            return false;
        }

        deleteOtp(email, Purpose);
        return true;
    }

    @Transactional
    public void deleteOtp(String email, String purpose) {
        otpRepository.findByEmailAndPurpose(email, purpose)
                .ifPresent(otpRepository::delete);
    }
}
