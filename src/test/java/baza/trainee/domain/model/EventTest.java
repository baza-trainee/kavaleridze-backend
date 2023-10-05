package baza.trainee.domain.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import static baza.trainee.domain.TestConstraints.OVERSIZED_DESCRIPTION;
import static baza.trainee.domain.TestConstraints.OVERSIZED_TITLE;
import static baza.trainee.domain.TestConstraints.UNDERSIZED_DESCRIPTION;
import static baza.trainee.domain.TestConstraints.UNDERSIZED_TITLE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {

    private Validator validator;
    private Event validEvent;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        validEvent = new Event();
        validEvent.setId("123");
        validEvent.setType("valid-type");
        validEvent.setTitle("Valid Title");
        validEvent.setSummary("Valid Description");
        validEvent.setDescription("Test-content");
        validEvent.setBanner(UUID.randomUUID().toString());
        validEvent.setBegin(LocalDate.now());
        validEvent.setEnd(LocalDate.now().plusDays(1));
    }

    @Test
    void testEventWithValidData() {
        // given:
        Event event = validEvent;

        // then:
        assertTrue(validator.validate(event).isEmpty());
    }
}
