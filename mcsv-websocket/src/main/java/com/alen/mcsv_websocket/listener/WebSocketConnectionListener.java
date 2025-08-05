package com.alen.mcsv_websocket.listener;

import com.alen.mcsv_websocket.service.UserRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


//Only load this bean if the property feature.enabled is set to true.
@ConditionalOnProperty(name = "feature.enabled", havingValue = "false")
//Only works if the WebSocket connection has been secured and authenticated beforehand.
@Component
@RequiredArgsConstructor
public class WebSocketConnectionListener {

    private final UserRedisService userRedisService;
    //Event that react when successfully connection
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Register user in Redis
    //Triggered when new STOMP WebSocket session is connected
    @EventListener
    public void handleWebSocketConnectionListener(SessionConnectedEvent event){
        userRedisService.registerUser(event);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Delete user from Redis
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        //pending
    }
}
