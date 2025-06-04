package com.alen.mcsv_message.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.Instant;

@PrimaryKeyClass
@Data
@Builder
public class MessageKey {
    //partition key->decided where the messages are stored on
    //All messages belonging the same conversation will be stores on together in the same partition
    @PrimaryKeyColumn(name = "conversation_id", type = PrimaryKeyType.PARTITIONED)
    private String conversationId;

    //clustering column->We define how the messages are ordered
    //descending->newer messages appear first
    @PrimaryKeyColumn(name = "sent_at", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant sentAt;
}
