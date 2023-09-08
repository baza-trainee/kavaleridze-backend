package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.enums.EventTheme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record EventPreviewDto(

        @NotNull
        String id,

        @NotNull
        ContentType contentType,

        @NotNull
        EventTheme eventTheme,

        List<String> tags,

        @NotBlank
        @NotNull
        String title,

        @NotNull
        @NotBlank
        String shortDescription,

        String bannerImagePreview,

        @NotNull
        LocalDate published,

        @NotNull
        LocalDate begin,

        @NotNull
        LocalDate end
) { }
