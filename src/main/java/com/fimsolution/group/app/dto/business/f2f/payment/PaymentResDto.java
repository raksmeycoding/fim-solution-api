package com.fimsolution.group.app.dto.business.f2f.payment;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fimsolution.group.app.constant.business.f2f.payment.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.payment.STATUS;
import com.fimsolution.group.app.constant.business.f2f.payment.TYPE;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleResDto;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDto {

    private String id;

    private LocalDateTime paymentDate;

    private LocalDateTime createdAt;

    private Double amount;
    private Double adjust;
    private Double principle;
    private Double fimFee;
    private Double receipt;
    private Double balance;

    private TYPE type;

    private STATUS status;

    private SOURCE source;
    private String memo;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ScheduleResDto scheduleResDto;

}
