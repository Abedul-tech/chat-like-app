package com.alen.mcsv_websocket.interceptor;

import com.alen.mcsv_websocket.service.WsSessionService;
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

@Component
@RequiredArgsConstructor
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    private final WsSessionService wsSessionService;
    private static final Logger log = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);
    //It is a Spring WebSocket interceptor that authenticates WebSocket connections using a JWT sent in the Authorization header.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //To inspect STOMP headers
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if(accessor == null){ //Checking if accessor is null
            log.warn("Failed to wrap STOMP message: accessor is null");
            return message;
        }
        //Check if it's the initial handshake(if it has command CONNECT)
        //This is the first STOMP frame(to start session) the client send to server after establishing a connection.
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            wsSessionService.embedAuthenticatedUser(accessor);
        } else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
            //Set user session as OFFLINE
        }
        else if (accessor.isHeartbeat()) {
            log.info("TIK");
        }
        return message;
    }
}
