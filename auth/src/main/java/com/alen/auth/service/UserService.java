package com.alen.auth.service;

import com.alen.auth.dto.UserIdDto;
import com.alen.auth.model.User;
import com.alen.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserIdDto getIdByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not Found"));
        return UserIdDto.builder()
                .idUser(user.getId().toString())
                .build();
    }
}
