package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.loanuser.LinkLoanUserToUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserProjection;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserResDto;

import java.util.List;

public interface LoanUsersService {

    LoanUserResDto createLoanUser(LoanUserReqDto loanUserReqDto);

    Boolean linkLonUserToUser(LinkLoanUserToUserReqDto linkLoanUserToUserReqDto);

    List<LoanUserProjection> getLoanUsers();
}
