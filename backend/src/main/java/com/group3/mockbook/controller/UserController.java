package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.*;

import com.group3.mockbook.dto.User.StatusUpdateDTO;
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
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
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
    public void updateStatus(@PathVariable int id, @RequestBody StatusUpdateDTO status) {
        User userDetails = new User();
        userDetails.setStatus(status.getStatus());
        userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable int id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }
    
}
