package kr.mikuwallets.djyurika400.song;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class PagedObjectList {
    private List<?> list;
    private Integer fetchCount;
    private Integer totalPages;
    private Integer currentPage;
    private Long totalElements;

    public List<?> getList() {
        return this.list;
    }

    public Integer getFetchCount() {
        return fetchCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Long getTotalElements() {
        return totalElements;
    }

}
