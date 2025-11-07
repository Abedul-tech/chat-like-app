package com.alen.auth.controller;

import com.alen.auth.dto.CurrentUserDto;
import com.alen.auth.dto.UserDto;
import com.alen.auth.dto.UserIdDto;
import com.alen.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/")
public class UserController {
    private final UserService userService;
    @GetMapping(value = "get-id-by-username")
    public ResponseEntity<UserIdDto> getId(@RequestParam String username) {
        return ResponseEntity.ok(userService.getIdByUsername(username));
    }
    @GetMapping(value = "get-current-session/{username}")
    public ResponseEntity<CurrentUserDto> getCurrentUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getCurrentUserByUsername(username));
    }
    @GetMapping(value = "get-user-by-username/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @GetMapping(value = "get-users-by-username/{username}")
    public  ResponseEntity<List<String>> getUsers(@PathVariable String username){
        List<String> users = userService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }
}
