package kr.mikuwallets.djyurika400.song;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE Song s SET s.reviewed = 1 WHERE s.id = :id")
//    Song updateSongReviewedById(@Param("id") String id);
    List<Song> findSongsById(String id);
    List<Song> findSongsByIdAndGuild(String id, String server);
    Page<Song> findAllByGuild(String server, Pageable pageable);
}
