package com.alen.mcsv_message.resolver;

import com.alen.mcsv_message.client.UserClient;
import com.alen.mcsv_message.dto.UserIDsDto;
import com.alen.mcsv_message.model.redis.TemporaryId;
import com.alen.mcsv_message.service.redis.TemporaryIdService;
import com.alen.mcsv_message.service.redis.UserRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//Used to general interactions with Redis
@Slf4j
@Service
@RequiredArgsConstructor
public class UserIdResolverService {
    private final UserRedisService userRedisService;
    private final TemporaryIdService temporaryIdService;
    private final UserClient userClient;
    public UserIDsDto retrieveIDs(String sender, String receiver){
        String senderId = resolveSenderId(sender);
        String receiverId = resolveReceiverId(receiver);
        return new UserIDsDto(senderId,receiverId);
    }
    //Helper methods
    private String resolveSenderId(String sender){
        return userRedisService.getIdByUsername(sender)
                .orElseThrow(()->new IllegalArgumentException("User ID not found in Redis: " + sender));
    }
    private String resolveReceiverId(String receiver){
        return userRedisService.getIdByUsername(receiver)
                .orElseGet(()->resolveReceiverFallBack(receiver)); //Fallback in case it's not in RedisHash tables(cleaner and lazy-evaluated)
    }

    private String resolveReceiverFallBack(String receiver) {
        return temporaryIdService.getIdByUsername(receiver)
                .map(id -> { //Triggered in case if finds the receiverUsername in the temporary Redis for IDs
                    log.info("ID: {} retrieved from temporary redis", id);
                    return id;
                })
                .orElseGet(() -> { //If it is not in RedisHash tables neither Redis temporary, search in Mysql(Slower only the first time)
                   String idFromDb = userClient.getIdByUsername(receiver).getIdUser();
                    //Store in Redis for the following messages
                    temporaryIdService.save(new TemporaryId(idFromDb,receiver));
                    return idFromDb;
                });
    }
}
