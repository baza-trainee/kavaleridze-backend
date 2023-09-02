package baza.trainee.utils;

import baza.trainee.exceptions.custom.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ControllerUtils {
    public static void handleFieldsErrors(final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(err -> errorMessage.append(err.getField())
                    .append(" - ").append(err.getDefaultMessage())
                    .append(";"));

            throw new MethodArgumentNotValidException(errorMessage.toString());
        }
    }
}
