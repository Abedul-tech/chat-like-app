package com.alen.mcsv_message.model.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

//To store only ID of user for faster searching in case the userSession is not in Redis
@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "TemporaryId", timeToLive = 1800) // TTL = 30 minutes (in seconds)
public class TemporaryId  implements Serializable {
    @Id
    private String id;
    @Indexed
    private String username;
}
