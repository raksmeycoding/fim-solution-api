package com.fimsolution.group.app.controller.business.f2f;

import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.schedule.ScheduleAmountDueDto;
import com.fimsolution.group.app.service.f2f.ScheduleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/api/v2/f2f")
public class ScheduleControllerV2 {

    private final ScheduleService scheduleService;

    public ScheduleControllerV2(@Qualifier("ScheduleServiceImplV2") ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule/amount-due")
    public ResponseEntity<RespondDto<?>> getAmountDueForCurrentUser() {
        /**
         * @Note: Current user can be a borrower or a lender
         * */

        Optional<ScheduleAmountDueDto> scheduleAmountDueDtoOptional = scheduleService.getAmountDueForCurrentUserV2();

        return scheduleAmountDueDtoOptional
                .<ResponseEntity<RespondDto<?>>>map(scheduleAmountDueDto
                        -> ResponseEntity.status(HttpStatus.OK)
                        .body(RespondDto.<ScheduleAmountDueDto>builder()
                                .data(scheduleAmountDueDto)
                                .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespondDto.builder()
                        .data(null)
                        .errorMessage("Schedule not found")
                        .httpStatusCode(HttpStatus.NOT_FOUND.value())
                        .httpStatusName(HttpStatus.NOT_FOUND)
                        .build()));

    }


    @GetMapping("/schedule/loan/{loanId}/amount-due/borrower")
    public ResponseEntity<RespondDto<?>> getAmountDueByLoanIdAndSourceEqualBorrower(@PathVariable String loanId) {
        /**
         * @Note: Current user can be a borrower or a lender
         * */

        Optional<ScheduleAmountDueDto> scheduleAmountDueDtoOptional = scheduleService.getAmountDueByLoanIdAndSourceEqualBorrower(loanId);

        return scheduleAmountDueDtoOptional
                .<ResponseEntity<RespondDto<?>>>map(scheduleAmountDueDto
                        -> ResponseEntity.status(HttpStatus.OK)
                        .body(RespondDto.<ScheduleAmountDueDto>builder()
                                .data(scheduleAmountDueDto)
                                .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespondDto.builder()
                        .data(null)
                        .errorMessage("Schedule not found")
                        .httpStatusCode(HttpStatus.NOT_FOUND.value())
                        .httpStatusName(HttpStatus.NOT_FOUND)
                        .build()));

    }


    @GetMapping("/schedule/loan/{loanId}/amount-due/lender")
    public ResponseEntity<RespondDto<?>> getAmountDueByLoanIdAndSourceEqualLender(@PathVariable String loanId) {
        /**
         * @Note: Current user can be a borrower or a lender
         * */

        Optional<ScheduleAmountDueDto> scheduleAmountDueDtoOptional = scheduleService.getAmountDueByLoanIdAndSourceEqualLender(loanId);

        return scheduleAmountDueDtoOptional
                .<ResponseEntity<RespondDto<?>>>map(scheduleAmountDueDto
                        -> ResponseEntity.status(HttpStatus.OK)
                        .body(RespondDto.<ScheduleAmountDueDto>builder()
                                .data(scheduleAmountDueDto)
                                .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespondDto.builder()
                        .data(null)
                        .errorMessage("Schedule not found")
                        .httpStatusCode(HttpStatus.NOT_FOUND.value())
                        .httpStatusName(HttpStatus.NOT_FOUND)
                        .build()));

    }
}
