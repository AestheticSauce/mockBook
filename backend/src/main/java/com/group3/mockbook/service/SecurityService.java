package com.group3.mockbook.service;

import com.group3.mockbook.model.User;
import com.group3.mockbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateSecurityAnswer(String email, String securityAnswer) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getSecurity_answer().equals(securityAnswer);
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }
}
