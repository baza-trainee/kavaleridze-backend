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

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource
    void testEventWithInvalidTitle(String title) {
        // given:
        Event event = validEvent;
        event.setTitle(title);

        // when:
        var constraintViolations = validator.validate(event);

        // then:
        assertFalse(constraintViolations.isEmpty());
    }

    private static Stream<Arguments> testEventWithInvalidTitle() {
        return Stream.of(
                Arguments.of(UNDERSIZED_TITLE),
                Arguments.of(OVERSIZED_TITLE)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource
    void testEventWithInvalidDescription(String description) {
        // given:
        Event event = validEvent;
        event.setSummary(description);

        // when:
        var constraintViolations = validator.validate(event);

        // then:
        assertFalse(constraintViolations.isEmpty());
    }

    private static Stream<Arguments> testEventWithInvalidDescription() {
        return Stream.of(
                Arguments.of(UNDERSIZED_DESCRIPTION),
                Arguments.of(OVERSIZED_DESCRIPTION)
        );
    }

    @ParameterizedTest
    @NullSource
    @MethodSource
    void testEventWithInvalidDates(LocalDate date) {
        // given:
        Event event = validEvent;
        event.setBegin(date);
        event.setEnd(date);

        // when:
        var constraintViolations = validator.validate(event);

        // then:
        assertFalse(constraintViolations.isEmpty());
    }

    private static Stream<Arguments> testEventWithInvalidDates() {
        return Stream.of(
                Arguments.of(LocalDate.now().minusDays(1))
        );
    }
}
