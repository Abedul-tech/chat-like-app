package com.alen.mcsv_websocket.controller;

import com.alen.mcsv_websocket.dto.AuthMessageDto;
import com.alen.mcsv_websocket.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @MessageMapping("/auth")
    public void authenticate(AuthMessageDto message, StompHeaderAccessor accessor){
        authService.authenticate(message,accessor);
    }
}
