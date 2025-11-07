package com.alen.auth.dto;

import com.alen.auth.model.StatusFriendship;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipResponseDto {
    private UUID senderId;
    private String senderUsername;
    private UUID receiverId;
    private String receiverUsername;
    private StatusFriendship status;
    private LocalDateTime requestedAt;
    private LocalDateTime acceptedAt;//it's an object type, so it can naturally hold a _null_ value
}
