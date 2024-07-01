package com.group3.mockbook.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.group3.mockbook.model.User;
import com.group3.mockbook.security.UserPrincipal;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    User getUserByEmailAndPassword(String email, String password);

    User updateUser(int id, User userDetails);

    void deleteUser(int id);
}
