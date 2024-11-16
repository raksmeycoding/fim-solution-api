package com.fimsolution.group.app.dto.business.f2f;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fimsolution.group.app.constant.business.f2f.event.MEDIUM;
import com.fimsolution.group.app.constant.business.f2f.event.TYPE;
import com.fimsolution.group.app.model.business.f2f.Loan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsDto {

    @JsonIgnore
    private String id;


//    private String userId;


    private LocalDateTime createdEventDateTime;


    private TYPE type;


    private MEDIUM medium;

    private String memo;

    private Loan loan;

    @JsonProperty

    public String getId() {
        return id;
    }

    @JsonProperty

    public String getMemo() {
        return memo;
    }
}
