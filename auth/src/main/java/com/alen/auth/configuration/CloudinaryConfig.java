package com.alen.auth.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary(){
        final Map<String, String> config = new HashMap<>();
        config.put("cloud_name","drevqfcsh");
        config.put("api_key", "633364318986149");
        config.put("api_secret", "auafoyZpDv8RMfs4LerR1Mgxflg");
        return new Cloudinary(config);
    }
}
