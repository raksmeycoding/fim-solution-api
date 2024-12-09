package com.fimsolution.group.app.exception;


import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.constant.ResponseStatus;
import com.fimsolution.group.app.dto.RespondDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<RespondDto<?>> handleRuntimeException(Exception exception) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID {}: {}", errorId, exception.getMessage());
        logger.error(":::Unhandled exception occurred object:::", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespondDto.builder()
                        .message(exception.getMessage())
                        .httpStatusName(HttpStatus.INTERNAL_SERVER_ERROR)
                        .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .errorMessage(exception.getMessage())
                        .data(RespondDto.builder().data(Map.of("errorId", errorId)).build())
                        .build());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> handleAuthException(InternalAuthenticationServiceException exception) {
        String errorId = UUID.randomUUID().toString();
        logger.error("Error ID {}: {}", errorId, exception.getMessage());
        logger.error(":::Unhandled exception occurred object:::", exception);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(RespondDto.builder()
                        .message(exception.getMessage())
                        .httpStatusName(HttpStatus.UNAUTHORIZED)
                        .httpStatusCode(HttpStatus.UNAUTHORIZED.value())
                        .errorMessage(exception.getMessage())
                        .data(RespondDto.builder().data(Map.of("errorId", errorId)).build())
                        .build());
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<RespondDto<?>> handleNotFoundException(NotFoundException exception) {
        logger.error(":::Unhandled exception occurred::: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespondDto.builder()
                .errorMessage(exception.getMessage())
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .httpStatusName(HttpStatus.NOT_FOUND)
                .data(null)
                .build());

    }


    @ExceptionHandler({AlreadyExistException.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<RespondDto<?>> alreadyExistException(AlreadyExistException alreadyExistException) {
        logger.error(":::Unhandled exception occurred:::", alreadyExistException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(RespondDto.builder()
                        .message(alreadyExistException.getMessage())
                        .httpStatusName(HttpStatus.CONFLICT)
                        .httpStatusCode(HttpStatus.CONFLICT.value())
                        .data(null)
                        .build());

    }


    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespondDto<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField().replace("data.", "");
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RespondDto.<Map<String, String>>builder().message("Invalid validation").httpStatusCode(HttpStatus.BAD_REQUEST.value()).httpStatusName(HttpStatus.BAD_REQUEST).data(errors).build());
    }


    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RespondDto<?>> badCredential(BadCredentialsException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RespondDto.builder().message("Incorrect username or password!").build());

    }
}
