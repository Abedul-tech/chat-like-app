package com.alen.mcsv_message.service;

import com.alen.dto.MessageDto;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
//Why service? Spring needs to register this class as a bean to activate the listener
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final MessageService messageService;
    @KafkaListener(topics = "chat", groupId = "chatId")
    public void saveIncomingMessage(MessageDto messageDto){
        LOGGER.info("Received message:{}", messageDto);
        messageService.saveMessage(messageDto);
    }
}
