package com.patricksantino.simplifyguitarchords.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class SimplifyChatPromptRequestDto {
    private String model;

    private Map<String, String> responseType;

    private List<ChatPromptMessageDto> messages;
}


