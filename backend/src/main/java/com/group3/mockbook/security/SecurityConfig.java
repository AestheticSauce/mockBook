package com.group3.mockbook.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.group3.mockbook.service.Implementation.CustomUserDetailService;
import com.group3.mockbook.service.Implementation.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserServiceImpl userDetailsService;

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	CustomUserDetailService customUserDetailService;


	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement(
				(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.csrf((csrf)->csrf.disable())
			.cors((cors) -> cors.disable())
			.formLogin((formLogin) -> formLogin.disable())
			.authorizeHttpRequests((registry) -> registry
				.requestMatchers("/auth/login", "/auth/register", "/auth/password-reset")
				.permitAll()
				.anyRequest().authenticated()
				)
			.cors(
				(cors) -> cors
					.configurationSource((request) -> {
						var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
						corsConfiguration.setAllowedOriginPatterns(java.util.List.of("*"));
						corsConfiguration.setAllowedMethods(java.util.List.of("*"));
						corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
						return corsConfiguration;
					})
			)

			;
		return http.build();
    }

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder manager = http.getSharedObject(AuthenticationManagerBuilder.class);
		manager.userDetailsService((customUserDetailService)).passwordEncoder(passwordEncoder());
		return manager.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}