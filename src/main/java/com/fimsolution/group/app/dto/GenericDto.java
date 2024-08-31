package com.fimsolution.group.app.dto;


import com.fimsolution.group.app.constant.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericDto<T> {

    @Schema(description = "The system requestId for tacking", example = "938db946273a4399a34d3c42d2d40212")

    String requestId;
    //    String responseId;
    String message;

    @Schema(description = "The system response code for tacking")
    String code;


    @Valid
    T data;


    public GenericDto(ResponseStatus responseStatus, T data) {
        this.message = responseStatus.getMessage();
        this.code = responseStatus.getCode();
        this.data = data;
    }

    public GenericDto(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }

}
