package com.fimsolution.group.app.dto.business.f2f.obligation;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fimsolution.group.app.constant.business.f2f.obligation.CHANGE;
import com.fimsolution.group.app.constant.business.f2f.obligation.SOURCE;
import com.fimsolution.group.app.model.business.f2f.Event;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObligationResDto {


    private String id;


    private LocalDateTime createdAt;

    private Double free;


    private SOURCE source;

    private Boolean use;


    private CHANGE change;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Event event;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Schedule> schedules;

}
