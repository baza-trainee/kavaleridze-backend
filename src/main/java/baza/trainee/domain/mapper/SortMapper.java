package baza.trainee.domain.mapper;

import baza.trainee.dto.SortObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortMapper {

    public SortObject toSortObject(Sort sort) {
        var sortObject = new SortObject();

        sortObject.setSorted(sort.isSorted());
        sortObject.setEmpty(sort.isEmpty());
        sortObject.setUnsorted(sort.isUnsorted());

        return sortObject;
    }
}
