package com.patricksantino.simplifyguitarchords.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "chords")
public class Chord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String chord;

    @Column
    private int capo;

    @Column
    private int openChords;

    @Column
    private int barreChords;

    @Column
    private int chordChanges;

    @Column
    private int fingerPlacement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @Column
    private int difficulty;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}

