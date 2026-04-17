package com.example.reset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reset.service.PasswordService;

@RestController
@RequestMapping("/api")
public class ResetController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/forgot-password")
    public String forgot(@RequestParam String email) {
        return "Your OTP is: " + passwordService.generateOtp(email);
    }

    @PostMapping("/reset-password")
    public String reset(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        boolean success = passwordService.verifyAndReset(email, otp, newPassword);
        return success ? "Success: Password reset!" : "Error: Invalid or expired OTP!";
    }
}
