package com.alen.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserDto {
    private UUID id;
    private String username;
    private String bio;
    private String photoUrl;
    private List<FriendDto> friends;
}
