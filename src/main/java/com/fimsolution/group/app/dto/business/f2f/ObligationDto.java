package com.fimsolution.group.app.dto.business.f2f;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fimsolution.group.app.constant.business.f2f.obligation.CHANGE;
import com.fimsolution.group.app.constant.business.f2f.obligation.SOURCE;
import com.fimsolution.group.app.model.business.f2f.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ObligationDto {


    @JsonIgnore
    private String id;


    private String eventId;



    private LocalDateTime createdAt;

    private Double free;


    private SOURCE source;

    private Boolean use;


    private CHANGE change;


    @JsonIgnore
    private Event event;

    @JsonProperty
    public String getId() {
        return id;
    }

    public static ObligationDto empty() {
        return null;
    }
}
