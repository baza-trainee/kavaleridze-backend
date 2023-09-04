package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.domain.dto.event.EventPublicationDto;
import baza.trainee.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    /**
     * @return  A list of event previews.
     */
    @GetMapping
    public ResponseEntity<List<EventPreviewDto>> getEvents() {
        List<EventPreviewDto> events = eventService.getEvents();
        return ResponseEntity.ok(events);
    }

    /**
     * @param id The event id for looking a certain event
     * @return An event dto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(final @PathVariable String id) {
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    /**
     * @param event The EventPublicationDto containing information about the event to be created.
     * @return ResponseEntity with an HTTP status code indicating the result of the operation.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(final EventPublicationDto event) {
        eventService.createEvent(event);
        return ResponseEntity.ok().build();
    }

    /**
     * Update an existing event identified by its unique identifier.
     * @param id The unique identifier of the event to be updated.
     * @param updatedEvent The EventPublicationDto containing the updated information for the event.
     * @return ResponseEntity with an HTTP status code indicating the result of the operation.
     */
    @PostMapping("/{id}/updated")
    public ResponseEntity<?> updateEvent(final @PathVariable String id, final EventPublicationDto updatedEvent) {
        eventService.updateEventById(updatedEvent, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete an existing event identified by its unique identifier.
     * @param id The unique identifier of the event to be deleted.
     * @return ResponseEntity with an HTTP status code indicating the result of the operation.
     */
    @PostMapping("/events/{id}/delete")
    public ResponseEntity<?> deleteEvent(final @PathVariable String id) {
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }
}
