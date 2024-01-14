package com.patricksantino.simplifyguitarchords.repository;

import com.patricksantino.simplifyguitarchords.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query(value = "SELECT s FROM Song as s JOIN FETCH s.chords WHERE s.name LIKE %?1% OR s.singer LIKE %?1%")
    Page<Song> findAllByNameOrSinger(String searchTerm, Pageable pageable);

    Optional<Song> findById(Long id);
}
