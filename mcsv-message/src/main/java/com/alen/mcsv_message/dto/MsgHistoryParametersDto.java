package com.alen.mcsv_message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgHistoryParametersDto {
    @NotBlank
    private String sender;
    @NotBlank
    private String receiver;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Instant untilTime; //Can be null
    @Builder.Default
    @Min(1)
    private int pageSize = 10;
}
