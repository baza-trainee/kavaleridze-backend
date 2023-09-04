package baza.trainee.controller;


import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEvents() {
        List<EventPreviewDto> mockEvents = Arrays.asList(
                new EventPreviewDto("1", "Event 1", "Lorem ipsum dolor", "/images/image1"),
                new EventPreviewDto("2", "Event 2", "no content at all", "/images/image2")
        );
        when(eventService.getEvents()).thenReturn(mockEvents);

        ResponseEntity<List<EventPreviewDto>> responseEntity = eventController.getEvents();

        verify(eventService, times(1)).getEvents();

        assert (responseEntity.getStatusCode()).equals(HttpStatus.OK);
        assert (responseEntity.getBody()).equals(mockEvents);
    }

    @Test
    public void testGetEventById() {
        String eventId = "1";
        EventDto mockEvent = new EventDto(eventId, "Event 1", "Lorem Ipsum is simply", "/images/image3");
        when(eventService.getEventById(eventId)).thenReturn(mockEvent);

        ResponseEntity<EventDto> responseEntity = eventController.getEvent(eventId);

        verify(eventService, times(1)).getEventById(eventId);
        assert (responseEntity.getStatusCode()).equals(HttpStatus.OK);
        assert (responseEntity.getBody()).equals(mockEvent);
    }

    @Test
    public void testGetEventsWhenServiceReturnsNull() {
        when(eventService.getEvents()).thenReturn(null);

        ResponseEntity<List<EventPreviewDto>> responseEntity = eventController.getEvents();

        verify(eventService, times(1)).getEvents();
        assert (responseEntity.getStatusCode()).equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testGetEventByIdWithInvalidId() {
        String invalidId = "invalid_id";
        when(eventService.getEventById(invalidId)).thenReturn(null);

        ResponseEntity<EventDto> responseEntity = eventController.getEvent(invalidId);

        verify(eventService, times(1)).getEventById(invalidId);
        assert (responseEntity.getStatusCode()).equals(HttpStatus.NOT_FOUND);
    }
}
