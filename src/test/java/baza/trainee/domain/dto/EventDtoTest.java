package baza.trainee.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import baza.trainee.domain.dto.event.EventDto;
import org.junit.jupiter.api.Test;

public class EventDtoTest {

    @Test
    void testEventDtoConstructorAndGetters() {
        EventDto eventDto = new EventDto(
                "1",
                "Sample Event",
                "Sample Content",
                "https://example.com/image.jpg"
        );

        assertEquals("1", eventDto.id());
        assertEquals("Sample Event", eventDto.title());
        assertEquals("Sample Content", eventDto.content());
        assertEquals("https://example.com/image.jpg", eventDto.picture());
    }
}
