package com.alen.mcsv_websocket.service;

import com.alen.mcsv_websocket.model.SessionStatus;
import com.alen.mcsv_websocket.model.UserSession;
import com.alen.mcsv_websocket.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class UserRedisService {
    private final UserSessionRepository userSessionRepository;
    public void registerUser(SessionConnectedEvent event){
        // The SessionConnectedEvent holds a Message<?> (Spring messaging abstraction) which represents the STOMP CONNECT frame sent by the client.
        // We wrap it to access to STOMP metadata such as user principal, custom headers or SESSION ID (OJITO)
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        // Get the Principal (Authentication object)
        Principal principal = accessor.getUser();
        //Pattern Matching for instanceof
        if (principal instanceof Authentication auth){
            String userId = (String) auth.getDetails();
            String username = auth.getName();
            String sessionId = accessor.getSessionId();
            userSessionRepository.save(UserSession.builder()
                            .id(userId)
                            .username(username)
                            .status(SessionStatus.CONNECTED.name())
                            .sessionId(sessionId)
                            .connectTime(Instant.now())
                            .expiryTime(Instant.now().plus(30, ChronoUnit.MINUTES))
                            .build());
        }
    }
}
