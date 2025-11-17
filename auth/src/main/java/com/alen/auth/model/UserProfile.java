package com.alen.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {
    public UserProfile(User user){ //Initializing id
        this.user = user;
        this.id = user.getId();
    }
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id; // Same as user.id (1:1 relationship)

    @OneToOne(fetch = FetchType.LAZY) //When you load a UserProfile, the User object is not fetched immediately.
    @MapsId // Makes this entity share the same primary key as User
    @JoinColumn(name = "id") // FK referencing user.id
    private User user;

    @Column(length = 150)
    private String bio;

    @Column(name = "photo_url", length = 300)
    private String photoUrl;
}
