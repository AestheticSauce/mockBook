package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.*;

import com.group3.mockbook.dto.User.UserDTO;
import com.group3.mockbook.model.User;
import com.group3.mockbook.service.UserService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        } else {
            return user;
        }
    }
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    /*changed the put mapping so updating the profile works, we can talk later on it.. I dont really understand
    how put mapping works so im leaving it commented incase we need it later.
     */
//    @PutMapping("")
//    @ResponseStatus(HttpStatus.OK)
//    public User updateUser(@RequestBody User user) {
//        return userService.updateUser(user);
//    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@PathVariable int id, @RequestBody String status) {
        Optional<User>optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        } else {
            User user = optionalUser.get();
            user.setStatus(status);
            userService.updateUser(user);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable int id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        User user = optionalUser.get();
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setSecurityQuestion(userDetails.getSecurityQuestion());
        user.setSecurityAnswer(userDetails.getSecurityAnswer());
        user.setUsername(userDetails.getUsername());
        user.setBiography(userDetails.getBiography());
        user.setBirthday(userDetails.getBirthday());
        user.setDegree(userDetails.getDegree());
        user.setFavFood(userDetails.getFavFood());

        return userService.updateUser(user);
    }
    
}
