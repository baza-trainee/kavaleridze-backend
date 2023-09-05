package baza.trainee.domain.dto.event;

import baza.trainee.domain.model.Event;

public class EventConverter {

    /**
     * Converts an Event object to an EventDto object.
     * @param event The Event object to be converted.
     * @return An EventDto object representing the converted Event.
     */
    public EventDto eventToEventDto(final Event event) {
        return new EventDto(
                event.getId(),
                event.getContentType(),
                event.getTitle(),
                event.getContent(),
                event.getPicture()
        );
    }

    /**
     * Converts an Event object to an EventPreviewDto object.
     * @param event The Event object to be converted.
     * @return An EventPreviewDto object representing the converted Event.
     */
    public EventPreviewDto eventToEventPreviewDto(final Event event) {
        return new EventPreviewDto(
                event.getId(),
                event.getContentType(),
                event.getTitle(),
                event.getContent(),
                event.getPicturePreview()
        );
    }

    /**
     * Converts an EventPublicationDto object to an Event object.
     * @param newEvent The EventPublicationDto object to be converted.
     * @return An Event object representing the converted EventPublicationDto.
     */
    public Event eventPublicationDtoToEvent(final EventPublicationDto newEvent) {
        Event event = new Event();
        event.setTitle(newEvent.title());
        event.setContent(newEvent.content());
        if (newEvent.picture() != null) {
            event.setPicture(newEvent.picture());
        }
        if (newEvent.picturePreview() != null) {
            event.setPicturePreview(newEvent.picturePreview());
        }
        event.setBegin(newEvent.begin());
        event.setEnd(newEvent.end());
        return event;
    }

}
