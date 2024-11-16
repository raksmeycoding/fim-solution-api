package com.fimsolution.group.app.repository;

import com.fimsolution.group.app.model.business.test.Lender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface LenderRepository extends JpaRepository<Lender, String> {
}
