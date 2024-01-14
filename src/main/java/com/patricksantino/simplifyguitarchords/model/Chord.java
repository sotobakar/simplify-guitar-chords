package com.patricksantino.simplifyguitarchords.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private int chordTransitions;

    @Column
    private int fingerPlacement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    @JsonIgnore
    private Song song;

    @Column
    private int difficulty;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}

