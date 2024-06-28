package com.group3.mockbook.UnitTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.group3.mockbook.service.UserService;
import com.group3.mockbook.service.Implementation.FriendshipServiceImpl;
import com.group3.mockbook.service.Implementation.PostServiceImpl;

import jakarta.persistence.EntityNotFoundException;

import com.group3.mockbook.repository.FriendshipRepository;
import com.group3.mockbook.repository.PostRepository;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.dto.Post.CreatePostDto;
import com.group3.mockbook.dto.Post.UpdatePostDto;
import com.group3.mockbook.model.Friendship;
import com.group3.mockbook.model.Post;
import com.group3.mockbook.model.User;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplUnitTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    private Post post;

    private User user;

    private UserPrincipal userPrincipal;

    @BeforeEach
    void setup() {
        user = new User("test1@example.com", "test1", "pass1", "q", "a");
        user.setId(1);

        userPrincipal = new UserPrincipal();
        userPrincipal.setId(1);
        userPrincipal.setUsername("test1@example.com");
        userPrincipal.setPassword("pass1");

        post = new Post();
        post.setAuthor(user);
        post.setContent("content");
        post.setTitle("title");
        post.setId(1);
    }

    // Test createPost
    @Test
    void testCreatePost() {
        when(userService.getUserById(anyInt())).thenReturn(user);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setContent("content");
        createPostDto.setTitle("title");

        Post newPost = postServiceImpl.createPost(createPostDto, userPrincipal);

        assertEquals(post, newPost);
    }

    // Test getPostById
    @Test
    void testGetPostById() {
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));

        Post foundPost = postServiceImpl.getPostById(1);

        assertEquals(post, foundPost);
    }

    @Test
    void testGetPostById_NotFound() {
        when(postRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            postServiceImpl.getPostById(1);
        });
    }

    // Test updatePost
    @Test
    void testUpdatePost_NoChanges() {
        when(postRepository.findById(anyInt())).
            thenReturn(Optional.of(post));

        when(postRepository.save(any(Post.class))).
            thenReturn(post);
        
        Post updatedPost = postServiceImpl.updatePost(1, new UpdatePostDto(), userPrincipal);

        assertEquals(post, updatedPost);
    }

    @Test
    void testUpdatePost_TitleChange() {
        when(postRepository.findById(anyInt())).
            thenReturn(Optional.of(post));

        when(postRepository.save(any(Post.class))).
            thenReturn(post);

        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setTitle("new title");

        Post updatedPost = postServiceImpl.updatePost(1, updatePostDto, userPrincipal);

        assertEquals("new title", updatedPost.getTitle());
    }

    @Test
    void testUpdatePost_ContentChange() {
        when(postRepository.findById(anyInt())).
            thenReturn(Optional.of(post));

        when(postRepository.save(any(Post.class))).
            thenReturn(post);

        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setContent("new content");

        Post updatedPost = postServiceImpl.updatePost(1, updatePostDto, userPrincipal);

        assertEquals("new content", updatedPost.getContent());
    }

    @Test
    void testUpdatePost_TitleAndContentChange() {
        when(postRepository.findById(anyInt())).
            thenReturn(Optional.of(post));

        when(postRepository.save(any(Post.class))).
            thenReturn(post);

        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setTitle("new title");
        updatePostDto.setContent("new content");

        Post updatedPost = postServiceImpl.updatePost(1, updatePostDto, userPrincipal);

        assertEquals("new title", updatedPost.getTitle());
        assertEquals("new content", updatedPost.getContent());
    }

    // Test deletePost
    @Test
    void testDeletePost_Success() {
        when(postRepository.findById(anyInt())).
            thenReturn(Optional.of(post));

        postServiceImpl.deletePost(1, userPrincipal);

        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void testDeletePost_NotFound() {
        when(postRepository.findById(anyInt())).
            thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            postServiceImpl.deletePost(1, userPrincipal);
        });
    }

}