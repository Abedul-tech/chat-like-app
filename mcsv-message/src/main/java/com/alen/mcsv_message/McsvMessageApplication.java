package com.alen.mcsv_message;

import com.alen.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class McsvMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsvMessageApplication.class, args);
	}

	@KafkaListener(topics = "chat", groupId = "chatId")
	public void handleMessage(MessageDto messageDto){
		log.info("Received message:{}", messageDto);
	}

}
