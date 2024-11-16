package com.fimsolution.group.app.dto.business.f2f;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fimsolution.group.app.constant.LOAN_TYPE;
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
public class LoanDto {

    @JsonIgnore
    private String id;

    @JsonIgnore
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


    @JsonIgnore
    private List<Event> events;


    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getLoanId() {
        return loanId;
    }

    @JsonProperty
    public List<Event> getEvents() {
        return events;
    }
}
