package com.patricksantino.simplifyguitarchords.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplifySongChordRequestDto {
    private String name;
    private int capo;
    private String singer;
    private String genre;
    private String body;
}
