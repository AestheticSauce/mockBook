package com.group3.mockbook.service;

import java.util.List;

import com.group3.mockbook.model.User;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    User getUserByEmailAndPassword(String email, String password);

    User updateUser(User user);

    User updateStatus(String Status);

    void deleteUser(int id);
}
