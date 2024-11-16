package com.fimsolution.group.app.dto.business.f2f.obligation;


import com.fimsolution.group.app.constant.business.f2f.obligation.CHANGE;
import com.fimsolution.group.app.constant.business.f2f.obligation.SOURCE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObligationReqDto {

    private String eventId;

    private LocalDateTime createdAt;

    private Double free;


    private SOURCE source;

    private Boolean use;


    private CHANGE change;

}
