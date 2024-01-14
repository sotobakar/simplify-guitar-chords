package com.patricksantino.simplifyguitarchords.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private String content;
}

