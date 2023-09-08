package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.enums.EventTheme;
import baza.trainee.domain.model.ContentBlock;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.List;

public record EventPublicationDto(
        @NotNull
        ContentType contentType,

        @NotNull
        EventTheme eventTheme,

        List<String> tags,

        @NotBlank
        String title,

        @NotBlank
        String shortDescription,

        @URL
        String bannerImage,

        @NotNull
        List<ContentBlock> contentBlocks,

        @NotNull
        LocalDate begin,

        @NotNull
        LocalDate end
) { }
