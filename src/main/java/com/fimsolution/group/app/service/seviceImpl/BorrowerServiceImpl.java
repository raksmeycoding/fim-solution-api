package com.fimsolution.group.app.service.seviceImpl;

import com.fimsolution.group.app.model.business.test.Borrower;
import com.fimsolution.group.app.repository.BorrowerRepository;
import com.fimsolution.group.app.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BorrowerServiceImpl implements BorrowerService {
    private final BorrowerRepository borrowerRepository;


    @Override
    public Optional<Borrower> findBorrowerById(String borrowerId) {
        // Retrieve the Borrower from the repository
        return borrowerRepository.findById(borrowerId);
    }
}
