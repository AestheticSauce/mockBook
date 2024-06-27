package com.group3.mockbook.service.Implementation;

import com.group3.mockbook.model.FriendRequest;
import com.group3.mockbook.repository.FriendRequestRepository;
import com.group3.mockbook.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestRepository friendshipRepository;

    @Override
    public FriendRequest requestFriendship(Integer sourceId, Integer targetId) {
        // Create a new friendship request
        FriendRequest friendship = new FriendRequest();
        friendship.setSourceId(sourceId);
        friendship.setTargetId(targetId);

        // save the friendship request
        return friendshipRepository.save(friendship);
    }

    @Override
    public FriendRequest acceptFriendshipRequest(Integer id) {
        // Implement logic to accept a friendship request
        Optional<FriendRequest> friendship = friendshipRepository.findById(id);
        friendship.ifPresent(f -> {
            f.setStatus(FriendRequest.RequestStatus.ACCEPTED);
            friendshipRepository.save(f);
        });
        return friendship.orElse(null);
    }

    @Override
    public FriendRequest rejectFriendshipRequest(Integer id) {
        // Implement logic to reject a friendship request
        Optional<FriendRequest> friendship = friendshipRepository.findById(id);
        friendship.ifPresent(f -> {
            f.setStatus(FriendRequest.RequestStatus.REJECTED);
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
    public Optional<FriendRequest> getFriendshipDetails(Integer id) {
        // Implement logic to get a specific friendship detail
        return friendshipRepository.findById(id);
    }
}