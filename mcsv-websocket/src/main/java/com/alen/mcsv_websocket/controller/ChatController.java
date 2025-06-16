package com.alen.mcsv_websocket.controller;

import com.alen.dto.MessageDto;
import com.alen.mcsv_websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller  // Correct annotation for WebSocket controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendToUser("/queue/messages")
    public MessageDto sendMessage(@Payload MessageDto messageDto, Principal principal) {
        return messageDto;
    }

    @MessageMapping("/chat.sendPrivateMessage")
    //Inside the service it will require the SimpMessagingTemplate.convertAndSendToUser()
    public void sendPrivateMessage(@Payload MessageDto messageDto) {
        chatService.sendMessage(messageDto);
    }
}
