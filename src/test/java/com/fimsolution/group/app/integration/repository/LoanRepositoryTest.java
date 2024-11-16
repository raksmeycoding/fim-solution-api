//package com.fimsolution.group.app.integration.repository;
//
//
//import com.fimsolution.group.app.model.business.test.Borrower;
//import com.fimsolution.group.app.model.business.test.Lender;
//import com.fimsolution.group.app.model.business.f2f.entity.Loan;
//import com.fimsolution.group.app.repository.BorrowerRepository;
//import com.fimsolution.group.app.repository.LenderRepository;
//import com.fimsolution.group.app.repository.f2f.LoanRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@SpringBootTest
//@ActiveProfiles("dev")
//public class LoanRepositoryTest extends AbstractTestNGSpringContextTests {
//
//    @Autowired
//    private BorrowerRepository borrowerRepository;
//
//    @Autowired
//    private LenderRepository lenderRepository;
//
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//
//    private static final Logger logger = LoggerFactory.getLogger(LoanRepositoryTest.class);
//
//    @Test
//    public void initialTest() {
//
//    }
//
//
//    @Test
//    @Transactional
//    public void testSaveAndFindWithRelationships() {
//        // create a new borrower
//        Borrower borrower = Borrower.builder()
//                .email("borrower@gmail.com")
//                .address("borrower@gmail.com")
//                .name("borrower")
//                .createdAt(LocalDateTime.now())
//                .phoneNumber("078768764")
//                .build();
//
//        borrower = borrowerRepository.save(borrower);
//
//        logger.info(":::Borrower:::{}", borrower);
//
//
//
//
//        // create a new lender
//        Lender lender = Lender.builder()
//                .email("borrower@gmail.com")
//                .address("borrower@gmail.com")
//                .name("borrower")
//                .createdAt(LocalDateTime.now())
//                .phoneNumber("078768764")
//                .build();
//
//        lender = lenderRepository.save(lender);
//
//
//        logger.info(":::Lender:::{}", lender);
//
//
//
//        // create a new loan
//        Loan loan = Loan.builder()
//                .loanId("FA001")
//                .amount(2000.00)
//                .interest(8.00)
//                .lender(lender)
//                .borrower(borrower)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        loan = loanRepository.save(loan);
//
//        logger.info(":::Save Loan:::{}", loan);
//
//
//        // find loan by id
//        Optional<Loan> foundLoan = loanRepository.findById(loan.getId());
//
//        logger.info(":::Find Loan:::{}", foundLoan);
//
//
//        // Assertion
//        Assert.assertTrue(foundLoan.isPresent());
//        Assert.assertEquals(2000.00, foundLoan.get().getAmount());
//        Assert.assertEquals(8.00, foundLoan.get().getInterest());
//        Assert.assertEquals("FA001", foundLoan.get().getLoanId());
//        Assert.assertNotNull(borrower, "Borrower should not be null");
//        Assert.assertNotNull(lender, "lender should not be null");
//    }
//}
