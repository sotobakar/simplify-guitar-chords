package com.patricksantino.simplifyguitarchords.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SimplifySongChordRequestDto {
    @NotNull
    private String name;

    @NotNull
    @Min(0)
    @Max(15)
    private int capo;

    @NotNull
    @Length(min = 0, max = 200)
    private String singer;

    @NotNull
    @Length(min = 3, max = 100)
    private String genre;

    @NotNull
    private String body;
}
