package com.alen.mcsv_message.repository.redis;

import com.alen.mcsv_message.model.redis.UserSession;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {
    //To search by indexed field which is username
    Optional<UserSession> findByUsername(@Param("username") String username);
}

