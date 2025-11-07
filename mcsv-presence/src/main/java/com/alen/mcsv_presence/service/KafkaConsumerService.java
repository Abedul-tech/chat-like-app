package com.alen.mcsv_presence.service;

import com.alen.dto.HeartbeatDto;
import com.alen.dto.UserSessionDto;
import com.alen.mcsv_presence.service.redis.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final UserSessionService userSessionService;
    @KafkaListener(topics = "session", groupId = "sessionId")
    public void saveIncomingSession(UserSessionDto sessionDto){
        LOGGER.info(">>>>>session arrived the presence-service: {}",sessionDto);
        userSessionService.save(sessionDto);
    }
    @KafkaListener(topics = "heartbeat", groupId = "heartbeatId")
    public void saveIncomingHeartbeat(HeartbeatDto heartbeatDto){
        LOGGER.info(">>>>>session was closed in presence-service: {}",heartbeatDto);
        userSessionService.updateStatus(heartbeatDto.getIdUser(),heartbeatDto.getStatus());
    }
}
