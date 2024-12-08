package com.fimsolution.group.app.controller.business.f2f;


import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleAmountDueDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleResDto;
import com.fimsolution.group.app.mapper.business.f2f.ScheduleMapper;
import com.fimsolution.group.app.service.f2f.ScheduleService;
import com.fimsolution.group.app.service.f2f.ScheduleServiceImplV2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/f2f")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleServiceImplV2 scheduleServiceImplV2;

    public ScheduleController(@Qualifier("ScheduleServiceImpl") ScheduleService scheduleService, ScheduleServiceImplV2 scheduleServiceImplV2) {
        this.scheduleService = scheduleService;
        this.scheduleServiceImplV2 = scheduleServiceImplV2;
    }


    @PostMapping("/schedule")
    public ResponseEntity<RespondDto<ScheduleResDto>> createSchedule(@RequestBody ScheduleReqDto scheduleReqDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(RespondDto.<ScheduleResDto>builder().httpStatusName(HttpStatus.CREATED).message("Schedule created")
                .data(scheduleService.createSchedule(scheduleReqDto))
                .build());
    }


    @GetMapping("/schedule/all")
    public ResponseEntity<RespondDto<List<ScheduleResDto>>> getAllSchedules() {
        return ResponseEntity.status(HttpStatus.CREATED).body(RespondDto.<List<ScheduleResDto>>builder().httpStatusName(HttpStatus.CREATED).message("Schedule created")
                .data(scheduleService.getAllSchedules())
                .build());
    }


    @PostMapping("/schedule/loan/{loanId}")
    public ResponseEntity<RespondDto<List<ScheduleResDto>>> getAllSchedules(@PathVariable("loanId") String idOfLoan) {

        return ResponseEntity.status(HttpStatus.CREATED).body(RespondDto.<List<ScheduleResDto>>builder().httpStatusName(HttpStatus.CREATED).message("Schedule created")
                .data(scheduleService.getScheduleByLoanId(idOfLoan))
                .build());
    }


    @GetMapping("/schedule/{id}")
    public ResponseEntity<RespondDto<ScheduleResDto>> getSchedule(@PathVariable("id") String scheduleId) {


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(RespondDto.<ScheduleResDto>builder()
                        .httpStatusCode(HttpStatus.OK.value())
                        .httpStatusName(HttpStatus.OK)
                        .message("Get data")
                        .data(scheduleService.getScheduleById(scheduleId))
                        .build());
    }


    @GetMapping("/schedule/amount-due")
    public ResponseEntity<RespondDto<?>> getAmountDueForCurrentUser() {
        /**
         * @Note: Current user can be a borrower or a lender
         * */
        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.<ScheduleAmountDueDto>builder()
                .httpStatusName(HttpStatus.OK)
                .httpStatusCode(HttpStatus.OK.value())
                .data(scheduleService.getAmountDueForCurrentUser())
                .build());
    }


    @GetMapping("/schedule/schedule-to-receive")
    public ResponseEntity<RespondDto<ScheduleAmountDueDto>> getYouAreScheduleToReceive() {
        /**
         * @Note: Current user can be a borrower or a lender
         * */
        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.<ScheduleAmountDueDto>builder()
                .httpStatusName(HttpStatus.OK)
                .httpStatusCode(HttpStatus.OK.value())
                .data(scheduleService.getYouAreScheduleToReceive())
                .build());
    }


    @GetMapping("/schedule/past-due/{loanId}")
    public ResponseEntity<RespondDto<ScheduleAmountDueDto>> getCalculatedPassedDue(@PathVariable("loanId") String loanId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                RespondDto.<ScheduleAmountDueDto>builder()
                        .httpStatusName(HttpStatus.OK)
                        .httpStatusCode(HttpStatus.OK.value())
                        .data(scheduleServiceImplV2.getCalculatedPassedDueByLoanId(loanId))
                        .build()
        );
    }


}
