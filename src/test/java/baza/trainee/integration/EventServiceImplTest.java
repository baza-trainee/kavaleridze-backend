package baza.trainee.integration;

import baza.trainee.domain.dto.event.EventPublication;
import baza.trainee.domain.mapper.EventMapper;
import baza.trainee.domain.model.Event;
import baza.trainee.exceptions.custom.EntityNotFoundException;
import baza.trainee.service.EventService;
import baza.trainee.service.SearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Import({EventTestDataInitializer.class})
class EventServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventMapper mapper;

    @MockBean
    private SearchService searchService;

    /*
     * TODO: fix code style.
     *
     * Separate code blocks by stages.
     */
    @Test
    @DisplayName("Checking number of pages and objects found.")
    void getAllTest() {

        // given:
        var pageable = Pageable.ofSize(10).withPage(0);
        Page<Event> result = eventService.getAll(pageable);

        // when:
        int numberOfElements = result.getNumberOfElements();
        int numberPage = result.getTotalPages();                // This check is redundant.
        int numberEvents = (int) result.getTotalElements();

        // then:
        assertEquals(10, numberOfElements);
        assertEquals(20, numberEvents);

        /*
         * You can check fields that are set by DB, like ID or date of record creation.
         */
        for (var event : result) {
            assertNotNull(event.getId());
            assertNotNull(event.getCreated());
        }
    }

    /*
     * TODO: fix naming.
     *
     * Add Test postfix to methods names, like 'getByIdTest()'.
     */
    @Test
    @DisplayName("Checking correctness of the search by id.")
    void getByIdTest() {

        /*
         * TODO: fix dates as in update method below.
         *
         * LocalDate.ofEpochDay(long number) << But you try to pass '2023-5-25' like a
         * date.
         *
         * Assuming that this timestamp is in seconds: 2023525
         * GMT: Saturday, January 24, 1970 10:05:25 AM
         * Your time zone: Saturday, January 24, 1970 1:05:25 PM GMT+03:00
         * Relative: 54 years ago
         *
         * And do not try to pass concrete date.
         * Checks is relative to date of tests execution.
         * If you provide Sep 14 2023 in date that checked
         * by {@link FutureOrPresent} annotation,
         * in Sep 15 2023 your tests become invalid.
         *
         */
        // given:
        EventPublication eventPublication = new EventPublication(
                "Title1",
                "Description1",
                "Type1",
                null,
                null,
                "event/banner1",
                LocalDate.now(), // LocalDate.ofEpochDay(long number)
                LocalDate.now().plusDays(10));

        // when:
        Event newEvent = eventService.save(eventPublication);
        Event checkEvent = eventService.getById(newEvent.getId());

        // then:
        assertEquals(newEvent, checkEvent);
    }


    @Test
    @DisplayName("Checking correctness of update object.")
    void updateTest() {

        // given:
        var eventPublication = new EventPublication(
                "Title2",
                "Description2",
                "Type2",
                null,
                null,
                "event/banner2",
                LocalDate.now(),
                LocalDate.now().plusDays(10));
        Event eventToUpdate = eventService.save(eventPublication);

        var eventPublicationForUpdate = new EventPublication(
                "TitleUpdate",
                "DescriptionUpdate",
                "TypeUpdate",
                null,
                null,
                "event/bannerUpdate",
                LocalDate.now(),
                LocalDate.now().plusDays(10));

        // when:
        String id = eventToUpdate.getId();
        Event expected = mapper.toEvent(eventPublicationForUpdate);
        expected.setId(id);
        Event actual = eventService.update(id, eventPublicationForUpdate);

        // then:
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Checking delete object.")
    void deleteEventByIdTest() {

        // given:
        var eventPublication = new EventPublication(
                "Title3",
                "Description3",
                "Type3",
                null,
                null,
                "event/banner3",
                LocalDate.now(),
                LocalDate.now().plusDays(10));   // << The same is in other dates. TODO: fix wrong method parameter.

        // when:
        Event eventDelete = eventService.save(eventPublication);
        String id = eventDelete.getId();
        eventService.deleteEventById(id);

        // then:
        /*
         * You throw an exception in your method when the Event is not found.
         * And, as we want, it is not found.
         * So, you should check that an exception is thrown in assertions.
         */
        assertThrows(EntityNotFoundException.class, () -> eventService.getById(id),
                "Event with `ID: " + id + "` was not found!");
    }
}
