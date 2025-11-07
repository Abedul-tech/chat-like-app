package com.alen.mcsv_message.processor;

import com.alen.dto.MessageBackDto;
import com.alen.dto.MessageDto;
import com.alen.mcsv_message.model.cassandra.MessageStatus;
import com.alen.mcsv_message.service.cassandra.MessageService;
import com.alen.mcsv_message.service.redis.UserRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class IncomingMessageProccesor {
    private final MessageService messageService;
    private final UserRedisService userRedisService;
    private final KafkaTemplate<String, MessageBackDto> receiverStatusTemplate;
    public void proccess(MessageDto messageDto){
        boolean isReceiverOnline = userRedisService.findOnlineSessionByUsername(messageDto.getReceiver()).isPresent();
        if(isReceiverOnline){
            messageService.saveMessage(messageDto, MessageStatus.SENT);
            receiverStatusTemplate.send("msgBack",new MessageBackDto(messageDto.getSender(),messageDto.getReceiver(), messageDto.getContent(), Instant.now()));
            System.out.println("MESSAGE IN ONLINE FRIEND PROCESS "+ messageDto.getContent());
        }else{
            messageService.saveMessage(messageDto,MessageStatus.PENDING);
            receiverStatusTemplate.send("msgBack",new MessageBackDto(messageDto.getSender(),messageDto.getReceiver(), messageDto.getContent(), Instant.now()));
            log.info("MESSAGE AS PENDING<<<<<<<<<<<<");
        }
    }
}
