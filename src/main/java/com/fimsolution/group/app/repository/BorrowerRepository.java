package com.fimsolution.group.app.repository;


import com.fimsolution.group.app.model.business.test.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, String> {

    Optional<Borrower> findByEmail(String email);
}
