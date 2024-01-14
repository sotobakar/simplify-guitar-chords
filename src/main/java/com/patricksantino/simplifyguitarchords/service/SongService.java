package com.patricksantino.simplifyguitarchords.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patricksantino.simplifyguitarchords.dto.request.ChatPromptMessageDto;
import com.patricksantino.simplifyguitarchords.dto.request.SimplifyChatPromptRequestDto;
import com.patricksantino.simplifyguitarchords.dto.request.SimplifySongChordRequestDto;
import com.patricksantino.simplifyguitarchords.dto.response.CompletionResponseDto;
import com.patricksantino.simplifyguitarchords.dto.response.SimplifiedChordResponseDto;
import com.patricksantino.simplifyguitarchords.exception.ModelNotFoundException;
import com.patricksantino.simplifyguitarchords.model.Chord;
import com.patricksantino.simplifyguitarchords.model.Song;
import com.patricksantino.simplifyguitarchords.repository.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SongService {

    @Value("${openapi.api.completion.path}")
    private String openAICompletionPath;

    @Value("${openapi.api.url}")
    private String openAIApiUrl;

    @Value("${openapi.api.key}")
    private String openAIApiKey;

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song simplify(SimplifySongChordRequestDto simplifySongChordRequestDto) throws JsonProcessingException {
        SimplifyChatPromptRequestDto requestBody = new SimplifyChatPromptRequestDto();

        requestBody.setModel("gpt-3.5-turbo-1106");

        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");
        requestBody.setResponseFormat(responseFormat);

        // Build prompt
        List<ChatPromptMessageDto> messages = new ArrayList<>();
        ChatPromptMessageDto systemPrompt = new ChatPromptMessageDto();
        systemPrompt.setRole("system");
        systemPrompt.setContent("You are going to be a singer who happened to be an expert in playing the guitar. " +
                "You will simplify or transpose chords that will be asked by the user to the simplest chords possible, " +
                "and then rate the simplified chord difficulty by the number of open chords, " +
                "barre chords, chord transitions, and finger placement. The criteria for simple is the the highest number of " +
                "open chords, the lowest number of barre chords, the lowest number of chord transitions " +
                "and the easiest finger placement.");

        ChatPromptMessageDto userPrompt = new ChatPromptMessageDto();
        userPrompt.setRole("user");
        userPrompt.setContent("I will give you a song chord in json format, where the 'capo' property " +
                "is the fret in which the capo is used (0 for no capo), and then 'body' property is the chord " +
                "and the lyrics below it. You will return to me in JSON format, the simplified song chords with the lyrics " +
                "back in 'body' property, the 'capo' property which is which fret should we put the capo on for the simplified chords(0 for no capo), 'open_chords' which is " +
                "the number of open chords, 'barre_chords' which is the number of barre chords, 'chord_transitions' " +
                "which is the number of chord transitions, 'finger_placement' which is the difficulty from 1-10 " +
                "categorized as the easiest to hardest, and then the 'difficulty' which is the chord playing " +
                "difficulty from 1-10 categorized as the easiest to hardest");

        ChatPromptMessageDto assistantPrompt = new ChatPromptMessageDto();
        assistantPrompt.setRole("assistant");
        assistantPrompt.setContent("Understood.");

        ChatPromptMessageDto songPrompt = new ChatPromptMessageDto();
        songPrompt.setRole("user");
        Map<String, Object> songPromptDto = new HashMap<>();
        songPromptDto.put("capo", simplifySongChordRequestDto.getCapo());
        songPromptDto.put("body", simplifySongChordRequestDto.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        songPrompt.setContent(objectMapper.writeValueAsString(songPromptDto));

        // Add prompts to messages list
        messages.add(systemPrompt);
        messages.add(userPrompt);
        messages.add(assistantPrompt);
        messages.add(songPrompt);

        // Set message
        requestBody.setMessages(messages);

        // Create rest client
        RestClient openAIHttpClient = RestClient.create();

        ResponseEntity<CompletionResponseDto> result = openAIHttpClient.post().uri(openAIApiUrl + openAICompletionPath)
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .header("Authorization", "Bearer " + openAIApiKey)
                .header("Accept", "application/json")
                .retrieve()
                .toEntity(CompletionResponseDto.class);

        // Get result
        SimplifiedChordResponseDto simplifiedChordResponseDto = objectMapper.readValue(result.getBody().getChoices()[0].getMessage().getContent(), SimplifiedChordResponseDto.class);

        // Save song to database
        Song song = new Song();
        song.setName(simplifySongChordRequestDto.getName());
        song.setGenre(simplifySongChordRequestDto.getGenre());
        song.setSinger(simplifySongChordRequestDto.getSinger());

        // Save simplified chord to database
        Chord chord = new Chord();
        chord.setFingerPlacement(simplifiedChordResponseDto.getFingerPlacement());
        chord.setDifficulty(simplifiedChordResponseDto.getDifficulty());
        chord.setChord(simplifiedChordResponseDto.getBody());
        chord.setOpenChords(simplifiedChordResponseDto.getOpenChords());
        chord.setBarreChords(simplifiedChordResponseDto.getBarreChords());
        chord.setCapo(simplifiedChordResponseDto.getCapo());
        chord.setChordTransitions(simplifiedChordResponseDto.getChordTransitions());

        // Add chord to song
        chord.setSong(song);
        song.getChords().add(chord);

        return songRepository.save(song);
    }

    // Get all songs
    public Page<Song> getAll(String searchTerm, Pageable pageable) {
        return songRepository.findAllByNameOrSinger(searchTerm, pageable);
    }

    public Song getById(Long id) {
        Song song = songRepository.findById(id).orElse(null);

        if (song == null) {
            throw new ModelNotFoundException("Song is not found.");
        }

        return song;
    }
}
