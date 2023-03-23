package com.example.country.rest;

import com.example.country.exceptions.RateLimitExceededException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(
        ConstraintViolationException ex, WebRequest request)
    {
        List<String> errors = ex
            .getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .toList();

        return new ResponseEntity<>(Map.of("errors", errors),
            HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<?> exception(RateLimitExceededException ex, WebRequest request)
    {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(Map.of("errors", errors),
            HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> exception(Exception ex, WebRequest request)
    {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(Map.of("errors", errors),
            HttpStatus.BAD_REQUEST);
    }
}
