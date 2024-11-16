package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.loanList.LoanListResDto;

import java.util.List;

public interface LoanListService {

    LoanListResDto getLoanListsForCurrentUser();
}
