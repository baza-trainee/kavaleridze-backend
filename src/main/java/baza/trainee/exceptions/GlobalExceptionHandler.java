package baza.trainee.exceptions;

import baza.trainee.exceptions.custom.BasicApplicationException;
import baza.trainee.exceptions.errors.ErrorResponse;
import baza.trainee.utils.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LoggingService loggingService;

    @ExceptionHandler(BasicApplicationException.class)
    private ResponseEntity<ErrorResponse> handleCustomException(
            BasicApplicationException ex) {
        loggingService.logError(ex.getClass().getName(), ex.getMessage());

        ErrorResponse response =
                new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerException(Exception ex) {
        loggingService.logError(ex.getClass().getName(), ex.getMessage());

        ErrorResponse response =
                new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
