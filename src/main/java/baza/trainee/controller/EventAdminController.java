package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventPublication;
import baza.trainee.domain.model.Event;
import baza.trainee.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    /**
     * @param request The EventPublication containing information about the event to be created.
     * @return Saved event.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody final EventPublication request) {
        return eventService.save(request);
    }

    /**
     * Update an existing event identified by its unique identifier.
     *
     * @param id      The unique identifier of the event to be updated.
     * @param request The EventPublication containing the updated information for the event.
     * @return Updated event.
     */
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable("id") final String id,
                             @RequestBody final EventPublication request) {
        return eventService.update(id, request);
    }

    /**
     * Delete an existing event identified by its unique identifier.
     *
     * @param id The unique identifier of the event to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(final @PathVariable("id") String id) {
        eventService.deleteEventById(id);
    }
}
