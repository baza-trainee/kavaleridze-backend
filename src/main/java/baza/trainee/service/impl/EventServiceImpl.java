package baza.trainee.service.impl;


import baza.trainee.domain.mapper.EventMapper;
import baza.trainee.domain.mapper.PageEventMapper;
import baza.trainee.domain.model.Event;
import baza.trainee.dto.EventPublication;
import baza.trainee.dto.EventResponse;
import baza.trainee.dto.PageEvent;
import baza.trainee.exceptions.custom.EntityNotFoundException;
import baza.trainee.repository.EventRepository;
import baza.trainee.service.EventService;
import baza.trainee.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ImageService imageService;
    private final EventMapper eventMapper;
    private final PageEventMapper pageMapper;

    @Override
    public PageEvent getAll(Pageable pageable) {
        return pageMapper.toPageEvent(eventRepository.findAll(pageable)
                .map(eventMapper::toResponse));
    }

    @Override
    public EventResponse getById(String id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(getNotFoundExceptionSupplier(id));
        return eventMapper.toResponse(event);
    }

    @Override
    @Transactional
    public EventResponse save(EventPublication newEvent, String sessionId) {
        Event eventToSave = eventMapper.toEvent(newEvent);

        var fileNames = newEvent.getBanner();

        imageService.persist(List.of(fileNames), sessionId);
        Event savedEvent = eventRepository.save(eventToSave);

        return eventMapper.toResponse(savedEvent);
    }

    @Override
    @Transactional
    public EventResponse update(String id, EventPublication publication, String sessionId) {
        var eventToUpdate = eventRepository.findById(id)
                .orElseThrow(getNotFoundExceptionSupplier(id));
        var eventForUpdate = eventMapper.toEvent(publication);
        eventForUpdate.setId(eventToUpdate.getId());
        eventForUpdate.setCreated(eventToUpdate.getCreated());

        imageService.persist(List.of(eventForUpdate.getBannerId()), sessionId);
        var updatedEvent = eventRepository.update(eventForUpdate);

        return eventMapper.toResponse(updatedEvent);
    }

    @Override
    @Transactional
    public void deleteEventById(String id) {
        getById(id);
        eventRepository.deleteById(id);
    }

    private static Supplier<EntityNotFoundException> getNotFoundExceptionSupplier(String id) {
        return () -> new EntityNotFoundException("Event", "ID: " + id);
    }
}
