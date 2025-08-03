package com.alen.mcsv_websocket.listener;

import com.alen.mcsv_websocket.model.SessionStatus;
import com.alen.mcsv_websocket.model.UserSession;
import com.alen.mcsv_websocket.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
//Only load this bean if the property feature.enabled is set to true.
@ConditionalOnProperty(name = "feature.enabled", havingValue = "false")
//Only works if the WebSocket connection has been secured and authenticated beforehand.
@Component
@RequiredArgsConstructor
public class WebSocketConnectionListener {

    //Used to perform operations in Redis
    private final RedisTemplate<String, String> redisTemplate;
    private final UserSessionRepository userSessionRepository;
    private static final Logger log = LoggerFactory.getLogger(WebSocketConnectionListener.class);
    //Event that react when successfully connection
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Register user in Redis
    //It's triggered automatically by spring when websocket reaches this point
    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent event){ //Triggered when new STOMP WebSocket session is connected
        // The SessionConnectedEvent holds a Message<?> (Spring messaging abstraction) which represents the STOMP CONNECT frame sent by the client.
        //We wrap it to access to STOMP metadata such as user principal, custom headers or SESSION ID (OJITO)
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String username = accessor.getUser() != null ? accessor.getUser().getName() : "anonymous";//Username extracted from Authenticated user setted in the JwtInterceptor
        //Setting  user status in Redis with its expiration
        String userStatusKey = "user:status: " + sessionId;
        //Storing in redis
        userSessionRepository.save(UserSession.builder()
                .username(username)//pending
                .status(SessionStatus.CONNECTED.name())
                .sessionId(sessionId)
                .connectTime(Instant.now())
                .expiryTime(Instant.now().plus(30, ChronoUnit.MINUTES))
                .build());
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Delete user from Redis
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String username = headers.getUser().getName();

        //Update status to disconnected
        String userStatusKey = "user:status:" + username;
        redisTemplate.opsForValue().set(userStatusKey, SessionStatus.DISCONNECTED.name());

        //Optionally remove session info or mark as inactive
        String sessionKey = "user:session:" + username;
        redisTemplate.delete(sessionKey);
    }
}
