package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventConverter;
import baza.trainee.domain.dto.event.EventPublicationDto;
import baza.trainee.domain.model.Event;
import baza.trainee.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;
    private final EventConverter eventConverter;

    /**
     * @param eventPublicationDto The EventPublicationDto containing information about the event to be created.
     * @return ResponseEntity with an HTTP status code indicating the result of the operation.
     */
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(final EventPublicationDto eventPublicationDto) {
        Event newEvent = eventConverter.eventPublicationDtoToEvent(eventPublicationDto);
        eventService.createEvent(newEvent);
        return ResponseEntity.ok().build();
    }

    /**
     * Update an existing event identified by its unique identifier.
     * @param id The unique identifier of the event to be updated.
     * @param eventPublicationDto The EventPublicationDto containing the updated information for the event.
     * @return ResponseEntity with an HTTP status code indicating the result of the operation.
     */
    @PutMapping("events/{id}")
    public ResponseEntity<?> updateEvent(final @PathVariable String id, final EventPublicationDto eventPublicationDto) {
        Event updatedEvent = eventConverter.eventPublicationDtoToEvent(eventPublicationDto);
        eventService.updateEventById(updatedEvent, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete an existing event identified by its unique identifier.
     * @param id The unique identifier of the event to be deleted.
     * @return ResponseEntity with an HTTP status code indicating the result of the operation.
     */
    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvent(final @PathVariable String id) {
        eventService.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }
}
