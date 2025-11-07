package com.alen.mcsv_message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgHistoryDto {
    private String content;
    private String sender;
    private Instant sentAt;
}
