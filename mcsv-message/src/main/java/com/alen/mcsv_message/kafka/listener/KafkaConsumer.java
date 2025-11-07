package com.alen.mcsv_message.kafka.listener;

import com.alen.dto.MessageDto;

import com.alen.mcsv_message.processor.IncomingMessageProccesor;
import com.alen.mcsv_message.service.cassandra.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
//Why service? Spring needs to register this class as a bean to activate the listener
@RequiredArgsConstructor
public class KafkaConsumer {
    private final IncomingMessageProccesor msgProcessor;
    @KafkaListener(topics = "chat", groupId = "chatId")
    public void receiveIncomingMessage(MessageDto messageDto){
        msgProcessor.proccess(messageDto);
    }
}
