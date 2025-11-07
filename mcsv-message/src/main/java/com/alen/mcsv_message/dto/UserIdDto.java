package com.alen.mcsv_message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Store ID retrieved from Mysql using the username(Class UserClient)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIdDto {
    private String idUser;
}
