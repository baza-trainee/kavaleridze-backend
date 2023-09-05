package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventConverter;
import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.model.Event;
import baza.trainee.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private EventConverter eventConverter;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testGetEvents() throws Exception {
        List<Event> events = new ArrayList<>();
        Event event1 = new Event();
        event1.setId("1");
        Event event2 = new Event();
        event2.setId("2");
        events.add(event1);
        events.add(event2);

        when(eventService.getEvents()).thenReturn(events);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetEvent() throws Exception {
        String eventId = "1";
        Event event = new Event();
        event.setId(eventId);
        EventDto eventDto = new EventDto(eventId, ContentType.EVENT, "Test Event", "Event Content", null);

        when(eventService.getEventById(eventId)).thenReturn(event);
        when(eventConverter.eventToEventDto(event)).thenReturn(eventDto);

        mockMvc.perform(get("/events/{id}", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId))
                .andExpect(jsonPath("$.title").value("Test Event"))
                .andExpect(jsonPath("$.content").value("Event Content"));
    }
}
