package com.fimsolution.group.app.integration.repository;

import com.fimsolution.group.app.constant.LOAN_TYPE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.PRIORITIZE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.ROLE;
import com.fimsolution.group.app.constant.business.f2f.loanuser.TYPE;
import com.fimsolution.group.app.dto.business.f2f.loanuser.LoanUserProjection;
import com.fimsolution.group.app.model.business.f2f.Loan;
import com.fimsolution.group.app.model.business.f2f.LoanUser;
import com.fimsolution.group.app.model.business.f2f.User;
import com.fimsolution.group.app.model.security.UserCredential;
import com.fimsolution.group.app.repository.UserCredentialRepository;
import com.fimsolution.group.app.repository.f2f.LoanRepository;
import com.fimsolution.group.app.repository.f2f.LoanUsersRepository;
import com.fimsolution.group.app.repository.f2f.UsersRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("LoanUser Repository Test Suite")
public class LoanUserRepoTesting {

    private static final String TEST_USER_EMAIL = "david@gmail.com"; // Constant for repetitive values
    private static final Logger logger = LoggerFactory.getLogger(LoanUserRepoTesting.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanUsersRepository loanUsersRepository;
    @Autowired
    private UsersRepository usersRepository;

    private UserCredential userCredential;
    private User user;
    private LoanUser loanUser;
    private Loan loan;

    @BeforeEach
    void setUp() {
        userCredential = createUserCredential(TEST_USER_EMAIL);
        user = createUser(TEST_USER_EMAIL);
        loan = createLoan();
        loanUser = createLoanUser(TEST_USER_EMAIL);
    }

    @Nested
    @DisplayName("When creating a LoanUser with a valid loan")
    class WhenCreatingLoanUser {

        @Test
        @Transactional
        @Rollback(false) // Consider using @Rollback(true) for unit tests
        @DisplayName("should correctly associate the user with a default loan")
        void shouldCheckIfLoanUserHasDefaultLoan() {
            // Save UserCredential and assert it's not null
            UserCredential savedUserCredential = userCredentialRepository.save(userCredential);
            assertThat(savedUserCredential).isNotNull();

            // Save User and associate UserCredential
            User savedUser = usersRepository.save(user);
            assertThat(savedUser).isNotNull();
            savedUser.setUserCredential(savedUserCredential); // Link credential to user
            User withUserCredential = usersRepository.save(savedUser);
            assertThat(withUserCredential).isNotNull();

            // Save Loan and assert it's not null
            Loan savedLoan = loanRepository.save(loan);
            assertThat(savedLoan).isNotNull();

            // Create LoanUser and link it to the Loan and User
            loanUser.setLoan(savedLoan);
            loanUser.setUser(withUserCredential);
            LoanUser savedLoanUser = loanUsersRepository.save(loanUser);
            assertThat(savedLoanUser).isNotNull();

            // Check if the LoanUser has a default loan associated with the email
            Optional<LoanUserProjection> loanUserProjection = loanUsersRepository.checkLoanUserHasDefaultLoanOrNot(TEST_USER_EMAIL);

            // Assert that a LoanUserProjection is returned
            assertThat(loanUserProjection).isPresent();
            loanUserProjection.ifPresent(projection ->
                    logger.info("Loan user projection: {}", projection)); // Log the result for debugging
        }
    }

    @Nested
    @DisplayName("When no loans are associated with the user")
    class WhenNoLoansAreAssociated {

        @Test
        @Transactional
        @Rollback(true) // Rollback to keep the test isolated
        @DisplayName("should return an empty result for default loan check")
        void shouldReturnEmptyWhenNoDefaultLoan() {
            // Test with a user who does not have any loans
            Optional<LoanUserProjection> loanUserProjection = loanUsersRepository.checkLoanUserHasDefaultLoanOrNot("nonexistent@example.com");
            assertThat(loanUserProjection).isNotPresent();
        }
    }

    // Helper methods to create entities
    private UserCredential createUserCredential(String email) {
        String password = passwordEncoder.encode("2H5pLJdKIzGdrt.&afYy"); // Use a consistent password
        UserCredential credential = new UserCredential();
        credential.setFirstname("david");
        credential.setLastname("fim");
        credential.setPhone("(+855) 78768764");
        credential.setUsername(email);
        credential.setPassword(password);
        credential.setEnabled(true);
        credential.setAccountNonExpired(true);
        credential.setAccountNonLocked(true);
        credential.setCredentialsNonExpired(true);
        return credential;
    }

    private User createUser(String email) {
        return User.builder()
                .email(email)
                .familyName("david fam")
                .middleName("david middle")
                .nickName("david nickname")
                .build();
    }

    private Loan createLoan() {
        return Loan.builder()
                .nickName("Nick name loan")
                .interest(300.0)
                .loanType(LOAN_TYPE.FIXED)
                .amount(200.0)
                .fee(20.0)
                .note("This loan must be critical")
                .createdDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusMonths(6)) // Added realistic end date
                .build();
    }

    private LoanUser createLoanUser(String email) {
        return LoanUser.builder()
                .memo("Memo")
                .role(ROLE.BORROWER)
                .loanName("LoanUserName")
                .type(TYPE.ACCESS)
                .email(email)
                .prioritize(PRIORITIZE.DEFAULT)
                .build();
    }
}
