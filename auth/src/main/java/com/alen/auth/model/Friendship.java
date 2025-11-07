package com.alen.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="friendship")
public class Friendship {

    @EmbeddedId
    private FriendshipId id;

    @ManyToOne //User 1 is the sender
    @MapsId("user1Id") // maps this field to the corresponding part of the composite key
    @JoinColumn(name="user_id1", referencedColumnName = "id")
    private User user1;

    @ManyToOne
    @MapsId("user2Id") // User 2 is the receiver
    @JoinColumn(name ="user_id2", referencedColumnName = "id")
    private User user2;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusFriendship status;

    @CreationTimestamp
    @Column(
            name="requested_at",
            updatable = false,
            nullable = false)
    private LocalDateTime requested_at;

    @Column(name="accepted_at")
    private LocalDateTime accepted_at;

}
