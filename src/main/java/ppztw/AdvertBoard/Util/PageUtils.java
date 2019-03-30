package ppztw.AdvertBoard.Util;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.beans.support.SortDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

public class PageUtils<T> {

    public Page<T> getPage(List<T> content, Pageable pageable) {
        Sort sort = pageable.getSort();
        ComparatorChain comparatorChain = new ComparatorChain();
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            SortDefinition sortDefinition = new MutableSortDefinition(property,
                    true, order.isAscending());
            PropertyComparator.sort(content, sortDefinition);
            comparatorChain.addComparator(new PropertyComparator<>(sortDefinition));
        }
        content.sort(comparatorChain);

        PagedListHolder<T> pagedListHolder = new PagedListHolder<T>(Collections.unmodifiableList(content));
        pagedListHolder.setPageSize(pageable.getPageSize());
        pagedListHolder.setPage(pageable.getPageNumber());

        return new PageImpl<>(pagedListHolder.getPageList(), pageable, content.size());
    }


}
