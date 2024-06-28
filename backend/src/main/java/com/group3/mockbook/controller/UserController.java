package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.group3.mockbook.dto.User.UserDTO;
import com.group3.mockbook.model.User;
import com.group3.mockbook.service.UserService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;



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
    public User getUser(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + id);
        } else {
            return user.get();
        }
    }
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody UserDTO userDto) {
        User user = modelMapper.map(userDto, User.class);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
    
}
