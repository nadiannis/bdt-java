package com.nadiannis.reactive_r2dbc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import com.nadiannis.reactive_r2dbc.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
        Mono<ErrorResponse> errorResponse = Mono.just(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage())
        );
        return errorResponse;
    }

}
