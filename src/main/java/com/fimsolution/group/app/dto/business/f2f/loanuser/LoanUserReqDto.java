package com.fimsolution.group.app.dto.business.f2f.loanuser;

import com.fimsolution.group.app.constant.business.f2f.loanuser.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanUserReqDto {


    private String userId;

    private String loanId;

    private String loanName;

    private ROLE role;

    private TYPE type;

//    private EMAIL email;
//
//    private TEXT text;
//
//    private PHONE phone;
//
//    private ADDRESS address;

    private PRIORITIZE prioritize;

    private String memo;

    /**
     * @Note: Developer flow
     */
    private String email;


}
