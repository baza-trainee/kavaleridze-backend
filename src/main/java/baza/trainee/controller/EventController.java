package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.exceptions.custom.EntityNotFoundException;
import baza.trainee.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * @return  A list of event previews.
     */
    @GetMapping
    public List<EventPreviewDto> getEvents() {
        return eventService.getEvents();
    }

    /**
     * @param id The event id for looking a certain event
     * @return An event dto.
     */
    @GetMapping("/{id}")
    public EventDto getEvent(final @PathVariable String id) throws EntityNotFoundException {
        return eventService.getEventById(id);
    }
}
