package kr.mikuwallets.djyurika400.song;

import kr.mikuwallets.djyurika400.exception.InvalidArgumentException;
import kr.mikuwallets.djyurika400.shared.AdminAuthComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping({"api/v1/song"})
public class SongController {
    private final AdminAuthComponent authComponent;
    private final SongService songService;

    @Autowired
    public SongController(AdminAuthComponent authComponent, SongService songService) {
        this.authComponent = authComponent;
        this.songService = songService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ResponseBody
    public ResponseEntity<PagedObjectList> getAllSongList(
            HttpServletRequest request,
            @RequestParam Integer page,
            @RequestParam(required = false) Integer fetchCount,
            @RequestParam(value = "sort", required = false) List<String> sortParams
            ) {
        authComponent.validateAccessToken(request);

        if (page < 1) {
            throw new InvalidArgumentException("Page number cannot be lower than 1");
        }

        // service
        Page<Song> fetchData;
        if (sortParams == null) {
            fetchData = songService.getPagedSongs(page-1, fetchCount != null ? fetchCount : 100);
        }
        else {
            fetchData = songService.getPagedSongs(page-1, fetchCount != null ? fetchCount : 100, sortParams);
        }

        PagedObjectList pagedSongList = PagedObjectList.builder()
                .list(fetchData.getContent())
                .fetchCount(fetchData.getSize())
                .currentPage(fetchData.getNumber() + 1)
                .totalPages(fetchData.getTotalPages())
                .totalElements(fetchData.getTotalElements())
                .build();

        return new ResponseEntity<>(pagedSongList, HttpStatus.OK);
    }
}
