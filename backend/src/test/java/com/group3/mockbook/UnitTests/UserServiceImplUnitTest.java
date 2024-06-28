package com.group3.mockbook.UnitTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.group3.mockbook.model.User;
import com.group3.mockbook.repository.UserRepository;
import com.group3.mockbook.service.Implementation.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser_Success() {
        User user = new User(); // Assume User class has appropriate setters to set properties
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
    }

    @Test
    public void testSaveUser_NullUser() {
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(null));
    }
    
    @Test
    public void testGetAllUsers_Success() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }
    
    @Test
    public void testGetAllUsers_EmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<User> result = userService.getAllUsers();
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testGetUserById_Success() {
        Optional<User> user = Optional.of(new User());
        when(userRepository.findById(anyInt())).thenReturn(user);
        Optional<User> result = userService.getUserById(1);
        assertTrue(result.isPresent());
    }
    
    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserById(999);
        assertFalse(result.isPresent());
    }
    
    @Test
    public void testGetUserByEmail_Success() {
        Optional<User> user = Optional.of(new User());
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        Optional<User> result = userService.getUserByEmail("test@example.com");
        assertTrue(result.isPresent());
    }
    
    @Test
    public void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        Optional<User> result = userService.getUserByEmail("nonexistent@example.com");
        assertFalse(result.isPresent());
    }
    
    @Test
    public void testUpdateUser_Success() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        User updatedUser = userService.updateUser(user);
        assertNotNull(updatedUser);
    }
    
    @Test
    public void testDeleteUser_Success() {
        doNothing().when(userRepository).deleteById(anyInt());
        assertDoesNotThrow(() -> userService.deleteUser(1));
    }
}