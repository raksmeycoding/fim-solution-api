package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.loanuser.LinkLoanUserToUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserProjection;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserResDto;
import com.fimsolution.group.app.model.business.f2f.Loan;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoanUsersService {

    LoanUserResDto createLoanUser(LoanUserReqDto loanUserReqDto);

    Boolean linkLonUserToUser(LinkLoanUserToUserReqDto linkLoanUserToUserReqDto);

    List<LoanUserProjection> getLoanUsers();


    Optional<LoanUserProjection> checkLoanUserHasDefaultLoan();

}
