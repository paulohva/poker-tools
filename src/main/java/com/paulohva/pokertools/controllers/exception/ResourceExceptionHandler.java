package com.paulohva.pokertools.controllers.exception;

import com.paulohva.pokertools.services.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception handler class for Bean Validation exceptions and Runtime failures on business rules
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<StandardError> invalidRequest(InvalidRequestException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Invalid Value(s)", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Invalid Argument", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}