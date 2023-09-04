package baza.trainee.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import baza.trainee.domain.dto.event.EventPublicationDto;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class EventPublicationDtoTest {

    @Test
    void testEventPublicationDtoConstructorAndGetters() {
        final LocalDate begin = LocalDate.of(2023, 9, 1);
        final LocalDate end = LocalDate.of(2023, 9, 3);
        EventPublicationDto eventPublicationDto = new EventPublicationDto(
                "Sample Event",
                "Sample Content",
                "https://example.com/image.jpg",
                begin,
                end
        );

        assertEquals("Sample Event", eventPublicationDto.title());
        assertEquals("Sample Content", eventPublicationDto.content());
        assertEquals("https://example.com/image.jpg", eventPublicationDto.picture());
        assertEquals(begin, eventPublicationDto.begin());
        assertEquals(end, eventPublicationDto.end());
    }
}
