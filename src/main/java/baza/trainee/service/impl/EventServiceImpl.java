package baza.trainee.service.impl;


import baza.trainee.domain.mapper.EventMapper;
import baza.trainee.domain.model.Event;
import baza.trainee.dto.EventPublication;
import baza.trainee.dto.EventResponse;
import baza.trainee.exceptions.custom.EntityNotFoundException;
import baza.trainee.repository.EventRepository;
import baza.trainee.service.EventService;
import baza.trainee.service.ImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    private final ImageService imageService;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, ImageService imageService) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.imageService = imageService;
    }

    @Override
    public Page<Event> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public EventResponse getById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(getNotFoundExceptionSupplier(id));
        return eventMapper.toResponse(event);
    }

    @Override
    public EventResponse save(EventPublication newEvent, String sessionId) {
        Event eventToSave = eventMapper.toEvent(newEvent);

        var fileNames = newEvent.getBannerTempURI();

        imageService.persist(List.of(fileNames), sessionId);
        Event savedEvent = eventRepository.save(eventToSave);

        return eventMapper.toResponse(savedEvent);
    }

    @Override
    public EventResponse update(String id, EventPublication publication) {
        var eventToUpdate = eventRepository.findById(id)
                .orElseThrow(getNotFoundExceptionSupplier(id));
        var eventForUpdate = eventMapper.toEvent(publication);
        eventForUpdate.setId(eventToUpdate.getId());
        eventForUpdate.setCreated(eventToUpdate.getCreated());

        var updatedEvent = eventRepository.update(eventForUpdate);

        return eventMapper.toResponse(updatedEvent);
    }

    @Override
    public void deleteEventById(String id) {
        getById(id);
        eventRepository.deleteById(id);
    }

    private static Supplier<EntityNotFoundException> getNotFoundExceptionSupplier(String id) {
        return () -> new EntityNotFoundException("Event", "ID: " + id);
    }
}
