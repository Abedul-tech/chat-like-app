package com.alen.mcsv_websocket.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //Enables routing for messages from server to clients via a simple broker.
        config.enableSimpleBroker("/topic","/queue");
        //Defines the prefix for messages that come from clients to the server and should be handled by server-side code
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new CustomHandshakeHandler())// <----Here we configure our principal registered in the websocket session
                .setAllowedOrigins("http://localhost:4200")// KEEP THIS! It's for SockJS internal validation.
                .withSockJS();
    }
}
