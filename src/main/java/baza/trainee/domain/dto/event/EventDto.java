package baza.trainee.domain.dto.event;


public record EventDto(
        String id,
        String title,
        String content,
        String picture
) { }
