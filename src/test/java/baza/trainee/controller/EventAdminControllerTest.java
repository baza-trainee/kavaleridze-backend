package baza.trainee.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.domain.dto.event.EventPublicationDto;
import baza.trainee.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(EventAdminController.class)
public class EventAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    public void testGetEvents() throws Exception {
        List<EventPreviewDto> events = new ArrayList<>();

        when(eventService.getEvents()).thenReturn(events);

        mockMvc.perform(get("/admin/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetEvent() throws Exception {
        String eventId = "12";
        EventDto event = new EventDto("12", "Very cool title", "Lorem ipsum dolor 123213", "/images/image1");

        when(eventService.getEventById(eventId)).thenReturn(event);

        mockMvc.perform(get("/admin/events/{id}", eventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(eventId));
    }

    @Test
    public void testCreateEvent() throws Exception {
        final LocalDate begin  = LocalDate.of(2023, 9, 4);
        final LocalDate end = LocalDate.of(2023, 9, 5);
        EventPublicationDto eventPublicationDto = new EventPublicationDto(
                "Cool title",
                "Lorem ipsum",
                "/image1",
                begin,
                end
        );

        mockMvc.perform(post("/admin/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(eventPublicationDto)))
                .andExpect(status().isOk());

        verify(eventService, times(1)).createEvent(eventPublicationDto);
    }

    @Test
    public void testUpdateEvent() throws Exception {
        String eventId = "123";
        final LocalDate begin = LocalDate.of(2023, 9, 4);
        final LocalDate end = LocalDate.of(2023, 9, 5);
        EventPublicationDto updatedEvent = new EventPublicationDto(
                "Cool title",
                "Lorem ipsum",
                "/image1",
                begin,
                end
        );

        mockMvc.perform(post("/admin/events/{id}/updated", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedEvent)))
                .andExpect(status().isOk());

        verify(eventService, times(1)).updateEventById(updatedEvent, eventId);
    }

    @Test
    public void testDeleteEvent() throws Exception {
        String eventId = "123";

        mockMvc.perform(post("/admin/events/{id}/delete", eventId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventService, times(1)).deleteEventById(eventId);
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
