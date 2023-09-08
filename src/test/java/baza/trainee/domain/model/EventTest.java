package baza.trainee.domain.model;

import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.enums.EventTheme;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testEventWithValidData() {
        Event event = new Event();
        event.setId("123");
        event.setContentType(ContentType.EVENT);
        event.setEventTheme(EventTheme.PAINTING);
        event.setTags(Collections.singletonList("tag"));
        event.setTitle("Valid Title");
        event.setShortDescription("Valid Description");
        event.setBannerImage("https://example.com/banner.jpg");
        event.setBannerImagePreview("https://example.com/banner-preview.jpg");
        event.setContentBlocks(Collections.singletonList(new ContentBlock()));
        event.setBegin(LocalDate.now());
        event.setEnd(LocalDate.now().plusDays(1));
        event.setCreated(LocalDate.now());
        event.setUpdated(LocalDate.now());

        assertTrue(validator.validate(event).isEmpty());
    }

    @Test
    public void testEventWithInvalidData() {
        Event event = new Event();
        assertFalse(validator.validate(event).isEmpty());
    }
}
