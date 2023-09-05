package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventDtoTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    public void testValidEventDto() {
        EventDto eventDto = new EventDto(
                "1",
                ContentType.ARTICLE,
                "Valid Title",
                "Valid Content",
                "Valid Picture"
        );

        Set<ConstraintViolation<EventDto>> violations = validator.validate(eventDto);
        assertTrue(violations.isEmpty(), "EventDto should be valid");
    }

    @Test
    public void testInvalidEventDto() {
        EventDto eventDto = new EventDto(
                "2",
                null,
                "",
                "Valid Content",
                "Valid Picture"
        );

        Set<ConstraintViolation<EventDto>> violations = validator.validate(eventDto);
        assertFalse(violations.isEmpty(), "EventDto should be invalid");

        ConstraintViolation<EventDto> contentTypeViolation = violations
                .stream()
                .filter(violation -> "contentType".equals(violation.getPropertyPath().toString()))
                .findFirst()
                .orElse(null);

        assertNotNull(contentTypeViolation, "contentType should have a validation error");
    }
}
