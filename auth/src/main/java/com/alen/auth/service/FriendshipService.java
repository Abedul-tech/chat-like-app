package com.alen.auth.service;

import com.alen.auth.dto.FriendDto;
import com.alen.auth.dto.FriendRequestDto;
import com.alen.auth.dto.FriendshipRequestDto;
import com.alen.auth.dto.FriendshipResponseDto;
import com.alen.auth.model.Friendship;
import com.alen.auth.model.FriendshipId;
import com.alen.auth.model.StatusFriendship;
import com.alen.auth.model.User;
import com.alen.auth.repository.FriendshipRepository;
import com.alen.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//SENDERS ALWAYS GO FIRST, AND THE RECEIVER
@Service
@RequiredArgsConstructor
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipResponseDto sendFriendRequest(FriendshipRequestDto request){
        if(request.getSenderId().equals(request.getReceiverId())){
            throw new IllegalArgumentException("You cannot send a friend request to yourself.");
        }
        FriendshipId friendshipId = new FriendshipId(request.getSenderId(), request.getReceiverId());

        //Checking if friendship already exists in either direction
        Optional<Friendship> existing = friendshipRepository.findById(friendshipId);
        if (existing.isEmpty()){
            // Also check reversed order(since friendship is bidirectional)
            FriendshipId reverseId = new FriendshipId(request.getReceiverId(), request.getSenderId());
            existing = friendshipRepository.findById(reverseId);
        }
        if (existing.isPresent()){
            throw  new IllegalStateException("Friend request already exists these users.");
        }
        // Checking if sender and receiver indeed exist
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(()-> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(()-> new EntityNotFoundException("Receiver not found"));
        Friendship friendship = Friendship.builder()
                .id(friendshipId)
                .user1(sender)
                .user2(receiver)
                .status(StatusFriendship.PENDING)
                .build();
        Friendship response = friendshipRepository.save(friendship);
        return FriendshipResponseDto.builder()
                .senderId(response.getUser1().getId())
                .senderUsername(response.getUser1().getUsername())
                .receiverId(response.getUser2().getId())
                .receiverUsername(response.getUser2().getUsername())
                .status(response.getStatus())
                .requestedAt(response.getRequested_at())
                .acceptedAt(response.getAccepted_at())
                .build();
    }
    /**
     * Returns all pending requests of user
     */
    public List<FriendRequestDto> getPendingRequests(UUID userId){
        List<Friendship> requests = friendshipRepository.findPendingRequestsForUser(userId);
        return requests.stream()
                .map(f -> new FriendRequestDto(
                        f.getUser1().getId(),
                        f.getUser1().getUsername(),
                        f.getRequested_at()
                ))
                .toList();
    }
    /**
     * Returns all friends (User) of the given userId.
     */
    public List<FriendDto> getFriendsOfUser(UUID userId) {
        List<FriendDto> friends = new ArrayList<>();
        friendshipRepository.findAcceptedByUser1(userId)
                .forEach(f -> friends.add(new FriendDto(f.getUser2().getId(), f.getUser2().getUsername())));
        friendshipRepository.findAcceptedByUser2(userId)
                .forEach(f -> friends.add(new FriendDto(f.getUser1().getId(), f.getUser1().getUsername())));
        System.out.println("Friends found: " + friends);
        return friends;
    }
    /**
     * Accept friend request
     */
    @Transactional
    public void acceptFriendRequest(FriendshipRequestDto request){
        //We store an instance of the Ids in the correct order. Sender first, receiver second
        FriendshipId id = FriendshipId.builder()
                .user1Id(request.getSenderId())
                .user2Id(request.getReceiverId())
                .build();
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Friend request not found!"));

        //Ensuring the receiver is the one who accepts the friend request
        if(!friendship.getUser2().getId().equals(request.getReceiverId())){
            throw new RuntimeException("Only the receiver can accept this request");
        }
        if(!friendship.getStatus().equals(StatusFriendship.PENDING)){
            throw new RuntimeException("Friend request already handled");
        }
        friendship.setStatus(StatusFriendship.ACCEPTED);
        friendship.setAccepted_at(LocalDateTime.now());
        friendshipRepository.save(friendship);
    }
    /**
     * Block friend
     */
    @Transactional
    public void blockFriendRequest(FriendshipRequestDto request){
        FriendshipId id = FriendshipId.builder()
                .user1Id(request.getSenderId())
                .user2Id(request.getReceiverId())
                .build();
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("FriendRequest not found"));
        if(!friendship.getUser2().getId().equals(request.getReceiverId())){
            throw new RuntimeException("Only the receiver can block this request");
        }
        friendship.setStatus(StatusFriendship.BLOCKED);
        friendshipRepository.save(friendship);
    }
    @Transactional
    public void rejectFriendRequest(FriendshipRequestDto request){
        FriendshipId id = FriendshipId.builder()
                .user1Id(request.getSenderId())
                .user2Id(request.getReceiverId())
                .build();
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("FriendRequest not found"));
        if(!friendship.getUser2().getId().equals(request.getReceiverId())){
            throw new RuntimeException("Only the receiver can deny this request");
        }
        friendship.setStatus(StatusFriendship.REJECTED);
        friendshipRepository.save(friendship);
    }
}
