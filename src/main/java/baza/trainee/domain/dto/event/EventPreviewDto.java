package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventPreviewDto(
        String id,
        @NotNull
        ContentType contentType,
        @NotBlank
        String title,
        @NotBlank
        String shortContent,
        String picturePreview
) { }
