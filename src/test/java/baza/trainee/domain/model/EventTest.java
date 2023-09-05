package baza.trainee.domain.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidEvent() {
        Event event = new Event();
        event.setId("1");
        event.setTitle("Sample Event");
        event.setContent("Event content");
        event.setBegin(LocalDate.now());
        event.setEnd(LocalDate.now().plusDays(1));

        assertEquals(0, validator.validate(event).size());
    }

    @Test
    void testInvalidEvent() {
        final int three = 3;
        Event event = new Event();
        event.setId(null);
        event.setTitle("");
        event.setContent(null);
        event.setBegin(LocalDate.now());
        event.setEnd(LocalDate.now().minusDays(1));

        assertEquals(three, validator.validate(event).size());
    }
}
