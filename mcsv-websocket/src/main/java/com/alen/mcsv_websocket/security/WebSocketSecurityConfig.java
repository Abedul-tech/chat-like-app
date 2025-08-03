package com.alen.mcsv_websocket.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;


//Spring Security for WebSocket STOMP messages.
@ConditionalOnProperty(name = "feature.enabled", havingValue = "true")
@Configuration
@EnableWebSocketSecurity
public class WebSocketSecurityConfig{
    //It's concerned with authorizing messages once they arrive
    @Bean
    public AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages){
        //Spring’s simpDestMatchers() matches against the destination path after stripping applicationDestinationPrefixes for example: /app/.
        //Everything that goes after this prefix, can be configured here
        return messages
                .simpDestMatchers("/chat/**").permitAll()
                .anyMessage().authenticated()
                .build();

    }
}
