package com.alen.mcsv_message.service;

import org.springframework.stereotype.Service;

import com.alen.mcsv_message.repository.MessageRepository;
import com.alen.dto.MessageDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void saveMessage(MessageDto messageDto){

    }
}
