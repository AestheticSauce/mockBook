package com.group3.mockbook.service.Implementation;

import com.group3.mockbook.model.Friendship;
import com.group3.mockbook.model.User;
import com.group3.mockbook.model.Friendship.RequestStatus;
import com.group3.mockbook.repository.FriendshipRepository;
import com.group3.mockbook.service.FriendshipService;
import com.group3.mockbook.service.UserService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserService userService;

    @Override
    public Friendship requestFriendship(Integer sourceId, Integer targetId) {
        // Create a new friendship request
        Friendship friendship = new Friendship();

        // set the source user
        friendship.setSourceUser(userService.getUserById(sourceId));

        // set the target user
        friendship.setTargetUser(userService.getUserById(targetId));

        friendship.setStatus(Friendship.RequestStatus.PENDING);

        // save the friendship request
        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendshipRequest(Integer id) throws EntityNotFoundException{
        // Implement logic to accept a friendship request
        Optional<Friendship> friendship = friendshipRepository.findById(id);
        friendship.ifPresent(f -> {
            f.setStatus(Friendship.RequestStatus.ACCEPTED);
            friendshipRepository.save(f);
        });
        if (friendship.isPresent()) {
            return friendship.get();
        } else {
            throw new EntityNotFoundException("Friendship with id " + id + " not found");
        }
    }

    @Override
    public Friendship rejectFriendshipRequest(Integer id) {
        // Implement logic to reject a friendship request
        Optional<Friendship> friendship = friendshipRepository.findById(id);
        friendship.ifPresent(f -> {
            f.setStatus(Friendship.RequestStatus.REJECTED);
            friendshipRepository.save(f);
        });
        return friendship.orElse(null);
    }

    @Override
    public void deleteFriendship(Integer id) {
        // Implement logic to delete a friendship
        friendshipRepository.deleteById(id);
    }
    
    @Override
    public Friendship getFriendshipDetails(Integer id) {
        // Implement logic to get a specific friendship detail
        Optional<Friendship> friend = friendshipRepository.findById(id);
        if (friend.isPresent()) {
            return friend.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Friendship> getFriendshipRequests(Integer userId) {
        User user = userService.getUserById(userId);

        // Find all friendships for userId
        List<Friendship> friendships = friendshipRepository.findByTargetUserAndStatus(user, Friendship.RequestStatus.PENDING);
        
        return friendships;
    }

    @Override
    public List<Friendship> getFriendships(Integer userId) {
        User user = userService.getUserById(userId);

        List<Friendship> friendships = new ArrayList<>();

        // Find all friendships for userId
        friendships.addAll(friendshipRepository.findBySourceUserAndStatus(user, Friendship.RequestStatus.ACCEPTED));
        friendships.addAll(friendshipRepository.findByTargetUserAndStatus(user, Friendship.RequestStatus.ACCEPTED));
        
        return friendships;
    }
}