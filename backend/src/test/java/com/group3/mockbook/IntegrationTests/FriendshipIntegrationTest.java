package com.group3.mockbook.IntegrationTests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.mockbook.dto.Friendship.FriendRequestDto;
import com.group3.mockbook.model.Friendship;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@Sql({ "/test-data.sql" })
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration
@AutoConfigureMockMvc
public class FriendshipIntegrationTest {

    @Autowired
    private MockMvc api;

    @Test
    @WithMockUser
    public void testCreateFriendRequest() throws Exception {
        FriendRequestDto newRequest = new FriendRequestDto(3);

        String newRequestJson = new ObjectMapper().writeValueAsString(newRequest);

        api.perform(post("/friendships")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testAcceptFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(put("/friendships/" + requestId + "/accept"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testRejectFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(put("/friendships/" + requestId + "/reject"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(delete("/friendships/" + requestId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void testGetFriendRequest() throws Exception {
        Integer requestId = 1; // Example request ID
        api.perform(get("/friendships/" + requestId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testGetFriendships() throws Exception {
        MvcResult resp = api.perform(get("/friendships"))
            .andExpect(status().isOk())
            .andReturn();
        String json = resp.getResponse().getContentAsString();
        List<Friendship> friends = new ObjectMapper().readValue(json, new TypeReference<List<Friendship>>() {});
        assertEquals(friends.size(), 2);
    }
    
}