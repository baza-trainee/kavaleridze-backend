package baza.trainee.exceptions.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BasicApplicationException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BasicApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
