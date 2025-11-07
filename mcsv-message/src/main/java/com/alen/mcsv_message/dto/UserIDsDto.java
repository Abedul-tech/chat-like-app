package com.alen.mcsv_message.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Optional;

//We can create instances of this Dto directly using the constructor(more straightforward and cleaner)
//Avoid Builder because it has just a couple of fields
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIDsDto {
    private String senderId;
    private String receiverId;
}
