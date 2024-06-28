package com.group3.mockbook.service;

import com.group3.mockbook.dto.Post.CreatePostDto;
import com.group3.mockbook.dto.Post.UpdatePostDto;
import com.group3.mockbook.model.Post;
import com.group3.mockbook.security.UserPrincipal;

public interface PostService {

    Post createPost(CreatePostDto post, UserPrincipal userPrincipal);

    Post getPostById(int id);

    Post updatePost(int postId, UpdatePostDto post, UserPrincipal userPrincipal);

    Void deletePost(int id, UserPrincipal userPrincipal);

    Iterable<Post> getAllPosts();

    Iterable<Post> getFriendsPosts(UserPrincipal userPrincipal);
}
