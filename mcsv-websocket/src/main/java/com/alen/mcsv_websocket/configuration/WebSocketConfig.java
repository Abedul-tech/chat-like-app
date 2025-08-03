package com.alen.mcsv_websocket.configuration;

import com.alen.mcsv_websocket.jwt.JwtWebSocketInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtWebSocketInterceptor jwtWebSocketInterceptor; // Inject
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
//                .setHandshakeHandler(new CustomHandshakeHandler())// <----Here we configure our principal registered in the websocket session
                .setAllowedOrigins("http://localhost:4200")// KEEP THIS! It's for SockJS internal validation.
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
        registration.interceptors(jwtWebSocketInterceptor);//Add interceptor
    }

    //Configure Timeout Settings of messages
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
        registry.setSendTimeLimit(15 * 1000)   //15 seconds --- Maximum time allowed for sending a message to the client. If it hangs much time, disconnect
                .setSendBufferSizeLimit(512 * 1024)  //Limit of buffer(space where message are stored temporally) when the client cannot receive or it's too slow
                .setTimeToFirstMessage(900 * 1000); //30 minutes --- Time of close connection when client doesn't send any message after establishing connection
    }
}
