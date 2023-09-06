package baza.trainee.domain.dto.event;


import baza.trainee.domain.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventDto(
        @NotNull
        String id,

        @NotNull
        ContentType contentType,

        @NotBlank
        String title,

        @NotBlank
        String content,

        String picture,

        @NotNull
        LocalDate published,

        @NotNull
        LocalDate begin,

        @NotNull
        LocalDate end
) { }
