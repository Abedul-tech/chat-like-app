package com.alen.mcsv_websocket.configuration;

import com.alen.dto.HeartbeatDto;
import com.alen.dto.MessageDto;
import com.alen.dto.UserSessionDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
//CONFIGURATION OF KAFKA PRODUCERS
@Configuration
public class KafkaConfig {
    @Bean
    public KafkaTemplate<String, MessageDto> messageKafkaTemplate(ProducerFactory<String,MessageDto> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
    @Bean
    public KafkaTemplate<String, UserSessionDto> sessionKafkaTemplate(ProducerFactory<String,UserSessionDto> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
    @Bean
    public KafkaTemplate<String, HeartbeatDto> heartbeatKafkaTemplate(ProducerFactory<String,HeartbeatDto> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
}
