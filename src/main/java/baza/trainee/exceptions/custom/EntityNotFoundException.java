package baza.trainee.exceptions.custom;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BasicApplicationException {
    public EntityNotFoundException(String entityType, String details) {
        super(String.format("%s with `%s` was not found!", entityType, details),
                HttpStatus.NOT_FOUND);
    }
}
