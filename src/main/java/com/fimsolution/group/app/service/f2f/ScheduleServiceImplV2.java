package com.fimsolution.group.app.service.f2f;


import com.fimsolution.group.app.constant.business.f2f.schedule.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleAmountDueDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleResDto;
import com.fimsolution.group.app.exception.NotFoundException;
import com.fimsolution.group.app.mapper.business.f2f.ScheduleMapper;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import com.fimsolution.group.app.repository.f2f.ScheduleRepository;
import com.fimsolution.group.app.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("ScheduleServiceImplV2")
@RequiredArgsConstructor
public class ScheduleServiceImplV2 implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final Logger logger = LoggerFactory.getLogger(ScheduleServiceImplV2.class);


    @Override
    public ScheduleResDto createSchedule(ScheduleReqDto scheduleReqDto) {
        return null;
    }

    @Override
    public ScheduleResDto getScheduleById(String scheduleId) {
        return null;
    }

    @Override
    public ScheduleDto updateScheduleById(String schedule, ScheduleDto scheduleDto) {
        return null;
    }

    @Override
    public void deleteScheduleById(String scheduleId) {

    }

    @Override
    public List<ScheduleDto> getSchedules() {
        return List.of();
    }

    @Override
    public List<ScheduleDto> getSchedulesByObligationId(String obligationId) {
        return List.of();
    }

    @Override
    public List<ScheduleResDto> getAllSchedules() {
        return List.of();
    }

    @Override
    public List<ScheduleResDto> getScheduleByLoanId(String loanId) {
        return List.of();
    }

    @Override
    public ScheduleAmountDueDto getAmountDueForCurrentUser() {

        return null;
    }

    @Override
    public Optional<ScheduleAmountDueDto> getAmountDueForCurrentUserV2() {
        String userName = SecurityUtils.getCurrentUser();
        logger.info("Username: {}", userName);


        Optional<Schedule> scheduleOptional = scheduleRepository.findAllScheduleByDefaultLoanAndUserEmailAndSourceAndDue(userName);
        logger.info("Query Schedule: {}", scheduleOptional.isPresent());

        if (scheduleOptional.isEmpty()) {
            logger.info("Schedule not found");
            return Optional.empty();
        }

        Schedule schedule = scheduleOptional.get();

        if (schedule.getSource().equals(SOURCE.BORROWER) || schedule.getSource().equals(SOURCE.LENDER)) {
            logger.info("Invalid source: {}", schedule.getSource());
            return Optional.empty();
        }

        ScheduleAmountDueDto scheduleAmountDueDto = new ScheduleAmountDueDto();
        scheduleAmountDueDto.setDue(schedule.getDue());
        scheduleAmountDueDto.setScheduleId(schedule.getId());
        scheduleAmountDueDto.setSource(schedule.getSource());
        scheduleAmountDueDto.setStatus(schedule.getStatus());
        scheduleAmountDueDto.setCreatedAt(schedule.getCreateAt());
        scheduleAmountDueDto.setBorrowerOrLender(scheduleOptional.get().getSource().name());


        return Optional.of(scheduleAmountDueDto);
    }

    @Override
    public Optional<ScheduleAmountDueDto> getAmountDueByLoanIdAndSourceEqualBorrower(String LoanId) {
        String userName = SecurityUtils.getCurrentUser();
        logger.info("Username: {}", userName);


        Optional<Schedule> scheduleOptional = scheduleRepository.findScheduleByLoanIdAndSourceEqualsBorrower(LoanId);
        logger.info("Query Schedule: {}", scheduleOptional.isPresent());

        if (scheduleOptional.isEmpty()) {
            logger.info("Schedule not found");
            return Optional.empty();
        }

        Schedule schedule = scheduleOptional.get();
        logger.info("Query Schedule: {}", schedule);

        ScheduleAmountDueDto scheduleAmountDueDto = new ScheduleAmountDueDto();
        scheduleAmountDueDto.setDue(schedule.getDue());
        scheduleAmountDueDto.setScheduleId(schedule.getId());
        scheduleAmountDueDto.setSource(schedule.getSource());
        scheduleAmountDueDto.setStatus(schedule.getStatus());
        scheduleAmountDueDto.setCreatedAt(schedule.getCreateAt());
        scheduleAmountDueDto.setBorrowerOrLender(scheduleOptional.get().getSource().name());


        return Optional.of(scheduleAmountDueDto);
    }

    @Override
    public Optional<ScheduleAmountDueDto> getAmountDueByLoanIdAndSourceEqualLender(String LoanId) {
        String userName = SecurityUtils.getCurrentUser();
        logger.info("Username: {}", userName);


        Optional<Schedule> scheduleOptional = scheduleRepository.findScheduleByLoanIdAndSourceEqualsLender(LoanId);

        logger.info("Query Schedule: {}", scheduleOptional.isPresent());

        if (scheduleOptional.isEmpty()) {
            logger.info("Schedule not found");
            return Optional.empty();
        }

        Schedule schedule = scheduleOptional.get();
        logger.info("Query Schedule: {}", schedule);

        ScheduleAmountDueDto scheduleAmountDueDto = new ScheduleAmountDueDto();
        scheduleAmountDueDto.setDue(schedule.getDue());
        scheduleAmountDueDto.setScheduleId(schedule.getId());
        scheduleAmountDueDto.setSource(schedule.getSource());
        scheduleAmountDueDto.setStatus(schedule.getStatus());
        scheduleAmountDueDto.setCreatedAt(schedule.getCreateAt());
        scheduleAmountDueDto.setBorrowerOrLender(scheduleOptional.get().getSource().name());


        return Optional.of(scheduleAmountDueDto);
    }

    @Override
    public ScheduleAmountDueDto getYouAreScheduleToReceive() {
        return null;
    }

    @Override
    public ScheduleAmountDueDto getCalculatedPassedDue() {
        return null;
    }


    @Override
    public ScheduleAmountDueDto getCalculatedPassedDueByLoanId(String LoanId) {
        logger.info("Calculated Passed Due: {}", LoanId);

        List<STATUS> pastDueStatuses = List.of(
                STATUS.PAST_DUE_5,
                STATUS.PAST_DUE_30,
                STATUS.PAST_DUE_60,
                STATUS.PAST_DUE_90
        );

        List<Schedule> schedules = scheduleRepository.getSchedulesPastDueByLoanId(LoanId, pastDueStatuses);


        if (schedules.isEmpty()) {
            throw new NotFoundException("Schedule not found");
        }

        // set type of use borrower or lender or proxy
        ScheduleAmountDueDto scheduleAmountResDto = ScheduleAmountDueDto.builder().build();
        Double dueCalculation = 0D;
        for (Schedule schedule : schedules) {
            dueCalculation += schedule.getDue();
        }
        scheduleAmountResDto.setDue(dueCalculation);
        scheduleAmountResDto.setStatus(scheduleAmountResDto.getStatus());
        scheduleAmountResDto.setSource(scheduleAmountResDto.getSource());

        return scheduleAmountResDto;
    }
}
