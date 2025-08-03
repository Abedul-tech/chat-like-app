package com.alen.mcsv_message.service;

import com.alen.dto.MessageDto;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "chat", groupId = "chatId")
    public void saveIncomingMessage(MessageDto messageDto){

    }
}
