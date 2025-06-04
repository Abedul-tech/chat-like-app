package com.alen.mcsv_websocket.service;

import com.alen.mcsv_websocket.dto.AuthMessageDto;
import com.alen.mcsv_websocket.security.util.JwtUtil;
import com.alen.mcsv_websocket.security.util.SessionRegistry;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final SessionRegistry sessionRegistry;

    //Class helps you to interact with STOMP headers
    public void authenticate (AuthMessageDto message, StompHeaderAccessor accessor){
        String token = message.getToken();
        try{
            //Fix this
            String userId = jwtUtil.getSubject(token);

            String sessionId = accessor.getSessionId();

            //Storing in registry
            sessionRegistry.registerSession(sessionId,userId);
            System.out.println("Authenticated user " + userId + " for session " + sessionId);

            //Storing in security context
            if(SecurityContextHolder.getContext().getAuthentication()==null){
                SecurityContextHolder.getContext().setAuthentication(getAuthObject(token));
            }

        }catch(JwtException e){
            System.out.println("SHREK: "+e);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthObject(String token){
        String username = jwtUtil.getSubject(token);
        List<String> roles = jwtUtil.getRoles(token);
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role->new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username,null, authorities);
    }
}
