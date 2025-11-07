package com.alen.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipId implements Serializable {
    @Column(name="user_id1")
    private UUID user1Id;
    @Column(name="user_id2")
    private UUID user2Id;
}
