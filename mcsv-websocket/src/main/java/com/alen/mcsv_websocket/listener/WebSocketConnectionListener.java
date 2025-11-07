package com.alen.mcsv_websocket.listener;

import com.alen.mcsv_websocket.interceptor.WebSocketChannelInterceptor;
import com.alen.mcsv_websocket.service.PresenceService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);
    private final PresenceService presenceService;
    //Event that react when successfully connection
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Register user in Redis
    //Triggered when new STOMP WebSocket session is connected
    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event){
        //Here we broadcast the user status to all their contacts(in the service PRESENCE)
        presenceService.sendUser(event);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Delete user from Redis
    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event){
        //If the server doesn't receive 2 heartbeat interval, it assumes the client is dead
        log.info("------------------------Session DISCONECTED");
        //Here we also broadcast the user status to all their contacts(in the service PRESENCE)
        presenceService.updateSession(event);
    }
}
