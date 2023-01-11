package ru.practicum.explorewithme.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNotFound(NoSuchElementException e) {
        ApiError response = new ApiError(null, e.getMessage(), e.toString(),
                HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleSQLException(ConstraintViolationException e) {
        ApiError response = new ApiError(null, e.getMessage(), e.toString(),
                HttpStatus.CONFLICT, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
