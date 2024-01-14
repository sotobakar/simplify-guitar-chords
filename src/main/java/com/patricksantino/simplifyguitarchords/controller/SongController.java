package com.patricksantino.simplifyguitarchords.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.patricksantino.simplifyguitarchords.dto.request.SimplifySongChordRequestDto;
import com.patricksantino.simplifyguitarchords.dto.response.CustomResponseDto;
import com.patricksantino.simplifyguitarchords.model.Song;
import com.patricksantino.simplifyguitarchords.service.SongService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/simplify")
    public ResponseEntity<CustomResponseDto<Song>> simplify(@RequestBody @Valid SimplifySongChordRequestDto simplifySongChordRequestDto) throws JsonProcessingException {
        Song song = songService.simplify(simplifySongChordRequestDto);

        CustomResponseDto<Song> res = new CustomResponseDto<>(HttpStatus.OK.value(), "Song is simplified successfully", song);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping
    public ResponseEntity<CustomResponseDto<List<Song>>> getAll() {
        List<Song> songs = songService.getAll();

        CustomResponseDto<List<Song>> res = new CustomResponseDto<>(HttpStatus.OK.value(), "List of songs", songs);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
