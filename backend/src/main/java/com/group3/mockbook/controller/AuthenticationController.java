package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.group3.mockbook.dto.Authorization.LoginResponseDto;
import com.group3.mockbook.dto.Authorization.PasswordResetDto;
import com.group3.mockbook.dto.Authorization.RegistrationRequestDto;
import com.group3.mockbook.model.User;
import com.group3.mockbook.security.JwtIssuer;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.service.AuthenticationService;
import com.group3.mockbook.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponseDto postLoginRequest(@RequestBody @Validated RegistrationRequestDto request) {
        return authenticationService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void postRegisterRequest(@RequestBody @Validated RegistrationRequestDto request) {
        authenticationService.register(request);
    }

    @PutMapping("/password-reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putPasswordReset(@RequestBody PasswordResetDto resetDto) {
        userService.resetPassword(resetDto.getEmail(), resetDto.getSecurityAnswer(), passwordEncoder.encode(resetDto.getNewPassword()));
    }
}
