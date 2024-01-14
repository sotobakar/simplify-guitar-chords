package com.patricksantino.simplifyguitarchords.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomPageResponseDto<T> extends CustomResponseDto<T> {
    private int page;
    private int limit;
    private Long count;
}
