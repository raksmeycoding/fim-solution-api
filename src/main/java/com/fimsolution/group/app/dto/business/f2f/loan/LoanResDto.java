package com.fimsolution.group.app.dto.business.f2f.loan;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fimsolution.group.app.constant.LOAN_TYPE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.ROLE;
import com.fimsolution.group.app.model.business.f2f.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanResDto {

    private String id;

    private String nickName;


    private Double interest;


    private LOAN_TYPE loanType;


    private Double amount;


    private Double fee;


    private String note;


    private LocalDateTime createdDate;


    private LocalDateTime endDate;


    private boolean isLinkToLoanUser;

    /**
     * @Note: Developer requirement
     */
    private ROLE role;


}