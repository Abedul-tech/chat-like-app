package com.alen.auth.controller;

import com.alen.auth.dto.UserProfileRequest;
import com.alen.auth.dto.UserProfileResponseDto;
import com.alen.auth.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/{userId}/profile")
    public ResponseEntity<UserProfileResponseDto> createProfile(
            @PathVariable UUID userId,
            @RequestBody UserProfileRequest request
            ){
        UserProfileResponseDto response = userProfileService.saveOrUpdate(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
