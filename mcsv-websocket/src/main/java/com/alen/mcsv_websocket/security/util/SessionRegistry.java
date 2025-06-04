package com.alen.mcsv_websocket.security.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionRegistry {
    //new ConcurrentHashMAp -> Means that multiple threads can safely read and write
    //Used to handle users properly
    private final Map<String,String> sessionUserMap = new ConcurrentHashMap<>();

    public void registerSession(String sessionId, String userId){
        sessionUserMap.put(sessionId,userId);
    }
    public String getUserId(String sessionId){
        return sessionUserMap.get(sessionId);
    }
    public void removeSession(String sessionId){
        sessionUserMap.remove(sessionId);
    }
}
