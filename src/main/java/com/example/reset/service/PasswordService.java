package com.example.reset.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reset.model.User;
import com.example.reset.repository.UserRepository;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepository;

    public String generateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String otp = String.format("%06d", new Random().nextInt(999999));
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5)); // Valid for 5 mins
        userRepository.save(user);
        return otp;
    }

    public boolean verifyAndReset(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getOtp() != null && user.getOtp().equals(otp) && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
            user.setPassword(newPassword);
            user.setOtp(null); // Clear OTP after success
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
