package baza.trainee.exceptions.custom;

import org.springframework.http.HttpStatus;

public class MethodArgumentNotValidException extends BasicApplicationException{
    public MethodArgumentNotValidException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
