package com.patricksantino.simplifyguitarchords.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patricksantino.simplifyguitarchords.dto.request.ChatPromptMessageDto;
import com.patricksantino.simplifyguitarchords.dto.request.SimplifyChatPromptRequestDto;
import com.patricksantino.simplifyguitarchords.dto.request.SimplifySongChordRequestDto;
import com.patricksantino.simplifyguitarchords.model.Song;
import com.patricksantino.simplifyguitarchords.repository.SongRepository;
import org.springframework.beans.factory.annotation.Value;
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

        Map<String, String> responseType = new HashMap<>();
        responseType.put("type", "json_object");
        requestBody.setResponseType(responseType);

        // Build prompt
        List<ChatPromptMessageDto> messages = new ArrayList<>();
        ChatPromptMessageDto systemPrompt = new ChatPromptMessageDto();
        systemPrompt.setRole("system");
        systemPrompt.setContent("You are going to be a singer who happened to be an expert in playing the guitar. " +
                "You will simplify chords that will be asked by the user to the simplest chords possible, " +
                "and then rate the simplified chord difficulty by the number of open chords, " +
                "barre chords, chord transitions, and finger placement. The criteria for simple is the the higher number of " +
                "open chords, the lower number of barre chords, the lower number of chord transitions " +
                "and the easier finger placement.");

        ChatPromptMessageDto userPrompt = new ChatPromptMessageDto();
        userPrompt.setRole("user");
        userPrompt.setContent("I will give you a song chord in json format, where the 'capo' property " +
                "is the fret in which the capo is used (0 for no capo), and then 'body' property is the chord " +
                "and the lyrics below it. You will return to me in JSON format, the song chords with the lyrics " +
                "simplified in 'body' property, the transposed 'capo' which is an integer, 'open_chords' which is " +
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

        ResponseEntity<String> result = openAIHttpClient.post().uri(openAIApiUrl + openAICompletionPath)
                .body(requestBody)
                .header("Authorization", "Bearer " + openAIApiKey)
                .header("Accept", "application/json")
                .retrieve()
                .toEntity(String.class);

        System.out.println(result.getBody());

        // TODO: Get result

        // TODO: Save to database

        return null;
    }

    // TODO: Get all simplified songs
}
