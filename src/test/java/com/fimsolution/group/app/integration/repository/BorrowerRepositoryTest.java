//package com.fimsolution.group.app.integration.repository;
//
//
//import com.fimsolution.group.app.model.business.test.Borrower;
//import com.fimsolution.group.app.repository.BorrowerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@SpringBootTest
//@ActiveProfiles("dev")
//public class BorrowerRepositoryTest extends AbstractTestNGSpringContextTests {
//
//
//    @Autowired
//    private BorrowerRepository borrowerRepository;
//
//
//    // Helper method to create a Borrower with random data
//    private Borrower createBorrower() {
//        Borrower borrower = new Borrower();
//        borrower.setName("Borrower one");
//        borrower.setEmail("borrowerone@gmail.com");
//        borrower.setAddress("borrowerone@gmail.com");
//        borrower.setPhoneNumber("078768764");
//        borrower.setCreatedAt(LocalDateTime.now());
//        return borrower;
//    }
//
//    @BeforeMethod
//    public void cleanUp() {
//        borrowerRepository.deleteAll();  // Ensure a clean state before each test
//    }
//
//    @Test
//    public void testCreateAndRetrieveBorrower() {
//        Borrower borrower = new Borrower();
//        borrower.setName("Borrower one");
//        borrower.setEmail("borrowerone@gmail.com");
//        borrower.setAddress("borrowerone@gmail.com");
//        borrower.setPhoneNumber("078768764");
//        borrower.setCreatedAt(LocalDateTime.now());
//
//        borrowerRepository.save(borrower);
//
//        // Retrieve the Borrower from the database
//        Borrower foundBorrower = borrowerRepository.findByEmail(borrower.getEmail()).orElse(null);
//
//        // Assertions
//        Assert.assertNotNull(foundBorrower, "Borrower should not be null");
//        Assert.assertEquals(foundBorrower.getName(), "Borrower one", "Names should match");
//        Assert.assertEquals(foundBorrower.getEmail(), "borrowerone@gmail.com", "Emails should match");
//        Assert.assertEquals(foundBorrower.getPhoneNumber(), "078768764", "Phone numbers should match");
//    }
//
//
//    @Test
//    public void testBorrowerNotFound() {
//        Borrower foundBorrower = borrowerRepository.findByEmail("nonexistent@example.com").orElse(null);
//        Assert.assertNull(foundBorrower, "Borrower should be null when not found");
//    }
//
//
//    @Test
//    public void testDeleteBorrower() {
//        Borrower borrower = createBorrower();
//        borrowerRepository.save(borrower);
//
//        borrowerRepository.delete(borrower);
//        Borrower deletedBorrower = borrowerRepository.findByEmail(borrower.getEmail()).orElse(null);
//        Assert.assertNull(deletedBorrower, "Borrower should be null after deletion");
//    }
//}
