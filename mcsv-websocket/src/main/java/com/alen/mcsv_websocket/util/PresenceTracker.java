package com.alen.mcsv_websocket.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class PresenceTracker {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event){
        String userId = extractUserId(event);
    }

    private String extractUserId(SessionConnectedEvent event) {
        return "Shrek";
    }
}
