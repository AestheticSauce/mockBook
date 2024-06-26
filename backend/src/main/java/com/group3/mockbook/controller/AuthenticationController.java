package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.group3.mockbook.dto.RegistrationRequestDto;
import com.group3.mockbook.model.User;
import com.group3.mockbook.dto.LoginResponseDto;
import com.group3.mockbook.security.JwtIssuer;
import com.group3.mockbook.security.UserPrincipal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtIssuer jwtIssuer;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponseDto postLoginRequest(@RequestBody @Validated RegistrationRequestDto request) {
        
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        var principal = (UserPrincipal) auth.getPrincipal();

        String token = jwtIssuer.issue(principal.getId(), principal.getUsername(), principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        // Implementation goes here
        return new LoginResponseDto(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void postRegisterRequest(@RequestBody @Validated RegistrationRequestDto request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setSecurity_question(request.getSecurityQuestion());
        user.setSecurity_answer(request.getSecurityAnswer());
        user.setRole("USER");
        user.setEnabled(true);
        userService.saveUser(user);
    }

}
