package com.nadiannis.phinroll.exception;

import com.nadiannis.phinroll.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.nadiannis.phinroll.utils.Strings.camelToSnake;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error("HttpMessageNotReadableException", ex);
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = camelToSnake(((FieldError) error).getField());
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        logger.error("MethodArgumentNotValidException", ex);
        ErrorResponse<Map<String, String>> errorResponse = new ErrorResponse<>(LocalDateTime.now(), "invalid request body", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<?>> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        logger.error("ResourceNotFoundException", ex);
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<?>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex, WebRequest request) {
        logger.error("ResourceAlreadyExistsException", ex);
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse<?>> handleMethodAgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        logger.error("MethodArgumentTypeMismatchException", ex);
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<?>> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Exception", ex);
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
