package baza.trainee.domain.mapper;


import baza.trainee.domain.dto.event.EventPublication;
import baza.trainee.domain.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "bannerURI", ignore = true)
    @Mapping(target = "bannerPreviewURI", ignore = true)
    @Mapping(target = "id", ignore = true)
    Event toEvent(EventPublication publication);
}
