package com.alen.mcsv_message.utility;

import org.springframework.stereotype.Component;

@Component
public class MessageUtility {
    public static String buildConversationId(Long user1, Long user2){
        //Always put the lowest id first to ensure consistency
        return user1<user2 ? user1+"_"+user2 : user2+"_"+user1;
    }
}
