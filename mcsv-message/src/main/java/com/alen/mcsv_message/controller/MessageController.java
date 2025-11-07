package com.alen.mcsv_message.controller;

import com.alen.mcsv_message.dto.MsgHistoryDto;
import com.alen.mcsv_message.dto.MsgHistoryParametersDto;
import com.alen.mcsv_message.dto.PendingMsgDto;
import com.alen.mcsv_message.model.cassandra.Message;
import com.alen.mcsv_message.service.cassandra.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    @GetMapping("/history/{sender}/{receiver}/{untilTime}")
    public ResponseEntity<List<MsgHistoryDto>> geMessageHistory(@PathVariable String sender,
                                                                @PathVariable String receiver,
                                                                @PathVariable Instant untilTime){
        MsgHistoryParametersDto parameters = MsgHistoryParametersDto.builder()
                .sender(sender)
                .receiver(receiver)
                .untilTime(untilTime)
                .build();
        return ResponseEntity.ok(messageService.getMessages(parameters));
    }
    @PostMapping(value = "/set-msgs-as-sent/{receiverId}")
    public ResponseEntity<String> setMsgAsSent(@PathVariable UUID receiverId){
        messageService.setMessagesAsSent(receiverId);
        return ResponseEntity.ok("Completed");
    }
}
