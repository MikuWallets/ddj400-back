package kr.mikuwallets.djyurika400.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SongRepository extends JpaRepository<Song, String> {
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE Song s SET s.reviewed = 1 WHERE s.id = :id")
//    Song updateSongReviewedById(@Param("id") String id);
}
