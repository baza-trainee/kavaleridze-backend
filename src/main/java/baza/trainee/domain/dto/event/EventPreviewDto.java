package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventPreviewDto(
        @NotNull
        String id,

        @NotNull
        ContentType contentType,

        @NotBlank
        @NotNull
        String title,

        @NotNull
        @NotBlank
        String shortDescription,

        String picturePreview,

        @NotNull
        LocalDate published,

        @NotNull
        LocalDate begin,

        @NotNull
        LocalDate end
) { }
