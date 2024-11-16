package com.fimsolution.group.app.dto.business.f2f.loanuser;

import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.ROLE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.TYPE;
import com.fimsolution.group.app.model.business.f2f.Event;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.User;
import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

public interface LoanUserProjection {

    String getId();


    String getLoanName();


    ROLE getRole();

    TYPE getType();


    String memo();


    PRIORITIZE getPrioritize();

    /**
     * @Note: Developer flow
     */
    String getEmail();


    UserProjection getUser();

    interface UserProjection {
        String getId();
    }


    LoanProjection getLoan();

    interface LoanProjection {
        String getId();
    }


}
