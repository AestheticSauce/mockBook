package com.group3.mockbook.service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group3.mockbook.model.User;
import com.group3.mockbook.repository.UserRepository;
import com.group3.mockbook.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        // Implementation goes here
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        // Implementation goes here
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        // Implementation goes here
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        // Implementation goes here
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        // Implementation goes here
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User updateUser(User user) {
        // Implementation goes here
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        // Implementation goes here
        userRepository.deleteById(id);
    }
}
