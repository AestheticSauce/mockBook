package com.group3.mockbook.utilities;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.security.UserPrincipalAuthenticationToken;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser>{

    @Override
    public SecurityContext createSecurityContext(WithMockUser annotation) {
        List<SimpleGrantedAuthority> authorities = Arrays.stream(annotation.roles()).map(SimpleGrantedAuthority::new).toList();
        UserPrincipal principal = new UserPrincipal();
        principal.setId(annotation.userId());
        principal.setUsername(annotation.username());
        principal.setPassword(annotation.password());
        principal.setAuthorities(authorities);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserPrincipalAuthenticationToken(principal));
        return context;
    }

}
