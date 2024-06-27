package com.group3.mockbook.IntegrationTests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.mockbook.dto.User.UserDTO;
import com.group3.mockbook.model.User;
import com.group3.mockbook.utilities.WithMockUser;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({ "/test-data.sql" })
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration
@AutoConfigureMockMvc
public class UserIntegrationTest {

        @Autowired
        private MockMvc api;

        @LocalServerPort
        private int port;

        @Test
        @WithMockUser
        public void testGetUsers() throws Exception {
                MvcResult resp = api.perform(get("/users"))
                                .andExpect(status().isOk())
                                .andReturn();
                String json = resp.getResponse().getContentAsString();
                List<User> users = new ObjectMapper().readValue(json, new TypeReference<List<User>>() {
                });
                assertEquals(3, users.size());
        }

        @Test
        public void testGetUsersNoAuth() throws Exception {
                api.perform(get("/users"))
                                .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser
        public void testCreateUser() throws Exception {
                UserDTO newUser = new UserDTO();
                newUser.setUsername("newUsername");
                newUser.setPassword("newPass");
                newUser.setEmail("altUser@example.com");
                newUser.setSecurityQuestion("What is your favorite book?");
                newUser.setSecurityAnswer("1984");
                String newUserJson = new ObjectMapper().writeValueAsString(newUser);

                MvcResult result = api.perform(post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newUserJson))
                                .andExpect(status().isCreated())
                                .andReturn();

                User responseUser = new ObjectMapper().readValue(result.getResponse().getContentAsString(), User.class);
                assertNotNull(responseUser.getId());

                // Verify the new user is in the database
                MvcResult getUserResult = api.perform(get("/users/" + responseUser.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

                // Verify the user was created correctly
                User getUserResponse = new ObjectMapper().readValue(getUserResult.getResponse().getContentAsString(),
                                User.class);
                assertNotNull(getUserResponse.getId());
                assertEquals("altUser@example.com", getUserResponse.getEmail());
                assertEquals("newUsername", getUserResponse.getUsername());
                assertEquals("What is your favorite book?", getUserResponse.getSecurityQuestion());
                assertEquals("1984", getUserResponse.getSecurityAnswer());
        }

        @Test
        public void testCreateUserNoAuth() throws Exception {
                User newUser = new User("altUser@example.com", "altUser", "newPass", "What is your favorite book?",
                                "1984");
                String newUserJson = new ObjectMapper().writeValueAsString(newUser);

                api.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(newUserJson))
                                .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser
        public void testUpdateUser() throws Exception {
                // Get the user to update
                UserDTO updatedUser = new UserDTO();
                updatedUser.setEmail("updatedEmail@dal.ca");
                updatedUser.setId(2);
                updatedUser.setUsername("updatedUsername");
                updatedUser.setPassword("updatedPassword");
                updatedUser.setSecurityQuestion("What is your favorite book?");
                updatedUser.setSecurityAnswer("1984");

                String updatedUserJson = new ObjectMapper().writeValueAsString(updatedUser);

                // Update the user
                MvcResult result = api.perform(put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedUserJson))
                                .andExpect(status().isOk())
                                .andReturn();

                // Verify the user was updated
                User responseUser = new ObjectMapper().readValue(result.getResponse().getContentAsString(), User.class);

                assertEquals(updatedUser.getEmail(), responseUser.getEmail());
                assertEquals(updatedUser.getId(), responseUser.getId());
                assertEquals(updatedUser.getSecurityAnswer(), responseUser.getSecurityAnswer());
                assertEquals(updatedUser.getUsername(), responseUser.getUsername());
        }

        @Test
        public void testUpdateUserNoAuth() throws Exception {
                // Get the user to update
                User updatedUser = new User("newuser@example.com", "newuser", "newpassword",
                                "What is your favorite book?",
                                "1984");
                updatedUser.setId(2);
                String updatedUserJson = new ObjectMapper().writeValueAsString(updatedUser);

                // Update the user
                api.perform(put("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatedUserJson))
                                .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser
        public void testDeleteUser() throws Exception {
                api.perform(get("/users/1"))
                                .andExpect(status().isOk());

                api.perform(delete("/users/1"))
                                .andExpect(status().isNoContent());

                api.perform(get("/users/1"))
                                .andExpect(status().isNotFound());
        }

        @Test
        public void testDeleteUserNoAuth() throws Exception {
                api.perform(delete("/users/1"))
                                .andExpect(status().isForbidden());
        }

}
