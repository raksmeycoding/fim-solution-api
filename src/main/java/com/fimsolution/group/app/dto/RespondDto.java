package com.fimsolution.group.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespondDto<T> {

    private HttpStatus httpStatusName;
    private int httpStatusCode;
    private String errorMessage;
    private String message;
    private T data;
}
