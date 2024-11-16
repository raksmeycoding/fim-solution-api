package com.fimsolution.group.app.service;

import com.fimsolution.group.app.model.business.test.Borrower;

import java.util.Optional;

public interface BorrowerService {

    Optional<Borrower> findBorrowerById(String borrowerId);
}
