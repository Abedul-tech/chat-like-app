package com.alen.auth.controller;

import com.alen.auth.dto.UserIdDto;
import com.alen.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/")
public class UserController {
    private final UserService userService;
    @GetMapping(value = "by-username")
    public ResponseEntity<UserIdDto> getId(@RequestParam String username) {
        return ResponseEntity.ok(userService.getIdByUsername(username));
    }
}
