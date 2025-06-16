package com.alen.mcsv_websocket.security.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //.cors(cors -> cors.configurationSource(corsConfigurationSource())) // <--- Important
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ws/**").permitAll()//no problem here
                        .anyRequest().authenticated())
                .build();
    }
    /*
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        // IMPORTANT: For allowedMethods, DO NOT include GET and POST if the Gateway handles them.
        // Or be very specific. However, if this is causing duplication, one trick is to
        // make this *backend* CorsConfigurationSource ONLY allow OPTIONS (the preflight).
        // IMPORTANT: Add GET and POST back here! SockJS uses them.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS")); // <-- CHANGE THIS LINE
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/ws/**", configuration); // Apply to WebSocket paths
        return source;
    }*/

}
