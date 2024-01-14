package com.patricksantino.simplifyguitarchords.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.patricksantino.simplifyguitarchords.dto.request.SimplifySongChordRequestDto;
import com.patricksantino.simplifyguitarchords.dto.response.CustomPageResponseDto;
import com.patricksantino.simplifyguitarchords.dto.response.CustomResponseDto;
import com.patricksantino.simplifyguitarchords.model.Song;
import com.patricksantino.simplifyguitarchords.service.SongService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<CustomPageResponseDto<List<Song>>> getAll(
            @RequestParam(value = "search_term", required = false, defaultValue = "") String searchTerm,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Song> songs = songService.getAll(searchTerm, pageable);

        CustomPageResponseDto<List<Song>> res = new CustomPageResponseDto<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("List of songs");
        res.setData(songs.getContent());
        res.setPage(page);
        res.setLimit(limit);
        res.setCount(songs.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseDto<Song>> getById(@PathVariable("id") Long id) {
        Song song = songService.getById(id);

        CustomResponseDto<Song> res = new CustomResponseDto<>(HttpStatus.OK.value(), "Song is found", song);
        return ResponseEntity.ok(res);
    }
}
