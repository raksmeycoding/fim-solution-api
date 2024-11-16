package com.fimsolution.group.app.repository.f2f;

import com.fimsolution.group.app.model.business.f2f.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {
}
