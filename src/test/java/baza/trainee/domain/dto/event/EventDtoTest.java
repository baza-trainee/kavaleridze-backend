package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.enums.EventTheme;
import baza.trainee.domain.model.ContentBlock;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidEventDto() {
        EventDto eventDto = new EventDto(
                "1",
                ContentType.EVENT,
                EventTheme.PAINTING,
                List.of("tag1", "tag2"),
                "Title",
                "Short Description",
                List.of(new ContentBlock(), new ContentBlock()),
                "http://example.com/banner.jpg",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        assertTrue(validator.validate(eventDto).isEmpty());
    }

    @Test
    void testInvalidEventDto() {
        EventDto eventDto = new EventDto(
                null,
                null,
                null,
                null,
                "",
                "",
                null,
                "invalid-url",
                null,
                null,
                null
        );

        assertFalse(validator.validate(eventDto).isEmpty());
    }
}
