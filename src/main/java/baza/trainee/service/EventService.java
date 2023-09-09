package baza.trainee.service;

import baza.trainee.domain.dto.event.EventPublication;
import baza.trainee.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    /**
     * @return A list of events.
     */
    Page<Event> getAll(Pageable pageable);

    /**
     * Retrieve detailed information about a specific event by its id.
     * @param id The unique identifier of the event.
     * @return An Event containing detailed information about the event.
     */
    Event getById(String id);

    /**
     * Create a new event based on the provided EventPublicationDto.
     * @param newEvent The EventPublicationDto containing information about the event to be created.
     * @return Saved event.
     */
    Event save(EventPublication newEvent);

    /**
     * Update an existing event identified by id.
     * @param updatedEvent The EventPublication containing updated information for the event.
     * @param id The unique identifier of the event to be updated.
     * @return Updated event.
     */
    Event update(String id, EventPublication updatedEvent);

    /**
     * Delete an event identified by its id.
     * @param id The unique identifier of the event to be deleted.
     */
    void deleteEventById(String id);
}
