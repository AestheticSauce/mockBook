package com.group3.mockbook.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.List;

@Component
public class JwtIssuer {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String issue(int id, String email, List<String> roles) {
        // Implementation goes here
        return JWT.create()
            .withSubject(String.valueOf(id))
            .withClaim("roles", roles)
            .withExpiresAt(Instant.now().plusMillis(expiration))
            .withClaim("email", email)
            .sign(Algorithm.HMAC256(secret));
    }
}
