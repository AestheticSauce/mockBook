package com.group3.mockbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group3.mockbook.model.FriendRequest;
import com.group3.mockbook.model.User;
import java.util.List;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer>{
}
