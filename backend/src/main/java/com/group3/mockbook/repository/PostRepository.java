package com.group3.mockbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.mockbook.model.Post;
import com.group3.mockbook.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    Iterable<Post> findAllByAuthor(User author);
}

