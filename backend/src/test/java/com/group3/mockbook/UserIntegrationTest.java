package com.group3.mockbook;

import com.group3.mockbook.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql({"/test-data.sql"})
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testGetUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);
    }

    @Test
    public void testCreateUser() {
        User newUser = new User("newuser@example.com", "newuser", "newpassword", "What is your favorite book?", "1984");
        ResponseEntity<User> response = restTemplate.postForEntity("/users", newUser, User.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());

        // Verify the new user is in the database
        ResponseEntity<User> getUserResponse = restTemplate.getForEntity("/users/" + response.getBody().getId(), User.class);
        assertEquals(HttpStatus.OK, getUserResponse.getStatusCode());
        assertEquals("newuser", getUserResponse.getBody().getUsername());
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User("newuser@example.com", "newuser", "newpassword", "What is your favorite book?", "1984");
        updatedUser.setId(2);
        ResponseEntity<User> response = restTemplate.exchange("/users", HttpMethod.PUT, new HttpEntity<>(updatedUser), User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser.getEmail(), response.getBody().getEmail());
        assertEquals(updatedUser.getId(), response.getBody().getId());
        assertEquals(updatedUser.getSecurity_answer(), response.getBody().getSecurity_answer());
        assertEquals(updatedUser.getUsername(), response.getBody().getUsername());
        assertEquals(updatedUser.getPassword(), response.getBody().getPassword());
    }

    @Test
    public void testDeleteUser() {
        ResponseEntity<Void> delResponse = restTemplate.exchange("/users/1", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, delResponse.getStatusCode());
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/users/1", String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
