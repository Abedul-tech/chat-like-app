package com.alen.mcsv_message.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@RedisHash("UserSession") //Persists instances in Redis under the UserSession:<username> keyspace
public class UserSession implements Serializable {
    @Id
    private String id;
    @Indexed // This allows searching by username
    private String username;
    private String status;
    private String sessionId;
    private Instant connectTime;
    private Instant expiryTime;
}
