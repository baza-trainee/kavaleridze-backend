package baza.trainee.domain.model;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEventValidation() {
        final LocalDate begin = LocalDate.of(2023, 9, 1);
        final LocalDate end = LocalDate.of(2023, 9, 3);
        Event event = new Event(
                "1",
                "Sample Event",
                "Sample Content",
                "https://example.com/image.jpg",
                begin,
                end
        );

        assertTrue(validator.validate(event).isEmpty());
    }

    @Test
    void testEventValidationWithInvalidData() {
        final LocalDate begin = LocalDate.of(2023, 9, 1);
        final LocalDate end = LocalDate.of(2023, 8, 30);
        Event event = new Event(
                null,
                "Sample Event",
                "Sample Content",
                "not-a-valid-url",
                begin,
                end
        );

        assertFalse(validator.validate(event).isEmpty());
    }
}
