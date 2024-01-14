package com.patricksantino.simplifyguitarchords.repository;

import com.patricksantino.simplifyguitarchords.model.Chord;
import com.patricksantino.simplifyguitarchords.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChordRepository extends JpaRepository<Chord, Long> {
}
