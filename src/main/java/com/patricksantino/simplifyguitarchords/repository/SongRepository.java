package com.patricksantino.simplifyguitarchords.repository;

import com.patricksantino.simplifyguitarchords.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query(value = "SELECT * FROM songs as s WHERE s.name LIKE %?1% OR s.singer LIKE %?1%",
            countQuery = "SELECT COUNT(*) FROM songs as s WHERE s.name LIKE %?1% OR s.singer LIKE %?1%",
            nativeQuery = true)
    Page<Song> findAllByNameOrSinger(String searchTerm, Pageable pageable);
}
