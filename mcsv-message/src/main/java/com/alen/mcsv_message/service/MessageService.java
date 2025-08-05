package com.alen.mcsv_message.service;

import com.alen.dto.MessageDto;
import com.alen.mcsv_message.client.UserClient;
import com.alen.mcsv_message.model.Message;
import com.alen.mcsv_message.model.MessageKey;
import com.alen.mcsv_message.utility.MessageUtility;
import org.springframework.stereotype.Service;

import com.alen.mcsv_message.repository.MessageRepository;


import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRedisService userRedisService;
    private final UserClient userClient;
    public void saveMessage(MessageDto messageDto){
        //Get ID of sender from Redis
        String receiver;
        String sender = userRedisService.getIdByUsername(messageDto.getSender()).orElseThrow(()->new RuntimeException("User not found"));
        boolean isReceiverInRedis = userRedisService.getIdByUsername(messageDto.getReceiver()).isPresent();
        //If it's in redis
        if(isReceiverInRedis){
            receiver = String.valueOf(userRedisService.getIdByUsername(messageDto.getReceiver()));
        }else{
            //If not retrieve from mysql database
            receiver = userClient.getIdByUsername(messageDto.getReceiver()).getIdUser();
            //Store in redis for future quickly search (PENDING)

        }
        //Storing in cassandra
        UUID conversationId = MessageUtility.buildConversationId(sender,receiver);
        MessageKey key = MessageKey.builder()
                .conversationId(conversationId)
                .sentAt(Instant.now())
                .build();
        Message message = Message.builder()
                .key(key)
                .senderId(UUID.fromString(sender))
                .receiverId(UUID.fromString(receiver))
                .content(messageDto.getContent())
                .status(Message.MessageStatus.SENT)
                .build();
        messageRepository.save(message);
    }
}
