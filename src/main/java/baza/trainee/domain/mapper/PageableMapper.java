package baza.trainee.domain.mapper;

import baza.trainee.dto.PageableObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageableMapper {

    private final SortMapper sortMapper;

    public PageableObject toPageableObject(Pageable pageable) {
        var pageableObject = new PageableObject();
        var sortObject = sortMapper.toSortObject(pageable.getSort());

        pageableObject.setSort(sortObject);
        pageableObject.setOffset(pageable.getOffset());
        pageableObject.setPaged(pageable.isPaged());
        pageableObject.setUnpaged(pageable.isUnpaged());
        pageableObject.setPageSize(pageable.getPageSize());
        pageableObject.setPageNumber(pageable.getPageNumber());

        return pageableObject;
    }
}
