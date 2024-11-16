package com.fimsolution.group.app.dto;


import jakarta.validation.Valid;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto<T> {

    @Valid
    T request;
}
