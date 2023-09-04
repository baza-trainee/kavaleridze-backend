package baza.trainee.service;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.domain.dto.event.EventPublicationDto;

import java.util.List;

public interface EventService {

    /**
     * @return A list of event previews (List<EventPreviewDto>).
     */
    List<EventPreviewDto> getEvents();

    /**
     * Retrieve detailed information about a specific event by its id.
     * @param id The unique identifier of the event.
     * @return An EventDto containing detailed information about the event.
     */
    EventDto getEventById(String id);

    /**
     * Create a new event based on the provided EventPublicationDto.
     * @param event The EventPublicationDto containing information about the event to be created.
     */
    void createEvent(EventPublicationDto event);

    /**
     * Update an existing event identified by id.
     * @param updatedEvent The EventPublicationDto containing updated information for the event.
     * @param id The unique identifier of the event to be updated.
     */
    void updateEventById(EventPublicationDto updatedEvent, String id);

    /**
     * Delete an event identified by its id.
     * @param id The unique identifier of the event to be deleted.
     */
    void deleteEventById(String id);
}
