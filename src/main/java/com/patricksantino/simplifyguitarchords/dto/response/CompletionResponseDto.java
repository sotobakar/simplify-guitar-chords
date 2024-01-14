package com.patricksantino.simplifyguitarchords.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletionResponseDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("choices")
    private ChatChoice[] choices;
}

