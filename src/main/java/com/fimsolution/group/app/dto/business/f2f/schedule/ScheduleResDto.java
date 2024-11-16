package com.fimsolution.group.app.dto.business.f2f.schedule;


import com.fimsolution.group.app.constant.business.f2f.schedule.DELINQUENCY;
import com.fimsolution.group.app.constant.business.f2f.schedule.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import com.fimsolution.group.app.dto.business.f2f.ObligationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ScheduleResDto {

    private String id;
    private String loanId;
    private LocalDateTime createAt;
    private Double amount;
    private Double adjustment;
    private Double paid;
    private Double due;
    private Double interest;
    private Double principle;
    private Double fimFee;
    private Double balance;

    private SOURCE source;

    private STATUS status;

    private DELINQUENCY delinquency;
    private String memo;


//    private ObligationDto obligation;
}
