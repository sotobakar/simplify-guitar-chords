package com.patricksantino.simplifyguitarchords.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChatPromptMessageDto {
    private String role;
    private String content;
}
