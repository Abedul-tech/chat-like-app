package com.alen.mcsv_message.service;

import com.alen.mcsv_message.model.UserSession;
import com.alen.mcsv_message.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserRedisService {
    private final UserSessionRepository userSessionRepository;
    public Optional<String> getIdByUsername(String username){
        //.map(...) transforms the value inside the Optional (if present)
        return userSessionRepository.findByUsername(username)
                .map(UserSession::getId);//replaced a lambda with method reference
    }
}
