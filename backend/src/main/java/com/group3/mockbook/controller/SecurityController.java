package com.group3.mockbook.controller;

import com.group3.mockbook.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @PostMapping("/validate-security-question")
    public ResponseEntity<?> validateSecurityQuestion(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String securityAnswer = payload.get("securityAnswer");
        boolean isValid = securityService.validateSecurityAnswer(email, securityAnswer);
        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String newPassword = payload.get("newPassword");
        securityService.resetPassword(email, newPassword);
        return ResponseEntity.ok().build();
    }
}

