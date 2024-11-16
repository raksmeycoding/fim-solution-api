package com.fimsolution.group.app.dto.business.f2f.obligation;


import com.fimsolution.group.app.constant.business.f2f.obligation.CHANGE;
import com.fimsolution.group.app.constant.business.f2f.obligation.SOURCE;
import com.fimsolution.group.app.model.business.f2f.Event;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObligationDto {


    private String id;




    private LocalDateTime createdAt;

    private Double free;


    private SOURCE source;

    private Boolean use;


    private CHANGE change;





    private Event event;


    private List<Schedule> schedules;


}
