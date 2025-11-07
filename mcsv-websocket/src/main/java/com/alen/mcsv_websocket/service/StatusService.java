package com.alen.mcsv_websocket.service;

import com.alen.dto.OnlineUsersEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final SimpMessagingTemplate messagingTemplate;
    public void broadcastPresence(OnlineUsersEvent event){
        messagingTemplate.convertAndSend("/topic/presence",event);
    }
}
