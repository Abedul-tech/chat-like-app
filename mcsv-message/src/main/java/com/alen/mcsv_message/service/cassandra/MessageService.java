package com.alen.mcsv_message.service.cassandra;

import com.alen.dto.MessageDto;
import com.alen.mcsv_message.dto.MsgHistoryDto;
import com.alen.mcsv_message.dto.MsgHistoryParametersDto;
import com.alen.mcsv_message.dto.PendingMsgDto;
import com.alen.mcsv_message.dto.UserIDsDto;
import com.alen.mcsv_message.model.cassandra.Message;
import com.alen.mcsv_message.model.cassandra.MessageKey;
import com.alen.mcsv_message.model.cassandra.MessageStatus;
import com.alen.mcsv_message.resolver.UserIdResolverService;
import com.alen.mcsv_message.service.redis.UserRedisService;
import com.alen.mcsv_message.utility.MessageUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.alen.mcsv_message.repository.cassandra.MessageRepository;


import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserIdResolverService userIdResolverService;
    private final UserRedisService userRedisService;
    public void saveMessage(MessageDto messageDto, MessageStatus status){
        UserIDsDto IDs = userIdResolverService.retrieveIDs(messageDto.getSender(),messageDto.getReceiver());
        //Storing in cassandra
        UUID conversationId = MessageUtility.buildConversationId(IDs.getSenderId(),IDs.getReceiverId());
        MessageKey key = MessageKey.builder()
                .conversationId(conversationId)
                .sentAt(Instant.now())
                .build();
        Message message = Message.builder()
                .key(key)
                .senderId(UUID.fromString(IDs.getSenderId()))
                .receiverId(UUID.fromString(IDs.getReceiverId()))
                .content(messageDto.getContent())
                .status(status)
                .build();
        messageRepository.save(message);
    }
    public Message updateMessageStatus(MessageKey key, MessageStatus status){
        // Find the message by its composite key
        return messageRepository.findById(key)
                .map(message -> {
                    message.setStatus(status);
                    return messageRepository.save(message);})
                .orElseThrow(() -> new EntityNotFoundException(
                        "Message not found with conversationId: " + key.getConversationId() +
                                " and sentAt: " + key.getSentAt()));
    }
    // sentAt = Fetches messages older than the given sentAt
    public List<MsgHistoryDto> getMessages(MsgHistoryParametersDto parameters){
        UserIDsDto IDs = userIdResolverService.retrieveIDs(parameters.getSender(),parameters.getReceiver());
        UUID conversationId = MessageUtility.buildConversationId(IDs.getSenderId(),IDs.getReceiverId());
        if(parameters.getUntilTime()==null){
            List<Message> messages = messageRepository.findFirstPage(conversationId, parameters.getPageSize());
            return messageStream(messages);
        }else{
            List<Message> messages = messageRepository.findNextPage(conversationId,parameters.getUntilTime(), parameters.getPageSize());
            return messageStream(messages);
        }
    }
    public void setMessagesAsSent(UUID receiverId) {
        List<Message> messages = getPendingMessages(receiverId);
        if (messages != null && !messages.isEmpty()) {
            for (Message msg : messages) {
                msg.setStatus(MessageStatus.SENT);
                messageRepository.save(msg);
            }
        }
    }
    //Helper method
    private List<MsgHistoryDto> messageStream(List<Message> messages){
        return messages.stream()
                .map(msg -> MsgHistoryDto
                        .builder()
                        .content(msg.getContent())
                        .sender(userRedisService.getUsernameById(msg.getSenderId()))
                        .sentAt(msg.getKey().getSentAt())
                        .build())
                .collect(Collectors.toList());
    }
    private List<Message> getPendingMessages(UUID receiverId){
        return messageRepository.getPendingMessagesByReceiverId(receiverId);
    }
}
