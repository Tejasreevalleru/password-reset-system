package com.example.reset.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.reset.model.User;
import com.example.reset.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
public class PasswordServiceTest {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testInvalidOtpFails() {
        // Test with a wrong OTP (e.g., '000000')
        boolean result = passwordService.verifyAndReset("test@example.com", "000000", "newPass123");
        assertFalse(result, "The reset should fail because the OTP is incorrect.");
    }

    @Test
    void testExpiredOtpFails() {
        // 1. Manually set an expired OTP for our test user
        User user = userRepository.findByEmail("test@example.com").get();
        user.setOtp("123456");
        user.setOtpExpiry(LocalDateTime.now().minusMinutes(10)); // Expired 10 mins ago
        userRepository.save(user);

        // 2. Try to reset with that expired OTP
        boolean result = passwordService.verifyAndReset("test@example.com", "123456", "newPass123");
        
        assertFalse(result, "The reset should fail because the OTP has expired.");
    }
}
