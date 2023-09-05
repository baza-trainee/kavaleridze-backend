package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventPreviewDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidEventPreviewDto() {
        EventPreviewDto eventPreviewDto = new EventPreviewDto(
                "12345",
                ContentType.EVENT,
                "title event",
                "short description",
                "preview.jpg"
        );

        Set<ConstraintViolation<EventPreviewDto>> violations = validator.validate(eventPreviewDto);
        assertTrue(violations.isEmpty(), "it must be 0 validation errors");
    }

    @Test
    public void testInvalidEventPreviewDto() {
         EventPreviewDto eventPreviewDto = new EventPreviewDto(
                "12345",
                null,
                "",
                "",
                null
        );

        Set<ConstraintViolation<EventPreviewDto>> violations = validator.validate(eventPreviewDto);

        assertEquals(3, violations.size(), "it must be 3 validation errors");

        for (ConstraintViolation<EventPreviewDto> violation : violations) {
            String propertyName = violation.getPropertyPath().toString();
            assertTrue(
                    propertyName.equals("contentType") || propertyName.equals("title") || propertyName.equals("shortContent"),
                    "validation error"
            );
        }
    }
}
