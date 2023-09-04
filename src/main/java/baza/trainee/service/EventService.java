package baza.trainee.service;

import baza.trainee.domain.dto.event.EventDto;
import baza.trainee.domain.dto.event.EventPreviewDto;
import baza.trainee.domain.dto.event.EventPublicationDto;

import java.util.List;

public interface EventService {

    List<EventPreviewDto> getEvents();

    EventDto getEventById(String id);

    void createEvent(EventPublicationDto event);

    void updateEventById(EventPublicationDto updatedEvent, String id);

    void deleteEventById(String id);
}
