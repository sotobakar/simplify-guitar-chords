package com.patricksantino.simplifyguitarchords.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatChoice {
    @JsonProperty("index")
    private int index;

    @JsonProperty("message")
    private ChatMessage message;

}
