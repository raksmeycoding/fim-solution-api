package com.fimsolution.group.app.dto.business.f2f.payment;


import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleResDto;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentWithSchedule {

//    private ScheduleResDto schedule;
    private List<PaymentResDto> paymentResDtos;
}
