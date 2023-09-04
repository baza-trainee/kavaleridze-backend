package baza.trainee.controller;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
