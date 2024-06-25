package com.group3.mockbook.utilities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory.class)
public @interface WithMockUser {
    int userId() default 1;
    String username() default "username";
    String password() default "password";

    String[] roles() default {"USER"};
}
