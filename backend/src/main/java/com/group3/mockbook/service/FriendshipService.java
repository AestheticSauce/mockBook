package com.group3.mockbook.service;

import com.group3.mockbook.model.Friendship;
import com.group3.mockbook.model.User;
import com.group3.mockbook.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

public interface FriendshipService {
    Friendship requestFriendship(Integer sourceId, Integer targetId);
    Friendship acceptFriendshipRequest(Integer id);
    Friendship rejectFriendshipRequest(Integer id);
    void deleteFriendship(Integer id);
    Friendship getFriendshipDetails(Integer id);
    List<Friendship> getFriendshipRequests(Integer userId);
    List<Friendship> getFriendships(Integer userId);
    List<User> getRecommendedFriends(UserPrincipal userPrincipal);
}