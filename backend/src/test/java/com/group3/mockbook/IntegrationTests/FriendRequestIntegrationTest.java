package com.group3.mockbook.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.mockbook.dto.FriendRequestDto;
import com.group3.mockbook.model.FriendRequest;
import com.group3.mockbook.utilities.WithMockUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql({ "/test-data.sql" })
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration
@AutoConfigureMockMvc
public class FriendRequestIntegrationTest {

    @Autowired
    private MockMvc api;

    @Test
    @WithMockUser
    public void testCreateFriendRequest() throws Exception {
        FriendRequestDto newRequest = new FriendRequestDto(3);

        String newRequestJson = new ObjectMapper().writeValueAsString(newRequest);

        api.perform(post("/friend-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testAcceptFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(put("/friend-requests/" + requestId + "/accept"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testRejectFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(put("/friend-requests/" + requestId + "/reject"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(delete("/friend-requests/" + requestId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void testGetFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(get("/friend-requests/" + requestId))
                .andExpect(status().isOk());
    }
}