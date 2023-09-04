package baza.trainee.domain.dto.event;

import java.time.LocalDate;

public record EventPublicationDto(
        String title,
        String content,
        String picture,
        LocalDate begin,
        LocalDate end
) { }
