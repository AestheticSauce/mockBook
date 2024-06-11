package com.group3.mockbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.mockbook.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailAndPassword(String email, String password);
}
