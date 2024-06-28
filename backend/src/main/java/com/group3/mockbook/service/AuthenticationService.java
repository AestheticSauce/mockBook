package com.group3.mockbook.service;

import com.group3.mockbook.dto.Authorization.LoginResponseDto;
import com.group3.mockbook.dto.Authorization.RegistrationRequestDto;

public interface AuthenticationService {

    public LoginResponseDto login(String email, String password);

    public void register(RegistrationRequestDto request);
}