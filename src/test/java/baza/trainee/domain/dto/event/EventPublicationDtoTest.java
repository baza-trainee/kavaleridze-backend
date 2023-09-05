package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EventPublicationDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidEventPublicationDto() {
        EventPublicationDto eventPublicationDto = new EventPublicationDto(
                ContentType.EVENT,
                "title",
                "content",
                "image.jpg",
                "image_preview.jpg",
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 10)
        );

        Set<ConstraintViolation<EventPublicationDto>> violations = validator.validate(eventPublicationDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEventPublicationDto() {
        EventPublicationDto eventPublicationDto = new EventPublicationDto(
                null,
                "",
                "",
                "image.jpg",
                "image_preview.jpg",
                null,
                LocalDate.of(2023, 9, 10)
        );

        Set<ConstraintViolation<EventPublicationDto>> violations = validator.validate(eventPublicationDto);
        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());
    }
}
