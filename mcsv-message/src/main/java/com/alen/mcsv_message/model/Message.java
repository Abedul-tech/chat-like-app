package com.alen.mcsv_message.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table("message")
@Data
@Builder
public class Message {
    @PrimaryKey
    private MessageKey key;

    @Column("sender_id")
    private Long senderId;

    @Column("receiver_id")
    private Long receiverId;

    @Column("content")
    private String content;

    @Column("status")
    private MessageStatus status;

    public enum MessageStatus {
        SENT, DELIVERED, READ
    }
}
