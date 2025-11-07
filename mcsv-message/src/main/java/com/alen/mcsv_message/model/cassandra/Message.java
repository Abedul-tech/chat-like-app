package com.alen.mcsv_message.model.cassandra;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table("message")
public class Message {
    @PrimaryKey
    private MessageKey key;

    @Column("sender_id")
    private UUID senderId;

    @Column("receiver_id")
    private UUID receiverId;

    @Column("content")
    private String content;

    @Column("status")
    private MessageStatus status;
}
