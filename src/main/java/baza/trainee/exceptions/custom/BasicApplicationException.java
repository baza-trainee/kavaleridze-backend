package baza.trainee.exceptions.custom;

import org.springframework.http.HttpStatus;

public class BasicApplicationException extends RuntimeException{
    private final HttpStatus httpStatus;

    public BasicApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
