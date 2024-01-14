package com.patricksantino.simplifyguitarchords.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomResponseDto<T> {
    private int statusCode;
    private String message;
    private T data;
}
