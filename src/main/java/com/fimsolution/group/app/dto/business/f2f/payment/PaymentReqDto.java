package com.fimsolution.group.app.dto.business.f2f.payment;


import com.fimsolution.group.app.constant.business.f2f.payment.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.payment.STATUS;
import com.fimsolution.group.app.constant.business.f2f.payment.TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReqDto {

    /**
     * @Note: when the client needs to create with their schedule immediate
     */
    private String scheduleId;


    /**
     * @Note: when the client needs to create with their schedule immediate
     */
    private String loanId;

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

}
