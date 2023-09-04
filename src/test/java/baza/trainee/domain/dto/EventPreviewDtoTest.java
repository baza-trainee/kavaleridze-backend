package baza.trainee.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import baza.trainee.domain.dto.event.EventPreviewDto;
import org.junit.jupiter.api.Test;

public class EventPreviewDtoTest {

    @Test
    void testEventPreviewDtoConstructorAndGetters() {
        EventPreviewDto eventPreviewDto = new EventPreviewDto(
                "1",
                "Sample Event",
                "Short Content",
                "https://example.com/small-image.jpg"
        );

        assertEquals("1", eventPreviewDto.id());
        assertEquals("Sample Event", eventPreviewDto.title());
        assertEquals("Short Content", eventPreviewDto.shortContent());
        assertEquals("https://example.com/small-image.jpg", eventPreviewDto.compressedPicture());
    }
}
