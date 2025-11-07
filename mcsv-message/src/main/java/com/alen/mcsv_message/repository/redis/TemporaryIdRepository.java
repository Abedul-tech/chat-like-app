package com.alen.mcsv_message.repository.redis;

import com.alen.mcsv_message.model.redis.TemporaryId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryIdRepository extends CrudRepository<TemporaryId, String> {
    Optional<TemporaryId> findByUsername(@Param("username") String username);
}
