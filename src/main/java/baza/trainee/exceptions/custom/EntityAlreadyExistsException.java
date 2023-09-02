package baza.trainee.exceptions.custom;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends BasicApplicationException{
    public EntityAlreadyExistsException(String entityType, String details) {
        super(String.format("%s with `%s` already exists!", entityType, details),
                HttpStatus.CONFLICT);
    }
}
