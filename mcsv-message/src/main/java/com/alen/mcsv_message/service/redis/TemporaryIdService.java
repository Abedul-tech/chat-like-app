package com.alen.mcsv_message.service.redis;

import com.alen.mcsv_message.model.redis.TemporaryId;
import com.alen.mcsv_message.repository.redis.TemporaryIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemporaryIdService {
    private final TemporaryIdRepository temporaryIdRepository;
    public Optional<String> getIdByUsername(String username){
        return temporaryIdRepository.findByUsername(username).map(TemporaryId::getId);
    }
    public void save(TemporaryId temporaryId){
        temporaryIdRepository.save(temporaryId);
    }
}
