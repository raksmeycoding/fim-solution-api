package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleAmountDueDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleResDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResDto createSchedule(ScheduleReqDto scheduleReqDto);

    ScheduleResDto getScheduleById(String scheduleId);

    ScheduleDto updateScheduleById(String schedule, ScheduleDto scheduleDto);

    void deleteScheduleById(String scheduleId);

    List<ScheduleDto> getSchedules();

    List<ScheduleDto> getSchedulesByObligationId(String obligationId);

    List<ScheduleResDto> getAllSchedules();


    List<ScheduleResDto> getScheduleByLoanId(String loanId);

    ScheduleAmountDueDto getAmountDueForCurrentUser();

    ScheduleAmountDueDto getYouAreScheduleToReceive();

    ScheduleAmountDueDto getCalculatedPassedDue();
}


