package com.group3.mockbook.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.mockbook.dto.RegistrationRequest;
import com.group3.mockbook.dto.LoginRequest;
import com.group3.mockbook.dto.LoginResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({ "/test-data.sql" })
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc api;

    @LocalServerPort
    private int port;

    @Test
    public void testLoginValid() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user1@example.com", "password1");

        MvcResult loginResult = api.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();
        LoginResponse loginResponse = new ObjectMapper().readValue(loginResult.getResponse().getContentAsString(), LoginResponse.class);
        assertNotNull(loginResponse.getToken());
    }

    @Test
    public void testLoginInvalidUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("IdontExist@example.com", "password1");

        api.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginInvalidPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user1@example.com", "notmypassword");

        api.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRegisterValidUser() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("newuser@example.com");
        registrationRequest.setPassword("password");
        registrationRequest.setUsername("newuser");
        registrationRequest.setSecurityQuestion("What is your favorite color?");
        registrationRequest.setSecurityAnswer("Blue");
    
        api.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated());
    
    }

}
