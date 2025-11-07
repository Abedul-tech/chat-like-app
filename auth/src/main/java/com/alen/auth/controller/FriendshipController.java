package com.alen.auth.controller;

import com.alen.auth.dto.FriendDto;
import com.alen.auth.dto.FriendRequestDto;
import com.alen.auth.dto.FriendshipRequestDto;
import com.alen.auth.dto.FriendshipResponseDto;
import com.alen.auth.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/friendship/")
public class FriendshipController {
    private final FriendshipService friendshipService;

    @GetMapping(value = "friends/{userId}")
    public ResponseEntity<List<FriendDto>> getFriends(@PathVariable UUID userId){
        return ResponseEntity.ok(friendshipService.getFriendsOfUser(userId));
    }
    @GetMapping("get-pending-requests/{userId}")
    public ResponseEntity<List<FriendRequestDto>> getPendingFriendRequests(@PathVariable UUID userId){
        //Consider retrieving the current user id automatically
        return ResponseEntity.ok(friendshipService.getPendingRequests(userId));
    }
    @PostMapping(value= "send-request")
    public ResponseEntity<FriendshipResponseDto> sendFriendRequest(@RequestBody FriendshipRequestDto request){
        FriendshipResponseDto friendship = friendshipService.sendFriendRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendship);
    }
    @PutMapping(value = "accept-request")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody FriendshipRequestDto request){
        //Here we can get the current user from wherever, that way we'd only send the sender id
        friendshipService.acceptFriendRequest(request);
        return ResponseEntity.ok("Friend request accepted");
    }
}
