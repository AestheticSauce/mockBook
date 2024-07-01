package com.group3.mockbook.IntegrationTests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group3.mockbook.dto.Post.CreatePostDto;
import com.group3.mockbook.dto.Post.UpdatePostDto;
import com.group3.mockbook.model.Post;
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
public class PostIntegrationTest {

    @Autowired
    private MockMvc api;

    @LocalServerPort
    private int port;

    @Test
    @WithMockUser
    public void shouldCreateNewPostSuccessfully() throws Exception {
        // Given
        CreatePostDto newPost = new CreatePostDto();
        newPost.setTitle("Test Post");
        newPost.setContent("This is a test post content.");

        String newPostJson = new ObjectMapper().writeValueAsString(newPost);
        
        // When & Then
        this.api.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPostJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.content").value("This is a test post content."));
    }

    @Test
    @WithMockUser
    public void shouldUpdatePostSuccessfully() throws Exception {
        UpdatePostDto updatedPost = new UpdatePostDto();
        updatedPost.setTitle("Updated Test Post");
        updatedPost.setContent("Updated content.");

        String updatedPostJson = new ObjectMapper().writeValueAsString(updatedPost);

        // When & Then
        this.api.perform(put("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedPostJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Test Post"))
                .andExpect(jsonPath("$.content").value("Updated content."));
    }

    @Test
    @WithMockUser
    public void shouldDeletePostSuccessfully() throws Exception {
        // Given
        int postId = 1;

        // When & Then
        this.api.perform(delete("/posts/" + postId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void shouldReturnPostNotFoundForInvalidId() throws Exception {
        // Given
        int invalidPostId = 999;

        // When & Then
        this.api.perform(get("/posts/" + invalidPostId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void shouldFetchAllPostsSuccessfully() throws Exception {
        // When & Then
        this.api.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].content").exists());
    }

    @Test
    @WithMockUser
    public void shouldGetFriendsPostsSuccessfully() throws Exception {
        MvcResult resp = this.api.perform(get("/posts/friends-posts"))
            .andExpect(status().isOk())
            .andReturn();
        
        String json = resp.getResponse().getContentAsString();
        List<Post> posts = new ObjectMapper().readValue(json, new TypeReference<List<Post>>() {
        });
        assertEquals(3, posts.size());
        
    }
}
