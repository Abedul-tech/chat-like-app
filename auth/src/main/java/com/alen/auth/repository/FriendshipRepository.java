package com.alen.auth.repository;

import com.alen.auth.model.Friendship;
import com.alen.auth.model.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, FriendshipId> {
    // User is user1
    @Query("SELECT f FROM Friendship f WHERE f.user1.id = :userId AND f.status = 'ACCEPTED'")
    List<Friendship> findAcceptedByUser1(@Param("userId") UUID userId);
    // User is user2(Friendship is table name)
    @Query("SELECT f FROM Friendship f WHERE f.user2.id = :userId AND f.status = 'ACCEPTED'")
    List<Friendship> findAcceptedByUser2(@Param("userId") UUID userId);

    @Query("SELECT f FROM Friendship f WHERE f.user2.id = :userId AND f.status = 'PENDING'")
    List<Friendship> findPendingRequestsForUser(@Param("userId") UUID userId);

}
