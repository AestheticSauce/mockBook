package com.group3.mockbook.service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.group3.mockbook.dto.Authorization.LoginResponseDto;
import com.group3.mockbook.dto.Authorization.RegistrationRequestDto;
import com.group3.mockbook.model.User;
import com.group3.mockbook.security.JwtIssuer;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.service.AuthenticationService;
import com.group3.mockbook.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtIssuer jwtIssuer;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Override
    public LoginResponseDto login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(auth);

        var principal = (UserPrincipal) auth.getPrincipal();

        String token = jwtIssuer.issue(principal.getId(), principal.getUsername(), principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        // Implementation goes here
        return new LoginResponseDto(token);
    }

    @Override
    public void register(RegistrationRequestDto request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setSecurityQuestion(request.getSecurityQuestion());
        user.setSecurityAnswer(request.getSecurityAnswer());
        user.setRole("USER");
        user.setEnabled(true);
        userService.saveUser(user);
    }

    @Override
    public void resetPassword(String email, String securityAnswer, String newPassword) {
        User user = userService.getUserByEmail(email);

        if (user.getSecurityAnswer().equals(securityAnswer)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUser(user);
        } else{
            throw new BadCredentialsException("Security answer is incorrect");
        }
    }
    
}
