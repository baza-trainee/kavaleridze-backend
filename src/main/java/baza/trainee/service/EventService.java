package baza.trainee.service;

import baza.trainee.dto.EventPublication;
import baza.trainee.dto.EventResponse;
import baza.trainee.dto.PageEvent;
import org.springframework.data.domain.Pageable;

public interface EventService {

    /**
     * @param pageable {@link Pageable} object.
     * @return A Page of events.
     */
    PageEvent getAll(Pageable pageable);

    /**
     * Retrieve detailed information about a specific event by its id.
     * @param id The unique identifier of the event.
     * @return An Event with given ID.
     */
    EventResponse getById(String id);

    /**
     * Create a new event based on the provided EventPublicationDto.
     *
     * @param newEvent The EventPublication containing information about the event to be created.
     * @param id - user id
     * @return Saved event.
     */
    EventResponse save(EventPublication newEvent, String id);

    /**
     * Update an existing event identified by id.
     * @param updatedEvent The EventPublication containing updated information for the event.
     * @param id The unique identifier of the event to be updated.
     * @return Updated event.
     */
    EventResponse update(String id, EventPublication updatedEvent);

    /**
     * Delete an event identified by its id.
     * @param id The unique identifier of the event to be deleted.
     */
    void deleteEventById(String id);
}
