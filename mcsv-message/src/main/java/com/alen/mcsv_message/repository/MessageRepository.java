package com.alen.mcsv_message.repository;


import com.alen.mcsv_message.model.Message;
import com.alen.mcsv_message.model.MessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends CassandraRepository<Message, MessageKey> {
}
