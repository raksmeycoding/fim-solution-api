package com.fimsolution.group.app.dto.business.f2f.loan;

import com.fimsolution.group.app.constant.LOAN_TYPE;
import com.fimsolution.group.app.model.business.f2f.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDto {


    private String id;



    private String loanId;

    private String nickName;


    private Double interest;


    private LOAN_TYPE loanType;


    private Double amount;


    private Double fee;


    private String note;



    private LocalDateTime createdDate;



    private LocalDateTime endDate;

    private boolean isLinkToLoanUser;



    private List<Event> events;

}