package com.fimsolution.group.app.mapper.business.f2f;

import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleResDto;
import com.fimsolution.group.app.model.business.f2f.Schedule;

public class ScheduleMapper {


    public static Schedule fromScheduleDtoToScheduleEntity(ScheduleDto scheduleDto) {
        return Schedule.builder()
                .createAt(scheduleDto.getCreateAt())
                .amount(scheduleDto.getAmount())
                .adjustment(scheduleDto.getAdjustment())
                .paid(scheduleDto.getPaid())
                .due(scheduleDto.getDue())
                .interest(scheduleDto.getInterest())
                .principle(scheduleDto.getPrinciple())
                .fimFee(scheduleDto.getFimFee())
                .balance(scheduleDto.getBalance())
                .source(scheduleDto.getSource())
                .delinquency(scheduleDto.getDelinquency())
                .memo(scheduleDto.getMemo())
                .status(scheduleDto.getStatus())
                .build();
    }


    public static Schedule fromScheduleReqDtoToScheduleEntity(ScheduleReqDto scheduleReqDto) {
        return Schedule.builder()
                .createAt(scheduleReqDto.getCreateAt())
                .amount(scheduleReqDto.getAmount())
                .adjustment(scheduleReqDto.getAdjustment())
                .paid(scheduleReqDto.getPaid())
                .due(scheduleReqDto.getDue())
                .interest(scheduleReqDto.getInterest())
                .principle(scheduleReqDto.getPrinciple())
                .fimFee(scheduleReqDto.getFimFee())
                .balance(scheduleReqDto.getBalance())
                .source(scheduleReqDto.getSource())
                .delinquency(scheduleReqDto.getDelinquency())
                .memo(scheduleReqDto.getMemo())
                .status(scheduleReqDto.getStatus())
                .build();
    }


    public static ScheduleResDto fromScheduleDtoToScheduleResDto(ScheduleDto scheduleDto) {
        return ScheduleResDto.builder()
                .createAt(scheduleDto.getCreateAt())
                .amount(scheduleDto.getAmount())
                .adjustment(scheduleDto.getAdjustment())
                .paid(scheduleDto.getPaid())
                .due(scheduleDto.getDue())
                .interest(scheduleDto.getInterest())
                .principle(scheduleDto.getPrinciple())
                .fimFee(scheduleDto.getFimFee())
                .balance(scheduleDto.getBalance())
                .source(scheduleDto.getSource())
                .delinquency(scheduleDto.getDelinquency())
                .memo(scheduleDto.getMemo())
                .status(scheduleDto.getStatus())
                .build();
    }

    public static ScheduleResDto fromScheduleEntityToScheduleResDto(Schedule schedule) {
        return ScheduleResDto.builder()
                .id(schedule.getId())
                .loanId(schedule.getLoan() != null ? schedule.getLoan().getId() : null)
                .createAt(schedule.getCreateAt())
                .amount(schedule.getAmount())
                .adjustment(schedule.getAdjustment())
                .paid(schedule.getPaid())
                .due(schedule.getDue())
                .interest(schedule.getInterest())
                .principle(schedule.getPrinciple())
                .fimFee(schedule.getFimFee())
                .balance(schedule.getBalance())
                .source(schedule.getSource())
                .delinquency(schedule.getDelinquency())
                .memo(schedule.getMemo())
                .status(schedule.getStatus())
                .build();
    }

    public static ScheduleDto fromScheduleEntityToScheduleDto(Schedule schedule) {
        return ScheduleDto
                .builder()
                .createAt(schedule.getCreateAt())
                .amount(schedule.getAmount())
                .adjustment(schedule.getAdjustment())
                .paid(schedule.getPaid())
                .due(schedule.getDue())
                .interest(schedule.getInterest())
                .principle(schedule.getPrinciple())
                .fimFee(schedule.getFimFee())
                .balance(schedule.getBalance())
                .source(schedule.getSource())
                .delinquency(schedule.getDelinquency())
                .status(schedule.getStatus())
                .memo(schedule.getMemo())
                .build();
    }

}
