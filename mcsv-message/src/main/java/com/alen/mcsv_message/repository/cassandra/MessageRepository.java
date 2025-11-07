package com.alen.mcsv_message.repository.cassandra;

import com.alen.mcsv_message.model.cassandra.Message;
import com.alen.mcsv_message.model.cassandra.MessageKey;
import com.alen.mcsv_message.model.cassandra.MessageStatus;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
    //Latest MESSAGES
    @Query("SELECT * FROM message WHERE conversation_id = ?0 LIMIT ?1")
    List<Message> findFirstPage(UUID conversationId, int pageSize);

    @Query("SELECT * FROM message WHERE conversation_id = ?0 AND sent_at > ?1 LIMIT ?2")
    List<Message> findNextPage(UUID conversationId, Instant sentAt, int pageSize);

    @Query("SELECT * FROM message WHERE receiver_id = ?0 AND status = 'PENDING' ALLOW FILTERING")
    List<Message> getPendingMessagesByReceiverId(UUID receiverId);


}
