package com.group3.mockbook.service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.mockbook.dto.Post.CreatePostDto;
import com.group3.mockbook.dto.Post.UpdatePostDto;
import com.group3.mockbook.model.Post;
import com.group3.mockbook.repository.PostRepository;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.service.PostService;
import com.group3.mockbook.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Override
    public Post createPost(CreatePostDto post, UserPrincipal userPrincipal) {
        // convert post dto to post entity
        Post newPost = new Post();
        newPost.setAuthor(userService.getUserById(userPrincipal.getId()));
        newPost.setContent(post.getContent());
        newPost.setTitle(post.getTitle());

        return postRepository.save(newPost);
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new EntityNotFoundException("post with id " + id + " not found");
        }
    }

    @Override
    public Post updatePost(int postId, UpdatePostDto postDto, UserPrincipal userPrincipal) {
        // get post by id
        Post post = getPostById(postId);

        // update post
        post.setTitle(null != postDto.getTitle() ? postDto.getTitle() : post.getTitle());
        post.setContent(null != postDto.getContent() ? postDto.getContent() : post.getContent());
        
        return postRepository.save(post);
    }

    @Override
    public Void deletePost(int id, UserPrincipal userPrincipal) {
        // get post by id
        Post post = getPostById(id);

        // check if user is the author of the post
        if (post.getAuthor().getId() != userPrincipal.getId()) {
            throw new EntityNotFoundException("post with id " + id + " not found");
        }

        postRepository.delete(post);
        return null;
    }

    @Override
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
