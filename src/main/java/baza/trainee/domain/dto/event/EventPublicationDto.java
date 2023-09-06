package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventPublicationDto(
        @NotNull
        ContentType contentType,

        @NotBlank
        String title,

        @NotBlank
        String content,

        @NotBlank
        String shortDescription,

        @NotNull
        LocalDate begin,

        @NotNull
        LocalDate end
) { }
