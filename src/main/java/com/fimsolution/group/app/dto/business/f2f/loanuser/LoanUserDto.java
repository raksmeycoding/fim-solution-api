package com.fimsolution.group.app.dto.business.f2f.loanuser;

import com.fimsolution.group.app.constant.business.f2f.loanuser.*;
import com.fimsolution.group.app.model.business.f2f.Event;
import com.fimsolution.group.app.model.business.f2f.User;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanUserDto {


    private String id;

    private String loanUserId;



    private String loanName;

    private ROLE role;
    private TYPE type;
//    private EMAIL email;
//    private TEXT text;
//    private PHONE phone;
//
//    private ADDRESS address;
    private String memo;

    private PRIORITIZE prioritize;

    /**
     * @Note: Developer flow
     */
    private String email;

    private User user;


    private List<Event> events;
}
