package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventConverter;
import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.domain.model.Event;
import baza.trainee.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventConverter eventConverter;

    /**
     * @return  A list of event previews.
     */
    @GetMapping
    public List<EventPreviewDto> getEvents() {
        List<Event> events = eventService.getEvents();

        return events.stream()
                .map(eventConverter::eventToEventPreviewDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id The event id for looking a certain event
     * @return An event dto.
     */
    @GetMapping("/{id}")
    public EventDto getEvent(final @PathVariable String id) {
        Event event = eventService.getEventById(id);
        return eventConverter.eventToEventDto(event);
    }
}
