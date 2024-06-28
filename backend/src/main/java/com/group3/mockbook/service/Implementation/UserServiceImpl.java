package com.group3.mockbook.service.Implementation;

import java.util.List;
import java.util.Optional;

import com.group3.mockbook.exceptions.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.group3.mockbook.model.User;
import com.group3.mockbook.repository.UserRepository;
import com.group3.mockbook.service.UserService;

import jakarta.persistence.EntityNotFoundException;

/**
 * UserServiceImpl is a class that implements the UserService interface.
 * It is used to perform CRUD operations on the User entity.
 * It is used by the UserController to interact with the database.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) throws EntityNotFoundException{
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public User getUserByEmail(String email) throws EntityNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User with email " + email + " not found");
        }
    }

    @Override
    public User getUserByUsername(String username) throws EntityNotFoundException{
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws EntityNotFoundException{
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("User with email " + email + " and password not found");
        }
    }

    @Override
    public User updateUser(int id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        if (!userDetails.getEmail().isEmpty()) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
            user.setUsername(userDetails.getUsername());
        }
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(userDetails.getPassword());
        }
        if (userDetails.getSecurityQuestion() != null && !userDetails.getSecurityQuestion().isEmpty()) {
            user.setSecurityQuestion(userDetails.getSecurityQuestion());
        }
        if (userDetails.getSecurityAnswer() != null && !userDetails.getSecurityAnswer().isEmpty()) {
            user.setSecurityAnswer(userDetails.getSecurityAnswer());
        }
        if (userDetails.getBiography() != null && !userDetails.getBiography().isEmpty()) {
            user.setBiography(userDetails.getBiography());
        }
        if (userDetails.getDegree() != null && !userDetails.getDegree().isEmpty()) {
            user.setDegree(userDetails.getDegree());
        }
        if (userDetails.getFavFood() != null && !userDetails.getFavFood().isEmpty()) {
            user.setFavFood(userDetails.getFavFood());
        }
        if (userDetails.getBirthday() != null && !userDetails.getBirthday().isEmpty()) {
            user.setBirthday(userDetails.getBirthday());
        }
        if (userDetails.getStatus() != null && !userDetails.getStatus().isEmpty()) {
            user.setStatus(userDetails.getStatus());
        }
        if (userDetails.getRole() != null && !userDetails.getRole().isEmpty()) {
            user.setRole(userDetails.getRole());
        }

        // Add any other fields that need to be updated
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        // Implementation goes here
        userRepository.deleteById(id);
    }
}
