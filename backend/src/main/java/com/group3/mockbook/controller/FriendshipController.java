package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.RestController;

import com.group3.mockbook.dto.Friendship.FriendRequestDto;
import com.group3.mockbook.model.Friendship;
import com.group3.mockbook.model.User;
import com.group3.mockbook.security.UserPrincipal;
import com.group3.mockbook.service.FriendshipService;

import java.util.List;

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
import org.springframework.security.core.annotation.CurrentSecurityContext;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {  

    @Autowired
    private FriendshipService friendRequestService;
    
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Friendship requestNewFriend(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal, @RequestBody FriendRequestDto requestDTO) {
        return friendRequestService.requestFriendship(userPrincipal.getId(), requestDTO.getTargetId());
    }
    
    @PutMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.OK)
    public Friendship acceptFriendRequest(@PathVariable Integer id) {
        return friendRequestService.acceptFriendshipRequest(id);
    }
    
    @PutMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.OK)
    public Friendship rejectFriendRequest(@PathVariable Integer id) {
        return friendRequestService.rejectFriendshipRequest(id);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFriendship(@PathVariable Integer id) {
        friendRequestService.deleteFriendship(id);
    }

    @GetMapping("/requests")
    public List<Friendship> getFriendRequests(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal) {
        return friendRequestService.getFriendshipRequests(userPrincipal.getId());
    }

    @GetMapping("")
    public List<Friendship> getFriendships(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal) {
        return friendRequestService.getFriendships(userPrincipal.getId());
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Friendship getFriendship(@PathVariable Integer id) {
        return friendRequestService.getFriendshipDetails(id);
    }

    @GetMapping("/recommended")
    public List<User> getRecommendedFriends(@CurrentSecurityContext(expression="authentication.principal") UserPrincipal userPrincipal) {
        return friendRequestService.getRecommendedFriends(userPrincipal);
    }
}
