package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.domain.dto.event.EventPublicationDto;
import baza.trainee.service.EventService;
import baza.trainee.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping
    public ResponseEntity<List<EventPreviewDto>> getEvents(){
        List<EventPreviewDto> events = eventService.getEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEvent(@PathVariable String id){
        EventDto event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(EventPublicationDto event){
        eventService.createEvent(event);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/updated")
    public ResponseEntity<?> updateEvent(@PathVariable String id, EventPublicationDto updatedEvent){
        eventService.updateEventById(updatedEvent, id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/events/{id}/delete")
    public ResponseEntity<?> deleteEvent(@PathVariable String id){
        eventService.deleteEventById(id);
        return ResponseEntity.ok().build();
    }
}
