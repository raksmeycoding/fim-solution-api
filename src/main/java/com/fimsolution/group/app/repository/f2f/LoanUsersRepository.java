package com.fimsolution.group.app.repository.f2f;

import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserProjection;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LoanUsersRepository extends JpaRepository<LoanUser, String> {
    Optional<LoanUser> findByUserId(String userId);

    @Query("""
            select lu from LoanUser lu
            join lu.user u
            join lu.loan l
            """)
    List<LoanUserProjection> findAllLoanUserProjects();

    boolean existsByUserIdAndLoanId(String userId, String loanId);

    Optional<LoanUser> findFirstByUserIdAndLoanId(String userId, String loanId);

    Optional<LoanUser> findLoanUserByUserEmailAndPrioritize(String email, PRIORITIZE prioritize);

}
