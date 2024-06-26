package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.group3.mockbook.exceptions.UserNotFoundException;
import com.group3.mockbook.model.User;
import com.group3.mockbook.service.UserService;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
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
            throw new UserNotFoundException(id);
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
            throw new UserNotFoundException(id);
        }
        User user = optionalUser.get();
        user.setUsername(userDetails.getUsername());
        user.setBiography(userDetails.getBiography());
        user.setBirthday(userDetails.getBirthday());
        user.setDegree(userDetails.getDegree());
        user.setFavFood(userDetails.getFavFood());

        return userService.updateUser(user);
    }
    
}
