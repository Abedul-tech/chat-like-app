package com.alen.mcsv_websocket.service;


import com.alen.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    //TO define what values will receive each instance. If I don't put, it may throw runtime errors
    //Properties defined in the config yaml
    private final KafkaTemplate<String,MessageDto> kafkaTemplate;
    public void sendMessage(MessageDto messageDto){
        kafkaTemplate.send("chat",messageDto);
    }
}
