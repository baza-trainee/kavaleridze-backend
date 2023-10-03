package baza.trainee.api.impl;

import baza.trainee.api.AdminApiDelegate;
import baza.trainee.dto.EventPublication;
import baza.trainee.dto.EventResponse;
import baza.trainee.service.EventService;
import baza.trainee.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class AdminApiDelegateImpl implements AdminApiDelegate {

    private final EventService eventService;
    private final ImageService imageService;
    private final HttpServletRequest httpServletRequest;

    public AdminApiDelegateImpl(
            EventService eventService,
            ImageService imageService,
            HttpServletRequest httpServletRequest) {
        this.eventService = eventService;
        this.imageService = imageService;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return AdminApiDelegate.super.getRequest();
    }

    @Override
    public ResponseEntity<EventResponse> createEvent(EventPublication eventPublication) {
        var sessionId = httpServletRequest.getSession().getId();
        return new ResponseEntity<>(
                eventService.save(eventPublication, sessionId),
                HttpStatusCode.valueOf(201));
    }

    @Override
    public ResponseEntity<EventResponse> updateEvent(String id, EventPublication eventPublication) {
        var sessionId = httpServletRequest.getSession().getId();
        return new ResponseEntity<>(
                eventService.update(id, eventPublication, sessionId),
                HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<Void> deleteEvent(String id) {
        eventService.deleteEventById(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }

    @Override
    public ResponseEntity<byte[]> getTempImage(String filename) {
        var sessionId = httpServletRequest.getSession().getId();
        return new ResponseEntity<>(
                imageService.loadTempResource(filename, sessionId),
                HttpStatusCode.valueOf(200));
    }

    @Override
    public ResponseEntity<String> saveImage(MultipartFile file) {
        var sessionId = httpServletRequest.getSession().getId();
        return new ResponseEntity<>(
                imageService.storeToTemp(file, sessionId),
                HttpStatusCode.valueOf(201));
    }
}
