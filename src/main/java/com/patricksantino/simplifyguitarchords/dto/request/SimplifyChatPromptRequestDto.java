package com.patricksantino.simplifyguitarchords.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class SimplifyChatPromptRequestDto {
    @JsonProperty("model")
    private String model;

    @JsonProperty("response_format")
    private Map<String, String> responseFormat;

    @JsonProperty("messages")
    private List<ChatPromptMessageDto> messages;
}


