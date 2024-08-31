package com.fimsolution.group.app.constant;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {

    SUCCESS("200", "Operation Completed Successfully"),

    CREATED("201", "Operation Created Successfully"),

    INVALID_REQUEST("400", "Invalid request"),
    UNAUTHORIZED("401", "Unauthorized access"),
    NOT_FOUND("404", "Resource not found"),
    SERVER_ERROR("500", "Internal server error"),

    CONFLICT("409", "Conflict");

    private final String code;
    private final String message;


    ResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;

    }

}
