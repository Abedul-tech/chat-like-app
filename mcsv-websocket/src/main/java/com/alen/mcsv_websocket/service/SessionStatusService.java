package com.alen.mcsv_websocket.service;

import com.alen.mcsv_websocket.model.SessionStatus;
import com.alen.mcsv_websocket.model.UserSession;
import com.alen.mcsv_websocket.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@ConditionalOnProperty(name = "feature.enabled", havingValue = "true")
@Service
@RequiredArgsConstructor
public class SessionStatusService {
    private final UserSessionRepository userSessionRepository;

    //Keep user session alive as long as this method is receiving heartbeats
    public void updateSession(Principal principal){
        String username = principal.getName();

        //Reset the Time Limit
        userSessionRepository.findById(username).ifPresent(session ->{
            session.setExpiryTime(Instant.now().plus(30, ChronoUnit.MINUTES));
            userSessionRepository.save(session);
        });
    }
    public boolean isUserConnected(String username){
        return "CONNECTED".equals(getUserStatus(username));
    }

    private String getUserStatus(String username){
        String status = userSessionRepository.findById(username).map(UserSession::getStatus).orElse(null);
        return status != null ? status : SessionStatus.DISCONNECTED.name();
    }

}
