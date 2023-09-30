package baza.trainee.integration;

import baza.trainee.domain.mapper.EventMapper;
import baza.trainee.domain.model.Event;
import baza.trainee.dto.ContentBlock;
import baza.trainee.dto.EventPublication;
import baza.trainee.dto.PageEvent;
import baza.trainee.exceptions.custom.EntityNotFoundException;
import baza.trainee.service.EventService;
import baza.trainee.service.ImageService;
import baza.trainee.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static baza.trainee.dto.ContentBlock.BlockTypeEnum.PICTURE_BLOCK;
import static org.junit.jupiter.api.Assertions.*;

@Import({EventTestDataInitializer.class})
class EventServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper mapper;

    @MockBean
    private SearchService searchService;

    @MockBean
    private HttpServletRequest httpServletRequest;

    @MockBean
    private ImageService imageService;

    @Test
    @DisplayName("Checking number of pages and objects found.")
    void getAllTest() {

        // given:
        var pageable = Pageable.ofSize(10).withPage(0);
        PageEvent result = eventService.getAll(pageable);

        // when:
        int numberOfElements = result.getNumberOfElements();

        // then:
        assertEquals(10, numberOfElements);

        for (var event : result.getContent()) {
            assertNotNull(event.getId());
            assertNotNull(event.getCreated());
        }
    }

    @Test
    @DisplayName("Checking correctness of the search by id.")
    void getByIdTest() {

        // given:
        ContentBlock cb = new ContentBlock();
        cb.setId("thisId");
        cb.setOrder(14);
        cb.setColumns(5);
        cb.setBlockType(PICTURE_BLOCK);
        cb.setTextContent("thisContent");
        MockHttpSession session = new MockHttpSession(null, "httpSessionId");

        var eventPublication = new EventPublication();
        eventPublication.title("Title");
        eventPublication.description("Short Description");
        eventPublication.type("PAINTING");
        eventPublication.tags(Set.of("tag1", "tag2"));
        eventPublication.content("content");
        eventPublication.bannerTempURI("http://example.com/banner.jpg");
        eventPublication.begin(LocalDate.now());
        eventPublication.end(LocalDate.now().plusDays(10));

        // when:
        var createdEvent = eventService.save(eventPublication, session.getId());

        // then:
        assertFalse(createdEvent.getId().isEmpty());
        assertNotNull(createdEvent.getCreated());
        assertEquals(eventPublication.getTitle(), createdEvent.getTitle());
        assertEquals(eventPublication.getDescription(), createdEvent.getDescription());
        assertEquals(eventPublication.getBegin(), createdEvent.getBegin());
        assertEquals(eventPublication.getEnd(), createdEvent.getEnd());
    }


    @Test
    @DisplayName("Checking correctness of update object.")
    void updateTest() {

        // given:
        ContentBlock cb = new ContentBlock();
        cb.setId("thisId");
        cb.setOrder(14);
        cb.setColumns(5);
        cb.setBlockType(PICTURE_BLOCK);
        cb.setTextContent("thisContent");
        Set<ContentBlock> contentBlocks = new HashSet<>();
        contentBlocks.add(cb);
        MockHttpSession session = new MockHttpSession(null, "httpSessionId");

        var eventPublication = new EventPublication();
        eventPublication.title("Title");
        eventPublication.description("Short Description");
        eventPublication.type("PAINTING");
        eventPublication.tags(Set.of("tag1", "tag2"));
        eventPublication.content("content");
        eventPublication.bannerTempURI("http://example.com/banner.jpg");
        eventPublication.begin(LocalDate.now());
        eventPublication.end(LocalDate.now().plusDays(10));
        var eventToUpdate = eventService.save(eventPublication, session.getId());

        eventPublication.setTitle("TitleUpdate");
        eventPublication.setDescription("DescriptionUpdate");
        eventPublication.setType("TypeUpdate");
        eventPublication.setBannerTempURI("event/bannerUpdate");

        // when:
        String id = eventToUpdate.getId();
        Event event = mapper.toEvent(eventPublication);
        event.setId(id);
        var expected = mapper.toResponse(event);
        var actual = eventService.update(id, eventPublication);

        // then:
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getBannerURI(), actual.getBannerURI());

    }

    @Test
    @DisplayName("Checking delete object.")
    void deleteEventByIdTest() {

        // given:
        var session = new MockHttpSession(null, "httpSessionId");

        var eventPublication = new EventPublication();
        eventPublication.title("Title");
        eventPublication.description("Short Description");
        eventPublication.type("PAINTING");
        eventPublication.tags(Set.of("tag1", "tag2"));
        eventPublication.content("content");
        eventPublication.bannerTempURI("http://example.com/banner.jpg");
        eventPublication.begin(LocalDate.now());
        eventPublication.end(LocalDate.now().plusDays(10));

        // when:
        var eventDelete = eventService.save(eventPublication, session.getId());
        String id = eventDelete.getId();
        eventService.deleteEventById(id);

        // then:
        assertThrows(EntityNotFoundException.class, () -> eventService.getById(id),
                "Event with `ID: " + id + "` was not found!");
    }
}
