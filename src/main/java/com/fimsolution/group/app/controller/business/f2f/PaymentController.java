package com.fimsolution.group.app.controller.business.f2f;


import com.fimsolution.group.app.dto.RespondDto;
import com.fimsolution.group.app.dto.business.f2f.payment.LinkPaymentToScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentReqDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentResDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentWithSchedule;
import com.fimsolution.group.app.model.security.UserDetailsImpl;
import com.fimsolution.group.app.service.f2f.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/f2f")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<RespondDto<PaymentResDto>> createPayment(@RequestBody PaymentReqDto paymentReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespondDto.<PaymentResDto>builder()
                        .data(paymentService.createPayment(paymentReqDto))
                        .build());
    }

    @Operation(
            summary = "Link payment to schedule if you what to change",
            description = "registration endpoint.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operation Completed Successfully")
            }
    )
    @PostMapping("/payment/schedule")
    public ResponseEntity<RespondDto<Boolean>> linkPaymentToSchedule(@RequestBody LinkPaymentToScheduleReqDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(RespondDto.<Boolean>builder()
                .data(paymentService.linkPaymentToSchedule(body))
                .message("Payment link completed successfully")
                .httpStatusName(HttpStatus.CREATED)
                .httpStatusCode(HttpStatus.CREATED.value())
                .build());
    }


    @GetMapping("/payment/schedule/{scheduleId}")
    public ResponseEntity<RespondDto<List<PaymentResDto>>> getPaymentSchedule(@PathVariable("scheduleId") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.<List<PaymentResDto>>builder()
                .data(paymentService.getPaymentByScheduleId(id))
                .message("Get payment completed successfully")
                .httpStatusName(HttpStatus.OK)
                .httpStatusCode(HttpStatus.OK.value())
                .build());
    }


    @GetMapping("/payments")
    public ResponseEntity<RespondDto<List<PaymentResDto>>> getPayments() {
        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.<List<PaymentResDto>>builder()
                .httpStatusCode(HttpStatus.OK.value())
                .httpStatusName(HttpStatus.OK)
                .data(paymentService.getAllPayments())
                .message("Get all payment done").build());
    }


//    @GetMapping("/payments/loanUser/{id}")
//    public ResponseEntity<RespondDto<List<PaymentResDto>>> getPaymentByLoanUserId(@PathVariable(name = "id", required = true) String loanUserId) {
//        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.<List<PaymentResDto>>builder()
//                .data(paymentService.getPaymentByLoanUserId(loanUserId))
//                .message("Get all payment done")
//                .httpStatusName(HttpStatus.OK)
//                .httpStatusCode(HttpStatus.OK.value())
//                .build());
//    }


    @GetMapping("/payments/current-user/last-4-payments")
    public ResponseEntity<RespondDto<List<PaymentResDto>>> getDefaultLoan() {

        return ResponseEntity.status(HttpStatus.OK).body(RespondDto.<List<PaymentResDto>>builder()
                .httpStatusCode(HttpStatus.OK.value())
                .httpStatusName(HttpStatus.OK)
                .data(paymentService.getLast4PaymentsForDefaultLoan())
                .message("Get all payment done").build());


    }




}
