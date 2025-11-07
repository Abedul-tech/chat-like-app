package com.alen.mcsv_websocket.listener.kafka;

import com.alen.dto.MessageBackDto;

import com.alen.dto.OnlineUsersEvent;
import com.alen.mcsv_websocket.service.ChatService;
import com.alen.mcsv_websocket.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ChatService chat;
    private final StatusService statusService;
    @KafkaListener(topics = "msgBack", groupId = "msgBackId")
    public void receiveIncomingMessage(MessageBackDto msgBackDto){
        chat.sendMessageToReceiver(msgBackDto);
    }
    @KafkaListener(topics = "online-users", groupId = "onlineUsersId")
    public void consumeOnlineUsers(OnlineUsersEvent event){
        statusService.broadcastPresence(event);
    }
}
