package baza.trainee.controller;


import baza.trainee.domain.dto.event.EventPublication;
import baza.trainee.domain.model.ContentBlock;
import baza.trainee.service.EventService;
import baza.trainee.utils.LoggingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@WebMvcTest(EventAdminController.class)
public class EventAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private LoggingService logService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateEvent() throws Exception {
        EventPublication eventDto = new EventPublication(
                "Title",
                "Short Description",
                "PAINTING",
                Set.of("tag1", "tag2"),
                Set.of(new ContentBlock()),
                "http://example.com/banner.jpg",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateEvent() throws Exception {
        String eventId = "12";
        EventPublication eventDto = new EventPublication(
                "Title",
                "Short Description",
                "PAINTING",
                Set.of("tag1", "tag2"),
                Set.of(new ContentBlock()),
                "http://example.com/banner.jpg",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/events/{id}", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteEvent() throws Exception {
        String eventId = "12";

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/events/{id}", eventId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(eventService, times(1)).deleteEventById(eq(eventId));
    }

}
