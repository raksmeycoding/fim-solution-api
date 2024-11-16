package com.fimsolution.group.app.service.f2f;


import com.fimsolution.group.app.dto.business.f2f.payment.LinkPaymentToScheduleReqDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentReqDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentResDto;
import com.fimsolution.group.app.dto.business.f2f.payment.PaymentWithSchedule;
import com.fimsolution.group.app.model.business.f2f.Payment;

import java.util.List;

public interface PaymentService {
    PaymentResDto createPayment(PaymentReqDto paymentReqDto);

    boolean linkPaymentToSchedule(LinkPaymentToScheduleReqDto body);

    List<PaymentResDto> getAllPayments();

//    List<PaymentResDto> getPaymentByLoanUserId(String loanUserId);

    List<PaymentResDto> getPaymentByScheduleId(String userId);

    List<PaymentResDto> getLast4PaymentsForDefaultLoan();
}
