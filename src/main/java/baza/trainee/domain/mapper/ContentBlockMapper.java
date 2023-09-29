package baza.trainee.domain.mapper;

import baza.trainee.dto.ContentBlock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentBlockMapper {

    ContentBlock toSwaggerCB(baza.trainee.domain.model.ContentBlock contentBlock);
}
