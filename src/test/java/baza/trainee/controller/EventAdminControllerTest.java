package baza.trainee.controller;


import baza.trainee.domain.mapper.EventMapper;
import baza.trainee.dto.EventPublication;
import baza.trainee.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EventAdminControllerTest {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @Test
    void testCreateEventStatusIsCreated() throws Exception {
        // given:
        MockHttpSession session = new MockHttpSession(null, "httpSessionId");
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.content("content size is between 30 and 3000 characters");
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));

        String eventDtoJson = objectMapper.writeValueAsString(eventDto);
        var event = eventMapper.toEvent(eventDto);
        var response = eventMapper.toResponse(event);


        // when:
        when(eventService.save(any(EventPublication.class), anyString())).thenReturn(response);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testCreateEventStatusBadRequest(String validatedField) throws Exception {
        // given:
        MockHttpSession session = new MockHttpSession(null, "httpSessionId");

        var eventDto = new EventPublication();
        eventDto.title(validatedField);
        eventDto.description(validatedField);
        eventDto.type(validatedField);
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.content("content");
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));

        String eventDtoJson = objectMapper.writeValueAsString(eventDto);
        var event = eventMapper.toEvent(eventDto);
        var response = eventMapper.toResponse(event);

        // when:
        when(eventService.save(eventDto, session.getId())).thenReturn(response);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateEvent() throws Exception {
        // given:
        MockHttpSession session = new MockHttpSession(null, "httpSessionId");

        String id = "12";
        var eventRequest = new EventPublication();
        eventRequest.title("Title");
        eventRequest.description("Short Description");
        eventRequest.type("PAINTING");
        eventRequest.tags(Set.of("tag1", "tag2"));
        eventRequest.content("updated content size is between 30 and 3000 characters");
        eventRequest.bannerId("http://example.com/banner.jpg");
        eventRequest.begin(LocalDate.now());
        eventRequest.end(LocalDate.now().plusDays(1));
        var event = eventMapper.toEvent(eventRequest);
        var response = eventMapper.toResponse(event);


        String eventDtoJson = objectMapper.writeValueAsString(eventRequest);

        // when:
        when(eventService.update(id, eventRequest, session.getId())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/admin/events/{id}", id)
                        .content(eventDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testUpdateEventStatusBadRequest(String validatedField) throws Exception {
        // given:
        MockHttpSession session = new MockHttpSession(null, "httpSessionId");

        String id = "12";

        var eventDto = new EventPublication();
        eventDto.title(validatedField);
        eventDto.description(validatedField);
        eventDto.type(validatedField);
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.content("content");
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));

        String eventDtoJson = objectMapper.writeValueAsString(eventDto);
        var event = eventMapper.toEvent(eventDto);
        var response = eventMapper.toResponse(event);


        // when:
        when(eventService.update(id, eventDto, session.getId())).thenReturn(response);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteEvent() throws Exception {
        String eventId = "12";

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/admin/events/{id}", eventId))
                .andExpect(status().isNoContent());

        verify(eventService, times(1)).deleteEventById(eq(eventId));
    }

}
