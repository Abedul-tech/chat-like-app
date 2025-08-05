package com.alen.mcsv_websocket.jwt;

import com.alen.mcsv_websocket.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


// ChannelInterceptor => Lets you intercept STOMP/WebSocket messages on the channel layer.

@ConditionalOnProperty(name = "feature.enabled", havingValue = "false")
@Component
@RequiredArgsConstructor
public class JwtWebSocketInterceptor implements ChannelInterceptor {
    private static final Logger log = LoggerFactory.getLogger(JwtWebSocketInterceptor.class);
    private final JwtUtil jwtUtil;
    //It is a Spring WebSocket interceptor that authenticates WebSocket connections using a JWT sent in the Authorization header.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //To inspect STOMP headers
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //Check if it's the initial handshake(if it has command CONNECT)
        //This is the first STOMP frame(to start session) the client send to server after establishing a connection.
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            try {
                String token = accessor.getFirstNativeHeader("Authorization");
                if (token != null && token.startsWith("Bearer ")) {
                    token = token.substring(7);
                    //Parse and return Authentication object with user's data to be stored in StompHeaderAccessor using accessor.setUser(authentication), making it available during the ws session.
                    Authentication authentication = getAuthenticationFromToken(token);
                    //Once set via accessor.setUser(authentication), your WebSocket listener can later use headers.getUser().getName() to get the username during SessionConnectedEvent.
                    accessor.setUser(authentication);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return message;
    }
    private Authentication getAuthenticationFromToken(String token){
        String username = jwtUtil.getSubject(token);
        String userId = jwtUtil.getUserId(token);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                jwtUtil.getRoles(token)
        );
        auth.setDetails(userId); // Set extra metadata like userId, we can also do it using a hashmap
        return auth; // Return the fully constructed Authentication object
    }
}
