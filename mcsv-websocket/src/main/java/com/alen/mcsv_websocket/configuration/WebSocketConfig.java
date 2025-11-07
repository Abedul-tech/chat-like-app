package com.alen.mcsv_websocket.configuration;


import com.alen.mcsv_websocket.interceptor.WebSocketChannelInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketChannelInterceptor wsChannelInterceptor; // Inject
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //Enables routing for messages from server to clients via a simple broker.(topics to be subscribed by the client)
        config.enableSimpleBroker("/topic","/queue")
                .setHeartbeatValue(new long[]{5000,5000}) //In ms format = 5 seconds
                .setTaskScheduler(heartbeatScheduler());
        //Defines the prefix for messages that come from clients to the server and should be handled by server-side code
        config.setUserDestinationPrefix("/user");// Set prefix for user destinations (for private messages)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
//                .setHandshakeHandler(new CustomHandshakeHandler())// <----Here we configure our principal registered in the websocket session
                .setAllowedOrigins("http://localhost:4200")// KEEP THIS! It's for SockJS internal validation.
                .withSockJS()
                .setHeartbeatTime(5000); //for sockJs fallback
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
        registration.interceptors(wsChannelInterceptor);//Add interceptor
    }

    //Configure Timeout Settings of messages
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        WebSocketMessageBrokerConfigurer.super.configureWebSocketTransport(registry);
        registry.setSendTimeLimit(15 * 1000)   //15 seconds --- Maximum time allowed for sending a message to the client. If it hangs much time, disconnect
                .setSendBufferSizeLimit(512 * 1024)  //Limit of buffer(space where message are stored temporally) when the client cannot receive or it's too slow
                .setTimeToFirstMessage(900 * 1000); //30 minutes --- Time of close connection when client doesn't send any message after establishing connection
    }

    @Bean
    public ThreadPoolTaskScheduler heartbeatScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(20); //For high currency = +10k connections. Monitor CPU usage to tune it
        scheduler.setThreadNamePrefix("ws-heartbeat-"); //Helps with debugging and thread profiling.
        scheduler.setRemoveOnCancelPolicy(true); // Canceled tasks don't hold in memory, just delete them
        scheduler.setErrorHandler(err->log.error("Heartbeat error :",err));
        return scheduler;
    }
}
