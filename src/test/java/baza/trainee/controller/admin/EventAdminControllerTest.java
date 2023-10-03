package baza.trainee.controller.admin;


import baza.trainee.dto.EventPublication;
import baza.trainee.domain.mapper.EventMapper;
import baza.trainee.security.RootUserInitializer;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = MOCK)
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

    @MockBean
    private RootUserInitializer rootUserInitializer;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testCreateEventStatusIsCreated() throws Exception {
        // given:
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        String eventDtoJson = objectMapper.writeValueAsString(eventDto);
        var event = eventMapper.toEvent(eventDto);
        var eventResponse = eventMapper.toResponse(event);

        // when:
        when(eventService.save(any(EventPublication.class), anyString())).thenReturn(eventResponse);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                        .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @WithMockUser(roles = {"ADMIN"})
    void testCreateEventStatusBadRequest(String validatedField) throws Exception {
        // given:
        MockHttpSession session = new MockHttpSession(null, "httpSessionId");
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        String eventDtoJson = objectMapper.writeValueAsString(eventDto);
        var event = eventMapper.toEvent(eventDto);
        var eventResponse = eventMapper.toResponse(event);

        // when:
        when(eventService.save(eventDto, session.getId())).thenReturn(eventResponse);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testUpdateEvent() throws Exception {
        // given:
        String id = "12";
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        var event = eventMapper.toEvent(eventDto);
        var eventResponse = eventMapper.toResponse(event);

        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        // when:
        when(eventService.update(id, eventDto, anyString())).thenReturn(eventResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/admin/events/{id}", id)
                        .content(eventDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @WithMockUser(roles = {"ADMIN"})
    void testUpdateEventStatusBadRequest(String validatedField) throws Exception {
        // given:
        String id = "12";
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        var event = eventMapper.toEvent(eventDto);
        var eventResponse = eventMapper.toResponse(event);
        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        // when:
        when(eventService.update(id, eventDto, anyString())).thenReturn(eventResponse);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testDeleteEvent() throws Exception {
        String eventId = "12";

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/admin/events/{id}", eventId))
                .andExpect(status().isNoContent());

        verify(eventService, times(1)).deleteEventById(eq(eventId));
    }


    @Test
    void testCreateEventStatusIsUnauthorized() throws Exception {
        // given:
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testCreateEventStatusIsUnauthorized(String validatedField) throws Exception {
        // given:
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testUpdateEventStatusIsUnauthorized() throws Exception {
        // given:
        String id = "12";
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/admin/events/{id}", id)
                        .content(eventDtoJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testUpdateEventStatusIsUnauthorized(String validatedField) throws Exception {
        // given:
        var eventDto = new EventPublication();
        eventDto.title("Title");
        eventDto.description("Short Description");
        eventDto.type("PAINTING");
        eventDto.tags(Set.of("tag1", "tag2"));
        eventDto.bannerId("http://example.com/banner.jpg");
        eventDto.content("Some valid content");
        eventDto.begin(LocalDate.now());
        eventDto.end(LocalDate.now().plusDays(1));
        String eventDtoJson = objectMapper.writeValueAsString(eventDto);

        // then:
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventDtoJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteEventIsUnauthorized() throws Exception {
        String eventId = "12";

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/admin/events/{id}", eventId))
                .andExpect(status().isUnauthorized());

        verify(eventService, times(0)).deleteEventById(eq(eventId));
    }
}
