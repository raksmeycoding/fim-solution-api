package com.fimsolution.group.app.dto.business.f2f.schedule;


import com.fimsolution.group.app.constant.business.f2f.schedule.DELINQUENCY;
import com.fimsolution.group.app.constant.business.f2f.schedule.SOURCE;
import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleReqDto {

    //    @NotBlank(message = "obligationId can not be blank!")
    //    @NotEmpty(message = "obligationId can not be empty!")
    //    private String obligationId;
    @NotBlank(message = "Loan number can not be blank!")
    @NotEmpty(message = "Loan number can not be empty!")
    private String loanId;

    private LocalDateTime createAt;

    private Double amount;

    private Double adjustment;

    private Double paid;

    private Double due;

    private Double interest;

    private Double principle;

    private Double fimFee;

    private Double balance;

    private SOURCE source;

    private STATUS status;

    private DELINQUENCY delinquency;
    private String memo;
}
