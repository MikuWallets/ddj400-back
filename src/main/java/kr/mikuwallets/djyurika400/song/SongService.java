package kr.mikuwallets.djyurika400.song;

import kr.mikuwallets.djyurika400.exception.EntityNotFoundException;
import kr.mikuwallets.djyurika400.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Page<Song> getPagedSongs(int pageIdx, int fetchCount, List<String> sortParams) {
        return songRepository.findAll(PageRequest.of(pageIdx, fetchCount, parseSortParam(sortParams)));
    }
    public Page<Song> getPagedSongs(int pageIdx, int fetchCount) {
        return songRepository.findAll(PageRequest.of(pageIdx, fetchCount));
    }

    public void deleteSong(String id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Requested song is not registered"));
        songRepository.delete(song);
    }

    public List<Song> updateSongReviewCheck(List<String> ids) {
        List<Song> updatedSongs = new ArrayList<>();
        for (String id : ids) {
            Song song = songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Requested song is not registered"));
            if (!song.isReviewed()) {
                song.setReviewed(true);
                songRepository.save(song);
                updatedSongs.add(song);
            }
        }

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
                return true;
        }

        return false;
    }
}
