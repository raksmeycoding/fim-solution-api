package com.fimsolution.group.app.dto.business.f2f.schedule;


import com.fimsolution.group.app.constant.business.f2f.schedule.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleAmountDueDto {
    private String scheduleId;
    private String borrowerOrLender;
    private String loanId;
    private SOURCE source;
    private STATUS status;
    private LocalDateTime createdAt;
    private Double due;


}
