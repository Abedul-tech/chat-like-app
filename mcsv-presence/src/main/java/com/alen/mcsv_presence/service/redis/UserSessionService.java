package com.alen.mcsv_presence.service.redis;

import com.alen.dto.OnlineUserDto;
import com.alen.dto.OnlineUsersEvent;
import com.alen.dto.SessionStatus;
import com.alen.dto.UserSessionDto;
import com.alen.mcsv_presence.dto.SessionDto;
import com.alen.mcsv_presence.model.UserSession;
import com.alen.mcsv_presence.repository.UserSessionRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;
    private final KafkaTemplate<String, OnlineUsersEvent> onlineUsersKafkaTemplate;

    public List<OnlineUserDto> getOnlineUsers() {
        return userSessionRepository.findByStatus(SessionStatus.ONLINE.name()).stream().map(u->new OnlineUserDto(u.getId(),u.getUsername())).toList();
        // Fetch all sessions from Redis
//        List<UserSession> allSessions = (List<UserSession>) userSessionRepository.findAll();
//
//        // Filter only ONLINE users
//        return allSessions.stream()
//                .filter(s -> "ONLINE".equalsIgnoreCase(s.getStatus()))
//                .map(s -> new OnlineUserDto(s.getId(), s.getUsername()))
//                .toList();
    }

    public void save(UserSessionDto session){
        userSessionRepository.save(UserSession.builder()
                .id(session.getId())
                .username(session.getUsername())
                .sessionId(session.getSessionId())
                .status(SessionStatus.ONLINE.name())
                .lastSeenAt(Instant.now())
                .build());
        //return to websocket online users for status friend
        sendOnlineUsersToKafka();
        //Check for pending messages

    }
    public SessionDto getSession(String userId){
        UserSession session = userSessionRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("Session not found: "+ userId));
        return SessionDto.builder()
                .id(session.getId())
                .username(session.getUsername())
                .status(session.getStatus())
                .lastSeenAt(session.getLastSeenAt())
                .build();
    }
    public void updateStatus(String id, SessionStatus status){
        UserSession userSession = userSessionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Session not found: "+ id));
        userSession.setStatus(status.name());
        userSession.setLastSeenAt(Instant.now());
        userSessionRepository.save(userSession);
        //return to websocket online users for status friend
        sendOnlineUsersToKafka();

    }
    public void delete(String id){
        userSessionRepository.deleteById(id);
    }

    //UTILITIES
    private void sendOnlineUsersToKafka(){
        OnlineUsersEvent users = new OnlineUsersEvent(getOnlineUsers());
        onlineUsersKafkaTemplate.send("online-users",users);
    }
}
//You cannot update specific fields of hash table in Redis, you just override everything, That's why we retrieve the object, change specific values, and then save the object
