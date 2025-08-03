package com.alen.mcsv_websocket.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@RedisHash("UserSession")
public class UserSession implements Serializable {
    @Id
    private String username;
    private String status;
    private String sessionId;
    private Instant connectTime;
    private Instant expiryTime;
}
