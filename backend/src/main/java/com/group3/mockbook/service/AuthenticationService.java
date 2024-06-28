package com.group3.mockbook.service;

import com.group3.mockbook.dto.Authorization.LoginResponseDto;
import com.group3.mockbook.dto.Authorization.RegistrationRequestDto;
import com.group3.mockbook.dto.Authorization.SecurityQuestionDto;

public interface AuthenticationService {

    public LoginResponseDto login(String email, String password);

    public void register(RegistrationRequestDto request);

    public void resetPassword(String email, String securityAnswer, String newPassword);

    public SecurityQuestionDto getSecurityQuestion(String email);
}