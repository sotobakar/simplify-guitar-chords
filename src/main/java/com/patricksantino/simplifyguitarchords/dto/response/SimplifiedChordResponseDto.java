package com.patricksantino.simplifyguitarchords.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplifiedChordResponseDto {
    @JsonProperty("capo")
    private int capo;

    @JsonProperty("body")
    private String body;

    @JsonProperty("open_chords")
    private int openChords;

    @JsonProperty("barre_chords")
    private int barreChords;

    @JsonProperty("chord_transitions")
    private int chordTransitions;

    @JsonProperty("finger_placement")
    private int fingerPlacement;

    @JsonProperty("difficulty")
    private int difficulty;
}
