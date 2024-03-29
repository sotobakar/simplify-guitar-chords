package com.patricksantino.simplifyguitarchords.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChatPromptMessageDto {
    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private String content;
}
