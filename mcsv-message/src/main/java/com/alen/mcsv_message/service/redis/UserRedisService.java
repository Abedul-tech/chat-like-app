package com.alen.mcsv_message.service.redis;

import com.alen.mcsv_message.model.redis.SessionStatus;
import com.alen.mcsv_message.model.redis.UserSession;
import com.alen.mcsv_message.repository.redis.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserRedisService {
    private final UserSessionRepository userSessionRepository;
    //It will return the ID only if the UserSession is there and the status is ONLINE
    //If it doesn't hold, just return Optional.empty()
    public Optional<String> getIdByUsername(String username){
        //.map(...) transforms the value inside the Optional (if present)
        //Return empty optional if not present
        return userSessionRepository.findByUsername(username)
//                .filter(userSession -> userSession.getStatus().equals(SessionStatus.ONLINE.name()))
                .map(UserSession::getId);
    }
    public Optional<UserSession> findOnlineSessionByUsername(String username) {
        return userSessionRepository.findByUsername(username) //flipped the .equals() for null safety.
                .filter(userSession -> SessionStatus.ONLINE.name().equals(userSession.getStatus()));
    }
    public String getUsernameById(UUID id){
        UserSession session = userSessionRepository.findById(id.toString()).orElseThrow();
        return session.getUsername();
    }
}
