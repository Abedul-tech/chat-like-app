package com.alen.mcsv_websocket.service;


import com.alen.dto.MessageBackDto;
import com.alen.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    //To define what values will receive each instance. If I don't put, it may throw runtime errors
    //Properties defined in the config yaml
    private final KafkaTemplate<String,MessageDto> messageKafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    public void storeMessageInCassandra(MessageDto messageDto){
        messageKafkaTemplate.send("chat",messageDto);
    }
    public void sendMessageToReceiver(MessageBackDto msg){
        System.out.println("message about to send to client");
        messagingTemplate.convertAndSendToUser(msg.getReceiver(), "/queue/private", msg);

        messagingTemplate.convertAndSendToUser(msg.getSender(), "/queue/private", msg);
        System.out.println("message sent to client");
    }
}
