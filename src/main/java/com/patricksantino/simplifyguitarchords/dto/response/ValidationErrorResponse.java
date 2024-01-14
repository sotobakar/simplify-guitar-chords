package com.patricksantino.simplifyguitarchords.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationErrorResponse extends ErrorResponse{
    private Map<String, List<String>> errors = new HashMap<>();
}
