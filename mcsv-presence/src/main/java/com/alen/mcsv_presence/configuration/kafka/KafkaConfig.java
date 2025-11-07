package com.alen.mcsv_presence.configuration.kafka;

import com.alen.dto.OnlineUsersEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaTemplate<String, OnlineUsersEvent> onlineUsersKafkaTemplate(ProducerFactory<String, OnlineUsersEvent> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
}
