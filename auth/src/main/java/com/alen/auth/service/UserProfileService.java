package com.alen.auth.service;

import com.alen.auth.dto.UserProfileRequest;
import com.alen.auth.dto.UserProfileResponseDto;
import com.alen.auth.model.User;
import com.alen.auth.model.UserProfile;
import com.alen.auth.repository.UserProfileRepository;
import com.alen.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    public UserProfileResponseDto saveOrUpdate(UUID userId, UserProfileRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseGet(()-> new UserProfile(user)); //If Optional has a value-> return it. If not(empty), call the function or supplier and return the result
        profile.setBio(request.getBio());
        profile.setPhotoUrl(request.getPhotoUrl());

        UserProfile saved = userProfileRepository.save(profile);
        return UserProfileResponseDto.builder()
                .id(saved.getId())
                .bio(saved.getBio())
                .photoUrl(saved.getPhotoUrl())
                .build();
    }
    public UserProfileResponseDto findById(UUID userId){
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Profile not found : "+userId));
        return UserProfileResponseDto
                .builder()
                .id(profile.getId())
                .bio(profile.getBio())
                .photoUrl(profile.getPhotoUrl())
                .build();
    }
    
}
