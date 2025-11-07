package com.alen.mcsv_presence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private String id;
    private String username;
    private String status;
    private Instant lastSeenAt;
}
