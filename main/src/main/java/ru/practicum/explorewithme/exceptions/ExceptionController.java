package ru.practicum.explorewithme.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({NoSuchElementException.class,
            NotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFound(Exception e, HttpServletRequest request) {
        ApiError response = new ApiError(null, request.getRequestURI(), e.getMessage(), e.toString(),
                HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleSQLException(ConstraintViolationException e, HttpServletRequest request) {
        ApiError response = new ApiError(null, request.getRequestURI(), e.getMessage(), e.toString(),
                HttpStatus.CONFLICT, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ConversionFailedException.class,
            BadRequestException.class
    })
    public ResponseEntity<ApiError> handleBadRequestException(Exception e, HttpServletRequest request) {
        ApiError response = new ApiError(null, request.getRequestURI(), e.getMessage(), e.toString(),
                HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ApiError> handleForbiddenException(Exception e, HttpServletRequest request) {
        ApiError response = new ApiError(null, request.getRequestURI(), e.getMessage(), e.toString(),
                HttpStatus.FORBIDDEN, LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
