package com.group3.mockbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.mockbook.model.Friendship;
import com.group3.mockbook.model.User;
import java.util.List;


@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer>{
    List<Friendship> findBySourceUser(User sourceUser);
    List<Friendship> findByTargetUser(User targetUser);
    List<Friendship> findBySourceUserAndStatus(User sourceUser, Friendship.RequestStatus status);
    List<Friendship> findByTargetUserAndStatus(User targetUser, Friendship.RequestStatus status);
}
