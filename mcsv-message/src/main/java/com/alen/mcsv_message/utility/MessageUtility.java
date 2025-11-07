package com.alen.mcsv_message.utility;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MessageUtility {
    public static UUID buildConversationId(String user1, String user2){
        /*   If both IDs are identical, it returns 0
        *   If the first user has a LOWER UNICODE value than user2, returns a negative number -> meaning user1 go first
        *   If it's higher, it returns a positive number -> meaning user 2 goes first
         * */
        String combined = user1.compareTo(user2)< 0 ? user1 + user2 : user2 + user1;
        // We generate the same UUID based on the combination of these two users to keep consistency. Same result no matter who starts the chat
        return UUID.nameUUIDFromBytes(combined.getBytes(StandardCharsets.UTF_8));
    }
}
