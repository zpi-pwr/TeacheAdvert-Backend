package ppztw.AdvertBoard.Util;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageUtils<T> {

    public Page<T> getPage(List<T> content, Pageable pageable) {
        PagedListHolder<T> pagedListHolder = new PagedListHolder<T>(content);
        pagedListHolder.setPageSize(pageable.getPageSize());
        pagedListHolder.setPage(pageable.getPageNumber());

        return new PageImpl<>(pagedListHolder.getPageList(), pageable, content.size());
    }
}
