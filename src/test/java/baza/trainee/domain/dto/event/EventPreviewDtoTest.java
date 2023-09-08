package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.enums.EventTheme;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventPreviewDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidEventDto() {
        EventPreviewDto eventPreviewDto = new EventPreviewDto(
                "1",
                ContentType.EVENT,
                EventTheme.PAINTING,
                List.of("tag1", "tag2"),
                "Title",
                "Short Description",
                "http://example.com/banner.jpg",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        assertTrue(validator.validate(eventPreviewDto).isEmpty());
    }

    @Test
    void testInvalidEventDto() {
        EventPreviewDto eventPreviewDto = new EventPreviewDto(
                null,
                null,
                null,
                null,
                "",
                "",
                "invalid-url",
                null,
                null,
                null
        );

        assertFalse(validator.validate(eventPreviewDto).isEmpty());
    }
}
