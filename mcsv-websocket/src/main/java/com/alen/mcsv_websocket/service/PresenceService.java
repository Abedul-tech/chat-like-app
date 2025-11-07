package com.alen.mcsv_websocket.service;

import com.alen.dto.HeartbeatDto;
import com.alen.dto.SessionStatus;
import com.alen.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class PresenceService {
    private final KafkaTemplate<String, UserSessionDto> sessionKafkaTemplate;
    private final KafkaTemplate<String, HeartbeatDto> heartbeatKafkaTemplate;
    public void sendUser(SessionConnectedEvent event){
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
            sessionKafkaTemplate.send("session",UserSessionDto.builder()
                            .id(userId)
                            .username(username)
                            .status(SessionStatus.ONLINE.name())
                            .sessionId(sessionId)
                    .build());
        }
    }
    public void updateSession(SessionDisconnectEvent event){
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = accessor.getUser();
        if (principal instanceof Authentication auth){
            String userId = (String) auth.getDetails();
            heartbeatKafkaTemplate.send("heartbeat", new HeartbeatDto(userId,SessionStatus.OFFLINE));
        }
    }
}