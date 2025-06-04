package com.alen.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//for getter and setters
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    //When serializing your TokenContainer object into JSON, Jackson calls the getToken() method to retrieve the value of the token field.
    private String token;
}