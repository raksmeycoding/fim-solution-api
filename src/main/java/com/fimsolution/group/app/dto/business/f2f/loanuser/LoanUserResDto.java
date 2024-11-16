package com.fimsolution.group.app.dto.business.f2f.loanuser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fimsolution.group.app.constant.business.f2f.loanuser.*;
import com.fimsolution.group.app.model.business.f2f.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanUserResDto {


//    private String id;

    private String userId;

    private String loanId;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String loanUserId;


    private String loanName;

    private ROLE role;
    private TYPE type;
//    private EMAIL email;
//    private TEXT text;
//    private PHONE phone;
//
//    private ADDRESS address;

    private PRIORITIZE prioritize;
    private String memo;

    /**
     * @Note: Developer flow
     */
    private String email;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private User user;


    /*
     * @Note: Should create seperated endpoints to reduce time loan
     * */
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private List<Events> events;


}
