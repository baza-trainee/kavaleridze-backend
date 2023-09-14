package baza.trainee.integration;

import baza.trainee.domain.dto.event.EventPublication;
import baza.trainee.domain.mapper.EventMapper;
import baza.trainee.domain.model.Event;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

@Import({ EventTestDataInitializer.class })
class EventServiceImplTest extends AbstractIntegrationTest {

    @MockBean
    private SearchService searchService;

    @Autowired
    private EventService eventService;

    @Autowired
    EventMapper mapper;

    /*
     * TODO: fix code style.
     * 
     * Separate code blocks by stages.
     */
    @Test
    @DisplayName("Checking number of pages and objects found.")
    void getAll() {
        var pageable = Pageable.ofSize(10).withPage(0);
        Page<Event> result = eventService.getAll(pageable);
        int numberPage = result.getTotalPages();
        int numberEvents = (int) result.getTotalElements();
        assertEquals(2, numberPage);
        assertEquals(20, numberEvents);
    }

    /*
     * TODO: fix naming.
     * 
     * Add Test postfix to methods names, like 'getByIdTest()'.
     */
    @Test
    @DisplayName("Checking correctness of the search by id.")
    void getById() {

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
        EventPublication eventPublication = new EventPublication(
                "Title1",
                "Description1",
                "Type1",
                null,
                null,
                "event/banner1",
                LocalDate.ofEpochDay(2023 - 5 - 25), // LocalDate.ofEpochDay(long number)
                LocalDate.ofEpochDay(2023 - 5 - 30));

        Event newEvent = eventService.save(eventPublication);
        Event checkEvent = eventService.getById(newEvent.getId());

        assertEquals(newEvent, checkEvent);
    }

    /*
     * TODO: remove commented code before push it to remote.
     */
    // @Test
    // void save() {
    // }

    @Test
    @DisplayName("Checking correctness of update object.")
    void update() {

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
        String id = eventToUpdate.getId();
        Event expected = mapper.toEvent(eventPublicationForUpdate);
        expected.setId(id);

        // when:
        Event actual = eventService.update(id, eventPublicationForUpdate);

        // then:
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Checking delete object.")
    void deleteEventById() {

        var eventPublication = new EventPublication(
                "Title3",
                "Description3",
                "Type3",
                null,
                null,
                "event/banner3",
                LocalDate.ofEpochDay(2023 - 6 - 25),
                LocalDate.ofEpochDay(2023 - 6 - 30));   // << The same is in other dates. TODO: fix wrong method parameter.
        Event eventDelete = eventService.save(eventPublication);
        String id = eventDelete.getId();

        eventService.deleteEventById(id);

        Assertions.assertNotNull(eventService.getById(id));

    }
}
