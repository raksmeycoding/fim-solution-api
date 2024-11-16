package com.fimsolution.group.app.service.f2f;

import com.fimsolution.group.app.dto.business.f2f.loan.LoanReqDto;
import com.fimsolution.group.app.dto.business.f2f.loan.LoanResDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserReqDto;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserResDto;
import com.fimsolution.group.app.model.business.f2f.Loan;

import java.util.List;

public interface LoanService {

    LoanResDto createLoan(LoanReqDto loanReqDto);

    Loan getLoanById(String id);

    void updateLoan(Loan loan);

    boolean deleteLoanById(String id);

    List<Loan> getAllLoans();

    LoanResDto getCurrentUserLoanLoan();
}
