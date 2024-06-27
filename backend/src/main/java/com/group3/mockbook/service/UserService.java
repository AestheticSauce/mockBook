package com.group3.mockbook.service;

import java.util.List;
import java.util.Optional;

import com.group3.mockbook.model.User;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmailAndPassword(String email, String password);

    User updateUser(User user);

    void deleteUser(int id);

    void resetPassword(String email, String securityAnswer, String newPassword);
}
