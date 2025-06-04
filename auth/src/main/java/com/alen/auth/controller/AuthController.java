package com.alen.auth.controller;

import com.alen.auth.dto.LoginDto;
import com.alen.auth.dto.RegisterDto;
import com.alen.auth.dto.TokenDto;
import com.alen.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "login", produces = "application/json")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto user){
        return ResponseEntity.ok(authService.login(user));
    }
    @PostMapping(value="register")
    public ResponseEntity<TokenDto> register(@RequestBody RegisterDto register){
        return ResponseEntity.ok(authService.register(register));
    }
    @PostMapping(value="validate")
    public boolean validateToken(@RequestBody TokenDto tokenDto){
        return authService.validateToken(tokenDto);
    }
}
