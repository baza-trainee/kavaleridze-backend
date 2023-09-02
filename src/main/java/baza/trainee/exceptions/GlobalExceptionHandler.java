package baza.trainee.exceptions;

import baza.trainee.exceptions.custom.BasicApplicationException;
import baza.trainee.exceptions.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BasicApplicationException.class)
    private ResponseEntity<ErrorResponse> handleCustomException(
            BasicApplicationException ex) {
        ErrorResponse response =
                new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerException(Exception ex) {
        ErrorResponse response =
                new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
