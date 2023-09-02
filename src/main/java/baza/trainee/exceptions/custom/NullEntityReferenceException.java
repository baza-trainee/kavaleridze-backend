package baza.trainee.exceptions.custom;

import org.springframework.http.HttpStatus;

public class NullEntityReferenceException extends BasicApplicationException {
    public NullEntityReferenceException(String entityType) {
        super(String.format("%s can`nt be null!", entityType),
                HttpStatus.BAD_REQUEST);
    }
}
