package com.group3.mockbook.controller;

import com.group3.mockbook.dto.Post.CreatePostDto;
import com.group3.mockbook.dto.Post.UpdatePostDto;
import com.group3.mockbook.model.Post;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("")
    public ResponseEntity<Post> createPost(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal, @RequestBody CreatePostDto post) {
        Post newPost = postService.createPost(post, userPrincipal);
        return ResponseEntity.ok(newPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable int id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal, @RequestBody UpdatePostDto post, @PathVariable int id) {
        Post updatedPost = postService.updatePost(id, post, userPrincipal);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal, @PathVariable int id) {
        postService.deletePost(id, userPrincipal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Post>> getAllPosts() {
        Iterable<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}