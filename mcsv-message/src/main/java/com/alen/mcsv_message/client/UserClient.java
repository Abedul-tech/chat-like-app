package com.alen.mcsv_message.client;

import com.alen.mcsv_message.dto.UserIdDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mcsv-auth", url = "http://localhost:8080")
public interface UserClient {
    @GetMapping("/api/user/by-username")
    UserIdDto getIdByUsername(@RequestParam("username") String username);
}
