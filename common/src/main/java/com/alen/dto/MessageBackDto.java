package com.alen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBackDto {
    private String sender;
    private String receiver;
    private String content;
    private Instant sentAt;
}
