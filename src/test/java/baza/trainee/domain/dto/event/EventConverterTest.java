package baza.trainee.domain.dto.event;

import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventConverterTest {

    private EventConverter eventConverter;

    @BeforeEach
    public void setUp() {
        eventConverter = new EventConverter();
    }

    @Test
    public void testEventToEventDto() {
        Event event = createSampleEvent();
        EventDto eventDto = eventConverter.eventToEventDto(event);

        assertEquals(event.getId(), eventDto.id());
        assertEquals(event.getContentType(), eventDto.contentType());
        assertEquals(event.getTitle(), eventDto.title());
        assertEquals(event.getContent(), eventDto.content());
        assertEquals(event.getPicture(), eventDto.picture());
    }

    @Test
    public void testEventToEventPreviewDto() {
        Event event = createSampleEvent();
        EventPreviewDto eventPreviewDto = eventConverter.eventToEventPreviewDto(event);

        assertEquals(event.getId(), eventPreviewDto.id());
        assertEquals(event.getContentType(), eventPreviewDto.contentType());
        assertEquals(event.getTitle(), eventPreviewDto.title());
        assertEquals(event.getContent(), eventPreviewDto.shortContent());
        assertEquals(event.getPicturePreview(), eventPreviewDto.picturePreview());
    }

    @Test
    public void testEventPublicationDtoToEvent() {
        EventPublicationDto publicationDto = createSampleEventPublicationDto();
        Event event = eventConverter.eventPublicationDtoToEvent(publicationDto);

        assertEquals(publicationDto.title(), event.getTitle());
        assertEquals(publicationDto.content(), event.getContent());
        assertEquals(publicationDto.picture(), event.getPicture());
        assertEquals(publicationDto.picturePreview(), event.getPicturePreview());
        assertEquals(publicationDto.begin(), event.getBegin());
        assertEquals(publicationDto.end(), event.getEnd());
    }

    private Event createSampleEvent() {
        Event event = new Event();
        event.setId("1");
        event.setContentType(ContentType.EVENT);
        event.setTitle("Sample Event");
        event.setContent("Event content");
        event.setPicture("http://example.com/event.jpg");
        event.setPicturePreview("http://example.com/event_preview.jpg");
        return event;
    }

    private EventPublicationDto createSampleEventPublicationDto() {
        final int week = 7;
        return new EventPublicationDto(
                ContentType.EVENT,
                "Sample Event",
                "Event content",
                "http://example.com/event.jpg",
                "http://example.com/event_preview.jpg",
                LocalDate.now(),
                LocalDate.now().plusDays(week)
        );
    }
}
