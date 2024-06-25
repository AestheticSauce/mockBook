package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group3.mockbook.dto.LoginRequest;
import com.group3.mockbook.dto.LoginResponse;
import com.group3.mockbook.model.User;
import com.group3.mockbook.security.JwtIssuer;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtIssuer jwtIssuer;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public LoginResponse postLoginRequest(@RequestBody @Validated LoginRequest request) {
        
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        var principal = (UserPrincipal) auth.getPrincipal();

        String token = jwtIssuer.issue(principal.getId(), principal.getUsername(), principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        // Implementation goes here
        return new LoginResponse(token);
    }

}
