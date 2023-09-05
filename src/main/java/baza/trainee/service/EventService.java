package baza.trainee.service;

import baza.trainee.domain.model.Event;

import java.util.List;

public interface EventService {

    /**
     * @return A list of events.
     */
    List<Event> getEvents();

    /**
     * Retrieve detailed information about a specific event by its id.
     * @param id The unique identifier of the event.
     * @return An Event containing detailed information about the event.
     */
    Event getEventById(String id);

    /**
     * Create a new event based on the provided EventPublicationDto.
     * @param event The Event containing information about the event to be created.
     */
    void createEvent(Event event);

    /**
     * Update an existing event identified by id.
     * @param updatedEvent The Event containing updated information for the event.
     * @param id The unique identifier of the event to be updated.
     */
    void updateEventById(Event updatedEvent, String id);

    /**
     * Delete an event identified by its id.
     * @param id The unique identifier of the event to be deleted.
     */
    void deleteEventById(String id);
}
