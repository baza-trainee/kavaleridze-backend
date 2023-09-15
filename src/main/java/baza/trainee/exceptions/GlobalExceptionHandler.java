package baza.trainee.exceptions;

import baza.trainee.exceptions.custom.BasicApplicationException;
import baza.trainee.exceptions.errors.ErrorResponse;
import baza.trainee.utils.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom application exceptions and logs the error
     * before returning an error response.
     *
     * @param ex The custom application exception to handle.
     * @return A ResponseEntity containing an error response
     * with the exception message and timestamp.
     */
    @ExceptionHandler(BasicApplicationException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(final BasicApplicationException ex) {
        Logger.error(ex.getClass().getSimpleName(), ex.getMessage());

        ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    /**
     * Handles server exceptions and logs the error
     * before returning an internal server error response.
     *
     * @param ex The server exception to handle.
     * @return A ResponseEntity containing an error response
     * with the exception message and timestamp.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleServerException(final Exception ex) {
        Logger.error(ex.getClass().getSimpleName(), ex.getMessage());

        ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
