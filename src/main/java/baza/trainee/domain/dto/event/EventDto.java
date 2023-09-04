package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;

public record EventDto(
        String id,
        String title,
        String content,
        String picture
) { }
