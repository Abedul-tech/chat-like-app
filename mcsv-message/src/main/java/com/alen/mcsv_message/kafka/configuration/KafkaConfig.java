package com.alen.mcsv_message.kafka.configuration;


import com.alen.dto.MessageBackDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaTemplate<String, MessageBackDto> msgBackKafkaTemplate(ProducerFactory<String, MessageBackDto> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
}
