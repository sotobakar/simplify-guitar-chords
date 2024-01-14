package com.patricksantino.simplifyguitarchords.controller;

import com.patricksantino.simplifyguitarchords.dto.request.SimplifySongChordRequestDto;
import com.patricksantino.simplifyguitarchords.dto.response.CustomResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/songs")
public class SongController {
    @PostMapping("/simplify")
    public ResponseEntity<CustomResponseDto<Object>> simplify(@RequestBody SimplifySongChordRequestDto simplifySongChordRequestDto) {
        // TODO: Hit service
        return null;
    }
}
