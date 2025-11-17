package com.alen.auth.service;

import com.alen.auth.dto.*;
import com.alen.auth.model.User;
import com.alen.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FriendshipService friendshipService;
    private final UserProfileService userProfileService;
    public UserIdDto getIdByUsername(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return UserIdDto.builder()
                .idUser(user.getId())
                .build();
    }
    public List<String> searchUsersByUsername(String username){
        List<User> users = userRepository.searchUsersByUsername(username);
        return users.stream()
                .map(User::getUsername)
                .toList();
    }
    public CurrentUserDto getCurrentUserByUsername(String username){
        UserDto user = getUserByUsername(username);
        List<FriendDto> friends = friendshipService.getFriendsOfUser(user.getId());
        UserProfileResponseDto profile = userProfileService.findById(user.getId());
        return CurrentUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .bio(profile.getBio())
                .photoUrl(profile.getPhotoUrl())
                .friends(friends)
                .build();
    }
    public UserDto getUserByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+username));
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
