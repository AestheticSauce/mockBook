package com.group3.mockbook.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group3.mockbook.service.UserService;

@Component
public class JwtToPrincipalConverter{

    @Autowired
    UserService userService;

    public UserPrincipal convert(DecodedJWT token){
        UserPrincipal principal = new UserPrincipal();
        principal.setId(Integer.valueOf(token.getSubject()));
        principal.setUsername(token.getClaim("email").asString());
        principal.setAuthorities(convertToAuthorities(token));
        return principal;
    }

    private List<SimpleGrantedAuthority> convertToAuthorities(DecodedJWT token){
        Claim claim = token.getClaim("roles");
        if (claim.isNull()) {
            return List.of();
        }
        return claim.asList(SimpleGrantedAuthority.class);
    }

}