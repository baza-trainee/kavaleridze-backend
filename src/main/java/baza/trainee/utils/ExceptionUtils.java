package baza.trainee.utils;

import java.util.function.Supplier;

import baza.trainee.exceptions.custom.EntityNotFoundException;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExceptionUtils {
    

    public static Supplier<EntityNotFoundException> getNotFoundExceptionSupplier(Class<?> clazz, String id) {
        return () -> new EntityNotFoundException(clazz.getSimpleName(), " with: " + id + " not found");
    }
}
