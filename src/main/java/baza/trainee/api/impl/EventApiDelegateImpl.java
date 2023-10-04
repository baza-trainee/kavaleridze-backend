package baza.trainee.api.impl;

import baza.trainee.api.EventsApiDelegate;
import baza.trainee.dto.EventResponse;
import baza.trainee.dto.PageEvent;
import baza.trainee.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventApiDelegateImpl implements EventsApiDelegate {

    private final EventService eventService;

    @Override
    public ResponseEntity<PageEvent> getAll(Integer size, Integer page) {
        var pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(eventService.getAll(pageable), HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<EventResponse> getById(String id) {
        return new ResponseEntity<>(eventService.getById(id), HttpStatusCode.valueOf(200));
    }
}