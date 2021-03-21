package kr.mikuwallets.djyurika400.song;

import kr.mikuwallets.djyurika400.exception.DBQueryException;
import kr.mikuwallets.djyurika400.exception.EntityNotFoundException;
import kr.mikuwallets.djyurika400.exception.InvalidArgumentException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SongService {
    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Page<Song> getPagedSongs(int pageIdx, int fetchCount, String server, List<String> sortParams) {
        if (server != null) {
            return songRepository.findAllByGuild(server, PageRequest.of(pageIdx, fetchCount, parseSortParam(sortParams)));
        }
        else {
            return songRepository.findAll(PageRequest.of(pageIdx, fetchCount, parseSortParam(sortParams)));
        }
    }

    public Page<Song> getPagedSongs(int pageIdx, int fetchCount, String server) {
        if (server != null) {
            return songRepository.findAllByGuild(server, PageRequest.of(pageIdx, fetchCount));
        }
        else {
            return songRepository.findAll(PageRequest.of(pageIdx, fetchCount));
        }
    }

    public List<Song> getSongsById(String id, String server) {
        try {
            List<Song> songs;
            if (server != null) {
                songs = songRepository.findSongsByIdAndGuild(id, server);
            }
            else {
                songs = songRepository.findSongsById(id);
            }

            if (songs == null || songs.size() == 0) {
                throw new EntityNotFoundException("Could not found using requested id");
            }
            else {
                return songs;
            }
        }
        catch (HibernateException e) {
            throw new DBQueryException(e.getMessage());
        }
    }

    public void deleteSong(Long regNo) {
        Song song = songRepository.findById(regNo).orElseThrow(() -> new EntityNotFoundException("Requested song is not registered"));
        songRepository.delete(song);
    }

    public List<Song> updateSongReviewCheck(List<String> ids) {
        List<Song> updatedSongs = new ArrayList<>();
        List<String> updatedSongsId = new ArrayList<>();
        for (String id : ids) {
            List<Song> songs = songRepository.findSongsById(id);
            if (songs.size() == 0) {
                throw new EntityNotFoundException("Requested song is not registered");
            }

            Song song = songs.get(0);
            if (song.isReviewed()) {
                for (Song s : songs) {
                    s.setReviewed(true);
                }
                songRepository.save(song);
                updatedSongs.add(song);
                updatedSongsId.add(song.getId());
            }

        }
        log.info(String.format("%d queries, %d songs committed: %s", ids.size(), updatedSongs.size(), updatedSongsId));

        return updatedSongs;
    }

    private Sort parseSortParam(List<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) return Sort.unsorted();
        List<Sort.Order> orderList = new ArrayList<>();

        for (String sortParam : sortParams) {
            if (sortParam == null || sortParam.isEmpty())
                continue;
            String[] splitSort = sortParam.split(",");
            if (splitSort.length < 1 || splitSort.length > 2) {
                throw new InvalidArgumentException("The format of \"sort\" is wrong");
            }

            String sortColumn = splitSort[0];
            String sortOrder = (splitSort.length > 1) ? splitSort[1] : null;

            if (!isSupportedColumnForSort(sortColumn)) {
                throw new InvalidArgumentException("Not supported column : " + sortColumn);
            }

            if (sortOrder != null && !sortOrder.equals("asc") && !sortOrder.equals("desc")) {
                throw new InvalidArgumentException("Wrong order : " + sortOrder);
            }

            Sort.Direction direction = (sortOrder != null && sortOrder.equals("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort.Order order = new Sort.Order(direction, sortColumn);
            orderList.add(order);
        }

        if (orderList.isEmpty()) throw new InvalidArgumentException("Invalid sort params");
        return Sort.by(orderList);
    }

    private boolean isSupportedColumnForSort(String column) {
        if (column == null)
            return false;
        switch (column) {
            case "reviewed":
            case "title":
            case "createdAt":
            case "pickCount":
            case "lastPlayedAt":
                return true;
        }

        return false;
    }
}
