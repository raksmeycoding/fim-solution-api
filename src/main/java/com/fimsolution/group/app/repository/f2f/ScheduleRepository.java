package com.fimsolution.group.app.repository.f2f;

import com.fimsolution.group.app.constant.business.f2f.schedule.STATUS;
import com.fimsolution.group.app.model.business.f2f.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    List<Schedule> findByObligationId(String id);

    @Query("""
                        select s from Schedule s   
                        join Loan l 
                        on s.loan.id = l.id 
                        join LoanUser  lu 
                        on l.id = lu.loan.id 
                        where lu.email = :email 
                        and s.due > 0 
                        and lu.prioritize = 'DEFAULT'
                        and s.status = 'FUTURE'
                        and s.createAt >= CURRENT DATE 
                        order by s.createAt asc limit 1
            """)
    Optional<Schedule> findAllScheduleByDefaultLoanAndUserEmailAndSourceAndDue(@Param("email") String email);


    @Query("""
              select s from Schedule s  
              join Loan l
              on s.loan.id = l.id
              join LoanUser  lu
              on l.id = lu.loan.id
              and s.due > 0
              and s.loan.id = :loanId
              and s.source = 'BORROWER'
              and s.status = 'FUTURE'
              and s.createAt >= CURRENT DATE
              order by s.createAt asc limit 1
            """)
    Optional<Schedule> findScheduleByLoanIdAndSourceEqualsBorrower(@Param("loanId") String loanId);


    @Query("""
              select s from Schedule s  
              join Loan l
              on s.loan.id = l.id
              join LoanUser  lu
              on l.id = lu.loan.id
              and s.due > 0
              and s.loan.id = :loanId
              and s.source = 'LENDER'
              and s.status = 'FUTURE'
              and s.createAt >= CURRENT DATE
              order by s.createAt asc limit 1
            """)
    Optional<Schedule> findScheduleByLoanIdAndSourceEqualsLender(@Param("loanId") String loanId);


    @Query("""
            select s from Schedule s
            join Loan l on s.loan.id = l.id
            where l.id = :loanId
            and s.status in :statuses
            and s.createAt < CURRENT DATE
            """)
    List<Schedule> getSchedulesPastDueByLoanId(String loanId, List<STATUS> statuses);


}
