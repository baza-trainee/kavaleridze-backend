package baza.trainee.domain.mapper;

import baza.trainee.dto.EventResponse;
import baza.trainee.dto.PageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PageEventMapper {

    private final PageableMapper pageableMapper;
    private final SortMapper sortMapper;

    public PageEvent toPageEvent(Page<EventResponse> page) {
        var pageEvent = new PageEvent();

        var sortObject = sortMapper.toSortObject(page.getSort());
        var pageableObject = pageableMapper.toPageableObject(page.getPageable());

        pageEvent.setTotalPages(page.getTotalPages());
        pageEvent.setSort(sortObject);
        pageEvent.setPageable(pageableObject);
        pageEvent.setNumber(page.getNumber());
        pageEvent.setLast(page.isLast());
        pageEvent.setEmpty(page.isEmpty());
        pageEvent.setFirst(page.isFirst());
        pageEvent.setContent(page.getContent());
        pageEvent.setTotalPages(page.getTotalPages());
        pageEvent.setNumberOfElements(page.getNumberOfElements());
        pageEvent.setTotalElements(page.getTotalElements());

        return pageEvent;
    }
}
