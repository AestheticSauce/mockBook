package com.group3.mockbook.UnitTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.group3.mockbook.service.UserService;
import com.group3.mockbook.service.Implementation.FriendshipServiceImpl;

import jakarta.persistence.EntityNotFoundException;

import com.group3.mockbook.repository.FriendshipRepository;
import com.group3.mockbook.model.Friendship;
import com.group3.mockbook.model.User;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceImplUnitTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    private Friendship acceptedFriendship;

    private Friendship acceptedFriendship2;

    private Friendship pendingFriendship;

    private User user1;

    private User user2;

    private User user3;

    private User user4;

    @BeforeEach
    void setup() {
        user1 = new User("test1@example.com", "test1", "pass1", "q", "a");
        user1.setId(1);
        user2 = new User("test2@example.com", "test2", "pass2", "q", "a");
        user2.setId(2);
        user3 = new User("test3@example.com", "test3", "pass3", "q", "a");
        user3.setId(3);
        user4 = new User("test4@example.com", "test4", "pass4", "q", "a");
        user4.setId(4);

        acceptedFriendship = new Friendship();
        acceptedFriendship.setId(1);
        acceptedFriendship.setSourceUser(user1);
        acceptedFriendship.setTargetUser(user2);
        acceptedFriendship.setStatus(Friendship.RequestStatus.ACCEPTED);

        acceptedFriendship2 = new Friendship();
        acceptedFriendship.setId(2);
        acceptedFriendship.setSourceUser(user3);
        acceptedFriendship.setTargetUser(user1);
        acceptedFriendship.setStatus(Friendship.RequestStatus.ACCEPTED);

        pendingFriendship = new Friendship();
        pendingFriendship.setId(2);
        pendingFriendship.setSourceUser(user1);
        pendingFriendship.setTargetUser(user3);
        pendingFriendship.setStatus(Friendship.RequestStatus.PENDING);
    }

    // Test the requestFriendship method
    @Test
    void testRequestFriendship_Success() {
        when(userService.getUserById(1)).thenReturn(user1);
        when(userService.getUserById(3)).thenReturn(user3);
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(pendingFriendship);
        Friendship result = friendshipService.requestFriendship(1, 3);
        assertEquals(pendingFriendship, result);
    }

    // Test the acceptFriendshipRequest method
    @Test
    void testAcceptFriendshipRequest_Success() {
        when(friendshipRepository.findById(anyInt())).thenReturn(Optional.of(pendingFriendship));
        Friendship result = friendshipService.acceptFriendshipRequest(2);
        assertEquals(pendingFriendship, result);
    }

    @Test
    void testAcceptFriendshipRequest_NotFound() {
        when(friendshipRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> friendshipService.acceptFriendshipRequest(999));
    }

    // Test the rejectFriendshipRequest method
    @Test
    void testRejectFriendshipRequest_Success() {
        when(friendshipRepository.findById(anyInt())).thenReturn(Optional.of(pendingFriendship));
        Friendship result = friendshipService.rejectFriendshipRequest(2);
        assertEquals(pendingFriendship, result);
    }

    @Test
    void testRejectFriendshipRequest_NotFound() {
        when(friendshipRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertNull(friendshipService.rejectFriendshipRequest(999));
    }

    // Test the deleteFriendship method
    @Test
    void testDeleteFriendship_Success() {
        friendshipService.deleteFriendship(1);
        verify(friendshipRepository, times(1)).deleteById(1);
    }

    // Test the getFriendshipDetails method
    @Test
    void testGetFriendshipDetails_Success() {
        when(friendshipRepository.findById(anyInt())).thenReturn(Optional.of(acceptedFriendship));
        Friendship result = friendshipService.getFriendshipDetails(1);
        assertEquals(acceptedFriendship, result);
    }

    @Test
    void testGetFriendshipDetails_NotFound() {
        when(friendshipRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertNull(friendshipService.getFriendshipDetails(999));
    }

    // Test the getFriendshipRequests method
    @Test
    void testGetFriendshipRequests_Success() {
        when(userService.getUserById(1)).thenReturn(user1);
        when(friendshipRepository.findByTargetUserAndStatus(user1, Friendship.RequestStatus.PENDING))
                .thenReturn(List.of(pendingFriendship));
        List<Friendship> result = friendshipService.getFriendshipRequests(user1.getId());
        assertEquals(pendingFriendship, result.get(0));
    }

    @Test
    void testGetFriendshipRequests_NoRequests() {
        when(userService.getUserById(1)).thenReturn(user1);
        when(friendshipRepository.findByTargetUserAndStatus(user1, Friendship.RequestStatus.PENDING)).thenReturn(List.of());
        List<Friendship> result = friendshipService.getFriendshipRequests(user1.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetFriendshipRequests_UserNotFound() {
        when(userService.getUserById(999)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> friendshipService.getFriendshipRequests(999));
    }

    // Test the getFriendships methods
    @Test
    void testGetFriendships_Success() {
        when(userService.getUserById(1)).thenReturn(user1);
        when(friendshipRepository.findBySourceUserAndStatus(user1, Friendship.RequestStatus.ACCEPTED)).thenReturn(List.of(acceptedFriendship));
        when(friendshipRepository.findByTargetUserAndStatus(user1, Friendship.RequestStatus.ACCEPTED)).thenReturn(List.of(acceptedFriendship2));
        List<Friendship> result = friendshipService.getFriendships(user1.getId());
        assertEquals(List.of(acceptedFriendship, acceptedFriendship2), result);
    }

    @Test
    void testGetFriendships_NoFriendships() {
        when(userService.getUserById(1)).thenReturn(user1);
        when(friendshipRepository.findBySourceUserAndStatus(user1, Friendship.RequestStatus.ACCEPTED)).thenReturn(List.of());
        List<Friendship> result = friendshipService.getFriendships(user1.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetFriendships_UserNotFound() {
        when(userService.getUserById(999)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> friendshipService.getFriendships(999));
    }
}