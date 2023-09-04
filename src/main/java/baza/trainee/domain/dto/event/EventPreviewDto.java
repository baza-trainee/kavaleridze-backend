package baza.trainee.domain.dto.event;

public record EventPreviewDto(
        String id,
        String title,
        String shortContent,
        String compressedPicture
) { }
