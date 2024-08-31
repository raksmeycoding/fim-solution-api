package com.fimsolution.group.app.exception;


import com.fimsolution.group.app.dto.GenericDto;
import com.fimsolution.group.app.constant.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GenericDto<?>> handleRuntimeException(Exception exception) {
        logger.error(":::Unhandled exception occurred:::", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericDto.builder()
                        .message(exception.getMessage())
                        .code(ResponseStatus.SERVER_ERROR.getCode())
                        .data(null)
                        .build());
    }


    @ExceptionHandler({AlreadyExistException.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<GenericDto<?>> alreadyExistException(AlreadyExistException alreadyExistException) {
        logger.error(":::Unhandled exception occurred:::", alreadyExistException);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(GenericDto.builder()
                        .message(alreadyExistException.getMessage())
                        .code(ResponseStatus.CONFLICT.getCode())
                        .data(null)
                        .build());

    }


    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericDto<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField().replace("data.", "");
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GenericDto.<Map<String, String>>builder().message("Invalid validation").code(String.valueOf(HttpStatus.BAD_REQUEST.value())).data(errors).build());
    }
}
