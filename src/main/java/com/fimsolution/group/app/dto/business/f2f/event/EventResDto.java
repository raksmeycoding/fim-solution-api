package com.fimsolution.group.app.dto.business.f2f.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fimsolution.group.app.constant.business.f2f.event.MEDIUM;
import com.fimsolution.group.app.constant.business.f2f.event.TYPE;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResDto {

    private String id;


    private LocalDateTime createdEventDateTime;


    private TYPE type;


    private MEDIUM medium;


    private String memo;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LoanUser loanUser;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Loan loan;
}
