package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.domain.enums.ContentType;
import baza.trainee.domain.enums.EventTheme;
import baza.trainee.service.EventService;
import baza.trainee.utils.LoggingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private LoggingService logService;

    @Test
    public void testGetEvents() throws Exception {
        List<EventPreviewDto> events = new ArrayList<>();

        when(eventService.getEvents()).thenReturn(events);

        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetEvent() throws Exception {
        final LocalDate published = LocalDate.now();
        final LocalDate begin = LocalDate.of(2023, 9, 3);
        final LocalDate end = LocalDate.of(2023, 9, 12);
        final String eventId = "32";

        EventDto event = new EventDto(
                eventId,
                ContentType.EVENT,
                EventTheme.CINEMA,
                Collections.emptyList(),
                "cool title",
                "shortDesc",
                Collections.emptyList(),
                "/images/image1.jpeg",
                published,
                begin,
                end);

        when(eventService.getEventById(eventId)).thenReturn(event);

        mockMvc.perform(get("/events/{id}", eventId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId));
    }

    @Test
    public void testGetEventsWithNoEvents() throws Exception {
        when(eventService.getEvents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetEventWithInvalidId() throws Exception {
        final String invalidEventId = "999";

        when(eventService.getEventById(invalidEventId)).thenReturn(null);

        mockMvc.perform(get("/events/{id}", invalidEventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
