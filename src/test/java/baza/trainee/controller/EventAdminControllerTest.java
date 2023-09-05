package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventConverter;
import baza.trainee.domain.dto.event.EventPublicationDto;
import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.model.Event;
import baza.trainee.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventAdminControllerTest {

    private EventAdminController eventAdminController;

    @Mock
    private EventService eventService;

    @Mock
    private EventConverter eventConverter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        eventAdminController = new EventAdminController(eventService, eventConverter);
    }

    @Test
    public void testCreateEvent() {
        final LocalDate begin = LocalDate.of(2023, 9, 15);
        final LocalDate end = LocalDate.of(2023, 9, 18);
        EventPublicationDto eventPublicationDto = new EventPublicationDto(
                ContentType.EVENT,
                "Event Title",
                "Event Content",
                "https://example.com/picture.jpg",
                "https://example.com/picture-preview.jpg",
                begin,
                end
        );
        Event newEvent = new Event();

        when(eventConverter.eventPublicationDtoToEvent(eventPublicationDto)).thenReturn(newEvent);

        ResponseEntity<?> response = eventAdminController.createEvent(eventPublicationDto);

        verify(eventService).createEvent(newEvent);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateEvent() {
        String eventId = "1";
        final LocalDate begin = LocalDate.of(2023, 9, 15);
        final LocalDate end = LocalDate.of(2023, 9, 18);
        EventPublicationDto eventPublicationDto = new EventPublicationDto(
                ContentType.EVENT,
                "Updated Event Title",
                "Updated Event Content",
                "https://example.com/updated-picture.jpg",
                "https://example.com/updated-picture-preview.jpg",
                begin,
                end
        );
        Event updatedEvent = new Event();

        when(eventConverter.eventPublicationDtoToEvent(eventPublicationDto)).thenReturn(updatedEvent);

        ResponseEntity<?> response = eventAdminController.updateEvent(eventId, eventPublicationDto);

        verify(eventService).updateEventById(updatedEvent, eventId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteEvent() {
        String eventId = "1";

        ResponseEntity<?> response = eventAdminController.deleteEvent(eventId);

        verify(eventService).deleteEventById(eventId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
