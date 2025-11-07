package com.alen.mcsv_presence.controller;

import com.alen.mcsv_presence.dto.SessionDto;
import com.alen.mcsv_presence.service.redis.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final UserSessionService userSessionService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<SessionDto> getSessionByUserId(@PathVariable String userId) {
        SessionDto session = userSessionService.getSession(userId);
        return ResponseEntity.ok(session);
    }
}