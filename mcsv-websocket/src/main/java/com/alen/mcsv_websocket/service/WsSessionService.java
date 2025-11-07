package com.alen.mcsv_websocket.service;

import com.alen.mcsv_websocket.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WsSessionService {
    private final JwtUtil jwtUtil;
    public void embedAuthenticatedUser(StompHeaderAccessor accessor){
        //We don't check the JWT cause this "extraction" happens just right away its creation
        //Here we can implement future token verifications
        try {
            String token = accessor.getFirstNativeHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                //Parse and return Authentication object with user's data to be stored in StompHeaderAccessor using accessor.setUser(authentication), making it available during the ws session.
                Authentication authentication = jwtUtil.getAuthenticationFromToken(token);
                //Once set via accessor.setUser(authentication), your WebSocket listener can later use headers.getUser().getName() to get the username during SessionConnectedEvent.
                accessor.setUser(authentication); //Principal created
                //Spring uses this user identity to associate all future messages from this session with that Principal.
                System.out.println("WsSessionService: User setted in session");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error when embedding user into ws session: ",e);
        }
    }
}
