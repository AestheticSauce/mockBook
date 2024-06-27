package com.group3.mockbook.service;

import com.group3.mockbook.model.FriendRequest;

import java.util.List;
import java.util.Optional;

public interface FriendRequestService {
    FriendRequest requestFriendship(Integer sourceId, Integer targetId);
    FriendRequest acceptFriendshipRequest(Integer id);
    FriendRequest rejectFriendshipRequest(Integer id);
    void deleteFriendship(Integer id);
    Optional<FriendRequest> getFriendshipDetails(Integer id);
}