package com.alen.mcsv_websocket.controller;


import com.alen.mcsv_websocket.service.SessionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
//This comes from Java vanilla
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HeartBeatController {
    private final SessionStatusService sessionStatusService;

    @MessageMapping("/heartBeat")
    public void updateSession(Principal principal){
        sessionStatusService.updateSession(principal);
    }
}
