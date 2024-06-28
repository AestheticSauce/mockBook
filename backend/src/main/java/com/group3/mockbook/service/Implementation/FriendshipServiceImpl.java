package com.group3.mockbook.service.Implementation;

import com.group3.mockbook.model.Friendship;
import com.group3.mockbook.model.User;
import com.group3.mockbook.repository.FriendshipRepository;
import com.group3.mockbook.service.FriendshipService;
import com.group3.mockbook.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        friendship.setSourceUser(userService.getUserById(sourceId).get());

        // set the target user
        friendship.setTargetUser(userService.getUserById(targetId).get());

        friendship.setStatus(Friendship.RequestStatus.PENDING);

        // save the friendship request
        return friendshipRepository.save(friendship);
    }

    @Override
    public Friendship acceptFriendshipRequest(Integer id) {
        // Implement logic to accept a friendship request
        Optional<Friendship> friendship = friendshipRepository.findById(id);
        friendship.ifPresent(f -> {
            f.setStatus(Friendship.RequestStatus.ACCEPTED);
            friendshipRepository.save(f);
        });
        return friendship.orElse(null);
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
    public Optional<Friendship> getFriendshipDetails(Integer id) {
        // Implement logic to get a specific friendship detail
        return friendshipRepository.findById(id);
    }

    @Override
    public List<Friendship> getFriendshipRequests(Integer userId) {
        User user = userService.getUserById(userId).get();

        // Find all friendships for userId
        List<Friendship> friendships = friendshipRepository.findByTargetUser(user);
        
        // Filter for pending status
        friendships.removeIf(f -> f.getStatus() != Friendship.RequestStatus.PENDING);

        return friendships;
    }

    @Override
    public List<Friendship> getFriendships(Integer userId) {
        User user = userService.getUserById(userId).get();

        // Find all friendships for userId
        List<Friendship> friendships = friendshipRepository.findBySourceUser(user);
        
        // Filter for accepted status
        friendships.removeIf(f -> f.getStatus() != Friendship.RequestStatus.ACCEPTED);

        return friendships;
    }
}