package com.group3.mockbook.UnitTests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.group3.mockbook.model.User;
import com.group3.mockbook.repository.UserRepository;
import com.group3.mockbook.service.Implementation.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;

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
        User result = userService.getUserById(1);
        assertTrue(result != null);
    }
    
    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(999));
    }
    
    @Test
    public void testGetUserByEmail_Success() {
        Optional<User> user = Optional.of(new User());
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        User result = userService.getUserByEmail("test@example.com");
        assertTrue(result != null);
    }
    
    @Test
    public void testGetUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByEmail("nonexistent@example.com"));
    }

    // Test cases for getUserByUsername
    @Test
    public void testGetUserByUsername_Success() {
        Optional<User> user = Optional.of(new User());
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        User result = userService.getUserByUsername("testuser");
        assertTrue(result != null);
    }

    @Test
    public void testGetUserByUsername_NotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByUsername("nonexistentuser"));
    }

    // Test cases for getUserByEmailAndPassword
    @Test
    public void testGetUserByEmailAndPassword_Success() {
        Optional<User> user = Optional.of(new User());
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);
        User result = userService.getUserByEmailAndPassword("user@example.com", "password");
        assertTrue(result != null);
    }

    @Test
    public void testGetUserByEmailAndPassword_NotFound() {
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByEmailAndPassword("notreal@example.com", "password"));
    }
    
    @Test
    public void updateUser_Success() {
        int userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("old@example.com");
        existingUser.setUsername("oldUsername");

        User updatedDetails = new User();
        updatedDetails.setEmail("new@example.com");
        updatedDetails.setUsername("newUsername");
        updatedDetails.setPassword("newPassword");
        updatedDetails.setBiography("newBiography");
        updatedDetails.setSecurityQuestion("newSecurityQuestion");
        updatedDetails.setSecurityAnswer("newSecurityAnswer");
        updatedDetails.setBirthday("newBirthday");
        updatedDetails.setDegree("newDegree");
        updatedDetails.setFavFood("newFavFood");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.saveAndFlush(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updatedUser = userService.updateUser(userId, updatedDetails);

        assertEquals("new@example.com", updatedUser.getEmail());
        assertEquals("newUsername", updatedUser.getUsername());
        verify(userRepository).saveAndFlush(updatedUser);
    }

    @Test
    public void updateUser_UserNotFound() {
        int userId = 999;
        User updatedDetails = new User();
        updatedDetails.setEmail("new@example.com");
        updatedDetails.setUsername("newUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(userId, updatedDetails));
    }

    @Test
    public void updateUser_NoChanges() {
        int userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("old@example.com");
        existingUser.setUsername("oldUsername");

        User updatedDetails = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.saveAndFlush(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updatedUser = userService.updateUser(userId, updatedDetails);

        assertEquals("old@example.com", updatedUser.getEmail());
        assertEquals("oldUsername", updatedUser.getUsername());
        verify(userRepository).saveAndFlush(updatedUser);
    }

    @Test
    public void testDeleteUser_Success() {
        doNothing().when(userRepository).deleteById(anyInt());
        assertDoesNotThrow(() -> userService.deleteUser(1));
    }
        
}