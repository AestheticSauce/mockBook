package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.group3.mockbook.dto.Friendship.FriendRequestDto;
import com.group3.mockbook.model.FriendRequest;
import com.group3.mockbook.model.User;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.service.FriendRequestService;
import com.group3.mockbook.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;

@RestController
@RequestMapping("/friend-requests")
public class FriendRequestController {  

    @Autowired
    private FriendRequestService friendRequestService;
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public FriendRequest requestNewFriend(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal, @RequestBody FriendRequestDto requestDTO) {
        return friendRequestService.requestFriendship(userPrincipal.getId(), requestDTO.getTargetId());
    }
    
    @PutMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.OK)
    public FriendRequest acceptFriendRequest(@PathVariable Integer id) {
        return friendRequestService.acceptFriendshipRequest(id);
    }
    
    @PutMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    public FriendRequest rejectFriendRequest(@PathVariable Integer id) {
        return friendRequestService.rejectFriendshipRequest(id);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriendRequest(@PathVariable Integer id) {
        friendRequestService.deleteFriendship(id);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FriendRequest> getFriendRequest(@PathVariable Integer id) {
        Optional<FriendRequest> friendRequest = friendRequestService.getFriendshipDetails(id);
        return friendRequest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
