package com.fimsolution.group.app.repository.f2f;

import com.fimsolution.group.app.model.business.f2f.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payment, String> {

//    @Query("SELECT p FROM Payment p " +
//            "JOIN p.schedule s " +
//            "JOIN s.obligation o " +
//            "JOIN o.event e " +
//            "JOIN e.loanUser lu " +
//            "WHERE lu.loanUserId = :loanUserId")
//    List<Payment> findPaymentsByLoanUserId(@Param("loanUserId") String loanUserId);
}
