package com.alen.mcsv_message.model.cassandra;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.time.Instant;
import java.util.UUID;

@PrimaryKeyClass
@Data
@Builder
public class MessageKey {
    //Messages are sorted by time, with the most recent first__________________
    @PrimaryKeyColumn(name = "conversation_id", type = PrimaryKeyType.PARTITIONED)
    private UUID conversationId;
    //clustering key-> Where we define how the messages are ordered(ascending or descending)
    @PrimaryKeyColumn(name = "sent_at", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant sentAt;
}
