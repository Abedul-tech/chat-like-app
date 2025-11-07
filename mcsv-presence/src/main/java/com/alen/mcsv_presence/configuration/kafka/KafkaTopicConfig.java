package com.alen.mcsv_presence.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic onlineTopic(){
        return TopicBuilder.name("online-users")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic chatTopic(){
        return TopicBuilder.name("chat")
                .partitions(3)
                .replicas(2)
                .build();
    }
    @Bean
    public NewTopic sessionTopic(){
        return TopicBuilder.name("session")
                .partitions(3)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic heartbeatTopic(){
        return TopicBuilder.name("heartbeat")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic receiverStatusTopic(){
        return TopicBuilder.name("msgBack")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
