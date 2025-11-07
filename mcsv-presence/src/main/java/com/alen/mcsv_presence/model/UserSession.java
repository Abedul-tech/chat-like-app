package com.alen.mcsv_presence.model;

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
public class UserSession implements Serializable{
    @Id
    private String id;
    @Indexed
    private String username;
    private String sessionId;
    @Indexed
    private String status;
    private Instant lastSeenAt;
}
